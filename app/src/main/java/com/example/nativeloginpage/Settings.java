package com.example.nativeloginpage;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.material.slider.RangeSlider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.Objects;

public class Settings extends AppCompatActivity {
    private static String showMe;
    private static final String EMAIL = "email";
    TextView txtViewDistance, txtPhoneNumber, txtShowMe, txtAgeRange, txtLocation, txtViewLocation,txtCurrentLocation, txtManualLocation;
    ConstraintLayout EditPhoneNumber, loutShowMe, loutSetLocation, loutChangeLocation;
    RangeSlider ageRange;
    private static Boolean showCurrentLocation;
    ImageView chkCurrentLocation, chkManualLocation, imgCurrentLocation, imgManualLocation;
    public static void setShowMe(String showMe) {
        Settings.showMe = showMe;
    }
    public static Boolean getShowCurrentLocation() { return showCurrentLocation; }
    public static void setShowCurrentLocation(Boolean showCurrentLocation) { Settings.showCurrentLocation = showCurrentLocation; }
    com.google.android.material.slider.Slider sliderDistance;
    SwitchCompat btnShowGlobal;
    LoginButton fbLogIn;
    CallbackManager callbackManager;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Objects.requireNonNull(getSupportActionBar()).hide();

        showCurrentLocation = false;
        setShowMe("Woman");
        getSettings();
        Button btnSettingsBack = findViewById(R.id.btnSettingsBack);
        sliderDistance = findViewById(R.id.sliderDistance);
        txtViewDistance = findViewById(R.id.txtViewDistance);
        txtPhoneNumber = findViewById(R.id.txtPhoneNumber);
        EditPhoneNumber = findViewById(R.id.EditPhoneNumber);
        loutShowMe = findViewById(R.id.loutShowMe);
        txtShowMe = findViewById(R.id.txtShowMe);
        ageRange = findViewById(R.id.ageRange);
        txtAgeRange = findViewById(R.id.txtAgeRange);
        txtLocation = findViewById(R.id.txtLocation);
        loutSetLocation = findViewById(R.id.loutSetLocation);
        loutChangeLocation = findViewById(R.id.loutChangeLocation);
        txtViewLocation = findViewById(R.id.txtViewLocation);
        txtCurrentLocation = findViewById(R.id.txtCurrentLocation);
        txtManualLocation = findViewById(R.id.txtManualLocation);
        chkCurrentLocation = findViewById(R.id.chkCurrentLocation);
        chkManualLocation = findViewById(R.id.chkManualLocation);
        imgCurrentLocation = findViewById(R.id.imgCurrentLocation);
        imgManualLocation = findViewById(R.id.imgManualLocation);
        btnShowGlobal = findViewById(R.id.btnShowGlobal);
        fbLogIn = findViewById(R.id.fbLogIn);
//
//        txtViewDistance.setText(sliderDistance.getValue() + "km.");
        txtPhoneNumber.setText(Tabs.getProfileNumber()+" >");
//        txtShowMe.setText(showMe);
//        txtAgeRange.setText(ageRange.getValues().get(0).intValue() + " - " + ageRange.getValues().get(1).intValue());

        fbLogIn.setPermissions(Collections.singletonList(EMAIL));

        callbackManager = CallbackManager.Factory.create();

        fbLogIn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(getApplicationContext(),"onSuccess",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Settings.this, UserLogin.class));
                finish();
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


        btnSettingsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSettings();
                finish();
            }
        });

//        sliderDistance.setOnPositionChangeListener(new Slider.OnPositionChangeListener() {
//            @Override
//            public void onPositionChanged(Slider view, boolean fromUser, float oldPos, float newPos, int oldValue, int newValue) {
//                txtViewDistance.setText(sliderDistance.getValue() + "km.");
//            }
//        });
        sliderDistance.addOnChangeListener(new com.google.android.material.slider.Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull com.google.android.material.slider.Slider slider, float value, boolean fromUser) {
                txtViewDistance.setText(sliderDistance.getValue() + "km.");
            }
        });

        EditPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.this, ChangePhoneNumber.class).putExtra("myNumber",Tabs.getProfileNumber()));
            }
        });

        loutShowMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.this, ChooseShowMe.class).putExtra("showMe", String.valueOf(txtShowMe.getText())));
            }
        });

        ageRange.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                txtAgeRange.setText(String.valueOf(ageRange.getValues().get(0).intValue()) + " - " + ageRange.getValues().get(1).intValue());
            }
        });

        loutSetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtLocation.setVisibility(View.GONE);
                loutChangeLocation.setVisibility(View.VISIBLE);
            }
        });

        txtCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chkCurrentLocation.setVisibility(View.VISIBLE);
                imgCurrentLocation.setVisibility(View.VISIBLE);
                chkManualLocation.setVisibility(View.INVISIBLE);
                imgManualLocation.setVisibility(View.INVISIBLE);
                showCurrentLocation = true;

                String location;
                try {
                    location = "("+Tabs.getCurrentLatitude()+", "+Tabs.getCurrentLongitude()+")";
                }catch (Exception e){
                    location = "";
                }
                txtShowMe.setText(showMe);
                txtViewLocation.setText(location);
            }
        });

        txtManualLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.this, MapsActivity.class));
            }
        });

