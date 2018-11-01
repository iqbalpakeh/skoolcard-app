package com.progrema.skoolcardmerchant.api.model;

public class User {

    private String email;
    private String token; // todo: do we need to store this? can just call this FirebaseInstanceId.getInstance().getToken()

    @SuppressWarnings("unused")
    public User() {
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

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }

}
