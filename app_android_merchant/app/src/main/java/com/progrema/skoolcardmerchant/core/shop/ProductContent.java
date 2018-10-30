package com.progrema.skoolcardmerchant.core.shop;

import com.progrema.skoolcardmerchant.api.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductContent {

    public static final List<Product> ITEMS = new ArrayList<>();

    static {
        ITEMS.add(new Product("Biscuit", "Rp 10,500.00", ""));
        ITEMS.add(new Product("Biscuit", "Rp 11,000.00", ""));
        ITEMS.add(new Product("Biscuit", "Rp 13,500.00", ""));
        ITEMS.add(new Product("Biscuit", "Rp 9,500.00", ""));
        ITEMS.add(new Product("Biscuit", "Rp 8,500.00", ""));
        ITEMS.add(new Product("Biscuit", "Rp 7,500.00", ""));
    }

}
