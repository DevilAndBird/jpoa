package com.fh.util;

import static java.lang.Math.sqrt;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.fh.entity.Point;
import com.fh.entity.delivery.BaiduCoord;

public class MapUtil {  
	  
    private static double EARTH_RADIUS = 6378.137;  
    
    private static double x_PI = 3.14159265358979324 * 3000.0 / 180.0;
	private static double PI = 3.1415926535897932384626;

	static double bd_lon = 0.0;
	static double bd_lat = 0.0;
  
    private static double rad(double d) {  
        return d * Math.PI / 180.0;  
    }
    
    /**
     * 根据两个位置的经纬度，来计算两地的距离（单位为KM）
     * 参数为String类型
     * @param lat1 用户经度
     * @param lng1 用户纬度
     * @param lat2 商家经度
     * @param lng2 商家纬度
     * @return
     */
    public static String getDistance(String lat1Str, String lng1Str, String lat2Str, String lng2Str) {
    	Double lat1 = Double.parseDouble(lat1Str);
    	Double lng1 = Double.parseDouble(lng1Str);
    	Double lat2 = Double.parseDouble(lat2Str);
    	Double lng2 = Double.parseDouble(lng2Str);
    	double patm = 2;
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double difference = radLat1 - radLat2;
        double mdifference = rad(lng1) - rad(lng2);
        double distance = patm * Math.asin(Math.sqrt(Math.pow(Math.sin(difference / patm), patm)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(mdifference / patm), patm)));
        distance = distance * EARTH_RADIUS;
        String distanceStr = String.valueOf(distance);
        return distanceStr;
    }
    
    /**
	 * 获取当前用户一定距离以内的经纬度值
	 * 单位米 return minLat 
	 * 最小经度 minLng 
	 * 最小纬度 maxLat 
	 * 最大经度 maxLng 
	 * 最大纬度 minLat
	 */
	@SuppressWarnings("all")
	public static Map getAround(String latStr, String lngStr, String raidus) {
		Map map = new HashMap();
		
		Double latitude = Double.parseDouble(latStr);// 传值给经度
		Double longitude = Double.parseDouble(lngStr);// 传值给纬度

		Double degree = (24901 * 1609) / 360.0; // 获取每度
		double raidusMile = Double.parseDouble(raidus);
		
		Double mpdLng = Double.parseDouble((degree * Math.cos(latitude * (Math.PI / 180))+"").replace("-", ""));
		Double dpmLng = 1 / mpdLng;
		Double radiusLng = dpmLng * raidusMile;
		//获取最小经度
		Double minLat = longitude - radiusLng;
		// 获取最大经度
		Double maxLat = longitude + radiusLng;
		
		Double dpmLat = 1 / degree;
		Double radiusLat = dpmLat * raidusMile;
		// 获取最小纬度
		Double minLng = latitude - radiusLat;
		// 获取最大纬度
		Double maxLng = latitude + radiusLat;
		
		map.put("minLat", minLat+"");
		map.put("maxLat", maxLat+"");
		map.put("minLng", minLng+"");
		map.put("maxLng", maxLng+"");
		
		return map;
	}
	
	/**
	 * 
	 * @Title: transGDLocation
	 * @Description: 将火星坐标转变成百度坐标
	 * author：tangqm
	 * 2018年6月21日
	 * @param point
	 * @return
	 */
	public static BaiduCoord transGDLocation(Point point){
	      BaiduCoord baiduCoord = new BaiduCoord();
          Point baiduPoint = GD_TRANS_BD(point);
          baiduCoord.setLat(baiduPoint.getLat()+"");
          baiduCoord.setLng(baiduPoint.getLng()+"");
          return baiduCoord;
	}
	
	 /**
	  * 
	  * @Title: transBDLocation
	  * @Description: 将百度坐标转变成火星坐标
	  * author：tangqm
	  * 2018年6月21日
	  * @param point
	  * @return
	  */
    static Point transBDLocation(Point point)  
    {  
        double x = point.getLng() - 0.0065, y = point.getLat() - 0.006;  
        double z = sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_PI);  
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_PI);  
        return new Point( dataDigit(6,z * Math.cos(theta)),dataDigit(6,z * Math.sin(theta)));  
    }  
	
	/**
	 * 对double类型数据保留小数点后多少位 高德地图转码返回的就是 小数点后6位，为了统一封装一下
	 * 
	 * @param digit
	 *            位数
	 * @param in
	 *            输入
	 * @return 保留小数位后的数
	 */
	static double dataDigit(int digit, double in) {
		return new BigDecimal(in).setScale(6, BigDecimal.ROUND_HALF_UP)
				.doubleValue();
	}

	/**
	 * 将火星坐标转变成百度坐标
	 */
	public static Point GD_TRANS_BD(Point point) {
		double x = point.getLng(), y = point.getLat();
		double z = sqrt(x * x + y * y) + 0.00002 * Math.sin(y *x_PI);
		double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_PI);
		return new Point(dataDigit(6, z * Math.cos(theta) + 0.0065), dataDigit(
				6, z * Math.sin(theta) + 0.006));
	}
    
    public static void main(String[] args) {
    	System.out.println(transBDLocation(new Point(121.510806,31.248178)));
	}
    
}
