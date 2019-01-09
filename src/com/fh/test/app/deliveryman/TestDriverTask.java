package com.fh.test.app.deliveryman;

import com.fh.entity.app.AppRequestBean;
import com.fh.entity.app.userdelivery.UserDeliveryReqData;
import com.fh.util.HttpClientUtil;
import com.fh.util.MD5;
import com.google.gson.Gson;

public class TestDriverTask {

//	public final static String url = "http://localhost/jingpei/appdm/confirmDriverTaskStarted";
	  public final static String url = "http://localhost:8080/jpoa/appdm/prepareTransferluggage";//申请交接
	
	public static void main(String[] args) {
		
	
		
		AppRequestBean req = new AppRequestBean();
		req.setUser( "130354218011" );
		req.setKey( "admin444" );
		req.setTimestamp( "" + System.currentTimeMillis() );
		req.setSign( MD5.md5( "jingpei" + req.getUser() + req.getKey() + req.getTimestamp() ));
		
		UserDeliveryReqData rqdata = new UserDeliveryReqData();
		rqdata.setRoleid(70);
		
		Gson gson = new Gson();
		String data = gson.toJson( rqdata );
		
		req.setData( data );
		String json = gson.toJson( req );
		
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
