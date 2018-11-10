package com.progrema.skoolcardmerchant.core.history;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.progrema.skoolcardmerchant.R;
import com.progrema.skoolcardmerchant.api.firebase.FbTransactions;
import com.progrema.skoolcardmerchant.api.model.Transaction;
import com.progrema.skoolcardmerchant.core.EmptyRecyclerView;
import com.progrema.skoolcardmerchant.core.HomeActivity;

import java.util.ArrayList;
import java.util.List;

public class TransactionFragment extends Fragment implements FbTransactions.OnCompleteListener {

    public static final String TAG = "TransactionFragment";

    private static final String ARG_COLUMN_COUNT = "column-count";

    private int mColumnCount = 1;

    private OnListFragmentInteractionListener mListener;

    private List<Transaction> mTransactions;

    private FbTransactions mFbTransactions;

    private EmptyRecyclerView mRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTransactions = new ArrayList<>();
        mFbTransactions = FbTransactions.build(getContext(), this);
        mFbTransactions.fetchTransactions();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transaction_list, container, false);

        setActionBarTitle(getString(R.string.title_transaction_history));

        mRecyclerView = view.findViewById(R.id.list);
        if (mColumnCount <= 1) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        } else {
            mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), mColumnCount));
        }
        mRecyclerView.setEmptyView(view.findViewById(R.id.empty_view));
        mRecyclerView.setAdapter(new TransactionAdapter(mTransactions, mListener));

        return view;
    }

    private void setActionBarTitle(String title) {
        ((HomeActivity) getActivity()).getSupportActionBar().setTitle(title);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Transaction item);
    }

    @Override
    public void fetchTransactionsComplete(String transactions) {
        Log.d(TAG, "transactions = " + transactions);
        Gson gson = new Gson();
        Transaction[] datas = gson.fromJson(transactions, Transaction[].class);
        for (Transaction transaction : datas) {
            mTransactions.add(transaction);
        }
        if (mRecyclerView != null)
            mRecyclerView.getAdapter().notifyDataSetChanged();
    }
}
