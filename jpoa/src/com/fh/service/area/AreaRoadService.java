package com.fh.service.area;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.area.AreaCityInfo;
import com.fh.entity.area.AreaDistrictInfo;
import com.fh.entity.area.AreaProvInfo;
import com.fh.entity.area.AreaRoadInfo;
import com.fh.util.PageData;

@Service
public class AreaRoadService {

	@Autowired
	private DaoSupport dao;
	
	/**
	 * 根据name获街道对象
	 * 木桐
	 * 2018年1月18日19:30:23
	 */
	public AreaCityInfo getByName(String name) throws Exception{
		PageData pd = new PageData();
		pd.put("name", name);
		return (AreaCityInfo)dao.findForObject("AreaRoadInfoMapper.getByName", pd);
	}
	
	/**
	 * 修改路街信息
	 * @throws Exception 
	 */
	public String updateRoad( PageData pd ) throws Exception{
		
		AreaProvInfo provInfo = (AreaProvInfo)dao.findForObject("AreaProvInfoMapper.getByName", pd);
		if( null != provInfo ){
			pd.put("modelProvID", provInfo.getId());
		}
		AreaCityInfo areaCityInfo = (AreaCityInfo)dao.findForObject("AreaCityInfoMapper.getByModelCityName", pd);
		if( null != areaCityInfo ){
			pd.put("modelCityID", areaCityInfo.getId());
		}
		AreaDistrictInfo areaDistrictInfo = (AreaDistrictInfo)dao.findForObject("AreaDistrictInfoMapper.getByModelDistrictID", pd);
		if( null != areaDistrictInfo ){
			pd.put("modelDistrictID", areaDistrictInfo.getId());
		}
		
		AreaRoadInfo areaRoadInfo = (AreaRoadInfo)dao.findForObject("AreaRoadInfoMapper.checkRoadInfo", pd);
		if( null != areaRoadInfo ){
			if( !areaRoadInfo.getId().equalsIgnoreCase(pd.getString("modelId")) ){
				return "1";
			}
		}
		
		dao.update("AreaRoadInfoMapper.updateRoad", pd);
		return "2";
	}
	
	
	/**
	 * 新增街道
	 * @throws Exception 
	 */
	public void insertRoad( PageData pd ) throws Exception{
		dao.save("AreaRoadInfoMapper.insertRoad", pd);
	}
	
	/**
	 * 审核ID是否存在
	 * 木桐
	 * 2018年1月17日15:50:43
	 * @throws Exception 
	 */
	public AreaRoadInfo checkID( PageData pd ) throws Exception{
		return (AreaRoadInfo)dao.findForObject("AreaRoadInfoMapper.checkID", pd);
	}
	
	/**
	 * 修改状态
	 * 木桐
	 * 2018年1月10日14:35:53
	 * @throws Exception 
	 */
	public void changeStatus( PageData pd ) throws Exception{
		
		dao.update("AreaRoadInfoMapper.changeStatus", pd);
		
	}
	
	
	/**
	 * 街道列表查询
	 * 木桐
	 * 2018年1月17日22:15:43
	 * @throws Exception 
	 */
	@SuppressWarnings("all")
	public List<PageData> roadList( Page page ) throws Exception{
		PageData pd = page.getPd();
		String name = pd.getString("name");
		if( null != name ){
			pd.put("name", name.replace(" ", ""));
		}
		List<PageData> roadList = (List<PageData>)dao.findForList("AreaRoadInfoMapper.roadlistPage", page);
		return roadList;
	}
	
	
	/**
	 * 街道所有
	 * 木桐
	 * 2018年1月17日22:15:43
	 * @throws Exception 
	 */
	@SuppressWarnings("all")
	public List<PageData> findAll() throws Exception{
		List<PageData> cityList = (List<PageData>)dao.findForList("AreaRoadInfoMapper.findAll", null);
		return cityList;
	}
	
}
