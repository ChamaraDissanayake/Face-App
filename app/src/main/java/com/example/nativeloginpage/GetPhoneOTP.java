package com.example.nativeloginpage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class GetPhoneOTP extends AppCompatActivity {

    LinearLayout btnOTP;
    Button btnBack;
    TextView textViewPNumber, textViewResend;
    String phoneNumber, mVerificationId;
//    Bundle userData;
    PhoneAuthProvider.ForceResendingToken mResendToken;
    PinView enterOTP;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    public ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_phone_otp);
        Objects.requireNonNull(getSupportActionBar()).hide();

        progressDialog = new ProgressDialog(GetPhoneOTP.this);
        progressDialog.setMessage("Verification going on...");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.show();

        btnOTP = findViewById(R.id.btnVerifyOTP);
        btnBack = findViewById(R.id.btnBack);
        enterOTP = findViewById(R.id.enterOTP);
        textViewPNumber = findViewById(R.id.textViewPNumber);
        textViewResend = findViewById(R.id.textViewResend);

//        userData = getIntent().getExtras();
//        phoneNumber = userData.getString("myNumber");
        phoneNumber = getIntent().getStringExtra("myNumber");
        textViewPNumber.setText(phoneNumber);

        btnOTP.setOnClickListener(v -> {
            progressDialog.show();
            if(enterOTP.getText().length() == 6){
                verifyCode(String.valueOf(enterOTP.getText()));
                Toast.makeText(GetPhoneOTP.this,"Validating ",Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(GetPhoneOTP.this,"Not valid ",Toast.LENGTH_SHORT).show();
            }
            progressDialog.hide();
//77 286 85 99
        });

        btnBack.setOnClickListener(v ->
                finish()
        );

        textViewResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendVerificationCode(phoneNumber, mResendToken);
            }
        });

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                progressDialog.hide();
//                Toast.makeText(GetPhoneOTP.this,"Automatically verified",Toast.LENGTH_SHORT).show();
                String code = phoneAuthCredential.getSmsCode();
                if(code != null){
                    enterOTP.setText(code);
                    verifyCode(code);
                }
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(GetPhoneOTP.this,"Automatic verification failed" + e,Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(GetPhoneOTP.this, GetPhoneNumber.class).putExtras(userData);
                Intent intent = new Intent(GetPhoneOTP.this, GetPhoneNumber.class);
                startActivity(intent);
                progressDialog.hide();
            }

            @Override
            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(verificationId, forceResendingToken);
                progressDialog.hide();
                mVerificationId = verificationId;
                mResendToken = forceResendingToken;
                Toast.makeText(GetPhoneOTP.this,"Code sent",Toast.LENGTH_SHORT).show();
            }
        };
        sendVerifyCode(phoneNumber);
    }

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void sendVerifyCode(String pNum){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(pNum)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(GetPhoneOTP.this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);



//        https://www.youtube.com/watch?v=lk4du-8giyQ
//        https://www.youtube.com/watch?v=KVnJCOBsWGQ
//        https://firebase.google.com/docs/auth/android/phone-auth
    }

    private void resendVerificationCode(String pNum,
        PhoneAuthProvider.ForceResendingToken token) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(GetPhoneOTP.this)
                        .setCallbacks(mCallbacks)
                        .setForceResendingToken(token)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(GetPhoneOTP.this,"Verification successful",Toast.LENGTH_SHORT).show();
//                            if(userData.getBoolean("isNewUser")){
                            Tabs.setProfileNumber(String.valueOf(getIntent().getStringExtra("myNumber")));
                            Intent intent;
                            if(Tabs.getProfileIsNewUser()){
//                                Intent intent = new Intent(GetPhoneOTP.this, GetGender.class).putExtras(userData);
                                intent = new Intent(GetPhoneOTP.this, GetGender.class);
                            }else {
//                                Tabs.setProfileNumber(String.valueOf(userData.get("myNumber")));
                                intent = new Intent(GetPhoneOTP.this, Settings.class);
                            }
                            startActivity(intent);
                            finish();
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(GetPhoneOTP.this,"Verification failed:" + task.getException(),Toast.LENGTH_SHORT).show();
                                enterOTP.setError("Code not match");
//                                txtOTPMessage.setText(String.valueOf(task.getException()));
                            }
                        }
                    }
                });
    }
}