package com.start.models;




public class AlertSource {

	
	private String ids;
	private String text;
	private String link;
	private String link_img;
	
	
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
	@Override
	public String toString() {
		return "AlertSource [ids=" + ids + ", link=" + link + "]";
	}
	
	
	
	
}
