package com.fh.test.util;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fh.dao.DaoSupport;
import com.fh.entity.area.AreaCityInfo;
import com.fh.entity.delivery.BaiduCoord;
import com.fh.test.base.BaseTest;
import com.fh.thirdparty.baidu.BaiDuMapBO;
import com.fh.util.PageData;

public class BaiduMapUtilTest extends BaseTest {
	
	@Resource
	private DaoSupport dao;
	@Autowired
	private BaiDuMapBO baiDuMapBO;
	
	/**
     * 更新城市中心点
     */
    @Test
    public void getLngAndLat() throws Exception {
    	String address = "浙江省杭州市萧山机场";// 121.35492, lat=31.192945
    	BaiduCoord coordinate = baiDuMapBO.getLngAndLat(address);
    	System.out.println(coordinate);
    }
	
    /**
     * 更新城市中心点
     */
    @Test
    public void getGpsByAddr() throws Exception {
    	String address = "上海市闵行区申长路688号";// 121.35492, lat=31.192945
    	BaiduCoord coordinate = baiDuMapBO.getGpsByAddr(address);
    	System.out.println(coordinate);
    }
    
	/**
     * 更新城市中心点
     */
    @Test
    public void updateCityCenterPointGps() throws Exception {
    	List<PageData> pdList = (List<PageData>) dao.findForList("AreaCityInfoMapper.findProvname", null);
    	for (PageData pd : pdList) {
    		String address = pd.getString("provnameandcityname");
    		BaiduCoord coordinate = baiDuMapBO	.getLngAndLat(address);
    		if(coordinate == null) {
    			continue;
    		}
    		String gps = "{'lng':'"+ coordinate.getLng() +"','lat':'"+ coordinate.getLat() +"'}";
    		AreaCityInfo areaCityInfo = new AreaCityInfo();
    		areaCityInfo.setId(pd.getString("cityid"));
    		areaCityInfo.setCentralpointgps(gps);
    		dao.update("AreaCityInfoMapper.updateGps", areaCityInfo);
		}
    }

}
