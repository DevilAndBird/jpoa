package com.fh.controller.auxiliary.coupon;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.common.constant_enum.COUPON_TYPE;
import com.fh.common.constant_enum.ISVALID_TYPE;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.auxiliary.coupon.CouponInfo;
import com.fh.service.auxiliary.coupon.CouponInfoService;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
/**
 * 优惠券模块
 * @author tqm
 * @date 2018年11月5日
 */
/**
 * @author zhangjj
 * 优惠卷信息处理
 */
@Controller
@RequestMapping(value="/coupon")
public class CouponInfoController extends BaseController{

	@Autowired
	private CouponInfoService couponInfoService;
	
	
	/**
	 * 导出
	 * 木桐
	 * 2018年1月22日11:20:04
	 */
	@RequestMapping(value="outExcel")
	public ModelAndView outExcel(){
		ModelAndView mv = new ModelAndView();
		PageData pd = this.getPageData();
		
		
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("编号");
			titles.add("类型");
			titles.add("生成时间");
			titles.add("开始时间");
			titles.add("有效时间");
			titles.add("目标用户");
			titles.add("总数量");
			titles.add("使用数量");
			titles.add("是否有效");
			dataMap.put("titles", titles);
			
			List<PageData> varOList = couponInfoService.couponList(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				
				DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String addtime = null;
				if( (varOList.get(i).get("addtime")) !=null ){
					addtime = sdf.format((Date)varOList.get(i).get("addtime"));
				}
				
				String startdate = null;
				if( varOList.get(i).get("startdate") !=null ){
					startdate = sdf.format((Date)varOList.get(i).get("startdate"));
				}
				
				String invaliddate = null;
				if( (varOList.get(i).get("invaliddate")) !=null ){
					invaliddate = sdf.format((Date)varOList.get(i).get("invaliddate"));
				}
				
				
				String type = varOList.get(i).getString("type");
				
				String channel = varOList.get(i).getString("channel")+"";
				switch (channel) {
				case "1":
					channel = "手工指定用户优惠券";
					break;
				case "2":
					channel = "新用户优惠券";
					break;
				case "3":
					channel = "微信注册渠道用户优惠券";
					break;
				default:
					channel = "携程优惠券";
					break;
				}
				
				String isValid = varOList.get(i).getString("isvalid");
				
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("code"));
				vpd.put("var2", COUPON_TYPE.DIRECT_REDUCE.getValue().equals(type)? COUPON_TYPE.DIRECT_REDUCE.getName():COUPON_TYPE.FULL_REDUCE.getValue().equals(type)?COUPON_TYPE.FULL_REDUCE.getName():COUPON_TYPE.DISCOUNT.getName());
				vpd.put("var3", addtime);
				vpd.put("var4", startdate);
				vpd.put("var5", invaliddate);
				vpd.put("var6", channel);
				vpd.put("var7", (Integer)varOList.get(i).get("leftnum")+"");
				vpd.put("var8", (Integer)varOList.get(i).get("usenum")+"");
				vpd.put("var9", ISVALID_TYPE.VALID.getValue().equals(isValid)? ISVALID_TYPE.VALID.getName():ISVALID_TYPE.INVALID.getName());
				
				varList.add(vpd);
				
			}
			
			dataMap.put("varList", varList);
			ObjectExcelView erv = new ObjectExcelView();
			mv = new ModelAndView(erv,dataMap);
			mv.addObject("pd", pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
		
	}
	
	/**
	 * 返回首页
	 * 木桐
	 * 2018年1月22日11:07:27
	 */
	@RequestMapping(value="/backPage")
	public String backPage( Page page ){
		return "redirect:http:couponList";
	}
	
	/**
	 * 优惠券生成
	 * 木桐
	 * 2018年1月12日15:33:10
	 */
	@RequestMapping(value="/createCoupon")
	public String createCoupon(){
		PageData pd = this.getPageData();
		try {
			couponInfoService.createCoupon(pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:http:couponList";
	}
	
	/**
	 * 核对优惠劵编码是否存在
	 * 木桐
	 * 2018年1月12日13:56:26
	 */
	@ResponseBody
	@RequestMapping(value="/checkCode")
	public String checkCode(){
		PageData pd = this.getPageData();
		CouponInfo couponInfo = null;
		try {
			couponInfo = couponInfoService.checkCode(pd);
			if( null != couponInfo ){
				return "1";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "1";
		}
		return "2";
	}
	
	/**
	 * go优惠券生成页面
	 * 木桐
	 * 2018年1月11日22:05:50
	 */
	@RequestMapping(value="/goCreate")
	public ModelAndView goCreate(){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("auxiliary/coupon/coupon_create");
		return mv;
	}
	
	/**
	 * 优惠券配置列表查询
	 * 木桐
	 * 2018年1月11日21:19:20
	 */
	@RequestMapping(value="/couponList")
	public ModelAndView couponList( Page page ){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("auxiliary/coupon/coupon_list");
		PageData pd = this.getPageData();
		page.setPd(pd);
		try {
			List<PageData> couponInfoList = couponInfoService.couponInfoList(page);
			if( null != couponInfoList && couponInfoList.size()>0 ){
				mv.addObject("couponInfoList", couponInfoList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		mv.addObject("pd", pd);
		return mv;
	}
}
