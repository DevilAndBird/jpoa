package com.fh.controller.wxpublicnum;

import com.fh.util.PropertiesUtils;
/**
 * 
 * 项目名称：jpoa   
 *
 * 类描述：微信常量
 * 类名称：com.fh.controller.wxpublicnum.WeChatConst     
 * 创建人：tangqm
 * 创建时间：2018年9月26日 下午4:08:42   
 * 修改人：
 * 修改时间：2018年9月26日 下午4:08:42   
 * 修改备注：
 */
public interface WeChatConst {
	public static String WX_APPID = PropertiesUtils.getString("WX_APPID");
	public static String WX_APP_SECRET = PropertiesUtils.getString("WX_APP_SECRET");
	
    //公众号支付商户号
    String MCH_ID = "1502576941";
    //商户后台配置的一个32位的key,位置:微信商户平台-账户中心-API安全
    String KEY = "we469mw591nm199812n31239123123te";
    //交易类型
    String TRADE_TYPE = "JSAPI";
    
    /**
     * 订单生成
     */
    String ORDER_GENERATE = "x3lMhManB9oZDl-yWgVNXlHok62SrQjrnRVvIQy5FVw";
    /**
     * 订单状态
     */
    String ORDER_STATUS = "rckFHe64BONpulNupmf9jeYBNniCBR6kgkXEKAUimKU";
    /**
     * 派送成功
     */
    String DELIVERY_SUCC = "Q5ha3xwaZovxMWvk1yBP9mGiTj-BmAf2r0Rdi5_8Teo";
    /**
     * 派送成功
     */
    String ARRIVE_DESC = "SxbuVR5IpZGKpHjN656R8SRs-i92imZL3-6M3JBfGFA";
    /**
     * 自动分配失败
     */
    String AUTO_FAIL = "HI1DUnUX6FM_G1YbahyIKgcwhKrwGWgjmJQZpKvOl9k";
    

}
