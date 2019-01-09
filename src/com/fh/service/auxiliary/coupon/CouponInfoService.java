package com.fh.service.auxiliary.coupon;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.fh.common.constant_enum.COUPON_TYPE;
import com.fh.common.constant_enum.ISVALID_TYPE;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.auxiliary.coupon.CouponInfo;
import com.fh.entity.system.User;
import com.fh.util.Const;
import com.fh.util.ExceptionUtil;
import com.fh.util.PageData;

@Service
public class CouponInfoService {

	@Autowired
	private DaoSupport dao;
	
	
	/**
	 * 优惠券配置列表查询
	 * 木桐
	 * 2018年1月11日21:13:49
	 * @throws Exception 
	 */
	@SuppressWarnings("all")
	public List<PageData> couponList( PageData pd ) throws Exception{
		String code = pd.getString("code");
		if( null != code ){
			pd.put("code", code.replace(" ", ""));
		}
		return (List<PageData>)dao.findForList("CouponInfoMapper.couponList", pd);
		
	}
	
	/**
	 * 优惠券生成
	 * 木桐
	 * 2018年1月12日15:39:52
	 * @throws Exception 
	 */
	public void createCoupon( PageData pd ) throws Exception{
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		User user = (User)session.getAttribute( Const.SESSION_USER );
		if( null != user ){
			pd.put("createdby", user.getUSERNAME());
		}
		pd.put("startdate", pd.getString("startdate")+" "+pd.getString("starttime"));
		pd.put("invaliddate", pd.getString("invaliddate")+" "+pd.getString("invalidtime"));
		
		String fullmoney = pd.getString("fullmoney");
		if( StringUtils.isBlank(fullmoney) ){
			pd.put("fullmoney", null);
		}else{
			pd.put("fullmoney", Integer.parseInt(fullmoney));
		}
		String money = pd.getString("money");
		if( StringUtils.isBlank(money) ){
			pd.put("money", null);
		}else{
			pd.put("money", Integer.parseInt(money));
		}
		String discount = pd.getString("discount");
		if( StringUtils.isBlank(discount) ){
			pd.put("discount", null);
		}else{
			pd.put("discount", Float.parseFloat(discount));
		}
		
		dao.save("CouponInfoMapper.insert", pd);
		
	}
	
	/**
	 * 核对优惠劵编码是否存在
	 * 木桐
	 * 2018年1月12日13:59:42
	 * @throws Exception 
	 */
	public CouponInfo checkCode( PageData pd ) throws Exception{
		return (CouponInfo)dao.findForObject("CouponInfoMapper.checkCode", pd);
	}
	
	/**
	 * 优惠券配置列表查询
	 * 木桐
	 * 2018年1月11日21:13:49
	 * @throws Exception 
	 */
	@SuppressWarnings("all")
	public List<PageData> couponInfoList( Page page ) throws Exception{
		PageData pd = page.getPd();
		String code = pd.getString("code");
		if( null != code ){
			pd.put("code", code.replace(" ", ""));
		}
		page.setPd(pd);
		
		return (List<PageData>)dao.findForList("CouponInfoMapper.couponInfolistPage", page);
	}
	
	/**
	 * @desc 客户使用优惠卷信息
	 * @auther zhangjj
	 * @date 2018年10月23日
	 */
	@SuppressWarnings("all")
	public Float useCoupon(String couponcode, Float totalmoney) throws Exception{
		// 根据优惠码查询
		CouponInfo couponInfo = (CouponInfo) dao.findForObject("CouponInfoMapper.findcouponinfoByCode", couponcode);
		ExceptionUtil.checkNotNull(couponInfo, "此优惠卷已失效！");

		// 校验优惠码优惠码
		// 有效时间是否满足
		Date nowDate = new Date();
		long nowtime = nowDate.getTime();
		Date startdate = couponInfo.getStartdate();
		long starttime = startdate.getTime();
		Date invaliddate = couponInfo.getInvaliddate();
		long invalidtime = invaliddate.getTime();
		ExceptionUtil.checkBoolean(nowtime <= starttime || nowtime >= invalidtime, "此优惠卷未在有效期！");
		// 是否失效
		ExceptionUtil.checkBoolean(StringUtils.isBlank(couponInfo.getIsvalid()) 
				|| (!ISVALID_TYPE.INVALID.getValue().equals(couponInfo.getIsvalid()) && !ISVALID_TYPE.VALID.getValue().equals(couponInfo.getIsvalid())
				|| ISVALID_TYPE.INVALID.getValue().equals(couponInfo.getIsvalid())) , "此优惠卷已失效！");
		
		
		Float discountamount = 0f;
		if(COUPON_TYPE.DIRECT_REDUCE.getValue().equals(couponInfo.getType())) {// 直减
			discountamount = totalmoney - couponInfo.getMoney();
			if(discountamount < 0) {
				discountamount = 0f;
			}
		} else if(COUPON_TYPE.FULL_REDUCE.getValue().equals(couponInfo.getType())) {// 满减
			if(totalmoney >= couponInfo.getFullmoney()) {
				discountamount = totalmoney - couponInfo.getMoney();
			}
		} else if(COUPON_TYPE.DISCOUNT.getValue().equals(couponInfo.getType())) {// 折扣
			discountamount = totalmoney * couponInfo.getDiscount();
		} else {
			
		}
		
		return discountamount;
	}
	
	/**
	 * @desc 增加优惠卷使用数量统计
	 * @auther zhangjj
	 * @date 2018年10月24日
	 */
	public void addCouponUseNum(String orderno) throws Exception{
		 dao.update("CouponInfoMapper.userCouponAddNum", orderno);
	}
	
	/**
	 * @desc 减少优惠卷使用数量统计
	 * @auther zhangjj
	 * @date 2018年10月24日
	 */
	public void cutCouponUseNum(String couponcode) throws Exception{
		 dao.update("CouponInfoMapper.userCouponCutNum", couponcode);
	}
	
}
