package com.example.nativeloginpage;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import com.rey.material.widget.ImageView;

import java.util.Objects;

public class ChooseShowMe extends AppCompatActivity {
    RadioButton btnSelectMan, btnSelectWoman, btnSelectEveryone;
    ImageView chkMan, chkWoman, chkEveryone;
    Button btnShowMeBack;
    String show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_show_me);
        Objects.requireNonNull(getSupportActionBar()).hide();

        btnSelectMan = findViewById(R.id.btnSelectMan);
        btnSelectWoman = findViewById(R.id.btnSelectWoman);
        btnSelectEveryone = findViewById(R.id.btnSelectEveryone);
        chkMan = findViewById(R.id.chkMan);
        chkWoman = findViewById(R.id.chkWoman);
        chkEveryone = findViewById(R.id.chkEveryone);
        btnShowMeBack = findViewById(R.id.btnShowMeBack);
        show = getIntent().getStringExtra("showMe");

        if(show.equals("Man")){
            btnSelectMan.setChecked(true);
            chkMan.setVisibility(View.VISIBLE);
            chkWoman.setVisibility(View.INVISIBLE);
            chkEveryone.setVisibility(View.INVISIBLE);
        } else if (show.equals("Woman")){
            btnSelectWoman.setChecked(true);
            chkMan.setVisibility(View.INVISIBLE);
            chkWoman.setVisibility(View.VISIBLE);
            chkEveryone.setVisibility(View.INVISIBLE);
        } else {
            btnSelectEveryone.setChecked(true);
            chkMan.setVisibility(View.INVISIBLE);
            chkWoman.setVisibility(View.INVISIBLE);
            chkEveryone.setVisibility(View.VISIBLE);
        }


        btnSelectMan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnSelectMan.isChecked()){
                    chkMan.setVisibility(View.VISIBLE);
                    chkWoman.setVisibility(View.INVISIBLE);
                    chkEveryone.setVisibility(View.INVISIBLE);
                }
            }
        });

        btnSelectWoman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnSelectWoman.isChecked()){
                    chkMan.setVisibility(View.INVISIBLE);
                    chkWoman.setVisibility(View.VISIBLE);
                    chkEveryone.setVisibility(View.INVISIBLE);
                }
            }
        });

        btnSelectEveryone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnSelectEveryone.isChecked()){
                    chkMan.setVisibility(View.INVISIBLE);
                    chkWoman.setVisibility(View.INVISIBLE);
                    chkEveryone.setVisibility(View.VISIBLE);
                }
            }
        });

        btnShowMeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(btnSelectMan.isChecked()){
                    show = String.valueOf(btnSelectMan.getText());
                } else if (btnSelectWoman.isChecked()){
                    show = String.valueOf(btnSelectWoman.getText());
                } else {
                    show = String.valueOf(btnSelectEveryone.getText());
                }

                Settings.setShowMe(show);
                finish();
            }
        });
    }
}