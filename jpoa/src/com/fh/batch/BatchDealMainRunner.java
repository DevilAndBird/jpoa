 package com.fh.batch;


import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fh.controller.wxpublicnum.WeChatConst;
import com.fh.controller.wxpublicnum.wxpushinfo.AccessToken;
import com.fh.controller.wxpublicnum.wxpushinfo.WeixinUtil;
import com.fh.service.autoallot.AutoAllotService;
import com.fh.util.RedisUtil;

/**
 * 
 * 项目名称：jpoa   
 *
 * 类描述：批处理获取token
 * 类名称：com.fh.batch.BatchDealTokenRunner     
 * 创建人：tangqm
 * 创建时间：2018年6月6日 下午10:36:39   
 * 修改人：
 * 修改时间：2018年6月6日 下午10:36:39   
 * 修改备注：
 */
 @Component
 public class BatchDealMainRunner {
	 
	  private static Logger logger = LoggerFactory.getLogger(BatchDealMainRunner.class);  
		@Autowired
		private AutoAllotService autoAllotService;
		
		
		public void run() {
			try {
				 AccessToken accessToken = WeixinUtil.getAccessToken(WeChatConst.WX_APPID, WeChatConst.WX_APP_SECRET);  
				 String jsapiTicket = getJsapiTicket(accessToken.getToken());
				  RedisUtil.set("accessToken", accessToken);
				  RedisUtil.set("jsapiTicket", jsapiTicket);
	              logger.info("获取access_token成功，有效时长{}秒 token:{}", accessToken.getExpiresIn(), accessToken.getToken());  
	              logger.info("获取jsapiTicket成功，有效时长{}秒 token:{}", accessToken.getExpiresIn(), jsapiTicket);  
			} catch (Exception e) {
				 e.printStackTrace();
			}
		}
		
	    private  String getJsapiTicket(String accessToken){
	    	String JsapiTicket_url="https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+accessToken+"&type=jsapi";
	        JSONObject jsonObject = WeixinUtil.httpRequest(JsapiTicket_url,"GET",null);
	        String jsapi_ticket =jsonObject.toString();  
	       String[] spli = jsapi_ticket.split(":");
	       String string = spli[3];
	       String[] split2 = string.split(",");
	       String string2 = split2[0];
	       String replace=  string2.substring(1, string2.length()-1);
	       return replace;  
	    } 

}
