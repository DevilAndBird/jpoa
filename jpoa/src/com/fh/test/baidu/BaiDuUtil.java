package com.fh.test.baidu;

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

import com.fh.util.PageData;

public class BaiDuUtil {

	/**
	 * @desc 给出坐标
	 * @auther zhangjj
	 * @date 2018年4月13日
	 */
	public Object[] getCoordinate(String addr) throws IOException {
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
		BaiDuUtil getLatAndLngByBaidu = new BaiDuUtil();
		Object[] o = getLatAndLngByBaidu.getCoordinate("上海市普陀区长寿路189号");
		System.out.println(o[0]);// 经度
		System.out.println(o[1]);// 纬度
	}
	
//	public static void main(String[] args) {  
//        // 被检测的经纬度点  
//        PageData orderLocation = new PageData();  
//        orderLocation.put("lng", "121.248382");  
//        orderLocation.put("lat", "31.253047");  
//        
//        String a = "[{'lng':'121.248382','lat':'31.253047'},{'lng':'121.30573','lat':'31.257369'},{'lng':'121.278881','lat':'31.237144'},{'lng':'121.269797','lat':'31.209571'}]";
//        JSONArray jsonArray = JSONArray.fromObject(a); 
//        List<PageData> partitionLocation = (List<PageData>) JSONArray.toCollection(jsonArray, PageData.class);
//        // 商业区域（百度多边形区域经纬度集合）  
//        System.out.println(isInPolygon(orderLocation, partitionLocation));  
//    }  
      
     /**   
     * 判断当前位置是否在多边形区域内   
     * @param orderLocation 当前点  
     * @param partitionLocation 区域顶点  
     * @return   
     */    
    public static boolean isInPolygon(PageData orderLocation, List<PageData> partitionLocation){    
          
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
