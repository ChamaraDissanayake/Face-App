package com.example.nativeloginpage;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.Objects;

public class GetLocation extends AppCompatActivity {
    LinearLayout btnGetLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private static Boolean isGPSOn;
    TextView txtTellMore;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_location);
        Objects.requireNonNull(getSupportActionBar()).hide();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
//        checkLocationPermission();
        Tabs.setIsFirstLoad(true);
        btnGetLocation = findViewById(R.id.btnGetLocation);
        txtTellMore = findViewById(R.id.txtTellMore);
        isGPSOn = false;

//        if (!Tabs.getProfileIsNewUser()){
//            getLocation();
//        }

        btnGetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getLocation();
                turnGPSOn();
            }
        });
        Log.i("TEST2", "Updated version");
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if(!isGPSOn){
//            turnGPSOn();
//        }
    }

    private void checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(GetLocation.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            checkLocationPermission();
        }else{
            getLocation();
        }
    }

    public void getLocation(){
        if (ActivityCompat.checkSelfPermission(GetLocation.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(GetLocation.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(GetLocation.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null){
                    String lastLocation = location.getLatitude() + "," + location.getLongitude();
                    Tabs.setProfileLocation(lastLocation);
                    if (Tabs.getProfileIsNewUser()){
                        Tabs.setLatitude(location.getLatitude());
                        Tabs.setLongitude(location.getLongitude());
                    } else {
                        Tabs.setCurrentLatitude(location.getLatitude());
                        Tabs.setCurrentLongitude(location.getLongitude());
                    }

                    txtTellMore.setText(lastLocation);
                    Toast.makeText(GetLocation.this,"Location: " + lastLocation,Toast.LENGTH_LONG).show();
//                    Intent intent = new Intent(GetLocation.this, Tabs.class);
//                    Intent intent = new Intent(GetLocation.this, SplashActivity.class);
//                    startActivity(intent);
//                    finish();
                }else {
//                    checkLocationPermission();
                    txtTellMore.setText(null);
                }
                Intent intent = new Intent(GetLocation.this, Tabs.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void turnGPSOn() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
        } else {
            isGPSOn = true;
            checkLocationPermission();
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