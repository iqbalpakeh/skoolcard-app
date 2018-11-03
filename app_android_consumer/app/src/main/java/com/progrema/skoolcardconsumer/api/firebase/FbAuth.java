package com.progrema.skoolcardconsumer.api.firebase;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;
import com.progrema.skoolcardconsumer.App;
import com.progrema.skoolcardconsumer.api.model.User;

public class FbAuth extends FbBase {

    /**
     * for debugging purpose
     */
    private static final String LOG_TAG = FbAuth.class.getSimpleName();

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
     * Connect to Firebase to register user if it's not existed yet.
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
                            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            String token = FirebaseInstanceId.getInstance().getToken();
                            App.storeUserData(mContext, email, token, uid);

                            mDatabase.collection(ROOT)
                                    .document(uid)
                                    .set(new User(email, token, uid))
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            showProgress(false);
                                            mInterface.onRegisterSuccess();
                                        }
                                    }). addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(LOG_TAG, "Error adding document", e);
                                    App.clearUserData(mContext);
                                    mInterface.onRegisterFailed();
                                }
                            });
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
                            App.clearUserData(mContext);

                        } else {
                            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            String token = FirebaseInstanceId.getInstance().getToken();

                            Log.w(LOG_TAG, "uid = " +  uid);
                            Log.w(LOG_TAG, "token = " +  token);

                            mDatabase.collection(ROOT).document(uid).update("token", token);
                            App.storeUserData(mContext, email, token, uid);
                            showProgress(false);
                            mInterface.onLoginSuccess();
                        }
                    }
                });
    }

    /**
     * Check if any user signed in. If yes, update local token data. Otherwise, clean local token
     * for security
     *
     * @return true if any user signed in. Otherwise, return false.
     */
    public boolean isUserSignedIn() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            App.storeUserData(mContext, user.getEmail(),
                    FirebaseInstanceId.getInstance().getToken(), user.getUid());
            return true;
        } else {
            App.clearUserData(mContext);
            return false;
        }
    }

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

}
