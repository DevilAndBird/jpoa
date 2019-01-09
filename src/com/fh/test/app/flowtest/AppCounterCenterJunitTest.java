package com.fh.test.app.flowtest;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.fh.common.constant_enum.DESTINSATION_TYPE;
import com.fh.common.constant_enum.IS_FINISH;
import com.fh.common.constant_enum.LISTQUERY_TYPE;
import com.fh.common.constant_enum.ORDER_STATUS;
import com.fh.common.constant_enum.QUERY_ORERYDETAILS_TYPE;
import com.fh.common.constant_enum.ROLE_TYPE;
import com.fh.common.constant_enum.UPDATE_TYPE;
import com.fh.entity.app.AppRequestBean;
import com.fh.entity.app.counterservice.AppAffirmConnectReqData;
import com.fh.entity.app.order.AppOrderDetailsReqData;
import com.fh.entity.app.order.AppOrderReqData;
import com.fh.entity.app.order.CountBaggageNumReqData;
import com.fh.entity.app.order.OrderBaggageReqData;
import com.fh.entity.app.userdelivery.UserDeliveryReqData;
import com.fh.entity.order.OrderRole;
import com.fh.util.HttpClientUtil;
import com.fh.util.MD5;
import com.google.gson.Gson;

/**
 * @des 柜台模块单元测试
 * @author zhangjj
 * @date 2018年3月12日
 */
public class AppCounterCenterJunitTest {

	//public   String urlHeader = "http://localhost/jpoa/";// 地址头
	public  String urlHeader = "http://47.96.186.145/jpoa/";//测试服务器
	
	/**
	 * 收取客户信息时扫描二维码得到订单编码
	 */
	@Test
	public void findAppOrderDetails() {
		urlHeader+="apporder/findAppOrderDetails";
		
		AppOrderDetailsReqData data = new AppOrderDetailsReqData();
		
		data.setOrderno("JPWX20180326112524054");
		
		List<String> queryDetailsType = new ArrayList<String>();
//		queryDetailsType.add(QUERY_ORERYDETAILS_TYPE.ORDERBAGAGE.getValue());
//		queryDetailsType.add(QUERY_ORERYDETAILS_TYPE.ORDERADDRESS.getValue());
		queryDetailsType.add(QUERY_ORERYDETAILS_TYPE.ORDERDMAN.getValue());
		queryDetailsType.add(QUERY_ORERYDETAILS_TYPE.ORDERTRANSIT.getValue());
		data.setQueryDetailsType(queryDetailsType);
		
		List<OrderRole> orderRoleList = new ArrayList<OrderRole>();
		OrderRole orderRole = new OrderRole();
		orderRole.setRoletype(ROLE_TYPE.ROLE_AIRPORT_TASK.getValue());
		orderRole.setIsfinish(IS_FINISH.ONGOING.getValue());
		orderRoleList.add(orderRole);
		data.setOrderRoleList(orderRoleList);
		
		commonTest(urlHeader,new Gson().toJson(data));
	}
	
	/**
	 * 关联订单
	 */
	@Test
	public void saveOrderBaggage() {
		urlHeader+="appOrderBaggage/saveOrderBaggage";
		
		OrderBaggageReqData data = new OrderBaggageReqData();
		data.setOrderid(21);
		data.setBaggageid("JPQR00SSS2661125");
		
		commonTest(urlHeader,new Gson().toJson(data));
	}
	
	/**
	 * 确认交接
	 */
	@Test
	public void affirmConnect() {
		urlHeader+="appCounterService/affirmConnect";
		AppAffirmConnectReqData data = new AppAffirmConnectReqData();
		List<Integer>orderidList= new ArrayList<Integer>();
		orderidList.add(133);
		data.setOrderidList(orderidList);
		data.setUnloadBeforeRoleid(75);
		data.setUnloadLaterRoleid(77);
		data.setDestaddress("1");
		commonTest(urlHeader,new Gson().toJson(data));
	}
	
	/**
     * 图片上传
     */
    @Test
    public void baggageImgUrlUpload() {
        urlHeader+="appOrderBaggage/baggageImgUrlUpload";
        
        OrderBaggageReqData data = new OrderBaggageReqData();
        data.setOrderid(21);
        data.setBaggageid("45");
        data.setImgurl("https://www.baidu.com/s?tn=99006304_7_oem_d25");
        
        commonTest(urlHeader,new Gson().toJson(data));
    }
    
    /**
	 * 删除关联QR码
	 */
	@Test
	public void deleteLinkQR() {
		urlHeader+="appOrderBaggage/deleteLinkQR";
		
		OrderBaggageReqData data = new OrderBaggageReqData();
		data.setOrderid(21);
		data.setBaggageid("JPQR00SSS266111");
		
		commonTest(urlHeader,new Gson().toJson(data));
	}
    
    
    /**
     * 柜台确认收件
     */
    @Test
    public void updateOrderStatus_TRAKGOOGSOVER() {
        urlHeader+="apporder/updateOrderStatus";
        AppOrderReqData data  = new AppOrderReqData();
        data.setId(21);
        data.setUpdateType(UPDATE_TYPE.ORDERID.getValue());
        data.setStatus(ORDER_STATUS.TAKEGOOGSOVER.getValue());
        commonTest(urlHeader,new Gson().toJson(data));
    }
    
