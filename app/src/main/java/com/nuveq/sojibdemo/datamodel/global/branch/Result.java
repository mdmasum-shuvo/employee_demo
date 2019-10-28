package com.nuveq.sojibdemo.datamodel.global.branch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

@SerializedName("branchId")
@Expose
private Integer branchId;
@SerializedName("branch")
@Expose
private String branch;

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

}