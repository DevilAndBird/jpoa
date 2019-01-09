package com.fh.service.delivery;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.thirdparty.baidu.BaiDuMapBO;
import com.fh.util.Logger;
import com.fh.util.PageData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Service
@SuppressWarnings("all")
public class RegionManagementService {
    
    Logger logger = Logger.getLogger(RegionManagementService.class);

    @Autowired
    private DaoSupport dao;
    @Autowired
    private BaiDuMapBO baiDuMapBO;

    /**	
     * @desc:市级信息查詢-分页
     * @author: zhangjj
     * @history: 2018年1月15日
     */
    public List<PageData> regionCityListPage(Page page) throws Exception {
        return (List<PageData>) dao.findForList("RegionCityMapper.regionCtiyInfolistPage", page);
    }
    
    /** 
     * @desc:集散中心查詢-分页
     * @author: zhangjj
     * @history: 2018年1月15日
     */
    public List<PageData> transitCenterListPage(Page page) throws Exception {
        return (List<PageData>) dao.findForList("RegionTransitCenterMapper.regionTransitCenterlistPage", page);
    }
    
    /** 
     * @desc:取派员区域
     * @author: zhangjj
     * @history: 2018年1月15日
     */
    public List<PageData> regionDeliveryManListPage(Page page) throws Exception {
        return (List<PageData>) dao.findForList("RegionDeliveryManMapper.regionDeliveryManlistPage", page);
    }
    
    /**
     * 
     * @Title: regionDeliveryManList
     * @Description: 获取取派员下拉框
     * author：tangqm
     * 2018年6月6日
     * @param pd
     * @return
     * @throws Exception
     */
    public List<PageData> selcRegionDeliveryManlist(PageData pd) throws Exception {
    	return (List<PageData>) dao.findForList("RegionDeliveryManMapper.selcRegionDeliveryManlist", pd);
    }
    
    /** 
     * @desc: 取派员区域列表查询
     * @author: zhangjj
     * @history: 2018年1月15日
     */
    public List<PageData> regionDeliveryManList(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("RegionDeliveryManMapper.regionDeliveryManList", pd);
    }
    
    /** 
     * @desc:市级信息查詢
     * @author: zhangjj
     * @history: 2018年1月15日
     */
    public List<PageData> findAllRegionCity() throws Exception {
        return (List<PageData>) dao.findForList("RegionCityMapper.findAllRegionCity", null);
    }

    /** 
     * @desc:市级信息查詢
     * @author: zhangjj
     * @history: 2018年1月15日
     */
	public int saveRegionCityGps(PageData pageData) throws Exception {
		return (int) dao.save("RegionCityMapper.saveRegionCtiy", pageData);
	}

	/** 
     * @desc:检查新增的市是否已经重复
     * @author: zhangjj
     * @history: 2018年1月15日
     */
	public  List<PageData> checkRegionCtiyExist(PageData pageData) throws Exception {
		 return (List<PageData>)dao.findForList("RegionCityMapper.checkRegionCtiyExist", pageData);
	}

	/** 
     * @desc:根据主键查询市级区域
     * @author: zhangjj
     * @history: 2018年1月15日
     */
    public PageData findRegionCtiyByid(PageData pageData) throws Exception {
        return (PageData) dao.findForObject("RegionCityMapper.findRegionCtiyByid", pageData);
    }

    /** 
     * @desc:根据主键更新市级区域
     * @author: zhangjj
     * @history: 2018年1月15日
     */
    public void updateRegionCity(PageData pageData) throws Exception {
        dao.update("RegionCityMapper.updateRegionCity", pageData);
    }

    /** 
     * @desc:查询集散中心区域
     * @author: zhangjj
     * @history: 2018年1月15日
     */
    public List<PageData> findAllRegionTransitCenter(PageData pageData) throws Exception  {
        return (List<PageData>) dao.findForList("RegionTransitCenterMapper.findAllRegionTransitCenter", pageData);
    }

