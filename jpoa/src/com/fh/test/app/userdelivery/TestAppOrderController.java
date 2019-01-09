package com.fh.test.app.userdelivery;


import com.fh.entity.app.AppRequestBean;
import com.fh.entity.app.userdelivery.UserPickerReqData;
import com.fh.util.HttpClientUtil;
import com.fh.util.MD5;
import com.google.gson.Gson;

public class TestAppOrderController {
    
//    public final static String url = "http://localhost:8080/jpoa/userpicker/getDeliveryManListByParam";//tangqm
    public final static String url = "http://localhost:8080/jpoa/userpicker/prepareTransferluggage";//tanqm
    
    public static void main(String[] args) {
        AppRequestBean bean = new AppRequestBean();
        bean.setUser( "130354218011" );
        bean.setKey( "admin444" );
        bean.setTimestamp( "" + System.currentTimeMillis() );
        bean.setSign( MD5.md5( "jingpei" + bean.getUser() + bean.getKey() + bean.getTimestamp() ));

        UserPickerReqData data = new UserPickerReqData();
//        data.setCityid("11010");
//        data.setProvid("11000");
//        data.setDestaddr("小司机");
//        data.setRoleid(1);
//        data.setRoleType("AIRPORT_PICKER");
//        data.setRoleType("SERVICE_CENTER");
        data.setTransitid(10);
        data.setIsfinish("UNFINISHED");
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
