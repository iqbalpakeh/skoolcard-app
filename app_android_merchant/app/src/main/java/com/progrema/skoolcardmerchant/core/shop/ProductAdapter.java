package com.progrema.skoolcardmerchant.core.shop;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.progrema.skoolcardmerchant.R;
import com.progrema.skoolcardmerchant.api.model.Product;
import com.progrema.skoolcardmerchant.core.shop.ProductFragment.OnListFragmentInteractionListener;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private final List<Product> mValues;
    private final OnListFragmentInteractionListener mListener;

    public ProductAdapter(List<Product> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mNameView.setText(holder.mItem.getName());
        holder.mPriceView.setText(holder.mItem.getPrice());
        holder.mNumberView.setText(holder.mItem.getNumber());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });

        holder.mBuyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.mItem.incNumber();
                holder.mNumberView.setText(holder.mItem.getNumber());
            }
        });

        holder.mClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.mItem.decNumber();
                holder.mNumberView.setText(holder.mItem.getNumber());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mNameView;
        public final TextView mPriceView;
        public final TextView mNumberView;
        public final Button mBuyButton;
        public final ImageButton mClearButton;

        public Product mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = (TextView) view.findViewById(R.id.product_name);
            mPriceView = (TextView) view.findViewById(R.id.product_price);
            mNumberView = (TextView) view.findViewById(R.id.number);
            mBuyButton = (Button) view.findViewById(R.id.buy_button);
            mClearButton = (ImageButton) view.findViewById(R.id.clear_button);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNameView.getText() + "'" + mPriceView.getText();
        }
    }
}
