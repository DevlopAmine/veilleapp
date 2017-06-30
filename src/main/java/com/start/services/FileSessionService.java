package com.start.services;

import com.start.models.Customer;

public interface FileSessionService {

	public void writeSession(Customer cl);
	public Customer readSession();
	public boolean deleteSession();
}
