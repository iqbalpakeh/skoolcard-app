package com.progrema.skoolcardmerchant.api.firebase;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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

    public void doPayment(String transaction) {
        callDoPayment(transaction).addOnCompleteListener((AppCompatActivity) mContext, new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (task.isSuccessful()) {
                    String result = task.getResult();
                    Log.d("FbPayment", "return from cloud functions = " + result);
                    if (result.equals("TC")) {
                        mInterface.onPaymentApproved();
                    } else if (result.equals("AAC")) {
                        mInterface.onPaymentRejected();
                    } else {
                        mInterface.onPaymentRejected();
                    }
                } else {
                    Log.d("FbPayment", "Cloud functions error");
                    mInterface.onPaymentRejected();
                }
            }
        });
    }

    private Task<String> callDoPayment(String transaction) {
        Map<String, Object> data = new HashMap<>();
        data.put("transaction", transaction);
        return mFunctions.getHttpsCallable("doPayment").call(data)
                .continueWith(new Continuation<HttpsCallableResult, String>() {
                    @Override
                    public String then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                        // This continuation runs on either success or failure, but if the task
                        // has failed then getResult() will throw an Exception which will be
                        // propagated down.
                        Map<String, String> result = (Map<String, String>) task.getResult().getData();
                        Log.d("FbPayment", "result = " + result);
                        return result.get("trans_result");
                    }
                });
    }

    public interface FbPayAble {

        void onPaymentApproved();

        void onPaymentRejected();

    }

}
