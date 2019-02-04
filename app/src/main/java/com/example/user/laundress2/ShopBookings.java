package com.example.user.laundress2;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
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

public class ShopBookings extends Fragment {
    ArrayList<String> arrID = new ArrayList<>();
    ArrayList<String> arrName = new ArrayList<>();
    ArrayList<String> arrReqService = new ArrayList<>();
    ArrayList<String> arrExtService = new ArrayList<>();
    ArrayList<String> arrServiceType = new ArrayList<>();
    ArrayList<String> arrWeight = new ArrayList<>();
    ArrayList<String> arrDateTime = new ArrayList<>();
    ArrayList<String> arrStatus = new ArrayList<>();
    ArrayList<String> arrTransNo = new ArrayList<>();
    private Context context;
    ListView listView;
    private static final String URL_ALL = "http://192.168.124.83/laundress/shop_bookings.php";
    ArrayList<ShopBookingsList> shopBookingsLists = new ArrayList<>();
    ShopBookingsAdapter shopBookingsAdapter;
    private RequestQueue requestQueue;
    String shop_name;
    int shop_id;
    private String booking;
    private int client_id;
    private int transNo;

    public static ShopBookings newInstance(int shop_id, String shop_name) {
        ShopBookings shopBookings = new ShopBookings();
        Bundle args = new Bundle();
        args.putInt("shop_id", shop_id);
        args.putString("shop_name", shop_name);
        shopBookings.setArguments(args);

        return shopBookings;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.shop_booking, container, false);
        listView = rootView.findViewById(R.id.lv_booking);
        shopBookingsAdapter = new ShopBookingsAdapter(context,shopBookingsLists);
        listView.setAdapter(shopBookingsAdapter);
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();

