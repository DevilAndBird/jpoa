package com.fh.test.app.flowtest;

import com.alibaba.fastjson.JSONObject;
import com.fh.common.constant_enum.INVICE_STATUS;
import com.fh.common.constant_enum.ORDER_ADDRESS_TYPE;
import com.fh.common.constant_enum.QUERY_ORERYDETAILS_TYPE;
import com.fh.entity.Page;
import com.fh.entity.app.AppRequestBean;
import com.fh.entity.app.order.AppOrderDetailsReqData;
import com.fh.entity.app.order.AppOrderReqData;
import com.fh.entity.app.userdelivery.AppDmanCurrentGpsReqData;
import com.fh.entity.customer.CusCompliantInfo;
import com.fh.entity.customer.CusInfo;
import com.fh.entity.h5.*;
import com.fh.entity.order.OrderInvoiceInfo;
import com.fh.test.base.BaseTest;
import com.fh.util.DateUtil;
import com.fh.util.HttpClientUtil;
import com.fh.util.MD5;
import com.fh.util.RedisUtil;
import com.google.gson.Gson;
import org.junit.Test;

import java.util.*;

/**
 * @des H5单元测试
 * @author tangqm
 * @date 2018年3月12日
 */
public class WebJunitTest extends BaseTest {

	public   String urlHeader = "http://localhost:8080/jpoa/";// 地址头
//	public  String urlHeader = "http://47.96.186.145/jpoa/";//测试服务器
	
	/**
     * 测试订单列表
     */
    @Test
    public void findWebOrderMainList() {
        urlHeader+="weborder/findWebOrderMainList";
        Page data  = new Page();
        commonTest(urlHeader,new Gson().toJson(data));
    }

    /**
     * 查询订单详情
     */
    @Test
    public void findWebOrderDetailList() {
        urlHeader+="weborder/findWebOrderDetailList";
		//组装参数
		AppOrderDetailsReqData reqDetails = new AppOrderDetailsReqData();
		//去除带零点的String类型
		reqDetails.setOrderid(((Number) (Float.parseFloat("5814.0"))).intValue());
		List<String> queryDetailsType = new ArrayList<String>();
		// 客户信息
		queryDetailsType.add(QUERY_ORERYDETAILS_TYPE.ORDERCUS.getValue());
		// 地址
		queryDetailsType.add(QUERY_ORERYDETAILS_TYPE.ORDERADDRESS.getValue());
		// 行李qr码
		queryDetailsType.add(QUERY_ORERYDETAILS_TYPE.ORDERBAGAGE.getValue());
		// 寄件收件
		queryDetailsType.add(QUERY_ORERYDETAILS_TYPE.ORDERSENDERRECEIVER.getValue());
		// 备注
		queryDetailsType.add(QUERY_ORERYDETAILS_TYPE.ORDERNOTES.getValue());
		// 订单价格详情
		queryDetailsType.add(QUERY_ORERYDETAILS_TYPE.ORDER_PRICE_DETAIL.getValue());
		reqDetails.setQueryDetailsType(queryDetailsType);
		// 查询订单详情
		System.out.println(reqDetails);
        commonTest(urlHeader,new Gson().toJson(reqDetails));
    }

	public static void commonTest(String url, String data) {
		AppRequestBean bean = new AppRequestBean();
		bean.setUser("wojiaotangqiming");
		bean.setKey("JPWEIXIN");
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
