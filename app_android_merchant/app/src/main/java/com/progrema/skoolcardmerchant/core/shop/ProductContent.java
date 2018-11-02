package com.progrema.skoolcardmerchant.core.shop;

import com.progrema.skoolcardmerchant.api.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductContent {

    public static final List<Product> ITEMS = new ArrayList<>();

    static {
        ITEMS.add(Product.create().setName("Sandwich").setPrice("Rp 10,500.00").setPicture(""));
        ITEMS.add(Product.create().setName("Hamburger").setPrice("Rp 9,500.00").setPicture(""));
        ITEMS.add(Product.create().setName("Fried Fries").setPrice("Rp 8,500.00").setPicture(""));
        ITEMS.add(Product.create().setName("Popcorn").setPrice("Rp 7,500.00").setPicture(""));
        ITEMS.add(Product.create().setName("Donut").setPrice("Rp 6,500.00").setPicture(""));
        ITEMS.add(Product.create().setName("Chips").setPrice("Rp 5,500.00").setPicture(""));
    }

}
