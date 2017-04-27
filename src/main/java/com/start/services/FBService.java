package com.start.services;

import java.util.List;

import com.restfb.types.Page;
import com.restfb.types.Post;

public interface FBService {

	public List<Page> pageIdscollect();
	public List<Post> feedOfPage();
	public List<Post> precizeKey(String keyword);
	public List<Post> getLowerCaseKeyword(String keyword,String pageId);
	public List<Post> dataInterval();
	void feedOfTimeline();
	public void test();
	
}
