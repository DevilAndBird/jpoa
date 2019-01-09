package com.fh.controller.app.order;

import com.alibaba.fastjson.JSONObject;
import com.fh.common.constant.ConfigCenterKeys;
import com.fh.common.constant_enum.AIRPORTORDER_TYPE;
import com.fh.common.constant_enum.APP_RESPONSE_CODE;
import com.fh.common.constant_enum.ORDER_STATUS;
import com.fh.common.constant_enum.UPDATE_TYPE;
import com.fh.controller.base.BaseController;
import com.fh.entity.app.AppRequestBean;
import com.fh.entity.app.AppResponseBean;
import com.fh.entity.app.order.*;
import com.fh.entity.configcenter.GoldPriceDetail;
import com.fh.entity.configcenter.SpecialPriceDetail;
import com.fh.service.ConfigCenter.ConfigCenterService;
import com.fh.service.order.OrderBaggageService;
import com.fh.service.order.OrderEvaluateService;
import com.fh.service.order.orderinfo.OrderInfoService;
import com.fh.service.order.orderinfo.OrderMainService;
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
 * 
 * 项目名称：jpoa   
 *
 * 类描述：app订单列表，订单详情以及附属功能接口
 * 类名称：com.fh.controller.app.order.AppOrderController     
 * 创建人：tangqm
 * 创建时间：2018年9月26日 下午3:01:12   
 * 修改人：
 * 修改时间：2018年9月26日 下午3:01:12   
 * 修改备注：
 */
@Controller
@RequestMapping(value = "apporder")
public class AppOrderController extends BaseController {
	@Autowired
	private OrderInfoService orderInfoService;
	@Autowired
	private OrderBaggageService orderBaggageService;
	@Autowired
	private OrderEvaluateService orderEvaluateService;
	@Autowired
	private OrderMainService orderMainService;
	@Autowired
	private ConfigCenterService priceRuleService;

	/**
	 * @desc app_订单列表查询
	 * @auther zhangjj
	 * @history 2018年2月28日
	 */
	@ResponseBody
	@RequestMapping(value = "/findAppOrderMainList", produces = "application/json;charset=UTF-8")
	public String findAppOrderMainList(@RequestBody AppRequestBean reqParm) {
		AppResponseBean rtBean = doValidate(reqParm);
		if (!rtBean.getCode().equals(APP_RESPONSE_CODE.SUCCESS.getValue())) {
			return gson.toJson(rtBean);
		}

		AppOrderReqData appOrderReqData = new Gson().fromJson(reqParm.getData().toString(), AppOrderReqData.class);
		if (appOrderReqData == null) {
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("查询订单信息成功失败，原因是请求参数转换异常");
			return gson.toJson(rtBean);
		}

		try {
			List<AppOrderResData> appOrderResDataList = orderInfoService.findAppOrderMainList(appOrderReqData);
			rtBean.setJsonData(new Gson().toJson(appOrderResDataList));
			rtBean.setCode(APP_RESPONSE_CODE.SUCCESS.getValue());
			rtBean.setMsg("查询订单列表信息成功");
			return gson.toJson(rtBean);
		} catch (RulesCheckedException e) {
		    logger.error("查询订单列表信息校验异常:" + e);
            rtBean.setCode(APP_RESPONSE_CODE.CHECK.getValue());
            rtBean.setMsg("校验异常：" + e.getMessage());
			rtBean.setJsonData("");//ios 强转，保持key存在
            return gson.toJson(rtBean);
		} catch (Exception e) {
			logger.error("查询订单列表信息非预期异常:" + e);
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("非预期异常，请联系IT");
			rtBean.setJsonData("");//ios 强转，保持key存在
			return gson.toJson(rtBean);
		}
	}
	
