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

public class ShopBookingsList{
    private String name;
    private String transServ1;
    private String transServ2;
    private String transServ3;
    private String transExtra1;
    private String transExtra2;
    private String transExtra3;
    private String transServType;
    private String transWeight;
    private String transDateTime;
    private String transStat;
    private String id;
    private String transNo;

    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTransServ1() {
        return transServ1;
    }

    public void setTransServ1(String transServ1) {
        this.transServ1 = transServ1;
    }

    public String getTransServ2() {
        return transServ2;
    }

    public void setTransServ2(String transServ2) {
        this.transServ2 = transServ2;
    }

    public String getTransServ3() {
        return transServ3;
    }

    public void setTransServ3(String transServ3) {
        this.transServ3 = transServ3;
    }

    public String getTransExtra1() {
        return transExtra1;
    }

    public void setTransExtra1(String transExtra1) {
        this.transExtra1 = transExtra1;
    }

    public String getTransExtra2() {
        return transExtra2;
    }

    public void setTransExtra2(String transExtra2) {
        this.transExtra2 = transExtra2;
    }

    public String getTransExtra3() {
        return transExtra3;
    }

    public void setTransExtra3(String transExtra3) {
        this.transExtra3 = transExtra3;
    }

    public String getTransServType() {
        return transServType;
    }

    public void setTransServType(String transServType) {
        this.transServType = transServType;
    }

    public String getTransWeight() {
        return transWeight;
    }

    public void setTransWeight(String transWeight) {
        this.transWeight = transWeight;
    }

    public String getTransDateTime() {
        return transDateTime;
    }

    public void setTransDateTime(String transDateTime) {
        this.transDateTime = transDateTime;
    }

    public String getTransStat() {
        return transStat;
    }

    public void setTransStat(String transStat) {
        this.transStat = transStat;
    }
}