package com.start.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.json.JsonObject;
import com.start.models.SNresult;

public class TstCases {
	final static Logger logger = Logger.getLogger(FBserviceImpl.class);
	private static String[] tab = {"432050416881597","163062750449250","1587232614838771","1215106251863394" };
	
	
	static FacebookClient fbClient;
	
	@SuppressWarnings("deprecation")
	public TstCases(@Value("${fb_accesToken}") String accessToken ) {
		System.err.println(accessToken);
		fbClient = new DefaultFacebookClient(accessToken);
		}
	
	public List<SNresult> test()
	{
			Connection<JsonObject> feedcon = fbClient.fetchConnection(tab[1]+"/posts", JsonObject.class,Parameter.with("fields","message,created_time,id,link,likes,picture,shares,comments"));
		
			List<SNresult> listP = new ArrayList<>();
			SNresult sc;
			int persolimit=20;
			try {
				
				for (List<JsonObject> objectList: feedcon) {
					 
					for (JsonObject obj: objectList) {
						
						if(obj.isNull("message"))
							System.err.println("null msg");
						else
							{
							
							if(obj.isNull("link") && obj.isNull("picture"))
								sc = new SNresult(obj.get("id").toString(),obj.get("message").toString(),
										"","");
							else if(!obj.isNull("link") && obj.isNull("picture"))
								sc = new SNresult(obj.get("id").toString(),obj.get("message").toString(),
										obj.get("link").toString(),"");
							
							else if(obj.isNull("link") && !obj.isNull("picture"))
								sc = new SNresult(obj.get("id").toString(),obj.get("message").toString(),
										"",obj.get("picture").toString());
							
							sc = new SNresult(obj.get("id").toString(),obj.get("message").toString(),
									obj.get("link").toString(),obj.get("picture").toString());
							//sc.setDate_creation(formatDate(obj.get("created_time").toString()) );
								
							
								if(!obj.isNull("likes") && !obj.isNull("comments")) 
								{
									sc.setLikes_count(obj.getJsonObject("likes").getJsonArray("data").length());
									sc.setComts_count(obj.getJsonObject("comments").getJsonArray("data").length());
								  
								}
							
								else if(obj.isNull("likes") && !obj.isNull("comments"))
								{
								
									sc.setComts_count(obj.getJsonObject("comments").getJsonArray("data").length());
									
								}
								
								else if(!obj.isNull("likes") && obj.isNull("comments"))
								{
									
									sc.setLikes_count(obj.getJsonObject("likes").getJsonArray("data").length());
								   
								   
								}
								else{System.err.println("null likes or comments");}
							
							listP.add(sc);
							}
						
						persolimit--;	
						if (persolimit == 0) {
	           		          break;
	           		       }
						 			      
					   }
					
					}
				
			} catch (Exception e) {
			e.printStackTrace();
			}
			return listP;
		
	}
	

	public Date formatDate(String dateStr)
	{
		
		  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss+SSSS");
		  Date date = null;

	        try {

	             date = formatter.parse(dateStr);
	            System.out.println(date);
	            //System.out.println(formatter.format(date));

	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
	        return date;
		}
	
	/*
	 * public List<SNresult> test()
	{
			Connection<JsonObject> feedcon = fbClient.fetchConnection(tab[1]+"/posts", JsonObject.class,Parameter.with("fields","message,created_time,id,link,likes,picture,shares,comments"));
		
			List<SNresult> listP = new ArrayList<>();
			SNresult sc;
			int persolimit=20;
			try {
				
				for (List<JsonObject> objectList: feedcon) {
					 
					for (JsonObject obj: objectList) {
						
						if(obj.isNull("message"))
							System.err.println("null msg");
						else
							{
							logger.info(obj.get("created_time")); 
							sc = new SNresult(obj.get("id").toString(),obj.get("message").toString(),
									obj.get("link").toString(),obj.get("picture").toString());
							sc.setDate_creation(formatDate(obj.get("created_time").toString()) );
								if(!obj.isNull("likes") && !obj.isNull("comments")) 
								{
									sc.setLikes_count(obj.getJsonObject("likes").getJsonArray("data").length());
									sc.setComts_count(obj.getJsonObject("comments").getJsonArray("data").length());
								    //sc.setShares_count(Integer.parseInt(obj.get("shares")..toString() )); 
								}
								 if(obj.getJsonObject("shares")==null) 
								{
									
								    sc.setLikes_count(obj.getJsonObject("likes").getJsonArray("data").length());
								    sc.setComts_count(obj.getJsonObject("comments").getJsonArray("data").length());
								}
								else if(obj.isNull("likes") && !obj.isNull("comments"))
								{
									
								    //sc.setShares_count(Integer.parseInt(obj.getJsonObject("shares").get("count").toString()) );
									sc.setComts_count(obj.getJsonObject("comments").getJsonArray("data").length());
									
								}
								
								else if(!obj.isNull("likes") && obj.isNull("comments"))
								{
									
									sc.setLikes_count(obj.getJsonObject("likes").getJsonArray("data").length());
								    //sc.setShares_count(Integer.parseInt(obj.getJsonObject("shares").get("count").toString()) );
								   
								}
								else{System.err.println("null likes or comments");}
							
							listP.add(sc);
							}
						
						persolimit--;	
						if (persolimit == 0) {
	           		          break;
	           		       }
						 			      
					   }
					
					}
				
			} catch (Exception e) {
			e.printStackTrace();
			}
			return listP;
		
	}
	 * **/
}