	/**
	 * 
	 * @Title: updateprepaid
	 * @Description: 修改订单支付成功
	 * author：tangqm
	 * 2018年10月22日
	 * @param reqParm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateprepaid", produces = "application/json;charset=UTF-8")
	public String updateprepaid(@RequestBody AppRequestBean reqParm) {
		AppResponseBean rtBean = doValidate(reqParm);
		if (!rtBean.getCode().equals(APP_RESPONSE_CODE.SUCCESS.getValue())) {
			return gson.toJson(rtBean);
		}

		AppUpdatePrepaidReqData appUpdatePrepaidReqData = new Gson().fromJson(reqParm.getData().toString(), AppUpdatePrepaidReqData.class);
		if (appUpdatePrepaidReqData == null) {
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("修改订单支付成功，原因是请求参数转换异常");
			return gson.toJson(rtBean);
		}

		try {
			orderInfoService.updateprepaid(appUpdatePrepaidReqData.getOrderno());
			rtBean.setCode(APP_RESPONSE_CODE.SUCCESS.getValue());
			rtBean.setMsg("支付状态修改成功");
			return gson.toJson(rtBean);
		} catch (RulesCheckedException e) {
		    logger.error("支付状态修改成功校验,异常:" + e);
            rtBean.setCode(APP_RESPONSE_CODE.CHECK.getValue());
            rtBean.setMsg("校验异常：" + e.getMessage());
            return gson.toJson(rtBean);
		} catch (Exception e) {
			logger.error("支付状态修改成功,非预期异常:" + e);
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("非预期异常，请联系IT");
			return gson.toJson(rtBean);
		}
	}
	
	/**
	 * @desc app 删除订单
	 * @auther zhangjj
	 * @date 2018年10月30日
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteOrder", produces = "application/json;charset=UTF-8")
	public String deleteOrder(@RequestBody AppRequestBean reqParm) {
		AppResponseBean rtBean = doValidate(reqParm);
		if (!rtBean.getCode().equals(APP_RESPONSE_CODE.SUCCESS.getValue())) {
			return gson.toJson(rtBean);
		}

		AppDeleteOrderReqData reqData = new Gson().fromJson(reqParm.getData().toString(), AppDeleteOrderReqData.class);
		if (reqData == null) {
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("请求参数转换异常");
			return gson.toJson(rtBean);
		}

		try {
//			String status = orderMainService.getOrderStatusByOrderid(reqData.getOrderid());
//			ExceptionUtil.checkBoolean(!ORDER_STATUS.CANCELLED.getValue().equals(status) && !ORDER_STATUS.WAITPPAY.getValue().equals(status), "订单为取消状态或未支付才能删除");
			orderMainService.updateIsvalidByid(reqData.getOrderid());
			rtBean.setCode(APP_RESPONSE_CODE.SUCCESS.getValue());
			rtBean.setMsg("删除订单成功");
			return gson.toJson(rtBean);
		} catch (RulesCheckedException e) {
		    logger.error("删除订单失败，校验异常:" + e);
            rtBean.setCode(APP_RESPONSE_CODE.CHECK.getValue());
            rtBean.setMsg("删除订单失败，校验异常：" + e.getMessage());
            return gson.toJson(rtBean);
		} catch (Exception e) {
			logger.error("删除订单失败，非预期异常:" + e);
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("删除订单失败，非预期异常，请联系IT");
			return gson.toJson(rtBean);
		}
	}
	
	/**
	 * @desc app_取派员任务列表(未增加同一目的地订单汇总功能);
	 * @auther zhangjj
	 * @history 2018年2月28日
	 */
	@ResponseBody
	@RequestMapping(value = "/findAppOrderTaskList", produces = "application/json;charset=UTF-8")
	public String findAppOrderTaskList(@RequestBody AppRequestBean reqParm) {
		AppResponseBean rtBean = doValidate(reqParm);
		if (!rtBean.getCode().equals(APP_RESPONSE_CODE.SUCCESS.getValue())) {
			return gson.toJson(rtBean);
		}
		AppOrderReqData appOrderReqData = new Gson().fromJson(reqParm.getData().toString(), AppOrderReqData.class);
		if (appOrderReqData == null) {
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("查询订单信息失败，原因是请求参数转换异常");
			return gson.toJson(rtBean);
		}

		try {
			List<AppOrderResData> appOrderResDataList = orderInfoService.findAppOrderTaskList(appOrderReqData);
			rtBean.setJsonData(new Gson().toJson(appOrderResDataList));
			rtBean.setCode(APP_RESPONSE_CODE.SUCCESS.getValue());
			rtBean.setMsg("取派员任务列表成功");
			return gson.toJson(rtBean);
		} catch (RulesCheckedException e) {
		    logger.error("取派员任务列表校验异常:" + e.getMessage());
            rtBean.setCode(APP_RESPONSE_CODE.CHECK.getValue());
            rtBean.setMsg("取派员任务列表校验异常：" + e.getMessage());
			rtBean.setJsonData("");//ios 强转，保持key存在
            return gson.toJson(rtBean);
		} catch (Exception e) {
			logger.error("取派员任务列表非预期异常:" + e.getLocalizedMessage());
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("取派员任务列表非预期异常，请联系IT");
			rtBean.setJsonData("");//ios 强转，保持key存在
			return gson.toJson(rtBean);
		}
	}
	
	/**
	 * @desc app_取派员任务列表;
	 * @auther zhangjj
	 * @history 2018年2月28日
	 */
	@ResponseBody
	@RequestMapping(value = "/findAppOrderNumTaskList", produces = "application/json;charset=UTF-8")
	public String findAppOrderNumTaskList(@RequestBody AppRequestBean reqParm) {
		AppResponseBean rtBean = doValidate(reqParm);
		if (!rtBean.getCode().equals(APP_RESPONSE_CODE.SUCCESS.getValue())) {
			return gson.toJson(rtBean);
		}
		AppOrderReqData appOrderReqData = new Gson().fromJson(reqParm.getData().toString(), AppOrderReqData.class);
		if (appOrderReqData == null) {
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("查询任务数量失败，原因是请求参数转换异常");
			return gson.toJson(rtBean);
		}

		try {
			AppOrderNumResData appOrderResDataList = orderInfoService.findAppOrderNumTaskList(appOrderReqData);
			rtBean.setJsonData(new Gson().toJson(appOrderResDataList));
			rtBean.setCode(APP_RESPONSE_CODE.SUCCESS.getValue());
			rtBean.setMsg("任务数量成功");
			return gson.toJson(rtBean);
		} catch (RulesCheckedException e) {
		    logger.error("任务数量校验异常:" + e.getMessage());
            rtBean.setCode(APP_RESPONSE_CODE.CHECK.getValue());
            rtBean.setMsg("取派员任务列表校验异常：" + e.getMessage());
			rtBean.setJsonData("");//ios 强转，保持key存在
            return gson.toJson(rtBean);
		} catch (Exception e) {
			logger.error("任务数量非预期异常:" + e.getLocalizedMessage());
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("任务数量非预期异常，请联系IT");
			rtBean.setJsonData("");//ios 强转，保持key存在
			return gson.toJson(rtBean);
		}
	}
	
