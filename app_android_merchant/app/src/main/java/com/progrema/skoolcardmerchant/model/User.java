package com.progrema.skoolcardmerchant.model;

public class User {

    private String email;

    private String token;

    public User() {
        // Needed by Firestore server
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
