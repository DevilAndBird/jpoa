package com.fh.service;

import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fh.common.constant_enum.APP_RESPONSE_CODE;
import com.fh.dao.DaoSupport;
import com.fh.entity.app.AppResponseBean;
import com.fh.entity.auxiliary.smstemplate.SMSTemplate;
import com.fh.service.auxiliary.smstemplate.SMSTemplateService;
import com.fh.sms.DSResult;
import com.fh.sms.SmsClientAccessTool;
import com.fh.sms.SmsCode;
import com.fh.util.MD5;
import com.fh.util.PageData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mysql.jdbc.StringUtils;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

@Service
public class SmsSendService {

	@Autowired
	private DaoSupport dao;
	
	private final static Logger log = Logger.getLogger(SMSTemplateService.class);
	private static  SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	private static String sendTime = "";
    private static String extno ="";
    
    /**
	 * 短信内容整合发送
	 * 木桐
	 * 2018年1月10日15:21:50
	 */
	public String smsTemplateSend( String reqStr ) throws Exception{
		AppResponseBean rtBean = new AppResponseBean();
		Gson gson = new Gson();
		Map<String, String> root = gson.fromJson( reqStr, new TypeToken<Map<String, String>>() { }.getType());
		String smscode = (String) root.get("smscode");
		String mobile = (String) root.get("mobile");
		String orderno = (String) root.get("orderno");
		if( StringUtils.isEmptyOrWhitespaceOnly(smscode) || StringUtils.isEmptyOrWhitespaceOnly(mobile) ){
			rtBean.setCode( APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg( "模版编号或者手机号码不能为空" );
		}
		PageData pd = new PageData();
		pd.put("smscode", smscode);
		SMSTemplate smsTemplate = (SMSTemplate)dao.findForObject("SMSTemplateMapper.queryBySmsCode",pd);
		
		Configuration cfg = new Configuration();
		StringTemplateLoader stringLoader = new StringTemplateLoader();
		String templateContent=smsTemplate.getTemplate();  
		log.error("订单："+orderno+" 的模版===》"+templateContent);
		stringLoader.putTemplate("myTemplate",templateContent);
		cfg.setTemplateLoader(stringLoader);
		Template template = cfg.getTemplate("myTemplate","utf-8");  
		StringWriter writer = new StringWriter();    
        template.process(root, writer);  
        log.error("订单："+orderno+" 的模版转换之后的需要发送的内容===》"+writer.toString());
            
        DSResult smsStr = sendSms(writer.toString(), mobile);
        if( smsStr.getStatus() == 200 ){
        	rtBean.setCode( APP_RESPONSE_CODE.SUCCESS.getValue());
			rtBean.setMsg( "订单："+orderno+" 短信发送成功  "+"电话号码："+ mobile);
        }else{
        	rtBean.setCode( APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg( "订单："+orderno+" 短信发送失败  "+" 电话号码："+ mobile);
        }
		return new Gson().toJson( rtBean );
	}

    /**
     * 短信内容整合发送
     * 木桐
     * 2018年1月10日15:21:50
     */
    public String smsTemplateSend1( String reqStr ) throws Exception{
        AppResponseBean rtBean = new AppResponseBean();
        Gson gson = new Gson();
        Map<String, String> root = gson.fromJson( reqStr, new TypeToken<Map<String, String>>() { }.getType());
        String smscode = (String) root.get("smscode");
        String mobile = (String) root.get("mobile");
        String orderno = (String) root.get("orderno");
        if( StringUtils.isEmptyOrWhitespaceOnly(smscode) || StringUtils.isEmptyOrWhitespaceOnly(mobile) ){
            rtBean.setCode( APP_RESPONSE_CODE.FAIL.getValue());
            rtBean.setMsg( "模版编号或者手机号码不能为空" );
        }
        PageData pd = new PageData();
        pd.put("smscode", smscode);
        SMSTemplate smsTemplate = (SMSTemplate)dao.findForObject("SMSTemplateMapper.queryBySmsCode",pd);

        Configuration cfg = new Configuration();
        StringTemplateLoader stringLoader = new StringTemplateLoader();
        String templateContent=smsTemplate.getTemplate();
        log.error("订单："+orderno+" 的模版===》"+templateContent);
        stringLoader.putTemplate("myTemplate",templateContent);
        cfg.setTemplateLoader(stringLoader);
        Template template = cfg.getTemplate("myTemplate","utf-8");
        StringWriter writer = new StringWriter();
        template.process(root, writer);
        log.error("订单："+orderno+" 的模版转换之后的需要发送的内容===》"+writer.toString());

        DSResult smsStr = sendSms(writer.toString(), mobile);
        if( smsStr.getStatus() == 200 ){
            rtBean.setCode( APP_RESPONSE_CODE.SUCCESS.getValue());
            rtBean.setMsg( "订单："+orderno+" 短信发送成功  "+"电话号码："+ mobile);
        }else{
            rtBean.setCode( APP_RESPONSE_CODE.FAIL.getValue());
            rtBean.setMsg( "订单："+orderno+" 短信发送失败  "+" 电话号码："+ mobile);
        }
        return rtBean.getMsg();
    }
	
	public static DSResult sendSms(String content,String mobile)
    {
    	StringBuffer sb = new StringBuffer();
    	SmsClientAccessTool sca = new SmsClientAccessTool();
    	String action ="send";
    	String timestamp = sdf.format(new Date());
   	 	String sign = getSign(timestamp);
    	 sb.append("action=");
         sb.append(action);
         sb.append("&userid=");
         sb.append(SmsCode.userid);
         sb.append("&timestamp=");
         sb.append(timestamp);
         sb.append("&sign=");
         sb.append(sign);
         sb.append("&mobile=");
         sb.append(mobile);
         sb.append("&content=");
         sb.append(content);
         sb.append("&sendTime=");
         sb.append(sendTime);
         sb.append("&extno=");
         sb.append(extno);
         String rtString = "";
    	try{
    		String sendMsg = sb.toString();  
    		//String utf8Msg = URLEncoder.encode( sendMsg, "UTF-8");
    		if( org.apache.commons.lang3.StringUtils.isNotEmpty(content) && org.apache.commons.lang3.StringUtils.isNotEmpty(mobile) )
    		{
    			sendMsg = sendMsg.replace( "null", "" );
    			sendMsg = sendMsg.replace( "NULL", "" );
    			sendMsg = sendMsg.replace( "Null", "" );
    			rtString = sca.doAccessHTTPPost( SmsCode.url, sendMsg, "UTF-8" );
    			log.error( "sms send result " + rtString +"  with content:"+sendMsg );
    			if(rtString.contains("Success") && rtString.contains("ok"))
    			{
    				return new DSResult().build(200, "success");
    			}else{
    				return new DSResult().build(500, "fail");
    			}
    		}else{
    			return new DSResult().build(500, "fail");
    		}
    	}catch(Exception e ){
    		rtString = e.getMessage();
    		return new DSResult().build(500, "fail");
    	}
    }
	
	private static String getSign(String timestamp){
    	String sign = MD5.md5(SmsCode.account+SmsCode.password+timestamp);
    	return sign;
    }
	
}
