package com.progrema.skoolcardconsumer.api.firebase;

import android.content.Context;

public class FbPayment extends FbBase {

    private FbPayAble mInterface;

    private FbPayment(Context context, FbPayAble anInterface) {
        this.mInterface = anInterface;
        this.mContext = context;
        // monitorBalance();
    }

    public static FbPayment build(Context context, FbPayAble anInterface) {
        return new FbPayment(context, anInterface);
    }

    private void monitorBalance() {
        // todo: implement firestore method here
        mInterface.onBalanceChange("2500");
    }

    public interface FbPayAble {

        void onBalanceChange(String newBalance);

    }

}
