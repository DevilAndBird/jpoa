package com.fh.test.app.flowtest;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.fh.common.constant_enum.DESTINSATION_TYPE;
import com.fh.common.constant_enum.ISSCAN_TYPE;
import com.fh.common.constant_enum.IS_FINISH;
import com.fh.common.constant_enum.ORDER_STATUS;
import com.fh.common.constant_enum.QUERY_ORERYDETAILS_TYPE;
import com.fh.common.constant_enum.ROLE_TYPE;
import com.fh.common.constant_enum.UPDATE_TYPE;
import com.fh.entity.app.AppRequestBean;
import com.fh.entity.app.order.AppOrderDetailsReqData;
import com.fh.entity.app.order.AppOrderFinishReqData;
import com.fh.entity.app.order.AppOrderJicunReqData;
import com.fh.entity.app.order.AppOrderReqData;
import com.fh.entity.app.order.OrderBaggageReqData;
import com.fh.entity.app.transitcenter.AppUnloadDonReqData;
import com.fh.entity.app.transitcenter.AppUnloadScanReqData;
import com.fh.entity.app.userdelivery.UserDeliveryReqData;
import com.fh.entity.order.OrderRole;
import com.fh.util.HttpClientUtil;
import com.fh.util.MD5;
import com.google.gson.Gson;

/**
 * 集散中心单元测试
 * 
 * @author tangqm
 * @date 2018年3月15日
 */
public class AppTRJunitTest {

	public String urlHeader = "http://localhost:8080/jpoa/";// 地址头
//	public  String urlHeader = "http://47.96.186.145/jpoa/";//测试服务器
	
	
//    @Test
//    public void unloadScan() {
//        urlHeader+="/userdelivery/loadingScan";
//        AppUnloadScanReqData data  = new AppUnloadScanReqData();
//        data.setRoleid(71);
//        data.setRoletype(ROLE_TYPE.ROLE_TRANSIT_SEND.getValue());
//        data.setDesttype(DESTINSATION_TYPE.TRANSITCERTER.getValue());
//        data.setDestaddress("1");
//        commonTest(urlHeader,new Gson().toJson(data));
//    }
	
	/**
     * 取派员列表查询
     */
    @Test
    public void getDeliveryManListByParam() {
        urlHeader+="/userdelivery/getDeliveryManListByParam";
        UserDeliveryReqData data  = new UserDeliveryReqData();
        data.setDestaddress("1");
        data.setDesttype(DESTINSATION_TYPE.TRANSITCERTER.getValue());
        commonTest(urlHeader,new Gson().toJson(data));
    }
    
    /**
	 * 待卸车列表 待装车列表
	 */
	@Test
	public void findAppOrderMainList_unload() {
		urlHeader += "apporder/findAppOrderMainList";
		AppOrderReqData data = new AppOrderReqData();
		data.setRoletype(ROLE_TYPE.ROLE_TRANSIT_SEND.getValue());
		data.setRoleid(75);
//		data.setRoletype(ROLE_TYPE.ROLE_TRANSIT_SEND.getValue());// 卸车
		data.setRoletype(ROLE_TYPE.ROLE_TRANSIT_TASK.getValue());// 装车
		data.setIsfinish(IS_FINISH.UNFINISHED.getValue());
		data.setDestaddress("1");
		data.setDesttype(DESTINSATION_TYPE.TRANSITCERTER.getValue());
		commonTest(urlHeader, new Gson().toJson(data));
	}
	
	/**
	 * @desc 5.3卸车扫描后加载订单列表查询(包涵QR码信息)
	 * @auther zhangjj
	 * @date 2018年3月24日
	 */
	@Test
    public void unloadScan() {
        urlHeader += "/appTransitCenter/unloadScan";
        AppUnloadScanReqData data  = new AppUnloadScanReqData();
        data.setRoleid(76);
        data.setRoletype(ROLE_TYPE.ROLE_TRANSIT_SEND.getValue());
        data.setIsfinish(IS_FINISH.ONGOING.getValue());
        data.setDesttype(DESTINSATION_TYPE.TRANSITCERTER.getValue());
        data.setDestaddress("1");
        commonTest(urlHeader,new Gson().toJson(data));
    }
	
	/**
	 * @desc 5.4 QR码查询订单信息
	 * @auther zhangjj
	 * @date 2018年3月24日
	 */
	@Test
    public void unloadScan_queryOrderByBagid() {
        urlHeader += "/appTransitCenter/unloadScan";
        AppUnloadScanReqData data  = new AppUnloadScanReqData();
        data.setBaggageid("JPQR00111212");
        commonTest(urlHeader,new Gson().toJson(data));
    }
	
	/**
	 * @desc 5.5 是否扫描通过
	 * @auther zhangjj
	 * @date 2018年3月24日
	 */
	@Test
    public void isScan() {
        urlHeader += "/appOrderBaggage/isScan";
        OrderBaggageReqData data  = new OrderBaggageReqData();
        data.setOrderid(38);
        data.setBaggageid("JPQR00111269");
        data.setIsscan(ISSCAN_TYPE.SCANNED.getValue());
        commonTest(urlHeader,new Gson().toJson(data));
    }
	
