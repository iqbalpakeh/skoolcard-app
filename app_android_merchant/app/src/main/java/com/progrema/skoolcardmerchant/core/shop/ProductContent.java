package com.progrema.skoolcardmerchant.core.shop;

import com.progrema.skoolcardmerchant.api.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductContent {

    public static final List<Product> ITEMS = new ArrayList<>();

    static {
        ITEMS.add(Product.newInstance().setName("Biscuit").setPrice("Rp 10,500.00").setPicture(""));
        ITEMS.add(Product.newInstance().setName("Biscuit").setPrice("Rp 10,500.00").setPicture(""));
        ITEMS.add(Product.newInstance().setName("Biscuit").setPrice("Rp 10,500.00").setPicture(""));
        ITEMS.add(Product.newInstance().setName("Biscuit").setPrice("Rp 10,500.00").setPicture(""));
        ITEMS.add(Product.newInstance().setName("Biscuit").setPrice("Rp 10,500.00").setPicture(""));
        ITEMS.add(Product.newInstance().setName("Biscuit").setPrice("Rp 10,500.00").setPicture(""));
    }

}
