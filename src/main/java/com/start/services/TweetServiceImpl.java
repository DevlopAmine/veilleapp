package com.start.services;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.SearchParameters;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.SearchParameters.ResultType;
import org.springframework.stereotype.Service;


@Service("TwService")
public class TweetServiceImpl implements TweetService {
	@Autowired
	private Twitter twitter;
	
	@Override
	public List<Tweet> tweetsByInterval() {
		
		
	    Long maxId = new Long("844587302915624960");Long sm = new Long("844587158396715008");
	    /*ArrayList<Tweet> listT =  (ArrayList<Tweet>) twitter.searchOperations().search(keyword,5).getTweets();
	   for (Tweet tweet: listT) 
	   		{
		   		System.out.println(tweet.getId());
	   		}*/
	    SearchResults results = twitter.searchOperations().search(
		    new SearchParameters("#FillonPrésident").sinceId(sm).
			maxId(maxId));  
	   
	    for (Tweet tweet: results.getTweets()) 
			{
	    	System.out.println(tweet.getIdStr()+" created at "+tweet.getCreatedAt());
			}
	    return  results.getTweets();
		
	}
	@Override
	public List<Tweet> ListByType() {
		 String keyword="#visitMorocco";
		    SearchParameters params = new SearchParameters(keyword).resultType(ResultType.RECENT);
		   
		    return  twitter.searchOperations().search(params).getTweets();
	}

	@Override
	public List<Tweet> getTweets(String hashtag) {
		return twitter.searchOperations().search(hashtag).getTweets();
	}

	@Override
	public List<Tweet> eliminateElt() {
		String keyword="visit Morocco -king";
		return twitter.searchOperations().search(keyword,5).getTweets();
	}

	@Override
	public List<Tweet> TweetsByDates() {
		Calendar cal1=Calendar.getInstance();Calendar cal2=Calendar.getInstance();
		Calendar cal = Calendar.getInstance();
		cal.clear();cal1.clear();cal2.clear();
		
		int day1=19,day2=23;
		cal1.set(Calendar.YEAR, Calendar.MONTH, day1);
		cal2.set(Calendar.YEAR, Calendar.MONTH, day2);
		
	    Long maxId = new Long("844587302915624960");Long sm = new Long("844587158396715008");
	   
	    /*SearchResults results = twitter.searchOperations().search(
		    new SearchParameters("#FillonPrésident").sinceId(sm).
			maxId(maxId));  */
	    SearchResults results = twitter.searchOperations().search(
	    	    new SearchParameters("#FillonPrésident"));
	   int c=0;
	    for (Tweet tweet: results.getTweets()) 
			{
	    	  cal.setTime(tweet.getCreatedAt());
	    	 
	    	 
	    	  if(cal.get(Calendar.DAY_OF_MONTH)>=day1 && cal.get(Calendar.DAY_OF_MONTH)<=day2)
	    	  { System.out.println(tweet.getIdStr()+" created at "+tweet.getCreatedAt());
	    	  c++;
	    	  }
	    		 
			}
	    System.out.println(c);
	    return  results.getTweets();
	}

	
}
