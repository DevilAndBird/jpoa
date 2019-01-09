package com.fh.entity.app.transitcenter;

import java.util.List;


public class ApploadDonReqData {
    /** 角色id */
    private Integer roleid;
    /** 角色类型 */
    private String roletype;
    /** 订单列表 */
    private List<Integer> orderidList;
    /** 卸货完毕前订单状态 */
    private String unloadBeforeStatus;
    /** 卸货完毕后订单状态状态 */
    private String unloadLaterStatus;
    /** 目的地id */
    private String destaddress;

    public Integer getRoleid() {
        return roleid;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
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

    public String getRoletype() {
        return roletype;
    }

    public void setRoletype(String roletype) {
        this.roletype = roletype;
    }

	public String getDestaddress() {
		return destaddress;
	}

	public void setDestaddress(String destaddress) {
		this.destaddress = destaddress;
	}
}
