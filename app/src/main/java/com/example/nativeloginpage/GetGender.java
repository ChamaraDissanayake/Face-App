package com.example.nativeloginpage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.checkbox.MaterialCheckBox;

import java.util.Objects;

public class GetGender extends AppCompatActivity {
    LinearLayout btnGetGender;
    Button btnBack;
    RadioGroup rdoIsFemale;
    RadioButton gender;
    MaterialCheckBox genderVisibility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_gender);
        Objects.requireNonNull(getSupportActionBar()).hide();

        btnGetGender = findViewById(R.id.btnGetGender);
        btnBack = findViewById(R.id.btnBack);
        rdoIsFemale = findViewById(R.id.rdoIsFemale);
        genderVisibility = findViewById(R.id.chkGenderVisibility);

//        Bundle userData = getIntent().getExtras();
//        String pNum = userData.getString("myNumber");
//        String myOTP = userData.getString("myOTP");
//
//        Toast.makeText(this, "pNumber "+ pNum + " myOTP: "+otp, Toast.LENGTH_LONG).show();

        btnGetGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = rdoIsFemale.getCheckedRadioButtonId();
                gender = findViewById(selectedId);
                boolean isFemale;
                isFemale = String.valueOf(gender.getText()).equals("WOMAN"); //Set boolean value without using if statement
//                userData.putBoolean("isFemale", isFemale);
//                userData.putBoolean("showGender", genderVisibility.isChecked());

                Tabs.setProfileIsFemale(isFemale);
                Tabs.setProfileShowGender(genderVisibility.isChecked());

//                Intent intent = new Intent(GetGender.this,GetUniversity.class).putExtras(userData);
                Intent intent = new Intent(GetGender.this,GetUniversity.class);
                startActivity(intent);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}