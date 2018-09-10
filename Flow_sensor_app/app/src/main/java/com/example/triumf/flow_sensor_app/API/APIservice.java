package com.example.triumf.flow_sensor_app.API;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIservice {

    public static final String BASE_URL = "http://192.168.2.200:5000/";

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static <S> S createService(Class<S> serviceClass) {

    Gson gson = new GsonBuilder()
        .setLenient()
        .create();


    Retrofit builder = new Retrofit.Builder()
         .baseUrl(BASE_URL)
         .client(httpClient.build())
         .addConverterFactory(GsonConverterFactory.create(gson))
         .build();

        return builder.create(serviceClass);
    }
}
