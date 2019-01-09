package com.fh.entity.app.order;

import java.io.Serializable;
import java.util.List;

import com.fh.entity.order.OrderRole;

/**
 * @desc 订单信息详情
 * @auther zhangjj
 * @history 2018年2月6日
 */
public class AppOrderDetailsReqData implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 订单内部编码 */
    private Integer orderid;
    /** 订单编码 */
    private String orderno;
    /** 提取码 */
    private String fetchcode;
    /** 行李QR码 */
    private String baggageid;
    /** 订单详情查询类型 */
    private List<String> queryDetailsType;
    /** 
     * 	角色动作
     *  roletype角色动作类型:配合订单id可以得知目前分配的取派员
     *  isfinish角色动作是否完成，配合角色动作类型使用
     */
    private List<OrderRole> orderRoleList;

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public List<String> getQueryDetailsType() {
        return queryDetailsType;
    }

    public void setQueryDetailsType(List<String> queryDetailsType) {
        this.queryDetailsType = queryDetailsType;
    }

    public String getFetchcode() {
        return fetchcode;
    }

    public void setFetchcode(String fetchcode) {
        this.fetchcode = fetchcode;
    }
    
    public String getBaggageid() {
        return baggageid;
    }

    public void setBaggageid(String baggageid) {
        this.baggageid = baggageid;
    }

    public List<OrderRole> getOrderRoleList() {
        return orderRoleList;
    }

    public void setOrderRoleList(List<OrderRole> orderRoleList) {
        this.orderRoleList = orderRoleList;
    }

	@Override
	public String toString() {
		return "AppOrderDetailsReqData [orderid=" + orderid + ", orderno="
				+ orderno + ", fetchcode=" + fetchcode + ", baggageid="
				+ baggageid + ", queryDetailsType=" + queryDetailsType
				+ ", orderRoleList=" + orderRoleList + "]";
	}
    
}
