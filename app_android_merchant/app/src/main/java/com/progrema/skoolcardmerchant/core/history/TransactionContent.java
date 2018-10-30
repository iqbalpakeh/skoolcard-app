package com.progrema.skoolcardmerchant.core.history;

import com.progrema.skoolcardmerchant.api.model.Product;
import com.progrema.skoolcardmerchant.api.model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionContent {


    public static final List<Transaction> ITEMS = new ArrayList<>();

    static {
        for (int i=0; i<10; i++) {
            ITEMS.add(Transaction.create()
                    .setInvoice("20181101-154-023")
                    .setAmount("Rp 50,000.00")
                    .setTimestamp("2018-10-01")
                    .setMerchant("123456")
                    .setConsumer("123456")
                    .setChild("123456")
                    .setState(Transaction.OPEN)
                    .setProducts(new Product[]{
                            Product.create().setName("Biscuit").setPrice("Rp 10,500.00").setPicture(""),
                            Product.create().setName("Biscuit").setPrice("Rp 10,500.00").setPicture(""),
                            Product.create().setName("Biscuit").setPrice("Rp 10,500.00").setPicture("")
                    }));
        }
    }

}
