package com.nuveq.sojibdemo.datamodel.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Result implements Serializable {

@SerializedName("empId")
@Expose
private Integer empId;
@SerializedName("name")
@Expose
private String name;
@SerializedName("marketingCode")
@Expose
private String marketingCode;
@SerializedName("picture")
@Expose
private Object picture;
@SerializedName("branchId")
@Expose
private Integer branchId;
@SerializedName("branch")
@Expose
private String branch;
@SerializedName("roleId")
@Expose
private Integer roleId;

public Integer getEmpId() {
return empId;
}

public void setEmpId(Integer empId) {
this.empId = empId;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getMarketingCode() {
return marketingCode;
}

public void setMarketingCode(String marketingCode) {
this.marketingCode = marketingCode;
}

public Object getPicture() {
return picture;
}

public void setPicture(Object picture) {
this.picture = picture;
}

public Integer getBranchId() {
return branchId;
}

public void setBranchId(Integer branchId) {
this.branchId = branchId;
}

public String getBranch() {
return branch;
}

public void setBranch(String branch) {
this.branch = branch;
}

public Integer getRoleId() {
return roleId;
}

public void setRoleId(Integer roleId) {
this.roleId = roleId;
}

}