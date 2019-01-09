package com.fh.controller.wxpublicnum.wxpushinfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

/** 
 * 发送模板消息调用实例 
 * @param websiteAndProject 请求地址与工程名：http://192.168.2.113/seafood 
 * @param receiverWeixinId  接受者的微信ID
 * @return 
 */
public class WeixinPushTest {
	
	public static void main(String[] args) {
		  
		TemplateNew tem=new TemplateNew();  
		tem.setTemplateId("seBr4j1bMok4WVem3zoghEAzq6N4wRD3zCaewqJlCjE");  
		tem.setTopColor("#00DD00");  
		tem.setToUser("okV0d1XVF_l1Sr4j3J6umVS8pFxw");  
		List<TemplateParam> paras=new ArrayList<TemplateParam>();  
		paras.add(new TemplateParam("first","收单成功","#FF3333"));  
		paras.add(new TemplateParam("keyword1","JPWX20180603022212035","#0044BB"));  
		paras.add(new TemplateParam("keyword2","世纪大道-->桂平小区","#0044BB"));  
		paras.add(new TemplateParam("keyword3","行李","#0044BB"));  
		paras.add(new TemplateParam("keyword4","20点38分","#0044BB"));  
		paras.add(new TemplateParam("Remark","感谢您对我们支持!!!!","#AAAAAA"));  
		tem.setTemplateParamList(paras);  
//		WXPublicNumController.sendTemplateMsg("10_KeUMacdGUDCdp2g-B_Nc3ZIiFkJSTI4fo02Yk4vdLQ9dYuzl1-ttponEOs0qyggUr"
//				+ "ueaaVqKKbzV8etrw-0IeUTZV0OFI2qbHO_ZbByy3MItxoZL34RfIiMEOZLyGGcUf4lL8UMphGY5i6V_WAWgACABFB", tem);
		
	}
	
}
