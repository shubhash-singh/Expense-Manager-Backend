package com.example.expensetracker.dto;

public class LoginResponse {

    private String message;
    private String uid;

    public LoginResponse() {
    }

    public LoginResponse(String message, String uid) {
        this.message = message;
        this.uid = uid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}

