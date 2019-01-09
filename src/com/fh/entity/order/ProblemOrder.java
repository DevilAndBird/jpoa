package com.fh.entity.order;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 项目名称：jpoa
 * 
 * 类描述： 类名称：com.fh.entity.order.ProblemOrder 创建人：tqm 创建时间：2018年2月10日 下午9:27:44 修改人： 修改时间：2018年2月10日 下午9:27:44 修改备注：
 * 
 * @version V1.0
 */
public class ProblemOrder implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Integer id;
    /** 订单编号 */
    private Integer orderid;
    /**
     * 反馈人id
     */
    private Integer feedbackuserid;
    /** 反馈人姓名 */
    private String feedbackusername;
    /** 反馈人姓名 */
    private String feedbackusermobile;
    /** 反馈时间 */
    private Date feedtime;
    /**
     * 反馈时间格式化
     */
    private String feedtimeformat;
    /** 处理人姓名 */
    private String processname;
    /** 处理人手机号 */
    private String processmobile;
    /** 处理时间 */
    private Date processtime;
    /**
     * 处理事件格式化
     */
    private String processtimeformat;
    /** 处理状态 */
    private String processstatus;
    /** 处理描述 */
    private String processdesc;
    /** 反馈描述 */
    private String feedbackdesc;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public String getFeedbackusername() {
        return feedbackusername;
    }

    public void setFeedbackusername(String feedbackusername) {
        this.feedbackusername = feedbackusername;
    }

    public String getFeedbackusermobile() {
        return feedbackusermobile;
    }

    public void setFeedbackusermobile(String feedbackusermobile) {
        this.feedbackusermobile = feedbackusermobile;
    }

    public Date getFeedtime() {
        return feedtime;
    }

    public void setFeedtime(Date feedtime) {
        this.feedtime = feedtime;
    }

    public String getProcessname() {
        return processname;
    }

    public void setProcessname(String processname) {
        this.processname = processname;
    }

    public String getProcessmobile() {
        return processmobile;
    }

    public void setProcessmobile(String processmobile) {
        this.processmobile = processmobile;
    }

    public Date getProcesstime() {
        return processtime;
    }

    public void setProcesstime(Date processtime) {
        this.processtime = processtime;
    }

    public String getProcessstatus() {
        return processstatus;
    }

    public void setProcessstatus(String processstatus) {
        this.processstatus = processstatus;
    }

    public String getProcessdesc() {
        return processdesc;
    }

    public void setProcessdesc(String processdesc) {
        if(processdesc == null) {
            processdesc = "";
        }
        this.processdesc = processdesc;
    }

    public String getFeedbackdesc() {
        return feedbackdesc;
    }

    public void setFeedbackdesc(String feedbackdesc) {
        if (feedbackdesc == null) {
            feedbackdesc = "";
        }
        this.feedbackdesc = feedbackdesc;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getFeedtimeformat() {
        return feedtimeformat;
    }

    public void setFeedtimeformat(String feedtimeformat) {
        this.feedtimeformat = feedtimeformat;
    }

    public String getProcesstimeformat() {
        return processtimeformat;
    }

    public void setProcesstimeformat(String processtimeformat) {
        this.processtimeformat = processtimeformat;
    }

    public Integer getFeedbackuserid() {
        return feedbackuserid;
    }

    public void setFeedbackuserid(Integer feedbackuserid) {
        this.feedbackuserid = feedbackuserid;
    }
    

}