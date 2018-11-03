package com.progrema.skoolcardmerchant.core.shop;

import com.progrema.skoolcardmerchant.api.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductContent {

    // TODO: need to replace the usage of this static class, this could lead to memory leak!!!

    public static final List<Product> ITEMS = new ArrayList<>();

    static {
        ITEMS.add(Product.create().setName("Sandwich")
                .setPrice("150").setPicture("dummy.jpg").setNumber(String.valueOf(0)));
        ITEMS.add(Product.create().setName("Hamburger")
                .setPrice("250").setPicture("dummy.jpg").setNumber(String.valueOf(0)));
        ITEMS.add(Product.create().setName("Fried Fries")
                .setPrice("350").setPicture("dummy.jpg").setNumber(String.valueOf(0)));
        ITEMS.add(Product.create().setName("Popcorn")
                .setPrice("400").setPicture("dummy.jpg").setNumber(String.valueOf(0)));
        ITEMS.add(Product.create().setName("Donut")
                .setPrice("450").setPicture("dummy.jpg").setNumber(String.valueOf(0)));
        ITEMS.add(Product.create().setName("Chips")
                .setPrice("500").setPicture("dummy.jpg").setNumber(String.valueOf(0)));
    }

}
