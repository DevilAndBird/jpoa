package com.fh.entity.app.counterservice;

import java.util.List;

public class AppAffirmConnectReqData {
    /** 订单内部编码 */
    private List<Integer> orderidList;
    /** 取派员id */
    private Integer unloadBeforeRoleid;
    /** 角色动作 */
    private String unloadBeforeRoleType;
    /** 角色动作是否完成 */
    private String unloadBeforeIsfinish;
    /** 机场人员id */
    private Integer unloadLaterRoleid;
    /** 角色动作 */
    private String unloadLaterRoleType;
    /** 目标地类型 */
    private String desttype;
    /** 目标地地址 */
    private String destaddress;
    /** 卸货前订单状态 */
    private String unloadBeforeStatus;
    /** 卸货后订单状态状态 */
    private String unloadLaterStatus;

    public List<Integer> getOrderidList() {
        return orderidList;
    }

    public void setOrderidList(List<Integer> orderidList) {
        this.orderidList = orderidList;
    }

    public Integer getUnloadBeforeRoleid() {
        return unloadBeforeRoleid;
    }

    public void setUnloadBeforeRoleid(Integer unloadBeforeRoleid) {
        this.unloadBeforeRoleid = unloadBeforeRoleid;
    }

    public String getUnloadBeforeRoleType() {
        return unloadBeforeRoleType;
    }

    public void setUnloadBeforeRoleType(String unloadBeforeRoleType) {
        this.unloadBeforeRoleType = unloadBeforeRoleType;
    }

    public String getUnloadBeforeIsfinish() {
        return unloadBeforeIsfinish;
    }

    public void setUnloadBeforeIsfinish(String unloadBeforIsfinish) {
        this.unloadBeforeIsfinish = unloadBeforIsfinish;
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

    public Integer getUnloadLaterRoleid() {
        return unloadLaterRoleid;
    }

    public void setUnloadLaterRoleid(Integer unloadLaterRoleid) {
        this.unloadLaterRoleid = unloadLaterRoleid;
    }

    public String getUnloadLaterRoleType() {
        return unloadLaterRoleType;
    }

    public void setUnloadLaterRoleType(String unloadLaterRoleType) {
        this.unloadLaterRoleType = unloadLaterRoleType;
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
}
