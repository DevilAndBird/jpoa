package com.fh.controller.app.appuser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.common.constant_enum.APPUSER_TYPE;
import com.fh.common.constant_enum.APP_RESPONSE_CODE;
import com.fh.common.constant_enum.ISVALID_TYPE;
import com.fh.controller.base.BaseController;
import com.fh.entity.app.AppRequestBean;
import com.fh.entity.app.AppResponseBean;
import com.fh.entity.app.login.AppUserLoginResData;
import com.fh.entity.app.login.AppUserReqData;
import com.fh.entity.app.login.AppUserRole;
import com.fh.entity.delivery.AppUser;
import com.fh.service.autoallot.AutoAllotService;
import com.fh.service.delivery.AppUserService;
import com.fh.service.delivery.CounterServiceCenter;
import com.fh.service.delivery.ServiceCenterService;
import com.fh.service.delivery.TransitCenterService;
import com.fh.service.delivery.UserDeliveryManService;
import com.fh.util.MD5;
import com.fh.util.PageData;
import com.fh.util.RedisUtil;
import com.google.gson.Gson;
/**
 * 
 * 项目名称：jpoa   
 *
 * 类描述：app用户登录接口
 * 类名称：com.fh.controller.app.appuser.AppUserLoginController     
 * 创建人：tangqm
 * 创建时间：2018年9月26日 下午2:58:38   
 * 修改人：
 * 修改时间：2018年9月26日 下午2:58:38   
 * 修改备注：
 */
@Controller
@RequestMapping(value = "applogin")
public class AppUserLoginController extends BaseController {

	@Autowired
	private AppUserService appUserService;
	@Autowired
	private ServiceCenterService serviceCenterService;
	@Autowired
	private TransitCenterService transitCenterService;
	@Autowired
	private UserDeliveryManService userDeliveryManService;
	@Autowired
	private AutoAllotService autoAllotService;

	/**
	 * app用户登录 陈玉石 2018年2月25日16:56:31 tangqm
	 */
	@ResponseBody
	@RequestMapping(value = "/appUserLogin", produces = "application/json;charset=UTF-8")
	public String appUserLogin(@RequestBody AppRequestBean reqParm) {
		AppResponseBean rtBean = doLoginValidate(reqParm);
		AppUserLoginResData rtData = null;
		if (!rtBean.getCode().equals(APP_RESPONSE_CODE.SUCCESS.getValue())) {
			return new Gson().toJson(rtBean);
		}
		
		AppUserReqData data = new Gson().fromJson(reqParm.getData().toString(), AppUserReqData.class);
				
		AppUser user = null;
		try {
			user = appUserService.getByMobileAndPwd(data.getMobile(), data.getPassword());
					
		if (user == null) {
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("账号或密码错误");
		} else if (user.getIsvalid().equals(ISVALID_TYPE.INVALID.getValue())) {
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("登录失败，该账号已被冻结");
		} else {
			String sign = MD5.md5( "jingpei"+reqParm.getUser()+reqParm.getKey()+reqParm.getTimestamp() );//生成sign
			RedisUtil.set( reqParm.getUser(),sign);
			rtData = new AppUserLoginResData();
			rtData.setName(user.getName());
			rtData.setType(user.getType());
			rtData.setRoleid(user.getId());
			rtData.setMobile(user.getMobile());
			rtData.setSign(sign);
			AppUserRole userRole = getUserRole(user);
			rtData.setAppuserrole(userRole);
		}
		} catch (Exception ex) {
			logger.error("登录异常:" + ex.getLocalizedMessage());
		}
		rtBean.setJsonData(new Gson().toJson(rtData));
		return new Gson().toJson(rtBean);
	}
	
	/**
	 * 
	 * @Title: isLogin
	 * @Description:是否有人登陆
	 * author：tangqm
	 * 2018年6月27日
	 * @param reqParm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/isLogin", produces = "application/json;charset=UTF-8")
	public String isLogin(@RequestBody AppRequestBean reqParm) {
		AppResponseBean rtBean =  new AppResponseBean();
		String user = reqParm.getUser();
		Object object = RedisUtil.get(user);
		rtBean.setCode( APP_RESPONSE_CODE.SUCCESS.getValue() );
		rtBean.setMsg( "已登陆成功!" );
		if(object==null){
			rtBean.setCode( APP_RESPONSE_CODE.FAIL.getValue() );
			rtBean.setMsg( "登陆异常,请联系IT!" );
			return new Gson().toJson(rtBean);
		}
		String sign = (String) object;
		if( !sign.equalsIgnoreCase( reqParm.getSign())){
			rtBean.setCode( APP_RESPONSE_CODE.LOGINERROR.getValue() );
	     	rtBean.setMsg( "此账户已在别处登陆!" );
	 	}
		return new Gson().toJson(rtBean);
	}

	/**
	 * @desc 登陆人员附加信息
	 * @auther zhangjj
	 * @date 2018年10月25日
	 */
	private AppUserRole getUserRole(AppUser user) {
		AppUserRole userRole = new AppUserRole();
		try {
			if (APPUSER_TYPE.SERVICE_CENTER.getValue().equals(user.getType())) {
				CounterServiceCenter counterServiceCenter = serviceCenterService.findByUserid(user.getId());
				userRole.setId(counterServiceCenter.getId());
				userRole.setRoleName(counterServiceCenter.getServicecentername() + counterServiceCenter.getRemark());
				userRole.setGps(counterServiceCenter.getGps());
				userRole.setProvid(counterServiceCenter.getProvid());
				userRole.setProvname(counterServiceCenter.getProvname());
				userRole.setCityid(counterServiceCenter.getCityid());
				userRole.setCityname(counterServiceCenter.getCityname());
			} else if (APPUSER_TYPE.TRANSIT_CENTER.getValue().equals(user.getType())) {
				PageData pd = transitCenterService.findByUserid(user.getId());
				userRole.setId(Integer.parseInt(pd.get("id").toString()));
				userRole.setRoleName(pd.get("name").toString());
				userRole.setProvid(pd.getString("provid"));
				userRole.setCityid(pd.getString("cityid"));
			}else if(APPUSER_TYPE.DELIVERY_MAN.getValue().equals(user.getType())){
				PageData pd = userDeliveryManService.findByUserid(user.getId());
				userRole.setId(Integer.parseInt(pd.get("id").toString()));
				userRole.setRoleName(pd.get("name").toString());
				userRole.setProvid(pd.getString("provid"));
				userRole.setCityid(pd.getString("cityid"));
			}
		} catch (Exception e) {
			logger.error("查询角色失败" + e.getLocalizedMessage());
		}
		return userRole;
	}
	
    
}