	/**
	 * @desc 卸车完成
	 * @auther zhangjj
	 * @date 2018年3月24日
	 */
	@Test
    public void unloadDone() {
        urlHeader += "/appTransitCenter/unloadDone";
        AppUnloadDonReqData data  = new AppUnloadDonReqData();
        List<Integer> orderidList = new ArrayList<Integer>();
        orderidList.add(229);
        orderidList.add(230);
        data.setOrderidList(orderidList);
//        data.setUnloadBeforeroleid(71);
        data.setUnloadLaterRoleid(82);
        data.setDestaddress("1");
        commonTest(urlHeader,new Gson().toJson(data));
    }
	
	/**
	 * 订单详情
	 */
	@Test
	public void findAppOrderDetails() {
		urlHeader += "apporder/findAppOrderDetails";
		List<String> queryDetailsType = new ArrayList<String>();
		AppOrderDetailsReqData data = new AppOrderDetailsReqData();
		data.setOrderid(87);
		queryDetailsType.add("ORDERBAGGAGE");
		queryDetailsType.add("ORDERADDRESS");
		queryDetailsType.add("ORDERFLIGHT");
		queryDetailsType.add("ORDERSENDERRECEIVER");
		data.setQueryDetailsType(queryDetailsType);
		commonTest(urlHeader, new Gson().toJson(data));
	}

	/**
	 * 扫描功能
	 */
	@Test
	public void scanQRCode() {
		urlHeader += "appOrderBaggage/scanQRCode";
		OrderBaggageReqData data = new OrderBaggageReqData();
		data.setOrderid(14);
		data.setBaggageid("JPQR001");
		commonTest(urlHeader, new Gson().toJson(data));
	}
	
	/**
	 * 装车扫描
	 */
	@Test
	public void updateOrderStatus() {
		urlHeader += "apporder/updateOrderStatus";
		AppOrderReqData data = new AppOrderReqData();
		data.setStatus(ORDER_STATUS.WAITTRUCELOADING.getValue());
		List<Integer>orderidList = new ArrayList<Integer>();
		orderidList.add(48);
		data.setOrderidList(orderidList);
		data.setUpdateType(UPDATE_TYPE.ORDERIDLIST.getValue());
		commonTest(urlHeader, new Gson().toJson(data));
	}
	

	/**
	 * 关联订单
	 */
	@Test
	public void saveOrderBaggage() {
		urlHeader += "appOrderBaggage/saveOrderBaggage";
		OrderBaggageReqData data = new OrderBaggageReqData();
		data.setOrderid(15);
		data.setBaggageid("JPQR002");
		commonTest(urlHeader, new Gson().toJson(data));
	}

	/**
	 * 图片上传
	 */
	@Test
	public void baggageImgUrlUpload() {
		urlHeader += "appOrderBaggage/baggageImgUrlUpload";
		OrderBaggageReqData data = new OrderBaggageReqData();
		data.setOrderid(14);
		data.setImgurl("https://www.baidu.com/s?tn=99006304_7_oem_dg");
		data.setBaggageid("JP001");
		commonTest(urlHeader, new Gson().toJson(data));
	}

	/**
	 * 订单详情
	 */
	@Test
	public void findAppOrderDetails_fenbo() {
		urlHeader += "apporder/findAppOrderDetails";
		AppOrderDetailsReqData data = new AppOrderDetailsReqData();
		data.setBaggageid("JPOR112233");;
		List<String> queryDetailsType = new ArrayList<String>();
		queryDetailsType.add("ORDERBAGGAGE");
		queryDetailsType.add("ORDERADDRESS");
		queryDetailsType.add("ORDERFLIGHT");
		queryDetailsType.add("ORDERSENDERRECEIVER");
		queryDetailsType.add(QUERY_ORERYDETAILS_TYPE.ORDERDMAN.getValue());
		data.setQueryDetailsType(queryDetailsType);
		
		List<OrderRole> orderRoleList = new ArrayList<OrderRole>();
		OrderRole orderRole = new OrderRole();
		orderRole.setRoletype(ROLE_TYPE.ROLE_TRANSIT_TASK.getValue());
		orderRole.setIsfinish(IS_FINISH.ONGOING.getValue());
		orderRoleList.add(orderRole);
		data.setOrderRoleList(orderRoleList);
		
		commonTest(urlHeader, new Gson().toJson(data));
	}

	public void commonTest(String url, String data) {
		AppRequestBean bean = new AppRequestBean();
		bean.setUser("130354218011");
		bean.setKey("admin444");
		bean.setTimestamp("" + System.currentTimeMillis());
		bean.setSign(MD5.md5("jingpei" + bean.getUser() + bean.getKey()
				+ bean.getTimestamp()));
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
	
	/**
	 * 寄存件查询
	 */
	@Test
    public void findAppOrderJicun() {
        urlHeader += "/apporder/findAppOrderJicun";
        AppOrderJicunReqData data  = new AppOrderJicunReqData();
        data.setDestaddress("2");
        commonTest(urlHeader,new Gson().toJson(data));
    }
	/**
	 * 完成件查询
	 */
	@Test
	public void findAppOrderFinish() {
		urlHeader += "/apporder/findAppOrderFinish";
		AppOrderFinishReqData data  = new AppOrderFinishReqData();
		data.setDestaddress("2");
		commonTest(urlHeader,new Gson().toJson(data));
	}

}
