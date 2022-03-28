package com.meonjicompany.planning.DTO;

public class RepositoryItemDTO {
    private String planTitle;
    private String planDate;

    public RepositoryItemDTO(String planTitle, String planDate) {
        this.planTitle = planTitle;
        this.planDate = planDate;
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
