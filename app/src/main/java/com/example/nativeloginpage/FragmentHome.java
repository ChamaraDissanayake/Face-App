package com.example.nativeloginpage;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FragmentHome extends Fragment {
    private SwipePlaceHolderView mSwipeView;
    private Context mContext;
    public static String profileList; //Uncomment this when get data from service call
    //    public String getProfileData;
    public static ProgressDialog pgd;

    LinearLayout loutProfileDetails;
    Button btnImage, btnMinimize, btnTest1, btnTest2;
    boolean tabVisibility;

    public FragmentHome() { }

    @androidx.annotation.Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @androidx.annotation.Nullable ViewGroup container, @androidx.annotation.Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        loutProfileDetails = getView().findViewById(R.id.loutProfileDetails);
        btnImage = getView().findViewById(R.id.btnImage);
        btnMinimize = getView().findViewById(R.id.btnMinimize);
        tabVisibility = true;

        pgd = new ProgressDialog(getContext());
        pgd.setMessage("Please wait...It is downloading");
        pgd.setIndeterminate(false);
        pgd.setCancelable(false);
        pgd.show();

//        try {
//            getProfileData = getProfileData(this);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        new Handler().postDelayed(() -> getData(), 5000);

        mSwipeView = getView().findViewById(R.id.swipeView);
        mContext = getContext();
        int bottomMargin = dpToPx(160);
        Point windowSize = getDisplaySize(Objects.requireNonNull(getActivity()).getWindowManager());

        mSwipeView.getBuilder()
                .setDisplayViewCount(3)
                .setSwipeDecor(new SwipeDecor()
                        .setViewWidth(windowSize.x)
                        .setViewHeight(windowSize.y - bottomMargin)
                        .setViewGravity(Gravity.TOP)
                        .setRelativeScale(0.01f)
                        .setSwipeInMsgLayoutId(R.layout.tinder_swipe_in_msg_view)
                        .setSwipeOutMsgLayoutId(R.layout.tinder_swipe_out_msg_view));

        for(Profile profile : loadProfiles(getContext())){
            mSwipeView.addView(new TinderCard(mContext, profile, mSwipeView));
        }

        getView().findViewById(R.id.btnReject).setOnClickListener(v -> mSwipeView.doSwipe(false));

        getView().findViewById(R.id.btnAccept).setOnClickListener(v -> mSwipeView.doSwipe(true));

        getView().findViewById(R.id.btnProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToggleFullScreenMode();
            }
        });

//        TinderCard tinderCard = new TinderCard(mContext);

        btnImage.setOnClickListener(v -> ToggleFullScreenMode());

        btnMinimize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToggleFullScreenMode();
            }
        });

//        btnTest1 = getView().findViewById(R.id.btnTest1);
//        btnTest2 = getView().findViewById(R.id.btnTest2);
//
//        btnTest1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.i("TEST2", "index" + tinderCard.getImageIndex());
//            }
//        });
//
//        btnTest2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tinderCard.setImageIndex(3);
//                Log.i("TEST2", "index" + tinderCard.getImageIndex());
//            }
//        });
    }

