package com.fh.service.app;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.fh.service.auxiliary.push.PushInfoService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fh.common.constant_enum.PROCESSSTATUS_TYPE;
import com.fh.common.constant_enum.TOUBLE_CODE;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.app.order.AppOrderToubleReqData;
import com.fh.entity.order.OrderOverTime;
import com.fh.entity.order.ProblemNotes;
import com.fh.entity.order.ProblemOrder;
import com.fh.entity.system.User;
import com.fh.util.Const;
import com.fh.util.DateUtil;
import com.fh.util.ExceptionUtil;
import com.fh.util.PageData;

@Service
public class AppOrderToubleInfoService {
    @Autowired
    private DaoSupport dao;
    @Autowired
    private PushInfoService pushInfoService;
    /**
     * @desc 反馈
     * @auther zhangjj
     * @history 2018年12月10日
     */
	public void feedback(ProblemOrder problemOrder) throws Exception{
	    problemOrder.setProcessstatus(PROCESSSTATUS_TYPE.UNSOLVED.getValue());
	    dao.save("ProblemOrderMapper.insert", problemOrder);
    }
	
	/**
     * @desc 处理
     * @auther zhangjj
     * @history 2018年12月10日
     */
    public void process(ProblemOrder problemOrder) throws Exception{
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute( Const.SESSION_USER );
        ExceptionUtil.checkNotNull(user, "重新登陆！");
        problemOrder.setProcessname(user.getNAME());
        problemOrder.setProcessmobile(user.getPHONE());
        String nowdate = DateUtil.getTime();
        problemOrder.setProcesstime(DateUtil.fomatDate(nowdate, "yyyy-MM-dd HH:mm:ss"));
        problemOrder.setProcesstimeformat(nowdate);
        problemOrder.setProcesstime(DateUtil.fomatDate(DateUtil.getTime(), "yyyy-MM-dd HH:mm:ss"));
        problemOrder.setProcessstatus(PROCESSSTATUS_TYPE.SOLVED.getValue());
        dao.update("ProblemOrderMapper.update", problemOrder);

        ProblemOrder troubleInfo = (ProblemOrder) dao.findForObject("ProblemOrderMapper.findTroubleInfoById",problemOrder);
        pushInfoService.processPush(troubleInfo.getFeedbackuserid(),troubleInfo.getOrderid());
    }
    
    /**
     * @desc 查询反馈信息
     * @auther zhangjj
     * @history 2018年12月10日
     */
    public List<ProblemOrder> findOrderTouble(Integer orderid) throws Exception{
        return (List<ProblemOrder>) dao.findForList("ProblemOrderMapper.findProblemOrderList", orderid);
    }
    
    /**
     * @desc 问题处理列表查询
     * @auther zhangjj
     * @history 2018年12月10日
     */
    public  List<PageData> findOrderlistPage(Page page) throws Exception{
        return (List<PageData>) dao.findForList("ProblemOrderMapper.findOrderlistPage", page);
    }

    
    /**
     * @desc 根据订单id统计订单下未处理订单
     * @auther zhangjj
     * @history 2018年12月10日
     */
    @SuppressWarnings("all")
    public Integer findUnsolvedByOrderid(Integer orderid) throws Exception {
        return (Integer) dao.findForObject("ProblemOrderMapper.findUnsolvedByOrderid", orderid);
    }
    
}
