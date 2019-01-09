package com.fh.service.wxpublicnum;

import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fh.controller.h5.util.WXPayUtil;
import com.fh.controller.wxpublicnum.ClientCustomSSL;
import com.fh.controller.wxpublicnum.WeChatConst;
import com.fh.controller.wxpublicnum.img.CommonUtil;
import com.fh.controller.wxpublicnum.wxpushinfo.WeixinUtil;
import com.fh.dao.DaoSupport;
import com.fh.entity.h5.wxpublicnum.WXInfoResBean;
import com.fh.entity.wxpublicnum.event.WxRefundResData;
import com.fh.service.order.orderinfo.OrderMainService;
import com.fh.util.SignUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 
 * 项目名称：jpoa   
 *
 * 类描述：微信公众号业务类
 * 类名称：com.fh.service.wxpublicnum.WXPublicNumService     
 * 创建人：tangqm
 * 创建时间：2018年6月8日 下午1:58:13   
 * 修改人：
 * 修改时间：2018年6月8日 下午1:58:13   
 * 修改备注：
 */
@Service
public class WXPublicNumService {

    @Autowired
    private DaoSupport dao;
	@Autowired
	private OrderMainService orderMainService;
    /**
     * 
     * @Title: refund
     * @Description: 退单
     * author：tangqm
     * 2018年9月26日
     * @param out_trade_no
     * @param refund_fee
     * @param total_fee
     * @return
     * @throws Exception
     */
    public WxRefundResData refund(String out_trade_no,Float refund_fee,Float total_fee) throws Exception{
    	String appid = WeChatConst.WX_APPID;
		String mch_id = WeChatConst.MCH_ID;			
		String nonce_str = UUID.randomUUID().toString().replace("-", "");
		String out_refund_no = UUID.randomUUID().toString().replace("-", "");
		refund_fee = refund_fee*100;
		total_fee = total_fee*100;
		SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>(); 
		packageParams.put("appid", appid);
		packageParams.put("mch_id", mch_id);
		packageParams.put("nonce_str", nonce_str);
		packageParams.put("out_refund_no", out_refund_no);
		packageParams.put("out_trade_no", out_trade_no);
		packageParams.put("refund_fee", refund_fee.intValue());
		packageParams.put("total_fee", total_fee.intValue());
		String sign  = SignUtil.creatSign("utf-8",packageParams);			
		packageParams.put("sign", sign);
		String reuqestXml = WXPayUtil.getRequestXml(packageParams);
		String wxUrl = "https://api.mch.weixin.qq.com/secapi/pay/refund";
		String weixinPost = ClientCustomSSL.doRefund(wxUrl, reuqestXml).toString();
		// 解析xml
		XStream stream = new XStream(new DomDriver());
		stream.alias("xml", WxRefundResData.class);
		WxRefundResData wxRefundResData = (WxRefundResData) stream.fromXML(weixinPost);
    	return wxRefundResData;
    }
    
    /**
     * 获取用户信息
     * @param accessToken 接口访问凭证
     * @param openId 用户标识
     * @return WXInfoResBean
     */
    public WXInfoResBean getUserInfo(String accessToken, String openId) {
    	Logger log = LoggerFactory.getLogger(WeixinUtil.class);
    	WXInfoResBean weixinUserInfo = null;
        // 拼接请求地址
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID";
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
        // 获取用户信息
        JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);

        if (null != jsonObject) {
            try {
                weixinUserInfo = new WXInfoResBean();
                // 用户的标识
                weixinUserInfo.setOpenid(jsonObject.getString("openid"));
                // 关注状态（1是关注，0是未关注），未关注时获取不到其余信息
                weixinUserInfo.setSubscribe(jsonObject.getInt("subscribe"));
                // 用户关注时间
                weixinUserInfo.setSubscribe_time(jsonObject.getString("subscribe_time"));
                // 昵称
                weixinUserInfo.setNickname(jsonObject.getString("nickname"));
                // 用户的性别（1是男性，2是女性，0是未知）
                weixinUserInfo.setSex(jsonObject.getInt("sex"));
                // 用户所在国家
                weixinUserInfo.setCountry(jsonObject.getString("country"));
                // 用户所在省份
                weixinUserInfo.setProvince(jsonObject.getString("province"));
                // 用户所在城市
                weixinUserInfo.setCity(jsonObject.getString("city"));
                // 用户的语言，简体中文为zh_CN
                weixinUserInfo.setLanguage(jsonObject.getString("language"));
                // 用户头像
                weixinUserInfo.setHeadimgurl(jsonObject.getString("headimgurl"));
            } catch (Exception e) {
                if (0 == weixinUserInfo.getSubscribe()) {
                    log.error("用户{}已取消关注", weixinUserInfo.getOpenid());
                } else {
                    int errorCode = jsonObject.getInt("errcode");
                    String errorMsg = jsonObject.getString("errmsg");
                    log.error("获取用户信息失败 errcode:{} errmsg:{}", errorCode, errorMsg);
                }
            }
        }
        return weixinUserInfo;
    }
    
}
