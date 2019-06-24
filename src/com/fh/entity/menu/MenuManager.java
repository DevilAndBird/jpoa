package com.fh.entity.menu;

import java.util.Random;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fh.util.HttpClientUtil;
import com.fh.util.WeixinUtil;


/**
 * @desc 公众号菜单配置 accessToken 生成菜单操作会使token失效，最好晚上升级 TODO
 * @auther zhangjj
 * @date 2018年7月29日
 */
public class MenuManager {
    private static Logger log = LoggerFactory.getLogger(MenuManager.class);

    // dev start ==================================================
    // 第三方用户唯一凭证
//    static final String  appId = "wx44636cc298fa4cad";
////    // 第三方用户唯一凭证密钥
//    static final String appSecret = "977f6fcef1aea65797e97e1bf1c06cb3";
////    // 公众号域名
//    static final String domainurl = "dat.porterme.cn";
   // dev end ======================================================

	// prd start ==================================================
    // 第三方用户唯一凭证
    static final String appId = "wxd4209fdab3d66847";
    // 第三方用户唯一凭证密钥
    static final String appSecret = "64819f738126f4b7389c311b72193d58";
    // 公众号域名
    static final String domainurl = "wx.porterme.cn";
    // prd end ======================================================

    public static void main(String[] args) {
        // 调用接口获取access_token
//        AccessToken at = WeixinUtil.getAccessToken(appId, appSecret);

//        if (null != at) {
//            // 调用接口创建菜单
            int result = WeixinUtil.createMenu(getMenu(), "22_SWKSW6a1w6u0CIc3Fdv20MAhMZLWkWDTjO0l7WS3mJdFiVycaYjMUdO8bndsKey0Fgiy_Q-amyeXZ33ZFJ6lTcR4NcHk05ZSD6ta1lDiq9ExAoyXSsKWXMFACeF_t-y_fdVRYeo5MGEKyNZ_OYJfAEAGQB");

         // 返回
        String url = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=22_SWKSW6a1w6u0CIc3Fdv20MAhMZLWkWDTjO0l7WS3mJdFiVycaYjMUdO8bndsKey0Fgiy_Q-amyeXZ33ZFJ6lTcR4NcHk05ZSD6ta1lDiq9ExAoyXSsKWXMFACeF_t-y_fdVRYeo5MGEKyNZ_OYJfAEAGQB";
        HttpClientUtil.doPost(url);
    }

    /**
     * 组装菜单数据
     *
     * @return
     */
    private static Menu getMenu() {

//        CommonButton btn31 = new CommonButton();
//        btn31.setName("订单管理");
//        btn31.setType("view");
//        btn31.setUrl("http://"+ domainurl +"/wx/02dingdanguanli.html");
//
//        CommonButton btn32 = new CommonButton();
//        btn32.setName("个人中心");
//        btn32.setType("view");
//        btn32.setUrl("http://"+ domainurl +"/wx/03wodexingyi.html");

        CommonButton btn33 = new CommonButton();
        btn33.setName("常见问题");
        btn33.setType("view");
        btn33.setUrl("https://mp.weixin.qq.com/s/MmsyJjB-q-y0sgJSw9nceg");

        CommonButton btn34 = new CommonButton();
        btn34.setName("联系客服");
        btn34.setType("click");
        btn34.setKey("custphone");

//        CommonButton btn35 = new CommonButton();
//        btn35.setName("查看物流");
//        btn35.setType("view");
//        btn35.setUrl("http://"+domainurl+"/wx/order_logistics.html");

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

//        CommonButton btn11 = new CommonButton();
//        btn11.setName("金牌服务");
//        btn11.setType("view");
//        btn11.setUrl("http://"+ domainurl +"/wx/01index.html?r=" + new Random().nextInt());

//        CommonButton btn12 = new CommonButton();
//        btn12.setName("专车专送");
//        btn12.setType("view");
//        btn12.setUrl("http://"+ domainurl +"/wx/specialcar/specialcar.html?r=" + new Random().nextInt());

        CommonButton btn13 = new CommonButton();
        btn13.setName("行李寄存");
        btn13.setType("view");
        btn13.setUrl("http://"+ domainurl +"/wx/consign/html/consign.html");

        /**
         * 微信：  mainBtn1,mainBtn2,mainBtn3底部的一级菜单。
         */
        MiniprogramButton bbb = new MiniprogramButton();
        bbb.setName("行李寄送");
        bbb.setType("miniprogram");
        bbb.setUrl("http://mp.weixin.qq.com");
        bbb.setAppid("wxa537bb199a6a1045");// 小程序ID
        bbb.setPagepath("pages/view/index/index");

        ComplexButton mainBtn1 = new ComplexButton();
        mainBtn1.setName("我要下单");
        Button[] b = new Button[2];
        b[0] = bbb;
        b[1] =  btn13;
        mainBtn1.setSub_button(b);

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
}
