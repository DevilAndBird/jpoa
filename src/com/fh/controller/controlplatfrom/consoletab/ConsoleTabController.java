package com.fh.controller.controlplatfrom.consoletab;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.service.controlplatfrom.autoalloc.ConsoleAutoAllocService;
import com.fh.service.controlplatfrom.waitmanualalloc.WaitManualAllocService;
import com.fh.util.PageData;

/**
 * @author zhangjj
 * 控制台_导航栏信息内容查询统计
 */
@Controller
@RequestMapping(value = "console_tab")
public class ConsoleTabController extends BaseController {
	
	@Autowired
	private WaitManualAllocService manualAllocService;
	@Autowired
	private ConsoleAutoAllocService consoleAutoAllocService;
	
	/**
     * @desc 导航页
     * @auther zhangjj
     * @date 2018年5月16日
     */
    @RequestMapping(value = "/console_tab_main")
    public ModelAndView console_tab_main() throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.addObject("pd", this.getPageData());
        mv.setViewName("jpoa/controlplatfrom/tab/jsp/console_tab");
        return mv;
    }
    
    /**
     * @desc 取派员列表
     * @auther zhangjj
     * @date 2018年5月16日
     */
    @RequestMapping(value = "/dmanlist_main")
    public ModelAndView dmanlist() throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.addObject("pd", this.getPageData());
        mv.setViewName("jpoa/controlplatfrom/dmanlist/jsp/dmanlist");
        return mv;
    }
    
    /**
     * @desc 待人工分配
     * @auther zhangjj
     * @date 2018年5月16日
     */
    @RequestMapping(value = "/waitallocmap_main")
    public ModelAndView waitallocmap_main() throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.addObject("pd", this.getPageData());
        mv.setViewName("jpoa/controlplatfrom/waitmanualalloc/jsp/waitallocmap");
        return mv;
    }
    
    /**
     * @desc 自动分配列表
     * @auther zhangjj
     * @date 2018年5月16日
     */
    @RequestMapping(value = "/autoallocmap_main")
    public ModelAndView autoallocmap_main() throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.addObject("pd", this.getPageData());
        mv.setViewName("jpoa/controlplatfrom/autoallot/jsp/autoallocmap");
        return mv;
    }
    
    /**
	 * @desc 待人工分配订单数量
	 * @auther zhangjj
	 * @date 2018年5月14日
	 */
	@ResponseBody
	@RequestMapping(value = "/countWaitManualAlloc")
	public String countWaitManualAlloc() throws Exception {
		List<PageData> waitmanualAlloc = manualAllocService.findWaitmanualAlloc(this.getPageData());
		return waitmanualAlloc != null? waitmanualAlloc.size() +"" : "0";
	}
	
	/**
	 * @desc 自动分配数量
	 * @auther zhangjj
	 * @date 2018年5月14日
	 */
	@ResponseBody
	@RequestMapping(value = "/autoallotcount")
	public String autoallotcount() throws Exception {
		List<PageData> autoallotcount = consoleAutoAllocService.findAutoAllotlist(this.getPageData());
		return autoallotcount != null? autoallotcount.size() +"" : "0";
	}
	
}
