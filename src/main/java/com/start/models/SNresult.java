package com.start.models;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SNresult {

	private String idR;
	private String text;
	private String url;
	private String url_img;

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
	@Override
	public String toString() {
		return "SNresult [idR=" + idR + ", text=" + text + ", url=" + url + "]";
	}
	
	
	
	
}
