package com.jayasundara.aifarm;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ProductsListAdapter extends BaseAdapter{

    private Context mContext;
    private List<Products> mProductList;

    public ProductsListAdapter(Context mContext, List<Products> mProductList) {
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

        View v = View.inflate(mContext,R.layout.item_products_list,null);
        TextView tvProduct = (TextView) v.findViewById(R.id.tv_product);
        TextView tvType = (TextView) v.findViewById(R.id.tv_type);
        TextView tvFixrate = (TextView) v.findViewById(R.id.tv_fixrate);
        TextView tvNote = (TextView) v.findViewById(R.id.tv_note);

        tvProduct.setText(mProductList.get(position).getProduct());
        tvType.setText(String.valueOf("Product Type: " +mProductList.get(position).getType()));
        tvFixrate.setText("Fix Rate: " +mProductList.get(position).getFixrate());
        tvNote.setText("Notes: " +mProductList.get(position).getNote());

        v.setTag(mProductList.get(position).getProduct());
        return v;
    }
}
