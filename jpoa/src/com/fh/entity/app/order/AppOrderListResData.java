package com.fh.entity.app.order;

import java.io.Serializable;
import java.util.List;

/**
 * @desc 订单信息列表查询
 * @auther zhangjj
 * @history 2018年2月6日
 */
public class AppOrderListResData implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /** 角色动作 */
    private String roletype;
    /** 角色动作是否完成 */
    private String isfinish;
    /** 返回结果  */
    private List<AppOrderResData> appOrderReqDataList;
    
	public String getRoletype() {
		return roletype;
	}
	public void setRoletype(String roletype) {
		this.roletype = roletype;
	}
	public String getIsfinish() {
		return isfinish;
	}
	public void setIsfinish(String isfinish) {
		this.isfinish = isfinish;
	}
	public List<AppOrderResData> getAppOrderReqDataList() {
		return appOrderReqDataList;
	}
	public void setAppOrderReqDataList(List<AppOrderResData> appOrderReqDataList) {
		this.appOrderReqDataList = appOrderReqDataList;
	}
	
	@Override
	public String toString() {
		return "AppOrderListResData [roletype=" + roletype + ", isfinish="
				+ isfinish + ", appOrderReqDataList=" + appOrderReqDataList
				+ "]";
	}
	
    
}
