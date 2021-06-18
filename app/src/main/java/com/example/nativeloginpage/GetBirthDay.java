package com.example.nativeloginpage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.checkbox.MaterialCheckBox;

import java.util.Objects;

public class GetBirthDay extends AppCompatActivity {

    DatePicker datePicker;
    MaterialCheckBox chkAgeVisibility;
    LinearLayout btnGetBirthDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_birth_day);
        Objects.requireNonNull(getSupportActionBar()).hide();

        datePicker = findViewById(R.id.datePicker);
        chkAgeVisibility = findViewById(R.id.chkAgeVisibility);
        btnGetBirthDay = findViewById(R.id.btnGetBirthDay);

        datePicker.updateDate(1990,0,1);

        btnGetBirthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tabs.setDoNotShowAge(chkAgeVisibility.isChecked());
                Tabs.setBirthYear(datePicker.getYear()+1);
                Tabs.setBirthMonth(datePicker.getMonth()+1);
                Tabs.setBirthDay(datePicker.getDayOfMonth());
                Log.i("TEST2", "isDoNotShowAge: " + Tabs.isDoNotShowAge() + Tabs.getBirthDay() + "/" + Tabs.getBirthMonth() + "/" + Tabs.getBirthYear());
                startActivity(new Intent(GetBirthDay.this, GetPhoneNumber.class));
            }
        });
    }
}