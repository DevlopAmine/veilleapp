package com.start.models;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@Document(collection="customer")
public class Customer {
	@Id
	private ObjectId id;
	
	private int idL;
	public int getIdL() {
		return idL;
	}
	public void setIdL(int idl) {
		this.idL = idl;
	}
	private String description;
	private String name;
	private String email;
	private String tel;
	private String password;
	private String username;
	private String token;
	
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
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	@Override
	public String toString() {
		return "Customer [idL=" + idL + ", description=" + description + ", name=" + name + ", email=" + email
				+ ", tel=" + tel + ", password=" + password + ", username=" + username + ", token=" + token + "]";
	}
	
	
	
	
	
	
}
