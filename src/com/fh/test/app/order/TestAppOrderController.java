package com.fh.test.app.order;


import com.fh.common.constant_enum.*;
import com.fh.entity.app.AppRequestBean;
import com.fh.entity.app.order.AppSaveOrderInfoReqData;
import com.fh.entity.app.order.OrderBaggageReqData;
import com.fh.entity.customer.CusInfo;
import com.fh.entity.order.*;
import com.fh.util.HttpClientUtil;
import com.fh.util.MD5;
import com.google.gson.Gson;

import java.util.ArrayList;

public class TestAppOrderController {
    // 订单列表查询 zhangjj
//    public final static String url = "http://localhost:8080/jpoa/apporder/findAppOrderMainList";
//    public final static String url = "http://localhost:8080/jpoa/apporder/findAppAllOrder";
    // 订单详情查询 zhangjj
//    public final static String url = "http://localhost:8080/jpoa/apporder/findAppOrderDetails";
//    public final static String url = "http://localhost:8080/jpoa/apporder/updateOrderStatus";
//    public final static String url = "http://localhost:8080/jpoa/apporder/countOrderBaggageNum";
//    public final static String url = "http://localhost:8080/jpoa/apporder/countBatchBagNumByQR";
//    public final static String url = "http://localhost:8080/jpoa/apporder/saveSignUrl";//tangqm
    public final static String url = "http://localhost:8081/jpoa/apporder/saveorder";// app 柜台下单保存信息
//    public final static String url = "http://localhost:8080/jpoa/apporder/updateprepaid";// app 订单状态修改为已支付
//    public final static String url = "http://localhost:8080/jpoa/appCounterService/findCountersByCity";// app 柜台信息查询
//	public final static String url = "http://localhost:8081/jpoa/apporder/findPricingRule";// app 计费价格回复
//	public final static String url = "http://localhost:8080/jpoa/apporder/findAppOrderAirport";// app 柜台订单列表查询
//	public final static String url = "http://localhost:8080/jpoa/apporder/deleteOrder";// app 删除订单
	
    
    
