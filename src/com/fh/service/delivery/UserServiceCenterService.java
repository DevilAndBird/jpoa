package com.fh.service.delivery;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

@Service
public class UserServiceCenterService {
	@Resource
	private DaoSupport dao;
	
    /**
     *@desc 根据Userid查询  serviceCenterid
     *@auther zhangjj
     *@history 2018年2月9日
     */
    public PageData findUserServiceCenterByUserid(PageData pd) throws Exception {
         return (PageData) dao.findForObject("UserServiceCenterMapper.findUserServiceCenterByUserid", pd);
    }
    
    

    /**
     *@desc appuser关联服务中心查询
     *@auther zhangjj
     *@history 2018年2月9日
     */
    public void appUserLinkServiceCenter(PageData PageDate) throws Exception {
         dao.save("UserServiceCenterMapper.appUserLinkServiceCenter", PageDate);
    }
}
