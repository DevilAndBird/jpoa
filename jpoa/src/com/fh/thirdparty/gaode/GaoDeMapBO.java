package com.fh.thirdparty.gaode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fh.entity.Point;
import com.fh.entity.delivery.BaiduCoord;
import com.fh.util.LoggerUtil;
import com.fh.util.MapUtil;

/**
 * Created by rcc on 2018/1/17.
 */

public class GaoDeMapBO {
	static double bd_lon = 0.0;
	static double bd_lat = 0.0;

	public static BaiduCoord getLngAndLat(String address) throws UnsupportedEncodingException {
		BaiduCoord baiduCoord = new BaiduCoord();
		address = URLEncoder.encode(address,"UTF-8");//高德地图必须要URLEncoder转换
		String city = URLEncoder.encode("上海","UTF-8");//高德地图必须要URLEncoder转换
		String url = "http://restapi.amap.com/v3/geocode/geo?address="+address+"&output=JSON&key=e67da935e5e104d78a830519b619bf10&city="+city;
		String json = loadJSON(url);
		if (json == null || "".equals(json)) {
			LoggerUtil.warn("获取地址坐标异常，请检查地址：" + address);
			return baiduCoord;
		}
		try {
			JsonNode jsonNode = new com.fasterxml.jackson.databind.ObjectMapper().readTree(json);
			if(jsonNode.findValue("status").textValue().equals("1")) {
                JsonNode listSource = jsonNode.findValue("location");
                String[] location = listSource.textValue().split(",");
                bd_lon = Double.parseDouble(location[0]);
                bd_lat = Double.parseDouble(location[1]);
                Point point = new Point(bd_lon,bd_lat);
                baiduCoord = MapUtil.transGDLocation(point);
        }
		} catch (Exception e) {
			e.printStackTrace();
		}
       return baiduCoord;
	}


	public static String loadJSON(String url) {
		StringBuilder json = new StringBuilder();
		try {
			URL oracle = new URL(url);
			URLConnection yc = oracle.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					yc.getInputStream(), "UTF-8"));
			String inputLine = null;
			while ((inputLine = in.readLine()) != null) {
				json.append(inputLine);
			}
			in.close();
		} catch (MalformedURLException e) {
		} catch (IOException e) {
		}
		return json.toString();
	}
	
	public static void main(String[] args) {
		BaiduCoord lngAndLat =null;
		try {
			lngAndLat = GaoDeMapBO.getLngAndLat("上海市上海凯宾斯基大酒店");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		System.out.println(lngAndLat);
	}
	
}