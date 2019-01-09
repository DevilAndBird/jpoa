package com.fh.controller.app.userdelivery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.common.constant_enum.APP_RESPONSE_CODE;
import com.fh.common.constant_enum.ISVALID_TYPE;
import com.fh.common.constant_enum.ORDER_STATUS;
import com.fh.common.constant_enum.QUERYCITYNODE_TYPE;
import com.fh.controller.base.BaseController;
import com.fh.entity.app.AppRequestBean;
import com.fh.entity.app.AppResponseBean;
import com.fh.entity.app.counterservice.AppCounterCenter;
import com.fh.entity.app.dm.AppCityNodeReqData;
import com.fh.entity.app.dm.AppCityNodeResData;
import com.fh.entity.app.transitcenter.AppTransitCenter;
import com.fh.entity.app.transitcenter.AppUnloadScanReqData;
import com.fh.entity.app.transitcenter.AppUnloadScanResData;
import com.fh.entity.app.transitcenter.ApploadDonReqData;
import com.fh.entity.app.userdelivery.AppDmanCurrentGpsReqData;
import com.fh.entity.app.userdelivery.UserDeliveryReqData;
import com.fh.entity.app.userdelivery.UserDeliveryResData;
import com.fh.service.app.AppDmanService;
import com.fh.service.app.AppTransitCenterService;
import com.fh.service.autoallot.AutoAllotService;
import com.fh.service.delivery.ServiceCenterService;
import com.fh.service.delivery.TaskMainDriverService;
import com.fh.service.delivery.TransitCenterService;
import com.fh.service.delivery.UserDeliveryManService;
import com.fh.service.order.orderinfo.OrderInfoService;
import com.fh.service.order.orderinfo.OrderMainService;
import com.fh.util.ExceptionUtil;
import com.fh.util.LoggerUtil;
import com.fh.util.PageData;
import com.fh.util.RedisUtil;
import com.fh.util.RulesCheckedException;
import com.google.gson.Gson;
/**
 * 
 * 项目名称：jpoa   
 *
 * 类描述：app端取派员接口part.2(订单列表在AppOrderController包里)
 * 类名称：com.fh.controller.app.userdelivery.AppUserDeliveryControl     
 * 创建人：tangqm
 * 创建时间：2018年9月26日 下午3:41:28   
 * 修改人：
 * 修改时间：2018年9月26日 下午3:41:28   
 * 修改备注：
 */
@Controller
@RequestMapping(value = "userdelivery")
public class AppUserDeliveryControl extends BaseController {

	@Autowired
	private TaskMainDriverService taskMainDriverService;
	@Autowired
	private UserDeliveryManService userDeliveryManService;
	@Autowired
	private OrderMainService orderMainService;
	@Autowired
	private OrderInfoService orderInfoService;
	@Autowired
	private AppTransitCenterService appTransitCenterService;
	@Autowired
	private AppDmanService appDmanService;
	@Autowired
	private TransitCenterService transitCenterService;
	@Autowired
	private ServiceCenterService serviceCenterService;
	@Autowired
	private AutoAllotService autoAllotService;

