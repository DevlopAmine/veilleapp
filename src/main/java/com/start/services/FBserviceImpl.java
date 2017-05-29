package com.start.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.json.JsonObject;
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
				Parameter.with("q","visit Morocco"),Parameter.with("fields", "id,link,name")
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
	
	
	/**
	 * return posts from specified FBpage 
	 */
	@Override
	public List<Post> feedOfPage() {
		 
		 Connection<Post> pageFeed = fbClient.fetchConnection(tab[2] + "/feed", Post.class,Parameter.with("fields","message,created_time,id,link,likes,picture"));
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
		Connection<Post> pageFeed = fbClient.fetchConnection(tab[1] + "/posts", Post.class,Parameter.with("fields","message,created_time,id,link,likes"));
		System.out.println(keyword);
        String[] words = keyword.toLowerCase().trim().split("\\s");
        String msg;
       
        //Getting posts:
                   //PRINTING THE POST 
        try {
	        	outerloop:
	        	for (List<Post> feed : pageFeed)
	        	{
	        		for (Post post : feed )
	        		{
	        			if(post.getMessage().equals(null))
	        				break outerloop;
	        			msg=post.getMessage().toLowerCase();
	        				for(String wd : words)
		   	        		 {
		   	        			 if(msg.contains(wd))
		   	        			 {
		   	        			  System.out.println("-"+post.getMessage());
		   	  	         		  listP.add(post);
		   	        				
		   	        			 }
		   	        		}
	     	       }
	        	}
        	}
        	 
        catch (Exception e) {
			logger.info("no post  match this "+e);
		}
        return listP;
       
  }

	@Override
	public List<Post>  getLowerCaseKeyword(String keyword,String pageId)
	{
		ArrayList<Post> lisP = new ArrayList<>();
		try{
            
            Connection<Post> pageFeed = fbClient.fetchConnection(pageId+"/posts", Post.class,Parameter.with("fields","message,created_time,id,link,likes,picture"));
           //Getting posts:
              
            for (List<Post> feed : pageFeed){
        		for (Post post : feed ){
	            	if(post.getMessage().toLowerCase().
	            			indexOf(keyword.trim().toLowerCase())!=-1)
	          		{
	            	
	                System.out.println("-"+post.getMessage()+" fb.com/"+post.getId()+" "+post.getLink()+" "+post.getPicture());
	                lisP.add(post);} 
            	
            	
            }   
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
	
	@Override
	public void feedOfTimeline() {
		 
		 Connection<Post> pageFeed = fbClient.fetchConnection(tab[1]+"/feed", Post.class);
		 ArrayList<Post> listP = new ArrayList<>();
		 Post.Likes likes=null;
		 Post.Comments comments = null;
		 Post.Shares shares = null;
		 int personalLimit = 10;
		 
		for (List<Post> feed : pageFeed){
             for (Post post : feed){
            	
            	  likes = fbClient.fetchObject(post.getId()+"/likes", Post.Likes.class, 
            			    Parameter.with("summary", true), Parameter.with("limit", 0));
            			long likesTotalCount = likes.getTotalCount();
            			
            	 comments = fbClient.fetchObject(post.getId()+"/comments", Post.Comments.class, 
                			Parameter.with("summary", true), Parameter.with("limit", 0));
                			long ComtsTotalCount = comments.getTotalCount();    
                			
                 shares = fbClient.fetchObject(post.getId()+"/likes", Post.Shares.class, 
                    	    Parameter.with("summary", true), Parameter.with("limit", 0));
                    		long SharesTotalCount = shares.getCount();		
            			
            			System.out.println(likesTotalCount+" liked");
            			System.out.println(ComtsTotalCount+" commented");
            			System.out.println(SharesTotalCount+" shared");
            			 personalLimit--;

          		       // break both loops
          		       if (personalLimit == 0) {
          		          return;
          		       }
              listP.add(post);
             }
             
          // pageFeed = fbClient.fetchConnectionPage(pageFeed.getNextPageUrl(),Post.class);
        }
		// return listP;
	}
	
	public void test()
	{
			Connection<JsonObject> feedcon = fbClient.fetchConnection(tab[1]+"/feed", JsonObject.class,Parameter
					.with("fields","message,created_time,id,permalink_url,likes"));

			int personalLimit = 25;
			
		for (List<JsonObject> objectList: feedcon) {
		   for (JsonObject obj: objectList) {
		       System.out.println(obj.getJsonObject("likes").getJsonArray("data").length());
		     
		       personalLimit--;

		       // break both loops
		       if (personalLimit == 0) {
		          return;
		       }
		   }
		}
		
	}
}
