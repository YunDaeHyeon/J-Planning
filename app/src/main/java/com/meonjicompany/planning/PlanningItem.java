package com.meonjicompany.planning;

public class PlanningItem {
    private String date;
    private String title;
    private String contents;

    public PlanningItem(String date, String title, String contents) {
        this.date = date;
        this.title = title;
        this.contents = contents;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
