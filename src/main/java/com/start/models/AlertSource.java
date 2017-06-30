package com.start.models;

import java.util.Date;

public class AlertSource {

	
	private String ids;
	private String text;
	private String link;
	private String link_img;
	private String date_creation;
	private int likes_count;
	private int comts_count;
	private int shares_count;
	String avis;
	public 	AlertSource()
	{}
	public AlertSource(String id,String txt,String l,String imgL)
	{
		this.ids=id;
		this.text=txt;
		this.link=l;
		this.link_img=imgL;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
	
	

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	

	public String getLink_img() {
		return link_img;
	}
	public void setLink_img(String link_img) {
		this.link_img = link_img;
	}
	
	public int getLikes_count() {
		return likes_count;
	}
	public void setLikes_count(int likes_count) {
		this.likes_count = likes_count;
	}
	public int getComts_count() {
		return comts_count;
	}
	public void setComts_count(int comts_count) {
		this.comts_count = comts_count;
	}
	public int getShares_count() {
		return shares_count;
	}
	public void setShares_count(int shares_count) {
		this.shares_count = shares_count;
	}
	public String getDate_creation() {
		return date_creation;
	}
	
	public void setDate_creation(String date_creation) {
		this.date_creation = date_creation;
	}
	
	
	
	public String getAvis() {
		return avis;
	}
	public void setAvis(String avis) {
		this.avis = avis;
	}
	@Override
	public String toString() {
		return "AlertSource [ids=" + ids + ", link=" + link + "]";
	}
	
	
	
	
}
