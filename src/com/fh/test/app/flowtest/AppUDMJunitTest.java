package com.fh.test.app.flowtest;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.fh.common.constant_enum.DESTINSATION_TYPE;
import com.fh.common.constant_enum.IS_FINISH;
import com.fh.common.constant_enum.ORDER_STATUS;
import com.fh.common.constant_enum.QUERYCITYNODE_TYPE;
import com.fh.common.constant_enum.QUERY_ORERYDETAILS_TYPE;
import com.fh.common.constant_enum.ROLE_TYPE;
import com.fh.common.constant_enum.UPDATE_TYPE;
import com.fh.entity.app.AppRequestBean;
import com.fh.entity.app.dm.AppCityNodeReqData;
import com.fh.entity.app.dm.AppOrderUserBean;
import com.fh.entity.app.login.AppUserReqData;
import com.fh.entity.app.order.AppOrderDetailsReqData;
import com.fh.entity.app.order.AppOrderReqData;
import com.fh.entity.app.order.AppQRCodeReqData;
import com.fh.entity.app.order.OrderBaggageReqData;
import com.fh.entity.app.transitcenter.AppUnloadScanReqData;
import com.fh.entity.app.transitcenter.ApploadDonReqData;
import com.fh.entity.app.userdelivery.AppDmanCurrentGpsReqData;
import com.fh.entity.app.userdelivery.UserDeliveryReqData;
import com.fh.entity.order.OrderRole;
import com.fh.util.HttpClientUtil;
import com.google.gson.Gson;

/**
 * 取派员单元测试
 * @author tangqm
 * @date 2018年3月12日
 */
public class AppUDMJunitTest {

//	public  String urlHeader = "http://localhost/jpoa/";// 地址头
	public  String urlHeader = "http://47.96.186.145/jpoa/";//测试服务器
//	
	/**
	 * 任务列表_待取件
	 */
	@Test
	public void taskList_dman() {
		urlHeader+="/apporder/findAppOrderMainList";
		
		AppOrderReqData data = new AppOrderReqData();
//		data.setId(204);
		data.setRoleid(213);
//		data.setRoletype(ROLE_TYPE.ROLE_TRANSIT_SEND.getValue());
		List<String> orderroleList = new ArrayList<String>();
		orderroleList.add(ROLE_TYPE.ROLE_HOTEL_SEND.getValue());
		orderroleList.add(ROLE_TYPE.ROLE_TRANSIT_SEND.getValue());	
		orderroleList.add(ROLE_TYPE.ROLE_AIRPORT_SEND.getValue());
		data.setOrderroleList(orderroleList);
		data.setIsfinish(IS_FINISH.UNFINISHED.getValue());
		
		commonTest(urlHeader,new Gson().toJson(data));
	}
	/**
	 * 任务列表_待取件
	 */
	@Test
	public void findAppOrderTaskList_total() {
		urlHeader+="/apporder/findAppOrderTaskList_total";
		
		AppOrderReqData data = new AppOrderReqData();
//		data.setId(204);
		data.setRoleid(244);
//		data.setRoletype(ROLE_TYPE.ROLE_TRANSIT_SEND.getValue());
		List<String> orderroleList = new ArrayList<String>();
		orderroleList.add(ROLE_TYPE.ROLE_HOTEL_SEND.getValue());
		orderroleList.add(ROLE_TYPE.ROLE_TRANSIT_SEND.getValue());	
		orderroleList.add(ROLE_TYPE.ROLE_AIRPORT_SEND.getValue());
		data.setOrderroleList(orderroleList);
		data.setIsfinish(IS_FINISH.UNFINISHED.getValue());
		
		commonTest(urlHeader,new Gson().toJson(data));
	}
	
	@Test
	public void findsrcaddessGps() {
		urlHeader+="/userdelivery/findsrcaddessGps";
		AppOrderReqData data = new AppOrderReqData();
		commonTest(urlHeader,new Gson().toJson(data));
	}
	
	@Test
	public void findAppOrderNumTaskList() {
		urlHeader+="/apporder/findAppOrderNumTaskList";
		AppOrderReqData data = new AppOrderReqData();
		data.setRoleid(213);
		commonTest(urlHeader,new Gson().toJson(data));
	}
	
