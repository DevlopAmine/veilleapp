package com.start.models;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection="customer")
public class Customer {
	@Id
	private ObjectId id;
	
	private String description;
	
	@Field(value="inst_ids")
	
	private List<ObjectId> listIds;
	@Field(value="users")
	private List<User> listusers;
	public Customer()
	{}
	public Customer(String desc)
	{
		this.description = desc;
	}
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public List<ObjectId> getListIds() {
		return listIds;
	}
	public void setListIds(List<ObjectId> listIds) {
		this.listIds = listIds;
	}
	public List<User> getListusers() {
		return listusers;
	}
	public void setListusers(List<User> listusers) {
		this.listusers = listusers;
	}
	@Override
	public String toString() {
		return "Customer [id=" + id + ", description=" + description + "]";
	}
	
	
	
	
}
