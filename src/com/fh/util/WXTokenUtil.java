package com.fh.util;

import com.fh.controller.wxpublicnum.WeChatConst;
import com.fh.controller.wxpublicnum.wxpushinfo.AccessToken;
import com.fh.controller.wxpublicnum.wxpushinfo.WeixinUtil;
/**
 * 
 * 项目名称：jpoa   
 *
 * 类描述：微信token工具类
 * 类名称：com.fh.util.TokenUtil     
 * 创建人：tangqm
 * 创建时间：2018年6月6日 下午10:42:20   
 * 修改人：
 * 修改时间：2018年6月6日 下午10:42:20   
 * 修改备注：
 */
public class WXTokenUtil {

	public static AccessToken getAccessToken(){
		AccessToken accessToken = (AccessToken) RedisUtil.get("accessToken");
		if(accessToken == null){
			 accessToken = WeixinUtil.getAccessToken(WeChatConst.WX_APPID, WeChatConst.WX_APP_SECRET);  
			 RedisUtil.set("accessToken", accessToken);
			 return accessToken;
		}
		return accessToken;
	}
	
}