	/**
	 * @desc app_取派员任务列表（增加同一目的地址汇总功能）;
	 * @auther zhangjj
	 * @history 2018年2月28日
	 */
	@ResponseBody
	@RequestMapping(value = "/findAppOrderTaskList_total", produces = "application/json;charset=UTF-8")
	public String findAppOrderTaskList_total(@RequestBody AppRequestBean reqParm) {
		AppResponseBean rtBean = doValidate(reqParm);
		if (!rtBean.getCode().equals(APP_RESPONSE_CODE.SUCCESS.getValue())) {
			return gson.toJson(rtBean);
		}
		AppOrderReqData appOrderReqData = new Gson().fromJson(reqParm.getData().toString(), AppOrderReqData.class);
		if (appOrderReqData == null) {
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("查询订单信息成功失败，原因是请求参数转换异常");
			return gson.toJson(rtBean);
		}

		try {
			List<Map<String, List<AppOrderResData>>> appOrderResDataList = orderInfoService.findAppOrderTaskList_total(appOrderReqData);
			rtBean.setJsonData(new Gson().toJson(appOrderResDataList));
			rtBean.setCode(APP_RESPONSE_CODE.SUCCESS.getValue());
			rtBean.setMsg("取派员任务列表成功");
			return gson.toJson(rtBean);
		} catch (RulesCheckedException e) {
		    logger.error("取派员任务列表校验异常:" + e.getMessage());
            rtBean.setCode(APP_RESPONSE_CODE.CHECK.getValue());
            rtBean.setMsg("取派员任务列表校验异常：" + e.getMessage());
			rtBean.setJsonData("");//ios 强转，保持key存在
            return gson.toJson(rtBean);
		} catch (Exception e) {
			logger.error("取派员任务列表非预期异常:" + e.getLocalizedMessage());
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("取派员任务列表非预期异常，请联系IT");
			rtBean.setJsonData("");//ios 强转，保持key存在
			return gson.toJson(rtBean);
		}
	}
	
	/**
	 * @desc app_取派员任务列表（地图）;
	 * @auther zhangjj
	 * @history 2018年2月28日
	 */
	@ResponseBody
	@RequestMapping(value = "/findAppMapTaskList", produces = "application/json;charset=UTF-8")
	public String findAppMapTaskList(@RequestBody AppRequestBean reqParm) {
		AppResponseBean rtBean = doValidate(reqParm);
		if (!rtBean.getCode().equals(APP_RESPONSE_CODE.SUCCESS.getValue())) {
			return gson.toJson(rtBean);
		}
		AppOrderReqData appOrderReqData = new Gson().fromJson(reqParm.getData().toString(), AppOrderReqData.class);
		if (appOrderReqData == null) {
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("查询订单信息失败，原因是请求参数转换异常");
			return gson.toJson(rtBean);
		}

		try {
			List<AppOrderResData> appOrderResDataList = orderInfoService.findAppMapTaskList(appOrderReqData);
			rtBean.setJsonData(new Gson().toJson(appOrderResDataList));
			rtBean.setCode(APP_RESPONSE_CODE.SUCCESS.getValue());
			rtBean.setMsg("地图信息列表查询成功");
			return gson.toJson(rtBean);
		} catch (RulesCheckedException e) {
		    logger.error("地图信息列表查询校验异常:" + e.getMessage());
            rtBean.setCode(APP_RESPONSE_CODE.CHECK.getValue());
            rtBean.setMsg("地图信息列表查询异常：" + e.getMessage());
			rtBean.setJsonData("");//ios 强转，保持key存在
            return gson.toJson(rtBean);
		} catch (Exception e) {
			logger.error("地图信息列表查询非预期异常:" + e.getLocalizedMessage());
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("地图信息列表查询非预期异常，请联系IT");
			rtBean.setJsonData("");//ios 强转，保持key存在
			return gson.toJson(rtBean);
		}
	}
	/**
	 * @desc 用户通知
	 * @auther tangqm
	 * @history 2018年7月18日
	 */
	@ResponseBody
	@RequestMapping(value = "notifyAppCus", produces = "application/json;charset=UTF-8")
	public String notifyAppCus(@RequestBody AppRequestBean reqParm) {
		AppResponseBean rtBean = doValidate(reqParm);
		if (!rtBean.getCode().equals(APP_RESPONSE_CODE.SUCCESS.getValue())) {
			return gson.toJson(rtBean);
		}
		AppOrderReqData appOrderReqData = new Gson().fromJson(reqParm.getData().toString(), AppOrderReqData.class);
		if (appOrderReqData == null) {
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("用户通知失败，原因是请求参数转换异常");
			return gson.toJson(rtBean);
		}
		try {
			String notifyAppCus = orderInfoService.notifyAppCus(appOrderReqData);
			Map notifymsg = new HashMap();
			notifymsg.put("notifymsg", notifyAppCus);
			rtBean.setJsonData(gson.toJson(notifymsg));
			rtBean.setCode(APP_RESPONSE_CODE.SUCCESS.getValue());
			rtBean.setMsg("用户通知接口调用成功");
			return gson.toJson(rtBean);
		} catch (Exception e) {
			logger.error("用户通知非预期异常:" + e.getLocalizedMessage());
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("用户通知非预期异常，请联系IT");
			rtBean.setJsonData("");//ios 强转，保持key存在
			return gson.toJson(rtBean);
		}
	}

