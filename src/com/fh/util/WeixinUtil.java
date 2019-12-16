package com.fh.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import com.fh.entity.menu.*;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author sunqp
 *	微信菜单
 */
public class WeixinUtil {
    
    private static Logger log = LoggerFactory.getLogger(WeixinUtil.class);
    
    // 获取access_token的接口地址（GET） 限200（次/天）
    public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    
    // 菜单创建（POST） 限100（次/天）
    public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

    // dev start ==================================================
    // 第三方用户唯一凭证
    static final String  appId = "wx44636cc298fa4cad";
    //    // 第三方用户唯一凭证密钥
    static final String appSecret = "977f6fcef1aea65797e97e1bf1c06cb3";

    // prd start ==================================================
    // 第三方用户唯一凭证
//    static final String appId = "wxd4209fdab3d66847";
//    // 第三方用户唯一凭证密钥
//    static final String appSecret = "64819f738126f4b7389c311b72193d58";

    // 公众号域名
    static final String domainurl = "uat1.porterme.cn";
//    static final String domainurl = "wx.porterme.cn";


    public static void main(String[] args) {
        // 获取access_token 测试环境专用，生产环境查看日志获取
        System.out.println(WeixinUtil.getAccessToken(appId, appSecret).getToken());
        // 菜单
        System.out.println(JSONObject.fromObject(getMenu()).toString());
    }

    /**
     * 组装菜单数据
     *
     * @return
     */
    private static Menu getMenu() {
        CommonButton btn33 = new CommonButton();
        btn33.setName("常见问题");
        btn33.setType("view");
        btn33.setUrl("https://mp.weixin.qq.com/s/MmsyJjB-q-y0sgJSw9nceg");

        CommonButton btn34 = new CommonButton();
        btn34.setName("联系客服");
        btn34.setType("click");
        btn34.setKey("custphone");

        CommonButton btn21 = new CommonButton();
        btn21.setName("服务指南");
        btn21.setType("view");
        btn21.setUrl("https://mp.weixin.qq.com/s/UA_8M4CbTvXbIsmol6KDZg");

        CommonButton btn22 = new CommonButton();
        btn22.setName("柜台位置");
        btn22.setType("view");
        btn22.setUrl("https://mp.weixin.qq.com/s/Gdrdq8DSyXI5ffebhpt9LQ");

        CommonButton btn23 = new CommonButton();
        btn23.setName("行李标准");
        btn23.setType("view");
        btn23.setUrl("https://mp.weixin.qq.com/s/LYVTRydiJKsSyoFFgq0p-Q");

        CommonButton btn24 = new CommonButton();
        btn24.setName("商务合作");
        btn24.setType("click");
        btn24.setKey("tojoin");



        /**
         * 微信：  mainBtn1,mainBtn2,mainBtn3底部的一级菜单。
         */
        MiniprogramButton btn11 = new MiniprogramButton();
        btn11.setName("行李寄送");
        btn11.setType("miniprogram");
        btn11.setUrl("http://mp.weixin.qq.com");
        btn11.setAppid("wxa537bb199a6a1045");// 小程序ID
        btn11.setPagepath("pages/view/index/index");

        CommonButton btn12 = new CommonButton();
        btn12.setName("行李寄存");
        btn12.setType("view");
        btn12.setUrl("http://"+ domainurl +"/wx/consign/html/consign.html");

        CommonButton btn13 = new CommonButton();
        btn13.setName("行李门到门");
        btn13.setType("view");
        btn13.setUrl("http://"+ domainurl +"/MU/specialcar/specialcar.html");

        ComplexButton mainBtn1 = new ComplexButton();
        mainBtn1.setName("我要下单");
        mainBtn1.setSub_button(new Button[] {btn13});

        ComplexButton mainBtn2 = new ComplexButton();
        mainBtn2.setName("服务介绍");
        mainBtn2.setSub_button(new CommonButton[] { btn21, btn22, btn23, btn24});

        ComplexButton mainBtn3 = new ComplexButton();
        mainBtn3.setName("个人中心");
        mainBtn3.setSub_button(new CommonButton[] {btn33, btn34});

        /**
         * 封装整个菜单
         */
        Menu menu = new Menu();
        menu.setButton(new Button[] { mainBtn1, mainBtn2, mainBtn3 });



        return menu;
    }

    /**
     * 创建菜单
     * 
     * @param menu 菜单实例
     * @param accessToken 有效的access_token
     * @return 0表示成功，其他值表示失败
     */
    public static int createMenu(Menu menu, String accessToken) {
        int result = 0;
        // 拼装创建菜单的url
        String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);
        // 将菜单对象转换成json字符串
        String jsonMenu = JSONObject.fromObject(menu).toString();
        System.out.println("req:" + jsonMenu);
        // 调用接口创建菜单
        JSONObject jsonObject = httpRequest(url, "POST", jsonMenu);
        if (null != jsonObject) {
            if (0 != jsonObject.getInt("errcode")) {
                result = jsonObject.getInt("errcode");
                log.error("创建菜单失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
            }
        }

        return result;
    }
    
    
    /**
     * 获取access_token
     * 
     * @param appid 凭证
     * @param appsecret 密钥
     * @return
     */
    public static AccessToken getAccessToken(String appid, String appsecret) {
        AccessToken accessToken = null;

        String requestUrl = access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
        JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
        // 如果请求成功
        if (null != jsonObject) {
            try {
                accessToken = new AccessToken();
                accessToken.setToken(jsonObject.getString("access_token"));
                accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
            } catch (JSONException e) {
                accessToken = null;
                // 获取token失败
                log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
            }
        }
        return accessToken;
    }
    
    
    /**
     * 描述:  发起https请求并获取结果
     * @param requestUrl 请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr 提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);

            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            
            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);

            if ("GET".equalsIgnoreCase(requestMethod))
                httpUrlConn.connect();

            // 当有数据需要提交时
            if (null != outputStr) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            jsonObject = JSONObject.fromObject(buffer.toString());
        } catch (ConnectException ce) {
            log.error("Weixin server connection timed out.");
        } catch (Exception e) {
            log.error("https request error:{}", e);
        }
        return jsonObject;
    }
}
