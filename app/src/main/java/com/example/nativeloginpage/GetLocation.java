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

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class GetLocation extends AppCompatActivity {
    LinearLayout btnGetLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;
    TextView txtTellMore;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_location);
        getSupportActionBar().hide();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        checkLocationPermission();

        btnGetLocation = findViewById(R.id.btnGetLocation);
        txtTellMore = findViewById(R.id.txtTellMore);
        Bundle userData = getIntent().getExtras();
//        userData.putString("myLocation", "7.1553552,80.1414968");

        btnGetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(GetLocation.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(GetLocation.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                fusedLocationProviderClient.getLastLocation().addOnSuccessListener(GetLocation.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if(location != null){
                            String lastLocation = location.getLatitude() + "," + location.getLongitude();
                            userData.putString("myLocation", lastLocation);
                            txtTellMore.setText(lastLocation);
                            Toast.makeText(GetLocation.this,"Location: " + lastLocation,Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(GetLocation.this, Tabs.class).putExtras(userData);
                            startActivity(intent);
                            //                finish();
                        }else {
                            checkLocationPermission();
                            txtTellMore.setText(null);
                        }

                    }
                });


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
}