//    public String getProfileData(Context context){
//        RequestQueue queue = Volley.newRequestQueue(context);
//        String url = "http://videounlimited.lk/app/socialque/api/getUsers";
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                response -> {
//                    profileList = response;
//
//                    getData();
//                }, error -> Log.i("EVENT","fail"+error));
//        queue.add(stringRequest);
//        return profileList;
//    }
//
//    public void getData(){
//        for(Profile profile : loadProfiles(this.getApplicationContext())){
//            mSwipeView.addView(new TinderCard(mContext, profile, mSwipeView));
//        }
//    }
//
//    public static List<Profile> loadProfiles(Context context){
//        try{
//            GsonBuilder builder = new GsonBuilder();
//            Gson gson = builder.create();
//            JSONArray array = new JSONArray(profileList);
////            JSONArray array = new JSONArray(loadJSONFromAsset(context, "profiles.json"));
//
//            List<Profile> profileList = new ArrayList<>();
//            for(int i=0;i<array.length();i++){
//                Profile profile = gson.fromJson(array.getString(i), Profile.class);
//                profileList.add(profile);
//            }
//            pgd.hide();
//            Log.i("TEST", "load profiles try is working");
//            return profileList;
//        }catch (Exception e){
//            e.printStackTrace();
//            pgd.hide();
//            Log.i("TEST", "load profiles else is working");
//            return null;
//        }
//    }

//    private static String loadJSONFromAsset(Context context, String jsonFileName) {
//        String json = null;
//        InputStream is=null;
//        try {
//            AssetManager manager = context.getAssets();
//            is = manager.open(jsonFileName);
//            int size = is.available();
//            byte[] buffer = new byte[size];
//            is.read(buffer);
//            is.close();
//            json = new String(buffer, "UTF-8");
//            Log.i("TEST", "loadJSONFromAsset try is working");
//        } catch (IOException ex) {
//            ex.printStackTrace();
//            Log.i("TEST", "loadJSONFromAsset catch is working");
//            return null;
//        }
//        return json;
//    }

    @org.jetbrains.annotations.Nullable
    public static List<Profile> loadProfiles(Context context){
        try{
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            JSONArray array = new JSONArray(loadJSONFromAsset(context, "profiles.json"));
            List<Profile> profileList = new ArrayList<>();

            for(int i=0;i<array.length();i++){
                Profile profile = gson.fromJson(array.getString(i), Profile.class);
                profileList.add(profile);
//                JSONObject obj = array.getJSONObject(i);
//                JSONArray photos = obj.getJSONArray("photos");
//
//                for(int j=0; j<photos.length(); j++){
//                    String url = photos.getString(j);
//                    Log.i("TEST2", i + "." + j + ") " + url);
//                }
            }
            pgd.hide();
            return profileList;
        }catch (Exception e){
            e.printStackTrace();
            pgd.hide();
            return null;
        }
    }

    private static @Nullable
    String loadJSONFromAsset(@NotNull Context context, String jsonFileName) {
        String json = null;
        InputStream is=null;
        try {
            AssetManager manager = context.getAssets();
            is = manager.open(jsonFileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public void ToggleFullScreenMode(){
        Log.i("TEST","Full Screen working");
        final TabLayout tabs = (TabLayout)getActivity().findViewById(R.id.tabs);
//        LinearLayout linearLayout= getView().findViewById(R.id.imageFooter);
        if (tabVisibility){
            tabs.setVisibility(View.GONE);
            mSwipeView.lockViews();
            loutProfileDetails.setVisibility(View.VISIBLE);
            btnImage.setVisibility(View.GONE);
            tabVisibility = false;
//            linearLayout.setVisibility(View.GONE);
        }else {
            tabs.setVisibility(View.VISIBLE);
            mSwipeView.unlockViews();
            loutProfileDetails.setVisibility(View.GONE);
            btnImage.setVisibility(View.VISIBLE);
            tabVisibility = true;
//            linearLayout.setVisibility(View.VISIBLE);

        }
    }

    @Contract("_ -> new")
    public static @NotNull Point getDisplaySize(WindowManager windowManager){
        try {
            if(Build.VERSION.SDK_INT > 16) {
                Display display = windowManager.getDefaultDisplay();
                DisplayMetrics displayMetrics = new DisplayMetrics();
                display.getMetrics(displayMetrics);
                return new Point(displayMetrics.widthPixels, displayMetrics.heightPixels);
            }else{
                return new Point(0, 0);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new Point(0, 0);
        }
    }
    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public void test(SwipePlaceHolderView swipeView, Context context, Profile profile){
        Log.i("TEST2", "Success");
//        ToggleFullScreenMode();
        mSwipeView = swipeView;
        mSwipeView.lockViews();
    }
}