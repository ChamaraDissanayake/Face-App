package com.example.nativeloginpage;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MyProfileView extends AppCompatActivity {
    public static String profileList;
    SwipePlaceHolderView mySwipeView;
    Button btnBackMyProfile;
    private ArrayList<String> photos;
    private ArrayList<String> passions;

    public MyProfileView() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile_view);
        Objects.requireNonNull(getSupportActionBar()).hide();

        mySwipeView = findViewById(R.id.mySwipeView);

        mySwipeView.getBuilder()
                .setDisplayViewCount(1)
                .setSwipeDecor(new SwipeDecor()
                        .setViewGravity(Gravity.TOP)
                        .setRelativeScale(0.001f)
                        .setSwipeInMsgLayoutId(R.layout.profiles_accept_msg_view)
                        .setSwipeOutMsgLayoutId(R.layout.profiles_reject_msg_view));

        mySwipeView.doSwipe(false);
        mySwipeView.lockViews();

//        for(Profile profile : Objects.requireNonNull(loadProfiles())){
//            mSwipeView.addView(new ProfilesCard(MyProfileView.this, profile, mSwipeView));
//        }
        btnBackMyProfile = findViewById(R.id.btnBackMyProfile);
        btnBackMyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getProfileData();
    }

    public void getProfileData(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://faceapp.vindana.com.au/api/v1/faceapp/getprofile?fbId="+Tabs.getProfileId();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    profileList = response;
                    Log.i("TEST2", profileList);
                    addData();
                }, error -> Log.i("EVENT","fail"+error));
        queue.add(stringRequest);
    }

//    public void TestData(){
////        passions.add("Netflix");
////        passions.add("Climbing");
////        passions.add("Swimming");
////
////        photos.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.1.jpg");
////        photos.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.2.jpg");
////        photos.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.3.jpg");
//
//        JSONArray jsonArray = new JSONArray();
////        JSONArray jsonArrayPassions = new JSONArray(passions);
////        JSONArray jsonArrayPhotos = new JSONArray(photos);
//
//        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("user_name", "Chamara");
//        jsonObject.addProperty("city", "Warakapola");
////        jsonArray.put(jsonArrayPassions);
////        jsonArray.put(jsonArrayPhotos);
//        jsonArray.put(jsonObject);
//
//        Log.i("TEST3", "test: " + jsonArray);
//    }

    public void addData(){
        for(Profile profile : Objects.requireNonNull(loadProfiles())){
            mySwipeView.addView(new MyProfileCard(this, profile, mySwipeView));
        }
    }

    public @Nullable List<Profile> loadProfiles(){
        try{
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            JSONArray array = new JSONArray(profileList);
            List<Profile> profilesList = new ArrayList<>();
//            for(int i=0;i<array.length();i++){
            Profile profile = gson.fromJson(array.getString(0), Profile.class);
            profilesList.add(profile);
//            }
            return profilesList;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}

//[{"user_id":"102042351870136","user_name":"Chamara","isFemale":false,"description":null,"longitude":"","latitude":"","age":29,"university":"UOK","passions":["Hiking","Sports","Fitness"],"city":null,"photos":["https:\/\/platform-lookaside.fbsbx.com\/platform\/profilepic\/?asid=102042351870136&height=200&width=200&ext=1623569575&hash=AeSsh0BwZPNT3YH_6Ao"]}]
