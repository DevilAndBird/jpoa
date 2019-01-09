package com.fh.test.Mq;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fh.entity.app.AppRequestBean;
import com.fh.test.base.BaseTest;
import com.fh.util.HttpClientUtil;
import com.fh.util.MD5;
import com.fh.util.PropertyConfigurer;
import com.google.gson.Gson;

/**
 * @des H5单元测试
 * @author tangqm
 * @date 2018年3月12日
 */
public class Mq extends BaseTest{

	public   String urlHeader = "http://localhost:8080/jpoa/";// 地址头
	
	@Autowired
	private PropertyConfigurer propertyConfigurer;
	
	@Test
	public void a() {
		String alert = propertyConfigurer.getProperty("alloc_neworder_dman");
		alert = alert.replace("ORDERNO", "爱你一万年");
		System.out.println(alert);
	}
	
	/**
     * 获取客户充值记录接口
     */
    @Test
    public void h5SaveCusInfo() {
    	 urlHeader+="h5order/JiGuangPush";
         commonTest(urlHeader,new Gson().toJson(null));
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
