package com.fh.service.area;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.area.AreaCityInfo;
import com.fh.entity.area.AreaDistrictInfo;
import com.fh.entity.area.AreaProvInfo;
import com.fh.util.PageData;

@Service
public class AreaDistrictService {

	@Autowired
	private DaoSupport dao;
	
	
	/**
	 * 修改区县
	 * @throws Exception 
	 */
	public String updateDistrict( PageData pd ) throws Exception{
		
		AreaProvInfo provInfo = (AreaProvInfo)dao.findForObject("AreaProvInfoMapper.getByName", pd);
		if( null != provInfo ){
			pd.put("modelProvID", provInfo.getId());
		}
		AreaCityInfo areaCityInfo = (AreaCityInfo)dao.findForObject("AreaCityInfoMapper.getByModelCityName", pd);
		if( null != areaCityInfo ){
			pd.put("modelCityID", areaCityInfo.getId());
		}
		
		AreaDistrictInfo areaDistrictInfo = (AreaDistrictInfo)dao.findForObject("AreaDistrictInfoMapper.checkDistrictInfo", pd);
		if( null != areaDistrictInfo ){
			if( !areaDistrictInfo.getId().equalsIgnoreCase(pd.getString("modelId")) ){
				return "1";
			}
		}
		
		dao.save("AreaDistrictInfoMapper.updateDistrict", pd);
		return "2";
	}
	
	
	/**
	 * 新增区县
	 * @throws Exception 
	 */
	public void insertDistrict( PageData pd ) throws Exception{
		dao.save("AreaDistrictInfoMapper.insertDistrict", pd);
	}
	
	/**
	 * 审核ID是否存在
	 * 木桐
	 * 2018年1月17日15:50:43
	 * @throws Exception 
	 */
	public AreaDistrictInfo checkID( PageData pd ) throws Exception{
		return (AreaDistrictInfo)dao.findForObject("AreaDistrictInfoMapper.checkID", pd);
	}
	
	/**
	 * 修改状态
	 * 木桐
	 * 2018年1月10日14:35:53
	 * @throws Exception 
	 */
	public void changeStatus( PageData pd ) throws Exception{
		
		dao.update("AreaDistrictInfoMapper.changeStatus", pd);
		
	}
	
	
	/**
	 * 区县列表查询
	 * 木桐
	 * 2018年1月17日22:15:43
	 * @throws Exception 
	 */
	@SuppressWarnings("all")
	public List<PageData> districtList( Page page ) throws Exception{
		PageData pd = page.getPd();
		String name = pd.getString("name");
		if( null != name ){
			pd.put("name", name.replace(" ", ""));
		}
		List<PageData> districtList = (List<PageData>)dao.findForList("AreaDistrictInfoMapper.districtlistPage", page);
		return districtList;
	}
	
	
	/**
	 * 区县所有
	 * 木桐
	 * 2018年1月17日22:15:43
	 * @throws Exception 
	 */
	@SuppressWarnings("all")
	public List<PageData> findAll() throws Exception{
		List<PageData> districtList = (List<PageData>)dao.findForList("AreaDistrictInfoMapper.findAll", null);
		return districtList;
	}
	
}
