package com.start.models;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
@Document(collection="alert")
public class Alert {

	@Id
	private ObjectId id;
	private String descA;
	
	@Field(value="instanceId")
	private ObjectId instanceId;
	
	private List<AlertSource> alertsources;
	
	
	private String optKeywords;
	private String forbidenKeywords;
	private String srcAutorises;
	private String srcBloques;
	private String langue;
	private String descI;
	
	private String srcAutorisesTw;
	private String srcBloquesTw;
	private String fbPage;
	
	
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
	
	
	public String getOptKeywords() {
		if(this.optKeywords.equals(null))
			return "";
		return optKeywords;
	}
	public void setOptKeywords(String optKeywords) {
		this.optKeywords = optKeywords;
	}
	public String getForbidenKeywords() {
		if(this.forbidenKeywords.equals(null))
			return "";
		return forbidenKeywords;
	}
	public void setForbidenKeywords(String forbidenKeywords) {
		this.forbidenKeywords = forbidenKeywords;
	}
	public String getSrcAutorises() {
		if(this.srcAutorises.equals(null))
			return "";
		return srcAutorises;
	}
	public void setSrcAutorises(String srcAutorises) {
		this.srcAutorises = srcAutorises;
	}
	public String getSrcBloques() {
		if(this.srcBloques.equals(null))
			return "";
		return srcBloques;
	}
	public void setSrcBloques(String srcBloques) {
		this.srcBloques = srcBloques;
	}
	
	
	public String getSrcAutorisesTw() {
		if(this.srcAutorisesTw.equals(null))
			return "";
		return srcAutorisesTw;
	}
	public void setSrcAutorisesTw(String srcAutorisesTw) {
		this.srcAutorisesTw = srcAutorisesTw;
	}
	public String getSrcBloquesTw() {
		if(this.srcBloquesTw.equals(null))
			return "";
		return srcBloquesTw;
	}
	public void setSrcBloquesTw(String srcBloquesTw) {
		this.srcBloquesTw = srcBloquesTw;
	}
	public String getLangue() {
		if(this.langue.equals(null))
			return "";
		return langue;
	}
	public void setLangue(String langue) {
		this.langue = langue;
	}
	public String getDescI() {
		if(this.descI.equals(null))
			return "";
		return descI;
	}
	public void setDescI(String descI) {
		this.descI = descI;
	}
	
	
	public String getFbPage() {
		return fbPage;
	}
	@Override
	public String toString() {
		return "Alert [id=" + id + ", descA=" + descA  + ", optKeywords=" + optKeywords
				+ ", forbidenKeywords=" + forbidenKeywords + ", srcAutorises=" + srcAutorises + ", srcBloques="
				+ srcBloques + ", langue=" + langue + "]";
	}

	
	
	
	
	
}
