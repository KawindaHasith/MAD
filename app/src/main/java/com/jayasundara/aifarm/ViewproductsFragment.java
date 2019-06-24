package com.jayasundara.aifarm;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ViewproductsFragment extends Fragment {

    private ListView lvProduct;
    private ProductsListAdapter adapter;
    private List<Products> mProductList;
    DatabaseHelper2 myDB;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_viewproducts,container,false);

        lvProduct = (ListView) v.findViewById(R.id.listview_products);
        mProductList = new ArrayList<>();

        myDB = new DatabaseHelper2(getActivity());
        Cursor data = myDB.getListContents();
        int numRows = data.getCount();

        if(numRows == 0)
            Toast.makeText(getActivity().getApplicationContext(),"No any data to display",Toast.LENGTH_SHORT).show();
        else{

            while (data.moveToNext()){

                mProductList.add(new Products(data.getString(0),data.getString(1),data.getString(2),data.getString(3)));
                adapter = new ProductsListAdapter(getActivity().getApplicationContext(),mProductList);
                lvProduct.setAdapter(adapter);
            }
        }

        /*lvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


            }
        });*/

        return  v;
    }
}