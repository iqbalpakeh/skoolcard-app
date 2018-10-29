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
import com.google.firebase.iid.FirebaseInstanceId;
import com.progrema.skoolcardconsumer.AppSharedPref;
import com.progrema.skoolcardconsumer.model.User;

public class FbUserAuth extends FbContract {

    /**
     * for debugging purpose
     */
    private static final String LOG_TAG = FbUserAuth.class.getSimpleName();

    /**
     * Interface to be implemented
     */
    public interface FbUserAuthAble {

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

        /**
         * Call back when user signed out
         */
        void onUserSignedOut();
    }

    /**
     * Interface to be implemented in activity class
     */
    private FbUserAuthAble mInterface;

    /**
     * Constructor of FbUserAuth
     *
     * @param context     of application
     * @param anInterface of user authentication
     */
    private FbUserAuth(Context context, FbUserAuthAble anInterface) {
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
                    mInterface.onUserSignedOut();
                }
            }
        };
    }

    /**
     * Build FbUserAuth object
     *
     * @param context     of application
     * @param anInterface to be implemented
     * @return FbUserAuth object
     */
    public static FbUserAuth build(Context context, FbUserAuthAble anInterface) {
        return new FbUserAuth(context, anInterface);
    }

    /**
     * Connect to Firebase to register user if it's not existed yet. If user is
     * already register, than try to login.
     *
     * @param email    of user
     * @param password of user
     */
    public void register(final String email, final String password) {
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
                            createNewUser(email);
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
    public void login(final String email, final String password) {
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
                            userLogin(email);
                        }
                    }
                });
    }

    /**
     * Login user to firebase server
     *
     * @param email of user
     */
    private void userLogin(String email) {
//        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        String token = FirebaseInstanceId.getInstance().getToken();
//        mDatabase.child(FbContract.ROOT_CONSUMER).child(uid).child("token").setValue(token);
//        AppSharedPref.storeUserData(mContext, email, token, uid);
        showProgress(false);
        mInterface.onLoginSuccess();
    }

    /**
     * Create new user data in Firebase real time data base
     *
     * @param email of user
     */
    private void createNewUser(final String email) {
        showProgress(false);
        mInterface.onRegisterSuccess();
//        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        String token = FirebaseInstanceId.getInstance().getToken();
//        AppSharedPref.storeUserData(mContext, email, token, uid);
//        mDatabase.child(ROOT_CONSUMER).child(uid).setValue(new User(email, token))
//                .addOnCompleteListener((Activity) mContext, new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            mInterface.onRegisterSuccess();
//                        } else {
//                            Log.w(LOG_TAG, "createNewUser:failed", task.getException());
//                        }
//                    }
//                });
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


}
