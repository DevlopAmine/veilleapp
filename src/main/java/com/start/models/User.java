package com.start.models;




public class User {

	
	private int idU;
	private String username;
	private String password;
	private String email;
	
	
	public int getId() {
		return idU;
	}
	public void setId(int id) {
		this.idU = id;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "User [idU=" + idU + ", username=" + username + ", password=" + password + ", email=" + email + "]";
	}
	
	
}
