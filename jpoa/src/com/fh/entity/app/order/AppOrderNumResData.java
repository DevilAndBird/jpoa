package com.fh.entity.app.order;

import java.io.Serializable;

/**
 * 
 * 项目名称：jpoa   
 *
 * 类描述：订单数量列表
 * 类名称：com.fh.entity.app.order.AppOrderNumResData     
 * 创建人：tangqm
 * 创建时间：2018年7月10日 上午11:59:10   
 * 修改人：
 * 修改时间：2018年7月10日 上午11:59:10   
 * 修改备注：
 */
public class AppOrderNumResData implements Serializable {
	
    private static final long serialVersionUID = 1L;
    /** 待取数量  */
    private String taskNum ;
    /** 待送数量  */
    private String sendNum ;
    /** 进行中数量  */
    private String goingNum ;
    /** 完成数量  */
    private String finishNum ;

	public String getTaskNum() {
		return taskNum;
	}

	public void setTaskNum(String taskNum) {
		this.taskNum = taskNum;
	}
    
	public String getSendNum() {
		return sendNum;
	}

	public void setSendNum(String sendNum) {
		this.sendNum = sendNum;
	}

	public String getGoingNum() {
		return goingNum;
	}

	public void setGoingNum(String goingNum) {
		this.goingNum = goingNum;
	}

	public String getFinishNum() {
		return finishNum;
	}

	public void setFinishNum(String finishNum) {
		this.finishNum = finishNum;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
