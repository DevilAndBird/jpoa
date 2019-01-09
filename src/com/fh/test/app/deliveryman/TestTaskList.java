package com.fh.test.app.deliveryman;

import com.fh.entity.app.AppRequestBean;
import com.fh.entity.app.AppUserIdBean;
import com.fh.util.HttpClientUtil;
import com.fh.util.MD5;
import com.google.gson.Gson;

public class TestTaskList
{
	public final static String url = "http://localhost/jingpei/code/getPickerTaskList";
	
	public static void main( String[] args )
	{
		System.out.println( "fffffffffff" );
		AppUserIdBean idBean = new AppUserIdBean();
		idBean.setUserid( 14 );
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
			String result = new String( res.getBytes() , "iso8859-1" );
			System.out.println( result );
		}catch( Exception ex )
		{
			ex.printStackTrace();
		}
	}
}
