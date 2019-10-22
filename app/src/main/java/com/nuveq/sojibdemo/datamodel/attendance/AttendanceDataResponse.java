package com.nuveq.sojibdemo.datamodel.attendance;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AttendanceDataResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("emp")
    @Expose
    private List<Emp> emp = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Emp> getEmp() {
        return emp;
    }

    public void setEmp(List<Emp> emp) {
        this.emp = emp;
    }

}
