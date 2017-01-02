package com.example.dudco.whatsyourname;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dudco on 2017. 1. 2..
 */

public class Util {
    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://iwin247.net:3000")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

}
