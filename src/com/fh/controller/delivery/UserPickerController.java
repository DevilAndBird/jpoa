package com.fh.controller.delivery;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.area.AreaProvService;
import com.fh.service.base.AirportInfoConfigService;
import com.fh.service.delivery.UserPickerInfoService;
import com.fh.util.PageData;

/**
 * @author zhangjj
 * 取件员（暂时未用到）
 */
@Controller
@RequestMapping(value = "/userPicker")
public class UserPickerController extends BaseController {

    @Autowired
    private UserPickerInfoService userPickerInfoService;
    @Autowired
    private AirportInfoConfigService airportInfoConfigService;
    @Autowired
    private AreaProvService areaProvService;
    

    /**
     * @desc 行李提取员
     * @auther zhangjj
     * @history 2018年2月3日
     */
    @RequestMapping(value = "/userPickerlistPage")
    public ModelAndView UserAirportList(Page page) throws Exception {
        ModelAndView mv = this.getModelAndView();
        page.setPd(this.getPageData());

        // 省份信息
        List<PageData> provList = areaProvService.findAll();
        if( null != provList && provList.size()>0 ){
            mv.addObject("provList", provList);
        }
        
        // 机场列表
        List<PageData> airprotInfoConfigList =  airportInfoConfigService.loadAirportInfoConfig();
        if (airprotInfoConfigList != null && airprotInfoConfigList.size() > 0) {
            mv.addObject("airprotInfoConfigList", airprotInfoConfigList);
        }
        
        List<PageData> userPickerlistPage = userPickerInfoService.findUserPickerlistPage(page);
        if (userPickerlistPage != null && userPickerlistPage.size() > 0) {
            mv.addObject("userPickerlistPage", userPickerlistPage);
        }

        mv.addObject("pd", this.getPageData());
        mv.setViewName("jpoa/delivery/userpicker/jsp/userpicker_list");

        return mv;
    }
    
    /**
     *@desc 根据id更新提取员信息
     *@auther zhangjj
     *@history 2018年2月4日
     */
    @ResponseBody
    @RequestMapping(value="/updateUserPicker")
    public String updateUserPickerInfoById() throws Exception{
        userPickerInfoService.updateUserPickerInfoById(this.getPageData());
        return "1";
    }
    
    /**
     *@desc 增加机场取件员
     *@auther zhangjj
     *@history 2018年2月9日
     */
    @ResponseBody
    @RequestMapping(value="/savePicker")
    public String savePicker(){
        try {
            userPickerInfoService.savePicker(this.getPageData());
        } catch (Exception e) {
            e.printStackTrace();
            return "2";
        }
        return "1";
    }
    

}
