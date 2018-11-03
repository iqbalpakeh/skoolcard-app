package com.progrema.skoolcardconsumer.api.model;

public class User {

    private String email;
    private String token;
    private String uid;

    public User() {
        // Needed by Firestore server
    }

    public User(String email, String token, String uid) {
        this.email = email;
        this.token = token;
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }

}
