package com.fh.entity.app.order;

import java.io.Serializable;
import java.util.List;

import com.fh.entity.app.counterservice.AppCounterCenter;
import com.fh.entity.app.transitcenter.AppTransitCenter;
import com.fh.entity.delivery.BaiduCoord;
import com.fh.entity.order.OrderBaggage;
import com.fh.entity.order.OrderRole;

/**
 * @desc 订单信息列表查询
 * @auther zhangjj tangqm
 * @history 2018年2月6日
 */
public class AppOrderResData implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 订单内部 */
    private Integer id;
    /** 订单编码 */
    private String orderno;
    /** 订单类型：机-酒  酒-机 */
    private String type;
    /** 订单状态 */
    private String status;
    /** 行李数 */
    private Integer num;
    /** 客户名字拼音首字母 */
    private String namespellinitial;
    /** 客户名字拼音 */
    private String namespell;
    /** 航班号 */
    private String flightno;
    /** 订单行李总数总数 */
    private Integer baggageNum;
    /** 是否完成 */
    private String isfinish;
    /** 是否完成 */
    private String channel;
    /** 动作类型 */
    private String roletype;
    /** 行驶状态 */
    private String travelstatus;
    /** 出发地类型*/
    private String srctype;
    /** 出发id */
    private String srcaddress;
    /** 出发地简称 */
    private String sraddrname;
    /** 出发地地址详情 */
    private String srcaddrdesc;
    /** 目的地类型*/
    private String desttype;
    /** 目的地id */
    private String destaddress;
    /** 目的名简称 */
    private String destaddrname;
    /** 目的地详情地址 */
    private String destaddrdesc;
    /**取派员姓名*/
    private String rolename;
    /**客户姓名*/
    private String cusname;
    /** 订单QR码查询 */
    private List<OrderBaggage> orderBaggageList;
    /** 经过的集散中心 */
    private List<AppTransitCenter> appTransitCenterList;
    /** 机场 */
    private AppCounterCenter appCounterCenter;
    /** 取件时间 */
    private String taketime;
    /** 送件时间 */
    private String sendtime;
    /** 是否抵达*/
    private String isarrived;
    /** 机场是否收件*/
    private String istake;
    /** 是否隔夜*/
    private String isnight;
    /** 最迟到达时间 */
    private long arrivedtime;
    /** 下一个绑定的动作 */
    private OrderRole nextBindAction;
    /** 订单的动作角色信息 */
    private Integer roleid;
    /** 寄件方式 */
    private String mailingway;
    /** 领取方式 */
    private String backway;
    /** 寄件人联系号码 */
    private String senderphone;
    /** 收件人联系号码 */
    private String receiverphone;
    /** 目的地经纬度 */
    private BaiduCoord destaddressGps;
    /** 目的地经纬度 */
    private String destGps;
    /** 当前时间戳 */
    private long currentstamp;

    public Integer getRoleid() {
		return roleid;
	}

	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}

	public OrderRole getNextBindAction() {
		return nextBindAction;
	}

	public void setNextBindAction(OrderRole nextBindAction) {
		this.nextBindAction = nextBindAction;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNamespellinitial() {
        return namespellinitial;
    }

    public void setNamespellinitial(String namespellinitial) {
        this.namespellinitial = namespellinitial;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getNamespell() {
        return namespell;
    }

    public void setNamespell(String namespell) {
        this.namespell = namespell;
    }

    public String getFlightno() {
        return flightno;
    }

    public void setFlightno(String flightno) {
        this.flightno = flightno;
    }

    public Integer getBaggageNum() {
        return baggageNum;
    }

    public void setBaggageNum(Integer baggageNum) {
        this.baggageNum = baggageNum;
    }

	public String getDestaddress() {
		return destaddress;
	}

	public void setDestaddress(String destaddress) {
		this.destaddress = destaddress;
	}

	public String getDestaddrname() {
		return destaddrname;
	}

	public void setDestaddrname(String destaddrname) {
		this.destaddrname = destaddrname;
	}

	public List<OrderBaggage> getOrderBaggageList() {
		return orderBaggageList;
	}

	public void setOrderBaggageList(List<OrderBaggage> orderBaggageList) {
		this.orderBaggageList = orderBaggageList;
	}

	public List<AppTransitCenter> getAppTransitCenterList() {
		return appTransitCenterList;
	}

	public void setAppTransitCenterList(List<AppTransitCenter> appTransitCenterList) {
		this.appTransitCenterList = appTransitCenterList;
	}

	public AppCounterCenter getAppCounterCenter() {
		return appCounterCenter;
	}

	public void setAppCounterCenter(AppCounterCenter appCounterCenter) {
		this.appCounterCenter = appCounterCenter;
	}

	public String getSrcaddress() {
		return srcaddress;
	}

	public void setSrcaddress(String srcaddress) {
		this.srcaddress = srcaddress;
	}

	public String getSraddrname() {
		return sraddrname;
	}

	public void setSraddrname(String sraddrname) {
		this.sraddrname = sraddrname;
	}

	public String getSrctype() {
		return srctype;
	}

	public void setSrctype(String srctype) {
		this.srctype = srctype;
	}

	public String getDesttype() {
		return desttype;
	}

	public void setDesttype(String desttype) {
		this.desttype = desttype;
	}

    public String getRoletype() {
        return roletype;
    }

    public void setRoletype(String roletype) {
        this.roletype = roletype;
    }

	public String getIsfinish() {
		return isfinish;
	}

	public void setIsfinish(String isfinish) {
		this.isfinish = isfinish;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public String getCusname() {
		return cusname;
	}

	public void setCusname(String cusname) {
		this.cusname = cusname;
	}

	public String getTravelstatus() {
		return travelstatus;
	}

	public void setTravelstatus(String travelstatus) {
		this.travelstatus = travelstatus;
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

	public String getSrcaddrdesc() {
		return srcaddrdesc;
	}

	public void setSrcaddrdesc(String srcaddrdesc) {
		this.srcaddrdesc = srcaddrdesc;
	}

	public String getDestaddrdesc() {
		return destaddrdesc;
	}

	public void setDestaddrdesc(String destaddrdesc) {
		this.destaddrdesc = destaddrdesc;
	}

	public String getIsarrived() {
		return isarrived;
	}

	public void setIsarrived(String isarrived) {
		this.isarrived = isarrived;
	}
	
	public long getArrivedtime() {
		return arrivedtime;
	}

	public void setArrivedtime(long arrivedtime) {
		this.arrivedtime = arrivedtime;
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

	public String getSenderphone() {
		return senderphone;
	}

	public void setSenderphone(String senderphone) {
		this.senderphone = senderphone;
	}

	public String getReceiverphone() {
		return receiverphone;
	}

	public void setReceiverphone(String receiverphone) {
		this.receiverphone = receiverphone;
	}

	public BaiduCoord getDestaddressGps() {
		return destaddressGps;
	}

	public void setDestaddressGps(BaiduCoord destaddressGps) {
		this.destaddressGps = destaddressGps;
	}

	public long getCurrentstamp() {
		return currentstamp;
	}

	public void setCurrentstamp(long currentstamp) {
		this.currentstamp = currentstamp;
	}

	public String getDestGps() {
		return destGps;
	}

	public void setDestGps(String destGps) {
		this.destGps = destGps;
	}

	public String getIstake() {
		return istake;
	}

	public void setIstake(String istake) {
		this.istake = istake;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getIsnight() {
		return isnight;
	}

	public void setIsnight(String isnight) {
		this.isnight = isnight;
	}
	
}
