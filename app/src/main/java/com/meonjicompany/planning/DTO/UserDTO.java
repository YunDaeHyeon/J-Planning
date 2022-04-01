package com.meonjicompany.planning.DTO;

import com.google.gson.annotations.SerializedName;

public class UserDTO {
    @SerializedName("userId")
    private int userId;
    @SerializedName("userEmail")
    private String userEmail;
    @SerializedName("userNickname")
    private String userNickname;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }
}
