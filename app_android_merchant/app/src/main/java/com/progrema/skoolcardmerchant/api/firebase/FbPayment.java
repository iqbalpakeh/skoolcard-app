package com.progrema.skoolcardmerchant.api.firebase;

import android.content.Context;
import android.os.Handler;

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
