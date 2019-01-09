package com.fh.controller.weixin;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.delivery.AppUser;
import com.fh.service.weixin.WXPublicNumCondigService;
import com.fh.util.LoggerUtil;
import com.fh.util.PageData;

@Controller
@RequestMapping(value = "/wxpublicnumconfig")
public class WXPublicNumConfigController extends BaseController {

	@Autowired
	private WXPublicNumCondigService wxPublicNumCondigService;
	
    
	/**
	 * @throws Exception 
	 * @desc 公众号配置信息查询
	 * @auther zhangjj
	 * @date 2018年4月10日
	 */
	@ResponseBody
	@RequestMapping(value = "/publicNumConfiglistPage")
	public ModelAndView publicNumConfiglistPage(Page page) throws Exception { 
		ModelAndView mv = new ModelAndView();
	    PageData pd = this.getPageData();
	    page.setPd(pd);
	    List<PageData> publicNumConfigList = wxPublicNumCondigService.findPublicNumConfiglistPage(page);
	
	    if (CollectionUtils.isNotEmpty(publicNumConfigList)) {
	        mv.addObject("publicNumConfigList", publicNumConfigList);
	    }
	
	    mv.addObject("pd", pd);
	    mv.setViewName("jpoa/winxin/publicnumconfig/jsp/publicnumconfig_list");
	    return mv;
    }
	
	/**
     *@desc 编辑角色登陆信息
     *@auther zhangjj
     *@history 2018年2月9日
     */
    @RequestMapping(value="/updatePubileNumConfig")
    public void updatePubileNumConfig(HttpServletResponse response)throws Exception{
    	// 用UTF-8转码，而不是用默认的ISO8859 ,避免中文乱码
    	response.reset();
    	response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
		PageData pd = this.getPageData();
		
		try {
			wxPublicNumCondigService.updatePubileNumConfig(pd);
		} catch (Exception e) {
			response.getWriter().print("FAIL:更新失败！原因：" + e.getMessage());
		}
    	response.getWriter().print("SUCCESS:更新成功！");
    }
    
    /**
     * @desc 增加appUser
     * @auther zhangjj tangqm
     * @history 2018年2月5日
     */
    @RequestMapping(value = "/savePubileNumConfig")
    public void savePubileNumConfig(HttpServletResponse response)  throws Exception    {
        // 用UTF-8转码，而不是用默认的ISO8859 ,避免中文乱码
    	response.reset();
    	response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
		
		try {
			wxPublicNumCondigService.save(this.getPageData());
			response.getWriter().print("SUCCESS:保存成功！");
		} catch (Exception e) {
			LoggerUtil.error("FAIL:保存失败！", e);
			response.getWriter().print("FAIL:保存失败！原因：" + e.getMessage());
		}
    }
    
    /**
     * @desc 增加appUser
     * @auther zhangjj tangqm
     * @history 2018年2月5日
     */
    @RequestMapping(value = "/refresh_publicnumconfig")
    public void refresh_publicnumconfig(HttpServletRequest request, HttpServletResponse response)  throws Exception    {
        // 用UTF-8转码，而不是用默认的ISO8859 ,避免中文乱码
    	response.reset();
    	response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
		
		try {
			wxPublicNumCondigService.initPublicNumConfigtoMap();
			response.getWriter().print("SUCCESS:刷新成功！");
		} catch (Exception e) {
			response.getWriter().print("FAIL:刷新失败！原因：" + e.getMessage());
		}
    }
    
    /**
     * @desc 增加appUser
     * @auther zhangjj tangqm
     * @history 2018年2月5日
     */
    @RequestMapping(value = "/delete_publicnumconfig")
    public void delete_publicnumconfig(HttpServletRequest request, HttpServletResponse response)  throws Exception    {
        // 用UTF-8转码，而不是用默认的ISO8859 ,避免中文乱码
    	response.reset();
    	response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PageData pd = this.getPageData();
        
		try {
			wxPublicNumCondigService.delete(pd);
			response.getWriter().print("SUCCESS:删除成功！");
		} catch (Exception e) {
			response.getWriter().print("FAIL:删除失败！原因：" + e.getMessage());
		}
    }
    
    
    
}