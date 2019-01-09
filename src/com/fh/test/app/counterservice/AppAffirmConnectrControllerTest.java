package com.fh.test.app.counterservice;

import java.util.ArrayList;
import java.util.List;

import com.fh.common.constant_enum.IS_FINISH;
import com.fh.common.constant_enum.ORDER_STATUS;
import com.fh.common.constant_enum.ROLE_TYPE;
import com.fh.entity.app.AppRequestBean;
import com.fh.entity.app.counterservice.AppAffirmConnectReqData;
import com.fh.util.HttpClientUtil;
import com.fh.util.MD5;
import com.google.gson.Gson;

public class AppAffirmConnectrControllerTest {
    
    public final static String url = "http://localhost:8080/jpoa/appCounterService/affirmConnect";
    
    public static void main(String[] args) {
        AppRequestBean bean = new AppRequestBean();
        bean.setUser( "130354218011" );
        bean.setKey( "admin444" );
        bean.setTimestamp( "" + System.currentTimeMillis() );
        bean.setSign( MD5.md5( "jingpei" + bean.getUser() + bean.getKey() + bean.getTimestamp() ));
        
        AppAffirmConnectReqData data = new AppAffirmConnectReqData();
        data.setUnloadBeforeRoleid(12);
        data.setUnloadBeforeRoleType(ROLE_TYPE.ROLE_AIRPORT_SEND.getValue());
        data.setUnloadBeforeIsfinish(IS_FINISH.FINISHED.getValue());
        
        data.setUnloadLaterRoleid(16);
        data.setUnloadLaterRoleType(ROLE_TYPE.ROLE_ARRIVE_AIRPORT.getValue());
        
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
