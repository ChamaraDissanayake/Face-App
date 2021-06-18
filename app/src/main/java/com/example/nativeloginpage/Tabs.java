package com.example.nativeloginpage;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.nativeloginpage.ui.main.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Objects;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class Tabs extends AppCompatActivity {

    private static TabLayout tabs;
    private static String profileId, profileImage, profileName, profileAge, profileUniversity, profileEmail,
            profileNumber, profileLocation, profileDescription, profileJob, profileCompany, profileCity;
    private static boolean profileIsFemale, profileShowGender, profileIsNewUser, isFirstLoad, doNotShowAge;
    private static ArrayList<String> profilePassions;
    private static Double Latitude, Longitude, CurrentLatitude, CurrentLongitude;
    private static int profileChatId, birthDay, birthMonth, birthYear;
    private final Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://35.247.160.195");
            Log.i("TEST2","Socket initialize: http://35.247.160.195");
        } catch (URISyntaxException e) {
            Log.i("TEST2","Error in socket initialize" + e);
            throw new RuntimeException(e);
        }
    }

    public static String getProfileId(){ return profileId; }
    public static String getProfileImage(){
        return profileImage;
    }
    public static String getProfileName(){
        return profileName;
    }
    public static String getProfileNumber() { return profileNumber; }
    public static boolean getProfileIsFemale() { return profileIsFemale; }
    public static boolean getProfileShowGender() { return profileShowGender; }
    public static boolean getProfileIsNewUser() { return profileIsNewUser; }
    public static boolean isDoNotShowAge() { return doNotShowAge; }
    public static Double getCurrentLatitude() { return CurrentLatitude; }
    public static Double getCurrentLongitude() { return CurrentLongitude; }
    public static String getProfileAge() { return profileAge; }
    public static Double getLatitude() {
        return Latitude;
    }
    public static Double getLongitude() {
        return Longitude;
    }
    public static boolean getIsFirstLoad() { return isFirstLoad; }
    public static int getProfileChatId() { return profileChatId; }
    public static void setDoNotShowAge(boolean doNotShowAge) { Tabs.doNotShowAge = doNotShowAge; }

    public static int getBirthDay() { return birthDay; }

    public static void setBirthDay(int birthDay) { Tabs.birthDay = birthDay; }

    public static int getBirthMonth() { return birthMonth; }

    public static void setBirthMonth(int birthMonth) { Tabs.birthMonth = birthMonth; }

    public static int getBirthYear() { return birthYear; }

    public static void setBirthYear(int birthYear) { Tabs.birthYear = birthYear; }

    public static String getProfileJob() {
        if (profileJob == null) {
            return "";
        } else {
            return profileJob;
        }
    }

    public static String getProfileCompany() {
        if (profileCompany == null) {
            return "";
        } else {
            return profileCompany;
        }
    }

    public static String getProfileCity() {
        if (profileCity == null) {
            return "";
        } else {
            return profileCity;
        }
    }

    public static String getProfileUniversity(){
        if (profileUniversity == null) {
            return "";
        }else {
            return profileUniversity;
        }
    }
    public static ArrayList<String> getProfilePassions() {
        if (profilePassions == null) {
            return new ArrayList<>();
        } else {
            return profilePassions;
        }
    }

    public static String getProfileDescription() {
        if (profileDescription == null || profileDescription.equals("null")) {
            return "";
        } else {
            return profileDescription;
        }
    }

    public static String getProfileEmail(){
        if (profileEmail == null) {
            return "Not available";
        }else {
            return profileEmail;
        }
    }

    public static String getProfileLocation() {
        if (profileLocation == null) {
            return "0.00,0.00";
        }else {
            return profileLocation;
        }
    }


    public static void setLatitude(Double latitude) {
        Latitude = latitude;
    }
    public static void setProfileId(String profileId) {
        Log.i("TEST2", profileId);
        Tabs.profileId = profileId;
    }
    public static void setLongitude(Double longitude) {
        Longitude = longitude;
    }
    public static void setProfileNumber(String profileNumber) { Tabs.profileNumber = profileNumber; }
    public static void setProfileAge(String profileAge) { Tabs.profileAge = profileAge; }

    public static void setProfileImage(String profileImage) {
        Tabs.profileImage = profileImage;
    }

    public static void setProfileName(String profileName) {
        Tabs.profileName = profileName;
    }

    public static void setProfileUniversity(String profileUniversity) {
        Tabs.profileUniversity = profileUniversity;
    }

    public static void setProfileEmail(String profileEmail) {
        Tabs.profileEmail = profileEmail;
    }

    public static void setProfileLocation(String profileLocation) {
        Tabs.profileLocation = profileLocation;
    }

    public static void setProfileDescription(String profileDescription) {
        Tabs.profileDescription = profileDescription;
    }

    public static void setProfileJob(String profileJob) {
        Tabs.profileJob = profileJob;
    }

    public static void setProfileCompany(String profileCompany) {
        Tabs.profileCompany = profileCompany;
    }

    public static void setProfileCity(String profileCity) {
        Tabs.profileCity = profileCity;
    }

    public static void setProfileIsFemale(boolean profileIsFemale) {
        Tabs.profileIsFemale = profileIsFemale;
    }

    public static void setProfileShowGender(boolean profileShowGender) {
        Tabs.profileShowGender = profileShowGender;
    }

    public static void setProfileIsNewUser(boolean profileIsNewUser) {
        Tabs.profileIsNewUser = profileIsNewUser;
    }

    public static void setProfilePassions(ArrayList<String> profilePassions) {
        Tabs.profilePassions = profilePassions;
    }

    public static void setCurrentLatitude(Double currentLatitude) {
        CurrentLatitude = currentLatitude;
    }

    public static void setCurrentLongitude(Double currentLongitude) {
        CurrentLongitude = currentLongitude;
    }

    public static void setIsFirstLoad(boolean isFirstLoad) {
        Tabs.isFirstLoad = isFirstLoad;
    }

    public static void setProfileChatId(int profileChatId) { Tabs.profileChatId = profileChatId; }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("TEST2", "test: "+requestCode + resultCode + data );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);

        Tabs.setIsFirstLoad(false);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);

        tabs = findViewById(R.id.tabs);

        tabs.setupWithViewPager(viewPager);
        Objects.requireNonNull(tabs.getTabAt(0)).setIcon(R.drawable.ic_home_24);
