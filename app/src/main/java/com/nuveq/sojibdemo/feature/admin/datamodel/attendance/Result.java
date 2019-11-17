package com.nuveq.sojibdemo.feature.admin.datamodel.attendance;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("empid")
    @Expose
    private Integer empid;
    @SerializedName("marketingCode")
    @Expose
    private String marketingCode;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("shift")
    @Expose
    private String shift;
    @SerializedName("checkInTime")
    @Expose
    private String checkInTime;
    @SerializedName("checkOutTime")
    @Expose
    private String checkOutTime;
    @SerializedName("checkInLocation")
    @Expose
    private String checkInLocation;
    @SerializedName("checkInRemarks")
    @Expose
    private String checkInRemarks;
    @SerializedName("checkOutLocation")
    @Expose
    private String checkOutLocation;
    @SerializedName("checkOutRemarks")
    @Expose
    private String checkOutRemarks;

    public Integer getEmpid() {
        return empid;
    }

    public void setEmpid(Integer empid) {
        this.empid = empid;
    }

    public String getMarketingCode() {
        return marketingCode;
    }

    public void setMarketingCode(String marketingCode) {
        this.marketingCode = marketingCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(String checkInTime) {
        this.checkInTime = checkInTime;
    }

    public String getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(String checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public String getCheckInLocation() {
        return checkInLocation;
    }

    public void setCheckInLocation(String checkInLocation) {
        this.checkInLocation = checkInLocation;
    }

    public String getCheckInRemarks() {
        return checkInRemarks;
    }

    public void setCheckInRemarks(String checkInRemarks) {
        this.checkInRemarks = checkInRemarks;
    }

    public String getCheckOutLocation() {
        return checkOutLocation;
    }

    public void setCheckOutLocation(String checkOutLocation) {
        this.checkOutLocation = checkOutLocation;
    }

    public String getCheckOutRemarks() {
        return checkOutRemarks;
    }

    public void setCheckOutRemarks(String checkOutRemarks) {
        this.checkOutRemarks = checkOutRemarks;
    }

}