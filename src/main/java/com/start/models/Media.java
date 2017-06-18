package com.start.models;



public class Media {

	private String mediaUrl;
	private String externalUrl;
	
	public Media(String mediaUrl,String externalUrl) {
		super();
		this.mediaUrl = mediaUrl;
		this.externalUrl=externalUrl;
		
	}


	public String getMediaUrl() {
		return mediaUrl;
	}


	public void setMediaUrl(String mediaUrl) {
		this.mediaUrl = mediaUrl;
	}
	public String getExternalUrl() {
		return externalUrl;
	}


	public void setExternalUrl(String extUrl) {
		this.externalUrl = extUrl;
	}

	
	
}
