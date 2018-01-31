package com.example.ins_02.firechat;

/**
 * Created by ins-02 on 30/1/18.
 */

public class Message {

    private String from;
    private String userName;
    private String message;

    public Message(String userId, String userName, String message) {
        this.from = userId;
        this.userName = userName;
        this.message = message;
    }

    public Message() {
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