    /** 
     * @desc:检查是否已经集散中心区域划分
     * @author: zhangjj
     * @history: 2018年1月15日
     */
    public List<PageData> checkRegionTransitExist(PageData pageData) throws Exception {
        return (List<PageData>)dao.findForList("RegionTransitCenterMapper.checkRegionTransitExist", pageData);
    }
    
    /** 
     * @desc:检查是否已经集散中心区域划分
     * @author: zhangjj
     * @history: 2018年1月15日
     */
    public Integer saveRegionTransit(PageData pageData) throws Exception {
         dao.save("RegionTransitCenterMapper.saveRegionTransit", pageData);
         return (Integer) pageData.get("id");
    }

    /** 
     * @desc:根据id查询集散中心划分
     * @author: zhangjj
     * @history: 2018年1月15日
     */
    public PageData findRegionTransitByid(PageData pageData) throws Exception {
        return (PageData) dao.findForObject("RegionTransitCenterMapper.findRegionTransitByid", pageData);
    }

    /** 
     * @desc:根据id查询集散中心划分
     * @author: zhangjj
     * @history: 2018年1月15日
     */
    public void updateRegionTransit(PageData pageData) throws Exception {
        dao.update("RegionTransitCenterMapper.updateRegionTransit", pageData);
    }

    /** 
     * @desc:根据所有取派员信息
     * @author: zhangjj
     * @history: 2018年1月15日
     */
    public List<PageData> findAllRegionDman(PageData pageData) throws Exception {
        return (List<PageData>) dao.findForList("RegionDeliveryManMapper.findAllRegionDman", pageData);
    }

    /** 
     * @desc:检查取派员是否划分区域
     * @author: zhangjj
     * @history: 2018年1月15日
     */
    public List<PageData> checkRegionDmanExist(PageData pageData) throws Exception {
        return (List<PageData>)dao.findForList("RegionDeliveryManMapper.checkRegionDmanExist", pageData);
    }

    /** 
     * @desc:新增取派员区域
     * @author: zhangjj
     * @history: 2018年1月15日
     */
    public void saveRegionDman(PageData pageData) throws Exception {
        dao.save("RegionDeliveryManMapper.saveRegionDman", pageData);
    }
    
    /** 
     * @desc:删除集散中心
     * @author: tangqm
     * @history: 2018-05-23 15:06
     */
    public void deleteRegionTransit(PageData pageData) throws Exception{
    	dao.delete("RegionTransitCenterMapper.deleteRegionTransit", pageData);
    }

    /** 
     * @desc:根据id取派员是否划分区域
     * @author: zhangjj
     * @history: 2018年1月15日
     */
    public PageData findRegionDmanByid(PageData pageData) throws Exception {
        return (PageData) dao.findForObject("RegionDeliveryManMapper.findRegionDmanByid", pageData);
    }

    /** 
     * @desc:更新取派员区域划分
     * @author: zhangjj
     * @history: 2018年1月15日
     */
    public void updateRegionDman(PageData pageData) throws Exception {
        dao.update("RegionDeliveryManMapper.updateRegionDman", pageData);
    }
    
    /** 
     * @desc:自动关联集散中心工作区域
     * @author: tangqm
     * @history: 2018年5月24日15点02分
     */
    public void checkWorkRegion(List<PageData> regionDeliveryManList,List<PageData> transitRegion,Integer transitid) throws Exception {
    	PageData transit = new PageData();
    	transit.put("transitid", transitid);
    	dao.update("RegionDeliveryManMapper.cleanDmanTransitId",transit);//清空取派员
    	for (PageData pd : regionDeliveryManList) {
			String gpsStr = (String) pd.get("gps");
			List<PageData> gpsList = new Gson().fromJson(gpsStr, new TypeToken<List<PageData>>(){}.getType());
			if(baiDuMapBO.checkContrainRegion(gpsList, transitRegion)){
				pd.put("transitid", transitid);
				dao.update("RegionDeliveryManMapper.updateDmanTransitId", pd);
			}
		}
    }
}
