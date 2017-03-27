package com.start.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;

import com.restfb.types.Page;
import com.restfb.types.Post;

@Service("fbService")
public class FBserviceImpl implements FBService {
	final static Logger logger = Logger.getLogger(FBserviceImpl.class);
	private static String[] tab = {"432050416881597","163062750449250","1587232614838771","1215106251863394" };
	
	
	static FacebookClient fbClient;
	
	@SuppressWarnings("deprecation")
	public FBserviceImpl(@Value("${fb_accesToken}") String accessToken ) {
		System.err.println(accessToken);
		fbClient = new DefaultFacebookClient(accessToken);
		
	
	}
	
	@Override
	public List<Page> pageIdscollect() {
		ArrayList<Page> listId = new ArrayList<>();
		
		Connection<Page> publicSearch = fbClient.fetchConnection("search",Page.class,Parameter.with("type","page"),
				Parameter.with("q","visit Morocco")
			   );
		for(List<Page>  pageFeed : publicSearch)
		{
			for(Page aPage:pageFeed)
			{
				
				listId.add(aPage);
				
			}
			
		}
		return listId;
		
		
	}
	
	

	@Override
	public List<Post> feedOfPage() {
		 
		 Connection<Post> pageFeed = fbClient.fetchConnection(tab[2] + "/feed", Post.class);
		 ArrayList<Post> listP = new ArrayList<>();
		 
		 for (List<Post> feed : pageFeed){
             for (Post post : feed){     
              listP.add(post);
             }
          // pageFeed = fbClient.fetchConnectionPage(pageFeed.getNextPageUrl(),Post.class);
        }
		 return listP;
	}

	@Override
	public List<Post> precizeKey(String keyword) {
		ArrayList<Post> listP = new ArrayList<>();
		Connection<Post> pageFeed = fbClient.fetchConnection(tab[1] + "/feed", Post.class);
		System.out.println(keyword);
        String[] words = keyword.trim().split("\\s");
        int c=0;
        //Getting posts:
                   //PRINTING THE POST 
        try {
        	 while(pageFeed.hasNext())
        	 {
        		 for(String wd : words){
  	         		if(pageFeed.getData().get(c).getMessage().contains(wd))
  	         		{
  	         			System.out.println("-"+pageFeed.getData().get(c).getMessage());
  	         			listP.add(pageFeed.getData().get(c));
  	         			
  	         		}
  	         		else{
  	         			System.out.println("null post");
  	         		}
  	         	}
        		 
        		 c++;
        		
        		
        		 
        	 }
     	         	
     	         		
              
		} catch (Exception e) {
			logger.info("null pointer ",e);
		}
        return listP;
       
  }

	@Override
	public List<Post>  getLowerCaseKeyword(String keyword)
	{
		ArrayList<Post> lisP = new ArrayList<>();
		try{
            
            Connection<Post> pageFeed = fbClient.fetchConnection(tab[1] + "/feed", Post.class);
            
            int c=0;
           
           //Getting posts:
              
                       //PRINTING THE POST 
            while (pageFeed.hasNext()) {
            	
            	
            	if(pageFeed.getData().get(c).getMessage().toLowerCase().
            			indexOf(keyword.trim().toLowerCase())!=-1)
          		{
            	
                System.out.println("-"+pageFeed.getData().get(c).getMessage()+" fb.com/"+pageFeed.getData().get(c).getId());
                lisP.add(pageFeed.getData().get(c));
               
          		} 
            	 c++;
            	pageFeed = fbClient.fetchConnectionPage(pageFeed.getNextPageUrl(),Post.class);
            	
            }   
              }
           
      catch(Exception ex){
            System.out.println("exeption here"+ex);
      }
		return lisP;
	}

	@Override
	public  List<Post>  dataInterval()
	{
		ArrayList<Post> listP = new ArrayList<>();
		//Date days = new Date(System.currentTimeMillis() - 30 * 1000L * 60L * 60L * 24L);
		Connection<Post> myFeed = fbClient.fetchConnection("me/feed", Post.class,
				Parameter.with("until", System.currentTimeMillis()), Parameter.with("since",te(2017,3,7)));

		// Connections support paging and are iterable
		int c=0;
		// Iterate over the feed to access the particular pages
		for (List<Post> myFeedPage : myFeed) {

		  // Iterate over the list of contained data 
		  // to access the individual object
		  for (Post post : myFeedPage) {
		    System.out.println("Post: " + post.getMessage());
		    listP.add(post);
		    c++;
		  }
		}
		System.out.println(c+" nb de posts");
		return listP;
	}
	
	public static long te(int year,int mounth,int day)
	{
		int m= mounth-1;
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		calendar.set(0, 0, 0, 0, 0, 0);
		
		calendar.set(year,m, day,0,0,0);
		long secondsSinceEpoch = calendar.getTimeInMillis() / 1000L;
		System.out.println(secondsSinceEpoch);
		return secondsSinceEpoch;
	}
	
}
