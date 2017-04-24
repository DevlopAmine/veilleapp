package com.start.models;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SNresult {

	private String idR;
	private String text;
	private String url;
	

	public SNresult()
	{
		
	}
	public SNresult(String idR, String text, String url) {
		
		this.idR = idR;
		this.text = text;
		this.url = url;
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

	@Override
	public String toString() {
		return "SNresult [idR=" + idR + ", text=" + text + ", url=" + url + "]";
	}
	
	
	
	
}
