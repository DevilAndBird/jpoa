package com.fh.entity.app.order;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


public class AppOrderJicunReqData implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /** 目标地地址 */
    private String destaddress;

	public String getDestaddress() {
		return destaddress;
	}

	public void setDestaddress(String destaddress) {
		this.destaddress = destaddress;
	}
    
    
    
}
