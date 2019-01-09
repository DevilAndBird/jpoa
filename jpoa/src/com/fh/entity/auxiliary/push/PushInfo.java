package com.fh.entity.auxiliary.push;

public class PushInfo {
	/** 主键 */
	private Integer id;
	/** 登陆id */
	private Integer userid;
	/** 推送内容 */
	private String pushcontent;
	/** 业务信息 */
	private String extras;
	/** 是否已阅读 */
	private String isread;
	/** 新增时间 */
	private String addtime;
	/** 类型 */
	private String type;
	/** 主题 */
	private String theme;
	
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIsread() {
		return isread;
	}

	public void setIsread(String isread) {
		this.isread = isread;
	}

	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getPushcontent() {
		return pushcontent;
	}

	public void setPushcontent(String pushcontent) {
		this.pushcontent = pushcontent;
	}

	public String getExtras() {
		return extras;
	}

	public void setExtras(String extras) {
		this.extras = extras;
	}
}
