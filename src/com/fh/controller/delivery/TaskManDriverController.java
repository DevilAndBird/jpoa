package com.fh.controller.delivery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.area.AreaProvService;
import com.fh.service.delivery.TaskMainDriverService;
import com.fh.service.delivery.TransitCenterService;
import com.fh.service.delivery.UserDeliveryManService;
import com.fh.service.delivery.UserDriverService;
import com.fh.service.delivery.UserPickerInfoService;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;

/**
 * @author zhangjj
 * 司机任务（暂未用到）
 */
@Controller
@RequestMapping(value="/taskmaindriver")
public class TaskManDriverController extends BaseController{

	@Autowired
	private TaskMainDriverService taskMainDriverService;
	@Autowired
	private AreaProvService areaProvService;
	@Autowired
	private UserDriverService userDriverService;
	@Autowired
	private UserPickerInfoService userPickerInfoService;
    @Autowired
    private UserDeliveryManService userDeliveryManService;
    @Autowired
    private TransitCenterService transitCenterService;
	
	/**
	 * 导出
	 * 木桐
	 * 2018年1月22日11:20:04
	 */
	@RequestMapping(value="outExcel")
	public ModelAndView outExcel() throws Exception{
        List<PageData> varOList = taskMainDriverService.findAll(this.getPageData());
		
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("省份");
		titles.add("城市");
		titles.add("班车司机");
		titles.add("手机号码");
		titles.add("出发地址");
		titles.add("到达地址");
		titles.add("要求开始时间");
		titles.add("要求到达时间");
		titles.add("任务类型");
		titles.add("任务状态");
		dataMap.put("titles", titles);
		
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("provDesc"));
			vpd.put("var2", varOList.get(i).getString("cityDesc"));
			vpd.put("var3", varOList.get(i).getString("name"));
			vpd.put("var4", varOList.get(i).getString("mobile"));
			vpd.put("var5", varOList.get(i).getString("srcaddr"));
			vpd.put("var6", varOList.get(i).getString("destaddr"));
			vpd.put("var7", varOList.get(i).get("depdatetime")+"");
			vpd.put("var8", varOList.get(i).get("arrdatetime")+"");
			vpd.put("var9", varOList.get(i).getString("typeDesc"));
			vpd.put("var10",varOList.get(i).getString("statusDesc"));
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		
		ModelAndView mv = new ModelAndView(erv,dataMap);
		mv.addObject("pd", this.getPageData());
			
		return mv;
	}
	
	/**
	 * 修改任务状态
	 * 木桐
	 * 2018年2月5日19:27:31
	 * @throws Exception 
	 */
	@RequestMapping(value="/updateStatus")
	public String updateStatus() throws Exception{
		taskMainDriverService.updateStatus(this.getPageData());
		return "redirect:http:findTaskMainDriverlistPage";
	}
	
	/**
	 * 修改取件员任务状态
	 * tangqm
	 * 2018年2月5日19:27:31
	 * @throws Exception 
	 */
	@RequestMapping(value="/updatePickerStatus")
	public String updatePickerStatus() throws Exception{
		taskMainDriverService.updatePickerStatus(this.getPageData());
		return "redirect:http:findTaskpikerDriverlistPage";
	}
	
	/**
	 * 班车新增任务
	 */
	@ResponseBody
	@RequestMapping(value="/insertTask")
	public void insertTask(HttpServletResponse response){
		PageData pd = this.getPageData();
		String msg = "集散中心之间运输员只需录入一次即可";
		try {
			if(CollectionUtils.isEmpty(taskMainDriverService.findAlikeTransit(pd))){
				taskMainDriverService.saveTaskMainDrive(pd);
				msg = "新增成功!";
			}
			// 用UTF-8转码，而不是用默认的ISO8859 ,避免中文乱码
			response.setHeader("Content-type", "text/html;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(msg);
		} catch (Exception e) {
			logger.error(e);
		}
	}
	/**
	 * 取件员新增任务
	 */
	@ResponseBody
	@RequestMapping(value="/insertPickerTask")
	public String insertPickerTask(){
		PageData pd = this.getPageData();
		try {
			taskMainDriverService.saveTaskPickerDrive(pd);
		} catch (Exception e) {
			e.printStackTrace();
			return "2";
		}
		return "1";
	}
	
	/**
	 * 班车任务列表分页查询
	 * 木桐
	 * 2018年2月5日17:12:21
	 */
	@RequestMapping(value="/findTaskMainDriverlistPage")
	public ModelAndView list( Page page ) throws Exception{
		ModelAndView mv = new ModelAndView();
		PageData pd = this.getPageData();
		page.setPd(pd);
		
	    // 省份信息
        List<PageData> provList = areaProvService.findAll();
        if(CollectionUtils.isNotEmpty(provList)){
            mv.addObject("provList", provList);
        }
		
        // 司机角色放开
//        List<PageData> driverList = userDriverService.findAll();
//        if(CollectionUtils.isNotEmpty(driverList)){
//            mv.addObject("driverList", driverList);
//        }
        
        // 取派员
        List<PageData> userDeliveryManList = userDeliveryManService.findAll(pd);
        if (CollectionUtils.isNotEmpty(userDeliveryManList)) {
            mv.addObject("userDeliveryManList", userDeliveryManList);
        }
        
        // 集散中心
        List<PageData> transitCenterList = transitCenterService.findAll(pd);
        if (CollectionUtils.isNotEmpty(transitCenterList)) {
            mv.addObject("transitCenterList", transitCenterList);
        }
		
        //取派员
		List<PageData> taskMainDriverlistPage = taskMainDriverService.findTaskMainDriverlistPage(page);
		 if (CollectionUtils.isNotEmpty(taskMainDriverlistPage)) {
			mv.addObject("taskMainDriverlistPage", taskMainDriverlistPage);
		}
		
		mv.addObject("pd", this.getPageData());
		mv.setViewName("jpoa/delivery/taskmaindriver/jsp/taskmaindriver_list");
		/*mv.setViewName("delivery/drive/taskmaindriver_list");*/
		return mv;
	}
	
	/**
	 * 取件员列表分页查询
	 * tangqm
	 * 2018年2月5日17:12:21
	 */
	@RequestMapping(value="/findTaskpikerDriverlistPage")
	public ModelAndView findTaskpikerDriverlistPage( Page page ) throws Exception{
		ModelAndView mv = new ModelAndView();
		page.setPd(this.getPageData());
		
	    // 省份信息
        List<PageData> provList = areaProvService.findAll();
        if(CollectionUtils.isNotEmpty(provList)){
            mv.addObject("provList", provList);
        }
		
        // 取件员列表
        List<PageData> driverList = userPickerInfoService.findUserPickerlistPage(page);
        if(CollectionUtils.isNotEmpty(driverList)){
            mv.addObject("driverList", driverList);
        }
           
		
		List<PageData> taskMainDriverlistPage = taskMainDriverService.findTaskPickerDriverlistPage(page);
		if( null != taskMainDriverlistPage && taskMainDriverlistPage.size()>0 ){
			mv.addObject("taskMainDriverlistPage", taskMainDriverlistPage);
		}
		
		mv.addObject("pd", this.getPageData());
		mv.setViewName("jpoa/delivery/taskpickerdriver/jsp/taskmaindriver_list");
		return mv;
	}
	
}
