package com.fh.test.app.h5;

import com.fh.entity.app.AppRequestBean;
import com.fh.entity.h5.H5OrderNoBean;
import com.fh.util.HttpClientUtil;
import com.fh.util.MD5;
import com.google.gson.Gson;

public class TestOrderPay {
	
//	public final static String url = "http://localhost/jingpei/h5order/h5OrderPay";
	public final static String url = "http://47.96.186.145/jpoa/h5order/h5OrderPay";
//	    public final static String url = "http://localhost:8080/jpoa/h5order/h5OrderPay";//支付
	
	public static void main(String[] args) {
		AppRequestBean bean = new AppRequestBean();
		bean.setUser( "130354218011" );
		bean.setKey( "admin444" );
		bean.setTimestamp( "" + System.currentTimeMillis() );
		bean.setSign( MD5.md5( "jingpei" + bean.getUser() + bean.getKey() + bean.getTimestamp() ));
		
		H5OrderNoBean reqBean = new H5OrderNoBean();
		reqBean.setOrderno( "JPWX20180331161544629" );

	
		bean.setData( new Gson().toJson( reqBean ) );
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
