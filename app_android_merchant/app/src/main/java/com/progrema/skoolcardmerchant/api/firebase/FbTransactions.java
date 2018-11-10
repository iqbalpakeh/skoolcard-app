package com.progrema.skoolcardmerchant.api.firebase;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.progrema.skoolcardmerchant.App;
import com.progrema.skoolcardmerchant.api.model.Transaction;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class FbTransactions extends FbBase {

    private final String TAG = "FbTransactions";

    private OnCompleteListener mInterface;

    private FbTransactions(Context context, OnCompleteListener anInterface) {
        this.mInterface = anInterface;
        this.mContext = context;
    }

    public static FbTransactions build(Context context, OnCompleteListener anInterface) {
        return new FbTransactions(context, anInterface);
    }

    public void fetchTransactions() {
        showProgress(true);
        mDatabase.collection(ROOT_MERCHANTS)
                .document(App.getUID(mContext))
                .collection("transactions")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .limit(10)
                .addSnapshotListener((AppCompatActivity) mContext,
                        new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                                showProgress(false);
                                if (e != null) {
                                    Log.w(TAG, "Listen failed.", e);
                                    return;
                                }
                                if (snapshot != null) {
                                    Log.d(TAG, "snapshot: " + snapshot.getDocuments());
                                    ArrayList<Transaction> transactions = new ArrayList<>();
                                    for (DocumentChange doc : snapshot.getDocumentChanges()) {
                                        Transaction transaction = doc.getDocument().toObject(Transaction.class);
                                        transactions.add(transaction);
                                    }
                                    Gson gson = new Gson();
                                    mInterface.fetchTransactionsComplete(gson.toJson(transactions));
                                } else {
                                    Log.d(TAG, "Current data: null");
                                }
                            }
                        });

    }

    public interface OnCompleteListener {

        void fetchTransactionsComplete(String transactions);

    }

}
