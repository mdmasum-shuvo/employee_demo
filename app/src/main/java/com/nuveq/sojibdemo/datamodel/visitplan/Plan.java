package com.nuveq.sojibdemo.datamodel.visitplan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Plan {
    @SerializedName("visitarea")
    @Expose
    private String visitarea;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("status")
    @Expose
    private String status;

    public String getVisitarea() {
        return visitarea;
    }

    public void setVisitarea(String visitarea) {
        this.visitarea = visitarea;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}