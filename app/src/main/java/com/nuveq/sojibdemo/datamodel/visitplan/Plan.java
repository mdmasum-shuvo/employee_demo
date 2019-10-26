package com.nuveq.sojibdemo.datamodel.visitplan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Plan {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("empId")
@Expose
private Integer empId;
@SerializedName("visitAreaId")
@Expose
private Integer visitAreaId;
@SerializedName("date")
@Expose
private String date;
@SerializedName("time")
@Expose
private String time;
@SerializedName("status")
@Expose
private String status;
@SerializedName("createDate")
@Expose
private String createDate;
@SerializedName("editDate")
@Expose
private String editDate;
@SerializedName("editUser")
@Expose
private String editUser;
@SerializedName("comments")
@Expose
private String comments;
@SerializedName("emp")
@Expose
private String emp;
@SerializedName("visitArea")
@Expose
private String visitArea;

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

public Integer getVisitAreaId() {
return visitAreaId;
}

public void setVisitAreaId(Integer visitAreaId) {
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

public String getCreateDate() {
return createDate;
}

public void setCreateDate(String createDate) {
this.createDate = createDate;
}

public String getEditDate() {
return editDate;
}

public void setEditDate(String editDate) {
this.editDate = editDate;
}

public String getEditUser() {
return editUser;
}

public void setEditUser(String editUser) {
this.editUser = editUser;
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

public String getVisitArea() {
return visitArea;
}

public void setVisitArea(String visitArea) {
this.visitArea = visitArea;
}

}