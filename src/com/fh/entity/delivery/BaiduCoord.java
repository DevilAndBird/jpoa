package com.fh.entity.delivery;

/**
 * 百度原坐标
 */
public class BaiduCoord {
   
    /** 经度 */
    private String lng;
    /** 纬度 */
    private String lat;
    /** 纬度 */
    private String regionColor;

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getRegionColor() {
		return regionColor;
	}

	public void setRegionColor(String regionColor) {
		this.regionColor = regionColor;
	}

	@Override
    public String toString() {
        return "BaiduCoord [lng=" + lng + ", lat=" + lat + "]";
    }
}
