package com.fh.controller.app.counterservice;

import com.fh.common.constant_enum.APP_RESPONSE_CODE;
import com.fh.controller.base.BaseController;
import com.fh.entity.app.AppRequestBean;
import com.fh.entity.app.AppResponseBean;
import com.fh.entity.app.counterservice.AppAffirmConnectReqData;
import com.fh.entity.app.order.AppCountersReqData;
import com.fh.entity.app.order.AppCountersResData;
import com.fh.service.app.AppAffirmConnectService;
import com.fh.service.delivery.ServiceCenterService;
import com.fh.util.ExceptionUtil;
import com.fh.util.PageData;
import com.fh.util.RulesCheckedException;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
/**
 * app柜台确认接口
 * @author zjj
 * @date 2018年11月5日
 */
/**
 * 
 * 项目名称：jpoa   
 *
 * 类描述：app柜台控制类
 * 类名称：com.fh.controller.app.counterservice.AppCounterServiceController     
 * 创建人：tangqm
 * 创建时间：2018年9月26日 下午2:59:59   
 * 修改人：
 * 修改时间：2018年9月26日 下午2:59:59   
 * 修改备注：
 */
@Controller
@RequestMapping(value="/appCounterService")
public class AppCounterServiceController extends BaseController
{
	@Autowired
	private AppAffirmConnectService appAffirmConnectService;
	@Autowired
	private ServiceCenterService serviceCenterService;
	
	/**
	 * @DESC 机场人员交接:确认交接
	 * @author zhangjj
	 * @history 2018年03月04日
	 */
	@ResponseBody
    @RequestMapping(value="/affirmConnect", produces = "application/json;charset=UTF-8" )
    public String affirmConnect( @RequestBody AppRequestBean reqParm ){
        AppResponseBean rtBean = doValidate( reqParm );
        if (!rtBean.getCode().equals(APP_RESPONSE_CODE.SUCCESS.getValue())) {
            return new Gson().toJson( rtBean );
        }
        
        AppAffirmConnectReqData reqData = new Gson().fromJson( reqParm.getData().toString(), AppAffirmConnectReqData.class );
        if(reqData == null) {
            rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
            rtBean.setMsg( "非预期异常，请联系IT" );
            return new Gson().toJson( rtBean );
        }
        
        try {
        	 // 校验
        	 ExceptionUtil.checkNotCollectNotEmpty(reqData.getOrderidList(), "订单列表不能为空");
        	 ExceptionUtil.checkBoolean(reqData.getUnloadBeforeRoleid() == null || reqData.getUnloadBeforeRoleid() == 0, "取派员id不能为空");
        	 ExceptionUtil.checkBoolean(reqData.getUnloadLaterRoleid()== null || reqData.getUnloadLaterRoleid() == 0, "服务中心人员id不能为空");
        	 ExceptionUtil.checkNotEmpty(reqData.getDestaddress(), "柜台服务中心编码");
        	
             appAffirmConnectService.cbAffirmConnect(reqData);
             
             rtBean.setCode(APP_RESPONSE_CODE.SUCCESS.getValue());
             rtBean.setMsg( "交接成功" );
             return new Gson().toJson( rtBean );
        } catch (RulesCheckedException e) {
		    logger.error("机场人员交接:确认交接校验异常:" + e.getMessage());
            rtBean.setCode(APP_RESPONSE_CODE.CHECK.getValue());
            rtBean.setMsg("校验异常：" + e.getMessage());
            return new Gson().toJson(rtBean);
        } catch (Exception e) {
            logger.error( "机场人员交接:确认交接非预期异常:"+ e.getLocalizedMessage() );
            rtBean.setCode( APP_RESPONSE_CODE.FAIL.getValue());
            rtBean.setMsg( "非预期异常，请联系IT" );
            return new Gson().toJson( rtBean );
        }
    }
	
	/**
	 * @desc 城市机场柜台查询
	 * @auther zhangjj
	 * @date 2018年10月22日
	 */
	@ResponseBody
    @RequestMapping(value="/findCountersByCity", produces = "application/json;charset=UTF-8" )
    public String findCountersByCity( @RequestBody AppRequestBean reqParm ){
        AppResponseBean rtBean = doValidate( reqParm );
        if (!rtBean.getCode().equals(APP_RESPONSE_CODE.SUCCESS.getValue())) {
            return new Gson().toJson( rtBean );
        }
        
        AppCountersReqData reqData = new Gson().fromJson( reqParm.getData().toString(), AppCountersReqData.class);
        if(reqData == null) {
            rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
            rtBean.setMsg( "请求参数转换异常" );
            return new Gson().toJson( rtBean );
        }
        
        try {
        	 // 校验
            ExceptionUtil.checkNotEmpty( reqData.getProvid(), "省份id不能为空");
            ExceptionUtil.checkNotEmpty( reqData.getCityid(), "市级id不能为空");
			
			 List<PageData> counterList = serviceCenterService.findCounterAll(reqData.getProvid(), reqData.getCityid(), "TASK");
			
		  	 AppCountersResData appCountersResData = new AppCountersResData();
			 appCountersResData.setCounterList(counterList);
			 
             rtBean.setCode(APP_RESPONSE_CODE.SUCCESS.getValue());
             rtBean.setMsg( "城市机场柜台查询成功" );
             rtBean.setJsonData(new Gson().toJson( appCountersResData ));
             return new Gson().toJson( rtBean );
        } catch (RulesCheckedException e) {
		    logger.error("城市机场柜台查询，校验异常:" + e.getMessage());
            rtBean.setCode(APP_RESPONSE_CODE.CHECK.getValue());
            rtBean.setMsg("校验异常：" + e.getMessage());
            return new Gson().toJson(rtBean);
        } catch (Exception e) {
            logger.error( "城市机场柜台查询，非预期异常:"+ e.getLocalizedMessage() );
            rtBean.setCode( APP_RESPONSE_CODE.FAIL.getValue());
            rtBean.setMsg( "非预期异常，请联系IT" );
            return new Gson().toJson( rtBean );
        }
    }
}
