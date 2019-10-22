package com.nuveq.sojibdemo.datamodel.attendance;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Emp {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("empId")
@Expose
private Integer empId;
@SerializedName("date")
@Expose
private String date;
@SerializedName("checkInTime")
@Expose
private String checkInTime;
@SerializedName("checkInLocation")
@Expose
private String checkInLocation;
@SerializedName("checkInRemarks")
@Expose
private String checkInRemarks;
@SerializedName("checkOutTime")
@Expose
private String checkOutTime;
@SerializedName("checkOutLocation")
@Expose
private String checkOutLocation;
@SerializedName("checkOutRemarks")
@Expose
private String checkOutRemarks;
@SerializedName("shifting")
@Expose
private String shifting;
@SerializedName("comments")
@Expose
private String comments;
@SerializedName("emp")
@Expose
private String emp;

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public Integer getEmpId() {
return empId;
}

public void setEmpId(Integer empId) {
this.empId = empId;
}

public String getDate() {
return date;
}

public void setDate(String date) {
this.date = date;
}

public String getCheckInTime() {
return checkInTime;
}

public void setCheckInTime(String checkInTime) {
this.checkInTime = checkInTime;
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

public String getCheckOutTime() {
return checkOutTime;
}

public void setCheckOutTime(String checkOutTime) {
this.checkOutTime = checkOutTime;
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

public String getShifting() {
return shifting;
}

public void setShifting(String shifting) {
this.shifting = shifting;
}

public String getComments() {
return comments;
}

public void setComments(String comments) {
this.comments = comments;
}

public String getEmp() {
return emp;
}

public void setEmp(String emp) {
this.emp = emp;
}

}