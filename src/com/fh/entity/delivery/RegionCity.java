package com.fh.entity.delivery;

import java.util.List;

/**
 * 市级区域管理
 */
public class RegionCity {
    /** 主键 */
    private Integer id;
    /** 省份主键 */
    private String provid;
    /** 市级主键 */
    private String cityid;
    /** 市级坐标集合 */
    private String gps;
    /** 坐标点集合 */
    private List<BaiduCoord> baiduCoords;
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getProvid() {
        return provid;
    }
    public void setProvid(String provid) {
        this.provid = provid;
    }
    public String getCityid() {
        return cityid;
    }
    public void setCityid(String cityid) {
        this.cityid = cityid;
    }
    public String getGps() {
        return gps;
    }
    public void setGps(String gps) {
        this.gps = gps;
    }
    public List<BaiduCoord> getBaiduCoords() {
        return baiduCoords;
    }
    public void setBaiduCoords(List<BaiduCoord> baiduCoords) {
        this.baiduCoords = baiduCoords;
    }

}
