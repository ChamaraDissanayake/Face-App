package com.example.nativeloginpage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class UserLogin extends AppCompatActivity {

    private static final String EMAIL = "email";

    LoginButton login;
    ImageView fbProfilePicture;
    CallbackManager callbackManager;
//    Bundle userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        login = (LoginButton) findViewById(R.id.login);
        fbProfilePicture = findViewById(R.id.fbProfilePicture);
        login.setPermissions(Collections.singletonList(EMAIL));
//        userData = new Bundle();

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
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
            getDataFromDB(accessToken.getUserId());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void fbLoginSuccess() {
        Toast.makeText(this,"Login success with Facebook",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(UserLogin.this, GetPhoneNumber.class);
        startActivity(intent);
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
                            Log.i("TEST2", "data"+ data);
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
//                            userData.putString("fbId", myId);
                            Tabs.setProfileId(myId);
//                            userData.putString("myName", myName);
                            Tabs.setProfileName(myName);
//                            userData.putString("myAge", myAge);
                            Tabs.setProfileAge(myAge);
//                            userData.putString("myEmail", myEmail);
                            Tabs.setProfileEmail(myEmail);
//                            userData.putString("myProfileImage", profilePicUrl);
                            Tabs.setProfileImage(profilePicUrl);
//                            userData.putBoolean("isNewUser", true);
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
                Log.i("TEST2", String.valueOf(response));
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

//            userData.putString("fbId", response.getString("fbId"));
            Tabs.setProfileId(response.getString("fbId"));
//            userData.putString("myName", response.getString("myName"));
            Tabs.setProfileName(response.getString("myName"));
//            userData.putString("myAge", response.getString("myAge"));
            Tabs.setProfileAge(response.getString("myAge"));
//            userData.putString("myEmail", response.getString("myEmail"));
            Tabs.setProfileEmail(response.getString("myEmail"));
//            userData.putString("myProfileImage", response.getString("myProfileImage"));
            Tabs.setProfileImage(response.getString("myProfileImage"));
//            userData.putString("myUniversity", response.getString("myUniversity"));
            Tabs.setProfileUniversity(response.getString("myUniversity"));
//            userData.putBoolean("isFemale", response.getBoolean("isFemale"));
            Tabs.setProfileIsFemale(response.getBoolean("isFemale"));
//            userData.putBoolean("showGender", response.getBoolean("showGender"));
            Tabs.setProfileShowGender(response.getBoolean("showGender"));
//            userData.putString("myNumber", response.getString("myNumber"));
            Tabs.setProfileNumber(response.getString("myNumber"));
//            userData.putString("myDescription", response.getString("myDescription"));
            Tabs.setProfileDescription(response.getString("myDescription"));
//            userData.putString("myJob", response.getString("myJob"));
            Tabs.setProfileJob(response.getString("myJob"));
//            userData.putString("myCompany", response.getString("myCompany"));
            Tabs.setProfileCompany(response.getString("myCompany"));
//            userData.putString("myCity", response.getString("myCity"));
            Tabs.setProfileImage(response.getString("myProfileImage"));
//            userData.putStringArrayList("myPassions",myPassions);
            Tabs.setProfilePassions(myPassions);
//            userData.putBoolean("isNewUser", false);
            Tabs.setProfileIsNewUser(false);

            Intent intent = new Intent(UserLogin.this, GetLocation.class);
            startActivity(intent);

        } catch (Exception e){
            Log.i("TEST2", String.valueOf(e));
        }
    }
}