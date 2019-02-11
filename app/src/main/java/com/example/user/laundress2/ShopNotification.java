package com.example.user.laundress2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShopNotification extends AppCompatActivity {
    ArrayList<ShopNotificationList> shopNotificationLists = new ArrayList<>();
    ShopNotifAdpater shopNotifAdpater;
    private static final String URL_ALL ="http://192.168.137.1/laundress/shop_notification.php";
    private static String URL_ADDPOST = "http://192.168.137.1/laundress/addratehandwasher.php";
    ListView lvnotif;
    String client_name, name;
    String notification_Message;
    float rating;
    String rating_Date, rating_Comment, comments;
    float rating_Score;
    int trans_No, transno, rate_No;
    int lsp_ID;
    int client_id, handwasher_lspid;
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void onBackPressed() {

        // Write your code here

        super.onBackPressed();
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_notification);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        lvnotif=findViewById(R.id.lv_notification);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        client_name = extras.getString("client_name");
        client_id = extras.getInt("client_id");
        allCategory();
        /*if(notification_Message.equals("Finished")){
            lvnotif.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(ClientNotification.this, "sud " +position, Toast.LENGTH_SHORT).show();
                }
            });
        }*/
    }

    private void allCategory() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ALL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("allbooking");
                            if (success.equals("1")){
                                for (int i =0;i<jsonArray.length();i++)
                                {
                                    notification_Message=jsonArray.getJSONObject(i).getString("notification_Message").toString();
                                    lsp_ID= Integer.parseInt(jsonArray.getJSONObject(i).getString("lsp_ID").toString());
                                    int client_ID= Integer.parseInt(jsonArray.getJSONObject(i).getString("client_ID").toString());
                                    String table = jsonArray.getJSONObject(i).getString("fromtable");
                                    if(notification_Message.equals("Approved") || notification_Message.equals("Declined")|| notification_Message.equals("Finished")) {
                                        trans_No= Integer.parseInt(jsonArray.getJSONObject(i).getString("trans_No").toString());
                                        name = jsonArray.getJSONObject(i).getString("name");
                                        final ShopNotificationList shopNotificationList = new ShopNotificationList();
                                        shopNotificationList.setClientID(client_ID);
                                        shopNotificationList.setTransNo(trans_No);
                                        if(notification_Message.equals("Finished")){
                                            rating_Score = Float.parseFloat(jsonArray.getJSONObject(i).getString("rating_Score"));
                                            rating_Comment = jsonArray.getJSONObject(i).getString("rating_Comment");
                                            rating_Date = jsonArray.getJSONObject(i).getString("rating_Date");
                                            rate_No = Integer.parseInt(jsonArray.getJSONObject(i).getString("rating_No"));

                                            shopNotificationList.setRate(rating_Score);
                                            shopNotificationList.setComment(rating_Comment);
                                            shopNotificationList.setDateRate(rating_Date);
                                            shopNotificationList.setRateNo(rate_No);
                                            lvnotif.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                @Override
                                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                    if(shopNotificationLists.get(position).getMessage().equals("Finished")){
                                                        transno = shopNotificationLists.get(position).getTransNo();
                                                        showChangeLangDialog();
                                                    }
                                                    // Toast.makeText(ClientNotification.this, "sud " +position, Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }

                                        shopNotificationList.setMessage(notification_Message);
                                        shopNotificationList.setClientName(name);
                                        shopNotificationList.setTable(table);
                                        shopNotificationLists.add(shopNotificationList);
                                    }
                                }
                                //if(notification_Message.equals("Approved") || notification_Message.equals("Declined") || notification_Message.equals("Finished")){
                                shopNotifAdpater = new ShopNotifAdpater(ShopNotification.this,shopNotificationLists);
                                lvnotif.setAdapter(shopNotifAdpater);
                                // }
                                shopNotifAdpater.notifyDataSetChanged();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ShopNotification.this, "failedddd" +e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ShopNotification.this, "Failed. No Connection. " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("client_id", String.valueOf(client_id));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public void showChangeLangDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ShopNotification.this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.shop_rateclient, null);
        final RatingBar rate = dialogView.findViewById(R.id.ratings);
        final EditText comment = dialogView.findViewById(R.id.comment);
        rate.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rate.setRating(rating);
            }
        });
        dialogBuilder.setView(dialogView);
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                rating = rate.getRating();
                comments = comment.getText().toString().trim();
                addRating(client_id, lsp_ID, trans_No, rating, comments);
                // Toast.makeText(getActivity(), "rating " + rating+" comments "+comments+ " client_ID "+client_ID+ " handwasher_lspid "+handwasher_lspid + " trans_No "+trans_No , Toast.LENGTH_LONG).show();
/*                String updatemessage = message.getText().toString().trim();
                String location = showlocation.toString().trim();
                int post_no = clientPostLists.get(pos).getPost_no();
                updatePost(updatemessage, location, post_no);
                ClientMyPost.this.recreate();*/
            }
        });
        dialogBuilder.setNegativeButton("Skip", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    private void addRating(final int client_ID, final int handwasher_lspid, final int trans_No, final float rating, final String comments) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ADDPOST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if(success.equals("1")){
                                //getActivity().recreate();
                                Toast.makeText(ShopNotification.this, "Your rate has been sent", Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e){
                            e.printStackTrace();;
                            Toast.makeText(ShopNotification.this, "Rate Failed " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ShopNotification.this, "Rate Failed. No connection." +error.toString(), Toast.LENGTH_SHORT).show();
                       /* load.setVisibility(View.GONE);
                        login.setVisibility(View.VISIBLE);*/
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("client_ID", String.valueOf(ShopNotification.this.client_id));
                params.put("handwasher_lspid", String.valueOf(ShopNotification.this.lsp_ID));
                params.put("trans_No", String.valueOf(ShopNotification.this.transno));
                params.put("rating", String.valueOf(ShopNotification.this.rating));
                params.put("comments", ShopNotification.this.comments);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(ShopNotification.this);
        requestQueue.add(stringRequest);
    }
}
