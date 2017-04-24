package com.start.daoservices;

import java.util.HashMap;
import java.util.List;

import com.start.models.User;

public interface CustomerService {

	public void createCustomer(HashMap<String, Object> CustomMap);
	public List<User>  findUsersByCustomer(String description);
	
}
