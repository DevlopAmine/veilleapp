package com.start.controllers;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.start.daoservices.UserService;
import com.start.models.User1;

@CrossOrigin(origins = "http://localhost", maxAge = 3600)
@RestController
@RequestMapping("/secure")
public class SecureController {

	/**
	 * @Autowired
	private UserService userService;
	 */
	@Autowired
    private UserService usServ;

	@RequestMapping("/user/users")
	public String loginSuccess() {
		return "Login Successful!";
	}

	@RequestMapping(value = "/user/userName", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<User1>  findByUsername(@RequestBody HashMap<String, Object> CustomMap ) {
		String username = CustomMap.get("username").toString();
		System.out.println(CustomMap.get("username").toString());
		return  new ResponseEntity<User1>(usServ.getUserByUserName(username), HttpStatus.OK); 
	}

/*	@RequestMapping(value = "/user/update", method = RequestMethod.POST)
	public User updateUser(@RequestBody User user) {
		return userService.save(user);
	}*/
}