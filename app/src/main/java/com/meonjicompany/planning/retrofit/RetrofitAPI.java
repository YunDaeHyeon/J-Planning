package com.meonjicompany.planning.retrofit;

import com.meonjicompany.planning.DTO.PlanPOJO;
import com.meonjicompany.planning.DTO.PlanRoadDTO;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitAPI {

    // 최초 로그인(최초 앱실행) 시 유저 정보 저장
    @FormUrlEncoded
    @POST("/user/save")
    Call<Message> sendUser(@Field("user_email") String userEmail,
                           @Field("user_nickname") String userNickname);

    // 로그인 시 유저의 PK값 불러오기
    @FormUrlEncoded
    @POST("/user/roadUserId")
    Call<Message> roadUserId(@Field("user_email") String userEmail);

    // 저장 버튼 클릭 시 계획 저장
    @POST("/user/savePlan")
    Call<Message> savePlan(@Body PlanPOJO planPOJO);

    // 서버(plan 테이블)에서 계획 불러오기 (제목과 날짜)
    @GET("/user/roadPlan")
    Call<List<PlanRoadDTO>> roadPlan(@Query("user_id") int userId);

}
