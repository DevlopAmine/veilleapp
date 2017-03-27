package com.start.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.services.customsearch.model.Result;
import com.start.services.GoogleService;



@RestController
@RequestMapping(GoogleController.Google_BASE_URI)
public class GoogleController {

	public static final String Google_BASE_URI ="customse";

	@Autowired
	private GoogleService gService;
	

	@RequestMapping(value="/data", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,method= RequestMethod.GET)
	public List<Result> getData()
	{
		return gService.searchedResults("");
		
	}
	
	@RequestMapping(value="/exclu", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,method= RequestMethod.GET)
	public List<Result> getExcluTerm()
	{
		return gService.searchedResults("b");
		
	}
	
	@RequestMapping(value="/dateRestrict", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,method= RequestMethod.GET)
	public List<Result> getDateRst()
	{
		return gService.searchedResults("c");
		
	}
}




	
	