package com.fh.controller.app.picker;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.common.constant_enum.APP_RESPONSE_CODE;
import com.fh.controller.base.BaseController;
import com.fh.entity.app.AppRequestBean;
import com.fh.entity.app.AppResponseBean;
import com.fh.entity.app.AppUserIdBean;
import com.fh.entity.app.dm.AppOrderUserBean;
import com.fh.entity.app.dm.AppOrders;
import com.fh.entity.app.order.AppOrderReqData;
import com.fh.entity.app.userdelivery.UserDeliveryReqData;
import com.fh.entity.delivery.TaskMainDriver;
import com.fh.entity.order.OrderMainSpec;
import com.fh.service.delivery.ServiceCenterService;
import com.fh.service.delivery.TaskMainDriverService;
import com.fh.service.delivery.UserDeliveryManService;
import com.fh.service.delivery.UserPickerInfoService;
import com.fh.service.order.orderinfo.OrderInfoService;
import com.fh.service.order.orderinfo.OrderMainService;
import com.fh.util.ExceptionUtil;
import com.fh.util.RulesCheckedException;
import com.google.gson.Gson;
/**
 * 
 * 项目名称：jpoa   
 *
 * 类描述：app端取派员接口part.1(订单列表在AppOrderController包里,part.2见AppUserDeliveryControl)
 * 类名称：com.fh.controller.app.picker.AppUserDMController     
 * 创建人：tangqm
 * 创建时间：2018年9月26日 下午3:05:02   
 * 修改人：
 * 修改时间：2018年9月26日 下午3:05:02   
 * 修改备注：
 */
@Controller
@RequestMapping(value = "appdm")
public class AppUserDMController extends BaseController {
	@Autowired
	private TaskMainDriverService taskMainDriverService;
	@Autowired
	private OrderMainService orderMainService;
	@Autowired
	private UserPickerInfoService userPickerInfoService;
	@Autowired
	private UserDeliveryManService userDeliveryManService;
	@Autowired
	private OrderInfoService orderInfoService;
	@Autowired
	private ServiceCenterService serviceCenterService;

	/**
	 * 取派员确认派件接口 陈玉石 tangqm 
	 */
	@ResponseBody
	@RequestMapping(value = "/confirmSentOrders", produces = "application/json;charset=UTF-8")
	public String confirmSentOrders(@RequestBody AppRequestBean reqParm) {
		AppResponseBean rtBean = doValidate(reqParm);
		String data = reqParm.getData();
		Gson gson = new Gson();
		if (!rtBean.getCode().equals(APP_RESPONSE_CODE.SUCCESS.getValue())) {
			return new Gson().toJson(rtBean);
		}
		AppOrderUserBean idBean = (AppOrderUserBean) gson.fromJson(data,
				AppOrderUserBean.class);

		if (idBean == null) {
			rtBean.setCode("1");
			rtBean.setMsg("订单编号和用户编号未获取到");
			return new Gson().toJson(rtBean);
		}
		try {
			orderMainService.confirmSentOrders(idBean.getOrderid(),idBean.getRoleid());
			rtBean.setMsg("确认派件成功");
		} catch (Exception e) {
			logger.error("操作异常:" + e.getLocalizedMessage());
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("非预期异常，请联系IT");
		} 
		return new Gson().toJson(rtBean);
	}

	/**
	 * 取派员确认取件
	 * 取件接口 陈玉石 2018年3月3日14:56:59
	 */
	@ResponseBody
	@RequestMapping(value = "/confirmTakenOrders", produces = "application/json;charset=UTF-8")
	public String confirmTakenOrders(@RequestBody AppRequestBean reqParm) {
		AppResponseBean rtBean = doValidate(reqParm);
		if (!rtBean.getCode().equals(APP_RESPONSE_CODE.SUCCESS.getValue())) {
			return new Gson().toJson(rtBean);
		}
		
		AppOrderUserBean idBean = (AppOrderUserBean) new Gson().fromJson(reqParm.getData(), AppOrderUserBean.class);
		if (idBean == null) {
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("订单编号和用户编号未获取到");
			return new Gson().toJson(rtBean);
		}
		
		try {
			ExceptionUtil.checkBoolean(idBean.getOrderid() == null || idBean.getOrderid() == 0, "订单id不能为空");
			ExceptionUtil.checkBoolean(idBean.getRoleid() == null || idBean.getOrderid() == 0,  "取派员id不能为空");
			
			orderMainService.cbConfirmTakenOrders(idBean.getOrderid(), idBean.getRoleid());
			
			rtBean.setCode(APP_RESPONSE_CODE.SUCCESS.getValue());
			rtBean.setMsg("确认取件成功");
		} catch (RulesCheckedException e) {
		    logger.error("确认取件校验异常:" + e.getMessage());
            rtBean.setCode(APP_RESPONSE_CODE.CHECK.getValue());
            rtBean.setMsg("校验异常：" + e.getMessage());
		} catch (Exception e) {
			logger.error("确认取件非预期异常:" + e.getLocalizedMessage());
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("非预期异常，请联系IT");
		}
		return new Gson().toJson(rtBean);
	}

