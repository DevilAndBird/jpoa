package com.fh.service.auxiliary.smstemplate;

import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fh.common.constant_enum.APP_RESPONSE_CODE;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.app.AppResponseBean;
import com.fh.entity.auxiliary.smstemplate.SMSTemplate;
import com.fh.entity.system.User;
import com.fh.sms.DSResult;
import com.fh.sms.SmsClientAccessTool;
import com.fh.sms.SmsCode;
import com.fh.util.Const;
import com.fh.util.MD5;
import com.fh.util.PageData;
import com.google.gson.Gson;
import com.mysql.jdbc.StringUtils;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

@Service
public class SMSTemplateService {

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
    @SuppressWarnings("all")
	public String SMSIntegrationSend( String reqStr ) throws Exception{
		Gson gson = new Gson();
		Map<String,Object> root = gson.fromJson(reqStr, Map.class);
		AppResponseBean rtBean = new AppResponseBean();
		String smscode = (String) root.get("smscode");
		String mobile = (String) root.get("mobile");
		String orderno = (String) root.get("orderno");
		if( StringUtils.isEmptyOrWhitespaceOnly(smscode) || StringUtils.isEmptyOrWhitespaceOnly(mobile) ){
			rtBean.setCode( APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg( "模版编号或者手机号码不能为空" );
		}
		PageData pd = new PageData();
		pd.put("smscode", smscode);
		try {  
			if(dao == null){
				System.out.println(dao);
			}
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
            /*String timestamp = new Date().getTime() + "";
            root.put("content", writer.toString());
            root.put("user", "jinpei");
            root.put("key", "jinpei");
            root.put("timestamp", timestamp);
            root.put("sign", MD5.md5("jinpei" + "jinpei" + timestamp));
            
            String resp = HttpClientUtil.doPostJson("http://127.0.0.1:8011/sms/freemark/sendSms", gson.toJson(root));
            
            log.error(resp);*/
                
            DSResult smsStr = sendSms(writer.toString(), mobile);
            if( smsStr.getStatus() == 200 ){
            	rtBean.setCode( APP_RESPONSE_CODE.SUCCESS.getValue());
    			rtBean.setMsg( "订单："+orderno+" 短信发送成功" );
            }else{
            	rtBean.setCode( APP_RESPONSE_CODE.FAIL.getValue());
    			rtBean.setMsg( "订单："+orderno+" 短信发送失败" );
            }
		}catch (Exception e) {  
			e.printStackTrace();  
			rtBean.setCode( APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg( "订单："+orderno+" 短信发送异常 "+e.getLocalizedMessage() );
		}
		return new Gson().toJson( rtBean );
	}
	
	/**
	 * 删除模板
	 * 木桐
	 * 2018年1月10日15:01:05
	 * @throws Exception 
	 */
	public void delTemplate( PageData pd ) throws Exception{
		dao.delete("SMSTemplateMapper.delTemplate", pd);
	}
	
	/**
	 * 修改状态
	 * 木桐
	 * 2018年1月10日14:35:53
	 * @throws Exception 
	 */
	public void changeStatus( PageData pd ) throws Exception{
		
		dao.update("SMSTemplateMapper.changeStatus", pd);
		
	}
	
	/**
	 * 修改短信模板
	 * 木桐
	 * 2018年1月10日13:42:47
	 */
	public void updateSmsTemplate( PageData pd ){
		User user = getuser();
		pd.put("updateby", user.getNAME());
		try {
			dao.update("SMSTemplateMapper.changeTemplate", pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 判断是否存在smscode
	 * 木桐
	 * 2018年1月9日20:31:12
	 */
	public SMSTemplate checkCode( String smscode ){
		PageData pd = new PageData();
		pd.put("smscode", smscode);
		
		SMSTemplate smsTemplate = null;
		try {
			smsTemplate = (SMSTemplate)dao.findForObject("SMSTemplateMapper.checkCode", pd);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return smsTemplate;
	}
	
	/**
	 * 新增短信模板
	 * 木桐
	 * 2018年1月9日16:58:52
	 */
	public void saveTemplate( PageData pd ){
		
		SMSTemplate smsTemplate = new SMSTemplate();
		smsTemplate.setExplain(pd.getString("explain"));
		smsTemplate.setSmscode(pd.getString("smscode"));
		smsTemplate.setSmsname(pd.getString("smsname"));
		smsTemplate.setStatus(1);
		smsTemplate.setTemplate(pd.getString("template"));
		try {
			dao.save("SMSTemplateMapper.insert", smsTemplate);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 短信模板列表
	 * 木桐
	 * 2018年1月8日10:26:42
	 * @throws Exception 
	 */
	@SuppressWarnings("all")
	public List<PageData> listSmsTemplate( Page page ) throws Exception{
		
		PageData pd = page.getPd();
		String title = pd.getString("title");
		String smsname = pd.getString("smsname");
		
		if( !StringUtils.isEmptyOrWhitespaceOnly(title) ){
			pd.put("title", title.replace(" ", ""));
		}
		if( !StringUtils.isEmptyOrWhitespaceOnly(smsname) ){
			pd.put("smsname", smsname.replace(" ", ""));
		}
		page.setPd(pd);
		
		return (List<PageData>)dao.findForList("SMSTemplateMapper.smsTemplatelistPage", page);
		
	}
	
	/**
	 * 根据id获取相应模板信息
	 * 木桐
	 * 2018年1月8日17:58:20
	 */
	public SMSTemplate queryById( PageData pd ){
		
		SMSTemplate smsTemplate = new SMSTemplate();
		try {
			smsTemplate = (SMSTemplate)dao.findForObject("SMSTemplateMapper.queryById", pd);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return smsTemplate;
	}
	
	
	/**
	 * 获取当前的登录用户信息
	 * mt
	 */
	public User getuser(){
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		User user = (User)session.getAttribute( Const.SESSION_USER );
		return user;
	}
	
	@SuppressWarnings("all")
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
