package com.example.nativeloginpage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apmem.tools.layouts.FlowLayout;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class GetPassionsEdit extends AppCompatActivity {
    Button btnBack;
    TextView txtSelectedCount, btnEditSkip;
    private final ArrayList<String> passions = new ArrayList<>();
    private ArrayList<String> selectedPassions = new ArrayList<>();
    FlowLayout loutEditPassions;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_passions_edit);
        Objects.requireNonNull(getSupportActionBar()).hide();

        getPassionsList(getApplicationContext());

        btnBack = findViewById(R.id.btnEditProfileBack);
        loutEditPassions = findViewById(R.id.loutEditPassions);
        txtSelectedCount = findViewById(R.id.txtSelectedCount);
        btnEditSkip = findViewById(R.id.btnEditSkip);

//        passions.add("Netflix");
//        passions.add("Coffee");
//        passions.add("Climbing");
//        passions.add("Gym");
//        passions.add("Fitness");
//        passions.add("Sports");
//        passions.add("Hill Climbing");

        try{
            selectedPassions = getIntent().getStringArrayListExtra("myPassionsList");
            txtSelectedCount.setText("("+ selectedPassions.size()+"/5)");
        }catch (Exception e){
            Toast.makeText(GetPassionsEdit.this, "Error loading:"+ e, Toast.LENGTH_LONG).show();
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedPassions.size()>2){
                    updateProfileData();
                    Toast.makeText(GetPassionsEdit.this, "Updating Passions", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(GetPassionsEdit.this, EditProfile.class));
                    finish();
                } else {
                    alertCreate();
                }
            }
        });

        btnEditSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setPassions(){
        for(int i =0; i<passions.size(); i++) {
            android.view.View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.passions_set_view, loutEditPassions, false);
            ToggleButton btnPassions = view.findViewById(R.id.btnPassion);
            btnPassions.setText(passions.get(i));
            btnPassions.setTextOn(passions.get(i));
            btnPassions.setTextOff(passions.get(i));
            loutEditPassions.addView(view);

            try {
                for(int j = 0; j < selectedPassions.size(); j++){
                    if(btnPassions.getText().equals(selectedPassions.get(j))){
                        btnPassions.setTag(btnPassions.getId(), btnPassions.getText());
                        btnPassions.setChecked(true);
                    }
                }
            } catch (Exception e){
                Toast.makeText(GetPassionsEdit.this, "Error set passions:"+ e, Toast.LENGTH_LONG).show();
            }



            btnPassions.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onClick(View v) {
                    if(selectedPassions.size() == 5){
                        if(btnPassions.getTag(btnPassions.getId())==null){
                            btnPassions.setChecked(false);
                        } else {
                            for(int i=0; i<selectedPassions.size(); i++){
                                if(selectedPassions.get(i).equals(String.valueOf(btnPassions.getTag(btnPassions.getId())))){
                                    selectedPassions.remove(i);
                                    btnPassions.setTag(btnPassions.getId(), null);
                                }
                            }
                        }
                    } else {
                        if(btnPassions.getTag(btnPassions.getId())==null) {
                            selectedPassions.add(String.valueOf(btnPassions.getText()));
                            btnPassions.setTag(btnPassions.getId(), btnPassions.getText());
                        } else {
                            for(int i=0; i<selectedPassions.size(); i++){
                                if(selectedPassions.get(i).equals(String.valueOf(btnPassions.getTag(btnPassions.getId())))){
                                    selectedPassions.remove(i);
                                    btnPassions.setTag(btnPassions.getId(), null);
                                }
                            }
                        }
                    }
                    txtSelectedCount.setText("("+ selectedPassions.size()+"/5)");
                }
            });
        }
    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        if(selectedPassions.size()>2){
//            updateProfileData();
//            Toast.makeText(GetPassionsEdit.this, "Updating Passions", Toast.LENGTH_LONG).show();
//        } else {
//            alertCreate();
//        }
//    }

    private void updateProfileData(){
        String postUrl = "http://faceapp.vindana.com.au/api/v1/faceapp/editprofile";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JSONArray jsonPassions = new JSONArray(selectedPassions);
        JSONObject postData = new JSONObject();
        try {
            postData.put("fbId", Tabs.getProfileId());
            postData.put("myPassions", jsonPassions);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("TEST2","response"+ String.valueOf(response));
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.i("TEST2", "error " + error);
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    public void alertCreate(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(GetPassionsEdit.this);
        alertDialogBuilder.setMessage("Pick up at least 3 passions or skip");
        alertDialogBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Toast.makeText(GetPassionsEdit.this,"Pick at least 3 passions or skip for now",Toast.LENGTH_LONG).show();
                    }
                }).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(selectedPassions.size()>2){
            updateProfileData();
            Toast.makeText(GetPassionsEdit.this, "Updating Passions", Toast.LENGTH_LONG).show();
            startActivity(new Intent(GetPassionsEdit.this, EditProfile.class));
            finish();
        } else {
            alertCreate();
        }
    }

    private void getPassionsList(Context context){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://faceapp.vindana.com.au/api/v1/faceapp/getpassions";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    JSONArray array;
                    try {
                        array = new JSONArray(response);
                        for(int i=0;i<array.length();i++){
                            passions.add(array.getString(i));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    setPassions();
                }, error -> Log.i("EVENT","fail"+error));
        queue.add(stringRequest);
    }
}