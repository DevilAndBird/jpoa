package com.fh.controller.system.workflowsheet;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.Sheet_info;
import com.fh.entity.system.WorkFlowInfo;
import com.fh.entity.system.WorkFlowSheet;
import com.fh.service.system.si.SheetInfoService;
import com.fh.service.system.wfi.WorkFlowInfoService;
import com.fh.service.system.workflowsheet.WorkFlowSheetService;
import com.fh.util.PageData;

/**
 * 
 * @Title:WorkFlowSheetController.java
 * @Package:com.fh.controller.system.workflowsheet
 * @Description:这是SheetInfo和WorkFlowInfo的关系表控制层
 * @author:唐启铭
 * @date:2016年7月4日 下午5:34:49
 */
@Controller
@RequestMapping(value = "/wfs")
public class WorkFlowSheetController extends BaseController {

	@Resource(name = "workFlowSheetService")
	private WorkFlowSheetService workflowsheetservice;
	@Resource(name = "sheetInfoService")
	private SheetInfoService sheetInfoService;
	@Resource(name = "workFlowInfoService")
	private WorkFlowInfoService workFlowInfoService;

	/*
	 * 保存新增数据
	 */
	@RequestMapping(value = "/save")
	public ModelAndView save(@RequestParam("flowname") String flowname,
			@RequestParam("sheetname") String sheetname) throws Exception {
		ModelAndView mv = this.getModelAndView();
		WorkFlowSheet wfs = new WorkFlowSheet();
		wfs.setSheetid(Integer.parseInt(sheetname.split("[+]")[1]));
		wfs.setSheetname(sheetname.split("[+]")[0]);
		wfs.setWorkflowid(Integer.parseInt(flowname.split("\\+")[1]));
		wfs.setWorkflowname(flowname.split("\\+")[0]);
		workflowsheetservice.save(wfs);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/*
	 * 分页
	 */
	@RequestMapping(value = "/golistpage")
	public ModelAndView golistpage(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String sheetname = pd.getString("sheetname");
		if (null != sheetname && !"".equals(sheetname)) {
			sheetname = sheetname.trim();
			pd.put("sheetname", sheetname);
		}
		String workflowname = pd.getString("workflowname");
		if (null != workflowname && !"".equals(workflowname)) {
			workflowname = workflowname.trim();
			pd.put("workflowname", workflowname);
		}
		page.setPd(pd);
		List<PageData> list = workflowsheetservice.wfslistPage(page);
		mv.addObject("userList", list);
		mv.setViewName("system/wfs/wfs_list");
		return mv;
	}

	/*
	 * 跳到保存页面
	 */
	@RequestMapping(value = "/gosave")
	public ModelAndView gosave() throws Exception {
		ModelAndView mv = this.getModelAndView();
		List<WorkFlowInfo> wfiList = workFlowInfoService.queryList();
		List<Sheet_info> siList = sheetInfoService.querySheetInfoList();
		mv.addObject("wfiList", wfiList);
		mv.addObject("siList", siList);
		mv.setViewName("system/wfs/wfs_add");
		return mv;
	}

	/*
	 * 跳转到修改页面
	 */
	@RequestMapping(value = "/goupdate")
	public ModelAndView goupdate(@RequestParam("id") String id)
			throws Exception {
		ModelAndView mv = this.getModelAndView();
		WorkFlowSheet wfs = workflowsheetservice.findObject(Integer
				.parseInt(id));
		List<WorkFlowInfo> wfiList = workFlowInfoService.queryList();
		List<Sheet_info> siList = sheetInfoService.querySheetInfoList();
		mv.addObject("wfiList", wfiList);
		mv.addObject("siList", siList);
		mv.addObject("wfs", wfs);
		mv.setViewName("system/wfs/wfs_edit");
		return mv;
	}

	/*
	 * 跳转到删除
	 */
	@RequestMapping(value = "/godelete")
	public ModelAndView delete(@RequestParam("id") String id) throws Exception {
		ModelAndView mv = this.getModelAndView();
		workflowsheetservice.delete(Integer.parseInt(id));
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/*
	 * 保存修改
	 */
	@RequestMapping(value = "/updatewfs")
	public ModelAndView updatewfs(@RequestParam("wfsid") String id,
			@RequestParam("flowname") String flowname,
			@RequestParam("sheetname") String sheetname) throws Exception {
		ModelAndView mv = this.getModelAndView();
		WorkFlowSheet wfs = new WorkFlowSheet();
		wfs.setSheetid(Integer.parseInt(sheetname.split("[+]")[1]));
		wfs.setSheetname(sheetname.split("[+]")[0]);
		wfs.setWorkflowid(Integer.parseInt(flowname.split("\\+")[1]));
		wfs.setWorkflowname(flowname.split("\\+")[0]);
		wfs.setId(Integer.parseInt(id));
		workflowsheetservice.update(wfs);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

}