	@Test
	public void notifyAppCus() {
		urlHeader+="/apporder/notifyAppCus";
		AppOrderReqData data = new AppOrderReqData();
		data.setRoleid(213);
		data.setRoletype("ROLE_AIRPORT_TASK");
		data.setId(2191);
		commonTest(urlHeader,new Gson().toJson(data));
	}
	
	@Test
	public void findAppMapTaskList() {
		urlHeader+="/apporder/findAppMapTaskList";
		AppOrderReqData data = new AppOrderReqData();
		data.setRoleid(213);
		commonTest(urlHeader,new Gson().toJson(data));
	}
	
	
	/**
	 * 任务列表_待取件
	 */
	@Test
	public void findAppOrderTaskList() {
		urlHeader+="/apporder/findAppOrderTaskList_test1";
//        List<String>orderroleList = new ArrayList<String>();
//        List<String>isfinishList = new ArrayList<String>();
//        isfinishList.add(IS_FINISH.UNFINISHED.getValue());
		AppOrderReqData data = new AppOrderReqData();
		data.setRoleid(217);
		List<String> orderroleList = new ArrayList<String>();
		orderroleList.add(ROLE_TYPE.ROLE_HOTEL_TASK.getValue());
		orderroleList.add(ROLE_TYPE.ROLE_TRANSIT_TASK.getValue());	
		orderroleList.add(ROLE_TYPE.ROLE_AIRPORT_TASK.getValue());
//		data.setOrderroleList(orderroleList);
		List<String>isfinishList=new ArrayList<String>();
		isfinishList.add(IS_FINISH.UNFINISHED.getValue());
//		isfinishList.add(IS_FINISH.FINISHED.getValue());
		data.setIsfinishList(isfinishList);
		commonTest(urlHeader,new Gson().toJson(data));
	}
	
	/**
	 * 自动分配
	 */
	@Test
	public void autoAllot() {
		urlHeader+="/applogin/autoAllot";
		AppUserReqData data = new AppUserReqData();
		commonTest(urlHeader,new Gson().toJson(data));
	}
	
	/**
	 * s
	 */
	@Test
	public void taskList_dmanSe() {
		urlHeader+="/apporder/findAppOrderMainList";
		AppOrderReqData data = new AppOrderReqData();
		data.setRoleid(71);
		List<String> orderroleList = new ArrayList<String>();
		orderroleList.add(ROLE_TYPE.ROLE_HOTEL_TASK.getValue());
		orderroleList.add(ROLE_TYPE.ROLE_TRANSIT_TASK.getValue());
		orderroleList.add(ROLE_TYPE.ROLE_AIRPORT_TASK.getValue());
		orderroleList.add(ROLE_TYPE.ROLE_TRANSIT_TASK.getValue());
		data.setOrderroleList(orderroleList);
		data.setIsfinish(IS_FINISH.UNFINISHED.getValue());		
		commonTest(urlHeader,new Gson().toJson(data));
	}
	/**
	 * 装车确认
	 */
	@Test
	public void serviceCenterLoadDone() {
		urlHeader+="/userdelivery/serviceCenterLoadDone";
		ApploadDonReqData data = new ApploadDonReqData();
		List<Integer>orderList = new ArrayList<Integer>();
		orderList.add(394);
		data.setRoleid(113);
		data.setDestaddress("1");
		data.setOrderidList(orderList);
		commonTest(urlHeader,new Gson().toJson(data));
	}
	
	/**
	 * 确认发车
	 */
	@Test
	public void confirmDriverStarted() {
		urlHeader+="appdm/confirmDriverStarted";
		AppOrderReqData data = new AppOrderReqData();
		List<Integer>orderidList = new ArrayList<Integer>();
//		orderidList.add(875);
		orderidList.add(1962);
		data.setOrderidList(orderidList);
		data.setRoleid(213);
		data.setRoletype(ROLE_TYPE.ROLE_AIRPORT_TASK.getValue());
		commonTest(urlHeader,new Gson().toJson(data));
	}
	
	/**
	 * 任务列表_进行中
	 */
	@Test
	public void taskList_dman_ing() {
		urlHeader+="/apporder/findAppOrderMainList";
		
		AppOrderReqData data = new AppOrderReqData();
		
		data.setRoleid(75);
		List<String> orderroleList = new ArrayList<String>();
		orderroleList.add(ROLE_TYPE.ROLE_HOTEL_TASK.getValue());
//		data.setOrderroleList(orderroleList);
		data.setIsfinish(IS_FINISH.ONGOING.getValue());
		
		commonTest(urlHeader,new Gson().toJson(data));
	}
	
