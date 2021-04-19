package com.example.nativeloginpage;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.nativeloginpage.activities.SplashActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.Objects;

public class GetLocation extends AppCompatActivity {
    LinearLayout btnGetLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;
    TextView txtTellMore;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_location);
        Objects.requireNonNull(getSupportActionBar()).hide();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        checkLocationPermission();
        Tabs.setIsFirstLoad(true);
        btnGetLocation = findViewById(R.id.btnGetLocation);
        txtTellMore = findViewById(R.id.txtTellMore);

        if (!Tabs.getProfileIsNewUser()){
            getLocation();
        }

        btnGetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
            }
        });
    }

    private void checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(GetLocation.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            }
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
                    Intent intent = new Intent(GetLocation.this, SplashActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    checkLocationPermission();
                    txtTellMore.setText(null);
                }
            }
        });
    }
}