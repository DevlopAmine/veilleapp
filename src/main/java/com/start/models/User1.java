package com.start.models;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.start.models.security.Authority;

@Document(collection="user")
public class User1 {
	@Id
	private ObjectId id;
	
	private String idL;
	public String getIdL() {
		return idL;
	}

	public void setIdL(String idL) {
		this.idL = idL;
	}

	private String name;
	private String email;
	private String password;
	private String username;
	
	public User1 (ObjectId Id, String n, String e, String pass, String usn)
	{
		this.id =Id;
		this.name =n;
		this.password=pass;
		this.username = usn;
	}
	
	public String getName() {
		return name;
	}
	public String getEmail() {
		return email;
	}
	public String getPassword() {
		return password;
	}
	public String getUsername() {
		return username;
	}
	
	
	 
	 
	
		@Override
		public String toString() {
			return "User1 [idL=" + idL + ", name=" + name + ", email=" + email + ", password=" + password
					+ ", username=" + username +"]";
		}
		
	    
	    
}
