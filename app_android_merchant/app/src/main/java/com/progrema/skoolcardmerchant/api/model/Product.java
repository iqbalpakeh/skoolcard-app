package com.progrema.skoolcardmerchant.api.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Product {

    private String name;
    private String price;
    private String picture;
    private String number;

    @SuppressWarnings("unused")
    public Product() {
    }

    public static Product create() {
        return new Product();
    }

    public Product setName(String name) {
        this.name = name;
        return this;
    }

    public Product setPrice(String price) {
        this.price = price;
        return this;
    }

    public Product setPicture(String picture) {
        this.picture = picture;
        return this;
    }

    public Product setNumber(String number) {
        this.number = number;
        return this;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getPicture() {
        return picture;
    }

    public String getNumber() {
        return number;
    }

    public void incNumber() {
        int num = Integer.valueOf(this.number);
        this.number = String.valueOf(++num);
    }

    public void decNumber() {
        int num = Integer.valueOf(this.number);
        if (num > 0) this.number = String.valueOf(--num);
    }

    public boolean isZero() {
        int num = Integer.valueOf(this.number);
        if (num == 0) {
            return true;
        } else {
            return false;
        }
    }

    public Product clear() {
        return setNumber("0");
    }

    public String json() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

}
