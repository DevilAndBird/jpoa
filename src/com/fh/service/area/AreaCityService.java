package com.fh.service.area;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.area.AreaCityInfo;
import com.fh.entity.area.AreaProvInfo;
import com.fh.util.PageData;

@Service
public class AreaCityService {

	@Autowired
	private DaoSupport dao;

	/**
	 * 判断城市是否存在
	 * 木桐
	 * 2018年1月24日11:20:20
	 */
	public AreaCityInfo checkInfoByName( PageData pd ) throws Exception{
		return (AreaCityInfo)dao.findForObject("AreaCityInfoMapper.checkInfoByName", pd);
	}
	
	
	/**
	 * 根据name获取城市对象
	 * 木桐
	 * 2018年1月18日19:30:23
	 */
	public AreaCityInfo getByName(String name) throws Exception{
		PageData pd = new PageData();
		pd.put("name", name);
		return (AreaCityInfo)dao.findForObject("AreaCityInfoMapper.getByName", pd);
	}
	
	/**
	 * 删除省份
	 * @throws Exception 
	 */
	public void delProv( PageData pd ) throws Exception{
		dao.delete("AreaCityInfoMapper.delProv", pd);
	}
	
	/**
	 * 修改城市
	 * @throws Exception 
	 */
	public String updateCity( PageData pd ) throws Exception{
		AreaProvInfo provInfo = (AreaProvInfo)dao.findForObject("AreaProvInfoMapper.getByName", pd);
		if( null != provInfo ){
			pd.put("modelProvID", provInfo.getId());
		}
		
		//判断城市是否存在
		AreaCityInfo areaCityInfo = (AreaCityInfo)dao.findForObject("AreaCityInfoMapper.checkInfoByName", pd);
		if( null != areaCityInfo ){
			if( !areaCityInfo.getId().equalsIgnoreCase(pd.getString("modelId")) ){
				return "1";
			}
		}
		
		dao.save("AreaCityInfoMapper.updateCity", pd);
		
		return "2";
	}
	
	
	/**
	 * 新增城市
	 * @throws Exception 
	 */
	public void insertCity( PageData pd ) throws Exception{
		dao.save("AreaCityInfoMapper.insertCity", pd);
	}
	
	/**
	 * 审核ID是否存在
	 * 木桐
	 * 2018年1月17日15:50:43
	 * @throws Exception 
	 */
	public AreaCityInfo checkID( PageData pd ) throws Exception{
		return (AreaCityInfo)dao.findForObject("AreaCityInfoMapper.checkID", pd);
	}
	
	/**
	 * 修改状态
	 * 木桐
	 * 2018年1月10日14:35:53
	 * @throws Exception 
	 */
	public void changeStatus( PageData pd ) throws Exception{
		
		dao.update("AreaCityInfoMapper.changeStatus", pd);
		
	}
	
	
	/**
	 * 城市列表查询
	 * 木桐
	 * 2018年1月17日22:15:43
	 * @throws Exception 
	 */
	@SuppressWarnings("all")
	public List<PageData> cityList( Page page ) throws Exception{
		PageData pd = page.getPd();
		String name = pd.getString("name");
		if( null != name ){
			pd.put("name", name.replace(" ", ""));
		}
		List<PageData> cityList = (List<PageData>)dao.findForList("AreaCityInfoMapper.citylistPage", page);
		return cityList;
	}
	
	
	/**
	 * 城市所有
	 * 木桐
	 * 2018年1月17日22:15:43
	 * @throws Exception 
	 */
	@SuppressWarnings("all")
	public List<PageData> findAll() throws Exception{
		List<PageData> cityList = (List<PageData>)dao.findForList("AreaCityInfoMapper.findAll", null);
		return cityList;
	}
	
	/**
     * 根据条件查询城市信息
     * 木桐
     * 2018年1月17日22:15:43
     * @throws Exception 
     */
	@SuppressWarnings("all")
    public List<AreaCityInfo> findCityByProvid(AreaCityInfo areaCityInfo) throws Exception{
        return (List<AreaCityInfo>)dao.findForList("AreaCityInfoMapper.findCityByProvid", areaCityInfo);
    }
    
    /**
     * @desc 根据城市id查询城市中心点坐标
     * @auther zhangjj
     * @date 2018年5月16日
     */
    public String findCentralpointgpsByid(String cityid) throws Exception{
        return (String)dao.findForObject("AreaCityInfoMapper.findCentralpointgpsByid", cityid);
    }
	
}