    /**
     * 取派员列表查询
     */
    @Test
    public void getDeliveryManListByParam() {
        urlHeader+="/userdelivery/getDeliveryManListByParam";
        UserDeliveryReqData data  = new UserDeliveryReqData();
        data.setDestaddress("1");
        data.setDesttype(DESTINSATION_TYPE.SERVICECERTER.getValue());
        data.setIsfinish(IS_FINISH.ONGOING.getValue());
        commonTest(urlHeader,new Gson().toJson(data));
    }
    
    /**
	 * 待装车列表查询
	 */
	@Test
	public void findAppOrderMainList_load() {
		urlHeader+="apporder/findAppOrderMainList";
		AppOrderReqData data = new AppOrderReqData();
		data.setRoleid(113);
		data.setRoletype(ROLE_TYPE.ROLE_AIRPORT_TASK.getValue());//机场
		data.setIsfinish(IS_FINISH.ONGOING.getValue());
		data.setDestaddress("1");
		data.setDesttype(DESTINSATION_TYPE.SERVICECERTER.getValue());
		
		List<String> listQueryType = new ArrayList<String>();
		listQueryType.add(LISTQUERY_TYPE.TRANSIT.getValue());
//		data.setListQueryType(listQueryType);
		commonTest(urlHeader,new Gson().toJson(data));
	}
	
	/**
     * 申请装车
     */
    @Test
    public void updateOrderStatus_load() {
        urlHeader+="apporder/updateOrderStatus";
        AppOrderReqData data  = new AppOrderReqData();
        List<Integer> orderidList = new ArrayList<Integer>();
        orderidList.add(48);
        data.setOrderidList(orderidList);
        data.setUpdateType(UPDATE_TYPE.ORDERIDLIST.getValue());
        data.setStatus(ORDER_STATUS.WAITTRUCELOADING.getValue());
        commonTest(urlHeader,new Gson().toJson(data));
    }
	
	/**
	 * 待卸车列表查询
	 */
	@Test
	public void findAppOrderMainList_unload() {
		urlHeader+="apporder/findAppOrderMainList";
		AppOrderReqData data = new AppOrderReqData();
		data.setRoleid(71);
		data.setRoletype(ROLE_TYPE.ROLE_AIRPORT_SEND.getValue());//机场
		data.setIsfinish(IS_FINISH.ONGOING.getValue());
		data.setDestaddress("1");
		data.setDesttype("SERVICECERTER");
		commonTest(urlHeader,new Gson().toJson(data));
	}
	
	/**
	 *  卸车扫描
	 */
	@Test
	public void aaa() {
		urlHeader+="apporder/countBatchBagNumByQR";
		CountBaggageNumReqData data = new CountBaggageNumReqData();
		data.setBaggageid("JPQR1548");
		data.setRoletype(ROLE_TYPE.ROLE_AIRPORT_SEND.getValue());
		data.setIsfinish(IS_FINISH.ONGOING.getValue());
		data.setDesttype(DESTINSATION_TYPE.SERVICECERTER.getValue());
		data.setDestaddress("1");
		commonTest(urlHeader,new Gson().toJson(data));
	}
	
	/**
	 * (拿行李)柜台扫描QR码/收款码 获取订单信息
	 */
	@Test
	public void findAppOrderDetails_orderno_fetchcode() {
		urlHeader+="apporder/findAppOrderDetails";
		
		AppOrderDetailsReqData data = new AppOrderDetailsReqData();
		
		data.setOrderno("JPWX20180312180136016");
		
		List<String> queryDetailsType = new ArrayList<String>();
//		queryDetailsType.add(QUERY_ORERYDETAILS_TYPE.ORDERSENDERRECEIVER.getValue());
//		queryDetailsType.add(QUERY_ORERYDETAILS_TYPE.ORDERCUS.getValue());
//		queryDetailsType.add(QUERY_ORERYDETAILS_TYPE.ORDERBAGAGE.getValue());
//		queryDetailsType.add(QUERY_ORERYDETAILS_TYPE.ORDERADDRESS.getValue());
		queryDetailsType.add(QUERY_ORERYDETAILS_TYPE.ORDERBAGAGE.getValue());
//		queryDetailsType.add(QUERY_ORERYDETAILS_TYPE.ORDERDMAN.getValue());
//		queryDetailsType.add(QUERY_ORERYDETAILS_TYPE.ORDERTRANSIT.getValue());
//		queryDetailsType.add(QUERY_ORERYDETAILS_TYPE.ORDERFLIGHT.getValue());
//		queryDetailsType.add(QUERY_ORERYDETAILS_TYPE.ORDERNOTES.getValue());
		data.setQueryDetailsType(queryDetailsType);
		
//		List<OrderRole> orderRoleList = new ArrayList<OrderRole>();
//		OrderRole orderRole = new OrderRole();
//		orderRole.setRoletype(ROLE_TYPE.ROLE_AIRPORT_TASK.getValue());
//		orderRole.setIsfinish(IS_FINISH.ONGOING.getValue());
//		orderRoleList.add(orderRole);
//		data.setOrderRoleList(orderRoleList);
		
		commonTest(urlHeader,new Gson().toJson(data));
	}
	
	/**
     * 释放行李
     */
    @Test
    public void updateOrderStatus_RELEASEOVER() {
        urlHeader+="apporder/updateOrderStatus";
        AppOrderReqData data  = new AppOrderReqData();
        data.setId(21);
        data.setUpdateType(UPDATE_TYPE.ORDERID.getValue());
        data.setStatus(ORDER_STATUS.RELEASEOVER.getValue());
        commonTest(urlHeader,new Gson().toJson(data));
    }
	
	public void commonTest(String url,String data) {
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
}
