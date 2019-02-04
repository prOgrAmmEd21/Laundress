package com.example.user.laundress2;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ShopRateAdapter extends BaseAdapter {
    Context context;
    ItemHolder itemHolder;
    ArrayList<ShopRateList> shopRateLists;

    public ShopRateAdapter(Context context, ArrayList<ShopRateList> shopRateLists) {
        this.context = context;
        this.shopRateLists = shopRateLists;
    }
    @Override
    public int getCount() {
        return shopRateLists.size();
    }

    @Override
    public Object getItem(int position) {
        return shopRateLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
            itemHolder = new ItemHolder();
            convertView = layoutInflater.inflate(R.layout.shop_rate_adapter, parent, false);
            itemHolder.clientName = (TextView) convertView.findViewById(R.id.tv_clientName);
            itemHolder.rating = (TextView) convertView.findViewById(R.id.tv_rating);
            itemHolder.comment = (TextView) convertView.findViewById(R.id.tv_comment);
            itemHolder.comment = (TextView) convertView.findViewById(R.id.tv_date);
            itemHolder.clientName.setText(shopRateLists.get(position).getClientName());
            itemHolder.rating.setText(shopRateLists.get(position).getRating());
            itemHolder.comment.setText(shopRateLists.get(position).getComment());
            itemHolder.date.setText(shopRateLists.get(position).getDate());
        }

        return convertView;
    }

    private class ItemHolder{
        TextView clientName;
        TextView rating;
        TextView comment;
        TextView date;
    }
}
