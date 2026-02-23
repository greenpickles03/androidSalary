package com.example.myapplication.DTO;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    private String message;

    @SerializedName("Details")
    private User details;

    public String getMessage() {
        return message;
    }

    public User getDetails() {
        return details;
    }
}