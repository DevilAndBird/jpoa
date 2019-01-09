package com.fh.test.app.login;

import com.fh.entity.app.AppRequestBean;
import com.fh.entity.app.login.AppUserReqData;
import com.fh.util.HttpClientUtil;
import com.fh.util.MD5;
import com.google.gson.Gson;

public class TestAppLogin {
	
	public final static String url = "http://localhost:8080/jpoa/applogin/appUserLogin";
//	public final static String url = "http://47.96.186.145/jpoa/applogin/appUserLogin";
//	public final static String url = "http://47.96.186.145/jpoa/applogin/isLogin";
	
	public static void main(String[] args) {//d8fa04c6aa0ee4a74b9aee9e99756ccd
		AppRequestBean bean = new AppRequestBean();
		bean.setUser( "18752066141" );//手机号码
		bean.setKey( "123456" );
		bean.setTimestamp( "" + System.currentTimeMillis() );
		bean.setSign("d8fa04c6aa0ee4a74b9aee9e99756ccd");
		
		AppUserReqData data = new AppUserReqData();
		data.setMobile( "15021006523" );
		data.setPassword( "JPKJ00" );
		data.setSource( "APP" );
		bean.setData( new Gson().toJson( data ));
		
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
