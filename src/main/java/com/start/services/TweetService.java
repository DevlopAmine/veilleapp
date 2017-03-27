package com.start.services;

import java.util.List;

import org.springframework.social.twitter.api.Tweet;

public interface TweetService {

	public List<Tweet> tweetsByInterval();
	public List<Tweet> getTweets(String htg);
	public List<Tweet> eliminateElt();
	public List<Tweet> ListByType();
	public List<Tweet> TweetsByDates();
}
