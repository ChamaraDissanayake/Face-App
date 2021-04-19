package com.example.nativeloginpage;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FragmentHome extends Fragment {
    private static int screenWidth;
    private SwipePlaceHolderView mSwipeView;
    private Context mContext;
    public static String profileList;
//    public String getProfileData;
    public static ProgressDialog pgd;
    private static FloatingActionButton btnBoost, btnRewind;

//    LinearLayout loutProfileDetails;
//    Button btnImage;
//    Button btnMinimize;
//    boolean tabVisibility;
//    110412007695746


    public FragmentHome() { }

    @androidx.annotation.Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @androidx.annotation.Nullable ViewGroup container, @androidx.annotation.Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

//        loutProfileDetails = getView().findViewById(R.id.loutProfileDetails);
//        btnImage = getView().findViewById(R.id.btnImage);
//        btnMinimize = getView().findViewById(R.id.btnMinimize);
//        tabVisibility = false;
//        ToggleFullScreenMode();

        pgd = new ProgressDialog(getContext());
        pgd.setMessage("Loading...");
        pgd.setIndeterminate(false);
        pgd.setCancelable(false);
        pgd.show();

        try {
            getProfileData(getContext());
//            getProfileData = getProfileData(getContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

//        new Handler().postDelayed(() -> addData(), 5000);

        mSwipeView = Objects.requireNonNull(getView()).findViewById(R.id.swipeView);
        mContext = getContext();
        int bottomMargin = dpToPx(160);
        Point windowSize = getDisplaySize(Objects.requireNonNull(getActivity()).getWindowManager());
        screenWidth = windowSize.x;
        mSwipeView.getBuilder()
                .setDisplayViewCount(3)
                .setSwipeDecor(new SwipeDecor()
//                        .setViewWidth(windowSize.x)
//                        .setViewHeight(windowSize.y - bottomMargin)
                        .setViewGravity(Gravity.TOP)
                        .setRelativeScale(0.01f)
                        .setSwipeInMsgLayoutId(R.layout.profiles_accept_msg_view)
                        .setSwipeOutMsgLayoutId(R.layout.profiles_reject_msg_view));

//        for(Profile profile : loadProfiles(getContext())){
//            mSwipeView.addView(new ProfilesCard(mContext, profile, mSwipeView));
//        }

        getView().findViewById(R.id.btnReject).setOnClickListener(v -> {
            ProfilesCard.setViewToSwipe();
            mSwipeView.doSwipe(false);
            showFabButtons();
        });

        getView().findViewById(R.id.btnAccept).setOnClickListener(v -> {
            ProfilesCard.setViewToSwipe();
            mSwipeView.doSwipe(true);
            showFabButtons();
        });

        Objects.requireNonNull(getView()).findViewById(R.id.btnProfile).setOnClickListener(v -> {
            Log.i("TEST", "Chat id " + Tabs.getProfileChatId());
//            Intent intent = new Intent(getContext(), SplashActivity.class);
//            startActivity(intent);
        });

        btnBoost = getView().findViewById(R.id.btnBoost);
        btnRewind = getView().findViewById(R.id.btnRewind);
    }

//    public String getProfileData(Context context){
//        RequestQueue queue = Volley.newRequestQueue(context);
//        String url = "http://faceapp.vindana.com.au/api/v1/faceapp/getprofiles";
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                response -> {
//                    profileList = response;
//                    addData();
//                }, error -> Log.i("EVENT","fail"+error));
//        queue.add(stringRequest);
//        return profileList;
//    }

    public void getProfileData(Context context){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://faceapp.vindana.com.au/api/v1/faceapp/getprofiles?fbId="+Tabs.getProfileId();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    profileList = response;
                    addData();
                }, error -> Log.i("EVENT","fail"+error));
        queue.add(stringRequest);
    }

    public void addData(){
        for(Profile profile : Objects.requireNonNull(loadProfiles(this.getContext()))){
            mSwipeView.addView(new ProfilesCard(mContext, profile, mSwipeView));
        }
    }

    public static @Nullable List<Profile> loadProfiles(Context context){
        try{
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            JSONArray array = new JSONArray(profileList);
//            JSONArray array = new JSONArray(loadJSONFromAsset(context, "profiles.json"));

            List<Profile> profilesList = new ArrayList<>();
            for(int i=0;i<array.length();i++){
                Profile profile = gson.fromJson(array.getString(i), Profile.class);
                profilesList.add(profile);
            }
            pgd.hide();
            return profilesList;
        }catch (Exception e){
            e.printStackTrace();
            pgd.hide();
            return null;
        }
    }

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

    @Contract("_ -> new")
    public static @NotNull Point getDisplaySize(WindowManager windowManager){
        try {
            Display display = windowManager.getDefaultDisplay();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            display.getMetrics(displayMetrics);
            return new Point(displayMetrics.widthPixels, displayMetrics.heightPixels);
        }catch (Exception e){
            e.printStackTrace();
            return new Point(0, 0);
        }
    }
    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static void hideFabButtons(){
        btnBoost.hide();
        btnRewind.hide();
    }

    public static void showFabButtons(){
        btnBoost.show();
        btnRewind.show();
    }

    public static int getScreenWidth() {
        return screenWidth;
    }
}