package com.start.controllers;


import java.util.Date;
import java.util.HashMap;

import javax.servlet.ServletException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.start.daoservices.CustomerService;
import com.start.daoservices.UserService;
import com.start.models.Customer;
import com.start.models.User1;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@CrossOrigin(origins = "http://localhost", maxAge = 3600)
@RestController
@RequestMapping("/user")
public class AuthenticationRestController {

   
    @Autowired
    private UserService usServ;
    @Autowired
	private CustomerService customServ;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
	public User1 registerUser(@RequestBody User1 user) {
		return null;
	}

    
    @RequestMapping(value = "/login", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
    		,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Customer login(@RequestBody HashMap<String,Object> login) throws ServletException {

        String jwtToken = "";

        if (login.get("username").toString() == null || login.get("password").toString() == null) {
            throw new ServletException("Please fill in username and password");
        }
        String username = login.get("username").toString();
        String password = login.get("password").toString();

        Customer client = customServ.getCustomerByUsername(username);
        if (client == null) {
            throw new ServletException("User username not found.");
        }

        String pwd = client.getPassword();

        if (!password.equals(pwd)) {
            throw new ServletException("Invalid login. Please check your name and password.");
        }

        jwtToken = Jwts.builder().setSubject(username).claim("roles", "user").setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "secretkey").compact();
        client.setToken(jwtToken);

        System.out.println(client.toString());
        return client;
    }
}