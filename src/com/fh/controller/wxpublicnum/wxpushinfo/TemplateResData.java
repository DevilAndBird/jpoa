package com.fh.controller.wxpublicnum.wxpushinfo;

public class TemplateResData {
	
	private  Integer errcode;
	
	private  String errmsg;
	
	private  String msgid;

	public Integer getErrcode() {
		return errcode;
	}

	public void setErrcode(Integer errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public String getMsgid() {
		return msgid;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}

	@Override
	public String toString() {
		return "TemplateResData [errcode=" + errcode + ", errmsg=" + errmsg
				+ ", msgid=" + msgid + "]";
	}
	
}
