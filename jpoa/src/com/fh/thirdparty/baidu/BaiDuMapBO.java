package com.fh.thirdparty.baidu;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import net.sf.json.JSONObject;

import com.fh.entity.delivery.BaiduCoord;
import com.fh.util.LoggerUtil;
import com.fh.util.PageData;

@Component
public class BaiDuMapBO {
	@Value("${BAIDU_SERVICE}")
    private String BAIDU_SERVICE = "";
	
	public  BaiduCoord getLngAndLat(String address) throws Exception{
		BaiduCoord baiduCoord = new BaiduCoord();
		
		try{
			// 中间空格替换
			address = address.replace("null", "").replace(" ", "");
			
			String url = "http://api.map.baidu.com/geocoder/v2/?address="+address+"&output=json&ak=" + BAIDU_SERVICE;
	        String json = loadJSON(url);     
	       
	        // 是否解析出百度地图坐标
	        if (json == null || "".equals(json)) {
	        	LoggerUtil.warn("获取地址坐标异常，请检查地址：" + address);
	        	return baiduCoord;
			}
	        
	        JSONObject obj = JSONObject.fromObject(json);
	        
	        if(!(obj.get("status").toString().equals("0"))){
	        	return baiduCoord;
	        }
	        	
	        double lng= obj.getJSONObject("result").getJSONObject("location").getDouble("lng"); 
	        double lat= obj.getJSONObject("result").getJSONObject("location").getDouble("lat");
	        baiduCoord.setLng(lng+"");
	        baiduCoord.setLat(lat+"");
		} catch(Exception e) {
			LoggerUtil.error("获取地址坐标异常:", e);
		}
		return baiduCoord;
    }
	
	public  BaiduCoord getGpsByAddr(String address){
		BaiduCoord baiduCoord = new BaiduCoord();
		
		try{
			// 中间空格替换
			address = address.replace("null", "").replace(" ", "").replace("\n", "");
			String url = "http://api.map.baidu.com/geocoder/v2/?address="+address+"&output=json&ak=" + BAIDU_SERVICE;
	        String json = loadJSON(url);     
	       
	        // 是否解析出百度地图坐标
	        if (json == null || "".equals(json)) {
	        	LoggerUtil.warn("获取地址坐标异常，请检查地址：" + address);
	        	return baiduCoord;
			}
	        
	        JSONObject obj = JSONObject.fromObject(json);
	        
	        if(!(obj.get("status").toString().equals("0"))){
	        	return baiduCoord;
	        }
	        	
	        double lng= obj.getJSONObject("result").getJSONObject("location").getDouble("lng"); 
	        double lat= obj.getJSONObject("result").getJSONObject("location").getDouble("lat");
	        baiduCoord.setLng(lng+"");
	        baiduCoord.setLat(lat+"");
		} catch(Exception e) {
			LoggerUtil.error("获取地址坐标异常:", e);
		}
		return baiduCoord;
    }

