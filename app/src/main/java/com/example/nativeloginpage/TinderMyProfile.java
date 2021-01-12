package com.example.nativeloginpage;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class TinderMyProfile extends AppCompatActivity {

    private Context myContext;
    private MyProfile myProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tinder_my_profile);
    }

    public TinderMyProfile(Context myContext, MyProfile myProfile) {
        this.myContext = myContext;
        this.myProfile = myProfile;
    }

    public TinderMyProfile() {
    }
}