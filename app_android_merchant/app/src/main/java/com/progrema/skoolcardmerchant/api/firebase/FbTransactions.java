package com.progrema.skoolcardmerchant.api.firebase;

import android.content.Context;

public class FbTransactions extends FbBase {

    private OnCompleteListener mInterface;

    private FbTransactions(Context context, OnCompleteListener anInterface) {
        this.mInterface = anInterface;
        this.mContext = context;
    }

    public static FbTransactions build(Context context, OnCompleteListener anInterface) {
        return new FbTransactions(context, anInterface);
    }

    public void fetchTransactions() {
        mInterface.fetchTransactionsComplete("Hello from FBTransaction");
    }

    public interface OnCompleteListener {

        void fetchTransactionsComplete(String transactions);

    }

}
