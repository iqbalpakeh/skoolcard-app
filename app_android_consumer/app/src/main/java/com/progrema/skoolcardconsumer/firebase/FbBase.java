package com.progrema.skoolcardconsumer.firebase;

import android.app.ProgressDialog;
import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FbBase {

    /**
     * Root location of merchant in firebase db
     */
    public static final String ROOT_CONSUMER = "consumers";

    /**
     * Firebase authentication object
     */
    FirebaseAuth mAuth;

    /**
     * Firebase authentication object listener
     */
    FirebaseAuth.AuthStateListener mAuthListener;

    /**
     * Firebase authentication object listener
     */
    DatabaseReference mDatabase;

    /**
     * Application context object
     */
    Context mContext;

    /**
     * Progress dialog view
     */
    private ProgressDialog mProgressDialog;

    /**
     * Default constructor
     */
    public FbBase() {
        this.mAuth = FirebaseAuth.getInstance();
        this.mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    /**
     * Shows the progress UI and hides the login form
     *
     * @param show flag to activate progress ui
     */
    void showProgress(final boolean show) {

        if (show) {
            mProgressDialog = new ProgressDialog(mContext);
            mProgressDialog.setMessage("Accessing server...");
            mProgressDialog.setCancelable(true);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.show();

        } else {
            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }
        }
    }

}
