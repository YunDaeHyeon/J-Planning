package com.meonjicompany.planning.retrofit;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RetrofitAPI {

    @FormUrlEncoded
    @POST("/user/save")
    Call<Message> sendUser(@Field("user_email") String userEmail,
                           @Field("user_nickname") String userNickname);
}
