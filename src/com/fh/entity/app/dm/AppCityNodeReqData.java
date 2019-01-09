package com.fh.entity.app.dm;

public class AppCityNodeReqData {
	/** 用户登录id */
	private Integer userid;
	/** 查询城市节点类型 */
	private String queryCityNodeType;

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getQueryCityNodeType() {
		return queryCityNodeType;
	}

	public void setQueryCityNodeType(String queryCityNodeType) {
		this.queryCityNodeType = queryCityNodeType;
	}
}