	/**
	 * @desc 派送员列表查询接口
	 * @auther zhangjj
	 * @date 2018年3月22日
	 */
	@ResponseBody
	@RequestMapping(value = "/getDeliveryManListByParam", produces = "application/json;charset=UTF-8")
	public String getDeliveryByParam(@RequestBody AppRequestBean reqParm) {
		AppResponseBean rtBean = doValidate(reqParm);
		if (!rtBean.getCode().equals(APP_RESPONSE_CODE.SUCCESS.getValue())) {
			return new Gson().toJson(rtBean);
		}

		UserDeliveryReqData reqData = new Gson().fromJson(reqParm.getData().toString(), UserDeliveryReqData.class);
		if (reqData == null) {
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("非预期异常，请联系IT");
			return new Gson().toJson(rtBean);
		}

		try {
			ExceptionUtil.checkNotEmpty(reqData.getDesttype(), "目的地类型不能为空");
			ExceptionUtil.checkNotEmpty(reqData.getDestaddress(), "目的地id不能为空");

			List<UserDeliveryResData> resDataList = taskMainDriverService.findTaskMainDriverlist(reqData);

			rtBean.setMsg(APP_RESPONSE_CODE.SUCCESS.getValue());
			rtBean.setMsg("取派员列表查询成功");
			rtBean.setJsonData(new Gson().toJson(resDataList));
			return new Gson().toJson(rtBean);
		} catch (RulesCheckedException e) {
			logger.error("派送员列表查询校验异常:" + e.getMessage());
			rtBean.setCode(APP_RESPONSE_CODE.CHECK.getValue());
			rtBean.setMsg("校验异常：" + e.getMessage());
			return new Gson().toJson(rtBean);
		} catch (Exception e) {
			logger.error("派送员列表查询非预期异常:" + e.getLocalizedMessage());
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("非预期异常，请联系IT");
			return new Gson().toJson(rtBean);
		}
	}

	/**
	 * @DESC 交接页面预加载
	 * @author zhangjj
	 * @history 2018年03月04日
	 */
	@ResponseBody
	@RequestMapping(value = "/preLoadingScan", produces = "application/json;charset=UTF-8")
	public String preLoadingScan(@RequestBody AppRequestBean reqParm) {
		AppResponseBean rtBean = doValidate(reqParm);
		if (!rtBean.getCode().equals(APP_RESPONSE_CODE.SUCCESS.getValue())) {
			return new Gson().toJson(rtBean);
		}

		AppUnloadScanReqData appUnloadScanReqData = new Gson().fromJson(reqParm
				.getData().toString(), AppUnloadScanReqData.class);
		if (appUnloadScanReqData == null) {
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("非预期异常，请联系IT");
			return new Gson().toJson(rtBean);
		}

		try {
			List<AppUnloadScanResData> resList = orderMainService
					.findAppOrderBaggageid(appUnloadScanReqData);
			// tangqm 利用迭代器去除未抵达集散中心的订单 2018年6月6日 18点28分
			Iterator<AppUnloadScanResData> iterator = resList.iterator();
			while (iterator.hasNext()) {
				AppUnloadScanResData appUnloadScanResData = iterator.next();
				PageData pd = new PageData();
				pd.put("id", appUnloadScanResData.getId());
				pd.put("destaddress", appUnloadScanResData.getDestaddress());
				String isvalidType = appDmanService.findTransitArrived(pd);
				if(ISVALID_TYPE.INVALID.getValue().equals(isvalidType)){		
					iterator.remove();
					continue;
				}
			}
			// 等待对方申请卸车校验
			for (AppUnloadScanResData res : resList) {
				ExceptionUtil.isTrue(ORDER_STATUS.WAITTRUCELOADING.getValue()
						.equals(res.getStatus()), "等待对方申请装车");
			}

			rtBean.setJsonData(new Gson().toJson(resList));
			rtBean.setCode(APP_RESPONSE_CODE.SUCCESS.getValue());
			rtBean.setMsg("查询订单信息根据QR码成功");
			return new Gson().toJson(rtBean);
		} catch (RulesCheckedException e) {
			logger.error("查询订单信息根据QR码校验异常:" + e.getMessage());
			rtBean.setCode(APP_RESPONSE_CODE.CHECK.getValue());
			rtBean.setMsg("校验异常：" + e.getMessage());
			return new Gson().toJson(rtBean);
		} catch (Exception e) {
			logger.error("查询订单信息根据QR码非预期异常:" + e.getLocalizedMessage());
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("非预期异常，请联系IT");
			return new Gson().toJson(rtBean);
		}
	}
	
