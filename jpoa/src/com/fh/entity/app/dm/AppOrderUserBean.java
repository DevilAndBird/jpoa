package com.fh.entity.app.dm;
/**
 * 订单角色实体类
 * @author tqm
 * @date 2018年11月6日
 */
public class AppOrderUserBean
{
	/** 角色id */
	private Integer roleid;
	/** 订单id */
	private Integer orderid;
	/** 行李个数 */
	private String begnum;
	
	
	public Integer getRoleid() {
		return roleid;
	}
	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}
	public Integer getOrderid() {
		return orderid;
	}
	public void setOrderId(Integer orderid) {
		this.orderid = orderid;
	}
    public String getBegnum() {
        return begnum;
    }
    public void setBegnum(String begnum) {
        this.begnum = begnum;
    }
	
	@Override
	public String toString() {
		return "AppOrderUserBean [roleid=" + roleid + ", orderid=" + orderid
				+ ", begnum=" + begnum + "]";
	}
    
	
    
}
