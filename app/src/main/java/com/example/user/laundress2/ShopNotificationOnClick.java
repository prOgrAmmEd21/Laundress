package com.example.user.laundress2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

import java.util.HashMap;
import java.util.Map;

public class ShopNotificationOnClick extends AppCompatActivity {
    TextView status, name, extraservice, servicereq, servicetype, weight;
    EditText estdatetime;
    String client_name,notif_message, notifNo;
    Button accept, decline, btnviewdet;
    int trans_no, client_id;
    String shop_name;
    int shop_id, handwasher_lspid;
    private static final String URL_ALL ="http://192.168.137.1/laundress/shop_transactiondetailsnotif.php";
    private static final String URL_ALL_UPDATE ="http://192.168.137.1/laundress/shop_transactionaccept.php";
    private static final String URL_ALL_UPDATE_DECLINE ="http://192.168.137.1/laundress/shop_transactiondescline.php";

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setContentView(R.layout.shop_laundry_details);
        name = findViewById(R.id.name);
        extraservice = findViewById(R.id.extraservice);
        servicereq = findViewById(R.id.servicereq);
        servicetype = findViewById(R.id.servicetype);
        weight = findViewById(R.id.weight);
        estdatetime = findViewById(R.id.estdatetime);
        accept = findViewById(R.id.accept);
        decline = findViewById(R.id.decline);
        btnviewdet = findViewById(R.id.btnviewdet);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        client_name = extras.getString("client_name");
        notif_message = extras.getString("notif_message");
        trans_no = extras.getInt("trans_no");
        client_id = extras.getInt("client_id");
        shop_name = extras.getString("shop_name");
        shop_id = extras.getInt("shop_id");
        name.setText(client_name);
        if(notif_message.equals("Missed")){
            accept.setVisibility(View.GONE);
            decline.setVisibility(View.GONE);
            btnviewdet.setVisibility(View.GONE);
        }
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTransaction();
            }
        });
        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTransactionDecline();
            }
        });
        btnviewdet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = new Bundle();
                extras.putString("shop_name", shop_name);
                extras.putInt("transNo", trans_no);
                extras.putInt("shopID", shop_id);
                extras.putInt("clientID", client_id);
                Intent intent = new Intent(ShopNotificationOnClick.this, ShopViewLaundryDetails.class);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
        allCategory();
    }

    private void updateTransactionDecline() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ALL_UPDATE_DECLINE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if (success.equals("1")){
                                Bundle extras = new Bundle();
                                extras.putString("name",shop_name);
                                extras.putInt("id", shop_id);
                                Intent intent = new Intent(ShopNotificationOnClick.this, ShopHomepage.class);
                                intent.putExtras(extras);
                                startActivity(intent);
                                Toast.makeText(ShopNotificationOnClick.this, "Request Declined", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ShopNotificationOnClick.this, "Failed" +e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ShopNotificationOnClick.this, "Failed. No Connection. " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("transNo", String.valueOf(trans_no));
                params.put("clientID", String.valueOf(client_id));
                params.put("shopID", String.valueOf(shop_id));
                params.put("notifNo", notifNo);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void updateTransaction() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ALL_UPDATE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if (success.equals("1")){
                                Bundle extras = new Bundle();
                                extras.putString("name",shop_name);
                                extras.putInt("id", shop_id);
                                extras.putInt("lspid", handwasher_lspid);
                                Intent intent = new Intent(ShopNotificationOnClick.this, ShopHomepage.class);
                                intent.putExtras(extras);
                                startActivity(intent);
                                Toast.makeText(ShopNotificationOnClick.this, "Request Accepted", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ShopNotificationOnClick.this, "failedddd" +e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ShopNotificationOnClick.this, "Failed. No Connection. " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("transNo", String.valueOf(trans_no));
                params.put("clientID", String.valueOf(client_id));
                params.put("shopID", String.valueOf(shop_id));
                params.put("notifNo", notifNo);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void allCategory() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ALL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("shopNotificationDet");
                            if (success.equals("1")){
                                for (int i =0;i<jsonArray.length();i++)
                                {
                                    String trans_Service=jsonArray.getJSONObject(i).getString("trans_Service").toString();
                                    String trans_ExtService=jsonArray.getJSONObject(i).getString("trans_ExtService").toString();
                                    String trans_ServiceType=jsonArray.getJSONObject(i).getString("trans_ServiceType").toString();
                                    String trans_EstWeight=jsonArray.getJSONObject(i).getString("trans_EstWeight").toString();
                                    String trans_EstDateTime=jsonArray.getJSONObject(i).getString("trans_EstDateTime").toString();
                                    notifNo =jsonArray.getJSONObject(i).getString("notification_No");

                                    extraservice.setText(trans_ExtService);
                                    servicereq.setText(trans_Service);
                                    servicetype.setText(trans_ServiceType);
                                    weight.setText(trans_EstWeight);
                                    estdatetime.setText(trans_EstDateTime);
                                    estdatetime.setEnabled(false);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ShopNotificationOnClick.this, "failed here" +e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ShopNotificationOnClick.this, "Failed. No Connection. " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("transNo", String.valueOf(trans_no));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}