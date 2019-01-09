package com.fh.test.app.h5;

import java.io.FileNotFoundException;
import java.util.Map;

import com.fh.common.constant_enum.MAILING_WAY;
import com.fh.common.constant_enum.ORDER_ADDRESS_TYPE;
import com.fh.common.constant_enum.ORDER_CHANNEL;
import com.fh.entity.app.AppRequestBean;
import com.fh.entity.h5.H5OrderAddress;
import com.fh.entity.h5.H5OrderInfo;
import com.fh.entity.h5.H5OrderInsure;
import com.fh.entity.h5.H5OrderMain;
import com.fh.entity.h5.H5OrderNotesInfo;
import com.fh.entity.h5.H5OrderSenderReceiver;
import com.fh.util.HttpClientUtil;
import com.fh.util.MD5;
import com.fh.util.excel.ReadExcelUtils;
import com.google.gson.Gson;

public class TestSaveOrder {
//	public final static String url = "http://localhost/jingpei/h5order/h5SaveOrder";
    public final static String url = "http://localhost/jpoa/h5order/h5SaveOrder";//下单
//  public final static String url = "http://47.96.186.145/jpoa/h5order//h5SaveOrder";//下单
	
    //测试下一单
	public static void main(String[] args) {
		AppRequestBean bean = new AppRequestBean();
		bean.setUser( "130354218011" );
		bean.setKey( "admin444" );
		bean.setTimestamp( "" + System.currentTimeMillis() );
		bean.setSign( MD5.md5( "jingpei" + bean.getUser() + bean.getKey() + bean.getTimestamp() ));
		
		H5OrderMain order = new H5OrderMain();
		H5OrderAddress addr = new H5OrderAddress();
		addr.setSrcaddrtype(ORDER_ADDRESS_TYPE.AIRPORTCOUNTER.getValue());
		addr.setSrcprovid( "310000" );
		addr.setSrccityid( "310100" );
		addr.setSrcdistrictid( "310111" );
		addr.setSrcprovname( "上海" );
		addr.setSrccityname( "上海市" );
		addr.setSrcdistrictname( "普陀" );
		addr.setSrcgps("{'lng':'121.32707','lat':'31.200373'}");
		addr.setSrcaddress("虹桥机场T2航站楼到达层28号行李转盘对面");
		addr.setDestaddrtype(ORDER_ADDRESS_TYPE.AIRPORTCOUNTER.getValue());
		addr.setDestprovid( "310000" );
		addr.setDestcityid( "310100" );
		addr.setDestdistrictid( "310112" );
		addr.setDestprovname( "上海" );
		addr.setDestcityname( "上海市" );
		addr.setDestdistrictname( "浦东新区" );
		addr.setDestaddress( "浦东机场T2航站楼出发层27号门外" );
		addr.setScrlandmark("浦东机场T2航站楼出发层27号门外");
		addr.setDestgps("{'lng':'121.32707','lat':'31.200373'}");
		addr.setSrcistransitgps(false);
		addr.setDestistransitgps(false);
		addr.setDestlandmark("浦东国际机场");
		order.setOrderAddr( addr );
		
		H5OrderInsure insure = new H5OrderInsure();
		insure.setInsureCode( "B003" );
		order.setOrderInsure( insure );
		
		H5OrderInfo orderInfo = new H5OrderInfo();
		orderInfo.setChannel(ORDER_CHANNEL.WINXIN_GS.getValue());
		orderInfo.setCusid( "4" );
		orderInfo.setCutmoney( (float)0 );
		orderInfo.setTotalmoney( (float)89 );
		orderInfo.setActualmoney( (float)89 );
		orderInfo.setNum( 1 );
		orderInfo.setFlightno( "CZ9909" );
		orderInfo.setMailingway(MAILING_WAY.ONESELF.getValue());
		orderInfo.setBackway(MAILING_WAY.ONESELF.getValue());
		orderInfo.setTaketime("2018-04-18 15:00:00");
		orderInfo.setSendtime("2018-04-18 19:00:00");
		order.setOrderMain( orderInfo );
		
		H5OrderSenderReceiver sr = new H5OrderSenderReceiver();
		sr.setSendername( "好帅才" );
		sr.setReceiveridno( "442401199401130000" );
		sr.setReceivername( "好帅才" );
		sr.setReceiverphone( "18000000000" );
		sr.setSenderidno( "442401199401130000" );
		sr.setSenderphone( "18000000000" );
		sr.setIsreceiverself( 1 );
		order.setSenderReceiver( sr );
		
		H5OrderNotesInfo h5OrderNotesInfo = new H5OrderNotesInfo();
		h5OrderNotesInfo.setAddusername("张桑");
		h5OrderNotesInfo.setNotes("黑色的箱子");
		order.setH5OrderNotesInfo(h5OrderNotesInfo);
		
		bean.setData( new Gson().toJson( order ) );
		
	
		
		Gson gson = new Gson();
		String json = gson.toJson( bean );
		
		System.out.println( json );
		try{
			String res = HttpClientUtil.doPostJson( url , json );
			System.out.println( res );
		}catch( Exception ex )
		{
			ex.printStackTrace();
		}
	}
    
