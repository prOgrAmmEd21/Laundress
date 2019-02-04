package com.example.user.laundress2;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RegisterLhw extends AppCompatActivity {
    private EditText fname, midname, lname, addr, bdate, phonenumber, username, password;
    private RadioGroup radioGender;
    private Spinner civilstat;
    private String genders;
    private String cvlstat;
    private Button register;
    private DatePickerDialog picker;
    private ProgressBar load;
    private static String URL_REGISTER = "http://192.168.137.1/laundress/lhwregister.php";
    //private static String URL_REGISTER = "http://192.168.1.5/laundress/lhwregister.php";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lhwregistration);
        fname = findViewById(R.id.fname);
        midname = findViewById(R.id.midname);
        lname = findViewById(R.id.lname);
        addr = findViewById(R.id.address);
        bdate = findViewById(R.id.bdate);
        phonenumber = findViewById(R.id.phonenumber);
        username = findViewById(R.id.email);
        password = findViewById(R.id.password);
        civilstat = findViewById(R.id.civilstatus);
        radioGender = findViewById(R.id.radioGender);
        register = findViewById(R.id.register);
        load = findViewById(R.id.loading);
        civilstat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cvlstat = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        bdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(RegisterLhw.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                bdate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });
    }
    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioMale:
                if(checked)
                    genders = "M";
                break;
            case R.id.radioFemale:
                if(checked)
                    genders = "F";
                break;
        }
    }
    private void Register(){

        load.setVisibility(View.VISIBLE);
        register.setVisibility(View.GONE);
        final Context context = this;
        final String fname = this.fname.getText().toString().trim();
        final String midname = this.midname.getText().toString().trim();
        final String lname = this.lname.getText().toString().trim();
        final String addr = this.addr.getText().toString().trim();
        final String bdate = this.bdate.getText().toString().trim();
        final String phonenumber = this.phonenumber.getText().toString().trim();
        final String gender = this.genders.trim();
        final String cvlstat = this.cvlstat.trim();
        final String username = this.username.getText().toString().trim();
        final String password = this.password.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if(success.equals("1")){
                                Toast.makeText(RegisterLhw.this, "Registered Successfully ", Toast.LENGTH_SHORT).show();
                                load.setVisibility(View.GONE);
                                register.setVisibility(View.VISIBLE);
                                Intent intent = new Intent(context, Login.class);
                                startActivity(intent);
                            }
                        } catch (JSONException e){
                            e.printStackTrace();;
                            Toast.makeText(RegisterLhw.this, "Register Failed " + e.toString(), Toast.LENGTH_SHORT).show();
                            load.setVisibility(View.GONE);
                            register.setVisibility(View.VISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegisterLhw.this, "Register Failed " + error.toString(), Toast.LENGTH_SHORT).show();
                        load.setVisibility(View.GONE);
                        register.setVisibility(View.VISIBLE);
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("fname", fname);
                params.put("midname", midname);
                params.put("lname", lname);
                params.put("addr", addr);
                params.put("bdate", bdate);
                params.put("gender", gender);
                params.put("cvlstat", cvlstat);
                params.put("phonenumber", phonenumber);
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
