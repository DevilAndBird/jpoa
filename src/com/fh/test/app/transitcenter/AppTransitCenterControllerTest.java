package com.fh.test.app.transitcenter;

import java.util.ArrayList;
import java.util.List;

import com.fh.common.constant_enum.IS_FINISH;
import com.fh.common.constant_enum.ORDER_STATUS;
import com.fh.common.constant_enum.ROLE_TYPE;
import com.fh.entity.app.AppRequestBean;
import com.fh.entity.app.transitcenter.AppUnloadDonReqData;
import com.fh.util.HttpClientUtil;
import com.fh.util.MD5;
import com.google.gson.Gson;


public class AppTransitCenterControllerTest {
    
//    public final static String url = "http://localhost:8080/jpoa/appTransitCenter/unloadScan";
    public final static String url = "http://localhost:8080/jpoa/appTransitCenter/unloadDone";
    
    public static void main(String[] args) {
        AppRequestBean bean = new AppRequestBean();
        bean.setUser( "130354218011" );
        bean.setKey( "admin444" );
        bean.setTimestamp( "" + System.currentTimeMillis() );
        bean.setSign( MD5.md5( "jingpei" + bean.getUser() + bean.getKey() + bean.getTimestamp() ));
        
//        AppUnloadScanReqData data = new AppUnloadScanReqData();
//        data.setBaggageid("1234567");
//        data.setStatus(ORDER_STATUS.TRUCELOADING.getValue());
//        data.setType(ORDER_TYPE.AIRPORTCOUNTER_TO_HOTEL.getValue());
//        data.setRoleid(15);
//        data.setRoletype(ROLE_TYPE.ROLE_DRIVER.getValue());
        
        AppUnloadDonReqData data = new AppUnloadDonReqData();
        data.setUnloadBeforeRoleid(12);
//        data.setUnloadBeforeRoletype(ROLE_TYPE.ROLE_TRANSIT_TRANSFEROVER.getValue());
        data.setUnloadBeforeInfinish(IS_FINISH.FINISHED.getValue());
        
        data.setUnloadLaterRoleid(20);
        data.setUnloadLaterRoletype(ROLE_TYPE.ROLE_ARRIVE_TRANSIT.getValue());
        data.setUnloadLaterInfinish(IS_FINISH.ONGOING.getValue());
        
        data.setUnloadBeforeStatus(ORDER_STATUS.WAITINGUNLOAD.getValue());
        data.setUnloadLaterStatus(ORDER_STATUS.UNLOAD.getValue());
        List<Integer> orderidList = new ArrayList<Integer>();
        orderidList.add(1);
        orderidList.add(2);
        data.setOrderidList(orderidList);
        
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
