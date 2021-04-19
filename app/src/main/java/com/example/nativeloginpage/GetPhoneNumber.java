package com.example.nativeloginpage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.hbb20.CountryCodePicker;

import java.util.Objects;

public class GetPhoneNumber extends AppCompatActivity {
    LinearLayout btnGetNumber;
    Button btnBack;
    EditText txtEnterPhone;
    CountryCodePicker codePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_phone_number);
        Objects.requireNonNull(getSupportActionBar()).hide();

        btnGetNumber = findViewById(R.id.btnGetNumber);
        btnBack = findViewById(R.id.btnBack);
        txtEnterPhone = findViewById(R.id.txtEnterPhone);
        codePicker = findViewById(R.id.countryCodePicker);

//        Bundle userData = getIntent().getExtras();

        btnGetNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pNumber = String.valueOf(txtEnterPhone.getText()).trim();
                if(pNumber.length()>=9){
                    String phoneNumber = "+"+codePicker.getSelectedCountryCode()+pNumber;
//                    userData.putString("myNumber", phoneNumber);
//                    Intent intent = new "myNumber", phoneNumber);
                    Intent intent = new Intent(GetPhoneNumber.this,GetPhoneOTP.class).putExtra("myNumber", phoneNumber);
                    startActivity(intent);
                } else {
                    txtEnterPhone.setError("Enter valid phone number");
                }
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