package com.fh.controller.wxpublicnum;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.fh.common.constant_enum.INVICE_STATUS;
import com.fh.common.constant_enum.INVOICE_INVOICETYPE;
import com.fh.controller.wxpublicnum.wxpushinfo.AccessToken;
import com.fh.controller.wxpublicnum.wxpushinfo.Article;
import com.fh.controller.wxpublicnum.wxpushinfo.NewsMessage;
import com.fh.dao.DaoSupport;
import com.fh.entity.order.OrderInvoiceInfo;
import com.fh.entity.wxpublicnum.autoreply.TextMessage;
import com.fh.service.weixin.WXPublicNumCondigService;
import com.fh.util.ExceptionUtil;
import com.fh.util.LoggerUtil;
import com.fh.util.MessageUtil;
import com.fh.util.PageData;
import com.fh.util.PropertiesUtils;
import com.fh.util.RedisUtil;
import com.fh.util.SignUtil;
import com.fh.util.SpringUtil;
import com.fh.util.WeixinUtil;

/**
 * @author zhangjj
 * 来接收微信服务器传来信息
 */
public class CoreServlet extends HttpServlet {

    private static final long serialVersionUID = 4323197796926899691L;
    /** 包给我公众号 */
    private static final String PUBLICNUM = "baogeiwo_jingpei"; 

    /**
     * 确认请求来自微信服务器
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // 微信加密签名
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 随机字符串
        String echostr = request.getParameter("echostr");

        PrintWriter out = response.getWriter();
        
        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        if (SignUtil.checkSignature(signature, timestamp, nonce)) {
            out.print(echostr);
        }
        
        out.close();
        out = null;
    }

    /**
     * 处理微信服务器发来的消息
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 将请求、响应的编码均设置为UTF-8（防止中文乱码）
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        // 调用核心业务类接收消息、处理消息
        String respXml = this.processRequest(request);
        // 响应消息
        PrintWriter out = response.getWriter();
        out.print(respXml);
        out.close();
    }
    
    /**
     * 处理微信发来的请求
     * @param request
     * @return xml
     */
    private String processRequest(HttpServletRequest request) {
        String respMessage ="";
        try {
            // 调用parseXml方法解析请求消息
            Map<String, String> requestMap = MessageUtil.parseXml(request);
            // 公众帐号
            String toUserName = requestMap.get("ToUserName");
            // 发送方帐号
            String fromUserName = requestMap.get("FromUserName");
            // 消息类型
            String msgType = requestMap.get("MsgType");
            
            if(MessageUtil.REQ_MESSAGE_TYPE_EVENT.equals(msgType)) {// 事件
            	// 事件类型
                String evert = requestMap.get("Event");
                
                // 订阅公众号时，返回
                if(MessageUtil.EVENT_TYPE_SUBSCRIBE.equals(evert)) {
                	String businessValue = WXPublicNumCondigService.PUBLICNUMCONFIGMAP.get(PUBLICNUM + MessageUtil.EVENT_TYPE_SUBSCRIBE);
                	JSONObject businessValueJsonObj = JSONObject.fromObject(businessValue);
                	String reqType = (String) businessValueJsonObj.get("REQ_MESSAGE_TYPE");
                	if(MessageUtil.REQ_MESSAGE_TYPE_TEXT.equals(reqType)) {
                		respMessage = build_text_msg(toUserName, fromUserName, (String) businessValueJsonObj.get("content"));
                	} else if(MessageUtil.RESP_MESSAGE_TYPE_NEWS.equals(reqType)) {
                		respMessage = buileNews(toUserName, fromUserName,(String) businessValueJsonObj.get("title"), (String) businessValueJsonObj.get("picUrl"), (String) businessValueJsonObj.get("url"));
                	}
                } else if(MessageUtil.EVENT_TYPE_CLICK.equals(evert)) { // 自定义菜单事件
                	// 事件类型 
                    String eventKey = requestMap.get("EventKey");
                	String businessValue = WXPublicNumCondigService.PUBLICNUMCONFIGMAP.get(PUBLICNUM + eventKey);
                	JSONObject businessValueJsonObj = JSONObject.fromObject(businessValue);
                	String reqType = (String) businessValueJsonObj.get("REQ_MESSAGE_TYPE");
                	if(MessageUtil.REQ_MESSAGE_TYPE_TEXT.equals(reqType)) {
                		respMessage = build_text_msg(toUserName, fromUserName, (String) businessValueJsonObj.get("content"));
                	} else if(MessageUtil.RESP_MESSAGE_TYPE_NEWS.equals(reqType)) {
                		respMessage = buileNews(toUserName, fromUserName,(String) businessValueJsonObj.get("title"), (String) businessValueJsonObj.get("picUrl"), (String) businessValueJsonObj.get("url"));
                	}
                } else if(MessageUtil.EVENT_TYPE_USER_AUTHORIZE_INVOICE.equals(evert)) {// 开局发票授权事件
                	LoggerUtil.info("发票回调成功，事件(user_authorize_invoice_req)：" + requestMap.toString());
                	
                	String succOrderId = requestMap.get("SuccOrderId");// 授权是否成功
                	// 未授权开具发票
                	if(StringUtils.isBlank(succOrderId)) {
                		return "";
                	}
                	
                	dealAuthorizeInvoice(toUserName, fromUserName, succOrderId);
                }
            }else if (MessageUtil.REQ_MESSAGE_TYPE_TEXT.equals(msgType)){// 文本
            	// 消息内容
                String content = requestMap.get("Content");
            	
            	String businessValue = WXPublicNumCondigService.PUBLICNUMCONFIGMAP.get(PUBLICNUM + MessageUtil.REQ_MESSAGE_TYPE_TEXT + content);
            	
            	if(StringUtils.isNotBlank(businessValue)) {
            		//自动回复
                	TextMessage text = new TextMessage();
                	text.setContent(businessValue);
                	text.setToUserName(fromUserName);
                	text.setFromUserName(toUserName);
                	text.setCreateTime(new Date().getTime() + "");
                	text.setMsgType(MessageUtil.REQ_MESSAGE_TYPE_TEXT);
                	respMessage = MessageUtil.messageToXml(text);
            	} else {
            		//自动回复
                	TextMessage text = new TextMessage();
                	text.setContent(WXPublicNumCondigService.PUBLICNUMCONFIGMAP.get(PUBLICNUM + MessageUtil.SERVICE_LIST));
                	text.setToUserName(fromUserName);
                	text.setFromUserName(toUserName);
                	text.setCreateTime(new Date().getTime() + "");
                	text.setMsgType(MessageUtil.REQ_MESSAGE_TYPE_TEXT);
                	respMessage = MessageUtil.messageToXml(text);
            	}
            }
            
            
        } catch (Exception e) {
        	LoggerUtil.warn("组装消息失败,原因："+ e.getMessage());
        }
        
        return respMessage;
    }
    
