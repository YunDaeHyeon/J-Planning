package com.meonjicompany.planning.DTO;

public class PlanningItemDTO {
    private String date;
    private String contents;

    public PlanningItemDTO(String date, String contents) {
        this.date = date;
        this.contents = contents;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}