package com.progrema.skoolcardmerchant.api.model;

public class Payload {

    private String email;
    private String consumerUid;

    // todo: to add child id here

    public Payload() {
        // Needed by Firestore server
    }

    public Payload(String email, String consumerUid) {
        this.email = email;
        this.consumerUid = consumerUid;
    }

    public String getEmail() {
        return email;
    }

    public String getConsumerUid() {
        return consumerUid;
    }
}
