package com.fh.entity.app.dm;

import java.util.List;

public class AppOrders {
	private List<AppOrderMain> onGoingOrders;	//进行中订单
	private List<AppOrderMain> gettingOrders;	//待取订单
	private List<AppOrderMain> sendingOrders;	//待派订单
	private List<AppOrderMain> finishedOrders;	//已完成订单
	
	public List<AppOrderMain> getOnGoingOrders() {
		return onGoingOrders;
	}
	public void setOnGoingOrders(List<AppOrderMain> onGoingOrders) {
		this.onGoingOrders = onGoingOrders;
	}
	public List<AppOrderMain> getGettingOrders() {
		return gettingOrders;
	}
	public void setGettingOrders(List<AppOrderMain> gettingOrders) {
		this.gettingOrders = gettingOrders;
	}
	public List<AppOrderMain> getSendingOrders() {
		return sendingOrders;
	}
	public void setSendingOrders(List<AppOrderMain> sendingOrders) {
		this.sendingOrders = sendingOrders;
	}
	public List<AppOrderMain> getFinishedOrders() {
		return finishedOrders;
	}
	public void setFinishedOrders(List<AppOrderMain> finishedOrders) {
		this.finishedOrders = finishedOrders;
	}
}
