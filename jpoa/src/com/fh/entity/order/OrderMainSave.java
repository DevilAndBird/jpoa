package com.fh.entity.order;

import java.io.Serializable;

/**
 * 保存DTO
 * 
 * @author tangqm
 * @date 2018年3月23日
 */
public class OrderMainSave implements Serializable {

	/**
	 * @Fields serialVersionUID : 序列化
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 出发省份ID
	 * */
	private String srcprovid;
	/**
	 * 出发地市ID
	 * */
	private String srccityid;
	/**
	 * 出发区县ID
	 * */
	private String srcdistrictid;
	/**
	 * 到达省份ID
	 * */
	private String destprovid;
	/**
	 * 到达地市ID
	 * */
	private String destcityid;
	/**
	 * 到达区县ID
	 * */
	private String destdistrictid;
	/** 出发地类型  */
	private String srcaddrtype;
	/** 目的地类型 */
	private String destaddrtype;
	/** 出发地建筑物标志 */
	private String scrlandmark;
	/** 目的地建筑物标志 */
	private String destlandmark;
	/**
	 * 客户id
	 */
	private String cusid;
	/**
	 * 类型编码
	 */
	private String type;
	/**
	 * 类型名称
	 */
	private String typeDesc;
	/**
	 * 状态编码
	 */
	private String status;
	/**
	 * 订单总额
	 */
	private Float totalmoney;
	/**
	 * 减免额
	 */
	private Float cutmoney;
	/**
	 * 行李数量
	 */
	private Integer num;
	/**
	 * 渠道编码: "weixin"
	 */
	private String channel;
	/**
	 * 备注
	 */
	private String notes;
	/**
	 * 客户名
	 */
	private String cusname;
	/**
	 * 客户手机
	 */
	private String cusmobile;
	/**
	 * 实际金额
	 */
	private Float actualmoney;
	/**
	 * 寄件人名
	 */
	private String sendername;
	/**
	 * 寄件人手机号码
	 */
	private String senderphone;
	/**
	 * 收件人名
	 */
	private String receivername;
	/**
	 * 收件人手机号码
	 */
	private String receiverphone;
	/**
	 * 寄件方式
	 */
	private String mailingway;
	/**
	 * 领回方式
	 */
	private String backway;
	/**
	 * 保险编号
	 */
	private String insurecode;
	/**
	 * 航班号
	 */
	private String flightno;
	/**
	 * 取件时间
	 */
	private String taketime;
	/**
	 * 派件时间
	 */
	private String sendtime;
	/**
	 * 出发地
	 */
	private String srcaddress;
	/**
	 * 目的地
	 */
	private String destaddress;
	/**
	 * 待寄人
	 */
	private String realname;
	/**
	 * 待寄人手机
	 */
	private String realphone;
	/**
	 * 出发省份名字
	 * */
	private String srcprovname;
	/**
	 * 出发地市名称
	 * */
	private String srccityname;
	/**
	 * 出发区县名字
	 * */
	private String srcdistrictname;
	/**
	 * 到达省份名字
	 * */
	private String destprovname;
	/**
	 * 到达地市名字
	 * */
	private String destcityname;
	/**
	 * 到达区县名字
	 * */
	private String destdistrictname;

	public String getSrcprovid() {
		return srcprovid;
	}

	public void setSrcprovid(String srcprovid) {
		this.srcprovid = srcprovid;
	}

	public String getSrccityid() {
		return srccityid;
	}

	public void setSrccityid(String srccityid) {
		this.srccityid = srccityid;
	}

	public String getSrcdistrictid() {
		return srcdistrictid;
	}

	public void setSrcdistrictid(String srcdistrictid) {
		this.srcdistrictid = srcdistrictid;
	}

	public String getDestprovid() {
		return destprovid;
	}

	public void setDestprovid(String destprovid) {
		this.destprovid = destprovid;
	}

	public String getDestcityid() {
		return destcityid;
	}

	public void setDestcityid(String destcityid) {
		this.destcityid = destcityid;
	}

	public String getDestdistrictid() {
		return destdistrictid;
	}

	public void setDestdistrictid(String destdistrictid) {
		this.destdistrictid = destdistrictid;
	}

	public String getCusid() {
		return cusid;
	}

