package com.fh.entity.app.order;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @desc 订单信息列表查询
 * @auther zhangjj tangqm
 * @history 2018年2月6日
 */
public class AppOrderReqData implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /** 订单内部编码 */
    private Integer id;
    /** 订单编码 */
    private String orderno;
    /** 订单类型：机-酒  酒-机 */
    private String type;
    /** 订单状态 */
    private String status;
    /** 客户名字首字母 */
    private String namespellinitial;
    /** 角色id */
    private Integer roleid;
    /** 角色动作 */
    private String roletype;
    /** 角色动作是否完成 */
    private String isfinish;
    /** 出发地类型 */
    private String srctype;
    /** 出发地地址 */
    private String srcaddress;
    /** 目的地类型 */
    private String desttype;
    /** 目标地地址 */
    private String destaddress;
    /** 经纬度  */
    private Map<String, Double> destaddressGps;
    /** 签字地址 */
    private String signurl;
    /** 签字姓名 */
    private String signname;
    /** 交接订单功能 */
    private List<Integer> orderidList;
    /** 交接订单功能 */
    private List<String> ordernoList;
    /** 更新类型 */
    private String updateType;
    /** 列表查询类型 */
    private List<String> listQueryType;
    /** 订单角色表 */
    private List<String> orderroleList;
    /** 是否完成集合 */
    private List<String> isfinishList;
    
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

	public String getSignurl() {
		return signurl;
	}

	public void setSignurl(String signurl) {
		this.signurl = signurl;
	}

    public List<Integer> getOrderidList() {
        return orderidList;
    }

    public void setOrderidList(List<Integer> orderidList) {
        this.orderidList = orderidList;
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

    public String getDestaddress() {
        return destaddress;
    }

    public void setDestaddress(String destaddress) {
        this.destaddress = destaddress;
    }

    public String getUpdateType() {
        return updateType;
    }

    public void setUpdateType(String updateType) {
        this.updateType = updateType;
    }

	public List<String> getListQueryType() {
		return listQueryType;
	}

	public void setListQueryType(List<String> listQueryType) {
		this.listQueryType = listQueryType;
	}

	public List<String> getOrderroleList() {
		return orderroleList;
	}

	public void setOrderroleList(List<String> orderroleList) {
		this.orderroleList = orderroleList;
	}

	public String getSignname() {
		return signname;
	}

	public void setSignname(String signname) {
		this.signname = signname;
	}

	public String getSrctype() {
		return srctype;
	}

	public void setSrctype(String srctype) {
		this.srctype = srctype;
	}

	public String getSrcaddress() {
		return srcaddress;
	}

	public void setSrcaddress(String srcaddress) {
		this.srcaddress = srcaddress;
	}

	public List<String> getIsfinishList() {
		return isfinishList;
	}

	public void setIsfinishList(List<String> isfinishList) {
		this.isfinishList = isfinishList;
	}

	public Map<String, Double> getDestaddressGps() {
		return destaddressGps;
	}

	public void setDestaddressGps(Map<String, Double> destaddressGps) {
		this.destaddressGps = destaddressGps;
	}

	@Override
	public String toString() {
		return "AppOrderReqData [id=" + id + ", orderno=" + orderno + ", type="
				+ type + ", status=" + status + ", namespellinitial="
				+ namespellinitial + ", roleid=" + roleid + ", roletype="
				+ roletype + ", isfinish=" + isfinish + ", srctype=" + srctype
				+ ", srcaddress=" + srcaddress + ", desttype=" + desttype
				+ ", destaddress=" + destaddress + ", destaddressGps="
				+ destaddressGps + ", signurl=" + signurl + ", signname="
				+ signname + ", orderidList=" + orderidList + ", ordernoList="
				+ ordernoList + ", updateType=" + updateType
				+ ", listQueryType=" + listQueryType + ", orderroleList="
				+ orderroleList + ", isfinishList=" + isfinishList + "]";
	}

}
