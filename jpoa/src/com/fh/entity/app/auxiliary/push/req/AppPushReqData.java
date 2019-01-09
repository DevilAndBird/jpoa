package com.fh.entity.app.auxiliary.push.req;

import java.io.Serializable;

public class AppPushReqData implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 登陆id */
	private Integer userid;
	/** 消息类型 */
	private String type;
	/** 消息id */
	private Integer id;
	/** isread */
	private String isread;
	

	public String getIsread() {
		return isread;
	}

	public void setIsread(String isread) {
		this.isread = isread;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
}
