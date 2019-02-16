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

public class ShopNotifAdapter extends BaseAdapter {
    Context context;
    ShopNotifAdapter.ItemHolder itemHolder;
    ArrayList<ShopNotificationList> shopNotificationLists;

    public ShopNotifAdapter(Context context, ArrayList<ShopNotificationList> shopNotificationLists) {
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
        itemHolder = new ShopNotifAdapter.ItemHolder();
        convertView = layoutInflater.inflate(R.layout.shop_notification_adapter, parent, false);
        itemHolder.name = (TextView) convertView.findViewById(R.id.name);
        itemHolder.status = (TextView) convertView.findViewById(R.id.status);
        itemHolder.ratingCustService = convertView.findViewById(R.id.rateCustService);
        itemHolder.ratingQualityService = convertView.findViewById(R.id.rateQualService);
        itemHolder.ratingOntime = convertView.findViewById(R.id.rateOntime);
        itemHolder.ratingOverall = convertView.findViewById(R.id.rateOverall);
        //final ClientPostList clientPostList=clientPostLists.get(position);
        //}
        itemHolder.name.setText(shopNotificationLists.get(position).getClientName());
        // itemHolder.status.setText(handwasherNotifLists.get(position).getNotification_message());
        String message = shopNotificationLists.get(position).getMessage();
        if(message.equals("Pending")){
            itemHolder.status.setText("Requested your service.");
        }else if(message.equals("Missed")){
            itemHolder.status.setText("You Missed the Service.");
        }else if(message.equals("Finished")){
            itemHolder.name.setText(shopNotificationLists.get(position).getClientName());
            itemHolder.status.setText(shopNotificationLists.get(position).getClientName()+" has rated you");
            itemHolder.ratingCustService.setVisibility(View.VISIBLE);
            itemHolder.ratingCustService.setRating(shopNotificationLists.get(position).getRatingCustService());
            itemHolder.ratingQualityService.setVisibility(View.VISIBLE);
            itemHolder.ratingQualityService.setRating(shopNotificationLists.get(position).getRatingQualityService());
            itemHolder.ratingOntime.setVisibility(View.VISIBLE);
            itemHolder.ratingOntime.setRating(shopNotificationLists.get(position).getRatingOntime());
            itemHolder.ratingOverall.setVisibility(View.VISIBLE);
            itemHolder.ratingOverall.setRating(shopNotificationLists.get(position).getRatingOverall());
        }


        return convertView;
    }

    private class ItemHolder {
        TextView name, status;
        RatingBar ratingCustService;
        RatingBar ratingQualityService;
        RatingBar ratingOntime;
        RatingBar ratingOverall;
    }
}