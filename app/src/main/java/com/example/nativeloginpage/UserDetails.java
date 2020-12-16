package com.example.nativeloginpage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserDetails extends AppCompatActivity {

    Button btnVerify, btnFB;
    TextView txtVerify, txtNameMain, txtEmailMain;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        btnVerify = findViewById(R.id.btnVerify);
        txtVerify = findViewById(R.id.txtVerify);
        txtNameMain = findViewById(R.id.txtNameMain);
        txtEmailMain = findViewById(R.id.txtEmailMain);
        btnFB = findViewById(R.id.login_button_main);

//        FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();
//            if(!firebaseUser.isEmailVerified()){
//                txtVerify.setVisibility(View.VISIBLE);
//                btnVerify.setVisibility(View.VISIBLE);
//            } else {
//                txtNameMain.setText(firebaseUser.getDisplayName());
//                txtEmailMain.setText(firebaseUser.getEmail());
//            }

//        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
//
//        if(signInAccount != null){
//            Toast.makeText(this,"Success",Toast.LENGTH_LONG);
//            txtNameMain.setText(signInAccount.getDisplayName());
//            txtEmailMain.setText(signInAccount.getEmail());
//        } else {
//            Toast.makeText(this,"No information",Toast.LENGTH_LONG);
//        }

    }

    public void btnLogoutOnCreate(View view) {
        firebaseAuth.getInstance().signOut();
        startActivity(new Intent(UserDetails.this,LoginChoose.class));
        finish();

    }

    public void btnVerifyOnClick(View view) {
        FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        firebaseUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(view.getContext(),"Please check your Email to verify your account",Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(view.getContext(),"Something went wrong: "+e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    public void gotoEmail(View view) {
//        startActivity(new Intent(UserDetails.this, MainActivity.class));
        startActivity(new Intent(UserDetails.this, Tabs.class));
    }
}