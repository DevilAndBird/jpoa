package com.fh.test.app.flowtest;

import com.alibaba.fastjson.JSONObject;
import com.fh.common.constant_enum.INVICE_STATUS;
import com.fh.common.constant_enum.ORDER_ADDRESS_TYPE;
import com.fh.entity.Page;
import com.fh.entity.app.AppRequestBean;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @des H5单元测试
 * @author tangqm
 * @date 2018年3月12日
 */
public class WebJunitTest extends BaseTest {

	public   String urlHeader = "http://localhost:8080/jpoa/";// 地址头
//	public  String urlHeader = "http://47.96.186.145/jpoa/";//测试服务器
	
	/**
     * 获取客户充值记录接口
     */
    @Test
    public void findWebOrderMainList() {
        urlHeader+="weborder/findWebOrderMainList";
        Page data  = new Page();
        commonTest(urlHeader,new Gson().toJson(data));
    }

    /**
     * 查询客户列表
     */
    @Test
    public void findCusCouponList() {
        urlHeader+="h5order/findCusCouponList";
        H5CusIdBean data  = new H5CusIdBean();
        data.setCusId(36);
        commonTest(urlHeader,new Gson().toJson(data));
    }

	public void commonTest(String url,String data) {
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
