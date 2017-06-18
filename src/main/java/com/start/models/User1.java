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
	
	private Long idL;
	public Long getIdL() {
		return idL;
	}

	private String name;
	private String email;
	private String password;
	private String username;
	private String token;
	
	@Field(value="authorities")
	private List<Authority> authorities;
	
	private Boolean enabled;
	private Date lastPasswordResetDate;
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
	
	 public List<Authority> getAuthorities() {
	        return authorities;
	    }

	 public void setAuthorities(List<Authority> authorities) {
	        this.authorities = authorities;
	    }   
	 
	 public Boolean getEnabled() {
	        return enabled;
	    }

	    public void setEnabled(Boolean enabled) {
	        this.enabled = enabled;
	    }
	 
	 public Date getLastPasswordResetDate() {
	        return lastPasswordResetDate;
	    }

	    public void setLastPasswordResetDate(Date lastPasswordResetDate) {
	        this.lastPasswordResetDate = lastPasswordResetDate;
	    }
		public String getToken() {
			return token;
		}
		public void setToken(String token) {
			this.token = token;
		}
		@Override
		public String toString() {
			return "User1 [idL=" + idL + ", name=" + name + ", email=" + email + ", password=" + password
					+ ", username=" + username + ", token=" + token + ", authorities=" + authorities + ", enabled="
					+ enabled + ", lastPasswordResetDate=" + lastPasswordResetDate + "]";
		}
		
	    
	    
}
