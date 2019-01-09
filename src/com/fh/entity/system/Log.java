package com.fh.entity.system;

public class Log {
    private Integer id;
    private String ip;
    private Integer userid;
    private String username;
    private String createdtime;
    private Integer type;
    private String typename;
    private String content;
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getCreatedtime() {
		return createdtime;
	}
	public void setCreatedtime(String createdtime) {
		this.createdtime = createdtime;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getTypename() {
		return typename;
	}
	public void setTypename(String typename) {
		this.typename = typename;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@Override
	public String toString() {
		return "log [id=" + id + ", ip=" + ip + ", userid=" + userid
				+ ", username=" + username + ", createdtime=" + createdtime
				+ ", type=" + type + ", typename=" + typename + ", content="
				+ content + "]";
	}
    
    
}