	/** 
	 *  即将到达
	 */
	@Test
	public void confirmDriverTaskArriving() {
		urlHeader+="appdm/confirmDriverTaskArriving";
		UserDeliveryReqData data = new UserDeliveryReqData();
		data.setRoleid(105);
		data.setRoletype(ROLE_TYPE.ROLE_TRANSIT_SEND.getValue());
		data.setIsfinish(IS_FINISH.ONGOING.getValue());
		data.setDesttype(DESTINSATION_TYPE.TRANSITCERTER.getValue());
		data.setDestaddress("1");
		commonTest(urlHeader,new Gson().toJson(data));
	}
//	
//	/**
//	 *  确认到达
//	 */
	@Test
	public void confirmDriverTaskArrived() {
		urlHeader+="appdm/confirmDriverTaskArrived";
		UserDeliveryReqData data = new UserDeliveryReqData();
		data.setRoleid(114);
		data.setRoletype(ROLE_TYPE.ROLE_AIRPORT_TASK.getValue());
		data.setIsfinish(IS_FINISH.ONGOING.getValue());
		data.setDesttype(DESTINSATION_TYPE.SERVICECERTER.getValue());
		data.setDestaddress("2");
		commonTest(urlHeader,new Gson().toJson(data));
	}
	
	/**
	 * 任务详情
	 */
	@Test
	public void findAppOrderDetails() {
		urlHeader+="apporder/findAppOrderDetails";
		List<String> queryDetailsType = new ArrayList<String>();
		AppOrderDetailsReqData data = new AppOrderDetailsReqData();
//		data.setOrderid(399);
		data.setOrderno("322424");
//		queryDetailsType.add("ORDERBAGGAGE");
//		queryDetailsType.add("ORDERADDRESS");
//		queryDetailsType.add("ORDERFLIGHT");
//		queryDetailsType.add("ORDERSENDERRECEIVER");
		queryDetailsType.add("ORDERDMAN");
		queryDetailsType.add(QUERY_ORERYDETAILS_TYPE.ORDERTRANSIT.getValue());
		List<OrderRole> orderrole = new ArrayList<OrderRole>();
		OrderRole ore = new OrderRole();
		ore.setRoletype(ROLE_TYPE.ROLE_HOTEL_TASK.getValue());
		ore.setIsfinish(IS_FINISH.ONGOING.getValue());
		orderrole.add(ore);
		data.setOrderRoleList(orderrole);
		data.setQueryDetailsType(queryDetailsType);
		commonTest(urlHeader,new Gson().toJson(data));
	}
	
	/**
	 * 扫描功能
	 */
	@Test
	public void scanQRCode() {
		urlHeader+="appOrderBaggage/scanQRCode";
		OrderBaggageReqData data = new OrderBaggageReqData();
		data.setOrderid(133);
		data.setBaggageid(" ");
		commonTest(urlHeader,new Gson().toJson(data));
	}
	
	/**
	 * 关联订单
	 */
	@Test
	public void saveOrderBaggage() {
		urlHeader+="appOrderBaggage/saveOrderBaggage";
		OrderBaggageReqData data = new OrderBaggageReqData();
		data.setOrderid(38);
		data.setBaggageid("JPQR00111269");
		commonTest(urlHeader,new Gson().toJson(data));
	}
	
	/**
	 * 图片上传
	 */
	@Test
	public void baggageImgUrlUpload() {
		urlHeader+="appOrderBaggage/baggageImgUrlUpload";
		OrderBaggageReqData data = new OrderBaggageReqData();
		data.setOrderid(38);
		data.setImgurl("https://www.baidu.com/s?tn=99006304_7_oem_d7");
		data.setBaggageid("JPQR00111269");
		commonTest(urlHeader,new Gson().toJson(data));
	}
	
	/**
	 * 确认取件
	 */
	@Test
	public void confirmTakenOrders() {
		urlHeader+="appdm/confirmTakenOrders";
		AppOrderUserBean data = new AppOrderUserBean();
		data.setOrderId(595);
		data.setRoleid(218);
		commonTest(urlHeader,new Gson().toJson(data));
	}
	
