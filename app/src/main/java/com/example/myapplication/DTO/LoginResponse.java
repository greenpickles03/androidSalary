package com.example.myapplication.DTO;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    private String message;
    private String token;

    @SerializedName("user")
    private User details;

    public String getMessage() {
        return message;
    }

    public String getToken(){
        return token;
    }

    public User getDetails() {
        return details;
    }
}