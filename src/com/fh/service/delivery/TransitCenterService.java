package com.fh.service.delivery;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;


@Service
@SuppressWarnings("all")
public class TransitCenterService {
	@Resource
	private DaoSupport dao;
	
	/**
	 *@desc 集散中心分页查询
	 *@auther zhangjj
	 *@history 2018年2月1日
	 */
	public List<PageData> findTransitCenterlistPage(Page page) throws Exception {
		return (List<PageData>) dao.findForList("TransitCenterMapper.findTransitCenterlistPage", page);
	}
	
	/**
     *@desc 集散中心查询根据主键
     *@auther zhangjj
     *@history 2018年2月1日
     */
    public PageData findTransitCenterById(PageData pd) throws Exception {
        return (PageData) dao.findForObject("TransitCenterMapper.findTransitCenterById", pd);
    }
    
    /**
     *@return 
     * @desc 修改集散中心
     *@auther zhangjj
     *@history 2018年2月1日
     */
    public Object updateTransitCenterById(PageData pd) throws Exception {
        return dao.update("TransitCenterMapper.updateTransitCenterById", pd);
    }
	
	
    /**
     *@return 
     * @desc 保存集散中心
     *@auther zhangjj
     *@history 2018年2月1日
     */
    public int saveTransitCenter(PageData pd) throws Exception {
        return (int) dao.update("TransitCenterMapper.saveTransitCenter", pd);
    }
	

	/**
     * 查询所有集散中心_用作下列表
     */
    public List<PageData> findAll(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("TransitCenterMapper.findAll", pd);
    }
    
    
    /**
     * 根据登陆用户名查询集散中心信息
     * zhangjj
     */
    public PageData findByUserid(Integer userid) throws Exception {
        PageData pd = new PageData();
        pd.put("userid", userid);
        return (PageData) dao.findForObject("TransitCenterMapper.findByUserid", pd);
    }

    /**
     * 查询省份以及城市
     * zhangjj
     */
	public List<PageData> findByProvidAndCityid(String provid, String cityid) throws Exception {
		 PageData pd = new PageData();
	     pd.put("provid", provid);
	     pd.put("cityid", cityid);
	     return (List<PageData>) dao.findForList("TransitCenterMapper.findByProvidAndCityid", pd);
	}
    
	 /**
     * @desc 获取集散中心当前位置
     * @auther 
     * @date 
     */
    public List<PageData> findTransitCentergps(PageData pd) throws Exception {
    	return (List<PageData>) dao.findForList("TransitCenterMapper.findTransitCentergps", pd);
    }
    
}
