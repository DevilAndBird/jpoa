package com.fh.entity.system;

/**
 * 	
 *@Title:WorkFlowSheet.java
 *@Package:com.fh.entity.system
 *@Description:WorkFlowSheet的实体类
 *@author:唐启铭
 *@date:2016年7月4日 下午10:27:45
 */
public class WorkFlowSheet {
      private Integer id;
      private Integer workflowid;
      private String workflowname;
      private Integer sheetid;
      private String sheetname;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getWorkflowid() {
		return workflowid;
	}
	public void setWorkflowid(Integer workflowid) {
		this.workflowid = workflowid;
	}
	public String getWorkflowname() {
		return workflowname;
	}
	public void setWorkflowname(String workflowname) {
		this.workflowname = workflowname;
	}
	public Integer getSheetid() {
		return sheetid;
	}
	public void setSheetid(Integer sheetid) {
		this.sheetid = sheetid;
	}
	public String getSheetname() {
		return sheetname;
	}
	public void setSheetname(String sheetname) {
		this.sheetname = sheetname;
	}
	
	@Override
	public String toString() {
		return "WorkFlowSheet [id=" + id + ", workflowid=" + workflowid
				+ ", workflowname=" + workflowname + ", sheetid=" + sheetid
				+ ", sheetname=" + sheetname + "]";
	}
      
	
      
}
