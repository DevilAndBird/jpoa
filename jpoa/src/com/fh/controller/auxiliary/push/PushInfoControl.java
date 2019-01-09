
package com.fh.controller.auxiliary.push;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.auxiliary.push.PushInfoService;
import com.fh.util.PageData;
/**
 * 后台系统-推送消息模块
 * @author tqm
 * @date 2018年11月5日
 */
/**
 * @author zhangjj
 * 角色推送信息，后台查询处理维护
 */
@Controller
@RequestMapping(value = "pushInfo")
public class PushInfoControl extends BaseController {

    @Autowired
    private PushInfoService pushInfoService;
    
    /**
     * 
     * @Description: 后台查询推送消息列表
     * @param page
     * @return
     */
    @RequestMapping(value = "listPushInfo")
    public ModelAndView listPushInfo(Page page) throws Exception {
        ModelAndView mv = new ModelAndView();
        PageData pd = this.getPageData();
        
        page.setPd(pd);
        
        List<PageData> pushInfoList = pushInfoService.pushInfolistPage(page);
        if (CollectionUtils.isNotEmpty(pushInfoList)) {
            mv.addObject("pushInfoList", pushInfoList);
        }

        mv.addObject("pd", pd);
        mv.setViewName("customer/pushinfo/pushinfo_list");
        return mv;
        
    }
    
    /**
     * 
     * @Title: pushinfoPage
     * @Description: 跳到推送人员页面
     * @param page
     * @return
     */
    @RequestMapping(value = "pushinfoPage")
    public ModelAndView pushinfoPage(Page page) throws Exception {   	
        ModelAndView mv = new ModelAndView();
        PageData pd = this.getPageData();
        page.setPd(pd);
        
        List<PageData> pushInfoUserList = pushInfoService.pushInfoUserlistPage(page);
        if (CollectionUtils.isNotEmpty(pushInfoUserList)) {
            mv.addObject("pushInfoUserList", pushInfoUserList);
        }
        
        mv.addObject("pd", pd);
        mv.setViewName("customer/pushinfo/pushinfo_add");
        return mv;
    }
    

    /**
     * 
     * @Title: pushinfoadd
     * @Description: 推送消息(所选人)
     * @return
     */
    @RequestMapping(value = "pushinfoadd", produces = "application/json;charset=UTF-8")
    public void pushinfoadd(HttpServletResponse response) throws Exception {
    	//用UTF-8转码，而不是用默认的ISO8859  ,避免中文乱码
    	response.setHeader("Content-type", "text/html;charset=UTF-8");  
		response.setCharacterEncoding("UTF-8");
		
        PageData pd = this.getPageData();
        String notice = pd.getString("notice");//主题
        String theme = pd.getString("theme");//内容
        String usershuzu = (String) pd.get("list");//推送的人员集
        
        if (usershuzu.equals("")) {
        	response.getWriter().print("请选择推送的人员");
        	return;
		}
        if (notice.equals("") || theme.equals("")) {
        	response.getWriter().print("请填写主题和内容");
        	return;
        }

        List<String> result = Arrays.asList(usershuzu.split(","));  
        List<Integer> idsList = new ArrayList<>();
        for(String str : result) {
        	  int i = Integer.parseInt(str);
        	  idsList.add(i);
        }       
		
    	pushInfoService.systemPush(idsList, notice, theme); 

    	response.getWriter().print("推送成功");
    }
    /**
     * 
     * @Title: pushinfoadd
     * @Description: 推送消息(所有人)
     * @returnall
     */
    @RequestMapping(value = "pushinfoaddall", produces = "application/json;charset=UTF-8")
    public void pushinfoaddall(HttpServletResponse response) throws Exception {
    	response.setHeader("Content-type", "text/html;charset=UTF-8");  
		response.setCharacterEncoding("UTF-8");
		
    	PageData pushData = this.getPageData();
    	String notice = pushData.getString("notice");
        String theme = pushData.getString("theme");
        
        if (notice.equals("") || theme.equals("")) {
        	response.getWriter().print("请填写主题和内容");
        	return;
        }
    	List<Integer> pushInfoUserList = pushInfoService.pushInfoUserlistPageallsum();   
    	
    	pushInfoService.systemPush(pushInfoUserList, notice, theme);  
    	//用UTF-8转码，而不是用默认的ISO8859  ,避免中文乱码
		
    	response.getWriter().print("推送成功");
    }
    /**
     * 
     * @Title: pushinfoadd
     * @Description: qu
     * @returnall
     */
    @RequestMapping(value = "pushinfoaddallqu", produces = "application/json;charset=UTF-8")
    public void pushinfoaddallqu(HttpServletResponse response) throws Exception {
    	response.setHeader("Content-type", "text/html;charset=UTF-8");  
    	response.setCharacterEncoding("UTF-8");
    	
    	PageData pushData = this.getPageData();
    	String notice = pushData.getString("notice");
    	String theme = pushData.getString("theme");
    	
    	if (notice.equals("") || theme.equals("")) {
    		response.getWriter().print("请填写主题和内容");
    		return;
    	}
    	List<Integer> pushInfoUserList = pushInfoService.pushInfoUserlistPageallqu();   
    	
    	pushInfoService.systemPush(pushInfoUserList, notice, theme);  
    	//用UTF-8转码，而不是用默认的ISO8859  ,避免中文乱码
    	
    	response.getWriter().print("推送成功");
    }
    /**
     * 
     * @Title: pushinfoadd
     * @Description: ji
     * @returnall
     */
    @RequestMapping(value = "pushinfoaddallji", produces = "application/json;charset=UTF-8")
    public void pushinfoaddallji(HttpServletResponse response) throws Exception {
    	response.setHeader("Content-type", "text/html;charset=UTF-8");  
    	response.setCharacterEncoding("UTF-8");
    	
    	PageData pushData = this.getPageData();
    	String notice = pushData.getString("notice");
    	String theme = pushData.getString("theme");
    	
    	if (notice.equals("") || theme.equals("")) {
    		response.getWriter().print("请填写主题和内容");
    		return;
    	}
    	List<Integer> pushInfoUserList = pushInfoService.pushInfoUserlistPageallji();   
    	
    	pushInfoService.systemPush(pushInfoUserList, notice, theme);  
    	//用UTF-8转码，而不是用默认的ISO8859  ,避免中文乱码
    	
    	response.getWriter().print("推送成功");
    }
    /**
     * 
     * @Title: pushinfoadd
     * @Description: gui
     * @returnall
     */
    @RequestMapping(value = "pushinfoaddallgui", produces = "application/json;charset=UTF-8")
    public void pushinfoaddallgui(HttpServletResponse response) throws Exception {
    	response.setHeader("Content-type", "text/html;charset=UTF-8");  
    	response.setCharacterEncoding("UTF-8");
    	
    	PageData pushData = this.getPageData();
    	String notice = pushData.getString("notice");
    	String theme = pushData.getString("theme");
    	
    	if (notice.equals("") || theme.equals("")) {
    		response.getWriter().print("请填写主题和内容");
    		return;
    	}
    	List<Integer> pushInfoUserList = pushInfoService.pushInfoUserlistPageallgui();   
    	
    	pushInfoService.systemPush(pushInfoUserList, notice, theme);  
    	//用UTF-8转码，而不是用默认的ISO8859  ,避免中文乱码
    	
    	response.getWriter().print("推送成功");
    }
	


}
