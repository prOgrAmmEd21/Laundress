package com.example.user.laundress2;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class ShopNotifAdpater extends BaseAdapter {
    Context context;
    ShopNotifAdpater.ItemHolder itemHolder;
    ArrayList<ShopNotificationList> shopNotificationLists;

    public ShopNotifAdpater(Context context, ArrayList<ShopNotificationList> shopNotificationLists) {
        this.context = context;
        this.shopNotificationLists = shopNotificationLists;
    }

    @Override
    public int getCount() {
        return shopNotificationLists.size();
    }

    @Override
    public Object getItem(int position) {
        return shopNotificationLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
        itemHolder = new ShopNotifAdpater.ItemHolder();
        convertView = layoutInflater.inflate(R.layout.shop_notification_adapter, parent, false);
        itemHolder.name = (TextView) convertView.findViewById(R.id.name);
        itemHolder.status = (TextView) convertView.findViewById(R.id.status);
        itemHolder.ratings = convertView.findViewById(R.id.ratings);
        String message = shopNotificationLists.get(position).getMessage();
        if(message.equals("Approved")){
            itemHolder.name.setText(shopNotificationLists.get(position).getClientName());
            itemHolder.status.setText("Approved your service. Please wait for laundry Details confirmation.");
        }else if(message.equals("Declined")){
            itemHolder.name.setText(shopNotificationLists.get(position).getClientName());
            itemHolder.status.setText("Your Request has been declined");
        }else if(message.equals("Finished")){
            itemHolder.name.setText(shopNotificationLists.get(position).getClientName());
            itemHolder.status.setText("Laundry Service is finished. \n" +shopNotificationLists.get(position).getClientName()+" has rated you");
            itemHolder.ratings.setVisibility(View.VISIBLE);
            itemHolder.ratings.setRating(shopNotificationLists.get(position).getRate());
        }

        return convertView;
    }

    private class ItemHolder {
        TextView name, status;
        RatingBar ratings;
    }
}
