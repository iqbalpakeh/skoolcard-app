package com.progrema.skoolcardmerchant.core.history;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.progrema.skoolcardmerchant.R;
import com.progrema.skoolcardmerchant.core.EmptyRecyclerView;
import com.progrema.skoolcardmerchant.core.history.TransactionFragment.OnListFragmentInteractionListener;
import com.progrema.skoolcardmerchant.api.model.Transaction;

import java.util.List;

public class TransactionAdapter extends EmptyRecyclerView.Adapter<TransactionAdapter.ViewHolder> {

    private final List<Transaction> mValues;
    private final OnListFragmentInteractionListener mListener;

    public TransactionAdapter(List<Transaction> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_transaction, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mInvoiceView.setText(mValues.get(position).getInvoice());
        holder.mTimestampView.setText(mValues.get(position).getTimestamp());
        holder.mAmountView.setText(mValues.get(position).getAmount());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public final TextView mInvoiceView;
        public final TextView mTimestampView;
        public final TextView mAmountView;
        public Transaction mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mInvoiceView = view.findViewById(R.id.transaction_invoice);
            mTimestampView = view.findViewById(R.id.transaction_timestamp);
            mAmountView = view.findViewById(R.id.transaction_amount);
        }

        @Override
        public String toString() {
            return super.toString() + " " + mInvoiceView.getText()
                    + " " + mTimestampView.getText() + " " + mAmountView.getText();
        }
    }
}