	public void setCusid(String cusid) {
		this.cusid = cusid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypeDesc() {
		return typeDesc;
	}

	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Float getTotalmoney() {
		return totalmoney;
	}

	public void setTotalmoney(Float totalmoney) {
		this.totalmoney = totalmoney;
	}

	public Float getCutmoney() {
		return cutmoney;
	}

	public void setCutmoney(Float cutmoney) {
		this.cutmoney = cutmoney;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getCusname() {
		return cusname;
	}

	public void setCusname(String cusname) {
		this.cusname = cusname;
	}

	public String getCusmobile() {
		return cusmobile;
	}

	public void setCusmobile(String cusmobile) {
		this.cusmobile = cusmobile;
	}

	public Float getActualmoney() {
		return actualmoney;
	}

	public void setActualmoney(Float actualmoney) {
		this.actualmoney = actualmoney;
	}

	public String getSendername() {
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

	public String getMailingway() {
		return mailingway;
	}

	public void setMailingway(String mailingway) {
		this.mailingway = mailingway;
	}

	public String getBackway() {
		return backway;
	}

	public void setBackway(String backway) {
		this.backway = backway;
	}

	public String getFlightno() {
		return flightno;
	}

	public void setFlightno(String flightno) {
		this.flightno = flightno;
	}

	public String getTaketime() {
		return taketime;
	}

	public void setTaketime(String taketime) {
		this.taketime = taketime;
	}

	public String getSendtime() {
		return sendtime;
	}

	public void setSendtime(String sendtime) {
		this.sendtime = sendtime;
	}

	public String getInsurecode() {
		return insurecode;
	}

	public void setInsurecode(String insurecode) {
		this.insurecode = insurecode;
	}

	public String getSrcaddress() {
		return srcaddress;
	}

	public void setSrcaddress(String srcaddress) {
		this.srcaddress = srcaddress;
	}

	public String getDestaddress() {
		return destaddress;
	}

	public void setDestaddress(String destaddress) {
		this.destaddress = destaddress;
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

	public String getSrcprovname() {
		return srcprovname;
	}

	public void setSrcprovname(String srcprovname) {
		this.srcprovname = srcprovname;
	}

	public String getSrccityname() {
		return srccityname;
	}

	public void setSrccityname(String srccityname) {
		this.srccityname = srccityname;
	}

	public String getSrcdistrictname() {
		return srcdistrictname;
	}

	public void setSrcdistrictname(String srcdistrictname) {
		this.srcdistrictname = srcdistrictname;
	}

	public String getDestprovname() {
		return destprovname;
	}

	public void setDestprovname(String destprovname) {
		this.destprovname = destprovname;
	}

	public String getDestcityname() {
		return destcityname;
	}

	public void setDestcityname(String destcityname) {
		this.destcityname = destcityname;
	}

	public String getDestdistrictname() {
		return destdistrictname;
	}

	public void setDestdistrictname(String destdistrictname) {
		this.destdistrictname = destdistrictname;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getScrlandmark() {
		return scrlandmark;
	}

	public void setScrlandmark(String scrlandmark) {
		this.scrlandmark = scrlandmark;
	}

	public String getDestlandmark() {
		return destlandmark;
	}

	public void setDestlandmark(String destlandmark) {
		this.destlandmark = destlandmark;
	}

	public String getSrcaddrtype() {
		return srcaddrtype;
	}

	public void setSrcaddrtype(String srcaddrtype) {
		this.srcaddrtype = srcaddrtype;
	}

	public String getDestaddrtype() {
		return destaddrtype;
	}

	public void setDestaddrtype(String destaddrtype) {
		this.destaddrtype = destaddrtype;
	}

	@Override
	public String toString() {
		return "OrderMainSave [srcprovid=" + srcprovid + ", srccityid="
				+ srccityid + ", srcdistrictid=" + srcdistrictid
				+ ", destprovid=" + destprovid + ", destcityid=" + destcityid
				+ ", destdistrictid=" + destdistrictid + ", cusid=" + cusid
				+ ", type=" + type + ", typeDesc=" + typeDesc + ", status="
				+ status + ", totalmoney=" + totalmoney + ", cutmoney="
				+ cutmoney + ", num=" + num + ", channel=" + channel
				+ ", notes=" + notes + ", cusname=" + cusname + ", cusmobile="
				+ cusmobile + ", actualmoney=" + actualmoney + ", sendername="
				+ sendername + ", senderphone=" + senderphone
				+ ", receivername=" + receivername + ", receiverphone="
				+ receiverphone + ", mailingway=" + mailingway + ", backway="
				+ backway + ", insurecode=" + insurecode + ", flightno="
				+ flightno + ", taketime=" + taketime + ", sendtime="
				+ sendtime + ", srcaddress=" + srcaddress + ", destaddress="
				+ destaddress + ", realname=" + realname + ", realphone="
				+ realphone + ", srcprovname=" + srcprovname + ", srccityname="
				+ srccityname + ", srcdistrictname=" + srcdistrictname
				+ ", destprovname=" + destprovname + ", destcityname="
				+ destcityname + ", destdistrictname=" + destdistrictname + "]";
	}


}