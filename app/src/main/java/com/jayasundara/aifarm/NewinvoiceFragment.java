package com.jayasundara.aifarm;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.FragmentTransaction;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

import java.util.ArrayList;
import java.util.List;

public class NewinvoiceFragment extends Fragment {

    private ListView lvProduct;
    private ProductListAdapter adapter;
    private List<Product> mProductList;

    // android built in classes for bluetooth operations
    BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;

    // needed for communication to bluetooth device / network
    OutputStream mmOutputStream;
    InputStream mmInputStream;
    Thread workerThread;

    byte[] readBuffer;
    int readBufferPosition;
    volatile boolean stopWorker;

    private AutoCompleteTextView client;
    private TextView invoice;
    private TextView date;
    private AutoCompleteTextView product;
    private TextView lot;
    private TextView qty;
    private TextView unit;
    private Button add_btn;
    private  int i=1;

    //String Array For Auto completed texts
    private static final String[] names = new String[]{"Rathnayake Bakers-Kandy","Devon Resturant-Dhalada Veediya","Siriramya Hotel"};
    private static final String[] productslist = new String[]{"Regular Size White Eggs","Medium Size Eggs","Pullet Size Eggs"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_newinvoice,container,false);

        //Add products from "Add" button
        add_btn = (Button) v.findViewById(R.id.add_btn);
        product = (AutoCompleteTextView) v.findViewById(R.id.product);
        lot = (TextView) v.findViewById(R.id.lot);
        qty = (TextView) v.findViewById(R.id.qty);
        unit = (TextView) v.findViewById(R.id.unit);
        invoice = (TextView) v.findViewById(R.id.invoice);
        date = (TextView) v.findViewById(R.id.date);
        client = (AutoCompleteTextView) v.findViewById(R.id.client);

