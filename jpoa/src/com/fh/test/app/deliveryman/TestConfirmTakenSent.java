package com.fh.test.app.deliveryman;

import com.fh.entity.app.AppRequestBean;
import com.fh.entity.app.dm.AppOrderUserBean;
import com.fh.util.HttpClientUtil;
import com.fh.util.MD5;
import com.google.gson.Gson;

public class TestConfirmTakenSent
{
	public final static String url = "http://localhost/jingpei/appdm/confirmSentOrders";
	
	public static void main(String[] args) 
	{
		System.out.println( "fffffffffff" );
		AppOrderUserBean idBean = new AppOrderUserBean();
		idBean.setOrderId( 2 );
//		idBean.setUserId( 14 );
		Gson gson = new Gson();
		String data = gson.toJson( idBean );
		
		AppRequestBean req = new AppRequestBean();
		req.setUser( "130354218011" );
		req.setKey( "admin444" );
		req.setTimestamp( "" + System.currentTimeMillis() );
		req.setSign( MD5.md5( "jingpei" + req.getUser() + req.getKey() + req.getTimestamp() ));
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
