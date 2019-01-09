package com.fh.entity.order;

import java.io.Serializable;

/**
 * 订单角色动作
 * 
 * @author zhangjj
 * @date 2018年3月13日
 */
public class OrderRole implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 主键 */
	private Integer id;
	/** 订单id */
	private Integer orderid;
	/** 取派员id */
	private Integer roleid;
	/** 角色是否完成 */
	private String roletype;
	/** 是否完成 */
	private String isfinish;
	/** 出发地类型 */
	private String srctype;
	/** 出发地id */
	private String srcaddress;
	/** 出发地简称 */
	private String srcaddrname;
	/** 出发地具体地址 */
	private String srcaddrdesc;
	/** 出发地gps */
	private String srcgps;
	/** 目的地类型 */
	private String desttype;
	/** 目的地id */
	private String destaddress;
	/** 目的名简称 */
	private String destaddrname;
	/** 目的地具体地址 */
	private String destaddrdesc;
	/** 目的地gps */
	private String destgps;
	/** 最迟到达时间 */
	private String arrivedtime;
	/** 是否显示 */
	private String isshow;
	/** 实际到达时间 */
	private String actionfinishtime;
	/** 角色姓名*/
	private String udmname;
	/**
	 * 角色姓名
	 */
	private String name;
	/** 角色手机号码 */
	private String mobile;
	private String roletypecodename;
	private String status;
	
	
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRoletypecodename() {
		return roletypecodename;
	}

	public void setRoletypecodename(String roletypecodename) {
		this.roletypecodename = roletypecodename;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOrderid() {
		return orderid;
	}

	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}

	public Integer getRoleid() {
		return roleid;
	}

	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}

	public String getRoletype() {
		return roletype;
	}

	public void setRoletype(String roletype) {
		this.roletype = roletype;
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

	public String getIsfinish() {
		return isfinish;
	}

	public void setIsfinish(String isfinish) {
		this.isfinish = isfinish;
	}

	public String getDesttype() {
		return desttype;
	}

	public void setDesttype(String desttype) {
		this.desttype = desttype;
	}

	public String getDestaddrname() {
		return destaddrname;
	}

	public void setDestaddrname(String destaddrname) {
		this.destaddrname = destaddrname;
	}

	public String getSrctype() {
		return srctype;
	}

	public void setSrctype(String srctype) {
		this.srctype = srctype;
	}

	public String getSrcaddrname() {
		return srcaddrname;
	}

	public void setSrcaddrname(String srcaddrname) {
		this.srcaddrname = srcaddrname;
	}

	public String getDestaddrdesc() {
		return destaddrdesc;
	}

	public void setDestaddrdesc(String destaddrdesc) {
		this.destaddrdesc = destaddrdesc;
	}

	public String getSrcaddrdesc() {
		return srcaddrdesc;
	}

	public void setSrcaddrdesc(String srcaddrdesc) {
		this.srcaddrdesc = srcaddrdesc;
	}

	public String getArrivedtime() {
		return arrivedtime;
	}

	public void setArrivedtime(String arrivedtime) {
		this.arrivedtime = arrivedtime;
	}

	public String getIsshow() {
		return isshow;
	}

	public void setIsshow(String isshow) {
		this.isshow = isshow;
	}

	public String getActionfinishtime() {
		return actionfinishtime;
	}

	public void setActionfinishtime(String actionfinishtime) {
		this.actionfinishtime = actionfinishtime;
	}

	public String getUdmname() {
		return udmname;
	}

	public void setUdmname(String udmname) {
		this.udmname = udmname;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getSrcgps() {
		return srcgps;
	}

	public void setSrcgps(String srcgps) {
		this.srcgps = srcgps;
	}

	public String getDestgps() {
		return destgps;
	}

	public void setDestgps(String destgps) {
		this.destgps = destgps;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
	public String toString() {
		return "OrderRole [id=" + id + ", orderid=" + orderid + ", roleid="
				+ roleid + ", roletype=" + roletype + ", isfinish=" + isfinish
				+ ", srctype=" + srctype + ", srcaddress=" + srcaddress
				+ ", srcaddrname=" + srcaddrname + ", srcaddrdesc="
				+ srcaddrdesc + ", desttype=" + desttype + ", destaddress="
				+ destaddress + ", destaddrname=" + destaddrname
				+ ", destaddrdesc=" + destaddrdesc + ", arrivedtime="
				+ arrivedtime + ", isshow=" + isshow + ", actionfinishtime="
				+ actionfinishtime + ", udmname=" + udmname + ", mobile="
				+ mobile + "]";
	}

}