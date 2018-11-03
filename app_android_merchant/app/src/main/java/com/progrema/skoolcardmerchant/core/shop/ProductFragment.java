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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.progrema.skoolcardmerchant.R;
import com.progrema.skoolcardmerchant.api.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductFragment extends Fragment {

    public static final String TAG = "Product";

    private int mColumnCount = 2;

    private OnListFragmentInteractionListener mListener;

    private List<Product> mProducts;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        initProducts();
    }

    private void initProducts() {
        mProducts = new ArrayList<>();
        mProducts.add(Product.create().setName("Sandwich")
                .setPrice("150").setPicture("dummy.jpg").setNumber(String.valueOf(0)));
        mProducts.add(Product.create().setName("Hamburger")
                .setPrice("250").setPicture("dummy.jpg").setNumber(String.valueOf(0)));
        mProducts.add(Product.create().setName("Fried Fries")
                .setPrice("350").setPicture("dummy.jpg").setNumber(String.valueOf(0)));
        mProducts.add(Product.create().setName("Popcorn")
                .setPrice("400").setPicture("dummy.jpg").setNumber(String.valueOf(0)));
        mProducts.add(Product.create().setName("Donut")
                .setPrice("450").setPicture("dummy.jpg").setNumber(String.valueOf(0)));
        mProducts.add(Product.create().setName("Chips")
                .setPrice("500").setPicture("dummy.jpg").setNumber(String.valueOf(0)));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
                recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
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
            recyclerView.setAdapter(new ProductAdapter(mProducts, mListener));
        }
        return view;
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
        if(id == R.id.checkout) {
            handleCheckout();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void handleCheckout() {
        if (anyProducts()) {
            // todo: to add confirmation dialog before continue checkout
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Intent intent = new Intent(getActivity(), ProductPayment.class);
            intent.putExtra("products", gson.toJson(mProducts));
            startActivity(intent);
        } else {
            stopUser();
        }
    }

    private boolean anyProducts() {
        int total = 0;
        for (Product product: mProducts) {
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
