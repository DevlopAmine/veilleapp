package com.start.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.services.customsearch.model.*;
import com.start.models.SNresult;
import com.start.services.GoogleService;
import com.start.services.NwGgServiceImpl;



@RestController
@RequestMapping(GoogleController.Google_BASE_URI)
public class GoogleController {

	public static final String Google_BASE_URI ="customse";

	@Autowired
	private GoogleService gService;
	@Autowired
	private NwGgServiceImpl ngw;
	

	@RequestMapping(value="/data", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,method= RequestMethod.GET)
	public List<SNresult> getData()
	{
		List<Result> resL= gService.searchedResults("a");//.subList(0,3) == (x,y is exclusive)
		List<SNresult> gGlist = new ArrayList<>();
		Map<String, List<Map<String, Object>>> pageMap;
		int c=0;
		for (Result result : resL) {
			
			  
			  try {
				  pageMap = result.getPagemap();
				  gGlist.add(new SNresult(result.getCacheId(), result.getTitle(), result.getLink(),pageMap.get("cse_image").get(0).get("src").toString()));
			} catch (Exception e) {
				System.err.println("not foud cse_image pageMap");
			}
			  
			  /*c++;
			  System.out.println("fin item");*/
			  

		}
		return gGlist;
		
	}
	@RequestMapping(value="/obligatkw", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,method= RequestMethod.GET)
	public List<Result> getObligatKw()
	{
		return gService.searchedResults("a");
		
	}
	@RequestMapping(value="/optkw", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,method= RequestMethod.GET)
	public List<Result> getOptKw()
	{
		return gService.searchedResults("g");
		
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
	@RequestMapping(value="/obligatsrc", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,method= RequestMethod.GET)
	public List<Result> getObligatSrc()
	{
		return gService.searchedResults("f");
		
	}
	
	@RequestMapping(value="/exclusrc", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,method= RequestMethod.GET)
	public List<Result> getExcluSrc()
	{
		return gService.searchedResults("d");
		
	}
	
	@RequestMapping(value="/lang", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,method= RequestMethod.GET)
	public List<Result> getLang()
	{
		return gService.searchedResults("e");
		
	}
	
	@RequestMapping(value="/filter", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,method= RequestMethod.GET)
	public List<Result> filtredData()
	{
		return null;
		
	}
	
}




	
	