	/**
	 * @desc App_订单详情接口
	 * @auther zhangjj
	 * @history 2018年2月28日
	 */
	@ResponseBody
	@RequestMapping(value = "/findAppOrderDetails", produces = "application/json;charset=UTF-8")
	public String findAppOrderDetails(@RequestBody AppRequestBean reqParm) {
		AppResponseBean rtBean = doValidate(reqParm);
		if (!rtBean.getCode().equals(APP_RESPONSE_CODE.SUCCESS.getValue())) {
			return new Gson().toJson(rtBean);
		}

		AppOrderDetailsReqData appOrderDetailsReqData = new Gson().fromJson(reqParm.getData().toString(), AppOrderDetailsReqData.class);
		if (appOrderDetailsReqData == null) {
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("查询订单详情成功失败，原因是请求参数转换异常");
			return new Gson().toJson(rtBean);
		}

		try {
			AppOrderDetailsResData appOrderDetailsResDataList = orderInfoService.findAppAppOrderDetails(appOrderDetailsReqData);
			rtBean.setJsonData(new Gson().toJson(appOrderDetailsResDataList));
			rtBean.setCode(APP_RESPONSE_CODE.SUCCESS.getValue());
			rtBean.setMsg("查询订单详情成功");
			return new Gson().toJson(rtBean);
		} catch (RulesCheckedException e) {
		    logger.error("订单详情查询校验异常:" + e.getMessage());
            rtBean.setCode(APP_RESPONSE_CODE.CHECK.getValue());
            rtBean.setMsg("校验异常：" + e.getMessage());
            return new Gson().toJson(rtBean);
		} catch (Exception e) {
			logger.error("查询订单详情信息非预期异常:" + e.getLocalizedMessage());
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("非预期异常，请联系IT");
			return new Gson().toJson(rtBean);
		}
	}

	/**
	 * @desc 订单状态更新
	 * @auther zhangjj
	 * @history 2018年2月28日
	 */
	@ResponseBody
	@RequestMapping(value = "/updateOrderStatus", produces = "application/json;charset=UTF-8")
	public String updateOrderStatus(@RequestBody AppRequestBean reqParm) {
		AppResponseBean rtBean = doValidate(reqParm);
		if (!rtBean.getCode().equals(APP_RESPONSE_CODE.SUCCESS.getValue())) {
			return new Gson().toJson(rtBean);
		}
		AppOrderReqData reqData = new Gson().fromJson(reqParm.getData().toString(), AppOrderReqData.class);
		if (reqData == null) {
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("更新订单状态失败失败，原因：请求参数转换异常");
			return new Gson().toJson(rtBean);
		}

		try {
		    ExceptionUtil.checkNotEmpty(reqData.getUpdateType(), "更新类型不能为空");
		    ExceptionUtil.checkNotEmpty(reqData.getStatus(), "订单状态不能为空");
		    
		    PageData pd = new PageData();
			pd.put("status",reqData.getStatus());
			pd.put("roleid",reqData.getRoleid());
		    if(UPDATE_TYPE.ORDERID.getValue().equals(reqData.getUpdateType())) {
		        pd.put("orderid",reqData.getId());
		        ExceptionUtil.isFalse(reqData.getId() == null || reqData.getId() == 0, "当更新类型："+ UPDATE_TYPE.ORDERID.getValue() + "订单id必传");
		    }
		    
            if(UPDATE_TYPE.ORDERNO.getValue().equals(reqData.getUpdateType())) {
                pd.put("orderno",reqData.getOrderno());
                ExceptionUtil.checkNotEmpty(reqData.getOrderno(), "当更新类型："+ UPDATE_TYPE.ORDERNO.getValue() + "订单编码必传");
            }
            
            if(UPDATE_TYPE.ORDERIDLIST.getValue().equals(reqData.getUpdateType())) {
                pd.put("orderidList",reqData.getOrderidList());
                ExceptionUtil.checkNotCollectNotEmpty((reqData.getOrderidList()), "当更新类型："+ UPDATE_TYPE.ORDERIDLIST.getValue() + "订单id列表必传");
            }
            if(UPDATE_TYPE.ORDERNOLIST.getValue().equals(reqData.getUpdateType())) {
                pd.put("ordernoList",reqData.getOrdernoList());
                ExceptionUtil.checkNotCollectNotEmpty(reqData.getOrdernoList(), "当更新类型："+ UPDATE_TYPE.ORDERNOLIST.getValue() + "订单编码列表必传");
            }
            
            // 改变已取件 -> 必须是已支付
		    if(ORDER_STATUS.TAKEGOOGSOVER.getValue().equals(reqData.getStatus())) {
		    	List<PageData> resTemp = orderInfoService.checkOrderStatusByOrderid(reqData.getId(), ORDER_STATUS.WAITPICK.getValue());
		    	ExceptionUtil.isTrue(resTemp != null && resTemp.size() == 1, "订单状态不是待取件状态,请刷新系统");
		    }
		    
		    // 待卸车，如果是已卸车则x
		    if(ORDER_STATUS.WAITINGUNLOAD.getValue().equals(reqData.getStatus())) {
		    	List<PageData> resTemp = orderInfoService.checkOrderStatusByOrderidList(reqData.getOrderidList(), ORDER_STATUS.UNLOAD.getValue());
		    	ExceptionUtil.isFalse(resTemp != null && resTemp.size() > 0, "订单状态是已卸载状态,请刷新系统");
		    }
		    
		    // 待装车，如果订单状态是已装车则x
		    if(ORDER_STATUS.WAITTRUCELOADING.getValue().equals(reqData.getStatus())) {
		    	List<PageData> resTemp = orderInfoService.checkOrderStatusByOrderidList(reqData.getOrderidList(), ORDER_STATUS.TRUCELOADING.getValue());
		    	ExceptionUtil.isFalse(resTemp != null && resTemp.size() > 0, "订单状态是已装车状态,请刷新系统");
		    }
		    
			orderInfoService.updateOrderStatus(pd);
            
			rtBean.setCode(APP_RESPONSE_CODE.SUCCESS.getValue());
			rtBean.setMsg("订单状态更改成功");
			return new Gson().toJson(rtBean);
		}catch (RulesCheckedException e) {
		    logger.error("订单状态校验异常:" + e.getMessage());
            rtBean.setCode(APP_RESPONSE_CODE.CHECK.getValue());
            rtBean.setMsg("校验异常：" + e.getMessage());
            return new Gson().toJson(rtBean);
		} catch (Exception e) {
			logger.error("订单状态更改非预期异常:" + e.getMessage());
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("非预期异常，请联系IT");
			return new Gson().toJson(rtBean);
		}
	}
	
