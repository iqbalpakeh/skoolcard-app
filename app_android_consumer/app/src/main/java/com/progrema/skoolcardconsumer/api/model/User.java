package com.progrema.skoolcardconsumer.api.model;

public class User {

    // Consumer:
    // - Name
    // - Address
    // - SchoolID
    // - Phone
    // - Balance
    // - Account Number
    // - Child[]
    // - Transactions[]

    private String email;
    private String token;
    private String uid;
    private String limit;
    private String balance;

    public User() {
        // Needed by Firestore server
    }

    public static User create() {
        return new User();
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public User setToken(String token) {
        this.token = token;
        return this;
    }

    public User setUid(String uid) {
        this.uid = uid;
        return this;
    }

    public User setLimit(String limit) {
        this.limit = limit;
        return this;
    }

    public User setBalance(String balance) {
        this.balance = balance;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }

    public String getUid() {
        return uid;
    }

    public String getLimit() {
        return limit;
    }

    public String getBalance() {
        return balance;
    }

}
