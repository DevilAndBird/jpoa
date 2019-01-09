package com.fh.sms;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
/**
 * Created by Administrator on 2017/7/21.
 */
public class SmsCode {

    public static final String url = "http://106.3.37.83:6678/v2sms.aspx";
    public static final String userid = "2875";
    public static final String account = "jinpei";
    public static final String password = "123456";
    
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个IP值，第一个为真实IP。
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        } else {
            return request.getRemoteAddr();
        }
    }
}
