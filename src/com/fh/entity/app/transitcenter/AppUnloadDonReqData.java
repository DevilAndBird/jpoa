package com.fh.entity.app.transitcenter;

import java.util.List;
/**
 * 卸车动作请求类
 * @author tqm
 * @date 2018年11月6日
 */
public class AppUnloadDonReqData {
    /** 角色取派员id */
    private Integer unloadBeforeRoleid;
    /** 角色动作 */
    private String unloadBeforeRoletype;
    /** 角色动作是否文成 */
    private String unloadBeforeInfinish;
    /** 角色集散中心人员id */
    private Integer unloadLaterRoleid;
    /** 角色动作 */
    private String unloadLaterRoletype;
    /** 角色动作是否完成 */
    private String unloadLaterInfinish;
    /** 目的地类型 */
    private String desttype;
    /** 目的地地址 */
    private String destaddress;
    /** 订单列表 */
    private List<Integer> orderidList;
    /** 卸货完毕前订单状态 */
    private String unloadBeforeStatus;
    /** 卸货完毕后订单状态状态 */
    private String unloadLaterStatus;
    
    private String roleid;

    public Integer getUnloadBeforeRoleid() {
		return unloadBeforeRoleid;
	}

	public void setUnloadBeforeRoleid(Integer unloadBeforeRoleid) {
		this.unloadBeforeRoleid = unloadBeforeRoleid;
	}

	public String getUnloadBeforeRoletype() {
        return unloadBeforeRoletype;
    }

    public void setUnloadBeforeRoletype(String unloadBeforeRoletype) {
        this.unloadBeforeRoletype = unloadBeforeRoletype;
    }

    public String getUnloadBeforeInfinish() {
        return unloadBeforeInfinish;
    }

    public void setUnloadBeforeInfinish(String unloadBeforeInfinish) {
        this.unloadBeforeInfinish = unloadBeforeInfinish;
    }

    public Integer getUnloadLaterRoleid() {
        return unloadLaterRoleid;
    }

    public void setUnloadLaterRoleid(Integer unloadLaterRoleid) {
        this.unloadLaterRoleid = unloadLaterRoleid;
    }

    public String getUnloadLaterRoletype() {
        return unloadLaterRoletype;
    }

    public void setUnloadLaterRoletype(String unloadLaterRoletype) {
        this.unloadLaterRoletype = unloadLaterRoletype;
    }

    public String getUnloadLaterInfinish() {
        return unloadLaterInfinish;
    }

    public void setUnloadLaterInfinish(String unloadLaterInfinish) {
        this.unloadLaterInfinish = unloadLaterInfinish;
    }

    public List<Integer> getOrderidList() {
        return orderidList;
    }

    public void setOrderidList(List<Integer> orderidList) {
        this.orderidList = orderidList;
    }

    public String getUnloadBeforeStatus() {
        return unloadBeforeStatus;
    }

    public void setUnloadBeforeStatus(String unloadBeforeStatus) {
        this.unloadBeforeStatus = unloadBeforeStatus;
    }

    public String getUnloadLaterStatus() {
        return unloadLaterStatus;
    }

    public void setUnloadLaterStatus(String unloadLaterStatus) {
        this.unloadLaterStatus = unloadLaterStatus;
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

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

}
