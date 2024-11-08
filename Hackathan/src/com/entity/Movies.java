package com.entity;

import java.sql.Date;

public class Movies {
	private int id;
	private String title;
	private Date rel_date;
	public Movies(int id, String title, Date rel_date) {
		this.id = id;
		this.title = title;
		this.rel_date = rel_date;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getRel_date() {
		return rel_date;
	}
	public void setRel_date(Date rel_date) {
		this.rel_date = rel_date;
	}
	@Override
	public String toString() {
		return "Movies [id=" + id + ", title=" + title + ", rel_date=" + rel_date + "]";
	}
	

}
