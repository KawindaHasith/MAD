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

public class AddcustomerFragment extends Fragment {

    EditText customer,address,contactNo,email;
    Button addCustomer;
    DatabaseHelper myDB;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_addcustomer,container,false);

        customer = (EditText) v.findViewById(R.id.customer);
        address = (EditText) v.findViewById(R.id.address);
        contactNo = (EditText) v.findViewById(R.id.contactNo);
        email = (EditText) v.findViewById(R.id.email);
        addCustomer = (Button) v.findViewById(R.id.addCustomer);

        myDB =new DatabaseHelper(getActivity());

        //Add button
        addCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

             String fCustomer = customer.getText().toString();
             String fAddress = address.getText().toString();
             String fContactNo = contactNo.getText().toString();
             String fEmail = email.getText().toString();


             //Validations

                //Check valid contact number
                if((fContactNo.length()!=10) || (fContactNo.charAt(0)!='0'))
                    Toast.makeText(getActivity().getApplicationContext(),"Enter valid phone number",Toast.LENGTH_SHORT).show();

                //Check valid email
                String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
                java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
                java.util.regex.Matcher m = p.matcher(fEmail);

                if(!m.matches())
                    Toast.makeText(getActivity().getApplicationContext(),"Enter valid e-mail address",Toast.LENGTH_SHORT).show();

                //check empty fields
             if(fCustomer.length() != 0 && fAddress.length() != 0 && fContactNo.length() != 0 && fEmail.length() != 0){

                 AddData(fCustomer,fAddress,fContactNo,fEmail);
                 customer.setText("");
                 address.setText("");
                 contactNo.setText("");
                 email.setText("");
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
