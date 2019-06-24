package com.jayasundara.aifarm;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddproductFragment extends Fragment {

    EditText product,product_type,fixrate,note;
    Button addProduct;
    DatabaseHelper2 myDB;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_addproduct,container,false);

        product = (EditText) v.findViewById(R.id.product);
        product_type = (EditText) v.findViewById(R.id.product_type);
        fixrate = (EditText) v.findViewById(R.id.fix_rate);
        note = (EditText) v.findViewById(R.id.note);
        addProduct = (Button) v.findViewById(R.id.addProduct);

        myDB =new DatabaseHelper2(getActivity());

        //Add button
        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String fProduct = product.getText().toString();
                String fProduct_type = product_type.getText().toString();
                String fFixrate = fixrate.getText().toString();
                String fNote = note.getText().toString();


                //Validations

                //check empty fields
                if(fProduct.length() != 0 && fProduct_type.length() != 0 && fFixrate.length() != 0 && fNote.length() != 0){

                    AddData(fProduct,fProduct_type,fFixrate,fNote);
                    product.setText("");
                    product_type.setText("");
                    fixrate.setText("");
                    note.setText("");
                }
                else{

                    Toast.makeText(getActivity().getApplicationContext(),"You must fill all fields",Toast.LENGTH_SHORT).show();
                }
            }
        });


        return  v;
    }

    public void AddData(String cus,String addrrs,String contct,String eml){

        boolean insertData = myDB.addData(cus,addrrs,contct,eml);

        if(insertData == true)
            Toast.makeText(getActivity().getApplicationContext(),"Data successfully added to the database",Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getActivity().getApplicationContext(),"Data insert failed",Toast.LENGTH_SHORT).show();

    }
}
