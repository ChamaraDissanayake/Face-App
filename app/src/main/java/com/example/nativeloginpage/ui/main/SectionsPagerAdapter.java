package com.example.nativeloginpage.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.nativeloginpage.FragmentChat;
import com.example.nativeloginpage.FragmentHome;
import com.example.nativeloginpage.FragmentProfile;
import com.example.nativeloginpage.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{
//            R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3, R.string.tab_text_4};
            R.string.tab_text_1, R.string.tab_text_3, R.string.tab_text_4};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch (position){
            case 0:
                fragment = new FragmentHome();
                break;

            case 1:
                fragment = new FragmentChat();
                break;
            case 2:
                fragment = new FragmentProfile();
                break;

//                case 1:
//                fragment = new FragmentRequests();
//                break;
//            case 2:
//                fragment = new FragmentChat();
//                break;
//            case 3:
//                fragment = new FragmentProfile();
//                break;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

}