package com.meonjicompany.planning.retrofit;

import com.meonjicompany.planning.DTO.PlanPOJO;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RetrofitAPI {

    @FormUrlEncoded
    @POST("/user/save")
    Call<Message> sendUser(@Field("user_email") String userEmail,
                           @Field("user_nickname") String userNickname);

    @FormUrlEncoded
    @POST("/user/roadUserId")
    Call<Message> roadUserId(@Field("user_email") String userEmail);

    @POST("/user/savePlan")
    Call<Message> savePlan(@Body PlanPOJO planPOJO);
}
