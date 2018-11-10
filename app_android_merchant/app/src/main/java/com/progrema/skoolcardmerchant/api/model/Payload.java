package com.progrema.skoolcardmerchant.api.model;

public class Payload {

    private String email;
    private String uid;

    // todo: to add child id here

    public Payload() {
        // Needed by Firestore server
    }

    public static Payload create() {
        return new Payload();
    }

    private Payload(String email, String uid) {
        this.email = email;
        this.uid = uid;
    }

    public Payload setEmail(String email) {
        this.email = email;
        return this;
    }

    public Payload setUid(String uid) {
        this.uid = uid;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public String getUid() {
        return uid;
    }
}
