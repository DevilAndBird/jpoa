package com.fh.test.app.orderbaggage;

import java.util.ArrayList;
import java.util.List;

import com.fh.common.constant_enum.IMGURL_BUSINESS_TYPE;
import com.fh.entity.app.AppRequestBean;
import com.fh.entity.app.order.OrderBaggageReqData;
import com.fh.util.HttpClientUtil;
import com.fh.util.MD5;
import com.google.gson.Gson;

public class AppOrderBaggageControllerTest {

    public final static String url = "http://localhost:8080/jpoa/appOrderBaggage/baggageImgUrlUpload";
    
    public static void main(String[] args) {
        AppRequestBean bean = new AppRequestBean();
        bean.setUser( "130354218011" );
        bean.setKey( "admin444" );
        bean.setTimestamp( "" + System.currentTimeMillis() );
        bean.setSign( MD5.md5( "jingpei" + bean.getUser() + bean.getKey() + bean.getTimestamp() ));
        
        OrderBaggageReqData data = new OrderBaggageReqData();
        data.setOrderid(3541);
        data.setBaggageid("JPQR151515");
//        data.setImgurl("abc/abd");
        data.getUploadUserid();
        data.setImgtype(IMGURL_BUSINESS_TYPE.COOLECT.getValue());
        List<String> list = new ArrayList<String>();
        list.add("http://jingpeioss.oss-cn-hangzhou.aliyuncs.com/1534902163.jpg");
        list.add("http://jingpeioss.oss-cn-hangzhou.aliyuncs.com/1534902163.jpg");
        list.add("http://jingpeioss.oss-cn-hangzhou.aliyuncs.com/1534902163.jpg");
        list.add("http://jingpeioss.oss-cn-hangzhou.aliyuncs.com/1534902163.jpg");
        list.add("http://jingpeioss.oss-cn-hangzhou.aliyuncs.com/1534902163.jpg");
        list.add("http://jingpeioss.oss-cn-hangzhou.aliyuncs.com/1534902163.jpg");
        data.setImgurlList(list);
        data.setUploadUserid(225);
        
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
