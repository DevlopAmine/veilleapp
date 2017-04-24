package com.start.models;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Document(collection="instance")
public class Instance {

	@Id
	private ObjectId id;
	
	private Double instance_id;
	private String descI;
	
	@Field(value="customer_id")
	private ObjectId customer_id;
	
/*	@DBRef
	@CascadeSave
	private Alert alert;
	*/
	
	List<Alert> alerts;
	
	public Instance(){}
	
	public Instance(Double InsId,String d)
	{
		this.instance_id=InsId;
		this.descI=d;
		
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public Double getInstance_id() {
		return instance_id;
	}

	public void setInstance_id(Double instance_id) {
		this.instance_id = instance_id;
	}

	public String getDescI() {
		return descI;
	}

	public void setDescI(String descI) {
		this.descI = descI;
	}

	public List<Alert> getAlerts() {
		return alerts;
	}

	public void setAlerts(List<Alert> alerts) {
		this.alerts = alerts;
	}

	
	/*public Alert getAlert() {
		return alert;
	}

	public void setAlert(Alert alert) {
		this.alert = alert;
	}*/

	public ObjectId getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(ObjectId customer_id) {
		this.customer_id = customer_id;
	}

	@Override
	public String toString() {
		return "Instance [id=" + id + ", customer_id=" + customer_id + ", descI=" + descI + "]";
	}
	
	
	
}
