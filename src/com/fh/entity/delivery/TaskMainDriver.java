package com.fh.entity.delivery;

import java.util.Date;

public class TaskMainDriver {
    private Integer id;
    /** 任务类型 */
    private String type;
    /** 出发地 */
    private String srcaddr;
    /** 目的地 */
    private String destaddr;
    /** 要求出发时间 */
    private Date depdatetime;
    /** 要求到达时间 */
    private Date arrdatetime;
    /** 状态 */
    private String status;
    /** 省份 */
    private String provid;
    /** 城市 */
    private String cityid;
    /** 司机 */
    private Integer driverid;
    
    private String createdby;
    
    private Date arrivingtime;
    
    private Date canceltime;
    
    private String cancelledby;
    
    private Date addtime;

    
    public Date getArrivingtime() {
		return arrivingtime;
	}

	public void setArrivingtime(Date arrivingtime) {
		this.arrivingtime = arrivingtime;
	}

	public Date getCanceltime() {
		return canceltime;
	}

	public void setCanceltime(Date canceltime) {
		this.canceltime = canceltime;
	}

	public String getCancelledby() {
		return cancelledby;
	}

	public void setCancelledby(String cancelledby) {
		this.cancelledby = cancelledby;
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

    public String getSrcaddr() {
        return srcaddr;
    }

    public void setSrcaddr(String srcaddr) {
        this.srcaddr = srcaddr;
    }

    public String getDestaddr() {
        return destaddr;
    }

    public void setDestaddr(String destaddr) {
        this.destaddr = destaddr;
    }

    public Date getDepdatetime() {
        return depdatetime;
    }

    public void setDepdatetime(Date depdatetime) {
        this.depdatetime = depdatetime;
    }

    public Date getArrdatetime() {
        return arrdatetime;
    }

    public void setArrdatetime(Date arrdatetime) {
        this.arrdatetime = arrdatetime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProvid() {
        return provid;
    }

    public void setProvid(String provid) {
        this.provid = provid;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public Integer getDriverid() {
        return driverid;
    }

    public void setDriverid(Integer driverid) {
        this.driverid = driverid;
    }

    public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }
}