    /**
     * @Title: generateOrder
     * @Description: 生成订单
     * 2018年8月13日 author：tangqm
     * @param reqParm
     * @return
     */
	@ResponseBody
	@RequestMapping(value = "/generateOrder", produces = "application/json;charset=UTF-8")
	public String generateOrder(@RequestBody AppRequestBean reqParm) {
		AppResponseBean rtBean = doValidate(reqParm);
		if (!rtBean.getCode().equals(APP_RESPONSE_CODE.SUCCESS.getValue())) {
			return new Gson().toJson(rtBean);
		}
		AppOrderReqData reqData = new Gson().fromJson(reqParm.getData().toString(), AppOrderReqData.class);
		if (reqData == null) {
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("更新订单状态失败失败，原因：请求参数转换异常");
			return new Gson().toJson(rtBean);
		}
		
		try {
			ExceptionUtil.checkNotEmpty(reqData.getUpdateType(), "更新类型不能为空");
			ExceptionUtil.checkNotEmpty(reqData.getStatus(), "订单状态不能为空");
			
			PageData pd = new PageData();
			pd.put("status",reqData.getStatus());
			pd.put("roleid",reqData.getRoleid());
			
			if(UPDATE_TYPE.ORDERID.getValue().equals(reqData.getUpdateType())) {
				pd.put("orderid",reqData.getId());
				ExceptionUtil.isFalse(reqData.getId() == null || reqData.getId() == 0, "当更新类型："+ UPDATE_TYPE.ORDERID.getValue() + "订单id必传");
			}
			
			if(UPDATE_TYPE.ORDERNO.getValue().equals(reqData.getUpdateType())) {
				pd.put("orderno",reqData.getOrderno());
				ExceptionUtil.checkNotEmpty(reqData.getOrderno(), "当更新类型："+ UPDATE_TYPE.ORDERNO.getValue() + "订单编码必传");
			}
			
			if(UPDATE_TYPE.ORDERIDLIST.getValue().equals(reqData.getUpdateType())) {
				pd.put("orderidList",reqData.getOrderidList());
				ExceptionUtil.checkNotCollectNotEmpty((reqData.getOrderidList()), "当更新类型："+ UPDATE_TYPE.ORDERIDLIST.getValue() + "订单id列表必传");
			}
			if(UPDATE_TYPE.ORDERNOLIST.getValue().equals(reqData.getUpdateType())) {
				pd.put("ordernoList",reqData.getOrdernoList());
				ExceptionUtil.checkNotCollectNotEmpty(reqData.getOrdernoList(), "当更新类型："+ UPDATE_TYPE.ORDERNOLIST.getValue() + "订单编码列表必传");
			}
			
			// 改变已取件 -> 必须是已支付
			if(ORDER_STATUS.TAKEGOOGSOVER.getValue().equals(reqData.getStatus())) {
				List<PageData> resTemp = orderInfoService.checkOrderStatusByOrderid(reqData.getId(), ORDER_STATUS.WAITPICK.getValue());
				ExceptionUtil.isTrue(resTemp != null && resTemp.size() == 1, "订单状态不是待取件状态,请刷新系统");
			}
			
			orderInfoService.cbGenerateOrder(pd);
			
			rtBean.setCode(APP_RESPONSE_CODE.SUCCESS.getValue());
			rtBean.setMsg("订单状态更改成功");
			return new Gson().toJson(rtBean);
		}catch (RulesCheckedException e) {
			logger.error("订单状态校验异常:" + e.getMessage());
			rtBean.setCode(APP_RESPONSE_CODE.CHECK.getValue());
			rtBean.setMsg("校验异常：" + e.getMessage());
			return new Gson().toJson(rtBean);
		} catch (Exception e) {
			logger.error("订单状态更改非预期异常:" + e.getMessage());
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("非预期异常，请联系IT");
			return new Gson().toJson(rtBean);
		}
	}

