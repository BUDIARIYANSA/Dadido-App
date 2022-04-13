package com.example.daradiapp;

import com.example.daradiapp.Model.User;

import java.util.ArrayList;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiList {

    @POST("webserver.php")
    Call<ArrayList<User>> getAllUser(@Body RequestBody body);

}
