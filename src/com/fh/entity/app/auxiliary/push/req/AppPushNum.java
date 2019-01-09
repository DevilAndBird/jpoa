package com.fh.entity.app.auxiliary.push.req;

import java.io.Serializable;

public class AppPushNum implements Serializable {
	private static final long serialVersionUID = 1L;
	/**消息类型 */
	private String type;
	/** 消息条数 */
	private Integer count;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}

	

	
}
