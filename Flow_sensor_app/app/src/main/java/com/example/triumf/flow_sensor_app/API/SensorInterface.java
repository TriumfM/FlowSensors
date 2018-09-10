package com.example.triumf.flow_sensor_app.API;

import com.example.triumf.flow_sensor_app.Entiry.SensorData;
import com.example.triumf.flow_sensor_app.Entiry.Sensors;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface SensorInterface {

    @GET("sensors")
    Call<List<Sensors>> getSensors();

//    @GET("sensors/{sensorId}")
//    void getSensors(@Path("sensorId") Integer sensorId);

    @POST("sensors")
    Call<JsonObject> createSensor(@Body JsonObject sensor);

    @PUT("sensors/{id}")
    Call<JsonObject> createSensor(@Path("id") long id, @Body JsonObject sensor);

    @POST("sensors")
    @FormUrlEncoded
    Call<Sensors> createSensor(@Field("input") Integer input,
                               @Field("output") Integer output,
                               @Field("status") Integer statusPin,
                               @Field("name") String name,
                               @Field("type") String type,
                               @Field("diameter") String diameter,
                               @Field("description") String description);


    @GET("/sensorData/{sensorId}")
    Call<List<SensorData>> getSensorData(@Path("sensorId") Integer sensorId);
}
