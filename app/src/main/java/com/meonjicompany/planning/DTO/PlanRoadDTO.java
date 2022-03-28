package com.meonjicompany.planning.DTO;

import com.google.gson.annotations.SerializedName;

public class PlanRoadDTO {
    @SerializedName("plan_id")
    private int PlanId;
    @SerializedName("plan_title")
    private String planTitle;
    @SerializedName("plan_date")
    private String planDate;

    public int getPlanId() {
        return PlanId;
    }

    public void setPlanId(int planId) {
        PlanId = planId;
    }

    public String getPlanTitle() {
        return planTitle;
    }

    public void setPlanTitle(String planTitle) {
        this.planTitle = planTitle;
    }

    public String getPlanDate() {
        return planDate;
    }

    public void setPlanDate(String planDate) {
        this.planDate = planDate;
    }
}
