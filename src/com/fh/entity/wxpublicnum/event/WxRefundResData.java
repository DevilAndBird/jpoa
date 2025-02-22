package com.fh.entity.wxpublicnum.event;

import java.io.Serializable;

public class WxRefundResData implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 返回状态码
	 */	
    private String return_code;
    /**
	 * 返回信息
	 */
    private String return_msg;
    /**
	 * 业务结果
	 */
    private String result_code;
    /**
	 * 错误代码
	 */
    private String err_code;
    /**
	 * 错误代码描述
	 */
    private String err_code_des;
    /**
	 * 公众账号ID
	 */
    private String appid;
    /**
	 * 商户号
	 */
    private String mch_id;
    /**
	 * 随机字符串
	 */
    private String nonce_str;
    /**
	 * 签名
	 */
    private String sign;
    /**
	 * 微信订单号
	 */
    private String transaction_id;
    /**
	 * 商户订单号
	 */
    private String out_trade_no;
    /**
	 * 微信退款单号
	 */
    private String out_refund_no;
    /**
	 * 微信退款单号
	 */
    private String refund_id;
    /**
	 * 退款金额
	 */
    private Integer refund_fee;
    /**
	 * 应结退款金额
	 */
    private Integer settlement_refund_fee;
    /**
	 * 标价金额
	 */
    private Integer total_fee;
    /**
	 * 应结订单金额
	 */
    private Integer settlement_total_fee;
    /**
	 * 标价币种
	 */
    private String fee_type;
    /**
	 * 现金支付金额
	 */
    private Integer cash_fee;
    /**
	 * 现金支付币种
	 */
    private String cash_fee_type;
    /**
	 * 现金退款金额
	 */
    private Integer cash_refund_fee;
    /**
	 * 代金券类型
	 */
    private String coupon_type_$n;
    /**
	 * 代金券退款总金额
	 */
    private Integer coupon_refund_fee;
    /**
	 * 单个代金券退款金额
	 */
    private Integer coupon_refund_fee_$n	;
    /**
	 * 退款代金券使用数量
	 */
    private Integer coupon_refund_count;
    /**
	 * 退款代金券ID
	 */
    private String coupon_refund_id_$n;
    /**
	 * 退款渠道
	 */
    private String refund_channel;
    
	public String getRefund_channel() {
		return refund_channel;
	}
	public void setRefund_channel(String refund_channel) {
		this.refund_channel = refund_channel;
	}
	public String getReturn_code() {
		return return_code;
	}
	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}
	public String getReturn_msg() {
		return return_msg;
	}
	public void setReturn_msg(String return_msg) {
		this.return_msg = return_msg;
	}
	public String getResult_code() {
		return result_code;
	}
	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}
	public String getErr_code() {
		return err_code;
	}
	public void setErr_code(String err_code) {
		this.err_code = err_code;
	}
	public String getErr_code_des() {
		return err_code_des;
	}
	public void setErr_code_des(String err_code_des) {
		this.err_code_des = err_code_des;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getMch_id() {
		return mch_id;
	}
	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}
	public String getNonce_str() {
		return nonce_str;
	}
	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getOut_refund_no() {
		return out_refund_no;
	}
	public void setOut_refund_no(String out_refund_no) {
		this.out_refund_no = out_refund_no;
	}
	public String getRefund_id() {
		return refund_id;
	}
	public void setRefund_id(String refund_id) {
		this.refund_id = refund_id;
	}
	public Integer getRefund_fee() {
		return refund_fee;
	}
	public void setRefund_fee(Integer refund_fee) {
		this.refund_fee = refund_fee;
	}
	public Integer getSettlement_refund_fee() {
		return settlement_refund_fee;
	}
	public void setSettlement_refund_fee(Integer settlement_refund_fee) {
		this.settlement_refund_fee = settlement_refund_fee;
	}
	public Integer getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(Integer total_fee) {
		this.total_fee = total_fee;
	}
	public Integer getSettlement_total_fee() {
		return settlement_total_fee;
	}
	public void setSettlement_total_fee(Integer settlement_total_fee) {
		this.settlement_total_fee = settlement_total_fee;
	}
	public String getFee_type() {
		return fee_type;
	}
	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}
	public Integer getCash_fee() {
		return cash_fee;
	}
	public void setCash_fee(Integer cash_fee) {
		this.cash_fee = cash_fee;
	}
	public String getCash_fee_type() {
		return cash_fee_type;
	}
	public void setCash_fee_type(String cash_fee_type) {
		this.cash_fee_type = cash_fee_type;
	}
	public Integer getCash_refund_fee() {
		return cash_refund_fee;
	}
	public void setCash_refund_fee(Integer cash_refund_fee) {
		this.cash_refund_fee = cash_refund_fee;
	}
	public String getCoupon_type_$n() {
		return coupon_type_$n;
	}
	public void setCoupon_type_$n(String coupon_type_$n) {
		this.coupon_type_$n = coupon_type_$n;
	}
	public Integer getCoupon_refund_fee() {
		return coupon_refund_fee;
	}
	public void setCoupon_refund_fee(Integer coupon_refund_fee) {
		this.coupon_refund_fee = coupon_refund_fee;
	}
	public Integer getCoupon_refund_fee_$n() {
		return coupon_refund_fee_$n;
	}
	public void setCoupon_refund_fee_$n(Integer coupon_refund_fee_$n) {
		this.coupon_refund_fee_$n = coupon_refund_fee_$n;
	}
	public Integer getCoupon_refund_count() {
		return coupon_refund_count;
	}
	public void setCoupon_refund_count(Integer coupon_refund_count) {
		this.coupon_refund_count = coupon_refund_count;
	}
	public String getCoupon_refund_id_$n() {
		return coupon_refund_id_$n;
	}
	public void setCoupon_refund_id_$n(String coupon_refund_id_$n) {
		this.coupon_refund_id_$n = coupon_refund_id_$n;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "WxRefundResData [return_code=" + return_code + ", return_msg="
				+ return_msg + ", result_code=" + result_code + ", err_code="
				+ err_code + ", err_code_des=" + err_code_des + ", appid="
				+ appid + ", mch_id=" + mch_id + ", nonce_str=" + nonce_str
				+ ", sign=" + sign + ", transaction_id=" + transaction_id
				+ ", out_trade_no=" + out_trade_no + ", out_refund_no="
				+ out_refund_no + ", refund_id=" + refund_id + ", refund_fee="
				+ refund_fee + ", settlement_refund_fee="
				+ settlement_refund_fee + ", total_fee=" + total_fee
				+ ", settlement_total_fee=" + settlement_total_fee
				+ ", fee_type=" + fee_type + ", cash_fee=" + cash_fee
				+ ", cash_fee_type=" + cash_fee_type + ", cash_refund_fee="
				+ cash_refund_fee + ", coupon_type_$n=" + coupon_type_$n
				+ ", coupon_refund_fee=" + coupon_refund_fee
				+ ", coupon_refund_fee_$n=" + coupon_refund_fee_$n
				+ ", coupon_refund_count=" + coupon_refund_count
				+ ", coupon_refund_id_$n=" + coupon_refund_id_$n + "]";
	}
 	
}