   /**
    * 
    * @Title: loadingScan
    * @Description: 装车扫描
    * author：tangqm
    * 2018年6月6日
    * @param reqParm
    * @return
    */
	@ResponseBody
	@RequestMapping(value = "/loadingScan", produces = "application/json;charset=UTF-8")
	public String loadingScan(@RequestBody AppRequestBean reqParm) {
		AppResponseBean rtBean = doValidate(reqParm);
		if (!rtBean.getCode().equals(APP_RESPONSE_CODE.SUCCESS.getValue())) {
			return new Gson().toJson(rtBean);
		}

		AppUnloadScanReqData appUnloadScanReqData = new Gson().fromJson(reqParm
				.getData().toString(), AppUnloadScanReqData.class);
		if (appUnloadScanReqData == null) {
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("非预期异常，请联系IT");
			return new Gson().toJson(rtBean);
		}
		try {
			List<AppUnloadScanResData> resList = orderMainService
					.findAppOrderBaggageid(appUnloadScanReqData);
			// 等待对方申请卸车校验
			for (AppUnloadScanResData res : resList) {
				ExceptionUtil.isTrue(ORDER_STATUS.WAITTRUCELOADING.getValue()
						.equals(res.getStatus()), "等待对方申请装车");
			}

			rtBean.setJsonData(new Gson().toJson(resList));
			rtBean.setCode(APP_RESPONSE_CODE.SUCCESS.getValue());
			rtBean.setMsg("查询订单信息根据QR码成功");
			return new Gson().toJson(rtBean);
		} catch (RulesCheckedException e) {
			logger.error("查询订单信息根据QR码校验异常:" + e.getMessage());
			rtBean.setCode(APP_RESPONSE_CODE.CHECK.getValue());
			rtBean.setMsg("校验异常：" + e.getMessage());
			return new Gson().toJson(rtBean);
		} catch (Exception e) {
			logger.error("查询订单信息根据QR码非预期异常:" + e.getLocalizedMessage());
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("非预期异常，请联系IT");
			return new Gson().toJson(rtBean);
		}
	}
		
	

	/**
	 * @DESC 取派员装车完毕接口
	 * @author zhangjj tangqm
	 * @history 2018年03月04日
	 */
	@ResponseBody
	@RequestMapping(value = "/tranistLoadDone", produces = "application/json;charset=UTF-8")
	public String tranistLoadDone(@RequestBody AppRequestBean reqParm) {
		AppResponseBean rtBean = doValidate(reqParm);
		if (!rtBean.getCode().equals(APP_RESPONSE_CODE.SUCCESS.getValue())) {
			return new Gson().toJson(rtBean);
		}

		ApploadDonReqData apploadDonReqData = new Gson().fromJson(reqParm
				.getData().toString(), ApploadDonReqData.class);
		if (apploadDonReqData == null) {
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("非预期异常，请联系IT");
			return new Gson().toJson(rtBean);
		}

		try {
			appDmanService.cbTransitLoadDone(apploadDonReqData);

			rtBean.setCode(APP_RESPONSE_CODE.SUCCESS.getValue());
			rtBean.setMsg("装车完毕");
			return new Gson().toJson(rtBean);
		} catch (RulesCheckedException e) {
			logger.error("装车完毕校验异常:" + e.getMessage());
			rtBean.setCode(APP_RESPONSE_CODE.CHECK.getValue());
			rtBean.setMsg("校验异常：" + e.getMessage());
			return new Gson().toJson(rtBean);
		} catch (Exception e) {
			logger.error("装车完毕非预期异异常:" + e.getLocalizedMessage());
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("非预期异常，请联系IT");
			return new Gson().toJson(rtBean);
		}
	}

