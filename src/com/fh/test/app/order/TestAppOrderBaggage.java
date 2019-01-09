package com.fh.test.app.order;

import com.fh.entity.app.AppRequestBean;
import com.fh.entity.app.order.OrderBaggageReqData;
import com.fh.util.HttpClientUtil;
import com.fh.util.MD5;
import com.google.gson.Gson;
/**
 * app订单行李接口
 * @author tangqm
 * @date 2018年2月28日
 */
public class TestAppOrderBaggage{
	
//	public final static String url = "http://localhost:8080/jpoa/apporder/scanQRCode";
//	public final static String url = "http://localhost:8080/jpoa/code/saveOrderBaggage";
//    public final static String url = "http://localhost:8080/jpoa/apporder/updateOrderBaggage";
	public final static String url = "http://localhost:8080/jpoa/appOrderBaggage/isScan";
	
	public static void main(String[] args) {
		AppRequestBean bean = new AppRequestBean();
		bean.setUser( "130354218011" );
		bean.setKey( "admin444" );
		bean.setTimestamp( "" + System.currentTimeMillis() );
		bean.setSign( MD5.md5( "jingpei" + bean.getUser() + bean.getKey() + bean.getTimestamp() ));
		
		OrderBaggageReqData data = new OrderBaggageReqData();
		data.setBaggageid("1234568");
		data.setOrderid(2);
		data.setIsscan("2");
		
		bean.setData( new Gson().toJson( data ) );
		
		Gson gson = new Gson();
		String json = gson.toJson( bean );
		
		System.out.println( json );
		try{
			String res = HttpClientUtil.doPostJson( url , json );
			System.out.println( res );
		}catch( Exception ex )
		{
			ex.printStackTrace();
		}
	}

}
