package com.example.nativeloginpage;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.HttpMethod;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class UserLogin extends AppCompatActivity {
    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final String EMAIL = "email";
    private static Intent mIntent;
    boolean isLoggedIn;
    LoginButton login;
    ImageView fbProfilePicture;
    CallbackManager callbackManager;
    ConstraintLayout directLoad, indirectLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        login = (LoginButton) findViewById(R.id.login);
        fbProfilePicture = findViewById(R.id.fbProfilePicture);
        directLoad = findViewById(R.id.directLoad);
        indirectLoad = findViewById(R.id.indirectLoad);
        login.setPermissions(Collections.singletonList(EMAIL));
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        mIntent = null;

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        isLoggedIn = accessToken != null && !accessToken.isExpired();
        callbackManager = CallbackManager.Factory.create();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("TEST2", "button working");
            }
        });
        login.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(getApplicationContext(),"onSuccess",Toast.LENGTH_SHORT).show();
                getDataFromDB(loginResult.getAccessToken().getUserId());
            }

            @Override
            public void onCancel() {
                Log.i("TEST2", "onCancel");
                Toast.makeText(getApplicationContext(),"onCancel",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Log.i("TEST2", "Error: " + error);
                Toast.makeText(getApplicationContext(),"onError",Toast.LENGTH_SHORT).show();
            }
        });

        if(isLoggedIn){
            assert accessToken != null;
            getDataFromDB(accessToken.getUserId());
        }else{
            directLoad.setVisibility(View.GONE);
            indirectLoad.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("TEST2", "Login check: " + !Tabs.getProfileIsNewUser() + isLoggedIn);
        if(!Tabs.getProfileIsNewUser() && isLoggedIn){
            try {
//            new Handler().postDelayed(() -> addData(), 5000);
                checkLocationPermission();
            } catch (Exception e){
                Log.i("TEST2", "Error: " + e);
            }
        }
        if(mIntent != null){
            turnGPSOn(mIntent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void fbLoginSuccess() {
        Toast.makeText(this,"Login success with Facebook",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(UserLogin.this, GetBirthDay.class);
//        startActivity(intent);
        if(Tabs.getProfileIsNewUser()){
            startActivity(intent);
        }else{
            turnGPSOn(intent);
        }
    }

    public void getDataFromFB(){
        login.setPermissions(Arrays.asList("public_profile","email","user_birthday"));
        Bundle params = new Bundle();
        params.putString("fields", "id,email,first_name,picture.type(large)");

        new GraphRequest(AccessToken.getCurrentAccessToken(), "me", params, HttpMethod.GET,
                response -> {
                    if (response != null) {
                        try {
                            JSONObject data = response.getJSONObject();
                            Log.i("TEST2", "Get Data From FB: " + data);
                            String myId = data.getString("id"); //110412007695746
                            String myName = data.getString("first_name");
                            String myAge = "35";
                            String myEmail = null;
                            String profilePicUrl = null;
                            if (data.has("picture")) {
                                profilePicUrl = data.getJSONObject("picture").getJSONObject("data").getString("url");
                                Log.i("TEST2", "Image from fb: " + profilePicUrl);
                                Glide.with(UserLogin.this).load(profilePicUrl).into(fbProfilePicture);
                            }
                            if (data.has("email")) {
                                myEmail = data.getString("email");
                            }
                            Tabs.setProfileId(myId);
                            Tabs.setProfileName(myName);
                            Tabs.setProfileAge(myAge);
                            Tabs.setProfileEmail(myEmail);
                            Tabs.setProfileImage(profilePicUrl);
                            Tabs.setProfileIsNewUser(true);
                            fbLoginSuccess();
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Login Failed: "+e,Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                }).executeAsync();
    }

    private void getDataFromDB(String fbId){
        String postUrl = "http://faceapp.vindana.com.au/api/v1/faceapp/getmyprofile";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject postData = new JSONObject();
        try {
            postData.put("fbId", fbId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("TEST2", "response for getDataFromDB: " + response);
                if (!response.has("error")){
                    getExistingData(response);
                } else {
                    getDataFromFB();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void getExistingData(JSONObject response){
        try {
            JSONArray arr = response.getJSONArray("myPassions");
            ArrayList<String> myPassions = new ArrayList<String>();
            for(int i = 0; i < arr.length(); i++){
                myPassions.add(String.valueOf(arr.getString(i)));
            }

            Tabs.setProfileId(response.getString("fbId"));
            Tabs.setProfileName(response.getString("myName"));
            Tabs.setProfileAge(response.getString("myAge"));
            Tabs.setProfileEmail(response.getString("myEmail"));
            Tabs.setProfileImage(response.getString("myProfileImage"));
            Tabs.setProfileUniversity(response.getString("myUniversity"));
            Tabs.setProfileIsFemale(response.getBoolean("isFemale"));
            Tabs.setProfileShowGender(response.getBoolean("showGender"));
            Tabs.setProfileNumber(response.getString("myNumber"));
            Tabs.setProfileDescription(response.getString("myDescription"));
            Tabs.setProfileJob(response.getString("myJob"));
            Tabs.setProfileCompany(response.getString("myCompany"));
            Tabs.setProfileImage(response.getString("myProfileImage"));
            Tabs.setProfilePassions(myPassions);
            Tabs.setProfileIsNewUser(false);

            Intent intent = new Intent(UserLogin.this, Tabs.class);
//            Intent intent = new Intent(UserLogin.this, GetBirthDay.class);
//            startActivity(intent);
            turnGPSOn(intent);

        } catch (Exception e){
            Log.i("TEST2", String.valueOf(e));
        }
    }

    private void checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(UserLogin.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            checkLocationPermission();
        } else {
            getLocation();
        }
    }

    public void getLocation() {
        Log.i("TEST2", "getLocation user login");
        if (ActivityCompat.checkSelfPermission(UserLogin.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(UserLogin.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(UserLogin.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    String lastLocation = location.getLatitude() + "," + location.getLongitude();
                    Tabs.setProfileLocation(lastLocation);
                    if (Tabs.getProfileIsNewUser()) {
                        Tabs.setLatitude(location.getLatitude());
                        Tabs.setLongitude(location.getLongitude());
                    } else {
                        Tabs.setCurrentLatitude(location.getLatitude());
                        Tabs.setCurrentLongitude(location.getLongitude());
                    }
                    Toast.makeText(UserLogin.this, "Location: " + lastLocation, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(UserLogin.this, "Location cannot be read" , Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public void turnGPSOn(Intent intent) {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            mIntent = intent;
            buildAlertMessageNoGps();
        } else {
            startActivity(intent);
            finish();
        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems disabled, please enable it to continue")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                        finish();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
}