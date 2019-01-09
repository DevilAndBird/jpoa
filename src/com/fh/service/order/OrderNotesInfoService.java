package com.fh.service.order;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.order.OrderNotesInfo;
import com.fh.entity.system.User;
import com.fh.util.Const;
import com.fh.util.ExceptionUtil;
import com.fh.util.PageData;

/**
 * QR码+图片路径
 */
@Service
public class OrderNotesInfoService {
	
	@Autowired
    private DaoSupport dao;
	
	/**
	 * @desc 根据订单id查询备注信息
	 * @auther zhangjj
	 * @date 2018年6月6日
	 */
	@SuppressWarnings("all")
	public List<PageData> findByOrderid(Integer orderid) throws Exception{
		return  (List<PageData>)  dao.findForList("orderNotesInfoMapper.findByOrderid", orderid);
	}
	
	
	/**
	 * @desc 订单备注信息增加
	 * @auther zhangjj
	 * @date 2018年6月6日
	 */
	public void insert(OrderNotesInfo orderNotesInfo) throws Exception {
	    // 添加联系人
	    User user = (User) SecurityUtils.getSubject().getSession().getAttribute( Const.SESSION_USER );
	    ExceptionUtil.checkNotNull(user, "您未登录，请及时登陆");
	    orderNotesInfo.setAddusername(user.getNAME());
	    dao.save( "orderNotesInfoMapper.insert",  orderNotesInfo);
	}
	
	
	/**
     * @desc 修改订单备注信息
     * @auther zhangjj
     * @date 2018年6月6日
     */
    public void updateNotes(OrderNotesInfo orderNotesInfo) throws Exception {
        dao.update( "orderNotesInfoMapper.updateNotesById",  orderNotesInfo);
    }
}
