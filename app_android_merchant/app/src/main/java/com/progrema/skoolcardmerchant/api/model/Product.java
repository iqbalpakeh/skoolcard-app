package com.progrema.skoolcardmerchant.api.model;

public class Product {

    private String name;
    private String price;
    private String picture;

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

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getPicture() {
        return picture;
    }
}
