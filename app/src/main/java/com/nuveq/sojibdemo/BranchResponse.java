package com.nuveq.sojibdemo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BranchResponse {

@SerializedName("branchId")
@Expose
private Integer branchId;
@SerializedName("branch")
@Expose
private String branch;
@SerializedName("phone")
@Expose
private String phone;
@SerializedName("email")
@Expose
private String email;
@SerializedName("address")
@Expose
private String address;
@SerializedName("status")
@Expose
private Boolean status;
@SerializedName("createUser")
@Expose
private Object createUser;
@SerializedName("createDate")
@Expose
private Object createDate;
@SerializedName("editUser")
@Expose
private Object editUser;
@SerializedName("editDate")
@Expose
private Object editDate;
@SerializedName("empInfo")
@Expose
private List<Object> empInfo = null;

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

public String getPhone() {
return phone;
}

public void setPhone(String phone) {
this.phone = phone;
}

public String getEmail() {
return email;
}

public void setEmail(String email) {
this.email = email;
}

public String getAddress() {
return address;
}

public void setAddress(String address) {
this.address = address;
}

public Boolean getStatus() {
return status;
}

public void setStatus(Boolean status) {
this.status = status;
}

public Object getCreateUser() {
return createUser;
}

public void setCreateUser(Object createUser) {
this.createUser = createUser;
}

public Object getCreateDate() {
return createDate;
}

public void setCreateDate(Object createDate) {
this.createDate = createDate;
}

public Object getEditUser() {
return editUser;
}

public void setEditUser(Object editUser) {
this.editUser = editUser;
}

public Object getEditDate() {
return editDate;
}

public void setEditDate(Object editDate) {
this.editDate = editDate;
}

public List<Object> getEmpInfo() {
return empInfo;
}

public void setEmpInfo(List<Object> empInfo) {
this.empInfo = empInfo;
}

}