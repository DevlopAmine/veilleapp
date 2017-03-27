package com.start.controllers;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.MediaType;
import org.springframework.social.twitter.api.SearchParameters;
import org.springframework.social.twitter.api.SearchParameters.ResultType;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.start.services.TweetService;
import com.start.services.TweetServiceImpl;

@RestController
@RequestMapping(TwitterController.TWITTER_BASE_URI)

public class TwitterController {
	
public static final String TWITTER_BASE_URI ="svc/v1/tweets";

@Autowired
private Twitter twitter;

@Autowired
private TweetService TwService;

@RequestMapping(value="{hashtag}" ,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public List<Tweet> getTweets(@PathVariable final String hashtag)
{
	
	return TwService.getTweets(hashtag);
	
}

@RequestMapping(path="/t",method = RequestMethod.GET)
public List<Tweet> getAllTweets() {

    return TwService.tweetsByInterval();
	
}

@RequestMapping(path="/eliminate",method = RequestMethod.GET)
public List<Tweet> filterTweet() {
   
   
    return TwService.eliminateElt();
}

@RequestMapping(path="/type",method = RequestMethod.GET)
public List<Tweet> filterByresultType() {
   return TwService.ListByType();
   
}

@RequestMapping(path="/tI",method = RequestMethod.GET)
public List<Tweet> TweetsByInterval()  {
	return TwService.TweetsByDates();
	
}

}

