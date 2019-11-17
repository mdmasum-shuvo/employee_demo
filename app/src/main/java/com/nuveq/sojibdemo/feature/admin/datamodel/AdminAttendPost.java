package com.nuveq.sojibdemo.feature.admin.datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdminAttendPost {

    @SerializedName("admid")
    @Expose
    private String admid;
    @SerializedName("roleid")
    @Expose
    private String roleid;
    @SerializedName("branchid")
    @Expose
    private String branchid;
    @SerializedName("empid")
    @Expose
    private String empid;
    @SerializedName("fromdate")
    @Expose
    private String fromdate;
    @SerializedName("todate")
    @Expose
    private String todate;

    public String getAdmid() {
        return admid;
    }

    public void setAdmid(String admid) {
        this.admid = admid;
    }

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    public String getBranchid() {
        return branchid;
    }

    public void setBranchid(String branchid) {
        this.branchid = branchid;
    }

    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid;
    }

    public String getFromdate() {
        return fromdate;
    }

    public void setFromdate(String fromdate) {
        this.fromdate = fromdate;
    }

    public String getTodate() {
        return todate;
    }

    public void setTodate(String todate) {
        this.todate = todate;
    }

}