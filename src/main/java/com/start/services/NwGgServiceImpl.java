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
import com.start.models.Alert;




public class NwGgServiceImpl {

CustomsearchRequest csr;
	
	static private String cseKey;
	static private String cse_Id;
	final static Logger logger = Logger.getLogger(GoogleServiceImpl.class);
	

	public NwGgServiceImpl(@Value("${Gg_key}") String Gkey,@Value("${cse_Id}") String cseId) {
		
		cseKey=Gkey;
		cse_Id=cseId;
	}
	public List<Result> executeSearch(Alert al, final Long start) throws GeneralSecurityException, IOException
	{
		Builder builder = new Customsearch.Builder(GoogleNetHttpTransport.newTrustedTransport(), new JacksonFactory(), null);
       
        Search searchResult;
        csr= new CustomsearchRequest(cseKey, cse_Id);
        csr.setStart(start);
        builder.setApplicationName("Search Test");
        builder.setCustomsearchRequestInitializer(csr);
        Customsearch customsearch = builder.build();
        
        
        String forbidKeywords =al.getForbidenKeywords();
		String optKeywords =al.getOptKeywords();
		String Lang ="lang_en"; 
		String srcAuth =al.getSrcAutorises();
		String srcForb =al.getForbidenKeywords();
		
		
		 searchResult = customsearch.cse().list(al.getDescA())
					 .setExactTerms(al.getDescA())
					 .setExcludeTerms(forbidKeywords)
					 .setOrTerms(optKeywords)
					 .setSiteSearchFilter("e").setSiteSearch(srcForb)
					 .setLr(Lang)
				  
				 .execute();
		 System.out.println("About " + searchResult.getSearchInformation().getTotalResults() + " results available");
	    return searchResult.getItems();
		
	}
	
	public List<Result> searchedResults(Alert alert)
	 {
		 List<Result> items = new ArrayList<Result>();
	       
	            try {
	            	for (long i = 1; i <= 10; i += 10) {
					items.addAll(executeSearch(alert, i));
					
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
