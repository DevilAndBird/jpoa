package com.fh.entity.wxpublicnum.event;

public class InvoiceAccredit extends BaseEvent{

	/** 授权成功的订单号，与失败订单号两者必显示其一 */
	private String SuccOrderId;
	/** 授权失败的订单号，与成功订单号两者必显示其一 */
	private String FailOrderId;
	/** 获取授权页链接的AppId */
	private String AuthorizeAppId;
	/** 授权来源，web：公众号开票，app：app开票，wxa：小程序开票，wap：h5开票 */
	private String Source;

	public String getSuccOrderId() {
		return SuccOrderId;
	}

	public void setSuccOrderId(String succOrderId) {
		SuccOrderId = succOrderId;
	}

	public String getFailOrderId() {
		return FailOrderId;
	}

	public void setFailOrderId(String failOrderId) {
		FailOrderId = failOrderId;
	}

	public String getAuthorizeAppId() {
		return AuthorizeAppId;
	}

	public void setAuthorizeAppId(String authorizeAppId) {
		AuthorizeAppId = authorizeAppId;
	}

	public String getSource() {
		return Source;
	}

	public void setSource(String source) {
		Source = source;
	}
}
