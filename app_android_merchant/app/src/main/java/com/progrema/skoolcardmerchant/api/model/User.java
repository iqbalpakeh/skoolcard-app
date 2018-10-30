package com.progrema.skoolcardmerchant.api.model;

public class User {

    private String email;

    private String token;

    public User() {
        // Used by Firestore
    }

    public User(String email, String token) {
        this.email = email;
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }

}
