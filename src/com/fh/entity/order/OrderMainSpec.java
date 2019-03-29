package com.fh.entity.order;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 
 * 项目名称：oa   
 *
 * 类描述：订单表
 * 类名称：com.fh.entity.order.OrderMain     
 * 创建人：tangqm 
 * 创建时间：2018年1月23日 下午9:23:43   
 * 修改人：
 * 修改时间：2018年1月23日 下午9:23:43   
 * 修改备注：   
 * @version   V1.0
 */
public class OrderMainSpec implements  Serializable{
    
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
     * 合作伙伴订单编号
     */
    private String porderno;
   /**
    * 客户id
    */
    private String cusid;
    /**
     * 类型编码
     */
    private String type;
    /**
     *  类型名称
     */
    private String typeDesc;
    /**
     * 状态编码
     */
    private String status;
    /**
     *  状态名称
     */
    private String statusDesc;
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
     * 渠道编码
     */
    private String channel;
    /**
     * 渠道名称
     */
    private String channelDesc;
    /**
     * 是否需要发票
     */
    private String neadinvoice;
    /**
     * 是否需要发票描述
     */
    private String neadinvoiceDesc;
    /**
     * 备注
     */
    private String notes;
    /**
     * 新增时间
     */
    private Date addtime;
    /**
     * 修改时间
     */
    private Date modifytime;
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
     * 出发地址
     */
    private String srcaddress;
	/**
	 * 寄件方式
	 */
	private String mailingway;
	/**
	 * 领回方式
	 */
	private String backway;
    /**
     * 到达地址
     */
    private String destaddress;
	/**
	 * 取件时间
	 */
	private String taketime;
	/**
	 *派件时间
	 */
	private String sendtime;
    /**
     * 取派员id
     */
    private String []userid;
    /** 客户姓名拼音 */
    private String namespell;
    /** 客户姓名首字母拼音  */
    private String namespellinitial;
    /** 航班号  */
    private String flightno;
    /** 提取码 */
    private String fetchcode;
    /** 角色id */
    private Integer roleid;
    /** 角色类型 */
    private String roletype;
    /** 根据订单跟新订单状态 */
    private List<String> ordernoList;
    /** 根据订单跟新订单状态 */
    private List<Integer> orderidList;
    /** 备注 */
    private String remark;

    /* 案件编号 */
    private String casenum;
    /*  */
    private String isbusiness;


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

    public String getPorderno() {
        return porderno;
    }

    public void setPorderno(String porderno) {
        this.porderno = porderno;
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

    public String getStatus() {
        return status;
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

    public String getNeadinvoice() {
        return neadinvoice;
    }

    public void setNeadinvoice(String neadinvoice) {
        this.neadinvoice = neadinvoice;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public String getChannelDesc() {
        return channelDesc;
    }

    public void setChannelDesc(String channelDesc) {
        this.channelDesc = channelDesc;
    }

    public String getNeadinvoiceDesc() {
        return neadinvoiceDesc;
    }

    public void setNeadinvoiceDesc(String neadinvoiceDesc) {
        this.neadinvoiceDesc = neadinvoiceDesc;
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
	
	public String[] getUserid() {
		return userid;
	}

	public void setUserid(String[] userid) {
		this.userid = userid;
	}

    public Date getModifytime() {
        return modifytime;
    }

    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
    }

    public String getNamespell() {
        return namespell;
    }

    public void setNamespell(String namespell) {
        this.namespell = namespell;
    }

    public String getNamespellinitial() {
        return namespellinitial;
    }

    public void setNamespellinitial(String namespellinitial) {
        this.namespellinitial = namespellinitial;
    }

    public String getFlightno() {
        return flightno;
    }

    public void setFlightno(String flightno) {
        this.flightno = flightno;
    }

    public String getFetchcode() {
        return fetchcode;
    }

    public void setFetchcode(String fetchcode) {
        this.fetchcode = fetchcode;
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

    public List<String> getOrdernoList() {
        return ordernoList;
    }

    public void setOrdernoList(List<String> ordernoList) {
        this.ordernoList = ordernoList;
    }

    public List<Integer> getOrderidList() {
        return orderidList;
    }

    public void setOrderidList(List<Integer> orderidList) {
        this.orderidList = orderidList;
    }

    public String getMailingway() {
		return mailingway;
	}

	public void setMailingway(String mailingway) {
		this.mailingway = mailingway;
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
    
	public String getBackway() {
		return backway;
	}

	public void setBackway(String backway) {
		this.backway = backway;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getCasenum() {
        return casenum;
    }

    public void setCasenum(String casenum) {
        this.casenum = casenum;
    }

    public String getIsbusiness() {
        return isbusiness;
    }

    public void setIsbusiness(String isbusiness) {
        this.isbusiness = isbusiness;
    }

    @Override
	public String toString() {
		return "OrderMainSpec [id=" + id + ", orderno=" + orderno
				+ ", porderno=" + porderno + ", cusid=" + cusid + ", type="
				+ type + ", typeDesc=" + typeDesc + ", status=" + status
				+ ", statusDesc=" + statusDesc + ", totalmoney=" + totalmoney
				+ ", cutmoney=" + cutmoney + ", num=" + num + ", channel="
				+ channel + ", channelDesc=" + channelDesc + ", neadinvoice="
				+ neadinvoice + ", neadinvoiceDesc=" + neadinvoiceDesc
				+ ", notes=" + notes + ", addtime=" + addtime + ", modifytime="
				+ modifytime + ", cusname=" + cusname + ", cusmobile="
				+ cusmobile + ", actualmoney=" + actualmoney + ", sendername="
				+ sendername + ", senderphone=" + senderphone
				+ ", receivername=" + receivername + ", receiverphone="
				+ receiverphone + ", srcaddress=" + srcaddress
				+ ", mailingway=" + mailingway + ", backway=" + backway
				+ ", destaddress=" + destaddress + ", taketime=" + taketime
				+ ", sendtime=" + sendtime + ", userid="
				+ Arrays.toString(userid) + ", namespell=" + namespell
				+ ", namespellinitial=" + namespellinitial + ", flightno="
				+ flightno + ", fetchcode=" + fetchcode + ", roleid=" + roleid
				+ ", roletype=" + roletype + ", ordernoList=" + ordernoList
				+ ", orderidList=" + orderidList + "]";
	}

	

}