package com.start.daoservices;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.start.models.User1;

public class UserServiceImpl implements UserService{

	@Autowired 
	MongoTemplate mongoTemplate;
	
	@Override
	public User1 getUserByUserName(String username) {
		User1 client = mongoTemplate.findOne(query(where("username").is(username)), User1.class, "user");
		
		return client;
	}

}