	/**
	 * @DESC 柜台装车完成
	 * @author zhangjj tangqm
	 * @history 2018年03月04日
	 */
	@ResponseBody
	@RequestMapping(value = "/serviceCenterLoadDone", produces = "application/json;charset=UTF-8")
	public String serviceCenterLoadDone(@RequestBody AppRequestBean reqParm) {
		AppResponseBean rtBean = doValidate(reqParm);
		if (!rtBean.getCode().equals(APP_RESPONSE_CODE.SUCCESS.getValue())) {
			return new Gson().toJson(rtBean);
		}

		ApploadDonReqData reqData = new Gson().fromJson(reqParm.getData().toString(), ApploadDonReqData.class);
		if (reqData == null) {
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("非预期异常，请联系IT");
			return new Gson().toJson(rtBean);
		}

		try {
			// 校验
			ExceptionUtil.checkBoolean(reqData.getRoleid() == null || reqData.getRoleid() == 0, "取派员登陆id不能为空");
			ExceptionUtil.checkNotCollectNotEmpty(reqData.getOrderidList(), "订单id列表不能为空");
			
			appDmanService.cbServiceCenterLoadDone(reqData);

			rtBean.setCode(APP_RESPONSE_CODE.SUCCESS.getValue());
			rtBean.setMsg("装车完毕");
			return new Gson().toJson(rtBean);
		} catch (RulesCheckedException e) {
			logger.error("柜台装车完成校验异常:" + e.getMessage());
			rtBean.setCode(APP_RESPONSE_CODE.CHECK.getValue());
			rtBean.setMsg("校验异常：" + e.getMessage());
			return new Gson().toJson(rtBean);
		} catch (Exception e) {
			logger.error("柜台装车完成非预期异常:" + e.getLocalizedMessage());
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("非预期异常，请联系IT");
			return new Gson().toJson(rtBean);
		}
	}

