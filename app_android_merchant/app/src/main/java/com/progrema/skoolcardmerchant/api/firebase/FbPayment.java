package com.progrema.skoolcardmerchant.api.firebase;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.Continuation;
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

    public void doPayment() {
        // dummy process

        Map<String, Object> data = new HashMap<>();
        data.put("text", "HELLO FROM ANDROID");
        data.put("push", true);

        mFunctions
                .getHttpsCallable("doPayment")
                .call(data)
                .continueWith(new Continuation<HttpsCallableResult, String>() {
                    @Override
                    public String then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                        // This continuation runs on either success or failure, but if the task
                        // has failed then getResult() will throw an Exception which will be
                        // propagated down.

                        Log.d("FbPayment", "return from cloud functions!");
                        mInterface.onPaymentApproved();

                        String result = (String) task.getResult().getData();
                        Log.d("FbPayment", "result = " + result);
                        return result;
                    }
                });

//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            public void run() {
//                mInterface.onPaymentApproved();
//            }
//        }, 5000);
    }

    public interface FbPayAble {

        void onPaymentApproved();

        void onPaymentRejected();

    }

}