//        Objects.requireNonNull(tabs.getTabAt(1)).setIcon(R.drawable.ic_star_24);
//        Objects.requireNonNull(tabs.getTabAt(2)).setIcon(R.drawable.ic_chat_24);
//        Objects.requireNonNull(tabs.getTabAt(3)).setIcon(R.drawable.ic_profile_24);
        Objects.requireNonNull(tabs.getTabAt(1)).setIcon(R.drawable.ic_chat_24);
        Objects.requireNonNull(tabs.getTabAt(2)).setIcon(R.drawable.ic_profile_24);

        if(getIntent().hasExtra("From")){
            Objects.requireNonNull(tabs.getTabAt(1)).select();
            Objects.requireNonNull(Objects.requireNonNull(tabs.getTabAt(1)).getIcon()).setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
        }else {
            Objects.requireNonNull(Objects.requireNonNull(tabs.getTabAt(0)).getIcon()).setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
        }
//        Objects.requireNonNull(Objects.requireNonNull(tabs.getTabAt(0)).getIcon()).setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
//        tabs.getTabAt(1).getIcon().setColorFilter(Color.parseColor("#a8a8a8"), PorterDuff.Mode.SRC_IN);
//        tabs.getTabAt(2).getIcon().setColorFilter(Color.parseColor("#a8a8a8"), PorterDuff.Mode.SRC_IN);
//        tabs.getTabAt(3).getIcon().setColorFilter(Color.parseColor("#a8a8a8"), PorterDuff.Mode.SRC_IN);

