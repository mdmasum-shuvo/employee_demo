package com.nuveq.sojibdemo.datamodel.global;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryDatum {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("category")
@Expose
private String category;
@SerializedName("status")
@Expose
private Boolean status;
@SerializedName("createDate")
@Expose
private String createDate;
@SerializedName("createUser")
@Expose
private String createUser;
@SerializedName("editDate")
@Expose
private String editDate;
@SerializedName("editUser")
@Expose
private String editUser;
@SerializedName("visitAreaInfo")
@Expose
private List<Object> visitAreaInfo = null;

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public String getCategory() {
return category;
}

public void setCategory(String category) {
this.category = category;
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

public String getCreateUser() {
return createUser;
}

public void setCreateUser(String createUser) {
this.createUser = createUser;
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

public List<Object> getVisitAreaInfo() {
return visitAreaInfo;
}

public void setVisitAreaInfo(List<Object> visitAreaInfo) {
this.visitAreaInfo = visitAreaInfo;
}

}