package com.progrema.skoolcardmerchant.api.firebase;

import android.app.ProgressDialog;
import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class FbBase {

    /**
     * Root location of merchant in firebase db
     */
    public static final String ROOT = "merchants";

    /**
     * Firebase authentication object
     */
    FirebaseAuth mAuth;

    /**
     * Firebase authentication object listener
     */
    FirebaseAuth.AuthStateListener mAuthListener;

    /**
     * Firebase firestore object
     */
    FirebaseFirestore mDatabase;

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
        this.mDatabase = FirebaseFirestore.getInstance();
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
