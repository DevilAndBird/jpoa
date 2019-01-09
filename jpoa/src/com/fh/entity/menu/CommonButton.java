package com.fh.entity.menu;

public class CommonButton extends Button{
	/**
	 * 二级菜单类型
	 */
	private String type;
	/**
	 * 二级菜单键值
	 */
	private String key;
	/**
	 * 二级菜单链接地址
	 */
	private String url;
	/**  */
	private String value;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
