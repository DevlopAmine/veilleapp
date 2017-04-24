package com.start.repositories;

import org.springframework.data.repository.CrudRepository;

import com.start.models.Customer;

public interface CustomRepository  extends CrudRepository<Customer, String>{

}
