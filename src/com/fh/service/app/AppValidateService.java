package com.fh.service.app;

import org.springframework.stereotype.Service;

import com.fh.entity.app.AppRequestBean;
import com.fh.util.MD5;

@Service
public class AppValidateService {

	/**
	 * 校验接口的数据是否正确
	 * 
	 * sign = MD5( "jingpei"+user + password + timestamp )
	 * @param param
	 * @return
	 */
	public boolean doValidate( AppRequestBean param )
	{
		String sign = MD5.md5( "jingpei"+param.getUser()+param.getKey()+param.getTimestamp() );
		if( sign.equalsIgnoreCase( param.getSign() ))
			return true;
		return false;
	}
}
