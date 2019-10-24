package com.nuveq.sojibdemo.datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VisitPlanDataPost {

@SerializedName("EmpId")
@Expose
private String empId;
@SerializedName("VisitAreaId")
@Expose
private String visitAreaId;
@SerializedName("Date")
@Expose
private String date;
@SerializedName("Time")
@Expose
private String time;
@SerializedName("Status")
@Expose
private String status;

public String getEmpId() {
return empId;
}

public void setEmpId(String empId) {
this.empId = empId;
}

public String getVisitAreaId() {
return visitAreaId;
}

public void setVisitAreaId(String visitAreaId) {
this.visitAreaId = visitAreaId;
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