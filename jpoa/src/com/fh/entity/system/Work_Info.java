package com.fh.entity.system;

import java.io.Serializable;

/**
 * 
 * @Title:Work_Info.java
 * @Package:com.fh.entity.system
 * @Description:这是自定义表单具体对象实体类
 * @author:唐启铭
 * @date:2016年6月28日 上午9:26:24
 */
public class Work_Info implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String sheetname;
	private Integer sheettype;
	private String sheettypename;
	private String notes;
	private String userid;
	private String username;
	private String createdtime;
	private Integer status;
	private String statusname;
	private Integer sheetid;
	private Integer workflowid;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSheetname() {
		return sheetname;
	}

	public void setSheetname(String sheetname) {
		this.sheetname = sheetname;
	}

	public Integer getSheettype() {
		return sheettype;
	}

	public void setSheettype(Integer sheettype) {
		this.sheettype = sheettype;
	}

	public String getSheettypename() {
		return sheettypename;
	}

	public void setSheettypename(String sheettypename) {
		this.sheettypename = sheettypename;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCreatedtime() {
		return createdtime;
	}

	public void setCreatedtime(String createdtime) {
		this.createdtime = createdtime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getStatusname() {
		return statusname;
	}

	public void setStatusname(String statusname) {
		this.statusname = statusname;
	}

	public Integer getSheetid() {
		return sheetid;
	}

	public void setSheetid(Integer sheetid) {
		this.sheetid = sheetid;
	}

	public Integer getWorkflowid() {
		return workflowid;
	}

	public void setWorkflowid(Integer workflowid) {
		this.workflowid = workflowid;
	}

	@Override
	public String toString() {
		return "work_info [id=" + id + ", sheetname=" + sheetname
				+ ", sheettype=" + sheettype + ", sheettypename="
				+ sheettypename + ", notes=" + notes + ", userid=" + userid
				+ ", username=" + username + ", createdtime=" + createdtime
				+ ", status=" + status + ", statusname=" + statusname
				+ ", sheetid=" + sheetid + "]";
	}

}
