package com.fh.thirdparty.jiguangpush;

import Decoder.BASE64Encoder;
import com.alibaba.fastjson.JSONArray;
import com.fh.util.HttpClientUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @desc java后台极光推送方式一：使用Http API/此种方式需要自定义http请求发送客户端:HttpClient
 * @auther zhangjj
 * @date 2018年5月7日
 */
@Component
public class JiGuangPushBO {
	@Value("${PUSH_APPKEY}")
    private String PUSH_APPKEY = "";
	@Value("${PUSH_MASTERSECRET}")
    private String PUSH_MASTERSECRET = "";// 可更改
	@Value("${PUSH_PUSH_URL}")
    private String PUSH_PUSH_URL = "https://api.jpush.cn/v3/push";  // push api_url
	@Value("${PUSH_APNS_PRODUCTION}")
    private boolean PUSH_APNS_PRODUCTION ; // true 调用生产环境  false 调用测试 
	@Value("${PUSH_TIME_TO_LIVE}")
    private String PUSH_TIME_TO_LIVE = ""; // 1天保存
	@Value("${ALIAS_PREFIX}")
	private String ALIAS_PREFIX = "";// 别名
    
    /**
     * @desc 推送方法-调用极光API
     * @auther zhangjj
     * @date 2018年5月7日
     * @param extras:业务参数;alert:推送信息;alias:别名，推送对象
     */
    public String pushRest(List<Integer> orgPushObjList, String alert, JSONObject extras){
        // 调用验证  HTTP Header（头）里加一个字段（Key/Value对）Authorization: Basic base64_auth_string
    	String authorization = "Basic " + new BASE64Encoder().encodeBuffer((PUSH_APPKEY + ":" + PUSH_MASTERSECRET).getBytes());
    	// 组装极光推送专用json串
    	String content = generateJson(orgPushObjList, alert, extras).toString();
    	// 调用极光
        return HttpClientUtil.sendPostRequest(PUSH_PUSH_URL, content, "UTF-8", authorization);
    }
    
    /**
     * @desc 组装极光推送专用json串
     * @auther zhangjj
     * @date 2018年5月7日
     */
    public JSONObject generateJson(List<Integer> orgPushObjList, String alert, JSONObject extras){
        JSONObject json = new JSONObject();
        //平台
        JSONArray platform = new JSONArray();
        platform.add("android");
        platform.add("ios");
        
        //推送目标 别名 由 业务传值进入
        JSONObject audience = new JSONObject();
        JSONArray alilsList = new JSONArray();
        for (Integer userid : orgPushObjList) {
        	alilsList.add(ALIAS_PREFIX + userid);
		}
        audience.put("alias", alilsList);
        
        // 安卓
        JSONObject notification = new JSONObject();//通知内容
        // andrioid 通知
       /* JSONObject android = new JSONObject();//android通知内容
        android.put("alert", alert); // 内容
        android.put("builder_id", 1);
//        JSONObject android_extras = new JSONObject(); //android额外参数:编写业务
//        android_extras.put("type", "infomation");
        android.put("extras", extras);*/
        
        // ios
        JSONObject ios = new JSONObject();//ios通知内容
        ios.put("alert", alert);
        ios.put("sound", extras.get("voice") + ".caf"); // 声音
        ios.put("badge", "+1");
//        JSONObject ios_extras = new JSONObject();//ios额外参数
//        ios_extras.put("type", "infomation");
        ios.put("extras", extras);

//        notification.put("android", android);
        notification.put("ios", ios);

        // 自定义消息
        JSONObject message = new JSONObject();//android通知内容
        message.put("msg_content", alert); // 消息内容本身
        message.put("extras", extras); // 额外参数
        
        // 可选参数
        JSONObject options = new JSONObject();//设置参数
        options.put("time_to_live", Integer.parseInt(PUSH_TIME_TO_LIVE));// 推送当前用户不在线时，为该用户保留多长时间的离线消息，以便其上线时再次推送
        options.put("apns_production", PUSH_APNS_PRODUCTION); // True 表示推送生产环境
        
        json.put("platform", platform);
        json.put("audience", audience);
        json.put("notification", notification);
        json.put("message", message);
        json.put("options", options);
        return json;
    }
     
}