	/**
	 * 获取行李取派员的任务列表 首先去订单表中获取近一个月的订单数据 然后去班车任务表中获取近一个月的任务数据
	 * 
	 * 入参只需要是userid 返回参数根据定义 陈玉石 2018年3月3日14:56:59
	 */
	@ResponseBody
	@RequestMapping(value = "/getPickerTaskList", produces = "application/json;charset=UTF-8")
	public String getPickerTaskList(@RequestBody AppRequestBean reqParm) {
		AppResponseBean rtBean = doValidate(reqParm);
		String data = reqParm.getData();
		Gson gson = new Gson();
		AppUserIdBean userIdBean = (AppUserIdBean) gson.fromJson(data,
				AppUserIdBean.class);
		if (userIdBean == null) {
			rtBean.setCode("1");
			rtBean.setMsg("未获取到用户编号");
			return new Gson().toJson(rtBean);
		}
		try {
			List<OrderMainSpec> orderList = orderMainService
					.getOrderListByUserId(userIdBean.getUserid());
			List<TaskMainDriver> taskList = taskMainDriverService
					.queryTasksByUserId(userIdBean.getUserid());
			AppOrders orders = orderMainService.mergeTaskAndOrders(orderList,
					taskList);
			if (orders != null) {
				String json = gson.toJson(orders);
				rtBean.setJsonData(json);
			}
		} catch (Exception ex) {
			rtBean.setCode("1");
			rtBean.setMsg("未获取到用户编号");
		}

		return new Gson().toJson(rtBean);
	}

	/**
	 * @desc 确认发车
	 * @auther  tangqm
	 * @date 2018年3月19日
	 */
	@ResponseBody
	@RequestMapping(value = "/confirmDriverStarted", produces = "application/json;charset=UTF-8")
	public String confirmDriverStarted(@RequestBody AppRequestBean reqParm) {
		AppResponseBean rtBean = doValidate(reqParm);
		if (!rtBean.getCode().equals(APP_RESPONSE_CODE.SUCCESS.getValue())) {
			return new Gson().toJson(rtBean);
		}
		AppOrderReqData rqData =  (AppOrderReqData) gson.fromJson(reqParm.getData(),
				AppOrderReqData.class);
		if (rqData==null) {
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("确认发车失败, 原因是请求参数转换异常");
			return new Gson().toJson(rtBean);
		}
    	   try {
    		   ExceptionUtil.checkBoolean(CollectionUtils.isEmpty(rqData.getOrderidList()), "订单id不能为空");
    		   ExceptionUtil.checkBoolean(rqData.getRoleid() == null || rqData.getRoleid() == 0, "取派员id不能为空");
    		   ExceptionUtil.checkNotEmpty(rqData.getRoletype(), "动作类型不能为空");
    		   
    		   orderInfoService.updateOrderRoleState(rqData);
    		   
    		   rtBean.setCode(APP_RESPONSE_CODE.SUCCESS.getValue());
    		   rtBean.setMsg("确认发车成功");
    	   } catch (RulesCheckedException e) {
    		   logger.error("确认发车失败校验异常:" + e.getMessage());
    		   rtBean.setCode(APP_RESPONSE_CODE.CHECK.getValue());
    		   rtBean.setMsg("校验异常：" + e.getMessage());
    	   } catch (Exception ex) {
    		   logger.error("确认发车失败非预期异常:" + ex.getLocalizedMessage());
    		   rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
    		   rtBean.setMsg("确认发车失败非预期，请联系IT");
    		   
    	   }
		return new Gson().toJson(rtBean);
		
	}

