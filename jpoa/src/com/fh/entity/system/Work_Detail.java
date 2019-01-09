package com.fh.entity.system;

import java.io.Serializable;

public class Work_Detail implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Integer id;
	 private Integer fid;
     private String detailname;
     private String content;
     private Integer detailid;
     
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getFid() {
		return fid;
	}
	public void setFid(Integer fid) {
		this.fid = fid;
	}
	public String getDetailname() {
		return detailname;
	}
	public void setDetailname(String detailname) {
		this.detailname = detailname;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getDetailid() {
		return detailid;
	}
	public void setDetailid(Integer detailid) {
		this.detailid = detailid;
	}
	@Override
	public String toString() {
		return "work_detail [id=" + id + ", fid=" + fid + ", detailname="
				+ detailname + ", content=" + content + ", detailid="
				+ detailid + "]";
	}
     
         
     
     
     
}
