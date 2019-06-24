package com.jayasundara.aifarm;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ProductListAdapter extends BaseAdapter{

    private Context mContext;
    private List<Product> mProductList;

    public ProductListAdapter(Context mContext, List<Product> mProductList) {
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

        View v = View.inflate(mContext,R.layout.item_product_list,null);
        TextView tvName = (TextView) v.findViewById(R.id.tv_name);
        TextView tvQty = (TextView) v.findViewById(R.id.tv_qty);
        TextView tvTotal = (TextView) v.findViewById(R.id.tv_total);

        tvName.setText(mProductList.get(position).getName());
        tvQty.setText(String.valueOf(mProductList.get(position).getQty()));
        tvTotal.setText("Rs: " +mProductList.get(position).getTotal());

        v.setTag(mProductList.get(position).getId());
        return v;
    }
}
