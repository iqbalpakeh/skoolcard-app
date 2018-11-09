package com.progrema.skoolcardmerchant.api.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class Transaction {

    public static final String OPEN = "open";
    public static final String CLOSE = "close";

    private String invoice;
    private String amount;
    private String timestamp;
    private String merchant;
    private String consumer;
    private String child;
    private String state;
    private ArrayList<Product> products;

    @SuppressWarnings("unused")
    public Transaction() {
    }

    public static Transaction create() {
        return new Transaction();
    }

    public Transaction setInvoice(String invoice) {
        // todo: invoice should be created on cloud function instead of from local device
        this.invoice = invoice;
        return this;
    }

    public Transaction setAmount(String amount) {
        this.amount = amount;
        return this;
    }

    public Transaction setTimestamp(String timestamp) {
        // todo: timestamp should be created on cloud function instead of from local device
        this.timestamp = timestamp;
        return this;
    }

    public Transaction setMerchant(String merchant) {
        this.merchant = merchant;
        return this;
    }

    public Transaction setConsumer(String consumer) {
        this.consumer = consumer;
        return this;
    }

    public Transaction setChild(String child) {
        this.child = child;
        return this;
    }

    public Transaction setState(String state) {
        this.state = state;
        return this;
    }

    public Transaction setProducts(List<Product> products) {
        ArrayList<Product> list = new ArrayList<>();
        for (Product product: products) {
            if (!product.isZero()) {
                list.add(product);
            }
        }
        this.products = list;
        return this;
    }

    public String getInvoice() {
        return invoice;
    }

    public String getAmount() {
        return amount;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getMerchant() {
        return merchant;
    }

    public String getConsumer() {
        return consumer;
    }

    public String getChild() {
        return child;
    }

    public String getState() {
        return state;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public String json() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}
