package com.fh.entity.auxiliary.push;

import java.util.List;

public class SystemPushInfo {

	/** 推送内容 */
	private String notice;
	/** 推送人员id */
	private List<Integer> userlist;
	public String getNotice() {
		return notice;
	}
	public void setNotice(String notice) {
		this.notice = notice;
	}
	public List<Integer> getUserlist() {
		return userlist;
	}
	public void setUserlist(List<Integer> userlist) {
		this.userlist = userlist;
	}

	
	
	
}
