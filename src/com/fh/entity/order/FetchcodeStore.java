package com.fh.entity.order;

import java.io.Serializable;

/**
 * @desc 提取码
 * @auther zhangjj
 * @date 2018年4月18日
 */
public class FetchcodeStore implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 主键 */
	private Integer id;
	/** 提取码 */
	private String fetchcode;
	/** 是否有效 */
	private String isvalid;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFetchcode() {
		return fetchcode;
	}

	public void setFetchcode(String fetchcode) {
		this.fetchcode = fetchcode;
	}

	public String getIsvalid() {
		return isvalid;
	}

	public void setIsvalid(String isvalid) {
		this.isvalid = isvalid;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
