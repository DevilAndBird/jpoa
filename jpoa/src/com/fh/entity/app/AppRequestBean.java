package com.fh.entity.app;

public class AppRequestBean {
	private String user;
	private String timestamp;
	private String sign;
	private String key;
	private String data;
	
	@Override
	public String toString() {
		return "AppRequestBean [user=" + user + ", timestamp=" + timestamp
				+ ", sign=" + sign + ", key=" + key + ", data=" + data + "]";
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
}
