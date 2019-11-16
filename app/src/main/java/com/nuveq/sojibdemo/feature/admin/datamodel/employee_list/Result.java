package com.nuveq.sojibdemo.feature.admin.datamodel.employee_list;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

@SerializedName("empid")
@Expose
private Integer empid;
@SerializedName("marketingCode")
@Expose
private String marketingCode;
@SerializedName("name")
@Expose
private String name;

public Integer getEmpid() {
return empid;
}

public void setEmpid(Integer empid) {
this.empid = empid;
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

}