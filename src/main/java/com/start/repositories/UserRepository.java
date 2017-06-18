package com.start.repositories;

import org.springframework.data.repository.CrudRepository;

import com.start.models.User1;


public interface UserRepository extends CrudRepository<User1, String>{

}