	/**
	 *  派送员列表查询
	 */
	@Test
	public void getDeliveryManListByParam() {
		urlHeader+="userdelivery/getDeliveryManListByParam";
		UserDeliveryReqData data = new UserDeliveryReqData();
		data.setDesttype(DESTINSATION_TYPE.SERVICECERTER.getValue());
		data.setDestaddress("1");
		commonTest(urlHeader,new Gson().toJson(data));
	}
	
	
	/**
	 * 签字
	 */
	@Test
	public void saveSignUrl() {
		urlHeader+="apporder/saveSignUrl";
		AppOrderReqData data = new AppOrderReqData();
		data.setId(22);
		data.setSignurl("https://www.baidu.com/s?tn=99006304_6_oem_dg&isource=infinity");
		data.setSignname("测试2018");
		commonTest(urlHeader,new Gson().toJson(data));
	}
	
	/**
	 *确认派件
	 */
	@Test
	public void confirmSentOrders() {
		urlHeader+="appdm/confirmSentOrders";
		AppOrderUserBean data = new AppOrderUserBean();
       data.setOrderId(23);
       data.setRoleid(71);
		commonTest(urlHeader,new Gson().toJson(data));
	}
	
	/**
	 * 待卸车列表
	 */
	@Test
	public void findAppOrderMainList() {
		urlHeader+="apporder/findAppOrderMainList";
		AppOrderReqData data = new AppOrderReqData();
		data.setRoleid(75);
		data.setRoletype(ROLE_TYPE.ROLE_TRANSIT_TASK.getValue());//集散中心
//	    data.setRoletype(ROLE_TYPE.ROLE_TRANSIT_SEND.getValue());//集散中心
		data.setIsfinish(IS_FINISH.ONGOING.getValue());// 
		data.setDestaddress("1");
		data.setDesttype(DESTINSATION_TYPE.TRANSITCERTER.getValue());
		commonTest(urlHeader,new Gson().toJson(data));
	}
	
	/**
	 * 申请卸车 
	 */
	@Test
	public void updateOrderStatus() {
		urlHeader+="apporder/updateOrderStatus";
		AppOrderReqData data  = new AppOrderReqData();
		data.setId(3034);
		data.setRoleid(123);
		data.setUpdateType(UPDATE_TYPE.ORDERID.getValue());
		data.setStatus(ORDER_STATUS.RELEASEOVER.getValue());
		commonTest(urlHeader,new Gson().toJson(data));
	}
	
	/**
	 * 生成订单
	 */
	@Test
	public void generateOrder() {
		urlHeader+="apporder/generateOrder";
		AppOrderReqData data  = new AppOrderReqData();
		data.setId(3034);
		data.setRoleid(212);
		data.setUpdateType(UPDATE_TYPE.ORDERID.getValue());
		data.setStatus(ORDER_STATUS.TAKEGOOGSOVER.getValue());
		commonTest(urlHeader,new Gson().toJson(data));
	}
	
	
	/**
	 *  装车扫描加载
	 */
	@Test
	public void loadingScan() {
		urlHeader+="userdelivery/loadingScan";
		AppUnloadScanReqData data = new AppUnloadScanReqData();
		data.setBaggageid("JPQR000003");
		commonTest(urlHeader,new Gson().toJson(data));
	}
	
	/**
	 *  装车扫描预加载
	 */
	@Test
	public void preLoadingScan() {
		urlHeader+="userdelivery/preLoadingScan";
		AppUnloadScanReqData data = new AppUnloadScanReqData();
		data.setRoleid(218);
		data.setRoletype("ROLE_TRANSIT_TASK");
		data.setIsfinish("ONGOING");
		data.setDesttype("TRANSITCERTER");
		data.setDestaddress("3");
		data.setIsfinish("ONGOING");
		data.setRoletype("ROLE_TRANSIT_TASK");
		data.setDestaddress("2");
		data.setRoleid(218);
		data.setDesttype("TRANSITCERTER");
		commonTest(urlHeader,new Gson().toJson(data));
	}
	
	/**
	 *  扫描通过
	 */
	@Test
	public void isScan() {
		urlHeader+="appOrderBaggage/isScan";
		AppUnloadScanReqData data = new AppUnloadScanReqData();
		data.setBaggageid("JPQR001");
		commonTest(urlHeader,new Gson().toJson(data));
	}
	
