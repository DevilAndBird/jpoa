package com.fh.entity.h5.wxpublicnum;


/**
 * 微信支付申请
 * @author tangqm
 *
 */
public class WXPayReqBean {
	/**
	 * 客户ip
	 */
    private String clientIp;
    /**
     * 公众号唯一标识
     */
    private String openId;
    /**
     * 订单编码
     */
    private String orderno;
    /**
     * 支付金额
     */
    private Integer totalFee;
    
	public String getClientIp() {
		return clientIp;
	}
	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getOrderno() {
		return orderno;
	}
	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}
	public Integer getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(Integer totalFee) {
		this.totalFee = totalFee;
	}
	@Override
	public String toString() {
		return "WXPayReqBean [clientIp=" + clientIp + ", openId=" + openId
				+ ", orderno=" + orderno + ", totalFee=" + totalFee + "]";
	}
	
}