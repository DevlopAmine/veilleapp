package com.start.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection="fbPage")
public class FbPage {

	@Id
	private ObjectId id;
	private String pageId;
	private String pageName;
	
	@Field("parent")
	private String parentAlert;
	
	private ObjectId alertid;
	
	public FbPage(){}
	public FbPage(String pageId,String pn)
	{
	this.pageId=pageId;	
	this.pageName = pn;
	}
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public String getPageId() {
		return pageId;
	}
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
	public String getPageName() {
		return pageName;
	}
	public void setPageName(String pageName) {
		this.pageName = pageName;
	}
	
		public ObjectId getAlertid() {
		return alertid;
	}
	public void setAlertid(ObjectId alertid) {
		this.alertid = alertid;
	}
	
	
	public String getParentAlert() {
		return parentAlert;
	}
	public void setParentAlert(String parentAlert) {
		this.parentAlert = parentAlert;
	}
	@Override
	public String toString() {
		return "FbPage [id=" + id + ", pageId=" + pageId + ", pageName=" + pageName + "]";
	}
	
	
	
	
}
