package com.fh.entity.app.order;

import java.io.Serializable;

public class CountBaggageNumReqData implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 订单行李QR码 */
    private String baggageid;
    /** 角色动作 */
    private String roletype;
    /** 是否完成 */
    private String isfinish;
    /** 目的地类型 */
    private String desttype;
    /** 目标地地址 */
    private String destaddress;

    public String getBaggageid() {
        return baggageid;
    }

    public void setBaggageid(String baggageid) {
        this.baggageid = baggageid;
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
