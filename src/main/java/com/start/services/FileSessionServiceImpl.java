package com.start.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.Gson;
import com.start.models.Customer;



public class FileSessionServiceImpl  implements FileSessionService{

	static Gson gson;
	final static Logger logger = Logger.getLogger(FileSessionServiceImpl.class);
	File file;
	@Override
	public void writeSession(Customer cl) {
		 gson = new Gson();
			
			
			String json = gson.toJson(cl);
	        System.out.println(json);

	        //2. Convert object to JSON string and save into a file directly
	        try (FileWriter writer = new FileWriter("user.json")) {

	            gson.toJson(cl, writer);

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		
	}

	@Override
	public Customer readSession() {
		 gson = new Gson();
		 Customer cl=null;
	        try (Reader reader = new FileReader("user.json")) {

				// Convert JSON to Java Object
	            cl = gson.fromJson(reader, Customer.class);
	            System.out.println(cl);

			

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		return cl;
	}

	@Override
	public boolean deleteSession() {
		JSONParser parser = new JSONParser();
	     boolean resp =false;
	     file = new File("user.json");
  	  Object obj;
		try {
			 if(file.length()== 0)
          	   return true;
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

		return resp;
	}

}
