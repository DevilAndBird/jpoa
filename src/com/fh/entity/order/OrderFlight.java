package com.fh.entity.order;

import java.io.Serializable;

/**
 * 订单航班信息
 * @author zhangjj
 * @date 2018年2月27日
 */
public class OrderFlight implements Serializable {

    private static final long serialVersionUID = 1L;
    /** 主键 */
    private Integer id;
    /** 订单编码 */
    private Integer orderid;
    /** 寄送时航班号 */
    private String sendflightno;
    /** 收取航班号 */
    private String takeflightno;
    /** 到达时间 */
    private String addtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }
  
    public String getSendflightno() {
		return sendflightno;
	}

	public void setSendflightno(String sendflightno) {
		this.sendflightno = sendflightno;
	}

	public String getTakeflightno() {
		return takeflightno;
	}

	public void setTakeflightno(String takeflightno) {
		this.takeflightno = takeflightno;
	}


    public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
