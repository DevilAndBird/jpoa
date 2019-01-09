package com.fh.entity.app.auxiliary.push.res;

import java.io.Serializable;
import java.util.List;

import com.fh.entity.app.auxiliary.push.req.AppPushNum;
import com.fh.entity.auxiliary.push.PushInfo;

public class AppPushResData implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 推送信息 */
	private List<PushInfo> pushInfoList;
	 /** 消息类型条数 */ 
	private List<AppPushNum> appPushNumlist;

	public List<PushInfo> getPushInfoList() {
		return pushInfoList;
	}

	public void setPushInfoList(List<PushInfo> pushInfoList) {
		this.pushInfoList = pushInfoList;
	}

	public List<AppPushNum> getAppPushNumlist() {
		return appPushNumlist;
	}

	public void setAppPushNumlist(List<AppPushNum> appPushNumlist) {
		this.appPushNumlist = appPushNumlist;
	}
	
}
