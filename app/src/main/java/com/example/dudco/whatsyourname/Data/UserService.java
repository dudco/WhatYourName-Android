package com.example.dudco.whatsyourname.Data;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by dudco on 2017. 1. 2..
 */

public interface UserService {
    @FormUrlEncoded
    @POST("/login")
    Call<UserData> login(@Field("id")String id, @Field("pass")String pass);

    @FormUrlEncoded
    @POST("/reg")
    Call<UserData> register(@Field("id")String id, @Field("pass")String pass, @Field("name")String name, @Field("phone")String phone, @Field("birth")String birth);
}