        //Auto complete text
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,names);
        client.setAdapter(adapter1);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,productslist);
        product.setAdapter(adapter2);



        lvProduct = (ListView) v.findViewById(R.id.listview_product);

        mProductList = new ArrayList<>();

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int lot1 = Integer.parseInt(lot.getText().toString());
                int qty1 = Integer.parseInt(qty.getText().toString());
                double unit1 = Double.parseDouble(unit.getText().toString());

                double total = lot1*qty1*unit1;

                mProductList.add(new Product(i,product.getText().toString(),lot.getText().toString()+"*"+qty.getText().toString()+"*"+unit.getText().toString(),""+total));
                adapter = new ProductListAdapter(getActivity().getApplicationContext(),mProductList);
                lvProduct.setAdapter(adapter);
                Toast.makeText(getActivity().getApplicationContext(),"Item Added",Toast.LENGTH_SHORT).show();
                i++;

                product.setText("");
                lot.setText("");
                qty.setText("");
                unit.setText("");

            }
        });


        //Bluetooth operations
        try {
            // more codes will be here
            // we are going to have three buttons for specific functions
            Button openButton = (Button) v.findViewById(R.id.open);
            Button sendButton = (Button) v.findViewById(R.id.send);
            Button closeButton = (Button) v.findViewById(R.id.close);


            // open bluetooth connection
            openButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    try {
                        findBT();
                        openBT();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });


            // send data typed by the user to be printed
            sendButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    try {
                        findBT();
                        openBT();
                        sendData();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });



            // close bluetooth connection
            closeButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    try {
                        closeBT();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });

        }catch(Exception e) {
            e.printStackTrace();
        }

        return v;
    }


    //Calendar Field
    public void onStart(){

        super.onStart();
        date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){

                    DateDialog dialog = new DateDialog(v);
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    dialog.show(ft,"DatePicker");
                }
            }
        });

    }


    // this will find a bluetooth printer device
    void findBT() {

        try {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

            if(mBluetoothAdapter == null) {
                Toast.makeText(getActivity().getApplicationContext(),"No bluetooth adapter available",Toast.LENGTH_SHORT).show();
            }

            if(!mBluetoothAdapter.isEnabled()) {
                Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBluetooth, 0);
            }

            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

            if(pairedDevices.size() > 0) {
                for (BluetoothDevice device : pairedDevices) {

                    // RPP300 is the name of the bluetooth printer device
                    // we got this name from the list of paired devices
                    if (device.getName().equals("MTP-3")) {
                        mmDevice = device;
                        break;
                    }
                }
            }



        }catch(Exception e){
            e.printStackTrace();
        }
    }

    // tries to open a connection to the bluetooth printer device
    void openBT() throws IOException {
        try {

            // Standard SerialPortService ID
            UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
            mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
            mmSocket.connect();
            mmOutputStream = mmSocket.getOutputStream();
            mmInputStream = mmSocket.getInputStream();

            beginListenForData();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*
     * after opening a connection to bluetooth printer device,
     * we have to listen and check if a data were sent to be printed.
     */
    void beginListenForData() {
        try {
            final Handler handler = new Handler();

            // this is the ASCII code for a newline character
            final byte delimiter = 10;

            stopWorker = false;
            readBufferPosition = 0;
            readBuffer = new byte[1024];

            workerThread = new Thread(new Runnable() {
                public void run() {

                    while (!Thread.currentThread().isInterrupted() && !stopWorker) {

                        try {

                            int bytesAvailable = mmInputStream.available();

                            if (bytesAvailable > 0) {

                                byte[] packetBytes = new byte[bytesAvailable];
                                mmInputStream.read(packetBytes);

                                for (int i = 0; i < bytesAvailable; i++) {

                                    byte b = packetBytes[i];
                                    if (b == delimiter) {

                                        byte[] encodedBytes = new byte[readBufferPosition];
                                        System.arraycopy(
                                                readBuffer, 0,
                                                encodedBytes, 0,
                                                encodedBytes.length
                                        );

                                        // specify US-ASCII encoding
                                        final String data = new String(encodedBytes, "US-ASCII");
                                        readBufferPosition = 0;

                                        // tell the user data were sent to bluetooth printer device
                                        handler.post(new Runnable() {
                                            public void run() {

                                                Toast.makeText(getActivity().getApplicationContext(),data,Toast.LENGTH_SHORT).show();

                                            }
                                        });

                                    } else {
                                        readBuffer[readBufferPosition++] = b;
                                    }
                                }
                            }

                        } catch (IOException ex) {
                            stopWorker = true;
                        }

                    }
                }
            });

            workerThread.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    // this will send text data to be printed by the bluetooth printer
    void sendData() throws IOException {
        try {

            String msg1 = "\n------------------------------------------------\n";
            mmOutputStream.write(PrinterCommands.ESC_RESETPRINTER);
            mmOutputStream.write(msg1.getBytes());
            msg1 = "AI Farm\n";
            mmOutputStream.write(PrinterCommands.ESC_RESETPRINTER);
            mmOutputStream.write(PrinterCommands.bb3);
            mmOutputStream.write(PrinterCommands.ESC_ALIGN_CENTER);
            mmOutputStream.write(msg1.getBytes());
            msg1 = "Kurunegala\n";
            mmOutputStream.write(PrinterCommands.ESC_RESETPRINTER);
            mmOutputStream.write(PrinterCommands.bb);
            mmOutputStream.write(PrinterCommands.ESC_ALIGN_CENTER);
            mmOutputStream.write(msg1.getBytes());
            msg1 = "D.H.Jayasundara\n";
            mmOutputStream.write(msg1.getBytes());
            msg1 = "0766867936\n";
            mmOutputStream.write(msg1.getBytes());
            msg1 = "------------------------------------------------\n";
            mmOutputStream.write(PrinterCommands.ESC_RESETPRINTER);
            mmOutputStream.write(msg1.getBytes());


            msg1 = "Invoice No: ";
            mmOutputStream.write(PrinterCommands.ESC_RESETPRINTER);
            mmOutputStream.write(PrinterCommands.bb);
            mmOutputStream.write(PrinterCommands.ESC_ALIGN_LEFT);
            mmOutputStream.write(msg1.getBytes());
            String msg = invoice.getText().toString();
            mmOutputStream.write(PrinterCommands.ESC_RESETPRINTER);
            mmOutputStream.write(PrinterCommands.ESC_ALIGN_RIGHT);
            mmOutputStream.write(msg.getBytes());

            msg1 = "\nDate: ";
            mmOutputStream.write(PrinterCommands.ESC_RESETPRINTER);
            mmOutputStream.write(PrinterCommands.bb);
            mmOutputStream.write(PrinterCommands.ESC_ALIGN_LEFT);
            mmOutputStream.write(msg1.getBytes());
            msg = date.getText().toString();
            mmOutputStream.write(PrinterCommands.ESC_RESETPRINTER);
            mmOutputStream.write(PrinterCommands.ESC_ALIGN_RIGHT);
            mmOutputStream.write(msg.getBytes());

            msg1 = "\nBill to: ";
            mmOutputStream.write(PrinterCommands.ESC_RESETPRINTER);
            mmOutputStream.write(PrinterCommands.bb);
            mmOutputStream.write(PrinterCommands.ESC_ALIGN_LEFT);
            mmOutputStream.write(msg1.getBytes());
            msg = client.getText().toString();
            mmOutputStream.write(PrinterCommands.ESC_RESETPRINTER);
            mmOutputStream.write(PrinterCommands.ESC_ALIGN_RIGHT);
            mmOutputStream.write(msg.getBytes());
            msg1 = "\n------------------------------------------------\n";
            mmOutputStream.write(PrinterCommands.ESC_RESETPRINTER);
            mmOutputStream.write(msg1.getBytes());

            msg1 = "\n      Product               Qty         Total  \n";
            mmOutputStream.write(PrinterCommands.ESC_RESETPRINTER);
            mmOutputStream.write(PrinterCommands.bb);
            mmOutputStream.write(PrinterCommands.ESC_ALIGN_LEFT);
            mmOutputStream.write(msg1.getBytes());

            msg1 = "Regular Size White Eggs  360*12*15.50   66960  \n";
            mmOutputStream.write(PrinterCommands.ESC_RESETPRINTER);
            mmOutputStream.write(msg1.getBytes());

            msg1 = "Medium Size White Eggs   360*20*12.50   90000 \n";
            mmOutputStream.write(PrinterCommands.ESC_RESETPRINTER);
            mmOutputStream.write(msg1.getBytes());

            msg1 = "Total Amount= Rs:156960  \n";
            mmOutputStream.write(PrinterCommands.ESC_RESETPRINTER);
            mmOutputStream.write(PrinterCommands.bb);
            mmOutputStream.write(PrinterCommands.ESC_ALIGN_RIGHT);
            mmOutputStream.write(msg1.getBytes());

            msg1 = "------------------------------------------------\n";
            mmOutputStream.write(PrinterCommands.ESC_RESETPRINTER);
            mmOutputStream.write(msg1.getBytes());

            msg1 = "****** Thank You ******\n";
            mmOutputStream.write(PrinterCommands.ESC_RESETPRINTER);
            mmOutputStream.write(PrinterCommands.ESC_ALIGN_CENTER);
            mmOutputStream.write(msg1.getBytes());

            msg1 = "------------------------------------------------\n\n\n";
            mmOutputStream.write(PrinterCommands.ESC_RESETPRINTER);
            mmOutputStream.write(msg1.getBytes());





            // the text typed by the user

            msg +=  "\n";








            // tell the user data were sent
            Toast.makeText(getActivity().getApplicationContext(),"Data sent to the printer.",Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    // close the connection to bluetooth printer.
    void closeBT() throws IOException {
        try {
            stopWorker = true;
            mmOutputStream.close();
            mmInputStream.close();
            mmSocket.close();
            Toast.makeText(getActivity().getApplicationContext(),"Bluetooth Printer Closed",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


