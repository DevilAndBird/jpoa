package com.fh.entity.menu;

public class MiniprogramButton extends Button{
	/**
	 * 一级菜单下子菜单集合
	 */
	private Button[] sub_button;
	/**
	 * 一级菜单类型
	 */
	
	private String type;
	/**
	 * 一级菜单链接地址
	 */
	private String url;

	private String appid;

	private String pagepath;
	
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Button[] getSub_button() {
		return sub_button;
	}

	public void setSub_button(Button[] sub_button) {
		this.sub_button = sub_button;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getPagepath() {
		return pagepath;
	}

	public void setPagepath(String pagepath) {
		this.pagepath = pagepath;
	}
}
