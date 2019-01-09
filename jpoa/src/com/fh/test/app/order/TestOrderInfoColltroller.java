package com.fh.test.app.order;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fh.entity.app.AppRequestBean;
import com.fh.util.HttpClientUtil;
import com.fh.util.MD5;
import com.google.gson.Gson;


public class TestOrderInfoColltroller {

    public final static String url = "http://localhost/jpoa/sms/smsSend";
    
    public static void main(String[] args) {
        /*AppRequestBean bean = new AppRequestBean();
        bean.setUser( "130354218011" );
        bean.setKey( "admin444" );
        bean.setTimestamp( "" + System.currentTimeMillis() );
        bean.setSign( MD5.md5( "jingpei" + bean.getUser() + bean.getKey() + bean.getTimestamp() ));*/
        
       /* Map<String,Object> map = new HashMap<String,Object>();
        map.put("header", "行李管家");
    	map.put("smscode", "Z001");
    	map.put("orderno", "7777777");
    	map.put("mobile", "18721247095");
    	map.put("datetime", "2018-03-04 23:00");
    	map.put("hotelname", "万豪");
        //bean.setData( new Gson().toJson( map ) );
        
        Gson gson = new Gson();
        String json = gson.toJson( map );
        
        System.out.println( json );
        try{
            String res = HttpClientUtil.doPostJson( url , json );
            System.out.println( res );
        }catch( Exception ex )
        {
            ex.printStackTrace();
        }*/
    	
    	/*SmsSendService sms = new SmsSendService();
    	Map<String,Object> map = new HashMap<String,Object>();
    	map.put("header", "行李管家");
    	map.put("smscode", "Z001");
    	map.put("orderno", "7777777");
    	map.put("mobile", "18721247095");
    	map.put("datetime", "2018-03-04 23:00");
    	map.put("hotelname", "万豪");
    	
    	String smsRespStr = sms.SMSIntegrationSend(map);
    	System.out.println(smsRespStr);*/
    	doTest2();
    }

    public static void doTest2(){
		
    	AppRequestBean bean = new AppRequestBean();
    	Map<String,Object> map = new HashMap<String,Object>();
    	map.put("header", "行李管家");
    	map.put("smscode", "Z001");
    	map.put("orderno", "7777777");
    	map.put("mobile", "18721247095");
    	map.put("datetime", new Date());
    	map.put("hotelname", "万豪");
		Gson gson = new Gson();
		String json = gson.toJson( map );
		bean.setData(json);
		bean.setUser("jinpei");
		bean.setKey("jinpei");
		bean.setTimestamp(System.currentTimeMillis()+"");
		bean.setSign( MD5.md5( "jingpei" + bean.getUser() + bean.getKey() + bean.getTimestamp() ) );
		String result;
		try {
			System.out.println( json );
			/*result = HtmlUtil.doPost(
					(HttpURLConnection) new URL(url).openConnection(), gson.toJson( bean ),
					"utf-8");*/
			result = HttpClientUtil.doPostJson(url, gson.toJson( bean ));
			System.out.println( result );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
    
    /**
     * 取消订单测试接口
     */
    public static void doTest3(){
		
    	AppRequestBean bean = new AppRequestBean();
    	Map<String,Object> map = new HashMap<String,Object>();
    	map.put("id", 2);
		Gson gson = new Gson();
		String json = gson.toJson( map );
		bean.setData(json);
		bean.setUser("jinpei");
		bean.setKey("jinpei");
		bean.setTimestamp(System.currentTimeMillis()+"");
		bean.setSign( MD5.md5( "jingpei" + bean.getUser() + bean.getKey() + bean.getTimestamp() ) );
		String result;
		try {
			System.out.println( gson.toJson( bean ) );
			/*result = HtmlUtil.doPost(
					(HttpURLConnection) new URL(url).openConnection(), gson.toJson( bean ),
					"utf-8");*/
			result = HttpClientUtil.doPostJson(url, gson.toJson( bean ));
			System.out.println( result );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
    
    
    /**
     * 申请发票接口
     */
    public static void doTest4(){
		
    	AppRequestBean bean = new AppRequestBean();
    	Map<String,Object> map = new HashMap<String,Object>();
    	map.put("orderid", 2);
    	map.put("title", "行李管家");
    	map.put("taxno", "25555222");
    	map.put("invoicetype", 1);
    	map.put("email", "wxb@162.com");
    	map.put("money", 30.5);
    	map.put("content", "团建");
		Gson gson = new Gson();
		String json = gson.toJson( map );
		bean.setData(json);
		bean.setUser("jinpei");
		bean.setKey("jinpei");
		bean.setTimestamp(System.currentTimeMillis()+"");
		bean.setSign( MD5.md5( "jingpei" + bean.getUser() + bean.getKey() + bean.getTimestamp() ) );
		String result;
		try {
			System.out.println( gson.toJson( bean ) );
			/*result = HtmlUtil.doPost(
					(HttpURLConnection) new URL(url).openConnection(), gson.toJson( bean ),
					"utf-8");*/
			result = HttpClientUtil.doPostJson(url, gson.toJson( bean ));
			System.out.println( result );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
