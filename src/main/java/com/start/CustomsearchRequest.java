package com.start;

import java.io.IOException;
import java.security.GeneralSecurityException;

import com.google.api.services.customsearch.Customsearch;

import com.google.api.services.customsearch.CustomsearchRequestInitializer;
import com.google.api.services.customsearch.Customsearch.Builder;

public class CustomsearchRequest extends CustomsearchRequestInitializer {

	private String cseKey;
	private String cse_Id;
	private Long start;
	
	public CustomsearchRequest(String key,String cseId) {
		this.cseKey=key;
		this.cse_Id=cseId;
	}
	
	@Override
	protected void initializeCustomsearchRequest(com.google.api.services.customsearch.CustomsearchRequest<?> request)
			throws IOException {
		  request.setKey(cseKey);
          request.set("cx", cse_Id);
          request.set("start", start);
          //request.set("siteSearch","www.facebook.com");	
	}
	
	public void test() throws GeneralSecurityException, IOException
	{
		 
		
	}

	public Long getStart() {
		return start;
	}

	public void setStart(Long start) {
		this.start = start;
	}

	
}
