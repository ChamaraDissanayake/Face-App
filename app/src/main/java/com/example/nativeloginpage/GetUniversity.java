package com.example.nativeloginpage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class GetUniversity extends AppCompatActivity {
    LinearLayout btnGetUniversity;
    Button btnBack;
    TextView btnSkip;
    EditText txtEnterUniversity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_university);
        Objects.requireNonNull(getSupportActionBar()).hide();

        btnGetUniversity = findViewById(R.id.btnGetUniversity);
        btnBack = findViewById(R.id.btnBack);
        btnSkip = findViewById(R.id.btnSkip);
        txtEnterUniversity = findViewById(R.id.txtEnterUniversity);
//        Bundle userData = getIntent().getExtras();


        btnGetUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String university = String.valueOf(txtEnterUniversity.getText()).trim();
                if(university.length()>=2){
//                    userData.putString("myUniversity", String.valueOf(txtEnterUniversity.getText()));
                    Tabs.setProfileUniversity(String.valueOf(txtEnterUniversity.getText()));
//                    Intent intent = new Intent(GetUniversity.this,GetPassions.class).putExtras(userData);
                    Intent intent = new Intent(GetUniversity.this,GetPassions.class);
                    startActivity(intent);
                } else {
                    txtEnterUniversity.setError("Enter university or skip this step");
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
//                Intent intent = new Intent(GetUniversity.this,GetPassions.class).putExtras(userData);
                Intent intent = new Intent(GetUniversity.this,GetPassions.class);
                startActivity(intent);
            }
        });
    }
}