package com.fh.entity.system;

import java.io.Serializable;

public class WorkFlowDetail implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer postid;
	private String  postname;
    private Integer orders;
	private String  stepname;
	private Integer wfiid;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPostid() {
		return postid;
	}
	public void setPostid(Integer postid) {
		this.postid = postid;
	}
	public String getPostname() {
		return postname;
	}
	public void setPostname(String postname) {
		this.postname = postname;
	}
	public Integer getOrders() {
		return orders;
	}
	public void setOrders(Integer orders) {
		this.orders = orders;
	}
	public String getStepname() {
		return stepname;
	}
	public void setStepname(String stepname) {
		this.stepname = stepname;
	}
	public Integer getWfiid() {
		return wfiid;
	}
	public void setWfiid(Integer wfiid) {
		this.wfiid = wfiid;
	}
	
	
}
