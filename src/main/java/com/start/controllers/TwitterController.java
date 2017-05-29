package com.start.controllers;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.social.twitter.api.MediaEntity;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.start.models.SNresult;
import com.start.services.NewTwServiceImpl;
import com.start.services.TweetService;


@RestController
@RequestMapping(TwitterController.TWITTER_BASE_URI)

public class TwitterController {

private static final Logger log = LoggerFactory.getLogger(TwitterController.class);	
public static final String TWITTER_BASE_URI ="svc/v1/tweets";


@Autowired
private TweetService TwService;
@Autowired
private NewTwServiceImpl ntw;

@RequestMapping(value="{hashtag}" ,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public List<SNresult> getTweets(@PathVariable final String hashtag)
{
	List<Tweet> tweetList = TwService.getTweets(hashtag);

	List<SNresult> resL = new ArrayList<>();
    
    int c=0;
	for (Tweet tweet : tweetList) {
		//System.out.println(tweet.getEntities().getMedia().size());
		Iterator<MediaEntity> mediaI = tweet.getEntities().getMedia().listIterator();
		
		if(mediaI.equals(null))
		{
			System.out.println("null media");
			
		}
		while(mediaI.hasNext())
		{
		 MediaEntity media = mediaI.next();
		 resL.add(new SNresult(tweet.getIdStr(),tweet.getText(), media.getUrl(),media.getDisplayUrl()));
		 System.out.println(resL.get(c).toString());c++;
		}
		
		 
	
	}
	
	return resL;
	
	
	
}

@RequestMapping(path="/t",method = RequestMethod.GET)
public List<Tweet> getAllTweets() {

    return TwService.tweetsByInterval();
	
}

/*@RequestMapping(path="/eliminate",method = RequestMethod.GET)
public List<Tweet> filterTweet() {
	
	List<Tweet> tweetList = TwService.eliminateElt();

	List<SNresult> resL = new ArrayList<>();
    
    int c=0;
	for (Tweet tweet : tweetList) {
		//System.out.println(tweet.getEntities().getMedia().size());
		Iterator<MediaEntity> mediaI = tweet.getEntities().getMedia().listIterator();
		
		if(mediaI.equals(null))
		{
			System.out.println("null media");
			
		}
		while(mediaI.hasNext())
		{
		 MediaEntity media = mediaI.next();
		 resL.add(new SNresult(tweet.getIdStr(),tweet.getText(), media.getUrl()));
		 System.out.println(resL.get(c).toString());c++;
		}
	
	}
   
}*/

@RequestMapping(path="/lang",method = RequestMethod.GET)
public List<Tweet> filterByLang() {
   return TwService.selectLang();
   
}

@RequestMapping(path="/type",method = RequestMethod.GET)
public List<Tweet> filterByresultType() {
   return TwService.ListByType();
   
}

@RequestMapping(path="/tI",method = RequestMethod.GET)
public List<Tweet> TweetsByInterval()  {
	return TwService.TweetsByDates();
	
}


@RequestMapping(path="/fd",method = RequestMethod.GET)
public List<SNresult> filterData()  
{
	
	//List<Tweet> tweetList = ntw.getFiltredData();
	List<Tweet> tweetList = null;
	List<SNresult> resL = new ArrayList<>();
    
  
	for (Tweet tweet : tweetList) 
	{
		Iterator<MediaEntity> mediaI = tweet.getEntities().getMedia().listIterator();
		
		if(mediaI.equals(null))
		{
			System.out.println("null media");
			
		}
		while(mediaI.hasNext())
		{
		 MediaEntity media = mediaI.next();
		 //resL.add(new SNresult(tweet.getIdStr(),tweet.getText(), media.getUrl()));
		 //System.out.println(resL.get(c).toString());
		 
		}
	
	
	}
	return resL;
}

}