//        fbLogIn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(Settings.this, UserLogin.class));
//                finish();
//            }
//        });
        fbLogIn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.i("TEST2", "beforeTextChanged"+s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i("TEST2", "onTextChanged"+s);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(String.valueOf(s).equals("Continue with Facebook")){
                    startActivity(new Intent(Settings.this, UserLogin.class));
                    finish();
                }
            }
        });
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onStart() {
        super.onStart();
        setLocationStatus();
    }

    @Override
    protected void onResume() {
        super.onResume();
        String location;
        try {
            location = "("+Tabs.getLatitude()+", "+Tabs.getLongitude()+")";
        }catch (Exception e){
            location = "";
        }
        txtShowMe.setText(showMe);
        txtViewLocation.setText(location);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        updateSettings();
        finish();
    }

    private void setLocationStatus() {
        if(showCurrentLocation){
            txtLocation.setText("My Current Location");
            chkCurrentLocation.setVisibility(View.VISIBLE);
            imgCurrentLocation.setVisibility(View.VISIBLE);
            chkManualLocation.setVisibility(View.INVISIBLE);
            imgManualLocation.setVisibility(View.INVISIBLE);
        }else {
            txtLocation.setText("Manual Location");
            chkCurrentLocation.setVisibility(View.INVISIBLE);
            imgCurrentLocation.setVisibility(View.INVISIBLE);
            chkManualLocation.setVisibility(View.VISIBLE);
            imgManualLocation.setVisibility(View.VISIBLE);
        }
    }

    private void updateSettings() {
        String postUrl = "http://faceapp.vindana.com.au/api/v1/faceapp/setSetting";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JSONObject postData = new JSONObject();

        try {
            postData.put("fbId", Tabs.getProfileId());
            postData.put("showCurrentLocation", showCurrentLocation);
            postData.put("showGlobal", btnShowGlobal.isChecked());
            postData.put("maxDistance", sliderDistance.getValue());
            postData.put("showMe", String.valueOf(txtShowMe.getText()));
            postData.put("ageLower",ageRange.getValues().get(0).intValue());
            postData.put("ageHigher", ageRange.getValues().get(1).intValue());
            if(!showCurrentLocation){
                postData.put("manualLat", Tabs.getLatitude());
                postData.put("manualLong", Tabs.getLongitude());
                Log.i("TEST2", "success");
            }
        } catch (JSONException e) {
            Log.i("TEST2", "fail: " + e);
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("TEST2", String.valueOf(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.i("TEST2", "error " + error);
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void getSettings() {
        String postUrl = "http://faceapp.vindana.com.au/api/v1/faceapp/getSetting";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JSONObject postData = new JSONObject();

        try {
            postData.put("fbId", Tabs.getProfileId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("TEST2", "response: " + response);

                try {
                    showCurrentLocation = response.getBoolean("showCurrentLocation");
                    btnShowGlobal.setChecked(response.getBoolean("showGlobal"));
                    sliderDistance.setValue(response.getInt("maxDistance"));
                    showMe = response.getString("showMe");
                    setShowMe(showMe);
                    if(!showCurrentLocation){
                        Tabs.setLatitude(response.getDouble("manualLat"));
                        Tabs.setLongitude(response.getDouble("manualLong"));
                    }

                    txtViewDistance.setText(response.getInt("maxDistance") + "km.");
                    txtShowMe.setText(showMe);
                    txtAgeRange.setText(response.getInt("ageLower") + " - " + response.getInt("ageHigher"));
                    ageRange.setValues((float) response.getInt("ageLower"), (float) response.getInt("ageHigher"));
                    setLocationStatus();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.i("TEST2", "error " + error);
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}