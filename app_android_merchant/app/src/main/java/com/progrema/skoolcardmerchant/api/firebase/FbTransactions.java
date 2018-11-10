package com.progrema.skoolcardmerchant.api.firebase;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.progrema.skoolcardmerchant.api.model.Transaction;
import com.progrema.skoolcardmerchant.core.history.TransactionContent;

import java.util.List;

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
        List<Transaction> transactions =  TransactionContent.ITEMS;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        mInterface.fetchTransactionsComplete(gson.toJson(transactions));
    }

    public interface OnCompleteListener {

        void fetchTransactionsComplete(String transactions);

    }

}
