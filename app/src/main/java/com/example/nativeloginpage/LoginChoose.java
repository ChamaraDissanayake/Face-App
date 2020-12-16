package com.example.nativeloginpage;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


public class LoginChoose extends AppCompatActivity {
    private final static int RC_SIGN_IN = 123;
    private GoogleSignInClient mGoogleSignInClient;
//    private static final String EMAIL = "email";



    Button btnChooseEmail, loginButton, fbLogin;
    TextView lblChooseSignUp;
    CallbackManager callbackManager;
    EditText txtEmail, txtPassword;
    ProgressBar pgbLogin;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_choose);

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        loginButton = (LoginButton) findViewById(R.id.login_button);
//        ((LoginButton) loginButton).setReadPermissions(Arrays.asList(EMAIL));
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
//        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));

        btnChooseEmail = findViewById(R.id.btnChooseEmail);
        lblChooseSignUp = findViewById(R.id.lblChooseSignUp);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        pgbLogin = findViewById(R.id.pgbLogin);
//        fbLogin = findViewById(R.id.login_button);


        callbackManager = CallbackManager.Factory.create();
        firebaseAuth = FirebaseAuth.getInstance();

        createRequest();
        findViewById(R.id.btnGoogleSignIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pgbLogin.setVisibility(View.VISIBLE);
                signIn();
            }
        });

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        fbLoginSuccess();
                        pgbLogin.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCancel() {
                        pgbLogin.setVisibility(View.GONE);                    }

                    @Override
                    public void onError(FacebookException exception) {
                        pgbLogin.setVisibility(View.GONE);                    }
                });

        lblChooseSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginChoose.this,SignUp.class));
            }
        });

        btnChooseEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtEmail.getText().toString().trim();
                String password = txtPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    txtPassword.setError("Email is required");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    txtPassword.setError("Password is required");
                    return;
                }

                if(password.length()<6){
                    txtPassword.setError("Password must include at least 6 characters");
                    return;
                }

                pgbLogin.setVisibility(View.VISIBLE);
                firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginChoose.this,"Login Success",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(LoginChoose.this, UserDetails.class));
                        } else {
                            Toast.makeText(LoginChoose.this,"Error! "+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }
                        pgbLogin.setVisibility(View.GONE);
                    }
                });
            }
        });

//        fbLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                pgbLogin.setVisibility(View.VISIBLE);
//            }
//        });

        if(firebaseAuth.getCurrentUser() != null){
            Toast.makeText(this,"User logged in",Toast.LENGTH_LONG).show();
            startActivity(new Intent(LoginChoose.this, UserDetails.class));
            finish();
        }
        if(isLoggedIn) {
            Toast.makeText(this, "Logged in", Toast.LENGTH_LONG).show();
            startActivity(new Intent(LoginChoose.this, UserDetails.class));
        } else {
            Toast.makeText(this, "Not logged in", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
        if (requestCode == RC_SIGN_IN) {
            Log.i("TEST","Login with google");
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Toast.makeText(this,"Google sign in success", Toast.LENGTH_LONG).show();
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                Toast.makeText(this,"Google sign in failed"+ e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    public void fbLoginSuccess() {
        Toast.makeText(this,"Login success with Facebook",Toast.LENGTH_LONG).show();
        startActivity(new Intent(LoginChoose.this, UserDetails.class));
    }

    //Google

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if(currentUser!=null){
            Log.i("TEST","Auto Login");
            startActivity(new Intent(LoginChoose.this, UserDetails.class));
        }
    }

    private void createRequest(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.i("Test","Login with google 2");
                        if (task.isSuccessful()) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Log.i("Test","Login with google 3");
                            startActivity(new Intent(getApplicationContext(), UserDetails.class));
                        } else {
                            Toast.makeText(LoginChoose.this,"Navigate failed", Toast.LENGTH_LONG).show();
                        }
                        pgbLogin.setVisibility(View.GONE);
                    }
                });
    }

    public void txtResetOnClick(View view) {
        EditText resetMail = new EditText(LoginChoose.this);
        AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(LoginChoose.this);
        passwordResetDialog.setTitle("Reset Password?");
        passwordResetDialog.setMessage("Enter your email to receive reset link");
        passwordResetDialog.setView(resetMail);

        passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String mail = resetMail.getText().toString().trim();
                if(!mail.isEmpty()) {
                    firebaseAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(LoginChoose.this, "Reset link send to your email",Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(LoginChoose.this, "Error! Reset link not sent" + e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(LoginChoose.this, "Enter your email to rest the password",Toast.LENGTH_SHORT).show();                }
            }
        });

        passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        passwordResetDialog.create().show();
    }
}