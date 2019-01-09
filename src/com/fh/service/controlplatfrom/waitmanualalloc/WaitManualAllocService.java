package com.fh.service.controlplatfrom.waitmanualalloc;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

@Service
public class WaitManualAllocService {
	@Resource
	private DaoSupport dao;
	
    /**
     * @desc 待人工分配信息查询
     * @auther zhangjj
     * @date 2018年5月17日
     */
    @SuppressWarnings("unchecked")
    public List<PageData> findWaitmanualAlloc(PageData pd) throws Exception {
    	return (List<PageData>) dao.findForList("OrderMainMapper.findWaitmanualAlloc", pd);
    }
    
    
}
