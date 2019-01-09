package com.fh.entity.app.order;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


public class AppOrderAirportReqData implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /** 机场id */
    private Integer airportid;
    /** 订单类型 */
    private String ordertype;
    
    

	public String getOrdertype() {
		return ordertype;
	}

	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}

	public Integer getAirportid() {
		return airportid;
	}

	public void setAirportid(Integer airportid) {
		this.airportid = airportid;
	}

    



    
    
    
}