    /**
     * @desc 订阅公众号时推送
     * @auther zhangjj
     * @date 2018年7月26日
     */
    private String build_text_msg(String publicnum, String openid, String content) {
    	TextMessage text = new TextMessage();
    	text.setToUserName(openid);
    	text.setFromUserName(publicnum);
    	text.setCreateTime(new Date().getTime() + "");
    	text.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
    	text.setContent(content);
    	return MessageUtil.messageToXml(text);
    }
    
    /**
     * @desc 返回图文消息
     * @auther zhangjj
     * @date 2018年7月29日
     */
    private String buileNews(String publicnum, String openid, String title, String picUrl, String url) {
        NewsMessage newsMessage = new NewsMessage();
    	newsMessage.setToUserName(openid);
    	newsMessage.setFromUserName(publicnum);
    	newsMessage.setCreateTime(new Date().getTime());
    	newsMessage.setMsgType("news");
    	Article article = new Article(); 
    	article.setTitle(title);
    	article.setDescription("");
    	article.setPicUrl(picUrl);
    	article.setUrl(url);
    	List<Article> articlelist = new ArrayList<Article>();
    	articlelist.add(article);
    	newsMessage.setArticleCount(articlelist.size());
    	newsMessage.setArticles(articlelist);
    	return MessageUtil.messageToXml(newsMessage);
    }
    
    /**
     * @desc 电子发票逻辑处理_异步处理
     * @auther zhangjj
     * @date 2018年4月27日
     */
    private void dealAuthorizeInvoice(String publicNum, String openid, String orderno) throws Exception {
    	// 查询发票信息
    	OrderInvoiceInfo orderInvoiceInfo = findAuthorizeInvoice(orderno);
    	ExceptionUtil.checkNotNull(orderInvoiceInfo, "发票信息未能查询出来，请检查原因");
    	// 根据orderno获取到orderid
    	DaoSupport dao = (DaoSupport) SpringUtil.getBean("daoSupport");
    	PageData pd = (PageData) dao.findForObject("OrderMainMapper.findIsExistsOrder", orderno);
    	// 统一开票接口-开具蓝票
    	makeOutAnInvoice(openid, orderno, (Float)pd.get("actualmoney"), pd.getString("name"), orderInvoiceInfo);
    }
    
