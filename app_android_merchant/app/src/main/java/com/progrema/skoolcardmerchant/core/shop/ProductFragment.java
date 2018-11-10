package com.progrema.skoolcardmerchant.core.shop;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.progrema.skoolcardmerchant.App;
import com.progrema.skoolcardmerchant.R;
import com.progrema.skoolcardmerchant.api.model.Product;
import com.progrema.skoolcardmerchant.api.model.Transaction;
import com.progrema.skoolcardmerchant.core.HomeActivity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductFragment extends Fragment {

    public static final String TAG = "transaction";

    private int mColumnCount = 2;

    private OnListFragmentInteractionListener mListener;

    private RecyclerView mRecycleView;

    private List<Product> mProducts;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        initProducts();
    }

    private void initProducts() {
        // todo: and create Product creation page
        mProducts = new ArrayList<>();
        mProducts.add(Product.create().setName("Sandwich")
                .setPrice("150").setPicture("dummy.jpg").setNumber("0"));
        mProducts.add(Product.create().setName("Hamburger")
                .setPrice("250").setPicture("dummy.jpg").setNumber("0"));
        mProducts.add(Product.create().setName("Fried Fries")
                .setPrice("350").setPicture("dummy.jpg").setNumber("0"));
        mProducts.add(Product.create().setName("Popcorn")
                .setPrice("400").setPicture("dummy.jpg").setNumber("0"));
        mProducts.add(Product.create().setName("Donut")
                .setPrice("450").setPicture("dummy.jpg").setNumber("0"));
        mProducts.add(Product.create().setName("Chips")
                .setPrice("500").setPicture("dummy.jpg").setNumber("0"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);

        setActionBarTitle(getString(R.string.title_dashboard));

        // todo: Use EmptyRecycleView
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            mRecycleView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                mRecycleView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                mRecycleView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
                mRecycleView.addItemDecoration(new RecyclerView.ItemDecoration() {
                    @Override
                    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

                        int space = ProductFragment.this.getResources()
                                .getDimensionPixelSize(R.dimen.grid_view_spacing);

                        if ((parent.getChildLayoutPosition(view) % 2) == 1) {
                            outRect.right = space;
                            outRect.left = space / 2;
                        } else {
                            outRect.right = space / 2;
                            outRect.left = space;
                        }

                        outRect.bottom = space;
                        if (parent.getChildLayoutPosition(view) < 2) {
                            outRect.top = space;
                        } else {
                            outRect.top = 0;
                        }
                    }
                });
            }
            mRecycleView.setAdapter(new ProductAdapter(mProducts, mListener, this));
        }
        return view;
    }

    protected void setActionBarTitle(String title) {
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_shopping, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.checkout) {
            handleCheckout();
            return true;
        } else if (id == R.id.clear) {
            handleClear();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void handleClear() {
        for (Product product : mProducts) {
            product.clear();
        }
        mRecycleView.getAdapter().notifyDataSetChanged();
    }

    private void handleCheckout() {
        if (anyProducts()) {
            // todo: to add confirmation dialog before continue checkout
            Intent intent = new Intent(getActivity(), ProductPayment.class);
            intent.putExtra(TAG, createTransaction());
            startActivity(intent);
        } else {
            stopUser();
        }
    }

    private String createTransaction() {

        String merchantId = App.getUID(getContext());
        String consumerId = "kjypVYRbNIP6jqGONdDaNDzRNb02"; // todo: get consumerId from NFC tag
        String childId = "xxx"; // todo: get childId from NFC tag

        return Transaction.create()
                .setAmount(calculateTotalPayment())
                .setMerchant(merchantId)
                .setConsumer(consumerId)
                .setChild(childId)
                .setState(Transaction.OPEN)
                .setProducts(mProducts).json();
    }

    private String calculateTotalPayment() {
        BigDecimal total = BigDecimal.ZERO;
        for (Product product : mProducts) {
            Log.d(TAG, product.json());
            total = total.add(new BigDecimal(product.getPrice())
                    .multiply(new BigDecimal(product.getNumber())));
        }
        return total.toString();
    }

    private boolean anyProducts() {
        int total = 0;
        for (Product product : mProducts) {
            total += Integer.valueOf(product.getNumber());
        }
        return total > 0;
    }

    private void stopUser() {
        new AlertDialog.Builder(getContext())
                .setTitle("No product selected")
                .setMessage("Please select any product before checkout")
                .setIcon(R.drawable.ic_warning_orange_24dp)
                .setNegativeButton("Close", null).show();
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Product item);
    }

}
