package com.example.project_android.Retrofit;

import com.example.project_android.Config.IP;
import com.google.gson.Gson;

import com.google.gson.GsonBuilder;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.example.project_android.Retrofit.*;

public class RetrofitService {


    public APIUser getApiService() {
        return retrofit.create(APIUser.class);
    }


    private static final String BASE_URL = IP.network;
    private static Retrofit retrofit = null;

    public RetrofitService() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}
