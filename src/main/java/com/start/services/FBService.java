package com.start.services;

import java.util.List;

import com.restfb.types.Page;
import com.restfb.types.Post;
import com.start.models.SNresult;

public interface FBService {

	public List<Page> pageIdscollect(String queryForPgae);
	public List<Post> feedOfPage();
	public List<Post> precizeKey(String keyword);
	public List<Post> getLowerCaseKeyword(String keyword,String pageId);
	public List<Post> dataInterval();
	void feedOfTimeline();
	public List<SNresult> requete(String keyword,String pageId);
	void countLCS(String keyword);

	
}
