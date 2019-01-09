package com.fh.test.app.order;

import org.junit.Test;

import com.fh.common.constant_enum.EVALUATE_CLASS;
import com.fh.entity.app.AppRequestBean;
import com.fh.entity.app.order.AppOrderEvaluateReqData;
import com.fh.util.HttpClientUtil;
import com.fh.util.MD5;
import com.google.gson.Gson;

/**
 * 集散中心单元测试
 * 
 * @author tangqm
 * @date 2018年3月15日
 */
public class OrderEvaluateColltrollerJunitTest {

	public String urlHeader = "http://localhost:8080/jpoa/";// 地址头
	
    @Test
    public void saveOrderEvaluate() {
        urlHeader+="/orderEvaluate/saveOrderEvaluate";
        AppOrderEvaluateReqData data = new AppOrderEvaluateReqData();
        data.setOrderid(133);
        data.setEvaluatedesc("物流超快");
        data.setStaffattitude(EVALUATE_CLASS.VERYGOOD.getValue());
        data.setLogisticsservice(EVALUATE_CLASS.VERYGOOD.getValue());
        commonTest(urlHeader,new Gson().toJson(data));
    }
	
    @Test
    public void addToEvaluate() {
        urlHeader+="/orderEvaluate/addToEvaluate";
        AppOrderEvaluateReqData data = new AppOrderEvaluateReqData();
        data.setId(1);
        data.setEvaluatedesc("物流超快");
        commonTest(urlHeader,new Gson().toJson(data));
    }
    
    @Test
    public void selectEvaluate() {
        urlHeader+="/orderEvaluate/selectEvaluate";
        AppOrderEvaluateReqData data = new AppOrderEvaluateReqData();
        data.setOrderid(133);
        commonTest(urlHeader,new Gson().toJson(data));
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

}
