package com.example.scele.movielab.Models;

import com.example.scele.movielab.Constant;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworksUtils {

    public static Retrofit getRetrofit(){
        return new Retrofit.Builder().baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
    }
}
