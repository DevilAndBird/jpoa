package com.fh.entity.order;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 项目名称：jpoa   
 *
 * 类描述：
 * 类名称：com.fh.entity.order.OrderOberTime     
 * 创建人：tangqm 
 * 创建时间：2018年2月5日 上午2:56:02   
 * 修改人：
 * 修改时间：2018年2月5日 上午2:56:02   
 * 修改备注：   
 * @version   V1.0
 */
public class OrderOverTime implements  Serializable{
    
	/**
	 * @Fields serialVersionUID : 序列化
	 */ 
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键
	 */
    private Integer id;
    /**
     * 订单编号
     */
    private String orderno;
    /**
     * 到达时间
     */
    private Date arrivetime;
    /**
     * 添加时间
     */
    private Date addtime;
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOrderno() {
		return orderno;
	}
	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}
	public Date getArrivetime() {
		return arrivetime;
	}
	public void setArrivetime(Date arrivetime) {
		this.arrivetime = arrivetime;
	}
	public Date getAddtime() {
		return addtime;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	@Override
	public String toString() {
		return "OrderOberTime [id=" + id + ", orderno=" + orderno
				+ ", arrivetime=" + arrivetime + ", addtime=" + addtime + "]";
	}
    
    
}