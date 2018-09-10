package com.example.triumf.flow_sensor_app.API;

import com.example.triumf.flow_sensor_app.Entiry.Users;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserInterface {
    @GET("users")
    Call<List<Users>> getUsers();

    @POST("users")
    Call<JsonObject> createUser(@Body JsonObject user);

    @POST("login")
    Call<JsonObject> login(@Body JsonObject user);

    @POST("login")
    Call<JsonObject> createSensor(@Body JsonObject sensor);



    @PUT("users/{id}")
    Call<JsonObject> createUser(@Path("id") long id, @Body JsonObject user);

    @GET("/users/{userId}")
    Call<List<Users>> getUser(@Path("userId") Integer userId);
}
