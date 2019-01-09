package com.fh.entity.app.transitcenter;

/**
 * 集散中心查询接口
 * @author tqm
 * @date 2018年11月6日
 */
public class TransitOrderReqData {

    /** 角色动作 */
    private String roletype;
    /** 角色动作是否完成 */
    private String isfinish;
    /** 集散中心id */
    private String srcaddress;
    /** 目的地类型 */
    private String desttype;
    /** 目标地地址 */
    private String destaddress;
    /** 查询范围 */
    private String queryScope;

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

    public String getQueryScope() {
        return queryScope;
    }

    public void setQueryScope(String queryScope) {
        this.queryScope = queryScope;
    }

	public String getSrcaddress() {
		return srcaddress;
	}

	public void setSrcaddress(String srcaddress) {
		this.srcaddress = srcaddress;
	}
}