	/**
	 *  集散中心装车完毕
	 */
	@Test
	public void tranistLoadDone() {
		urlHeader+="userdelivery/tranistLoadDone";
		ApploadDonReqData data = new ApploadDonReqData();
		data.setRoleid(75);
		List<Integer> orderidList = new ArrayList<Integer>();
        orderidList.add(133);
		data.setOrderidList(orderidList);
		data.setDestaddress("1");
//		 data.setUnloadBeforeStatus(ORDER_STATUS.WAITTRUCELOADING.getValue());
//		 data.setUnloadLaterStatus(ORDER_STATUS.TRUCELOADING.getValue());
		commonTest(urlHeader,new Gson().toJson(data));
	}
	
	/**
	 *  派送员列表查询
	 */
	@Test
	public void findCityNodeByUserid() {
		urlHeader+="userdelivery/findCityNodeByUserid";
		AppCityNodeReqData data = new AppCityNodeReqData();
		data.setQueryCityNodeType(QUERYCITYNODE_TYPE.TRANSITCENTERLIST.getValue());
		data.setUserid(71);
		commonTest(urlHeader,new Gson().toJson(data));
	}

    /**
     * 更新取派员坐标
     */
    @Test
    public void updateGps() {
        urlHeader+="userdelivery/updateDmanCurrentGps";
        AppDmanCurrentGpsReqData data  = new AppDmanCurrentGpsReqData();
        
        data.setUserid(213);
        data.setName("测试4 闵行");
        data.setMobile("15766664444");
        data.setSrccityid("310000");        
        data.setCurrentgps("{'lng':'121.32101','lat':'31.198809'}");
        
//        data.setUserid(217);
//        data.setName("测试2");
//        data.setMobile("15766662222");
//        data.setSrccityid("310000");        
//        data.setCurrentgps("{'lng':'121.363205','lat':'31.209698'}");
//        
//        data.setUserid(214);
//        data.setName("测试3");
//        data.setMobile("15766663333");
//        data.setSrccityid("310000");        
//        data.setCurrentgps("{'lng':'121.46573','lat':'31.184734'}");
//        
//        data.setUserid(213);
//        data.setName("测试4");
//        data.setMobile("15766664444");
//        data.setSrccityid("310000");        
//        data.setCurrentgps("{'lng':'121.415146','lat':'31.110586'}");
        
//        data.setUserid(204);
//        data.setName("测试5");
//        data.setMobile("15766665555");
//        data.setSrccityid("310000");        
//        data.setCurrentgps("{'lng':'121.67352','lat':'31.173351'}");
        
//        data.setUserid(114);
//        data.setName("测试6");
//        data.setMobile("15766666666");
//        data.setSrccityid("310000");        
//        data.setCurrentgps("{'lng':'121.480644','lat':'31.224953'}");
//        
//        data.setUserid(113);
//        data.setName("测试7");
//        data.setMobile("15766667777");
//        data.setSrccityid("310000");        
//        data.setCurrentgps("{'lng':'121.55886','lat':'31.230167'}");
//        
//        data.setUserid(109);
//        data.setName("测试8");
//        data.setMobile("15766668888");
//        data.setSrccityid("310000");        
//        data.setCurrentgps("{'lng':'121.34322','lat':'31.310755'}");
        
        
        commonTest(urlHeader,new Gson().toJson(data));
    }
    
    
    @Test
    public void findQRCodeLimitPage() {
       urlHeader+="/qrCode/findQRCodeLimitPage";
       AppQRCodeReqData data = new AppQRCodeReqData();
       data.setLimit(1);
       commonTest(urlHeader,new Gson().toJson(data));
    }
	
	public void commonTest(String url,String data) {
		AppRequestBean bean = new AppRequestBean();
		bean.setUser("18752066141");
		bean.setKey("123456");
		bean.setTimestamp("" + System.currentTimeMillis());
		bean.setSign("ef554aa7b3dc60ad54bdc3210908dc8");
		bean.setData(data);
		Gson gson = new Gson();
		String json = gson.toJson(bean);
		System.out.println(json);
		try {
			String res = HttpClientUtil.doPostJson(url, json);
			System.out.println(res);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
}
