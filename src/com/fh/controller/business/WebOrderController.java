package com.fh.controller.business;

import com.alibaba.fastjson.JSONObject;
import com.fh.common.constant.ConfigCenterKeys;
import com.fh.common.constant_enum.AIRPORTORDER_TYPE;
import com.fh.common.constant_enum.APP_RESPONSE_CODE;
import com.fh.common.constant_enum.ORDER_STATUS;
import com.fh.common.constant_enum.UPDATE_TYPE;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.app.AppRequestBean;
import com.fh.entity.app.AppResponseBean;
import com.fh.entity.app.counterservice.CheckAddrReqBean;
import com.fh.entity.app.counterservice.CheckAddrResBean;
import com.fh.entity.app.order.*;
import com.fh.entity.business.WebOrderPageResBean;
import com.fh.entity.configcenter.GoldPriceDetail;
import com.fh.entity.configcenter.SpecialPriceDetail;
import com.fh.entity.h5.H5CheckAddrResBean;
import com.fh.service.ConfigCenter.ConfigCenterService;
import com.fh.service.order.OrderAddressService;
import com.fh.service.order.OrderBaggageService;
import com.fh.service.order.OrderEvaluateService;
import com.fh.service.order.orderinfo.OrderInfoService;
import com.fh.service.order.orderinfo.OrderMainService;
import com.fh.service.report.reportFormsService;
import com.fh.util.ExceptionUtil;
import com.fh.util.PageData;
import com.fh.util.RulesCheckedException;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 企业下单接口
 * tangqm
 * 2019年3月25日
 */
@Controller
@RequestMapping(value = "weborder")
public class WebOrderController extends BaseController {

	@Autowired
	private OrderInfoService orderInfoService;
	@Autowired
	private com.fh.service.report.reportFormsService reportFormsService;

	/**
	 * @desc web_订单列表查询
	 * @auther tangqm
	 * @history 2019年3月25日
	 */
	@ResponseBody
	@RequestMapping(value = "/findWebOrderMainList", produces = "application/json;charset=UTF-8")
	public String findAppOrderMainList(@RequestBody AppRequestBean reqParm) {
		AppResponseBean rtBean = doValidate(reqParm);
		if (!rtBean.getCode().equals(APP_RESPONSE_CODE.SUCCESS.getValue())) {
			return gson.toJson(rtBean);
		}
		Page page = new Gson().fromJson(reqParm.getData().toString(), Page.class);
		WebOrderPageResBean data = new WebOrderPageResBean();
		if (page == null) {
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("查询订单信息失败，原因是请求参数转换异常");
			return gson.toJson(rtBean);
		}
		try {
			List<PageData> pageList = orderInfoService.orderMainlistPage(page);
			data.setPage(page);
			data.setPageList(pageList);
			rtBean.setJsonData(new Gson().toJson(data));
			rtBean.setCode(APP_RESPONSE_CODE.SUCCESS.getValue());
			rtBean.setMsg("查询订单列表信息成功");
			return gson.toJson(rtBean);
		} catch (Exception e) {
			logger.error("查询订单列表信息非预期异常:" + e);
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("非预期异常，请联系IT");
			return gson.toJson(rtBean);
		}
	}

	/**
	 * @desc web_订单详情查询
	 * @auther tangqm
	 * @history 2019年3月26日
	 */
	@ResponseBody
	@RequestMapping(value = "/findWebOrderDetailList", produces = "application/json;charset=UTF-8")
	public String findWebOrderDetailList(@RequestBody AppRequestBean reqParm) {
		AppResponseBean rtBean = doValidate(reqParm);
		if (!rtBean.getCode().equals(APP_RESPONSE_CODE.SUCCESS.getValue())) {
			return gson.toJson(rtBean);
		}
		AppOrderDetailsReqData reqDetails = new Gson().fromJson(reqParm.getData().toString(), AppOrderDetailsReqData.class);
		if (reqDetails == null) {
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("查询订单详情失败，原因是请求参数转换异常");
			return gson.toJson(rtBean);
		}
		try {
			AppOrderDetailsResData resDetails = orderInfoService.findWebAppOrderDetails(reqDetails);
			rtBean.setJsonData(new Gson().toJson(resDetails));
			rtBean.setCode(APP_RESPONSE_CODE.SUCCESS.getValue());
			rtBean.setMsg("查询订单详情信息成功");
			return gson.toJson(rtBean);
		} catch (Exception e) {
			logger.error("查询订单详情信息非预期异常:" + e);
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("非预期异常，请联系IT");
			return gson.toJson(rtBean);
		}
	}

	/**
	 * @desc web_导出报表
	 * @auther tangqm
	 * @history 2019年3月26日
	 */
	@ResponseBody
	@RequestMapping(value = "/outWebOrderMainExcel", produces = "application/json;charset=UTF-8")
	public String outWebOrderMainExcel(@RequestBody AppRequestBean reqParm) {
		AppResponseBean rtBean = doValidate(reqParm);
		if (!rtBean.getCode().equals(APP_RESPONSE_CODE.SUCCESS.getValue())) {
			return gson.toJson(rtBean);
		}
		Page page = new Gson().fromJson(reqParm.getData().toString(), Page.class);
		WebOrderPageResBean data = new WebOrderPageResBean();
		if (page == null) {
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("导出EXCEL失败，原因是请求参数转换异常");
			return gson.toJson(rtBean);
		}
		try {
			List<PageData> reportForms = reportFormsService.reportFormslistPage(page);
			data.setPageList(reportForms);
			rtBean.setJsonData(new Gson().toJson(data));
			rtBean.setCode(APP_RESPONSE_CODE.SUCCESS.getValue());
			rtBean.setMsg("导出EXCEL成功");
			return gson.toJson(rtBean);
		} catch (Exception e) {
			logger.error("导出EXCEL非预期异常:" + e);
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("导出EXCEL非预期异常，请联系IT");
			return gson.toJson(rtBean);
		}
	}
	
}
