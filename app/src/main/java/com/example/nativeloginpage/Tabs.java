package com.example.nativeloginpage;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.nativeloginpage.ui.main.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class Tabs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        tabs.getTabAt(0).setIcon(R.drawable.ic_home_24);
        tabs.getTabAt(1).setIcon(R.drawable.ic_star_24);
        tabs.getTabAt(2).setIcon(R.drawable.ic_chat_24);
        tabs.getTabAt(3).setIcon(R.drawable.ic_profile_24);
    }
}