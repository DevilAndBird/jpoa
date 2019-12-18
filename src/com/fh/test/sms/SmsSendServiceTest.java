package com.fh.test.sms;

import com.fh.entity.app.auxiliary.push.req.AppPushReqData;
import com.fh.service.SmsSendService;
import com.fh.test.app.flowtest.WebJunitTest;
import com.fh.test.base.BaseTest;
import com.fh.thirdparty.jiguangpush.JiGuangPushBO;
import com.google.gson.Gson;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class SmsSendServiceTest extends BaseTest{

	
	@Autowired
	private SmsSendService smsSendService;


    @Test
    public void smsTemplateSendTest() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("header", "包给我");
        map.put("smscode", "X023");
        map.put("mobile", "18752066145");
        map.put("orderno", "12123123");
        map.put("taketime", "2019-12-17 23：00");
        String req =  new Gson().toJson(map);
        try {
            String res = smsSendService.smsTemplateSend(req);
            System.out.println("==========================");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