    /**
     * @desc 查询电子发票授权状态
     * @auther zhangjj
     * @date 2018年4月27日
     */
    public OrderInvoiceInfo findAuthorizeInvoice(String orderno) {
    	LoggerUtil.info("\n===========================查询电子发票授权状态（getauthdata）start=======================================\n");
    	String reqUrl = "https://api.weixin.qq.com/card/invoice/getauthdata?access_token=ACCESS_TOKEN";
    	// token 获取
    	AccessToken access_token = (AccessToken) RedisUtil.get("accessToken");
    	reqUrl = reqUrl.replace("ACCESS_TOKEN", access_token.getToken());
    	// 请求参数组装
    	JSONObject reqParam_JsonObject = new JSONObject();
    	reqParam_JsonObject.put("s_pappid", PropertiesUtils.getString("WX_S_PAPPID"));
    	reqParam_JsonObject.put("order_id", orderno);
    	// 调用
    	LoggerUtil.info("\n查询电子发票授权状态getauthdata_req：\n" + reqParam_JsonObject.toString());
    	LoggerUtil.info("\n查询电子发票授权状态getauthdata_url：\n" + reqUrl);
    	JSONObject res = WeixinUtil.httpRequest(reqUrl, "POST", reqParam_JsonObject.toString());
    	LoggerUtil.info("\n查询电子发票授权状态getauthdata_res：\n" + res.toString());
		
    	// 电子发票授权失败
    	ExceptionUtil.checkBoolean((Integer) res.get("errcode") != 0 || !"auth success".equals(res.getString("invoice_status")), "查询电子发票授权状态返回异常, 异常码：" + res.getString("errmsg"));
		
		OrderInvoiceInfo orderInvoiceInfo = new OrderInvoiceInfo();
		
		JSONObject userAuthInfo = JSONObject.fromObject((String) res.get("user_auth_info"));
		// 企业级发票信息
		if(userAuthInfo.get("biz_field") != null) {
			JSONObject bizField = (JSONObject) userAuthInfo.get("biz_field");
			orderInvoiceInfo.setTitle((String) bizField.get("title"));
			orderInvoiceInfo.setTaxno((String) bizField.get("tax_no"));
			orderInvoiceInfo.setInvoicetype(INVOICE_INVOICETYPE.COMPANY.getValue());
			orderInvoiceInfo.setInvoicetime(new Date());
			orderInvoiceInfo.setEmail((String) bizField.get("email"));
			orderInvoiceInfo.setBanktype((String) bizField.get("bank_type"));
			orderInvoiceInfo.setBankno((String) bizField.get("bank_no"));
			orderInvoiceInfo.setAddr((String) bizField.get("addr"));
			orderInvoiceInfo.setPhone((String) bizField.get("phone"));
			orderInvoiceInfo.setStatus(INVICE_STATUS.APPLYED.getValue());
		}
		// 个人发票信息
		if( userAuthInfo.get("user_field") != null ) {
			JSONObject userfield = (JSONObject) userAuthInfo.get("user_field");
			orderInvoiceInfo.setTitle((String) userfield.get("title"));
			orderInvoiceInfo.setInvoicetype(INVOICE_INVOICETYPE.PERSONAL.getValue());
			orderInvoiceInfo.setEmail((String) userfield.get("email"));
			orderInvoiceInfo.setInvoicetime(new Date());
			orderInvoiceInfo.setStatus(INVICE_STATUS.APPLYED.getValue());
		}
		
		LoggerUtil.info("\n===========================查询电子发票授权状态（getauthdata）end=======================================\n");
		return orderInvoiceInfo;
    }
    
