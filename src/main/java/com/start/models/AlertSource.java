package com.start.models;




public class AlertSource {

	
	private String ids;
	private String text;
	private String link;
	
	
	public 	AlertSource()
	{}
	public AlertSource(String id,String txt,String l)
	{
		this.ids=id;
		this.text=txt;
		this.link=l;
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

	@Override
	public String toString() {
		return "AlertSource [ids=" + ids + ", link=" + link + "]";
	}
	
	
	
	
}
