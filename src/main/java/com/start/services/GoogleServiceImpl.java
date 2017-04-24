package com.start.services;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.customsearch.Customsearch;

import com.google.api.services.customsearch.Customsearch.Builder;
import com.google.api.services.customsearch.model.Result;
import com.google.api.services.customsearch.model.Search;
import com.start.CustomsearchRequest;



@Service("gService")
public class GoogleServiceImpl  implements GoogleService{

	CustomsearchRequest csr;
	
	static private String cseKey;
	static private String cse_Id;
	final static Logger logger = Logger.getLogger(GoogleServiceImpl.class);
	

	public GoogleServiceImpl(@Value("${Gg_key}") String Gkey,@Value("${cse_Id}") String cseId) {
		
		cseKey=Gkey;
		cse_Id=cseId;
	}
	
	 private  List<Result> executeSearch(String searchTerm, final Long start,String h) throws GeneralSecurityException, IOException {
	        Builder builder = new Customsearch.Builder(GoogleNetHttpTransport.newTrustedTransport(), new JacksonFactory(), null);
	        String as_sitesearch ="www.instagram.com";
	        Search searchResult;List<Result> items;
	        csr= new CustomsearchRequest(cseKey, cse_Id);
	        csr.setStart(start);
	        builder.setApplicationName("Search Test");
	        builder.setCustomsearchRequestInitializer(csr);
	         
	        Customsearch customsearch = builder.build();
	        switch (h) {
	        case "a":
			{
				 searchResult = customsearch.cse().list(searchTerm).setExactTerms("travel,visit").execute();
				 System.out.println("About " + searchResult.getSearchInformation().getTotalResults() + " results available");
			     items = searchResult.getItems();
				break;
			}
	        case "g":
			{
				 searchResult = customsearch.cse().list(searchTerm).setOrTerms("work").execute();
				 System.out.println("About " + searchResult.getSearchInformation().getTotalResults() + " results available");
			     items = searchResult.getItems();
				break;
			}
			case "b":
					{
						 searchResult = customsearch.cse().list(searchTerm).setExcludeTerms("office").execute();
						 System.out.println("About " + searchResult.getSearchInformation().getTotalResults() + " results available");
					     items = searchResult.getItems();
						break;
					}
			case "c":
			{
				 searchResult = customsearch.cse().list(searchTerm).setDateRestrict("2017/01/15").execute();
				 System.out.println("About " + searchResult.getSearchInformation().getTotalResults() + " results available");
			     items = searchResult.getItems();
				break;
			}
			case "f":
			{
				 searchResult = customsearch.cse().list(searchTerm).setSiteSearch(as_sitesearch).execute();
				 System.out.println("About " + searchResult.getSearchInformation().getTotalResults() + " results available");
			     items = searchResult.getItems();
				break;
			}
			case "d":
			{
				 searchResult = customsearch.cse().list(searchTerm).setSiteSearchFilter("e").setSiteSearch(as_sitesearch).execute();
				 System.out.println("About " + searchResult.getSearchInformation().getTotalResults() + " results available");
			     items = searchResult.getItems();
				break;
			}
			case "e":
			{
				 searchResult = customsearch.cse().list(searchTerm).setLr("fr").execute();
				 System.out.println("About " + searchResult.getSearchInformation().getTotalResults() + " results available");
			     items = searchResult.getItems();
				break;
			}	

			default:
					{
						searchResult = customsearch.cse().list(searchTerm).execute();
						 System.out.println("About " + searchResult.getSearchInformation().getTotalResults() + " results available");
					     items = searchResult.getItems();
						break;
					}
				
			}
	       
	       
	        
	        return items;
	    }
	 
	 public List<Result> searchedResults(String param)
	 {
		 List<Result> items = new ArrayList<Result>();
	       
	            try {
	            	for (long i = 1; i <= 10; i += 10) {
					items.addAll(executeSearch("#spain", i,param));
					
	            	}  
	            	int k = 1;  
					 for (Result item : items) {
				            System.out.println(k++ + ":    " + item.getTitle() + " (" + item.getLink() + ")");
				        }
				        
				} catch (GeneralSecurityException e) {
					
					logger.error("GeneralSecurityException ", e);
				} catch (IOException e) {
					
					logger.error("IOException ", e);
				}
	     
	     return items;
	 }

	
	
	 
}
