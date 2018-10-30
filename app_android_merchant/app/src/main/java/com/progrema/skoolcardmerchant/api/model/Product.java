package com.progrema.skoolcardmerchant.api.model;

public class Product {

    private String name;

    private String price;

    private String picture;

    @SuppressWarnings("unused")
    public Product() {
        // Used by Firestore
    }

    public Product(String name, String price, String picture) {
        this.name = name;
        this.price = price;
        this.picture = picture;
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
