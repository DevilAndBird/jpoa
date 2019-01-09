package com.fh.service.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.order.OrderEvaluate;

/**
 * 订单服务评价
 */
@Service
public class OrderEvaluateService {
	
	@Autowired
    private DaoSupport dao;

    /**
     * @desc 发表评价
     * @auther zhangjj
     * @date 2018年4月10日
     */
    public void save(OrderEvaluate orderEvaluate) throws Exception {
    	dao.save("orderEvaluateMapper.insert", orderEvaluate);
    }
    
    /**
     * @desc 更新评语
     * @auther zhangjj
     * @date 2018年4月10日
     */
    public void updateEvaluatedesc(OrderEvaluate orderEvaluate) throws Exception {
    	dao.update("orderEvaluateMapper.updateEvaluatedesc", orderEvaluate);
    }
    
    /**
     * @desc 查看评价
     * @auther zhangjj
     * @date 2018年4月10日
     */
    public OrderEvaluate findbyid(Integer orderid) throws Exception {
    	return (OrderEvaluate) dao.findForObject("orderEvaluateMapper.findbyid", orderid);
    }

}