    public String loadJSON (String url) {
        StringBuilder json = new StringBuilder();
        try {
            URL oracle = new URL(url);
            URLConnection yc = oracle.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream(), "UTF-8"));
            String inputLine = null;
            while ( (inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }
            in.close();
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }
        return json.toString();
    }

	/**
	 * @desc 根据地址返回坐标
	 * @auther zhangjj
	 * @date 2018年4月13日
	 */
	public static Object[] getCoordinate(String addr) throws IOException {
		String lng = null;// 经度
		String lat = null;// 纬度
		String address = null;
		try {
			address = java.net.URLEncoder.encode(addr, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String key = "YNMESw4kbfPi37cqEuUaASKh9fUi8UWn";
		String url = String.format("http://api.map.baidu.com/geocoder?address=%s&output=json&key=%s",address, key);
		URL myURL = null;
		URLConnection httpsConn = null;
		try {
			myURL = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		InputStreamReader insr = null;
		BufferedReader br = null;
		try {
			httpsConn = (URLConnection) myURL.openConnection();// 不使用代理
			if (httpsConn != null) {
				insr = new InputStreamReader(httpsConn.getInputStream(),"UTF-8");
				br = new BufferedReader(insr);
				String data = null;
				int count = 1;
				while ((data = br.readLine()) != null) {
					if (count == 5) {
						lng = (String) data.subSequence(data.indexOf(":") + 1,data.indexOf(","));// 经度
						count++;
					} else if (count == 6) {
						lat = data.substring(data.indexOf(":") + 1);// 纬度
						count++;
					} else {
						count++;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (insr != null) {
				insr.close();
			}
			if (br != null) {
				br.close();
			}
		}
		return new Object[] { lng, lat };
	}

	public static void main(String[] args) throws IOException {
//		System.out.println(BaiDuMapBO.getDistanceFromTwoPoints(29.490295,106.486654, 29.615467, 106.581515));
		
	}
	
     /**   
     * 判断当前位置是否在多边形区域内   
     * @param orderLocation 当前点  
     * @param partitionLocation 区域顶点  
     * @return   
     */    
    public boolean isInPolygon(PageData orderLocation, List<PageData> partitionLocation){    
          
        double p_x =Double.parseDouble(orderLocation.getString("lng"));    
        double p_y =Double.parseDouble(orderLocation.getString("lat"));    
        Point2D.Double point = new Point2D.Double(p_x, p_y);    
   
        List<Point2D.Double> pointList= new ArrayList<Point2D.Double>();    
        for (PageData obj : partitionLocation) {
        	 double polygonPoint_x=Double.parseDouble((String)obj.get("lng"));    
             double polygonPoint_y=Double.parseDouble((String)obj.get("lat"));    
             Point2D.Double polygonPoint = new Point2D.Double(polygonPoint_x,polygonPoint_y);    
             pointList.add(polygonPoint);  
		}
        
        return IsPtInPoly(point,pointList);    
    }
    
    /**   
    * 判断当前区域是否在多边形区域内   
    * @param orderLocation 当前点  
    * @param partitionLocation 区域顶点  
    * @return   
    */    
   public static boolean checkContrainRegion(List<PageData> dmanregion, List<PageData> partitionLocation){    
       Boolean returnFlag = true;   
	   for (PageData dmanGps : dmanregion) {
		   double p_x =Double.parseDouble(dmanGps.getString("lng"));    
		   double p_y =Double.parseDouble(dmanGps.getString("lat"));    
		   Point2D.Double point = new Point2D.Double(p_x, p_y);    
		   List<Point2D.Double> pointList= new ArrayList<Point2D.Double>();    
		   for (PageData obj : partitionLocation) {
			   double polygonPoint_x=Double.parseDouble((String)obj.get("lng"));    
			   double polygonPoint_y=Double.parseDouble((String)obj.get("lat"));    
			   Point2D.Double polygonPoint = new Point2D.Double(polygonPoint_x,polygonPoint_y);    
			   pointList.add(polygonPoint);  
		   }
		if(!IsPtInPoly(point,pointList)){
			returnFlag = false;
			break;//终端循环，该区域不在集散中心内
		}
	}
       return returnFlag ;    
   }    
    
    /**   
     * 返回一个点是否在一个多边形区域内， 如果点位于多边形的顶点或边上，不算做点在多边形内，返回false  
     * @param point   
     * @param polygon   
     * @return   
     */    
    public static boolean checkWithJdkGeneralPath(Point2D.Double point, List<Point2D.Double> polygon) {    
        java.awt.geom.GeneralPath p = new java.awt.geom.GeneralPath();    
        Point2D.Double first = polygon.get(0);    
        p.moveTo(first.x, first.y);    
        polygon.remove(0);    
        for (Point2D.Double d : polygon) {    
           p.lineTo(d.x, d.y);    
        }    
        p.lineTo(first.x, first.y);    
        p.closePath();    
        return p.contains(point);    
   }    
    
    
    private static final Double PI = Math.PI;  
  
    private static final Double PK = 180 / PI;  
      
   /**
    * 百度地图算距离
    * @param lat_a
    * @param lng_a
    * @param lat_b
    * @param lng_b
    * @return
    */
    public static double getDistanceFromTwoPoints(double lat_a, double lng_a, double lat_b, double lng_b) {  
        double t1 = Math.cos(lat_a / PK) * Math.cos(lng_a / PK) * Math.cos(lat_b / PK) * Math.cos(lng_b / PK);  
        double t2 = Math.cos(lat_a / PK) * Math.sin(lng_a / PK) * Math.cos(lat_b / PK) * Math.sin(lng_b / PK);  
        double t3 = Math.sin(lat_a / PK) * Math.sin(lat_b / PK);  
        double tt = Math.acos(t1 + t2 + t3);  
        return 6366000 * tt;  
    }  
  
   /**   
    * 判断点是否在多边形内，如果点位于多边形的顶点或边上，也算做点在多边形内，直接返回true  
    * @param point 检测点   
    * @param pts   多边形的顶点   
    * @return      点在多边形内返回true,否则返回false   
    */    
   public static boolean IsPtInPoly(Point2D.Double point, List<Point2D.Double> pts){    
           
       int N = pts.size();    
       boolean boundOrVertex = true; //如果点位于多边形的顶点或边上，也算做点在多边形内，直接返回true    
       int intersectCount = 0;//cross points count of x     
       double precision = 2e-10; //浮点类型计算时候与0比较时候的容差    
       Point2D.Double p1, p2;//neighbour bound vertices    
       Point2D.Double p = point; //当前点    
           
       p1 = pts.get(0);//left vertex            
       for(int i = 1; i <= N; ++i){//check all rays                
           if(p.equals(p1)){    
               return boundOrVertex;//p is an vertex    
           }    
               
           p2 = pts.get(i % N);//right vertex                
           if(p.x < Math.min(p1.x, p2.x) || p.x > Math.max(p1.x, p2.x)){//ray is outside of our interests                    
               p1 = p2;     
               continue;//next ray left point    
           }    
               
           if(p.x > Math.min(p1.x, p2.x) && p.x < Math.max(p1.x, p2.x)){//ray is crossing over by the algorithm (common part of)    
               if(p.y <= Math.max(p1.y, p2.y)){//x is before of ray                        
                   if(p1.x == p2.x && p.y >= Math.min(p1.y, p2.y)){//overlies on a horizontal ray    
                       return boundOrVertex;    
                   }    
                       
                   if(p1.y == p2.y){//ray is vertical                            
                       if(p1.y == p.y){//overlies on a vertical ray    
                           return boundOrVertex;    
                       }else{//before ray    
                           ++intersectCount;    
                       }     
                   }else{//cross point on the left side                            
                       double xinters = (p.x - p1.x) * (p2.y - p1.y) / (p2.x - p1.x) + p1.y;//cross point of y                            
                       if(Math.abs(p.y - xinters) < precision){//overlies on a ray    
                           return boundOrVertex;    
                       }    
                           
                       if(p.y < xinters){//before ray    
                           ++intersectCount;    
                       }     
                   }    
               }    
           }else{//special case when ray is crossing through the vertex                    
               if(p.x == p2.x && p.y <= p2.y){//p crossing over p2                        
                   Point2D.Double p3 = pts.get((i+1) % N); //next vertex                        
                   if(p.x >= Math.min(p1.x, p3.x) && p.x <= Math.max(p1.x, p3.x)){//p.x lies between p1.x & p3.x    
                       ++intersectCount;    
                   }else{    
                       intersectCount += 2;    
                   }    
               }    
           }                
           p1 = p2;//next ray left point    
       }    
           
       if(intersectCount % 2 == 0){//偶数在多边形外    
           return false;    
       } else { //奇数在多边形内    
           return true;    
       }    
   }    
   
}
