package com.nuveq.sojibdemo.datamodel.global.area;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AreaPost {

@SerializedName("categoryid")
@Expose
private String categoryid;

public String getCategoryid() {
return categoryid;
}

public void setCategoryid(String categoryid) {
this.categoryid = categoryid;
}

}