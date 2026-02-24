package com.example.myapplication.Service;

import com.example.myapplication.DTO.LoginRequest;
import com.example.myapplication.DTO.LoginResponse;
import com.example.myapplication.DTO.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @POST("api/users/login")
    Call<LoginResponse> getByEmailAndPassword(@Body LoginRequest loginRequest);
    @GET("api/users/all")
    Call<List<User>> getAllUsers();

    @POST("api/users/create")
    Call<LoginResponse> createUser(@Body User user);
}