//        Objects.requireNonNull(tabs.getTabAt(2)).select();
//        Objects.requireNonNull(Objects.requireNonNull(tabs.getTabAt(2)).getIcon()).setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tabPosition = tab.getPosition();
//                if(tabPosition == 1){
//                    Objects.requireNonNull(tab.getIcon()).setColorFilter(Color.parseColor("#FFDF00"), PorterDuff.Mode.SRC_IN);
//                } else {
                Objects.requireNonNull(tab.getIcon()).setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
//                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Objects.requireNonNull(tab.getIcon()).setColorFilter(Color.parseColor("#787878"), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        if(getProfileIsNewUser()) {
            createProfile();
            createDefaultSettings();
        } else {
            updateLocation();
            Log.i("TEST2", "Existing user");
        }
        mSocket.connect();
        mSocket.on("textMessage", onNewMessage);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSocket.disconnect();
        mSocket.off("textMessage", onNewMessage);
    }

    private final Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            Log.i("TEST2", "Message received");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
//                     String username = "Chamara";
                    String message;
                    try {
//                        username = data.getString("receiverId");
                        message = data.getString("message");
                    } catch (JSONException e) {
                        Log.i("TEST2", "Exception in onNewMessage " + e);
                        return;
                    }
                    Log.i("TEST2", "Message received" + data + message);
                }
            });
        }
    };

    public static void showTabBar(boolean bool){
        if(bool){
            tabs.setVisibility(View.VISIBLE);
        } else {
            tabs.setVisibility(View.GONE);
        }
    }

//    public void Display(){
//        Display display = getWindowManager().getDefaultDisplay();
//        Point size = new Point();
//        display.getSize(size);
//        int width = size.x;
//        Log.i("TEST2", "width" + width);
//    }

    public void createProfile(){
        String postUrl = "http://faceapp.vindana.com.au/api/v1/faceapp/setprofile";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONArray jsonPassions = new JSONArray(getProfilePassions());
        JSONObject postData = new JSONObject();
        try {
            postData.put("myName", getProfileName());
//            postData.put("myAge", "35");
            postData.put("fbId", getProfileId());
            postData.put("myEmail", getProfileEmail());
            postData.put("myProfileImage",getProfileImage());
            postData.put("myNumber",getProfileNumber());
            postData.put("isFemale", getProfileIsFemale());
            postData.put("showGender", getProfileShowGender());
            postData.put("myUniversity", getProfileUniversity());
            postData.put("myLocation", getProfileLocation());
            postData.put("chatId", getProfileChatId());
            postData.put("showAge", !isDoNotShowAge());
            postData.put("birthDay",getBirthDay());
            postData.put("birthMonth",getBirthMonth());
            postData.put("birthYear",getBirthYear());

            if(jsonPassions.length()>2){
                postData.put("myPassions", jsonPassions);
            }
            Log.i("TEST2", "Ready to send: " + postData);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("TEST2", "response for in createProfile: " + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.i("TEST2", "error in createProfile " + error);
                FragmentHome.pgd.hide();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void updateLocation(){
        String postUrl = "http://faceapp.vindana.com.au/api/v1/faceapp/editprofile";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JSONObject postData = new JSONObject();
        try {
            postData.put("fbId", Tabs.getProfileId());
            postData.put("myLocation", Tabs.getProfileLocation());      //Test data
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("TEST2","response"+ response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.i("TEST2", "error in updateLocation" + error);
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void createDefaultSettings() {
        String postUrl = "http://faceapp.vindana.com.au/api/v1/faceapp/setSetting";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JSONObject postData = new JSONObject();

        try {
            postData.put("fbId", getProfileId());
            postData.put("showCurrentLocation", true);
            postData.put("showGlobal", false);
            postData.put("maxDistance", "100");
            postData.put("ageLower","20");
            postData.put("ageHigher", "45");
            if(getProfileIsFemale()){
                postData.put("showMe", "Men");
            }else {
                postData.put("showMe", "Women");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("TEST2", "response in createDefaultSettings" + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.i("TEST2", "error in createDefaultSettings" + error);
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onBackPressed() {
        Log.i("TEST2", "BackKey pressed");
        buildAlertMessageQuitWarning();
    }

    private void buildAlertMessageQuitWarning() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure want to quit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
}