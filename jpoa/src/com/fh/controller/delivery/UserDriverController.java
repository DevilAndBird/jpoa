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
import com.fh.service.delivery.UserDriverService;
import com.fh.util.PageData;


/**
 * @author zhangjj
 * 班车司机（暂时未用到）
 */
@Controller
@RequestMapping(value="/userDriver")
public class UserDriverController extends BaseController{

	@Autowired
	private UserDriverService userDriverService;
	@Autowired
	private AreaProvService areaProvService;
	
    /**
     *@desc 班车司机列表
     *@auther zhangjj
     *@history 2018年2月7日
     */
    @RequestMapping(value="/findUserDriverlistPage")
    public ModelAndView userDriverlistPage( Page page ) throws Exception{
        ModelAndView mv = new ModelAndView();
        page.setPd(this.getPageData());
        
        // 省份信息
        List<PageData> provList = areaProvService.findAll();
        if( null != provList && provList.size()>0 ){
            mv.addObject("provList", provList);
        }
        
        List<PageData> userDriverlistPage = userDriverService.findUserDriverlistPage(page);
        if( null != userDriverlistPage && userDriverlistPage.size()>0 ){
            mv.addObject("userDriverlistPage", userDriverlistPage);
        }
        
        mv.addObject("pd", this.getPageData());
        mv.setViewName("jpoa/delivery/userdriver/jsp/userdriver_list");
        
        return mv;
    }
    
    /**
	 * 修改状态
	 * 木桐
	 * 2018年2月5日16:34:42
	 * @throws Exception 
	 */
    @ResponseBody
	@RequestMapping(value="/updateUserPicker")
	public String updateUserPicker() throws Exception{
		PageData pd = this.getPageData();
		userDriverService.updateUserPicker(pd);
		return "1";
	} 
	
	/**
	 * 保存班车司机
	 * 木桐
	 * 2018年2月5日15:23:48
	 */
	@ResponseBody
	@RequestMapping(value="/insertUserDriver")
	public String insertUserDriver(){
		PageData pd = this.getPageData();
		try {
/*			UserDriverInfo userDriverInfo = userDriverService.checkInfo(pd);
			if( null != userDriverInfo ){
				return "2";
			}
*/			userDriverService.insertUserDriver(pd);
		} catch (Exception e) {
			e.printStackTrace();
			return "2";
		}
		return "1";
	}
	
	
	
}
