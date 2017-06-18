package com.start.daoservices;

import java.util.HashMap;
import java.util.List;

import org.bson.types.ObjectId;

import com.start.models.Alert;
import com.start.models.Customer;
import com.start.models.User;

public interface CustomerService {

	public void createCustomer(Customer Custom);
	public List<User>  findUsersByCustomer(String description);
	Customer getCustomer(String name);
	public void update(Customer cust );
	public List<Alert> findAlertsByCustomer(String name);
	Customer getCustomerByUsername(String username); 
}
