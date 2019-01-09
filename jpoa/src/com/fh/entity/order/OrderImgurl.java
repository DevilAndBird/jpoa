package com.fh.entity.order;

import java.util.List;

public class OrderImgurl {
	/** 收取行李 */
	private List<String> COOLECT;
	/** 释放行李 */
	private List<String> RELEASE;

	public List<String> getCOOLECT() {
		return COOLECT;
	}

	public void setCOOLECT(List<String> cOOLECT) {
		COOLECT = cOOLECT;
	}

	public List<String> getRELEASE() {
		return RELEASE;
	}

	public void setRELEASE(List<String> rELEASE) {
		RELEASE = rELEASE;
	}
}
