package com.example.nativeloginpage.activities;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.example.nativeloginpage.App;
import com.example.nativeloginpage.R;
import com.example.nativeloginpage.Tabs;
import com.example.nativeloginpage.services.LoginService;
import com.example.nativeloginpage.utils.Consts;
import com.example.nativeloginpage.utils.KeyboardUtils;
import com.example.nativeloginpage.utils.SharedPrefsHelper;
import com.example.nativeloginpage.utils.ToastUtils;
import com.example.nativeloginpage.utils.ValidationUtils;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.QBEntityCallbackImpl;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.core.helper.Utils;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

import java.util.Objects;

public class LoginActivity extends BaseActivity {
    private final String TAG = LoginActivity.class.getSimpleName();

//    private static final int MIN_LENGTH = 3;

    private EditText userLoginEditText;
    private EditText userFullNameEditText;

    private QBUser userForSave;

    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Objects.requireNonNull(getSupportActionBar()).hide();
        Log.i("TEST2", "Login activity");
        initUI();
        manualAddUser();
    }

    private void initUI() {
        setActionBarTitle(R.string.title_login_activity);
        userLoginEditText = findViewById(R.id.user_login);
        userLoginEditText.addTextChangedListener(new LoginEditTextWatcher(userLoginEditText));

        userFullNameEditText = findViewById(R.id.user_full_name);
        userFullNameEditText.addTextChangedListener(new LoginEditTextWatcher(userFullNameEditText));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_login, menu);
        return true;
    }
    private void manualAddUser(){
        if (ValidationUtils.isLoginValid(this, userLoginEditText) &&
                ValidationUtils.isFullNameValid(this, userFullNameEditText)) {
            hideKeyboard();
            userForSave = createUserWithEnteredData();
            startSignUpNewUser(userForSave);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_login_user_done) {
            if (ValidationUtils.isLoginValid(this, userLoginEditText) &&
                    ValidationUtils.isFullNameValid(this, userFullNameEditText)) {
                hideKeyboard();
                userForSave = createUserWithEnteredData();
                startSignUpNewUser(userForSave);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void hideKeyboard() {
        KeyboardUtils.hideKeyboard(userLoginEditText);
        KeyboardUtils.hideKeyboard(userFullNameEditText);
    }

    private void startSignUpNewUser(final QBUser newUser) {
        Log.d(TAG, "SignUp New User");
        showProgressDialog(R.string.dlg_creating_new_user);
        requestExecutor.signUpNewUser(newUser, new QBEntityCallback<QBUser>() {
                    @Override
                    public void onSuccess(QBUser result, Bundle params) {
                        Log.d("TEST2", "SignUp Successful" + result.getId());
                        Tabs.setProfileChatId(result.getId());
                        saveUserData(newUser);
                        loginToChat(result);
                    }

                    @Override
                    public void onError(QBResponseException e) {
                        Log.d(TAG, "Error SignUp" + e.getMessage());
                        if (e.getHttpStatusCode() == Consts.ERR_LOGIN_ALREADY_TAKEN_HTTP_STATUS) {
                            signInCreatedUser(newUser);
                        } else {
                            hideProgressDialog();
                            ToastUtils.longToast(R.string.sign_up_error);
                        }
                    }
                }
        );
    }

    private void loginToChat(final QBUser qbUser) {
        qbUser.setPassword(App.USER_DEFAULT_PASSWORD);
        userForSave = qbUser;
        startLoginService(qbUser);
    }

    private void saveUserData(QBUser qbUser) {
        SharedPrefsHelper sharedPrefsHelper = SharedPrefsHelper.getInstance();
        sharedPrefsHelper.saveQbUser(qbUser);
    }

    private QBUser createUserWithEnteredData() {
        return createQBUserWithCurrentData(Tabs.getProfileId(),
                Tabs.getProfileName());
//        return createQBUserWithCurrentData(String.valueOf(userLoginEditText.getText()),
//                String.valueOf(userFullNameEditText.getText()));
    }

    private QBUser createQBUserWithCurrentData(String userLogin, String userFullName) {
        QBUser qbUser = null;
        if (!TextUtils.isEmpty(userLogin) && !TextUtils.isEmpty(userFullName)) {
            qbUser = new QBUser();
            qbUser.setLogin(userLogin);
            qbUser.setFullName(userFullName);
            qbUser.setPassword(App.USER_DEFAULT_PASSWORD);
        }
        return qbUser;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Consts.EXTRA_LOGIN_RESULT_CODE) {
            hideProgressDialog();
            boolean isLoginSuccess = data.getBooleanExtra(Consts.EXTRA_LOGIN_RESULT, false);
            String errorMessage = data.getStringExtra(Consts.EXTRA_LOGIN_ERROR_MESSAGE);

            if (isLoginSuccess) {
                saveUserData(userForSave);
                signInCreatedUser(userForSave);
            } else {
                ToastUtils.longToast(getString(R.string.login_chat_login_error) + errorMessage);
                userLoginEditText.setText(userForSave.getLogin());
                userFullNameEditText.setText(userForSave.getFullName());
            }
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void signInCreatedUser(final QBUser qbUser) {
        Log.d(TAG, "SignIn Started");
        requestExecutor.signInUser(qbUser, new QBEntityCallbackImpl<QBUser>() {
            @Override
            public void onSuccess(QBUser user, Bundle params) {
                Log.d(TAG, "SignIn Successful");
                sharedPrefsHelper.saveQbUser(userForSave);
                updateUserOnServer(qbUser);
            }

            @Override
            public void onError(QBResponseException responseException) {
                Log.d(TAG, "Error SignIn" + responseException.getMessage());
                hideProgressDialog();
                ToastUtils.longToast(R.string.sign_in_error);
            }
        });
    }

    private void updateUserOnServer(QBUser user) {
        user.setPassword(null);
        QBUsers.updateUser(user).performAsync(new QBEntityCallback<QBUser>() {
            @Override
            public void onSuccess(QBUser qbUser, Bundle bundle) {
                hideProgressDialog();
                OpponentsActivity.start(LoginActivity.this);
                finish();
            }

            @Override
            public void onError(QBResponseException e) {
                hideProgressDialog();
                ToastUtils.longToast(R.string.update_user_error);
            }
        });
    }

    private void startLoginService(QBUser qbUser) {
        Intent tempIntent = new Intent(this, LoginService.class);
        PendingIntent pendingIntent = createPendingResult(Consts.EXTRA_LOGIN_RESULT_CODE, tempIntent, 0);
        LoginService.start(this, qbUser, pendingIntent);
    }

    private String getCurrentDeviceId() {
        return Utils.generateDeviceId(this);
    }

    private static class LoginEditTextWatcher implements TextWatcher {
        private final EditText editText;

        private LoginEditTextWatcher(EditText editText) {
            this.editText = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            editText.setError(null);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}