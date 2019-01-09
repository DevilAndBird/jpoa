package com.fh.entity.h5;


public class H5OrderSenderReceiver 
{
	private String sendername;
	private String senderphone;
	private String receivername;
	private String receiverphone;
	private String senderidno;
	private String receiveridno;
	private Integer isreceiverself;
	/**
	 * 实际寄送人姓名
	 */
	private String realname;
	/**
	 * 实际寄送人电话
	 */
	private String realphone;
	
	public String getSendername() 
	{
		return sendername;
	}
	public void setSendername(String sendername) {
		this.sendername = sendername;
	}
	public String getSenderphone() {
		return senderphone;
	}
	public void setSenderphone(String senderphone) {
		this.senderphone = senderphone;
	}
	public String getReceivername() {
		return receivername;
	}
	public void setReceivername(String receivername) {
		this.receivername = receivername;
	}
	public String getReceiverphone() {
		return receiverphone;
	}
	public void setReceiverphone(String receiverphone) {
		this.receiverphone = receiverphone;
	}
	public String getSenderidno() {
		return senderidno;
	}
	public void setSenderidno(String senderidno) {
		this.senderidno = senderidno;
	}
	public String getReceiveridno() {
		return receiveridno;
	}
	public void setReceiveridno(String receiveridno) {
		this.receiveridno = receiveridno;
	}
	public Integer getIsreceiverself() {
		return isreceiverself;
	}
	public void setIsreceiverself(Integer isreceiverself) {
		this.isreceiverself = isreceiverself;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getRealphone() {
		return realphone;
	}
	public void setRealphone(String realphone) {
		this.realphone = realphone;
	}
	
}
