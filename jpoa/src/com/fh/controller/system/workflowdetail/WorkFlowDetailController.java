package com.fh.controller.system.workflowdetail;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.SysPost;
import com.fh.entity.system.WorkFlowDetail;
import com.fh.service.system.syspost.SysPostService;
import com.fh.service.system.wfd.WorkFlowDetailService;
import com.fh.util.PageData;

/**
 * 
 *@Title:WFDController.java
 *@Package:com.fh.controller.system.wfd
 *@Description:这是用于设计流程具体步骤的增删改的
 *@author:唐启铭
 *@date:2016年7月7日 上午00:03:02
 */
@Controller
@RequestMapping(value = "/wfd")
public class WorkFlowDetailController extends BaseController {

	@Resource(name = "workFlowDetailService")
	private WorkFlowDetailService workflowdetailService;
	@Resource(name = "sysPostService")
	private SysPostService syspostservice;

	/*
	 * 保存新增
	 */
	@SuppressWarnings("all")
	@RequestMapping(value = "/save")
	public ModelAndView save(@RequestParam("stepname") String stepname,
			@RequestParam("postid") String postid,
			@RequestParam("orders") String orders,
			@RequestParam("wfiid") String wfiid) throws Exception {
		ModelAndView mv = this.getModelAndView();
		WorkFlowDetail wfi = new WorkFlowDetail();
		PageData pd = new PageData();
		pd = this.getPageData();
		SysPost sp = syspostservice.listOneSP(Integer.parseInt(pd
				.getString("postid")));
		pd.put("postname", sp.getPostname());
		pd.put("wfiid", Integer.parseInt(pd.getString("wfiid")));
		pd.put("orders", Integer.parseInt(pd.getString("orders")));
		workflowdetailService.save(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/*
	 * 跳转修改页面获取具体的值
	 */
	@RequestMapping(value = "/goupdate")
	public ModelAndView goupdate(@RequestParam("id") String id) throws Exception {
		ModelAndView mv = this.getModelAndView();
		WorkFlowDetail wfd = workflowdetailService.findObject(Integer.parseInt(id));
		mv.setViewName("system/wfd/wfd_edit");
		mv.addObject("wfd", wfd);
		return mv;
	}
	
	/*
	 * 删除
	 */
	@RequestMapping(value = "/deletewfd")
	public ModelAndView deletewfd(
			@RequestParam("id") String id,
			@RequestParam("wfiid")String wfiid
			) throws Exception {
		ModelAndView mv = this.getModelAndView();
		workflowdetailService.delete(Integer.parseInt(id));
		WorkFlowDetail wfd = new WorkFlowDetail();
		wfd.setWfiid(Integer.parseInt(wfiid));
		wfd.setId(Integer.parseInt(id));
		List<WorkFlowDetail>list=workflowdetailService.ordersList(wfd);
		for (WorkFlowDetail wfdlist : list) {
			WorkFlowDetail wfdtemp = new WorkFlowDetail();
			wfdtemp.setOrders(wfdlist.getOrders()-1);
			wfdtemp.setStepname("第"+(wfdlist.getOrders()-1)+"步");
			wfdtemp.setId(wfdlist.getId());
			workflowdetailService.updateOrders(wfdtemp);
		}
		mv.setViewName("redirect:/wfd/wfdlistPage");
		return mv;
	}

	/*
	 * 分页
	 */
	@RequestMapping(value = "/wfdlistPage")
	public ModelAndView wfdlistPage(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Integer wfiid = Integer.parseInt(pd.getString("wfiid"));
		pd.put("wfiid", wfiid);
		page.setPd(pd);
		List<PageData> userList = workflowdetailService.wfdlistPage(page);
		mv.setViewName("system/wfd/wfd_list");
		mv.addObject("userList", userList);
		mv.addObject("wfiid", wfiid);
		mv.addObject("pd", pd);
		return mv;
	}
	
    /*
     * 跳转到生成页面
     */
	@RequestMapping(value = "/gocreatePage")
	public ModelAndView gocreatePage(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String sheetname = pd.getString("sheetname");
		if (null != sheetname && !"".equals(sheetname)) {
			sheetname = sheetname.trim();
			pd.put("sheetname", sheetname);
		}
		String typename = pd.getString("typename");
		if (null != typename && !"".equals(typename)) {
			typename = typename.trim();
			pd.put("typename", typename);
		}
		page.setPd(pd);
		mv.setViewName("tablesystem/create_list");
		mv.addObject("pd", pd);
		return mv;
	}

	/*
	 * 跳到保存页面
	 */
	@RequestMapping(value = "/gosave")
	public ModelAndView gosave(@RequestParam("wfiid") String wfiid)
			throws Exception {
		ModelAndView mv = this.getModelAndView();
		WorkFlowDetail wfdList = workflowdetailService.getNextOrders(Integer.parseInt(wfiid));
		if(wfdList.getPostname().equals("总经理")){
			mv.addObject("msg", "已经是最高级别了");
		}
		mv.addObject("orders", wfdList.getOrders());
		mv.addObject("stepname", wfdList.getStepname());
		mv.setViewName("system/wfd/wfd_add");
		mv.addObject("wfiid", wfiid);
		return mv;
	}

	/*
	 * 保存修改的方法
	 */
	@RequestMapping(value = "/updatewfd")
	public ModelAndView updatewfd(@RequestParam("id") String id,
			@RequestParam("stepname") String stepname,
			@RequestParam("postid") String postid,
			@RequestParam("orders") String orders) throws Exception {
		ModelAndView mv = this.getModelAndView();
		WorkFlowDetail wfd = new WorkFlowDetail();
		SysPost sp = syspostservice.listOneSP(Integer.parseInt(postid));
		wfd.setId(Integer.parseInt(id));
		wfd.setOrders(Integer.parseInt(orders));
		wfd.setPostname(sp.getPostname());
		wfd.setPostid(Integer.parseInt(postid));
		wfd.setStepname(stepname);
		workflowdetailService.update(wfd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

}
