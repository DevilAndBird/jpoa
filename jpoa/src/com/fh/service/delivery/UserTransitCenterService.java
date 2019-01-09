package com.fh.service.delivery;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;


@Service
public class UserTransitCenterService {
	@Resource
	private DaoSupport dao;
	
    /**
     *@desc 根据Userid查询集散中心id
     *@auther zhangjj
     *@history 2018年2月9日
     */
    public PageData findUserTransitCenterByUserid(PageData pd) throws Exception {
         return (PageData) dao.findForObject("UserTransitCenterMapper.findUserTransitCenterByUserid", pd);
    }
    
    /**
     *@desc appuser关联集散中心查询
     *@auther zhangjj
     *@history 2018年2月1日
     */
    public void appUserLinkTransitCenter(PageData PageDate) throws Exception {
         dao.save("UserTransitCenterMapper.appUserLinkTransitCenter", PageDate);
    }

}
