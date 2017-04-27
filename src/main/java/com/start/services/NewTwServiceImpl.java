package com.start.services;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.SearchParameters;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.Tweet;

import com.start.models.Alert;

public class NewTwServiceImpl {

	@Autowired
	private Twitter twitter;
	
	private Alert alert;
	
	
	public List<Tweet> getFiltredData()
	{
		String[] forbidKeywords ={"caftan","robe"};
		String[] optKeywords ={"style","jacket"};
		String Lang ="fr"; 
		String[] srcAuth ={"Mim_fr","citemodedesign"};
		String[] srcForb ={"NumeroMagazine","histoiresdemode"};
		alert = new Alert(new ObjectId(),"fashion");
		String keyword = alert.getDescA();
		String fk="";String sA="";String sF="";
		
		
		alert.setForbidenKeywords(forbidKeywords);
		
		
		
		for(int i=0;i<forbidKeywords.length;i++)
		{
			fk+=" -"+forbidKeywords[i];
			
		}
		System.out.println(fk);
		String s ="";
		for(String wd : optKeywords)
		{
			s+=wd+" OR ";
		
		}
		System.out.println(s.trim());
		System.out.println(s.trim().substring(0,s.lastIndexOf("OR")));
		String keyOption=s.trim().substring(0,s.lastIndexOf("OR")) ;
		
		//System.out.println(keyword=keyword+" "+keyOption+fk);
		
		for(int i=0;i<srcAuth.length;i++)
		{
			sA+=" url:"+srcAuth[i];
			
		}
		System.out.println(sA);
		
		for(int i=0;i<srcForb.length;i++)
		{
			sF+=" -url:"+srcForb[i];
				
		}	
		System.out.println(sF);
		//System.out.println(keyword=keyword+" "+keyOption+fk+sF);
		System.out.println(keyword=keyword+fk+sF);
		
		SearchParameters params = new SearchParameters(keyword).lang(Lang).count(10);
		   
		return  twitter.searchOperations().search(params).getTweets();
		
		    
	
		
			
			
		
	}
	
	
}
