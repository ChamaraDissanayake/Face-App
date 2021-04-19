package com.example.nativeloginpage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class ChangePhoneNumber extends AppCompatActivity {

    Button btnChangePhoneBack;
    TextView txtViewPhone;
    com.rey.material.widget.Button btnUpdatePhone;
//    Bundle userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_phone_number);
        Objects.requireNonNull(getSupportActionBar()).hide();

        txtViewPhone = findViewById(R.id.txtViewPhone);
        btnChangePhoneBack = findViewById(R.id.btnChangePhoneBack);
        btnUpdatePhone = findViewById(R.id.btnUpdatePhone);

        txtViewPhone.setText(Tabs.getProfileNumber());

//        try{
//            userData = getIntent().getExtras();
//            Log.i("TEST2", "new data available: " + userData.get("myNumber"));
//        }catch (Exception e){
//            Log.i("TEST2", "Data not available" + e);
//        }



        btnChangePhoneBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                try{
//                    startActivity(new Intent(ChangePhoneNumber.this, Settings.class).putExtras(userData));
//                }catch (Exception e){
//                    Toast.makeText(ChangePhoneNumber.this, "Error: " + e, Toast.LENGTH_LONG).show();
//                    finish();
//                }
                finish();
            }
        });

        btnUpdatePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                userData = new Bundle();
//                userData.putBoolean("isNewUser", false);
                Tabs.setProfileIsNewUser(false);
//                startActivity(new Intent(ChangePhoneNumber.this, GetPhoneNumber.class).putExtras(userData));
                startActivity(new Intent(ChangePhoneNumber.this, GetPhoneNumber.class));
            }
        });
    }
}