package com.meonjicompany.planning.retrofit;

import com.meonjicompany.planning.DTO.UserDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserInformationInterface {
     @GET("/user_access")
     Call<List<UserDTO>> getData (@Query("userId") String id);
}