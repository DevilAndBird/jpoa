package com.fh.entity.h5.wxpublicnum;

/**
 * 微信退款申请
 * @author sqp
 *
 */
public class WXRefundReqBean {
	
	/** 
	 * 订单内部编码
	 */
    private Integer id;
    /**
     * 商户订单号
     */
    private String out_trade_no;
    /**
     * 订单金额
     */
    private Float total_fee;
    /**
     * 退款金额
     */
    private Float refund_fee;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public Float getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(Float total_fee) {
		this.total_fee = total_fee;
	}
	public Float getRefund_fee() {
		return refund_fee;
	}
	public void setRefund_fee(Float refund_fee) {
		this.refund_fee = refund_fee;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	@Override
	public String toString() {
		return "WXRefundReqBean [id=" + id + ", out_trade_no=" + out_trade_no
				+ ", total_fee=" + total_fee + ", refund_fee=" + refund_fee
				+ "]";
	}
    	
}