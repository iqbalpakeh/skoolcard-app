package com.progrema.skoolcardmerchant.core.shop;

import com.progrema.skoolcardmerchant.api.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductContent {

    public static final List<Product> ITEMS = new ArrayList<>();

    static {
        ITEMS.add(new Product("Biscuit", "Rp 1,500.00", ""));
        ITEMS.add(new Product("Biscuit", "Rp 1,500.00", ""));
        ITEMS.add(new Product("Biscuit", "Rp 1,500.00", ""));
        ITEMS.add(new Product("Biscuit", "Rp 1,500.00", ""));
    }

}