	/**
	 * @desc 查询一批次订单的所有行李总数根据行李QR码
	 * @auther zhangjj
	 * @history 2018年2月28日
	 */
	@ResponseBody  
	@RequestMapping(value = "/countBatchBagNumByQR", produces = "application/json;charset=UTF-8")
	public String countBaggageNumByQR(@RequestBody AppRequestBean reqParm) {
		AppResponseBean rtBean = doValidate(reqParm);
		if (!rtBean.getCode().equals(APP_RESPONSE_CODE.SUCCESS.getValue())) {
			return new Gson().toJson(rtBean);
		}
		CountBaggageNumReqData reqData = new Gson().fromJson(
				reqParm.getData().toString(), CountBaggageNumReqData.class);
		try {
			// 校验
			ExceptionUtil.checkNotEmpty(reqData.getBaggageid(), "QR码不能为空");
			ExceptionUtil.checkNotEmpty(reqData.getRoletype(), "角色动作不能为空");
			ExceptionUtil.checkNotEmpty(reqData.getIsfinish(), "角色动作是否完成不能为空");
			ExceptionUtil.checkNotEmpty(reqData.getDestaddress(), "目标地信息不能为空");
			ExceptionUtil.checkNotEmpty(reqData.getDesttype(), "目的地类型不能为空");
			
			Integer countBagNum = orderInfoService.countBaggageNumByQR(reqData);
			
			ExceptionUtil.checkBoolean(countBagNum == null || countBagNum == 0, "已交接");
			
			CountBaggageNumResData countBaggageNumResData = new CountBaggageNumResData();
			countBaggageNumResData.setBaggageNum(countBagNum);

			rtBean.setJsonData(new Gson().toJson(countBaggageNumResData));
			rtBean.setCode(APP_RESPONSE_CODE.SUCCESS.getValue());
			rtBean.setMsg("查询一批次订单的所有行李总数成功");
			return new Gson().toJson(rtBean);
		}catch (RulesCheckedException e) {
		    logger.error("查询一批次订单的所有行李总数校验异常:" + e.getMessage());
            rtBean.setCode(APP_RESPONSE_CODE.CHECK.getValue());
            rtBean.setMsg("校验异常：" + e.getMessage());
            rtBean.setJsonData("");//ios 强转，保持key存在
            return new Gson().toJson(rtBean);
		} catch (Exception e) {
			logger.error("查询一批次订单的所有行李总数非预期异常:" + e.getMessage());
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("非预期异常，请联系IT");
			rtBean.setJsonData("");//ios 强转，保持key存在
			return new Gson().toJson(rtBean);
		}
	}

    /**
     * 
     * @Title: saveSignUrl
     * @Description: 签字地址保存
     * author：tangqm
     * 2018年9月26日
     * @param reqParm
     * @return
     */
	@ResponseBody
	@RequestMapping(value = "/saveSignUrl", produces = "application/json;charset=UTF-8")
	public String saveSignUrl(@RequestBody AppRequestBean reqParm) {
		AppResponseBean rtBean = doValidate(reqParm);
		if (!rtBean.getCode().equals(APP_RESPONSE_CODE.SUCCESS.getValue())) {
			return new Gson().toJson(rtBean);
		}
		AppOrderReqData appOrderReqData = new Gson().fromJson(reqParm.getData()
				.toString(), AppOrderReqData.class);
		try {
			orderInfoService.saveSignUrl(appOrderReqData);
			
		}catch (RulesCheckedException e) {
		    logger.error("签字地址校验异常:" + e.getMessage());
            rtBean.setCode(APP_RESPONSE_CODE.CHECK.getValue());
            rtBean.setMsg("签字地址校验异常：" + e.getMessage());
            return new Gson().toJson(rtBean);
		} catch (Exception e) {
			logger.error("签字保存非预期失败:" + e.getLocalizedMessage());
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("非预期异常，请联系IT");
			return new Gson().toJson(rtBean);
		}
		rtBean.setMsg("签字保存成功");
		return new Gson().toJson(rtBean);
	}	
	
	
	