	/**
	 * @desc 班车即将到达
	 * @auther zhangjj
	 * @date 2018年3月21日
	 */
	@ResponseBody
	@RequestMapping(value = "/confirmDriverTaskArriving", produces = "application/json;charset=UTF-8")
	public String confirmDriverTaskArriving(@RequestBody AppRequestBean reqParm) {
		AppResponseBean rtBean = doValidate(reqParm);
		if (!rtBean.getCode().equals(APP_RESPONSE_CODE.SUCCESS.getValue())) {
			return new Gson().toJson(rtBean);
		}
		
		UserDeliveryReqData reqData = (UserDeliveryReqData) new Gson().fromJson(reqParm.getData(), UserDeliveryReqData.class);
		if (reqData == null) {
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("请求参数转换异常");
			return new Gson().toJson(rtBean);
		}
		
		try {
			ExceptionUtil.checkBoolean(reqData.getRoleid() == null && reqData.getRoleid() == 0, "取派员登录id不能为空");
			ExceptionUtil.checkNotEmpty(reqData.getRoletype(), "角色动作不能为空");
			ExceptionUtil.checkNotEmpty(reqData.getIsfinish(), "角色动作是否完成不能为空");
			ExceptionUtil.checkNotEmpty(reqData.getDesttype(), "目的地类型不能为空");
			ExceptionUtil.checkNotEmpty(reqData.getDestaddress(), "目的地id不能为空");
			
			taskMainDriverService.cbConfirmArriving(reqData);
			
		} catch (RulesCheckedException e) {
		    logger.error("即将到达校验异常:" + e.getMessage());
            rtBean.setCode(APP_RESPONSE_CODE.CHECK.getValue());
            rtBean.setMsg("校验异常：" + e.getMessage());
            return new Gson().toJson(rtBean);
		} catch (Exception ex) {
			logger.error("即将到达非预期异常:" + ex.getLocalizedMessage());
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("非预期异常，请联系IT");
			return new Gson().toJson(rtBean);
		}
		
		rtBean.setMsg("即将到达成功");
		return new Gson().toJson(rtBean);
	}

	/**
	 * @desc 已经到达
	 * @auther zhangjj
	 * @date 2018年3月22日
	 */
	@ResponseBody
	@RequestMapping(value = "/confirmDriverTaskArrived", produces = "application/json;charset=UTF-8")
	public String confirmDriverTaskArrived(@RequestBody AppRequestBean reqParm) {
		AppResponseBean rtBean = doValidate(reqParm);
		if( rtBean.getCode().equals(APP_RESPONSE_CODE.FAIL.getValue()) ) {
			return new Gson().toJson( rtBean );
		}
		
		UserDeliveryReqData reqData = (UserDeliveryReqData) new Gson().fromJson(reqParm.getData(), UserDeliveryReqData.class);
		if (reqData == null) {
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("请求参数转换异常");
			return new Gson().toJson(rtBean);
		}
		try {
			taskMainDriverService.cbConfirmArrived(reqData);
			
			rtBean.setMsg("确认到达成功");
			return new Gson().toJson(rtBean);
		} catch (RulesCheckedException e) {
		    logger.error("确认到达校验异常:" + e.getMessage());
            rtBean.setCode(APP_RESPONSE_CODE.CHECK.getValue());
            rtBean.setMsg("校验异常：" + e.getMessage());
            return new Gson().toJson(rtBean);	
		} catch (Exception ex) {
			logger.error("确认到达达异常:" + ex.getLocalizedMessage());
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("非预期异常，请联系IT");
			return new Gson().toJson(rtBean);
		}
		
	}

	/**
	 * tangqm 预备交接接口
	 * 
	 * @param reqParm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/prepareTransferluggage", produces = "application/json;charset=UTF-8")
	public String prepareTransferluggage(@RequestBody AppRequestBean reqParm) {
		AppResponseBean rtBean = doValidate(reqParm);
		if (!rtBean.getCode().equals(APP_RESPONSE_CODE.SUCCESS.getValue())) {
			return new Gson().toJson(rtBean);
		}
		UserDeliveryReqData userDeliveryReqData = new Gson().fromJson(reqParm
				.getData().toString(), UserDeliveryReqData.class);
		try {
			Integer roleid = userDeliveryReqData.getRoleid();
			userDeliveryManService.updateUserDeliveryStatusById(roleid);

		} catch (Exception e) {
			logger.error("操作异常:" + e.getLocalizedMessage());
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("非预期异常，请联系IT");
			return new Gson().toJson(rtBean);
		}
		rtBean.setMsg("预备交接成功！");
		return new Gson().toJson(rtBean);
	}

}
