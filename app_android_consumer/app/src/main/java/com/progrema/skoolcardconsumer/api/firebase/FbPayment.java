package com.progrema.skoolcardconsumer.api.firebase;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.progrema.skoolcardconsumer.App;
import com.progrema.skoolcardconsumer.api.model.User;

import javax.annotation.Nullable;

public class FbPayment extends FbBase {

    private static final String TAG = "FbPayment";

    private FbPayAble mInterface;

    private FbPayment(Context context, FbPayAble anInterface) {
        this.mInterface = anInterface;
        this.mContext = context;
    }

    public static FbPayment build(Context context, FbPayAble anInterface) {
        return new FbPayment(context, anInterface);
    }

    public void monitorBalance() {
        mDatabase.collection(ROOT_CONSUMERS)
                .document(App.getUID(mContext))
                .addSnapshotListener((AppCompatActivity) mContext, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException e) {

                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    User user = snapshot.toObject(User.class);
                    Log.d(TAG, "User: " + user.json());
                    mInterface.onBalanceChange(user.calcRemainingBalance());
                } else {
                    Log.d(TAG, "Current data: null");
                }
            }
        });
    }

    public interface FbPayAble {

        void onBalanceChange(String newBalance);

    }

}
