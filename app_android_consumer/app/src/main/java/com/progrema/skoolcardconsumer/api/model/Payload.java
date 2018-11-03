package com.progrema.skoolcardconsumer.api.model;

public class Payload {

    private String email;
    private String uid;

    public Payload() {
        // Needed by Firestore server
    }

    public Payload(String email, String uid) {
        this.email = email;
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

}