	/**
	 * 
	 * @Title: findAppOrderJicun
	 * @Description: 查询集散中心寄存件
	 * author：tangqm
	 * 2018年9月26日
	 * @param reqParm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findAppOrderJicun", produces = "application/json;charset=UTF-8")
	public String findAppOrderJicun(@RequestBody AppRequestBean reqParm) {
		
		AppResponseBean rtBean = doValidate(reqParm);
		if (!rtBean.getCode().equals(APP_RESPONSE_CODE.SUCCESS.getValue())) {
			return gson.toJson(rtBean);
		}

		AppOrderJicunReqData appOrderReqData = new Gson().fromJson(reqParm.getData().toString(), AppOrderJicunReqData.class);
		if (appOrderReqData == null) {
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("查询寄存件失败，原因是请求参数转换异常");
			return gson.toJson(rtBean);
		}

		try {
			List<AppOrderJicunResData> appOrderResDataList = orderInfoService.findAppOrderMainJicunList(appOrderReqData);
			
			rtBean.setJsonData(new Gson().toJson(appOrderResDataList));
			rtBean.setCode(APP_RESPONSE_CODE.SUCCESS.getValue());
			rtBean.setMsg("查询订单列表信息成功");
			return gson.toJson(rtBean);
		} catch (RulesCheckedException e) {
		    logger.error("查询订单列表信息校验异常:" + e);
            rtBean.setCode(APP_RESPONSE_CODE.CHECK.getValue());
            rtBean.setMsg("校验异常：" + e.getMessage());
			rtBean.setJsonData("");//ios 强转，保持key存在
            return gson.toJson(rtBean);
		} catch (Exception e) {
			logger.error("查询订单列表信息非预期异常:" + e);
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("非预期异常，请联系IT");
			rtBean.setJsonData("");//ios 强转，保持key存在
			return gson.toJson(rtBean);
		}
	}

	/**
	 * 
	 * @Title: findAppOrderFinish
	 * @Description: 查询集散中心已完成件
	 * author：tangqm
	 * 2018年9月26日
	 * @param reqParm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findAppOrderFinish", produces = "application/json;charset=UTF-8")
	public String findAppOrderFinish(@RequestBody AppRequestBean reqParm) {
		
		AppResponseBean rtBean = doValidate(reqParm);
		if (!rtBean.getCode().equals(APP_RESPONSE_CODE.SUCCESS.getValue())) {
			return gson.toJson(rtBean);
		}
		
		AppOrderFinishReqData appOrderFinishReqData = new Gson().fromJson(reqParm.getData().toString(), AppOrderFinishReqData.class);
		if (appOrderFinishReqData == null) {
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("查询已完成件成功失败，原因是请求参数转换异常");
			return gson.toJson(rtBean);
		}
		
		try {
			List<AppOrderFinishResData> appOrderResDataList = orderInfoService.findAppOrderMainFinishList(appOrderFinishReqData);
			
			rtBean.setJsonData(new Gson().toJson(appOrderResDataList));
			rtBean.setCode(APP_RESPONSE_CODE.SUCCESS.getValue());
			rtBean.setMsg("查询已完成件信息成功");
			return gson.toJson(rtBean);
		} catch (RulesCheckedException e) {
			logger.error("查询已完成件信息校验异常:" + e);
			rtBean.setCode(APP_RESPONSE_CODE.CHECK.getValue());
			rtBean.setMsg("校验异常：" + e.getMessage());
			rtBean.setJsonData("");//ios 强转，保持key存在
			return gson.toJson(rtBean);
		} catch (Exception e) {
			logger.error("查询已完成件信息非预期异常:" + e);
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("非预期异常，请联系IT");
			rtBean.setJsonData("");//ios 强转，保持key存在
			return gson.toJson(rtBean);
		}
	}
	
	/**
	 * 
	 * @Title: findAppOrderAirport
	 * @Description: 查询机场柜台订单列表
	 * author：tangqm
	 * 2018年9月26日
	 * @param reqParm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findAppOrderAirport", produces = "application/json;charset=UTF-8")
	public String findAppOrderAirport(@RequestBody AppRequestBean reqParm) {
		
		AppResponseBean rtBean = doValidate(reqParm);
		if (!rtBean.getCode().equals(APP_RESPONSE_CODE.SUCCESS.getValue())) {
			return gson.toJson(rtBean);
		}

		AppOrderAirportReqData appOrderReqData = new Gson().fromJson(reqParm.getData().toString(), AppOrderAirportReqData.class);
		
		if (appOrderReqData == null) {
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("查询寄存件失败，原因是请求参数转换异常");
			return gson.toJson(rtBean);
		}

		try {
			List<AppOrderAirportResData> appOrderResDataList = null;
			
			if (AIRPORTORDER_TYPE.HOSTING.getValue().equals(appOrderReqData.getOrdertype())) {
				appOrderResDataList = orderInfoService.findAppOrderAirportHosting(appOrderReqData);
				rtBean.setMsg("查询寄存件订单列表信息成功");
			}else if(AIRPORTORDER_TYPE.FINISH.getValue().equals(appOrderReqData.getOrdertype())) {
				appOrderResDataList = orderInfoService.findAppOrderAirportFinish(appOrderReqData);
				rtBean.setMsg("查询已完成订单列表信息成功");
			}else if(AIRPORTORDER_TYPE.TAKING.getValue().equals(appOrderReqData.getOrdertype())) {
				appOrderResDataList = orderInfoService.findAppOrderAirportTake(appOrderReqData);
				rtBean.setMsg("查询待取件订单列表信息成功");
			}else if(AIRPORTORDER_TYPE.WAITPAY.getValue().equals(appOrderReqData.getOrdertype())) {
				appOrderResDataList = orderInfoService.findAppOrderAirportWaitPay(appOrderReqData);
				rtBean.setMsg("查询待支付订单列表信息成功");
			}else{
				appOrderResDataList = orderInfoService.findAppOrderAirportSend(appOrderReqData);
				rtBean.setMsg("查询待派件订单列表信息成功");
			}
			
			rtBean.setCode(APP_RESPONSE_CODE.SUCCESS.getValue());
			rtBean.setJsonData(new Gson().toJson(appOrderResDataList));
			return gson.toJson(rtBean);
		} catch (RulesCheckedException e) {
		    logger.error("查询订单列表信息校验异常:" + e);
            rtBean.setCode(APP_RESPONSE_CODE.CHECK.getValue());
            rtBean.setMsg("校验异常：" + e.getMessage());
			rtBean.setJsonData("");//ios 强转，保持key存在
            return gson.toJson(rtBean);
		} catch (Exception e) {
			logger.error("查询订单列表信息非预期异常:" + e);
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("非预期异常，请联系IT");
			rtBean.setJsonData("");//ios 强转，保持key存在
			return gson.toJson(rtBean);
		}
	}
	
	
	/**
	 * @desc app柜台下单
	 * @auther zhangjj
	 * @date 2018年10月22日
	 */
	@ResponseBody
	@RequestMapping(value = "/saveorder", produces = "application/json;charset=UTF-8")
	public String saveorder(@RequestBody AppRequestBean reqParm) {
		
		AppResponseBean rtBean = doValidate(reqParm);
		if (!rtBean.getCode().equals(APP_RESPONSE_CODE.SUCCESS.getValue())) {
			return gson.toJson(rtBean);
		}

		AppSaveOrderInfoReqData saveOrderInfoReqBean = gson.fromJson(reqParm.getData().toString(), AppSaveOrderInfoReqData.class);
		
		if (saveOrderInfoReqBean == null) {
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("保存订单信息失败，原因是请求参数转换异常");
			return gson.toJson(rtBean);
		}

		try {
			// 基础校验
			
			// 保存订单信息
			String orderno = orderMainService.saveAppOrder(saveOrderInfoReqBean);
			
			AppSaveOrderInfoResData appSaveOrderInfoResBean = new AppSaveOrderInfoResData();
			appSaveOrderInfoResBean.setOrderno(orderno);
			rtBean.setCode(APP_RESPONSE_CODE.SUCCESS.getValue());
			rtBean.setMsg("保存订单信息成功");
			rtBean.setJsonData( new Gson().toJson( appSaveOrderInfoResBean ) );
			return gson.toJson(rtBean);
		} catch (RulesCheckedException e) {
		    logger.error("保存订单信息校验异常:" + e);
            rtBean.setCode(APP_RESPONSE_CODE.CHECK.getValue());
            rtBean.setMsg("校验异常：" + e.getMessage());
            return gson.toJson(rtBean);
		} catch (Exception e) {
			logger.error("保存订单信息非预期异常:" + e);
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("非预期异常，请联系IT");
			return gson.toJson(rtBean);
		}
	}
	
