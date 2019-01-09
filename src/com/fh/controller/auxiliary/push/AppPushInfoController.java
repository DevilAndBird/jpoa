package com.fh.controller.auxiliary.push;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.common.constant_enum.APP_RESPONSE_CODE;
import com.fh.controller.base.BaseController;
import com.fh.entity.app.AppRequestBean;
import com.fh.entity.app.AppResponseBean;
import com.fh.entity.app.auxiliary.push.req.AppPushNum;
import com.fh.entity.app.auxiliary.push.req.AppPushReqData;
import com.fh.entity.app.auxiliary.push.res.AppPushResData;
import com.fh.entity.auxiliary.push.PushInfo;
import com.fh.service.auxiliary.push.PushInfoService;
import com.fh.util.ExceptionUtil;
import com.fh.util.RulesCheckedException;
import com.google.gson.Gson;
/**
 * app端极光推送
 * @author tqm
 * @date 2018年11月5日
 */
/**
 * @author zhangjj
 * app角色推送信息
 */
@Controller
@RequestMapping(value="/push")
public class AppPushInfoController extends BaseController{

	@Autowired
	private PushInfoService pushInfoService;
	
	/**
	 * @desc app_查询推送信息userid
	 * @auther zhangjj
	 * @history 2018年2月28日
	 */
	@ResponseBody
	@RequestMapping(value = "/findPushInfoByUserid", produces = "application/json;charset=UTF-8")
	public String findPushInfoByUserid(@RequestBody AppRequestBean reqParm) {
		AppResponseBean rtBean = doValidate(reqParm);
		if (rtBean.getCode().equals(APP_RESPONSE_CODE.FAIL.getValue()) ||rtBean.getCode().equals(APP_RESPONSE_CODE.LOGINERROR.getValue())) {
			return gson.toJson(rtBean);
		}

		AppPushReqData reqData = new Gson().fromJson(reqParm.getData().toString(), AppPushReqData.class);
		if (reqData == null) {
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("请求参数转换异常");
			return gson.toJson(rtBean);
		}

		try {
			ExceptionUtil.checkNotNull(reqData.getUserid(), "人员登陆id不能为空");
			
			List<PushInfo> pushInfoList = (List<PushInfo>) pushInfoService.getPushInfoByUserid(reqData.getUserid(), reqData.getType());

			// 返回参数
			AppPushResData resData = new AppPushResData();
			resData.setPushInfoList(pushInfoList);
			
			rtBean.setJsonData(new Gson().toJson(resData));
			rtBean.setCode(APP_RESPONSE_CODE.SUCCESS.getValue());
			rtBean.setMsg("查询推送信息userid成功");
			return gson.toJson(rtBean);
		} catch (RulesCheckedException e) {
		    logger.error("查询推送信息userid校验异常:" + e.getMessage());
            rtBean.setCode(APP_RESPONSE_CODE.CHECK.getValue());
            rtBean.setMsg("校验异常：" + e.getMessage());
            return gson.toJson(rtBean);
		} catch (Exception e) {
			logger.error("查询推送信息userid非预期异常:" + e.getLocalizedMessage());
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("查询推送信息userid非预期异常，请联系IT");
			return gson.toJson(rtBean);
		}
	}
	
	/**
	 * @desc app_查询推送条数未读
	 * @auther zhangjj
	 * @history 2018年2月28日
	 */
	@ResponseBody
	@RequestMapping(value = "/countIsread_N", produces = "application/json;charset=UTF-8")
	public String countIsread_N(@RequestBody AppRequestBean reqParm) {
		AppResponseBean rtBean = doValidate(reqParm);
		if (rtBean.getCode().equals(APP_RESPONSE_CODE.FAIL.getValue()) ||rtBean.getCode().equals(APP_RESPONSE_CODE.LOGINERROR.getValue())) {
			return gson.toJson(rtBean);
		}

		AppPushReqData reqData = new Gson().fromJson(reqParm.getData().toString(), AppPushReqData.class);
		if (reqData == null) {
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("查询订单信息成功失败，原因是请求参数转换异常");
			return gson.toJson(rtBean);
		}

		try {
			ExceptionUtil.checkNotNull(reqData.getUserid(), "人员登陆id不能为空");
			List<AppPushNum> appPushNum = pushInfoService.countIsread_N(reqData.getUserid());
			
			// 组装返回
			AppPushResData resBean = new AppPushResData();
			resBean.setAppPushNumlist(appPushNum);			
			
			rtBean.setJsonData(new Gson().toJson(resBean));
			rtBean.setCode(APP_RESPONSE_CODE.SUCCESS.getValue());
            rtBean.setMsg("查询推送条数未读成功");
            return new Gson().toJson(rtBean);
		} catch (RulesCheckedException e) {
		    logger.error("查询推送条数未读校验异常:" + e.getMessage());
            rtBean.setCode(APP_RESPONSE_CODE.CHECK.getValue());
            rtBean.setMsg("校验异常：" + e.getMessage());
            return gson.toJson(rtBean);
		} catch (Exception e) {
			logger.error("查询推送条数未读非预期异常:" + e.getLocalizedMessage());
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("查询推送条数未读非预期异常，请联系IT");
			return gson.toJson(rtBean);
		}
	}
	
	/**
	 * @desc app_更新未读为已读
	 * @auther zhangjj
	 * @history 2018年2月28日
	 */
	@ResponseBody
	@RequestMapping(value = "/updateIsreadToY", produces = "application/json;charset=UTF-8")
	public String updateIsreadToY(@RequestBody AppRequestBean reqParm) {
		AppResponseBean rtBean = doValidate(reqParm);
		if (rtBean.getCode().equals(APP_RESPONSE_CODE.FAIL.getValue()) ||rtBean.getCode().equals(APP_RESPONSE_CODE.LOGINERROR.getValue())) {
			return gson.toJson(rtBean);
		}

		AppPushReqData reqData = new Gson().fromJson(reqParm.getData().toString(), AppPushReqData.class);
		if (reqData == null) {
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("查询订单信息成功失败，原因是请求参数转换异常");
			return gson.toJson(rtBean);
		}

		try {
			ExceptionUtil.checkNotNull(reqData.getUserid(), "人员登陆id不能为空");
			
			pushInfoService.updateIsreadToY(reqData.getUserid(), reqData.getId(), reqData.getType(), reqData.getIsread());
			
			rtBean.setCode(APP_RESPONSE_CODE.SUCCESS.getValue());
			rtBean.setMsg("查询推送条数未读成功");
			return gson.toJson(rtBean);
		} catch (RulesCheckedException e) {
		    logger.error("查询推送条数未读校验异常:" + e.getMessage());
            rtBean.setCode(APP_RESPONSE_CODE.CHECK.getValue());
            rtBean.setMsg("更新未读为已读校验异常：" + e.getMessage());
            return gson.toJson(rtBean);
		} catch (Exception e) {
			logger.error("更新未读为已读非预期异常:" + e.getLocalizedMessage());
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("更新未读为已读非预期异常，请联系IT");
			return gson.toJson(rtBean);
		}
	}
	
}