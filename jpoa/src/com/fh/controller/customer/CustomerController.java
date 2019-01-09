package com.fh.controller.customer;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.customer.CusInfoService;
import com.fh.util.PageData;

/**
 * @author zhangjj
 * 客户基本信息维护
 */
@Controller
@RequestMapping(value = "/customer")
public class CustomerController extends BaseController {

    @Autowired
    private CusInfoService cusInfoService;
    
    /**
     *@desc:查询客户列表
     *@author:zhangjj
     *@history:2018年1月18日 
     */
    @RequestMapping(value = "/cusInfoList")
    public ModelAndView cusInfoList(Page page) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("customer/cusInfo/cusInfo_list");
        PageData pd = this.getPageData();
        page.setPd(pd);

        try {
            List<PageData> cusChargeList = cusInfoService.cusInfoList(page);
            if (null != cusChargeList && cusChargeList.size() > 0) {
                mv.addObject("cusInfoList", cusChargeList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        mv.addObject("pd", pd);
        
        return mv;
    }
    
    /**	
     * @desc:更新客户是否已经失效
     * @author: zhangjj
     * @history: 2018年1月17日
     */
    @ResponseBody
    @RequestMapping(value = "/updateCusInfoIsValid")
    public String updateCusInfoIsValid() {
        return cusInfoService.updateCusInfo(this.getPageData());
    }
    
    
    /**
     *@desc:根据客户号查询客户充值记录
     *@author:zhangjj
     *@history:2018年1月18日 
     */
    @RequestMapping(value = "/cusChargeList")
    public ModelAndView cusChargeList(Page page) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("customer/cuscharge/charge_list");
        PageData pd = this.getPageData();
        page.setPd(pd);

        try {
            List<PageData> cusChargeList = cusInfoService.cusChargeList(page);
            if (null != cusChargeList && cusChargeList.size() > 0) {
                mv.addObject("cusChargeList", cusChargeList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        mv.addObject("pd", pd);
        return mv;
    }
    
    /**
     *@desc:根据客户号查询优惠卷记录
     *@author:zhangjj
     *@history:2018年1月18日 
     */
    @RequestMapping(value = "/cusCouponList")
    public ModelAndView cusCouponList(Page page) throws Exception  {
        ModelAndView mv = new ModelAndView();
        PageData pd = this.getPageData();
        page.setPd(pd);

        List<PageData> cusCouponList = cusInfoService.cusCouponlistPage(page);
        if (CollectionUtils.isNotEmpty(cusCouponList)) {
            mv.addObject("cusCouponList", cusCouponList);
        }
        
        mv.addObject("pd", pd);
        mv.setViewName("customer/cusCoupon/cusCoupon_list");
        
        return mv;
    }
    
    /**
     *@desc:根据客户号查询客户订单记录
     *@author:zhangjj
     *@history:2018年1月18日 
     */
    @RequestMapping(value = "/cusOrderList")
    public ModelAndView cusOrderList(Page page) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("customer/cusOrder/cusOrder_list");
        PageData pd = this.getPageData();
        page.setPd(pd);

        try {
            List<PageData> cusOrderList = cusInfoService.cusOrderList(page);
            if (null != cusOrderList && cusOrderList.size() > 0) {
                mv.addObject("cusOrderList", cusOrderList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        mv.addObject("pd", pd);
        return mv;
    }
    

}