    //excel批量下单
    public static void main1(String[] args) {  
        try {  
        	
        	AppRequestBean bean = new AppRequestBean();
    		bean.setUser( "130354218011" );
    		bean.setKey( "admin444" );
    		bean.setTimestamp( "" + System.currentTimeMillis() );
    		bean.setSign( MD5.md5( "jingpei" + bean.getUser() + bean.getKey() + bean.getTimestamp() ));

//    		String filepath = "E:酒店到酒店.xls";
//    		String filepath = "E:虹桥到浦东.xls"; 
    		//String filepath = "E:酒店到虹qiao.xls"; 
//    		String filepath = "E:虹桥到酒店.xls";  
//    		String filepath = "E:浦东到酒店.xls";  
    		String filepath = "E:虹桥到浦东.xlsx"; 
    		//String filepath = "E:浦东到虹桥.xls"; 
//    		String filepath = "E:酒店到酒dian.xls"; 
    		//String filepath = "E:酒店到虹qiao.xls"; 
    		//String filepath = "E:酒店到浦dong.xls"; 
    		
            ReadExcelUtils excelReader = new ReadExcelUtils(filepath);  
            // 对读取Excel表格标题测试  
//          String[] title = excelReader.readExcelTitle();  
//          System.out.println("获得Excel表格的标题:");  
//          for (String s : title) {  
//              System.out.print(s + " ");  
//          }  
              
            // 对读取Excel表格内容测试  
            Map<Integer, Map<Integer,Object>> map = excelReader.readExcelContent();  
            System.out.println("获得Excel表格的内容:");  
            for (int i = 1; i <= map.size(); i++) {  
            	
                H5OrderMain h5OrderMain = new H5OrderMain();
                
                H5OrderInfo h5OrderInfo = new H5OrderInfo();
                h5OrderInfo.setCusid("208");
                //h5OrderInfo.setType("HOTELTOHOTEL");
                //h5OrderInfo.setType("HOTELTOAIRPORTCOUNTER");
                //h5OrderInfo.setType("AIRPORTCOUNTERTOHOTEL");
                h5OrderInfo.setType("AIRPORTCOUNTERTOHOTEL");
                
                h5OrderInfo.setChannel("weixin_gs");
                h5OrderInfo.setNum(1);
                h5OrderInfo.setMailingway("ONESELF");
                h5OrderInfo.setBackway("ONESELF");
                h5OrderInfo.setTotalmoney(28f);
                h5OrderInfo.setCutmoney(20f);
                h5OrderInfo.setActualmoney(8f);
                
                
                h5OrderInfo.setTaketime("2018-09-17 "+map.get(i).get(0).toString());
                h5OrderInfo.setSendtime("2018-09-17 "+map.get(i).get(1).toString());
            	
                H5OrderAddress h5OrderAddress = new H5OrderAddress();
                h5OrderAddress.setScrlandmark(map.get(i).get(2).toString());
                h5OrderAddress.setSrcaddress(map.get(i).get(3).toString());
                h5OrderAddress.setDestlandmark(map.get(i).get(4).toString());
                h5OrderAddress.setDestaddress(map.get(i).get(5).toString());
                
                //h5OrderAddress.setSrcaddrtype("HOTEL");
                h5OrderAddress.setSrcaddrtype("AIRPORTCOUNTER");
                //h5OrderAddress.setDestaddrtype("HOTEL");
                h5OrderAddress.setDestaddrtype(ORDER_ADDRESS_TYPE.HOTEL.getValue());
                
                //若果是机场
                h5OrderAddress.setSrcaddressid(1);
                //h5OrderAddress.setSrcaddressid(2);
                
                //h5OrderAddress.setDestaddressid(1);
                h5OrderAddress.setDestaddressid(2);
                
                h5OrderAddress.setSrcprovid("310000");
                h5OrderAddress.setSrccityid("310000");
                h5OrderAddress.setDestprovid("310000");
                h5OrderAddress.setDestcityid("310000");
                h5OrderAddress.setSrcprovname("上海");
                h5OrderAddress.setSrccityname("上海市");
                h5OrderAddress.setDestprovname("上海");
                h5OrderAddress.setDestcityname("上海市");
                h5OrderAddress.setSrcistransitgps(false);
                h5OrderAddress.setDestistransitgps(false);
                //h5OrderAddress.setScrlandmark("酒/宅");
                h5OrderAddress.setScrlandmark("机场");
                //h5OrderAddress.setDestlandmark("酒/宅");
                h5OrderAddress.setDestlandmark("机场");
                
                H5OrderInsure h5OrderInsure = new H5OrderInsure();
                h5OrderInsure.setInsureCode("默认");
                
                H5OrderSenderReceiver h5OrderSenderReceiver = new H5OrderSenderReceiver();
                h5OrderSenderReceiver.setSendername("王明");
                h5OrderSenderReceiver.setSenderphone("17749768456");
                h5OrderSenderReceiver.setSenderidno("320811199212023527");
                h5OrderSenderReceiver.setReceivername("王明");
                h5OrderSenderReceiver.setReceiverphone("17749768456");
                h5OrderSenderReceiver.setReceiveridno("320811199212023527");
                h5OrderSenderReceiver.setRealname("王明");
                h5OrderSenderReceiver.setRealphone("17749768456");
                
                
                H5OrderNotesInfo h5OrderNotesInfo = new H5OrderNotesInfo();
                h5OrderNotesInfo.setAddusername("张伟");
                h5OrderNotesInfo.setNotes("小心轻放");
                
                h5OrderMain.setOrderMain(h5OrderInfo);
                h5OrderMain.setOrderAddr(h5OrderAddress);
                h5OrderMain.setOrderInsure(h5OrderInsure);
                h5OrderMain.setSenderReceiver(h5OrderSenderReceiver);
                h5OrderMain.setH5OrderNotesInfo(h5OrderNotesInfo);
                bean.setData( new Gson().toJson( h5OrderMain ) );
            	
        		Gson gson = new Gson();
        		String json = gson.toJson( bean );
        		
        		System.out.println( json );
        		try{
        			String res = HttpClientUtil.doPostJson( url , json );
        			System.out.println( res );
        		}catch( Exception ex )
        		{
        			ex.printStackTrace();
        		}
            }  
        } catch (FileNotFoundException e) {  
            System.out.println("未找到指定路径的文件!");  
            e.printStackTrace();  
        }catch (Exception e) {  
            e.printStackTrace();  
        }  
    }     
    
}
