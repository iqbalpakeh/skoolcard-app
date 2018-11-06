package com.progrema.skoolcardmerchant.api.firebase;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.functions.HttpsCallableResult;

import java.util.HashMap;
import java.util.Map;

public class FbPayment extends FbBase {

    private FbPayAble mInterface;

    private FbPayment(Context context, FbPayAble anInterface) {
        this.mInterface = anInterface;
        this.mContext = context;
    }

    public static FbPayment build(Context context, FbPayAble anInterface) {
        return new FbPayment(context, anInterface);
    }

    public void doPayment(String uid, String amount) {
        callDoPayment(uid, amount).addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (task.isSuccessful()) {
                    String result = task.getResult();
                    Log.d("FbPayment", "return from cloud functions = " + result);
                    mInterface.onPaymentApproved();
                } else {
                    Log.d("FbPayment", "Cloud functions error");
                    mInterface.onPaymentRejected();
                }
            }
        });
    }

    private Task<String> callDoPayment(String uid, String amount) {
        Map<String, Object> data = new HashMap<>();
        data.put("uid", uid);
        data.put("amount", amount);
        return mFunctions
                .getHttpsCallable("doPayment")
                .call(data)
                .continueWith(new Continuation<HttpsCallableResult, String>() {
                    @Override
                    public String then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                        // This continuation runs on either success or failure, but if the task
                        // has failed then getResult() will throw an Exception which will be
                        // propagated down.
                        Map<String, String> result = (Map<String, String>) task.getResult().getData();
                        Log.d("FbPayment", "result = " + result);
                        return result.toString();
                    }
                });
    }

    private void dummyProcess() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                mInterface.onPaymentApproved();
            }
        }, 5000);
    }

    public interface FbPayAble {

        void onPaymentApproved();

        void onPaymentRejected();

    }

}
