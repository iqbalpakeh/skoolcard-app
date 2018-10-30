package com.progrema.skoolcardmerchant.api.model;

public class Transaction {

    public final String id;
    public final String content;
    public final String details;

    public Transaction(String id, String content, String details) {
        this.id = id;
        this.content = content;
        this.details = details;
    }

}
