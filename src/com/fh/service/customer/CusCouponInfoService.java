package com.fh.service.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fh.common.constant_enum.ISUSED_TYPE;
import com.fh.dao.DaoSupport;
import com.fh.entity.customer.CusCouponInfo;
import com.fh.service.auxiliary.coupon.CouponInfoService;
import com.fh.service.order.orderinfo.OrderMainService;
import com.fh.util.ExceptionUtil;
import com.fh.util.LoggerUtil;
import com.fh.util.PageData;

/**
 * 客户优惠券服务类
 * @author chenyushi
 *	2018年3月4日16:25:11
 */
@Service
public class CusCouponInfoService {
	@Autowired
	private DaoSupport dao;
	@Autowired
	private CouponInfoService couponInfoService;
	
	/**
	 * 根据ID获取对象
	 * 陈玉石
	 * 2018年3月4日16:33:54
	 * @param couponId
	 * @return
	 */
	public void chgCusCouponStatus( Integer couponId,Integer isvalid )throws Exception
	{
		PageData pd = new PageData();
		pd.put( "id", couponId );
		pd.put( "isvalid", isvalid );
		dao.update( "CusCouponInfoMapper.chgCusCouponStatus", pd );
	}
	
	
	/**
	 * 根据ID获取对象
	 * 陈玉石
	 * 2018年3月4日16:33:54
	 * @param couponId
	 * @return
	 */
	public CusCouponInfo getById( Integer couponId )throws Exception
	{
		PageData pd = new PageData();
		pd.put( "id", couponId );
		return (CusCouponInfo)dao.findForObject( "CusCouponInfoMapper.getById", pd );
	}
	
	/**
	 * 根据ID获取对象
	 * 陈玉石
	 * 2018年3月4日16:33:54
	 * @param couponId
	 * @return
	 */
	public List<CusCouponInfo> findCusCouponList( Integer cusid )throws Exception {
		return (List<CusCouponInfo>)dao.findForList( "CusCouponInfoMapper.getCusCouponListByCusId", cusid );
	}
	
	/**
	 * @desc 同一种优惠卷不能重复添加
	 * @auther zhangjj
	 * @date 2018年4月10日
	 */
	public List<PageData> checkRepeatCoupon(CusCouponInfo obj) throws Exception{
		PageData pd = new PageData();
		pd.put("cusid", obj.getCusid());
		pd.put("couponid", obj.getCouponid());
		return (List<PageData>) dao.findForList("CusCouponInfoMapper.checkRepeatCoupon", obj);
	}
	
	/**
	 * @desc 保存客户优惠卷信息
	 * @auther zhangjj
	 * @date 2018年4月10日
	 */
	public void insert(CusCouponInfo obj) throws Exception{
		dao.save("CusCouponInfoMapper.insert", obj);
	}
	
	/**
	 * @desc 保存客户优惠卷信息
	 * @auther zhangjj
	 * @date 2018年4月10日
	 */
	public void getCoupon(CusCouponInfo obj) throws Exception{
		
		// 检查优惠卷是否还有卷
		Integer leftnum = (Integer) dao.findForObject("CouponInfoMapper.chackCouponNum", obj.getCouponid());
		ExceptionUtil.checkBoolean(leftnum == 0, "已经没有此优惠卷");
		
		// 优惠卷表减少一个优惠卷
		dao.update("CouponInfoMapper.userCouponCutNum", obj.getCouponid());
		
		// 领取优惠卷
		dao.save("CusCouponInfoMapper.insert", obj);
	}
	
	/**
	 * @desc 根据订单号查询客户使用优惠卷信息
	 * @auther zhangjj
	 * @date 2018年4月10日
	 */
	public CusCouponInfo findCouponinfoByOrderno(String orderno) throws Exception{
		return (CusCouponInfo) dao.findForObject("CusCouponInfoMapper.findCouponinfoByOrderno", orderno);
	}
	
	/**
	 * @desc 使用优惠卷
	 * @auther zhangjj
	 * @date 2018年10月24日
	 */
	public void useCoupon(String orderno) {
		// 优惠卷信息处理
		try {
			LoggerUtil.info("\n支付成功回调时，优惠卷信息处理start,订单号：" + orderno + "\n");
			CusCouponInfo cusCouponInfo = findCouponinfoByOrderno(orderno);
			if(cusCouponInfo != null) {
				synchronized (CusCouponInfoService.class) {
					cusCouponInfo = findCouponinfoByOrderno(orderno);
					if(cusCouponInfo != null) {
						// 给订单下没有使用优惠卷
						dao.update( "CusCouponInfoMapper.useCoupon", orderno);
						couponInfoService.addCouponUseNum(cusCouponInfo.getCouponcode());
					}
					
				}
			}
			LoggerUtil.info("\n支付成功回调时，优惠卷信息处理end\n");
		} catch(Exception e) {
			LoggerUtil.error("支付成功回调时，优惠卷信息处理异常", e);
		}
	}
	
	/**
	 * @desc 回退使用优惠卷
	 * @auther zhangjj
	 * @date 2018年10月24日
	 */
	public void recallUseCoupon(String orderno) {
		// 优惠卷信息处理
		try {
			LoggerUtil.info("\n退款成功回调时，优惠卷信息处理start,订单号：" + orderno + "\n");
			CusCouponInfo cusCouponInfo = findCouponinfoByOrderno(orderno);
			if(cusCouponInfo != null) {
				synchronized (CusCouponInfoService.class) {
					cusCouponInfo = findCouponinfoByOrderno(orderno);
					if(cusCouponInfo != null) {
						// 给订单下没有使用优惠卷
						dao.update( "CusCouponInfoMapper.reCallUseCoupon", orderno);
						couponInfoService.cutCouponUseNum(cusCouponInfo.getCouponcode());
					}
					
				}
			}
			LoggerUtil.info("\n退款成功回调时，优惠卷信息处理end\n");
		} catch(Exception e) {
			LoggerUtil.error("退款成功回调时，优惠卷信息处理异常", e);
		}
	}
}
