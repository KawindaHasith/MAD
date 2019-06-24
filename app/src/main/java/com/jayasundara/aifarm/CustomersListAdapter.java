package com.jayasundara.aifarm;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomersListAdapter extends BaseAdapter{

    private Context mContext;
    private List<Customers> mProductList;

    public CustomersListAdapter(Context mContext, List<Customers> mProductList) {
        this.mContext = mContext;
        this.mProductList = mProductList;
    }


    @Override
    public int getCount() {
        return mProductList.size();
    }

    @Override
    public Object getItem(int position) {
        return mProductList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(mContext,R.layout.item_customers_list,null);
        TextView tvCustomer = (TextView) v.findViewById(R.id.tv_customer);
        TextView tvAddress = (TextView) v.findViewById(R.id.tv_address);
        TextView tvContactNo = (TextView) v.findViewById(R.id.tv_contactNo);
        TextView tvEmail = (TextView) v.findViewById(R.id.tv_email);

        tvCustomer.setText(mProductList.get(position).getCustomer());
        tvAddress.setText(String.valueOf(mProductList.get(position).getAddress()));
        tvContactNo.setText("Contact No: " +mProductList.get(position).getContactNo());
        tvEmail.setText("E-mail: " +mProductList.get(position).getEmail());

        v.setTag(mProductList.get(position).getCustomer());
        return v;
    }
}
