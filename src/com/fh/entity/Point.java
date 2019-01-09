 package com.fh.entity;

 /**
  * Created by rcc on 2018/1/18.
  */
 //经纬度实体类
 public class Point {
     private double lng;
     private double lat;

     public Point(double lng, double lat) {
		super();
		this.lng = lng;
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

     public double getLat() {
         return lat;
     }

     public void setLat(double lat) {
         this.lat = lat;
     }

	@Override
	public String toString() {
		return "Point [lng=" + this.lng + ", lat=" + this.lat + "]";
	}
	
	/**
	 * @desc 返回实体
	 * @auther zhangjj
	 * @date 2018年7月10日
	 */
	public String toEntity() {
		return "{'lng':'"+ this.lng +"','lat':'"+ this.lat +"'}";
	}
     
 }
