package com.fh.controller.app.smsSend;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import com.fh.entity.order.OrderNotesInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.common.constant_enum.APP_RESPONSE_CODE;
import com.fh.controller.base.BaseController;
import com.fh.entity.app.AppRequestBean;
import com.fh.entity.app.AppResponseBean;
import com.fh.service.SmsSendService;
import com.google.gson.Gson;
/**
 * 
 * 项目名称：jpoa   
 *
 * 类描述：app端发送短信
 * 类名称：com.fh.controller.app.smsSend.SmsSendContrller     
 * 创建人：tangqm
 * 创建时间：2018年9月26日 下午3:06:03   
 * 修改人：
 * 修改时间：2018年9月26日 下午3:06:03   
 * 修改备注：
 */
@Controller
@RequestMapping(value="/sms")
public class SmsSendContrller extends BaseController {

	@Autowired
	private SmsSendService smsSendService;
	
	/**
	 * 
	 * @Title: smsSend
	 * @Description: 发送短信
	 * author：tangqm
	 * 2018年9月26日
	 * @param reqParm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/smsSend", produces = "application/json;charset=UTF-8" )
	public String smsSend( @RequestBody AppRequestBean reqParm){
		AppResponseBean rtBean = doH5Validate( reqParm );
		if (!rtBean.getCode().equals(APP_RESPONSE_CODE.SUCCESS.getValue())) {
			return new Gson().toJson( rtBean );
		}
		logger.error("发送短信请求入参："+reqParm.getData().toString());
		String smsRespStr = null;
		try {
			smsRespStr = smsSendService.smsTemplateSend(reqParm.getData().toString());
			logger.error(smsRespStr);
		} catch (Exception e) {
			logger.error("发送短信请求异常!请求内容："+reqParm.getData().toString()+"， 异常错误："+e.getLocalizedMessage());
			e.printStackTrace();
			rtBean.setCode( APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg( "发送短信请求异常!请求内容："+reqParm.getData().toString()+"， 异常错误："+e.getLocalizedMessage() );
			return new Gson().toJson( rtBean );
		}
		return smsRespStr;
	}

	/**
	 * @desc 短信
	 * @auther tangqm
	 * @date 2018年6月2日 21点16分
	 */
	@RequestMapping(value = "/smsSend_in")
	@ResponseBody
	public Map<String, Object> smsSend_in(String req) {
		logger.error("发送短信请求入参："+ req);
		// 更改订单状态
		HashMap<String, Object> res = new HashMap<String, Object>();

		String smsRespStr = null;
		try {
			smsRespStr = smsSendService.smsTemplateSend1(req);
			logger.error(smsRespStr);
			res.put("success", true);
			res.put("resMag", smsRespStr);
		} catch (Exception e) {
			res.put("success", false);
			res.put("resMag", e.getMessage());
		}
		return res;
	}
	
	public String acceptJSON(HttpServletRequest request) {
		String acceptjson = "";
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(ServletInputStream) request.getInputStream(), "utf-8"));
			StringBuffer sb = new StringBuffer("");
			String temp;
			while ((temp = br.readLine()) != null) {
				sb.append(temp);
				acceptjson = sb.toString();
			}
			br.close();
			acceptjson = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return acceptjson;
	}
}
