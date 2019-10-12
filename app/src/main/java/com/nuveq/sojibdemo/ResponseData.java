package com.nuveq.sojibdemo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseData {

@SerializedName("empId")
@Expose
private Integer empId;
@SerializedName("marketingCode")
@Expose
private String marketingCode;
@SerializedName("name")
@Expose
private String name;
@SerializedName("picture")
@Expose
private String picture;
@SerializedName("phoneNumber")
@Expose
private String phoneNumber;
@SerializedName("macAddress")
@Expose
private String macAddress;
@SerializedName("branchId")
@Expose
private Integer branchId;
@SerializedName("password")
@Expose
private String password;
@SerializedName("location")
@Expose
private String location;
@SerializedName("roleId")
@Expose
private Integer roleId;
@SerializedName("status")
@Expose
private Boolean status;
@SerializedName("createDate")
@Expose
private String createDate;
@SerializedName("editDate")
@Expose
private String editDate;
@SerializedName("editUser")
@Expose
private String editUser;
@SerializedName("branch")
@Expose
private String branch;
@SerializedName("role")
@Expose
private String role;
@SerializedName("attendanceInfo")
@Expose
private List<Object> attendanceInfo = null;
@SerializedName("empTrackingInfo")
@Expose
private List<Object> empTrackingInfo = null;
@SerializedName("loginHistory")
@Expose
private List<Object> loginHistory = null;
@SerializedName("salesOrderInfo")
@Expose
private List<Object> salesOrderInfo = null;
@SerializedName("visitAreaInfo")
@Expose
private List<Object> visitAreaInfo = null;

public Integer getEmpId() {
return empId;
}

public void setEmpId(Integer empId) {
this.empId = empId;
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

public String getPicture() {
return picture;
}

public void setPicture(String picture) {
this.picture = picture;
}

public String getPhoneNumber() {
return phoneNumber;
}

public void setPhoneNumber(String phoneNumber) {
this.phoneNumber = phoneNumber;
}

public String getMacAddress() {
return macAddress;
}

public void setMacAddress(String macAddress) {
this.macAddress = macAddress;
}

public Integer getBranchId() {
return branchId;
}

public void setBranchId(Integer branchId) {
this.branchId = branchId;
}

public String getPassword() {
return password;
}

public void setPassword(String password) {
this.password = password;
}

public String getLocation() {
return location;
}

public void setLocation(String location) {
this.location = location;
}

public Integer getRoleId() {
return roleId;
}

public void setRoleId(Integer roleId) {
this.roleId = roleId;
}

public Boolean getStatus() {
return status;
}

public void setStatus(Boolean status) {
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

public String getBranch() {
return branch;
}

public void setBranch(String branch) {
this.branch = branch;
}

public String getRole() {
return role;
}

public void setRole(String role) {
this.role = role;
}

public List<Object> getAttendanceInfo() {
return attendanceInfo;
}

public void setAttendanceInfo(List<Object> attendanceInfo) {
this.attendanceInfo = attendanceInfo;
}

public List<Object> getEmpTrackingInfo() {
return empTrackingInfo;
}

public void setEmpTrackingInfo(List<Object> empTrackingInfo) {
this.empTrackingInfo = empTrackingInfo;
}

public List<Object> getLoginHistory() {
return loginHistory;
}

public void setLoginHistory(List<Object> loginHistory) {
this.loginHistory = loginHistory;
}

public List<Object> getSalesOrderInfo() {
return salesOrderInfo;
}

public void setSalesOrderInfo(List<Object> salesOrderInfo) {
this.salesOrderInfo = salesOrderInfo;
}

public List<Object> getVisitAreaInfo() {
return visitAreaInfo;
}

public void setVisitAreaInfo(List<Object> visitAreaInfo) {
this.visitAreaInfo = visitAreaInfo;
}

}