package com.start.models;

public class Tweet {

	private String id_s;
	private String sujet;
	private String url_Tt;
	private String name_pl;
	private String country;
	private String id_u;
	
	
	public String getId_s() {
		return id_s;
	}
	public void setId_s(String id_s) {
		this.id_s = id_s;
	}
	public String getSujet() {
		return sujet;
	}
	public void setSujet(String sujet) {
		this.sujet = sujet;
	}
	public String getUrl_Tt() {
		return url_Tt;
	}
	public void setUrl_Tt(String url_Tt) {
		this.url_Tt = url_Tt;
	}
	public String getName_pl() {
		return name_pl;
	}
	public void setName_pl(String nmpl) {
		this.name_pl = nmpl;
	}
	public String getId_u() {
		return id_u;
	}
	public void setId_u(String id_u) {
		this.id_u = id_u;
	}
	@Override
	public String toString() {
		return "Tweet [id_s=" + id_s + ", sujet=" + sujet + ", url_Tt="
				+ url_Tt + ", name place=" + name_pl +",country"+country+ ", id_u=" + id_u + "]";
	}
	
	

	
}