    /**
     * @desc 统一开票接口-开具蓝票
     * @auther zhangjj
     * @date 2018年4月28日
     */
    public void makeOutAnInvoice(String openid, String orderno, Float money, String cusname, OrderInvoiceInfo orderInvoiceInfo) {
    	LoggerUtil.info("\n===========================开具蓝票（makeoutinvoice）start=======================================\n");
    	// 税率
    	Float taxRate = Float.valueOf(PropertiesUtils.getString("WX_INVOICE_TAXRATE"));
    	// 合计金额
		Float amountInTotal = new BigDecimal(money/(taxRate + 1)).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
    	// 合计税额
		Float taxInTotal = new BigDecimal(money - amountInTotal).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
    	
		JSONObject invoiceinfo = new JSONObject();
    	invoiceinfo.put("wxopenid", openid);	// 用户的openid 用户知道是谁在开票
    	invoiceinfo.put("ddh", orderno);	// 订单号，企业自己内部的订单号码
    	invoiceinfo.put("fpqqlsh", PropertiesUtils.getString("WX_MCH_DISCERN") + orderno.substring(8, orderno.length()));	// 发票请求流水号，唯一查询发票的流水号
    	// 纳税人
    	invoiceinfo.put("nsrmc", PropertiesUtils.getString("WX_nsrmc"));	// 	纳税人名称
    	invoiceinfo.put("nsrsbh", PropertiesUtils.getString("WX_nsrsbh"));	// 纳税人识别码
    	invoiceinfo.put("nsrdz", PropertiesUtils.getString("WX_nsrdz"));	// 	纳税人地址
    	invoiceinfo.put("nsrdh", PropertiesUtils.getString("WX_nsrdh"));	// 纳税人电话
    	invoiceinfo.put("nsrbank", PropertiesUtils.getString("WX_nsrbank")); // 纳税人开户行
    	invoiceinfo.put("nsrbankid", PropertiesUtils.getString("WX_nsrbankid")); // 纳税人银行账号
    	// 购货方
    	invoiceinfo.put("ghfmc", orderInvoiceInfo.getTitle());	// 购货方名称
    	invoiceinfo.put("ghfnsrsbh", orderInvoiceInfo.getTaxno());	// 购货方识别号
    	invoiceinfo.put("ghfdz", orderInvoiceInfo.getAddr());	// 	购货方地址
    	invoiceinfo.put("ghfdh", orderInvoiceInfo.getPhone());	//	购货方电话
    	invoiceinfo.put("ghfbank", orderInvoiceInfo.getBanktype()); // 购货方开户行
    	invoiceinfo.put("ghfbankid", orderInvoiceInfo.getBankno()); // 购货方银行帐号
    	// 开票信息
    	invoiceinfo.put("kpr", cusname); // 	开票人
    	invoiceinfo.put("jshj", money.toString()); // 价税合计
    	invoiceinfo.put("hjje", amountInTotal.toString()); // 合计金额
    	invoiceinfo.put("hjse", taxInTotal.toString()); // 合计税额
    	// 发票行项目数据
    	JSONArray invoicedetail_list = new JSONArray();
    	JSONObject invoicedetail = new JSONObject();
    	invoicedetail.put("fphxz", "0");// 发票行性质 0 正常 1折扣 2 被折扣
    	invoicedetail.put("spbm", PropertiesUtils.getString("WX_INVOICE_19SPBM"));// 19位税收分类编码
    	invoicedetail.put("xmmc", "物流辅助服务");// 项目名称 
    	invoicedetail.put("xmsl", "1");// 项目数量
    	invoicedetail.put("xmdj", amountInTotal.toString());// 项目金额 不含税，单位元 两位小数
    	invoicedetail.put("xmje", amountInTotal.toString());// 项目金额 不含税，单位元 两位小数
    	invoicedetail.put("sl", taxRate.toString());// 税率 精确到两位小数 如0.01 
    	invoicedetail.put("se", taxInTotal.toString());
    	invoicedetail_list.add(invoicedetail);
    	invoiceinfo.put("invoicedetail_list", invoicedetail_list); // 发票行项目数据
    	
    	JSONObject makeOutAnInvoice = new JSONObject();
    	makeOutAnInvoice.put("invoiceinfo", invoiceinfo);
    	
    	// 开具蓝票 url
    	String reqUrl = "https://api.weixin.qq.com/card/invoice/makeoutinvoice?access_token=ACCESS_TOKEN";
    	AccessToken access_token = (AccessToken) RedisUtil.get("accessToken");
    	reqUrl = reqUrl.replace("ACCESS_TOKEN", access_token.getToken());
    	// 调用
    	LoggerUtil.info("\n开具蓝票makeoutinvoice_req:" + makeOutAnInvoice.toString());
    	JSONObject res = WeixinUtil.httpRequest(reqUrl, "POST", makeOutAnInvoice.toString());
    	LoggerUtil.info("\n开具蓝票makeoutinvoice_res:" + res.toString());
    	
    	ExceptionUtil.checkNotNull(res, "开具蓝票异常，请检查原因！");
    	LoggerUtil.info("\n===========================开具蓝票（makeoutinvoice）end=======================================\n");
    }

}