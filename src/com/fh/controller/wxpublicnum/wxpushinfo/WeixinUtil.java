package com.fh.controller.wxpublicnum.wxpushinfo;

import com.alibaba.fastjson.JSONException;
import com.fh.common.constant.MsgOfTmpCode;
import com.fh.common.constant_enum.APP_RESPONSE_CODE;
import com.fh.controller.wxpublicnum.MyX509TrustManager;
import com.fh.controller.wxpublicnum.WeChatConst;
import com.fh.entity.app.AppResponseBean;
import com.fh.service.SmsSendService;
import com.fh.util.GsonUtils;
import com.fh.util.PageData;
import com.fh.util.PropertiesUtils;
import com.fh.util.WXTokenUtil;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeixinUtil {

	private static Logger log = LoggerFactory.getLogger(WeixinUtil.class);
	@Autowired
	private SmsSendService sendService;

	/**
	 * 获取access_token
	 * 
	 * @param appid
	 *            凭证
	 * @param appsecret
	 *            密钥
	 * @return
	 */
	public static AccessToken getAccessToken(String appid, String appsecret) {
		AccessToken accessToken = null;
		String requestUrl = access_token_url.replace("APPID", appid).replace(
				"APPSECRET", appsecret);
		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null != jsonObject) {
			try {
				accessToken = new AccessToken();
				accessToken.setToken(jsonObject.getString("access_token"));
				accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
			} catch (JSONException e) {
				accessToken = null;
				// 获取token失败
				log.error("获取token失败 errcode:{} errmsg:{}",
						jsonObject.getInt("errcode"),
						jsonObject.getString("errmsg"));
			}
		}
		return accessToken;
	}

	/**
	 * 描述: 发起https请求并获取结果
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * @param outputStr
	 *            提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static JSONObject httpRequest(String requestUrl,
			String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url
					.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);

			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}
			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonObject = JSONObject.fromObject(buffer.toString());
		} catch (ConnectException ce) {
			log.error("Weixin server connection timed out.");
		} catch (Exception e) {
			log.error("https request error:{}", e);
		}
		return jsonObject;
	}

	// 获取access_token的接口地址（GET） 限200（次/天）
	public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

	public final static String template_id_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

	public final static String sendTemplateMessage_url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";

	/**
	 * 
	 * @Title: orderGenerate
	 * @Description: 订单生成 author：tangqm 2018年6月7日
	 * @param pd
	 */
	public static void orderGenerate(PageData pd) {
		TemplateNew tem = new TemplateNew();
		tem.setTemplateId(WeChatConst.ORDER_GENERATE);
		tem.setTopColor("#00DD00");
		tem.setUrl("http://"+ PropertiesUtils.getString("WX_server_domain") +"/wx/02dingdanguanli.html");
		tem.setToUser(pd.getString("openid"));
		List<TemplateParam> paras = new ArrayList<TemplateParam>();
		paras.add(new TemplateParam("first", "您好，您有一个新订单生成", "#FF3333"));
		paras.add(new TemplateParam("keyword1", pd.get("orderno") + "",
				"#0044BB"));
		paras.add(new TemplateParam("keyword2", pd.get("codename") + "",
				"#0044BB"));
		paras.add(new TemplateParam("keyword3", pd.get("srcaddress") + "",
				"#0044BB"));
		paras.add(new TemplateParam("keyword4", pd.get("addtime") + "",
				"#0044BB"));
		paras.add(new TemplateParam("remark",
				"点击本通知可至订单管理查看详情以及物流信息，如有问题请联系客服人员。", "#AAAAAA"));
		tem.setTemplateParamList(paras);
		sendTemplateMsg(tem);
		log.info("openid:" + pd.getString("openid") + ",orderno:"
				+ pd.get("orderno") + "生成订单");

	}

	/**
	 * 发送短信的接口
	 * 
	 * @param smsCode
	 * @param orderNo
	 * @param mobile
	 */

	public void orderGeMsg(String smsCode, String orderNo, String mobile) {
		Map<String, String> msgMap = new HashMap<String, String>();
		msgMap.put("mobile", mobile);
		msgMap.put("smscode", smsCode);// 模板编号
		msgMap.put("orderno", orderNo);// 订单编号
		msgMap.put("header", MsgOfTmpCode.HEADER);// header

		String retMsg = "";
		try {
			retMsg = sendService.smsTemplateSend(new Gson().toJson(msgMap));
			AppResponseBean appResponseBean = GsonUtils.jsonToObject(retMsg,
					AppResponseBean.class);
			if (APP_RESPONSE_CODE.SUCCESS.getValue().equals(
					appResponseBean.getCode())) {
				log.info("手机号为：" + mobile + "的客户短信成功");
			} else {
				log.error("手机号为：" + mobile + "的客户短信失败");
			}

		} catch (JsonSyntaxException e) {
			log.info(retMsg);
			log.error("json转换异常" + e);
		} catch (Exception e) {
			log.error("手机号为：" + mobile + "的客户短信发送失败" + e);
		}
	}

	/**
	 * 
	 * @Title: orderStatus
	 * @Description: 订单状态 author：tangqm 2018年6月7日
	 * @param pd
	 */
	public static void orderStatus(PageData pd, String msg) {
		TemplateNew tem = new TemplateNew();
		tem.setTemplateId(WeChatConst.ORDER_STATUS);
		tem.setTopColor("#00DD00");
		tem.setUrl("http://"+ PropertiesUtils.getString("WX_server_domain") +"/wx/02dingdanguanli.html");
		tem.setToUser(pd.getString("openid"));
		List<TemplateParam> paras = new ArrayList<TemplateParam>();
		paras.add(new TemplateParam("first", "尊敬的客户，您好", "#FF3333"));
		paras.add(new TemplateParam("keyword1", pd.get("orderno") + "",
				"#0044BB"));
		paras.add(new TemplateParam("keyword2", pd.get("udmname") + msg,
				"#0044BB"));
		paras.add(new TemplateParam("remark",
				"点击本通知可至订单管理查看详情以及物流信息，如有问题请联系客服人员。", "#AAAAAA"));
		tem.setTemplateParamList(paras);
		sendTemplateMsg(tem);
		log.info("openid:" + pd.getString("openid") + ",orderno:"
				+ pd.get("orderno") + "修改订单状态");
	}

	/**
	 * 
	 * @Title: deliverySucc
	 * @Description: 派送成功 author：tangqm 2018年6月7日
	 * @param pd
	 */
	public static void deliverySucc(PageData pd) {
		TemplateNew tem = new TemplateNew();
		tem.setTemplateId(WeChatConst.DELIVERY_SUCC);
		tem.setTopColor("#00DD00");
		tem.setUrl("http://"+ PropertiesUtils.getString("WX_server_domain") +"/wx/01index.html");
		tem.setToUser(pd.getString("openid"));
		List<TemplateParam> paras = new ArrayList<TemplateParam>();
		paras.add(new TemplateParam("first", "包裹派送成功！", "#FF3333"));
		paras.add(new TemplateParam("keyword1", pd.get("orderno") + "",
				"#0044BB"));
		paras.add(new TemplateParam("keyword2", "行李运输", "#0044BB"));
		paras.add(new TemplateParam("keyword3",
				pd.get("actionfinishtime") + "", "#0044BB"));
		paras.add(new TemplateParam("remark", "欢迎您再次使用!", "#AAAAAA"));
		tem.setTemplateParamList(paras);
		sendTemplateMsg(tem);
		log.info("openid:" + pd.getString("openid") + ",orderno:"
				+ pd.get("orderno") + "派送成功");
	}

	/**
	 * 
	 * @Title: arrivedesc
	 * @Description: 抵达目的地 author：tangqm 2018年6月7日
	 * @param pd
	 */
	public static void arrivedesc(PageData pd) {
		TemplateNew tem = new TemplateNew();
		tem.setTemplateId(WeChatConst.ARRIVE_DESC);
		tem.setTopColor("#00DD00");
		tem.setUrl("http://"+ PropertiesUtils.getString("WX_server_domain") +"/wx/01index.html");
		tem.setToUser(pd.getString("openid"));
		List<TemplateParam> paras = new ArrayList<TemplateParam>();
		paras.add(new TemplateParam("first", "尊敬的用户，您有新的快件到达!", "#FF3333"));
		paras.add(new TemplateParam("keyword1", pd.get("servicecentername")
				+ "", "#0044BB"));
		paras.add(new TemplateParam("keyword2",
				pd.get("actionfinishtime") + "", "#0044BB"));
		paras.add(new TemplateParam("keyword3", "景沛物流" + "", "#0044BB"));
		paras.add(new TemplateParam("keyword4", pd.get("orderno") + "",
				"#0044BB"));
		paras.add(new TemplateParam("remark", "点击详情查询取件码，感谢您的使用。", "#AAAAAA"));
		tem.setTemplateParamList(paras);
		sendTemplateMsg(tem);
		log.info("openid:" + pd.getString("openid") + ",orderno:"
				+ pd.get("orderno") + "抵达目的地");
	}

	/**
	 * 
	 * @Title: autoFail
	 * @Description: 自动分配失败 author：tangqm 2018年6月8日
	 * @param pd
	 */
	public static void autoFail(PageData pd) {
		TemplateNew tem = new TemplateNew();
		tem.setTemplateId(WeChatConst.AUTO_FAIL);
		tem.setTopColor("#00DD00");
		tem.setUrl("http://"+ PropertiesUtils.getString("WX_server_domain") +"/jpoa/");
		tem.setToUser("okV0d1TwfWRFrRDTyuY0nI-jSA4E"); // TODO 暂时写死控台人员
		List<TemplateParam> paras = new ArrayList<TemplateParam>();
		paras.add(new TemplateParam("first", "有一单自动分配失败的订单，请核实", "#FF3333"));
		paras.add(new TemplateParam("keyword1", pd.get("orderno") + "",
				"#0044BB"));
		paras.add(new TemplateParam("keyword2", pd.get("remark") + "",
				"#0044BB"));
		paras.add(new TemplateParam("remark", "请及时核实此单,点击本通知可跳往物流管理系统",
				"#AAAAAA"));
		tem.setTemplateParamList(paras);
		sendTemplateMsg(tem);
	}

	/**
	 * 微信发消息 tqm
	 * @return
	 */
	private static void sendTemplateMsg(TemplateNew template) {
		try {
			log.info("TemplateNew:" + template);
			AccessToken accessToken = WXTokenUtil.getAccessToken();
			String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
			requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken.getToken());
			String jsonResult = com.fh.util.HttpClientUtil.doPostJson(requestUrl,
					template.toJSON());
			log.info("openId:" + template.getToUser() + ",jsonResult:" + jsonResult);
		} catch (Exception e) {
			log.error("sendTemplateMsg:"+e.getLocalizedMessage());
		}
	}

	private void param() {

	}

}
