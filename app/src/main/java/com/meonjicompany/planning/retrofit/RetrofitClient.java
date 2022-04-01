package com.meonjicompany.planning.retrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static RetrofitClient instance = null;
    private static RetrofitAPI retrofitAPI;
    private final static String BASE_URL="http://192.168.0.4:8080/";

    private RetrofitClient(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30,TimeUnit.SECONDS)
                .writeTimeout(15,TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitAPI = retrofit.create(RetrofitAPI.class);
    }

    public static RetrofitClient getInstance(){
        if(instance == null){
            instance = new RetrofitClient();
        }
        return instance;
    }
    public static RetrofitAPI getRetrofitAPI(){
        return retrofitAPI;
    }
}
