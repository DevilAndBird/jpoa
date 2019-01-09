package com.fh.entity.system;

import java.io.Serializable;

/**
 * 
 *@Title:AuditInfo.java
 *@Package:com.fh.entity.system
 *@Description:这是审核实体类
 *@author:唐启铭
 *@date:2016年7月4日 上午9:25:25
 */
public class AuditInfo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Integer id;
    private Integer workid;
    private String auditby;
    private String auditusername;
    private String auditime;
    private Integer status;
    private String statusname;
    private String notes;
    private Integer stepid;
    private String stepname;
    private Integer level;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getWorkid() {
		return workid;
	}
	public void setWorkid(Integer workid) {
		this.workid = workid;
	}
	public String getAuditby() {
		return auditby;
	}
	public void setAuditby(String auditby) {
		this.auditby = auditby;
	}
	public String getAuditusername() {
		return auditusername;
	}
	public void setAuditusername(String auditusername) {
		this.auditusername = auditusername;
	}
	public String getAuditime() {
		return auditime;
	}
	public void setAuditime(String auditime) {
		this.auditime = auditime;
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
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public Integer getStepid() {
		return stepid;
	}
	public void setStepid(Integer stepid) {
		this.stepid = stepid;
	}
	public String getStepname() {
		return stepname;
	}
	public void setStepname(String stepname) {
		this.stepname = stepname;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
    
    
}
