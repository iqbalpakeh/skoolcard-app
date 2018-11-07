package com.progrema.skoolcardconsumer.core.auth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.progrema.skoolcardconsumer.core.HomeActivity;
import com.progrema.skoolcardconsumer.core.NfcActivity;
import com.progrema.skoolcardconsumer.R;
import com.progrema.skoolcardconsumer.api.firebase.FbAuth;

public class RegisterActivity extends AppCompatActivity implements FbAuth.FbAuthAble {

    /**
     * Object handling fb user authentication
     */
    private FbAuth mFbAuth;

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
        setContentView(R.layout.activity_register);

        mFbAuth = FbAuth.build(this, this);
        mEmailView = findViewById(R.id.email);
        mPasswordView = findViewById(R.id.password);

        Button loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        Button registerButton = findViewById(R.id.registration_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptRegister();
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        mFbAuth.addAuthListener();
    }

    @Override
    public void onStop() {
        super.onStop();
        mFbAuth.removeAuthListener();
    }

    @Override
    public void onRegisterSuccess() {
        Toast.makeText(this, "Register success", Toast.LENGTH_SHORT).show();
        // startActivity(new Intent(this, NfcActivity.class));
        startActivity(new Intent(this, HomeActivity.class));
    }

    @Override
    public void onRegisterFailed() {
        Toast.makeText(this, "Register Failed", Toast.LENGTH_SHORT).show();
        mPasswordView.setError(getString(R.string.error_incorrect_password));
        mPasswordView.requestFocus();
    }

    @Override
    public void onLoginSuccess() {
        throw new UnsupportedOperationException("");
    }

    @Override
    public void onLoginFailed() {
        throw new UnsupportedOperationException("");
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
     * Attempts to register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptRegister() {

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
            mFbAuth.doRegister(email, password);
        }
    }
}
