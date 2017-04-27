package com.start.controllers;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.restfb.types.Page;
import com.restfb.types.Post;
import com.start.daoservices.FBpageService;
import com.start.models.SNresult;
import com.start.services.*;


@RestController
@RequestMapping(FacebookController.Facebook_BASE_URI)
public class FacebookController {
	public static final String Facebook_BASE_URI ="svc/v1/fb";
	private static final Logger log = LoggerFactory.getLogger(FacebookController.class);
	@Autowired
	private FBService fbService;
	@Autowired
	private FBpageService fbPageServ; 
	

	@RequestMapping(value="/idscollect",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<Page> getIdPages()
	{
		return fbService.pageIdscollect();
		
	}
	
	
	/**
	 * return posts from specified FBpage 
	 */
	@RequestMapping(value="/posts", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,method= RequestMethod.GET)
	public List<Post> getFeedPages()
	{
		return fbService.feedOfPage();
		
	}
	
	//   Not working properly 
	@RequestMapping(value="/precise", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,method= RequestMethod.GET)
	public List<SNresult> getPrecize()
	{
		List<Post> listP = fbService.precizeKey("dunes");
		System.err.println(listP.size());
		List<SNresult> resultfb= new ArrayList<>();
		try {
			
			SNresult sc = new SNresult(listP.get(0).getId(),listP.get(0).getMessage(),listP.get(0).getPermalinkUrl());
			SNresult sc1 = new SNresult(listP.get(1).getId(),listP.get(1).getMessage(),listP.get(1).getPermalinkUrl());
			resultfb.add(sc);resultfb.add(sc1);
		} catch (Exception e) {
			log.info("Index out of bounds ");
		}
		
		
		return resultfb;
		
		
		
	}
	
	@RequestMapping(value="/lower", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,method= RequestMethod.GET)
	public List<SNresult> lowerkey()
	{

		String pageId = fbPageServ.getPageId("VISIT MOROCCO");
		List<Post> listP = fbService.getLowerCaseKeyword("Visit Morocco", pageId);
		List<SNresult> resultfb= new ArrayList<>();
		if(listP.size()!=0)
		{
		SNresult sc = new SNresult(listP.get(0).getId(),listP.get(0).getMessage(),listP.get(0).getLink());
		SNresult sc1 = new SNresult(listP.get(1).getId(),listP.get(1).getMessage(),listP.get(1).getLink());
		resultfb.add(sc);resultfb.add(sc1);
		}
		else
		{
			System.err.println("No data found");
		}
		
		
		return resultfb;
		
	}
	
	@RequestMapping(value="/interv", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,method= RequestMethod.GET)
	public List<Post> dataInterval()
	{
		return fbService.dataInterval();
		
	}
	
	@RequestMapping(value="/timeline", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,method= RequestMethod.GET)
	public void timeline()
	{
		 fbService.feedOfTimeline();
		
	}
	@RequestMapping(value="/tst", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,method= RequestMethod.GET)
	public void test()
	{
		 fbService.test();
		
	}
}





	