	/**
	 * @desc 计价规则查询
	 * @auther zhangjj
	 * @date 2018年10月22日
	 */
	@ResponseBody
	@RequestMapping(value = "/findPricingRule", produces = "application/json;charset=UTF-8")
	public String findPricingRule(@RequestBody AppRequestBean reqParm) {
		
		AppResponseBean rtBean = doValidate(reqParm);
		if (!rtBean.getCode().equals(APP_RESPONSE_CODE.SUCCESS.getValue())) {
			return gson.toJson(rtBean);
		}

		PricingRuleReqData reqBean = new Gson().fromJson(reqParm.getData().toString(), PricingRuleReqData.class);
		
		if (reqBean == null) {
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("请求参数转换异常");
			return gson.toJson(rtBean);
		}

		try {
			// 基础校验
			ExceptionUtil.checkNotEmpty(reqBean.getCityid(), "城市id不能为空");
			
			PricingRuleResData pricingRuleResData = new PricingRuleResData();

			// 金牌详情
			String goldPriceDetail = priceRuleService.getConfig(reqBean.getCityid(), ConfigCenterKeys.GOLD_PRICE_DETAIL);
 			if(StringUtils.isNotBlank(goldPriceDetail)) {
				pricingRuleResData.setGoldPriceDetail(JSONObject.parseObject(goldPriceDetail, GoldPriceDetail.class));
			}
            // 专车详情
            String specialPriceDetails = priceRuleService.getConfig(reqBean.getCityid(), ConfigCenterKeys.SPECIAL_PRICE_DETAIL);
 			if(StringUtils.isNotBlank(specialPriceDetails)) {
                pricingRuleResData.setSpecialPriceDetail(JSONObject.parseObject(specialPriceDetails, SpecialPriceDetail.class));
            }

			rtBean.setCode(APP_RESPONSE_CODE.SUCCESS.getValue());
			rtBean.setMsg("计价规则查询成功");
			rtBean.setJsonData( new Gson().toJson( pricingRuleResData ) );
			return gson.toJson(rtBean);
		} catch (RulesCheckedException e) {
		    logger.error("计价规则查询,校验异常:" + e);
            rtBean.setCode(APP_RESPONSE_CODE.CHECK.getValue());
            rtBean.setMsg("校验异常：" + e.getMessage());
            return gson.toJson(rtBean);
		} catch (Exception e) {
			logger.error("计价规则查询,非预期异常:" + e);
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("非预期异常，请联系IT");
			return gson.toJson(rtBean);
		}
	}
	
}
