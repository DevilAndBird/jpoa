package com.fh.controller.system.sysitem;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.druid.util.StringUtils;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.SysItem;
import com.fh.service.system.sysitem.SysItemService;
import com.fh.util.Const;
import com.fh.util.PageData;

@Controller
@RequestMapping(value="/sysitem")
public class SysItemController extends BaseController{

	@Resource(name="sysItemService")
	private SysItemService sysItemService;
	/**
	 * 显示数据项列表
	 * @param model
	 * @return
	 */
	@RequestMapping("list")
	public ModelAndView list(Page page)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		String itemcode = pd.getString("itemcode");
		
		if(StringUtils.isEmpty( itemcode )){
			pd.put("itemcode", itemcode);
		}
		
		page.setPd(pd);
		List<SysItem>	itemList = sysItemService.listAllItems(page);			//列出用户列表
		
		mv.setViewName("system/sysitem/sysitem_list");
		mv.addObject("itemList", itemList);
		mv.addObject("pd", pd);
		mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
		
		return mv;
	}
	
	/* ===============================权限================================== */
	@SuppressWarnings("all")
	public Map<String, String> getHC(){
		Subject currentUser = SecurityUtils.getSubject();  //shiro管理的session
		Session session = currentUser.getSession();
		return (Map<String, String>)session.getAttribute(Const.SESSION_QX);
	}
	/* ===============================权限================================== */
}
