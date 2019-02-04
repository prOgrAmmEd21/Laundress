package com.example.user.laundress2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

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

public class ShopRate extends AppCompatActivity {
    ArrayList<String> arrClientName = new ArrayList<>();
    ArrayList<String> arrRating = new ArrayList<>();
    ArrayList<String> arrComment = new ArrayList<>();
    ArrayList<String> arrDate = new ArrayList<>();
    private Context context;
    ListView listView;
    private static final String URL_ALL = "http://192.168.124.83/laundress/shop_ratings.php";
    ArrayList<ShopRateList> shopRateLists = new ArrayList<>();
    ShopRateAdapter shopRateAdapter;
    private RequestQueue requestQueue;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.shop_rate, container, false);
        listView = rootView.findViewById(R.id.lv_rate);
        shopRateAdapter = new ShopRateAdapter(context,shopRateLists);
        listView.setAdapter(shopRateAdapter);
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = ShopRate.this;

        requestQueue = Volley.newRequestQueue(ShopRate.this);

        try {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ALL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            getJsonResponse(response);
                            System.out.println("RESPONSEesponse");
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(ShopRate.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    });

            requestQueue.add(stringRequest);
        }
        catch (Exception e)
        {

        }
    }

    private void getJsonResponse(String response)
    {
        try {
            JSONObject jsonObject = new JSONObject(response);
            String success = jsonObject.getString("success");
            //JSONArray jArray = json.getJSONArray("platform");
            //JSONArray jsonArray = new JSONArray(response);
            JSONArray jsonArray = jsonObject.getJSONArray("shopRate");
            if (success.equals("1")){
                for (int i =0;i<jsonArray.length();i++)
                {
                    String clientName = jsonArray.getJSONObject(i).getString("clientName");
                    String rating = jsonArray.getJSONObject(i).getString("rating");
                    String comment = jsonArray.getJSONObject(i).getString("comment");
                    String date = jsonArray.getJSONObject(i).getString("date");
                    arrClientName.add(clientName);
                    arrRating.add(rating);
                    arrComment.add(comment);
                    arrDate.add(date);
                    ShopRateList shopRateList = new ShopRateList();
                    shopRateList.setClientName(clientName);
                    shopRateList.setRating(rating);
                    shopRateList.setComment(comment);
                    shopRateList.setDate(date);
                    shopRateLists.add(shopRateList);
                }
                shopRateAdapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(ShopRate.this, "failedddd" +e.toString(), Toast.LENGTH_SHORT).show();
        }

    }
}