    public static void main(String[] args) {
        AppRequestBean bean = new AppRequestBean();
        bean.setUser( "130354218011" );
        bean.setKey( "admin444" );
        bean.setTimestamp( "" + System.currentTimeMillis() );
        bean.setSign( MD5.md5( "jingpei" + bean.getUser() + bean.getKey() + bean.getTimestamp() ));

//        AppOrderReqData data = new AppOrderReqData();
//        data.setId(6);
//        data.setSignurl("https://baike.baidu.com/item/%E7%AD%BE%E5%AD%97/7372132?fr=aladdin");
//        List<String> ordernoList = new ArrayList<String>();
//        ordernoList.add("1232344");
//        ordernoList.add("1232345");
//        data.setOrdernoList(ordernoList);
//        
//        CountBaggageNumReqData data = new CountBaggageNumReqData();
//        data.setBaggageid("1234567");
//        data.setRoletype("SERVICE_CENTER");
        
//        data.setOrderno("1232345");
//        data.setType(ORDER_TYPE.AIRPORTCOUNTER_TO_HOTEL.getValue());
//        data.setStatus(ORDER_STATUS.WAITTRUCELOADING.getValue());
//        data.setNamespellinitial("ls");
//        data.setRoleid(14);
//        data.setRoletype("ROLE_SENDER");
//        data.setStatus(ORDER_STATUS.TAKEGOOGSOVER.getValue());
        
        // 订单列表查询
//        AppOrderReqData data = new AppOrderReqData();
//        data.setType(ORDER_TYPE.HOTEL_TO_AIRPORT.getValue());
//        data.setStatus(ORDER_STATUS.PREPAID.getValue());
//        data.setRoleid(70);
//        data.setRoletype(ROLE_TYPE.ROLE_AIRPORT_SEND.getValue());
//        data.setIsfinish(IS_FINISH.UNFINISHED.getValue());
//        data.setDesttype(DESTINSATION_TYPE.SERVICECERTER.getValue());
//        data.setDestaddress("1");
//        data.setNamespellinitial("WX");
        
        // 订单详情查询
//        AppOrderDetailsReqData data = new AppOrderDetailsReqData();
//        data.setOrderid(3541);
//        data.setOrderno("JPWX20181025171245564");
//        data.setFetchcode("EEEEEEE");
//        List<String> arrayList = new ArrayList<String>();
//        arrayList.add(QUERY_ORERYDETAILS_TYPE.ORDERADDRESS.getValue());
//        arrayList.add(QUERY_ORERYDETAILS_TYPE.ORDERBAGAGE.getValue());
//        arrayList.add(QUERY_ORERYDETAILS_TYPE.ORDERCUS.getValue());
//        arrayList.add(QUERY_ORERYDETAILS_TYPE.ORDERFLIGHT.getValue());
//        arrayList.add(QUERY_ORERYDETAILS_TYPE.ORDERNOTES.getValue());
//        arrayList.add(QUERY_ORERYDETAILS_TYPE.ORDERDMAN.getValue());
//        arrayList.add(QUERY_ORERYDETAILS_TYPE.ORDER_PRICE_DETAIL.getValue());
//        data.setQueryDetailsType(arrayList);
        
//        List<OrderRole> orderRoleList = new ArrayList<OrderRole>();
//        OrderRole orderRole1 = new OrderRole();
//        orderRole1.setRoletype(ROLE_TYPE.ROLE_AIRPORT_SEND.getValue());
//        orderRole1.setIsfinish(IS_FINISH.UNFINISHED.getValue());
//        orderRoleList.add(orderRole1);
//        
//        OrderRole orderRole2 = new OrderRole();
//        orderRole2.setRoletype(ROLE_TYPE.ROLE_HOTEL_SEND.getValue());
//        orderRole2.setIsfinish(IS_FINISH.UNFINISHED.getValue());
//        orderRoleList.add(orderRole2);
//        data.setOrderRoleList(orderRoleList);
        
        // 订单状态更改
//        AppOrderReqData data = new AppOrderReqData();
//        data.setStatus(ORDER_STATUS.TRUCELOADING.getValue());
//        List<Integer> orderidList = new ArrayList<Integer>();
//        orderidList.add(1);
//        orderidList.add(2);
//        data.setOrderidList(orderidList);
        
        // QR码查询本次订单行李总数
//        CountBaggageNumReqData data = new CountBaggageNumReqData();
//        data.setBaggageid("1234567");
//        data.setIsfinish(IS_FINISH.ONGOING.getValue());
//        data.setRoletype(ROLE_TYPE.ROLE_AIRPORT_SEND.getValue());
        
        
        // APP 订单生成接口 start
        AppSaveOrderInfoReqData data = new AppSaveOrderInfoReqData();
        CusInfo cusInfo = new CusInfo();
        cusInfo.setName("test5");
        cusInfo.setMobile("12345678904");
        cusInfo.setIdno("342401199401137913");
        data.setCusInfo(cusInfo);
        OrderMainSpec orderMainSpec = new OrderMainSpec();
        orderMainSpec.setTotalmoney(118f);
        orderMainSpec.setCutmoney(0f);
        orderMainSpec.setActualmoney(118f);
        orderMainSpec.setNum(2);
        orderMainSpec.setChannel(ORDER_CHANNEL.APP_SC.getValue());
        orderMainSpec.setMailingway(MAILING_WAY.AIRPOSTCOUNTER.getValue());
        orderMainSpec.setBackway(MAILING_WAY.OTHER.getValue());
        orderMainSpec.setTaketime("2018-10-31 15:30");
        orderMainSpec.setSendtime("2018-11-01 15:30");
        data.setOrderMainSpec(orderMainSpec);
        OrderAddress orderAddress = new OrderAddress();
        orderAddress.setSrcaddrtype(ORDER_ADDRESS_TYPE.AIRPORTCOUNTER.getValue());
        orderAddress.setSrcaddressid(1);
        orderAddress.setScrlandmark("虹桥机场T2航站楼到达层28号行李转盘对面");
        orderAddress.setSrcprovname("上海市");
        orderAddress.setSrcprovid("310000");
        orderAddress.setSrccityname("上海市");
        orderAddress.setSrccityid("310000");
        orderAddress.setSrcgps("{'lng':'121.32707','lat':'31.200373'}");
        orderAddress.setSrcaddress("虹桥机场T2航站楼到达层28号行李转盘对面");
        orderAddress.setDestaddrtype(ORDER_ADDRESS_TYPE.HOTEL.getValue());
//        orderAddress.setDestaddressid(2);
        orderAddress.setDestlandmark("浦东机场");
        orderAddress.setDestprovname("上海市");
        orderAddress.setDestprovid("310000");
        orderAddress.setDestcityname("上海市");
        orderAddress.setDestcityid("310000");
        orderAddress.setDestgps("{'lng':'121.32707','lat':'31.200373'}");
        orderAddress.setDestaddress("浦东机场");
        data.setOrderAddress(orderAddress);
	    OrderFlight orderFlight = new OrderFlight();
	    orderFlight.setTakeflightno("AASDFASDF");
        data.setOrderFlight(orderFlight);
        OrderInsureInfo orderInsureInfo = new OrderInsureInfo();
        orderInsureInfo.setInsurecode("0002");
        data.setOrderInsureInfo(orderInsureInfo);
        OrderNotesInfo orderNotesInfo = new OrderNotesInfo();
        orderNotesInfo.setNotes("app测试接口数据");
        data.setOrderNotesInfo(orderNotesInfo);
        OrderSenderReceiver orderSenderReceiver = new OrderSenderReceiver();
        orderSenderReceiver.setSendername("test5");
        orderSenderReceiver.setSenderphone("12345678901");
        orderSenderReceiver.setSenderidno("342401199401137913");
        orderSenderReceiver.setReceivername("test5");
        orderSenderReceiver.setReceiveridno("342401199401137913");
        orderSenderReceiver.setReceiverphone("12345678901");
        data.setOrderSenderReceiver(orderSenderReceiver);
        OrderPayInfo orderPayInfo = new OrderPayInfo();
        orderPayInfo.setType(ORDER_PAY_TYPE.MONTH.getValue());
        orderPayInfo.setStatus(ORDER_PAY_STUTAS.WAITPAY.getValue());
        data.setOrderPayInfo(orderPayInfo);
        ArrayList<OrderBaggageReqData> orderBaggageReqDataList = new ArrayList<OrderBaggageReqData>();
        OrderBaggageReqData orderBaggageReqData1 = new OrderBaggageReqData();
        orderBaggageReqData1.setImgtype(IMGURL_BUSINESS_TYPE.COOLECT.getValue());
        orderBaggageReqData1.setBaggageid("JPQR855164112");
        orderBaggageReqData1.setUploadUserid(1);
        ArrayList<String> imgurlList = new ArrayList<>();
        imgurlList.add("http://jingpeioss.oss-cn-hangzhou.aliyuncs.com/1534943187.jpg1");
        imgurlList.add("http://jingpeioss.oss-cn-hangzhou.aliyuncs.com/1534943187.jpg2");
        orderBaggageReqData1.setImgurlList(imgurlList);

        OrderBaggageReqData orderBaggageReqData2 = new OrderBaggageReqData();
        orderBaggageReqData2.setImgtype(IMGURL_BUSINESS_TYPE.COOLECT.getValue());
        orderBaggageReqData2.setBaggageid("JPQR987456522");
        orderBaggageReqData2.setUploadUserid(1);
        ArrayList<String> imgurlList2 = new ArrayList<>();
        imgurlList2.add("http://jingpeioss.oss-cn-hangzhou.aliyuncs.com/1534943187.jpg1");
        imgurlList2.add("http://jingpeioss.oss-cn-hangzhou.aliyuncs.com/1534943187.jpg2");
        orderBaggageReqData2.setImgurlList(imgurlList2);
        orderBaggageReqDataList.add(orderBaggageReqData1);
        orderBaggageReqDataList.add(orderBaggageReqData2);
//        data.setOrderBaggageReqDataList(orderBaggageReqDataList);

//         APP 订单生成接口 end
        
        // App 订单状态修改为已经支付 start
   /*     AppUpdatePrepaidReqData data = new AppUpdatePrepaidReqData();
        data.setOrderno("JPWX20181031094630675");*/
        // App 订单状态修改为已经支付 end
        
        // App 查询机场柜台信息 start
      /*  AppCountersReqData data = new AppCountersReqData();
        data.setProvid("330000");
        data.setCityid("330100");*/
        // App 查询机场柜台信息 end
        
        // App 查询机场柜台信息 start
//        PricingRuleReqData data = new PricingRuleReqData();
//        data.setCityid("330000");
        // App 查询机场柜台信息 end
        
        // App 柜台订单列表查询根据状态
        /*AppOrderAirportReqData data = new AppOrderAirportReqData();
        data.setAirportid(1);
        data.setOrdertype(AIRPORTORDER_TYPE.WAITPAY.getValue());*/
        // App 柜台订单列表查询根据状态
        
        // App 订单删除订单
      /*  AppDeleteOrderReqData data = new AppDeleteOrderReqData();
        data.setOrderid(4092);*/
        // App 订单删除订单
        
        bean.setData( new Gson().toJson( data ) );
        
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


}
