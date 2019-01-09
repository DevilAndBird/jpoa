package com.fh.service.controlplatfrom.websocket;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author tangqm
 * 控台导航页数量查询
 * 2018年12月13日
 */
@Service
public class WebSocketService {
	@Resource
	private DaoSupport dao;

    /**
     * tangqm
     * 查看待分配数量
     * 2018年12月13日
     */
    public Integer findWaitmanualAllocNum(PageData pd) throws Exception {
        List<PageData>newManualAllocNum= (List<PageData>) dao.findForList("OrderMainMapper.findWaitmanualAlloc", pd);
    	return newManualAllocNum != null? newManualAllocNum.size() : 0;
    }

    /**
     *tangqm
     * 查看自动分配数量
     */
    public Integer findAutoAllotNum(PageData pd) throws Exception {
        List<PageData> autoallotlist = (List<PageData>) dao.findForList("OrderMainMapper.findAutoAllotlist", pd);
        return autoallotlist != null? autoallotlist.size() : 0;
    }

    /**
     * @desc 查看未处理订单数
     * @auther tangqm
     * @history 2018年12月12日
     */
    public  Integer findUntreatedNum() throws Exception{
        return (Integer) dao.findForObject("ProblemOrderMapper.findNumByUntreated",null);
    }

}
