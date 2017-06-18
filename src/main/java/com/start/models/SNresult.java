package com.start.models;



import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SNresult {

	private String idR;
	private String text;
	private String url;
	private String url_img;
	private String date_creation;
	private int likes_count;
	private int comts_count;
	private int shares_count;

	public SNresult()
	{
		
	}
	public SNresult(String idR, String text, String url,String imgU) {
		
		this.idR = idR;
		this.text = text;
		this.url = url;
		this.url_img=imgU;
	}

	public String getIdR() {
		return idR;
	}

	public void setIdR(String idR) {
		this.idR = idR;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl_img() {
		return url_img;
	}
	public void setUrl_img(String url_img) {
		this.url_img = url_img;
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
	@Override
	public String toString() {
		return "SNresult [idR=" + idR + ", text=" + text + ", url=" + url + ", url_img=" + url_img + ", date_creation="
				+ date_creation + ", likes_count=" + likes_count + ", comts_count=" + comts_count + ", shares_count="
				+ shares_count + "]";
	}
	
	
	
	
	
	
}
