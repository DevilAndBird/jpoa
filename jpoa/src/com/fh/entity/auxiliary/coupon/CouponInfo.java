package com.fh.entity.auxiliary.coupon;

import java.util.Date;

public class CouponInfo {
    private Integer id;
    /** 1 、手工指定用户优惠券  2、新用户优惠券  3、微信注册渠道用户优惠券  4、携程优惠券  */
    private Integer channel;	
    /** 优惠卷类型 */
    private String type;	//1、直减金额	2、满减金额	3、折扣
    /** 开始时间 */
    private Date startdate;
    /** 结束时间 */
    private Date invaliddate;
    /** 直减 */
    private Integer money;
    /** 满减 */
    private Integer fullmoney;
    /** 折扣 */
    private Float discount;
    /** 优惠码 */
    private String code; 
    /** 可用数量 */
    private Integer leftnum;
    /** 已使用数量 */
    private Integer usenum;
    /** 备注信息 */
    private String notes;
    /** 是否失效 */
    private String isvalid;//1：有效		2：无效
    /**创建人 */
    private String createdby;
    /** 添加时间 */
    private Date addtime;
    
    public Integer getLeftnum() {
		return leftnum;
	}

	public void setLeftnum(Integer leftnum) {
		this.leftnum = leftnum;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }
    

    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes == null ? null : notes.trim();
    }

    public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public Integer getFullmoney() {
        return fullmoney;
    }

    public void setFullmoney(Integer fullmoney) {
        this.fullmoney = fullmoney;
    }

    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

	public Integer getChannel() {
		return channel;
	}

	public void setChannel(Integer channel) {
		this.channel = channel;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getUsenum() {
		return usenum;
	}

	public void setUsenum(Integer usenum) {
		this.usenum = usenum;
	}

	public String getIsvalid() {
		return isvalid;
	}

	public void setIsvalid(String isvalid) {
		this.isvalid = isvalid;
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
	
}