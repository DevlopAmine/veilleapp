package com.start.services;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.customsearch.Customsearch;
import com.google.api.services.customsearch.Customsearch.Builder;
import com.google.api.services.customsearch.model.Result;
import com.google.api.services.customsearch.model.Search;
import com.start.CustomsearchRequest;




public class NwGgServiceImpl {

CustomsearchRequest csr;
	
	static private String cseKey;
	static private String cse_Id;
	final static Logger logger = Logger.getLogger(GoogleServiceImpl.class);
	

	public NwGgServiceImpl(@Value("${Gg_key}") String Gkey,@Value("${cse_Id}") String cseId) {
		
		cseKey=Gkey;
		cse_Id=cseId;
	}
	public List<Result> executeSearch(String searchTerm, final Long start) throws GeneralSecurityException, IOException
	{
		Builder builder = new Customsearch.Builder(GoogleNetHttpTransport.newTrustedTransport(), new JacksonFactory(), null);
       
        Search searchResult;
        csr= new CustomsearchRequest(cseKey, cse_Id);
        csr.setStart(start);
        builder.setApplicationName("Search Test");
        builder.setCustomsearchRequestInitializer(csr);
        Customsearch customsearch = builder.build();
        
        
        String forbidKeywords ="caftan robe";
		String optKeywords ="style jacket";
		String Lang ="lang_en"; 
		String srcAuth ="www.facebook.com/kajsgoteborg";
		String srcForb ="www.facebook.com/imatiofashion www.facebook.com/anaaga.official";
		
		
		
		
		 searchResult = customsearch.cse().list(searchTerm)
					 .setExactTerms(searchTerm)
					 .setExcludeTerms(forbidKeywords)
					 .setOrTerms(optKeywords)
					 .setSiteSearchFilter("e").setSiteSearch(srcForb)
					 .setLr(Lang)
				  
				 .execute();
		 System.out.println("About " + searchResult.getSearchInformation().getTotalResults() + " results available");
	    return searchResult.getItems();
		
	}
	
	
	
	public List<Result> searchedResults()
	 {
		 List<Result> items = new ArrayList<Result>();
	       
	            try {
	            	for (long i = 1; i <= 10; i += 10) {
					items.addAll(executeSearch("#fashion", i));
					
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
