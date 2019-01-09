package com.fh.entity.app.dm;

import java.util.List;

import com.fh.entity.app.counterservice.AppCounterCenter;
import com.fh.entity.app.transitcenter.AppTransitCenter;

public class AppCityNodeResData {
	/** 柜台服务中心列表 */
	private List<AppCounterCenter> appCounterCenterList;
	/** 集散中心列表 */
	private List<AppTransitCenter> appTransitCenterList;

	public List<AppCounterCenter> getAppCounterCenterList() {
		return appCounterCenterList;
	}

	public void setAppCounterCenterList(List<AppCounterCenter> appCounterCenterList) {
		this.appCounterCenterList = appCounterCenterList;
	}

	public List<AppTransitCenter> getAppTransitCenterList() {
		return appTransitCenterList;
	}

	public void setAppTransitCenterList(List<AppTransitCenter> appTransitCenterList) {
		this.appTransitCenterList = appTransitCenterList;
	}
}
