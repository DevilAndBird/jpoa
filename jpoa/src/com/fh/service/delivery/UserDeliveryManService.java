package com.fh.service.delivery;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.common.constant_enum.DMAN_STATUS;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.order.OrderAddress;
import com.fh.util.PageData;
@Service
@SuppressWarnings("all")
public class UserDeliveryManService {
	@Resource
	private DaoSupport dao;
	
	/**
	 *@desc 查询快递员列表_分页
	 *@auther zhangjj
	 *@history 2018年2月2日
	 */
	public List<PageData> findDeliveryManlistPage(Page page) throws Exception {
		return (List<PageData>) dao.findForList("UserDeliveryManMapper.findDeliveryManlistPage", page);
	}
	
	/**
	 *@desc 控台_取派员信息(待取_UNFINISHED、待派_UNFINISHED、已经完成_FINISHED)
	 *@auther zhangjj
	 *@history 2018年2月2日
	 */
	public List<PageData> findDmanList_contralplatform(PageData pageData) throws Exception {
		return (List<PageData>) dao.findForList("UserDeliveryManMapper.findDmanList_contralplatform", pageData);
	}
	
	/**
	 *@desc 根据主键更新快递员信息
	 *@auther zhangjj
	 *@history 2018年2月5日
	 */
	public Object updateUserDeliveryManById(PageData pageData) throws Exception {
        return dao.update("UserDeliveryManMapper.updateUserDeliveryManById", pageData);
    }
	
	/**
     *@desc 插入快递员信息
     *@auther zhangjj
     *@history 2018年2月1日
     */
    public int saveUserDeliveryMan(PageData pd) throws Exception {
        return (int) dao.update("UserDeliveryManMapper.saveUserDeliveryMan", pd);
    }
	
    /**
     * @desc 查询所有快递员信息
     * @author zhangjj
     * @history 2018年02月01日
     */
    public List<PageData> findAll(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("UserDeliveryManMapper.findAll", pd);
    }
    
    /**
     * @desc 根据用户userid查询取派员信息
     * @author zhangjj
     * @history 2018年02月01日
     */
    public PageData findByUserid(Integer userid) throws Exception {
        return (PageData) dao.findForObject("UserDeliveryManMapper.findByUserid", userid);
    }
    
    public void updateUserDeliveryStatusById(Integer id) throws Exception{
    	PageData pd = new PageData();
    	pd.put("userid", id);
    	pd.put("status", DMAN_STATUS.CANNOTDISPATCH.getValue());
        dao.update("UserDeliveryManMapper.updateUserDeliveryStatusById", pd);
    }
   
    /**
     * @desc 获取寄件地址位置
     * @auther 
     * @date 
     */
    public OrderAddress findsrcaddessGps(PageData pd) throws Exception {
    	return (OrderAddress) dao.findForObject("UserDeliveryManMapper.findsrcaddessGps", pd);
    }
    
	/**
	 * @desc 获取取派员id
	 * @auther 
	 * @date 
	 */
	public List<PageData> findDmanId(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("UserDeliveryManMapper.findDmanId", pd);
	}
	
	/**
	 * @desc 获取取派员省份id,城市id
	 * @auther zhangjj
	 * @date 2018年3月14日
	 */
	public List<PageData> findDmanByProvidAndCityid(String provid, String cityid) throws Exception {
		PageData pd = new PageData();
		pd.put("provid", provid);
		pd.put("cityid", cityid);
		return (List<PageData>) dao.findForList("UserDeliveryManMapper.findDmanByProvidAndCityid", pd);
	}
	
}
