package com.start.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.json.JsonObject;
import com.start.models.SNresult;
import com.start.models.User;


public class TstCases {
	final static Logger logger = Logger.getLogger(FBserviceImpl.class);
	private static String[] tab = {"432050416881597","163062750449250","1587232614838771","1215106251863394" };
	static Gson gson;
	
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
	
	
	public static void tstJson() throws JsonIOException
	{
	
		 gson = new Gson();
		User us =new User();
		us.setId(1);us.setUsername("amine");
		
		String json = gson.toJson(us);
        System.out.println(json);

        //2. Convert object to JSON string and save into a file directly
        try (FileWriter writer = new FileWriter("user.json")) {

            gson.toJson(us, writer);

        } catch (IOException e) {
            e.printStackTrace();
        }

		
	}
	
	public static void readjSON()
	{
	 gson = new Gson();

        try (Reader reader = new FileReader("user.json")) {

			// Convert JSON to Java Object
            User us = gson.fromJson(reader, User.class);
            System.out.println(us);

			// Convert JSON to JsonElement, and later to String
            /*JsonElement json = gson.fromJson(reader, JsonElement.class);
            String jsonInString = gson.toJson(json);
            System.out.println(jsonInString);*/

        } catch (IOException e) {
            e.printStackTrace();
        }

	}
	
	public static void erasejSON()  
	{
		 
		  JSONParser parser = new JSONParser();
	      
	    	  Object obj;
			try {
				obj = parser.parse(new FileReader(
						  "user.json"));
				 JSONObject jsonObject = (JSONObject) obj;
		            System.out.println(jsonObject);
		            
		             FileWriter writer = new FileWriter("user.json");
                     writer.write("");

			} catch (FileNotFoundException e) {
				
				e.printStackTrace();
			} catch (IOException e) {
				
				e.printStackTrace();
			} catch (org.json.simple.parser.ParseException e) {
				
				logger.error("Parse ex "+e);
			}
	 
	           
	      
	      
	    
		}
        

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

