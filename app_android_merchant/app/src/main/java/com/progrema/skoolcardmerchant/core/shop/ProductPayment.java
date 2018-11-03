package com.progrema.skoolcardmerchant.core.shop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.progrema.skoolcardmerchant.R;
import com.progrema.skoolcardmerchant.api.model.Product;
import com.progrema.skoolcardmerchant.core.HomeActivity;
import com.wang.avi.AVLoadingIndicatorView;

import java.math.BigDecimal;

public class ProductPayment extends AppCompatActivity {

    private static final String TAG = "ProductPayment";

    private TextView mTotalPayment;

    private TextView mUserIndicator;

    private Button mActionButton;

    private AVLoadingIndicatorView mWaitingIndicator;

    private ImageView mApprovedIndicator;

    private ImageView mRejectIndicator;

    private Product[] mProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        mUserIndicator = findViewById(R.id.user_message);
        mTotalPayment = findViewById(R.id.total_payment_amount);
        mActionButton = findViewById(R.id.action_button);
        mWaitingIndicator = findViewById(R.id.waiting_indicator);
        mApprovedIndicator = findViewById(R.id.approved_indicator);
        mRejectIndicator = findViewById(R.id.reject_indicator);

        mActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 backHome();
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Gson gson = new Gson();
            mProducts = gson.fromJson(extras.getString("products"), Product[].class);
            mTotalPayment.setText(calculateTotalPayment());
        }

        transactionWaiting();

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

    private void backHome() {
        Intent intent = new Intent(ProductPayment.this, HomeActivity.class);
        startActivity(intent);
    }

    private void transactionWaiting() {
        mWaitingIndicator.setVisibility(View.VISIBLE);
        mApprovedIndicator.setVisibility(View.GONE);
        mRejectIndicator.setVisibility(View.GONE);
        mActionButton.setText("Cancel");
        mUserIndicator.setText("Waiting for tap");
    }

    private void transactionApproved() {
        mWaitingIndicator.setVisibility(View.GONE);
        mApprovedIndicator.setVisibility(View.VISIBLE);
        mRejectIndicator.setVisibility(View.GONE);
        mActionButton.setText("Done");
        mUserIndicator.setText("Transaction approved");
    }

    private void transactionRejected() {
        mWaitingIndicator.setVisibility(View.GONE);
        mApprovedIndicator.setVisibility(View.GONE);
        mRejectIndicator.setVisibility(View.VISIBLE);
        mActionButton.setText("Done");
        mUserIndicator.setText("Transaction rejected");
    }

}
