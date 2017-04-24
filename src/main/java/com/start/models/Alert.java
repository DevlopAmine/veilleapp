package com.start.models;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;



@Document(collection="alert")
public class Alert {

	@Id
	private ObjectId id;
	private String descA;
	
	@Field(value="instanceId")
	private ObjectId instanceId;
	
	private List<AlertSource> alertsources;
	
	private String[] oblKeywords;
	private String[] optKeywords;
	private String[] forbidenKeywords;
	private String[] srcAutorises;
	private String[] srcBloques;
	private String langue;
	
	public Alert(){}
	
	public Alert(ObjectId id,String desc)
	{
		this.id=id;
		this.descA= desc;
	}

	
	
	public ObjectId getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(ObjectId instanceId) {
		this.instanceId = instanceId;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getDescA() {
		return descA;
	}

	public void setDescA(String descA) {
		this.descA = descA;
	}

	public List<AlertSource> getAlertsources() {
		return alertsources;
	}

	public void setAlertsources(List<AlertSource> alertsources) {
		this.alertsources = alertsources;
	}
	
	public String[] getOblKeywords() {
		return oblKeywords;
	}
	public void setOblKeywords(String[] oblKeywords) {
		this.oblKeywords = oblKeywords;
	}
	public String[] getOptKeywords() {
		return optKeywords;
	}
	public void setOptKeywords(String[] optKeywords) {
		this.optKeywords = optKeywords;
	}
	public String[] getForbidenKeywords() {
		return forbidenKeywords;
	}
	public void setForbidenKeywords(String[] forbidenKeywords) {
		this.forbidenKeywords = forbidenKeywords;
	}
	public String[] getSrcAutorises() {
		return srcAutorises;
	}
	public void setSrcAutorises(String[] srcAutorises) {
		this.srcAutorises = srcAutorises;
	}
	public String[] getSrcBloques() {
		return srcBloques;
	}
	public void setSrcBloques(String[] srcBloques) {
		this.srcBloques = srcBloques;
	}
	public String getLangue() {
		return langue;
	}
	public void setLangue(String langue) {
		this.langue = langue;
	}

	@Override
	public String toString() {
		return "Alert [id=" + id + ", descA=" + descA + ", instanceId=" + instanceId + "]";
	}
	
	
	
	
	
}
