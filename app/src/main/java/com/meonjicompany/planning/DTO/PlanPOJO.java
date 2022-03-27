package com.meonjicompany.planning.DTO;

import java.util.ArrayList;

public class PlanPOJO {
    private int user_id;
    private String plan_title;
    private String plan_date;
    private ArrayList<Piece> contents = new ArrayList<>();

    public PlanPOJO(int user_id, String plan_title, String plan_date, ArrayList<Piece> contents) {
        this.user_id = user_id;
        this.plan_title = plan_title;
        this.plan_date = plan_date;
        this.contents = contents;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getPlan_title() {
        return plan_title;
    }

    public void setPlan_title(String plan_title) {
        this.plan_title = plan_title;
    }

    public String getPlan_date() {
        return plan_date;
    }

    public void setPlan_date(String plan_date) {
        this.plan_date = plan_date;
    }

    public ArrayList<Piece> getContents() {
        return contents;
    }

    public void setContents(ArrayList<Piece> contents) {
        this.contents = contents;
    }
}
