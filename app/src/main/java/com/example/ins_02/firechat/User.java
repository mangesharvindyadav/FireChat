package com.example.ins_02.firechat;

/**
 * Created by ins-02 on 29/1/18.
 */

public class User {

    private String name;
    private String email;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    private String userId;


    public User() {
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public User(String name, String email, String userId) {
        this.name = name;
        this.email = email;
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
