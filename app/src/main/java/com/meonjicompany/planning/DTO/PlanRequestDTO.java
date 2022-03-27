package com.meonjicompany.planning.DTO;

public class PlanRequestDTO {
    private int userId;
    private String planTitle;
    private String planDate;

    public PlanRequestDTO(int userId, String planTitle, String planDate) {
        this.userId = userId;
        this.planTitle = planTitle;
        this.planDate = planDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