	/**
	 * @DESC 取派员位置信息更新
	 * @author zhangjj tangqm
	 * @history 2018年03月04日
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/updateDmanCurrentGps", produces = "application/json;charset=UTF-8")
	public String updateDmanCurrentGps(@RequestBody AppRequestBean reqParm) {
		AppResponseBean rtBean = doValidate(reqParm);
		if (!rtBean.getCode().equals(APP_RESPONSE_CODE.SUCCESS.getValue())) {
			return new Gson().toJson(rtBean);
		}
			
		AppDmanCurrentGpsReqData reqData = new Gson().fromJson(reqParm.getData().toString(),AppDmanCurrentGpsReqData.class);
		if (reqData == null) {
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("非预期异常，请联系IT");
			return new Gson().toJson(rtBean);
		}
		
		try {
			// 校验
			ExceptionUtil.isFalse(reqData.getUserid() == null|| reqData.getUserid()== 0, "uerid不能为空");
			ExceptionUtil.checkNotEmpty(reqData.getCurrentgps(), "位置坐标不能为空");

			String provcityKey = reqData.getSrccityid().toString(); 
			if (RedisUtil.get(provcityKey) == null) {
				Map<String, AppDmanCurrentGpsReqData> paramMap = new HashMap<String, AppDmanCurrentGpsReqData>();					
				paramMap.put(reqData.getUserid().toString(), reqData);
				RedisUtil.set(provcityKey, paramMap);			
			}else {
				LoggerUtil.info("\n取派员更新位置请求参数(updateDmanCurGps_Req):" + reqData.toString());
				Map<String, AppDmanCurrentGpsReqData> udmMap = (Map<String, AppDmanCurrentGpsReqData>) RedisUtil.get(provcityKey);
				LoggerUtil.info("\n取派员位置历史信息(dmanHistoryGps)" + udmMap.toString());
				udmMap.put(reqData.getUserid().toString(), reqData);
				LoggerUtil.info("\n取派员更新后信息(updateDmanGps_Res):" + udmMap.toString());
				RedisUtil.set(provcityKey, udmMap);				
			}		
			
			rtBean.setCode(APP_RESPONSE_CODE.SUCCESS.getValue());
			rtBean.setMsg("更新成功");
			return new Gson().toJson(rtBean);
		} catch (RulesCheckedException e) {
			logger.error("取派员位置信息更新校验异常:" + e.getMessage());
			rtBean.setCode(APP_RESPONSE_CODE.CHECK.getValue());
			rtBean.setMsg("校验异常：" + e.getMessage());
			return new Gson().toJson(rtBean);
		} catch (Exception e) {
			logger.error("取派员位置信息更新非预期异常:" + e.getLocalizedMessage());
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("非预期异常，请联系IT");
			return new Gson().toJson(rtBean);
		}
	}

	/**
	 * @DESC 获取：取派员所在城市的：机场/集散中心
	 * @author zhangjj tangqm
	 * @history 2018年03月04日
	 */
	@ResponseBody
	@RequestMapping(value = "/findCityNodeByUserid", produces = "application/json;charset=UTF-8")
	public String find(@RequestBody AppRequestBean reqParm) {
		AppResponseBean rtBean = doValidate(reqParm);
		if (!rtBean.getCode().equals(APP_RESPONSE_CODE.SUCCESS.getValue())) {
			return new Gson().toJson(rtBean);
		}

		AppCityNodeReqData appCityNodeReqData = new Gson().fromJson(reqParm
				.getData().toString(), AppCityNodeReqData.class);
		if (appCityNodeReqData == null) {
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("非预期异常，请联系IT");
			return new Gson().toJson(rtBean);
		}

		try {
			ExceptionUtil.checkBoolean(appCityNodeReqData.getUserid() == null
					|| appCityNodeReqData.getUserid() == 0, "登陆用户ID不能为空");
			ExceptionUtil.checkNotEmpty(
					appCityNodeReqData.getQueryCityNodeType(), "查询城市节点类型不能为空");

			PageData dman = userDeliveryManService
					.findByUserid(appCityNodeReqData.getUserid());
			String provid = dman.getString("provid");
			String cityid = dman.getString("cityid");

			AppCityNodeResData appCityNodeResData = new AppCityNodeResData();
			// 某个城市集散中心节点
			if (QUERYCITYNODE_TYPE.TRANSITCENTERLIST.getValue().equals(
					appCityNodeReqData.getQueryCityNodeType())) {
				List<PageData> transitList = transitCenterService
						.findByProvidAndCityid(provid, cityid);

				ExceptionUtil.checkNotCollectNotEmpty(transitList,
						"未查询出给城市的集散中心");

				List<AppTransitCenter> appTransitCenterList = new ArrayList<AppTransitCenter>();
				for (PageData pageData : transitList) {
					AppTransitCenter appTransitCenter = new AppTransitCenter();
					appTransitCenter.setId((Integer) pageData.get("id"));
					appTransitCenter.setName((String) pageData
							.getString("name"));

					appTransitCenterList.add(appTransitCenter);
				}

				appCityNodeResData
						.setAppTransitCenterList(appTransitCenterList);
			}

			// 某个城市服务中心节点
			if (QUERYCITYNODE_TYPE.COUNTERCENTERLIST.getValue().equals(
					appCityNodeReqData.getQueryCityNodeType())) {
				List<PageData> counterCenter = serviceCenterService
						.findByProvidAndCityid(provid, cityid);

				ExceptionUtil.checkNotCollectNotEmpty(counterCenter,
						"未查询出给城市的服务中心");

				List<AppCounterCenter> appCounterCenterList = new ArrayList<AppCounterCenter>();
				for (PageData pageData : counterCenter) {
					AppCounterCenter appCounterCenter = new AppCounterCenter();
					appCounterCenter.setId((Integer) pageData.get("id"));
					appCounterCenter.setName((String) pageData
							.getString("servicecentername"));
					appCounterCenterList.add(appCounterCenter);
				}

				appCityNodeResData
						.setAppCounterCenterList(appCounterCenterList);
			}

			rtBean.setJsonData(new Gson().toJson(appCityNodeResData));
			rtBean.setCode(APP_RESPONSE_CODE.SUCCESS.getValue());
			rtBean.setMsg("查询成功");
			return new Gson().toJson(rtBean);
		} catch (RulesCheckedException e) {
			logger.error("城市节点查询校验异常:" + e.getMessage());
			rtBean.setCode(APP_RESPONSE_CODE.CHECK.getValue());
			rtBean.setMsg("校验异常：" + e.getMessage());
			return new Gson().toJson(rtBean);
		} catch (Exception e) {
			logger.error("城市节点查询非预期异常:" + e.getLocalizedMessage());
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("非预期异常，请联系IT");
			return new Gson().toJson(rtBean);
		}
	}
	
}
