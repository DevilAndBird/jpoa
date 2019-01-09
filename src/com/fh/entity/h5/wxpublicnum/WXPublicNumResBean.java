package com.fh.entity.h5.wxpublicnum;


/**
 * @desc 通过网页授权获取的用户信息
 * @auther zhangjj
 * @date 2018年4月20日
 */
public class WXPublicNumResBean {
	/** 用户信息 */
	public SNSUserInfo sNSUserInfo;

	public SNSUserInfo getsNSUserInfo() {
		return sNSUserInfo;
	}

	public void setsNSUserInfo(SNSUserInfo sNSUserInfo) {
		this.sNSUserInfo = sNSUserInfo;
	}
	
}