package com.fh.entity.customer;

import java.util.Date;

public class CusCouponInfo {
    /** 主键 */
    private Integer id;
    /** 客户号 */
    private Integer cusid;
    /** 优惠卷id */
    private Integer couponid;
    /** 优惠码 */
    private String couponcode;
    /** 订单id */
    private Integer orderid;
    /** 是否使用  */
    private String isused;
    /** 使用时间  */
    private Date usetime;
    /** 是否有效 */
    private String isvalid;
    /** 添加时间 */
    private Date addtime;
    
    /** 优惠卷码 */
    private String code;
    /** 优惠卷类型 */
    private String type;
    /** 满减 */
    private Float fullmoney;
    /** 优惠卷金额 */
    private Float money;
    /** 折扣 */
    private Float discount;
    /** 渠道 */
    private String channel;
    /** 开始时间 */
    private Date startdate;
    /** 结束时间 */
    private Date invaliddate;
    private String typeDesc;
    private Float couponmoney;
    private String isUsedDesc;
    private Date invalidtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCusid() {
		return cusid;
	}

	public void setCusid(Integer cusid) {
		this.cusid = cusid;
	}

	public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public Integer getCouponid() {
		return couponid;
	}

	public void setCouponid(Integer couponid) {
		this.couponid = couponid;
	}

	public Float getCouponmoney() {
        return couponmoney;
    }

    public void setCouponmoney(Float couponmoney) {
        this.couponmoney = couponmoney;
    }

    public String getIsused() {
        return isused;
    }

    public void setIsused(String isused) {
        this.isused = isused == null ? null : isused.trim();
    }

    public String getIsUsedDesc() {
        return isUsedDesc;
    }

    public void setIsUsedDesc(String isUsedDesc) {
        this.isUsedDesc = isUsedDesc;
    }

    public Date getUsetime() {
        return usetime;
    }

    public void setUsetime(Date usetime) {
        this.usetime = usetime;
    }

    public Date getInvalidtime() {
        return invalidtime;
    }

    public void setInvalidtime(Date invalidtime) {
        this.invalidtime = invalidtime;
    }

    public String getIsvalid() {
        return isvalid;
    }

    public void setIsvalid(String isvalid) {
        this.isvalid = isvalid == null ? null : isvalid.trim();
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Date getInvaliddate() {
		return invaliddate;
	}

	public void setInvaliddate(Date invaliddate) {
		this.invaliddate = invaliddate;
	}

	public Float getFullmoney() {
		return fullmoney;
	}

	public void setFullmoney(Float fullmoney) {
		this.fullmoney = fullmoney;
	}

	public Float getDiscount() {
		return discount;
	}

	public void setDiscount(Float discount) {
		this.discount = discount;
	}

	public String getCouponcode() {
		return couponcode;
	}

	public void setCouponcode(String couponcode) {
		this.couponcode = couponcode;
	}
	
}