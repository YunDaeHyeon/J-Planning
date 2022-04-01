package com.meonjicompany.planning.DTO;

public class RepositoryItemDTO {
    private int planId;
    private String planTitle;
    private String planDate;

    public RepositoryItemDTO(int planId, String planTitle, String planDate) {
        this.planId = planId;
        this.planTitle = planTitle;
        this.planDate = planDate;
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
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
