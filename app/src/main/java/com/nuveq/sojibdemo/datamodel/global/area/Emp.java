package com.nuveq.sojibdemo.datamodel.global.area;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Emp {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("visitCategoryId")
@Expose
private Integer visitCategoryId;
@SerializedName("visitLatPoint")
@Expose
private Double visitLatPoint;
@SerializedName("visitLogPoint")
@Expose
private Double visitLogPoint;
@SerializedName("visitLocation")
@Expose
private String visitLocation;
@SerializedName("status")
@Expose
private Object status;
@SerializedName("createDate")
@Expose
private Object createDate;
@SerializedName("editDate")
@Expose
private Object editDate;
@SerializedName("comments")
@Expose
private Object comments;
@SerializedName("remarks")
@Expose
private Object remarks;
@SerializedName("visitCategory")
@Expose
private Object visitCategory;
@SerializedName("visitPlanInfo")
@Expose
private List<Object> visitPlanInfo = null;

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public Integer getVisitCategoryId() {
return visitCategoryId;
}

public void setVisitCategoryId(Integer visitCategoryId) {
this.visitCategoryId = visitCategoryId;
}

public Double getVisitLatPoint() {
return visitLatPoint;
}

public void setVisitLatPoint(Double visitLatPoint) {
this.visitLatPoint = visitLatPoint;
}

public Double getVisitLogPoint() {
return visitLogPoint;
}

public void setVisitLogPoint(Double visitLogPoint) {
this.visitLogPoint = visitLogPoint;
}

public String getVisitLocation() {
return visitLocation;
}

public void setVisitLocation(String visitLocation) {
this.visitLocation = visitLocation;
}

public Object getStatus() {
return status;
}

public void setStatus(Object status) {
this.status = status;
}

public Object getCreateDate() {
return createDate;
}

public void setCreateDate(Object createDate) {
this.createDate = createDate;
}

public Object getEditDate() {
return editDate;
}

public void setEditDate(Object editDate) {
this.editDate = editDate;
}

public Object getComments() {
return comments;
}

public void setComments(Object comments) {
this.comments = comments;
}

public Object getRemarks() {
return remarks;
}

public void setRemarks(Object remarks) {
this.remarks = remarks;
}

public Object getVisitCategory() {
return visitCategory;
}

public void setVisitCategory(Object visitCategory) {
this.visitCategory = visitCategory;
}

public List<Object> getVisitPlanInfo() {
return visitPlanInfo;
}

public void setVisitPlanInfo(List<Object> visitPlanInfo) {
this.visitPlanInfo = visitPlanInfo;
}

}