        requestQueue = Volley.newRequestQueue(getContext());
        shop_id = getArguments().getInt("shop_id");
        shop_name = getArguments().getString("shop_name");
        client_id = getArguments().getInt("client_id");
        booking = getArguments().getString("booking");
        Toast.makeText(getActivity(), "id " +shop_id, Toast.LENGTH_SHORT).show();
        Toast.makeText(getActivity(), "name " +shop_name, Toast.LENGTH_SHORT).show();
        Toast.makeText(getActivity(), "client_id  " +client_id, Toast.LENGTH_SHORT).show();
        Toast.makeText(getActivity(), "booking  " +booking, Toast.LENGTH_SHORT).show();
        bookings(shop_id, shop_name);
        if(booking=="confirm")
        {
            confirm(shop_id, client_id);
        }

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
                            Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
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
            JSONArray jsonArray = jsonObject.getJSONArray("shopBookings");
            if (success.equals("1")){
                for (int i =0;i<jsonArray.length();i++)
                {
                    String id = jsonArray.getJSONObject(i).getString("clientID");
                    String name = jsonArray.getJSONObject(i).getString("clientName");
                    String transServ = jsonArray.getJSONObject(i).getString("transService");
                    String transExt = jsonArray.getJSONObject(i).getString("transExtra");
                    String transServType = jsonArray.getJSONObject(i).getString("transServType");
                    String transWeight = jsonArray.getJSONObject(i).getString("transWeight");
                    String transDateTime = jsonArray.getJSONObject(i).getString("transDateTime");
                    String transStat = jsonArray.getJSONObject(i).getString("transStat");
                    String transNo = jsonArray.getJSONObject(i).getString("transNo");
                    arrID.add(id);
                    arrName.add(name);
                    arrReqService.add(transServ);
                    arrExtService.add(transExt);
                    arrServiceType.add(transServType);
                    arrWeight.add(transWeight);
                    arrDateTime.add(transDateTime);
                    arrStatus.add(transStat);
                    arrTransNo.add(transNo);
                    ShopBookingsList shopBookingsList = new ShopBookingsList();
                    shopBookingsList.setTransNo(transNo);
                    shopBookingsList.setId(id);
                    shopBookingsList.setName(name);
                    shopBookingsList.setTransServ1(transServ);
                    shopBookingsList.setTransServ2(transServ);
                    shopBookingsList.setTransServ3(transServ);
                    shopBookingsList.setTransExtra1(transExt);
                    shopBookingsList.setTransExtra2(transExt);
                    shopBookingsList.setTransExtra3(transExt);
                    shopBookingsList.setTransServType(transServType);
                    shopBookingsList.setTransWeight(transWeight);
                    shopBookingsList.setTransDateTime(transDateTime);
                    shopBookingsList.setTransStat(transStat);
                    shopBookingsLists.add(shopBookingsList);
                }
                shopBookingsAdapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "failedddd" +e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
    private void bookings(final int shop_id, final String shop_name){

        //final Context context = this;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ALL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("shopBookings");
                            if (success.equals("1")){
                                for (int i =0;i<jsonArray.length();i++)
                                {
                                    String id = jsonArray.getJSONObject(i).getString("clientID");
                                    String name = jsonArray.getJSONObject(i).getString("clientName");
                                    String transServ = jsonArray.getJSONObject(i).getString("transService");
                                    String transExt = jsonArray.getJSONObject(i).getString("transExtra");
                                    String transServType = jsonArray.getJSONObject(i).getString("transServType");
                                    String transWeight = jsonArray.getJSONObject(i).getString("transWeight");
                                    String transDateTime = jsonArray.getJSONObject(i).getString("transDateTime");
                                    String transStat = jsonArray.getJSONObject(i).getString("transStat");
                                    String transNo = jsonArray.getJSONObject(i).getString("transNo");
                                    arrID.add(id);
                                    arrName.add(name);
                                    arrReqService.add(transServ);
                                    arrExtService.add(transExt);
                                    arrServiceType.add(transServType);
                                    arrWeight.add(transWeight);
                                    arrDateTime.add(transDateTime);
                                    arrStatus.add(transStat);
                                    arrTransNo.add(transNo);
                                    ShopBookingsList shopBookingsList = new ShopBookingsList();
                                    shopBookingsList.setTransNo(transNo);
                                    shopBookingsList.setId(id);
                                    shopBookingsList.setName(name);
                                    shopBookingsList.setTransServ1(transServ);
                                    shopBookingsList.setTransServ2(transServ);
                                    shopBookingsList.setTransServ3(transServ);
                                    shopBookingsList.setTransExtra1(transExt);
                                    shopBookingsList.setTransExtra2(transExt);
                                    shopBookingsList.setTransExtra3(transExt);
                                    shopBookingsList.setTransServType(transServType);
                                    shopBookingsList.setTransWeight(transWeight);
                                    shopBookingsList.setTransDateTime(transDateTime);
                                    shopBookingsList.setTransStat(transStat);
                                    shopBookingsLists.add(shopBookingsList);
                                }
                                shopBookingsAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "failedd" +e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("shop_id", String.valueOf(shop_id));
                //first 'email' nga kay mao pangan para kuha nimo para sa php
                params.put("shop_name", shop_name);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void confirm(final int shop_id, final int client_id){

        //final Context context = this;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ALL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("shopMyLaundry");
                            if (success.equals("1")){
                                for (int i =0;i<jsonArray.length();i++)
                                {
                                    String id = jsonArray.getJSONObject(i).getString("clientID");
                                    String name = jsonArray.getJSONObject(i).getString("clientName");
                                    String transServ = jsonArray.getJSONObject(i).getString("transService");
                                    String transExt = jsonArray.getJSONObject(i).getString("transExtra");
                                    String transServType = jsonArray.getJSONObject(i).getString("transServType");
                                    String transWeight = jsonArray.getJSONObject(i).getString("transWeight");
                                    String transDateTime = jsonArray.getJSONObject(i).getString("transDateTime");
                                    String transStat = jsonArray.getJSONObject(i).getString("transStat");
                                    String transNo = jsonArray.getJSONObject(i).getString("transNo");
                                    arrID.add(id);
                                    arrName.add(name);
                                    arrReqService.add(transServ);
                                    arrExtService.add(transExt);
                                    arrServiceType.add(transServType);
                                    arrWeight.add(transWeight);
                                    arrDateTime.add(transDateTime);
                                    arrStatus.add(transStat);
                                    arrTransNo.add(transNo);
                                    ShopBookingsList shopBookingsList = new ShopBookingsList();
                                    shopBookingsList.setTransNo(transNo);
                                    shopBookingsList.setId(id);
                                    shopBookingsList.setName(name);
                                    shopBookingsList.setTransServ1(transServ);
                                    shopBookingsList.setTransServ2(transServ);
                                    shopBookingsList.setTransServ3(transServ);
                                    shopBookingsList.setTransExtra1(transExt);
                                    shopBookingsList.setTransExtra2(transExt);
                                    shopBookingsList.setTransExtra3(transExt);
                                    shopBookingsList.setTransServType(transServType);
                                    shopBookingsList.setTransWeight(transWeight);
                                    shopBookingsList.setTransDateTime(transDateTime);
                                    shopBookingsList.setTransStat(transStat);
                                    shopBookingsLists.add(shopBookingsList);
                                }
                                shopBookingsAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "failedd" +e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("shop_id", String.valueOf(shop_id));
                params.put("client_id", String.valueOf(client_id));
                params.put("transNo", String.valueOf(transNo));
                //first 'email' nga kay mao pangan para kuha nimo para sa php
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }
}
