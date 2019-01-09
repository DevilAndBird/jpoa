package com.fh.entity.app.login;
/**
 * app登陆用户请求类
 * @author cys
 * @date 2018年11月5日
 */
public class AppUserReqData {
	/**
	 * 手机号码
	 */
	private String mobile;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 来源
	 */
	private String source;
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
}
