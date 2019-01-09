package com.fh.entity.app.transitcenter;


public class AppUnloadScanReqData {
    /** 订单QR码 */
    private String baggageid;
    /** 订单类型：机-酒 酒-机 */
    private String type;
    /** 订单状态 */
    private String status;
    /** 角色id */
    private Integer roleid;
    /** 角色动作 */
    private String roletype;
    /** 角色动作是否完成 */
    private String isfinish;
    /** 目的地类型 */
    private String desttype;
    /** 目的地编码 */
    private String destaddress;

    public String getBaggageid() {
        return baggageid;
    }

    public void setBaggageid(String baggageid) {
        this.baggageid = baggageid;
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
}
