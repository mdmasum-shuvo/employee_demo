package com.nuveq.sojibdemo.datamodel.global.branch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BranchPost {

@SerializedName("branchid")
@Expose
private Integer branchid;

public Integer getBranchid() {
return branchid;
}

public void setBranchid(Integer branchid) {
this.branchid = branchid;
}

}