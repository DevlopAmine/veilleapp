package com.start.daoservices;

import com.start.models.User1;

public interface UserService {

	
	public User1 saveSessionUser(User1 us);
	public User1 getUserByUserName(String username);
	public boolean deleteSessionUser(User1 us);
}
