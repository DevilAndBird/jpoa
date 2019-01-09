package com.fh.test.app.h5;

import com.fh.entity.app.AppRequestBean;
import com.fh.entity.customer.CusInfo;
import com.fh.util.HttpClientUtil;
import com.fh.util.MD5;
import com.google.gson.Gson;

public class TestCusAdd {

//	public final static String url = "http://localhost/jingpei/h5order/h5SaveCusInfo";
	public final static String url = "http://47.96.186.145/jpoa/h5order/h5SaveCusInfo";

	
	public static void main(String[] args) {
		AppRequestBean bean = new AppRequestBean();
		bean.setUser( "130354218011" );
		bean.setKey( "admin444" );
		bean.setTimestamp( "" + System.currentTimeMillis() );
		bean.setSign( MD5.md5( "jingpei" + bean.getUser() + bean.getKey() + bean.getTimestamp() ));
		
		CusInfo cus = new CusInfo();
		cus.setAddress("浙江省舟山普陀区展茅乡31号楼");
		cus.setChannel( "weixin" );
		cus.setCityid( "310100" );
		cus.setDistrictid( "310111" );
		cus.setEmail( "18000000009@163.com" );
		cus.setIdno( "442401199401130000" );
		cus.setMobile( "18000000023" );
		cus.setMoney( (float)0 );
		cus.setName( "鬼帅才" );
		cus.setNamespell( "qingzong" );
		cus.setPassword( "kkaaff" );
		cus.setProvid( "310000" );
		bean.setData( new Gson().toJson( cus ) );
		
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
