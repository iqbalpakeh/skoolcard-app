package com.progrema.skoolcardconsumer.firebase;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FbAuth extends FbBase {

    /**
     * for debugging purpose
     */
    private static final String LOG_TAG = FbAuth.class.getSimpleName();

    /**
     * Interface to be implemented
     */
    public interface FbAuthAble {

        /**
         * Call back when registration is success
         */
        void onRegisterSuccess();

        /**
         * Call back when registration is failed
         */
        void onRegisterFailed();

        /**
         * Call back when login is success
         */
        void onLoginSuccess();

        /**
         * Call back when login is failed
         */
        void onLoginFailed();

    }

    /**
     * Interface to be implemented in activity class
     */
    private FbAuthAble mInterface;

    /**
     * Constructor of FbAuth
     *
     * @param context     of application
     * @param anInterface of user authentication
     */
    private FbAuth(Context context, FbAuthAble anInterface) {
        this.mInterface = anInterface;
        this.mContext = context;
        this.mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(LOG_TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    Log.d(LOG_TAG, "onAuthStateChanged:signed_out");
                }
            }
        };
    }

    /**
     * Build FbAuth object
     *
     * @param context     of application
     * @param anInterface to be implemented
     * @return FbAuth object
     */
    public static FbAuth build(Context context, FbAuthAble anInterface) {
        return new FbAuth(context, anInterface);
    }

    /**
     * Add authentication listener to firebase authentication object
     */
    public void addAuthListener() {
        mAuth.addAuthStateListener(mAuthListener);
    }

    /**
     * Remove authentication listener from firebase authentication object
     */
    public void removeAuthListener() {
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    /**
     * Connect to Firebase to register user if it's not existed yet. If user is
     * already register, than try to login.
     *
     * @param email    of user
     * @param password of user
     */
    public void doRegister(final String email, final String password) {
        showProgress(true);
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener((Activity) mContext,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(LOG_TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                        if (!task.isSuccessful()) {
                            showProgress(false);
                            mInterface.onRegisterFailed();
                        } else {
                            showProgress(false);
                            mInterface.onRegisterSuccess();
                            // String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            // String token = FirebaseInstanceId.getInstance().getToken();
                            // AppSharedPref.storeUserData(mContext, email, token, uid);
                            // mDatabase.child(ROOT_CONSUMER).child(uid).setValue(new User(email, token))
                            //         .addOnCompleteListener((Activity) mContext, new OnCompleteListener<Void>() {
                            //             @Override
                            //             public void onComplete(@NonNull Task<Void> task) {
                            //                 if (task.isSuccessful()) {
                            //                     mInterface.onRegisterSuccess();
                            //                 } else {
                            //                     Log.w(LOG_TAG, "createNewUser:failed", task.getException());
                            //                 }
                            //             }
                            //         });
                        }
                    }
                });
    }

    /**
     * Connect to firebase to login existing user as attempt to register the user
     * was already failed
     *
     * @param email    of user
     * @param password of user
     */
    public void doLogin(final String email, final String password) {
        showProgress(true);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener((Activity) mContext,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(LOG_TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                        if (!task.isSuccessful()) {
                            Log.w(LOG_TAG, "signInWithEmail:failed", task.getException());
                            showProgress(false);
                            mInterface.onLoginFailed();
                        } else {
                            // String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            // String token = FirebaseInstanceId.getInstance().getToken();
                            // mDatabase.child(FbBase.ROOT_CONSUMER).child(uid).child("token").setValue(token);
                            // AppSharedPref.storeUserData(mContext, email, token, uid);
                            showProgress(false);
                            mInterface.onLoginSuccess();
                        }
                    }
                });
    }

}
