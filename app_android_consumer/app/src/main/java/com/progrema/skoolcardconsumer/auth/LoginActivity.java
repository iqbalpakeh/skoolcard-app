package com.progrema.skoolcardconsumer.auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.progrema.skoolcardconsumer.HomeActivity;
import com.progrema.skoolcardconsumer.R;
import com.progrema.skoolcardconsumer.firebase.FbUserAuth;

public class LoginActivity extends AppCompatActivity implements FbUserAuth.FbUserAuthAble {

    /**
     * Provide this class filter for debugging purpose
     */
    private static final String LOG_TAG = LoginActivity.class.getSimpleName();

    /**
     * Object handling fb user authentication
     */
    private FbUserAuth mFbUserAuth;

    /**
     * View contain email information
     */
    private AutoCompleteTextView mEmailView;

    /**
     * View contain password information
     */
    private EditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFbUserAuth = FbUserAuth.build(this, this);
        mEmailView = findViewById(R.id.email);
        mPasswordView = findViewById(R.id.password);

        Button registrationButton = findViewById(R.id.registration_button);
        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        Button loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        mFbUserAuth.addAuthListener();
    }

    @Override
    public void onStop() {
        super.onStop();
        mFbUserAuth.removeAuthListener();
    }

    @Override
    public void onRegisterSuccess() {
        Toast.makeText(this, "New user is created", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
    }

    @Override
    public void onRegisterFailed() {
        Log.d(LOG_TAG, "onRegisterFailed()");
    }

    @Override
    public void onLoginSuccess() {
        Toast.makeText(this, "Login success", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
    }

    @Override
    public void onLoginFailed() {
        Log.d(LOG_TAG, "onLoginFailed()");
        Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show();
        mPasswordView.setError(getString(R.string.error_incorrect_password));
        mPasswordView.requestFocus();
    }

    @Override
    public void onUserSignedIn() {

    }

    @Override
    public void onUserSignedOut() {

    }

    /**
     * Check email validity
     *
     * @param email of user
     * @return true if correct. Otherwise, return false.
     */
    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    /**
     * Check password quality
     *
     * @param password of user
     * @return true if correct. Otherwise, return false
     */
    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        mEmailView.setError(null);
        mPasswordView.setError(null);

        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error, don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            mFbUserAuth.loginOrRegister(email, password);
        }
    }

}
