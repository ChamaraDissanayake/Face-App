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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apmem.tools.layouts.FlowLayout;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Objects;

public class GetPassions extends AppCompatActivity {
    LinearLayout btnGetPassions;
    Button btnBack;
    TextView btnSkip, txtPassionsCount;
    private final ArrayList<String> passions = new ArrayList<>();
    private final ArrayList<String> selectedPassions = new ArrayList<>();
    FlowLayout loutPassions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_passions);
        Objects.requireNonNull(getSupportActionBar()).hide();

        getPassionsList(getApplicationContext());
        btnGetPassions = findViewById(R.id.btnGetPassions);
        btnBack = findViewById(R.id.btnBack);
        btnSkip = findViewById(R.id.btnSkip);
        loutPassions = findViewById(R.id.loutPassions);
        txtPassionsCount = findViewById(R.id.txtPassionsCount);
//        Bundle userData = getIntent().getExtras();

//        userData.putStringArrayList("myPassions",selectedPassions);
        Tabs.setProfilePassions(selectedPassions);

        btnGetPassions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedPassions.size()>2){
//                    Intent intent = new Intent(GetPassions.this,GetLocation.class).putExtras(userData);
                    Intent intent = new Intent(GetPassions.this,GetLocation.class);
                    startActivity(intent);
                } else {
                    alertCreate();
                }

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(GetPassions.this, GetLocation.class).putExtras(userData);
                Intent intent = new Intent(GetPassions.this, GetLocation.class);
                startActivity(intent);
            }
        });
    }

    private void setPassions(){
        for(int i =0; i<passions.size(); i++) {
            android.view.View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.passions_set_view, loutPassions, false);
            ToggleButton btnPassions = view.findViewById(R.id.btnPassion);
            btnPassions.setText(passions.get(i));
            btnPassions.setTextOn(passions.get(i));
            btnPassions.setTextOff(passions.get(i));
            loutPassions.addView(view);

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
                    txtPassionsCount.setText("CONTINUE ("+ selectedPassions.size()+"/5)");
                }
            });
        }
    }

    public void alertCreate(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(GetPassions.this);
        alertDialogBuilder.setMessage("Pick at least 3 passions or skip for now");
                alertDialogBuilder.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                Toast.makeText(GetPassions.this,"Pick some passions",Toast.LENGTH_LONG).show();
                            }
                        }).show();
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