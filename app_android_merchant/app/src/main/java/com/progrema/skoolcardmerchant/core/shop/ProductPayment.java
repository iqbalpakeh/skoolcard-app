package com.progrema.skoolcardmerchant.core.shop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.progrema.skoolcardmerchant.R;
import com.progrema.skoolcardmerchant.api.model.Product;
import com.progrema.skoolcardmerchant.core.HomeActivity;

import java.math.BigDecimal;

public class ProductPayment extends AppCompatActivity {

    private static final String TAG = "ProductPayment";

    private TextView mTotalPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Button cancelButton = findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductPayment.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        mTotalPayment = findViewById(R.id.total_payment_amount);
        mTotalPayment.setText(calculateTotalPayment());

    }

    private String calculateTotalPayment() {
        BigDecimal total = BigDecimal.ZERO;
        for (Product product : ProductContent.ITEMS) {
            Log.d(TAG, product.json());
            total = total.add(new BigDecimal(product.getPrice())
                    .multiply(new BigDecimal(product.getNumber())));
        }
        return total.toString();
    }

}
