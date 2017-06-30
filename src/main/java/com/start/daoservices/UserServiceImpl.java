package com.start.daoservices;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.start.models.User1;
import com.start.repositories.UserRepository;

public class UserServiceImpl implements UserService{

	@Autowired 
	MongoTemplate mongoTemplate;
	@Autowired
	UserRepository userRepo;
	@Override
	public User1 getUserByUserName(String username) {
		User1 client = mongoTemplate.findOne(query(where("username").is(username)), User1.class, "user");
		
		return client;
	}

	@Override
	public User1 saveSessionUser(User1 us) {
		us.setIdL("1");
		User1 us1= userRepo.save(us);
		return us1;
	}

	@Override
	public boolean deleteSessionUser(User1 us) {
		userRepo.delete(us);
		return true;
	}

}
