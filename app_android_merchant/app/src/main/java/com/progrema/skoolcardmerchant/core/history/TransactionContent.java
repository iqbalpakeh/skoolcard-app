package com.progrema.skoolcardmerchant.core.history;

import com.progrema.skoolcardmerchant.api.model.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionContent {


    public static final List<Transaction> ITEMS = new ArrayList<Transaction>();

    public static final Map<String, Transaction> ITEM_MAP = new HashMap<String, Transaction>();

    private static final int COUNT = 25;

    static {
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(Transaction item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static Transaction createDummyItem(int position) {
        return new Transaction(String.valueOf(position), "Item " + position, makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

}
