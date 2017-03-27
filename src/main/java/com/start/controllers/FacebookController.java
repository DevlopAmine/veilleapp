package com.start.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.restfb.types.Page;
import com.restfb.types.Post;
import com.start.services.FBService;


@RestController
@RequestMapping(FacebookController.Facebook_BASE_URI)
public class FacebookController {
	public static final String Facebook_BASE_URI ="svc/v1/fb";

	@Autowired
	private FBService fbService;
	

	@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<Page> getIdPages()
	{
		return fbService.pageIdscollect();
		
	}
	
	@RequestMapping(value="/posts", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,method= RequestMethod.GET)
	public List<Post> getFeedPages()
	{
		return fbService.feedOfPage();
		
	}
	
	@RequestMapping(value="/precise", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,method= RequestMethod.GET)
	public List<Post> getPrecize()
	{
		return fbService.precizeKey("dunes");
		
	}
	
	@RequestMapping(value="/lower", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,method= RequestMethod.GET)
	public List<Post> lowerkey()
	{
		return fbService.getLowerCaseKeyword("Visit Morocco");
		
	}
	
	@RequestMapping(value="/interv", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,method= RequestMethod.GET)
	public List<Post> dataInterval()
	{
		return fbService.dataInterval();
		
	}
}





	