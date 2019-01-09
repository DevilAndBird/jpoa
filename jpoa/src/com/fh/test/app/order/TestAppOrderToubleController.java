package com.fh.test.app.order;


import java.util.ArrayList;
import java.util.List;

import com.fh.entity.app.AppRequestBean;
import com.fh.entity.app.order.AppOrderToubleReqData;
import com.fh.entity.order.ProblemOrder;
import com.fh.util.HttpClientUtil;
import com.fh.util.MD5;
import com.google.gson.Gson;

public class TestAppOrderToubleController {
    // 订单详情查询 zhangjj
    public final static String url = "http://localhost:8080/jpoa/orderToubleInfo/orderToubleFeedback";
//    public final static String url = "http://localhost:8080/jpoa/orderToubleInfo/findOrderToubleList";
	
    
    
    public static void main(String[] args) {
        AppRequestBean bean = new AppRequestBean();
        bean.setUser( "130354218011" );
        bean.setKey( "admin444" );
        bean.setTimestamp( "" + System.currentTimeMillis() );
        bean.setSign( MD5.md5( "jingpei" + bean.getUser() + bean.getKey() + bean.getTimestamp() ));

        // 反馈信息查询
        AppOrderToubleReqData data = new AppOrderToubleReqData();
        ProblemOrder problemOrder = new ProblemOrder();
        problemOrder.setFeedbackdesc("第一个反馈test·");
        problemOrder.setFeedbackusermobile("1234567892");
        problemOrder.setFeedbackusername("张2");
        problemOrder.setOrderid(4238);
        problemOrder.setFeedbackuserid(243);
        data.setProblemOrder(problemOrder);
        
        // 反馈列表查询
//        AppOrderToubleReqData data = new AppOrderToubleReqData();
//        ProblemOrder problemOrder = new ProblemOrder();
//        problemOrder.setOrderid(4238);
//        data.setProblemOrder(problemOrder);
        
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
