package com.fh.test.app.flowtest;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.fh.common.constant_enum.INVICE_STATUS;
import com.fh.common.constant_enum.ORDER_ADDRESS_TYPE;
import com.fh.entity.app.AppRequestBean;
import com.fh.entity.app.order.AppOrderReqData;
import com.fh.entity.app.userdelivery.AppDmanCurrentGpsReqData;
import com.fh.entity.customer.CusCompliantInfo;
import com.fh.entity.customer.CusInfo;
import com.fh.entity.h5.CusGenerateVerify;
import com.fh.entity.h5.H5CheckAddrReqBean;
import com.fh.entity.h5.H5ContactIdBean;
import com.fh.entity.h5.H5ContactsReqBean;
import com.fh.entity.h5.H5CusChargeBean;
import com.fh.entity.h5.H5CusIdBean;
import com.fh.entity.h5.H5CusOrderCouponBean;
import com.fh.entity.h5.H5CusinfoReqBean;
import com.fh.entity.h5.H5HistoryAddrReqBean;
import com.fh.entity.h5.H5OrderIdBean;
import com.fh.entity.order.OrderInvoiceInfo;
import com.fh.test.base.BaseTest;
import com.fh.util.DateUtil;
import com.fh.util.HttpClientUtil;
import com.fh.util.MD5;
import com.fh.util.RedisUtil;
import com.google.gson.Gson;

/**
 * @des H5单元测试
 * @author tangqm
 * @date 2018年3月12日
 */
public class H5JunitTest extends BaseTest {

	public   String urlHeader = "http://localhost:8080/jpoa/";// 地址头
//	public  String urlHeader = "http://47.96.186.145/jpoa/";//测试服务器
	
	 @Test
    public void b() {
    	Map<String, AppDmanCurrentGpsReqData> testMap = (Map<String, AppDmanCurrentGpsReqData>) RedisUtil.get("310000");
//	    	AppDmanCurrentGpsReqData appDmanCurrentGpsReqData =  testMap.get("113");
    	System.out.println(testMap);
    }
	
	/**
     * 获取客户充值记录接口
     */
    @Test
    public void h5SaveCusInfo() {
        urlHeader+="h5order/h5SaveCusInfo";
        CusInfo data  = new CusInfo();
        data.setOpenid("111");
        data.setName("ABC");
        data.setIdno("3WER");
        data.setMobile("14751684265");
        data.setVerify("5153");
        commonTest(urlHeader,new Gson().toJson(data));
    }

    /**
     * 语音验证码
     */
    @Test
    public void h5VoiceNotification() {
        urlHeader+="h5order/h5VoiceNotification";
        CusInfo data  = new CusInfo();
        data.setMobile("15300870760");
        commonTest(urlHeader,new Gson().toJson(data));
    }
	
	/**
     * 获取客户充值记录接口
     */
    @Test
    public void getCusChargeList() {
        urlHeader+="h5order/getCusChargeList";
        H5CusIdBean data  = new H5CusIdBean();
        data.setCusId(36);
        commonTest(urlHeader,new Gson().toJson(data));
    }
    
    /**
     * 通过用户id查订单
     */
    @Test
    public void h5getOrderByCusId() {
        urlHeader+="h5order/h5getOrderByCusId";
        H5CusinfoReqBean data  = new H5CusinfoReqBean();
        data.setCusid(4);
        commonTest(urlHeader,new Gson().toJson(data));
    }
    
    /**
     * 通过订单id查角色动作
     */
    @Test
    public void getH5OrderOrderRole() {
        urlHeader+="h5order/getH5OrderRole";
        H5OrderIdBean data  = new H5OrderIdBean();
        data.setOrderid(133);
        commonTest(urlHeader,new Gson().toJson(data));
    }
    
    /**
     * 删除订单
     */
    @Test
    public void deleteOrder() {
        urlHeader+="h5order/deleteOrder";
        H5OrderIdBean data  = new H5OrderIdBean();
        data.setOrderid(132);
        commonTest(urlHeader,new Gson().toJson(data));
    }
     
    /**
     * 充值
     */
    @Test
    public void cusCharge() {
    	urlHeader+="h5order/cusCharge";
    	H5CusChargeBean data  = new H5CusChargeBean();
    	data.setCusId(15);
    	data.setMoney(500);
    	data.setSerialno("123456");
    	commonTest(urlHeader,new Gson().toJson(data));
    }
    
    /**
     * 修改优惠卷状态
     */
    @Test
    public void updateCusOrderCoupon() {
    	urlHeader+="h5order/updateCusOrderCoupon";
    	H5CusOrderCouponBean data  = new H5CusOrderCouponBean();
       data.setOrderId(21);
       data.setCouponId(1);
    	commonTest(urlHeader,new Gson().toJson(data));
    }
    
    /**
     * 客户投诉
     */
    @Test
    public void h5CusComplaint() {
    	urlHeader+="h5order/h5CusComplaint";
    	CusCompliantInfo data  = new CusCompliantInfo();
    	data.setCusid(15);
    	data.setCusname("测试");
    	data.setStatus(1);
    	data.setMobile("12345678912");
    	data.setContent("ceshi");
    	commonTest(urlHeader,new Gson().toJson(data));
    }
    
    /**
     * 查询客户列表
     */
    @Test
    public void findCusCouponList() {
    	urlHeader+="h5order/findCusCouponList";
    	H5CusIdBean data  = new H5CusIdBean();
    	data.setCusId(36);
    	commonTest(urlHeader,new Gson().toJson(data));
    }
    
    /**
     * 发票保存
     */
    @Test
    public void addInvoice() {
    	urlHeader+="h5order/addInvoice";
    	OrderInvoiceInfo data  = new OrderInvoiceInfo();
    	data.setOrderid(99);
//    	data.setMoney(80d);
    	data.setTaxno("HXLJ123");
    	data.setTitle("上海景沛网络科技有限公司");
    	data.setStatus(INVICE_STATUS.APPLYED.getValue());
    	data.setEmail("1136296996@QQ.com");
    	data.setNotes("备注公司地址需要");
    	commonTest(urlHeader,new Gson().toJson(data));
    }
    /**
     * 发票保存
     */
    @Test
    public void queryInvoice() {
    	urlHeader+="h5order/queryInvoiceByOrderid";
    	H5OrderIdBean data  = new H5OrderIdBean();
    	data.setOrderid(143);
    	commonTest(urlHeader,new Gson().toJson(data));
    }
    
    /**
     * 取消订单
     */
    @Test
    public void orderCancle() {
    	urlHeader+="h5order/orderCancle";
    	AppOrderReqData data  = new AppOrderReqData();
    	data.setId(52);
    	commonTest(urlHeader,new Gson().toJson(data));
    }
    
    /**
     * openid查询cusid
     */
    @Test
    public void findCusinfoByOpenid() {
    	urlHeader+="h5order/findCusinfoByOpenid";
    	H5CusinfoReqBean data  = new H5CusinfoReqBean();
    	data.setOpenid("okV0d1f-fZR5V0Euf8pAsR99u5zg");
    	commonTest(urlHeader,new Gson().toJson(data));
    }
    
    /**
     * 取消订单
     */
    @Test
    public void getCoupon() {
    	urlHeader+="h5order/getCoupon";
    	H5CusOrderCouponBean data  = new H5CusOrderCouponBean();
    	data.setCusid(36);
    	data.setCouponId(7);
    	commonTest(urlHeader,new Gson().toJson(data));
    }
    
    /**
     * 取消订单
     */
    @Test
    public void checkAddressUsable() {
    	urlHeader+="h5order/checkAddressUsable";
    	H5CheckAddrReqBean data  = new H5CheckAddrReqBean();
    	data.setProvid("310000");
    	data.setCityid("310000");
    	data.setDistrictid("310101");
    	data.setProvname("上海");
    	data.setCityname("上海市");
    	data.setDistrictname("黄浦区");
    	data.setAddress("21211");
    	commonTest(urlHeader,new Gson().toJson(data));
    }
    
    /**
     * 取消订单
     */
    @Test
    public void a() {
    	  Integer num = (int)(Math.random()*9000)+1000;
    	  System.out.print(num);
    }
    
    /**
     * 获取验证码
     */
    @Test
    public void cusGenerateVerify() {
    	urlHeader+="h5order/cusGenerateVerify";
    	
    	CusGenerateVerify data  = new CusGenerateVerify();
    	data.setMobile("18752066145");
    	commonTest(urlHeader,new Gson().toJson(data));
    }
    
    /**
     * 短信发送
     */
    @Test
    public void smsSend() {
    	urlHeader+="sms/smsSend";
    	
    	Map<String, String> a = new HashMap<String, String>();
    	a.put("mobile", "13155300783");
    	a.put("smscode", "X015");// 模板编号
    	a.put("orderno", "TEST001");// 模板编号
    	a.put("header", "智盾");// 模板编号
    	
    	commonTest(urlHeader,new Gson().toJson(a));
    }
    
    /**
     * 分钟测试
     */
    @Test
    public void aa() {
    	
//    	long beforeDate = DateUtil.fomatDate(DateUtil.format(addtime, "yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd HH:mm:ss").getTime();
//    	long currentDate = DateUtil.fomatDate(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd HH:mm:ss").getTime();
//    	ExceptionUtil.checkBoolean((currentDate - beforeDate)/(60*1000) > 30, "验证码超时");
    	
    	long a = DateUtil.fomatDate("2018-04-15 20:00:00", "yyyy-MM-dd HH:mm:ss").getTime();
    	long b = DateUtil.fomatDate("2018-04-15 21:00:00", "yyyy-MM-dd HH:mm:ss").getTime();
    	System.out.println((b-a)/(60*1000));// 1524844800000 1524909282000
    }
    
    /**
     * 增加联系人
     */
    @Test
    public void addContacts() {
    	urlHeader+="h5order/addContacts";
    	H5ContactsReqBean data  = new H5ContactsReqBean();
    	data.setName("周伟伟");
    	data.setMobile("1575558888"); 
    	data.setOpenid("12");
    	commonTest(urlHeader,new Gson().toJson(data));
    }
    /**
     * 查询联系人列表
     */
    @Test
    public void queryContacts() {
    	urlHeader+="h5order/h5getContactsByOpenId";
    	H5ContactsReqBean data  = new H5ContactsReqBean();
    	data.setOpenid("12");
    	commonTest(urlHeader,new Gson().toJson(data));
    }
    
    /**
     * 删除联系人
     */
    @Test
    public void deleteContact() {
    	urlHeader+="h5order/deleteContacts";
    	H5ContactIdBean data  = new H5ContactIdBean();
    	data.setContactid(7);
    	commonTest(urlHeader,new Gson().toJson(data));
    }
    
    /**
     * 修改联系人
     */
    @Test
    public void updateContact() {
    	urlHeader+="h5order/updateContact";
    	H5ContactsReqBean data  = new H5ContactsReqBean();
    	data.setId(4);
    	data.setName("左伟伟");
    	data.setMobile("15777777777");
    	data.setOpenid("12");
    	commonTest(urlHeader,new Gson().toJson(data));
    }
    
    /**
     * 查询历史地址信息
     */
    @Test
    public void findHistoryAddress() {
    	urlHeader+="h5order/findHistoryAddress";
    	H5HistoryAddrReqBean data  = new H5HistoryAddrReqBean();
    	data.setOpenid("wojiaotangqiming");
    	data.setAddresstype(ORDER_ADDRESS_TYPE.RESIDENCE.getValue());
    	commonTest(urlHeader,new Gson().toJson(data));
    }
    
    /**
     * 通过用户id查订单
     */
    @Test
    public void h5getOrderByCusId1() {
        urlHeader+="h5order/h5getOrderByCusId";
        H5CusinfoReqBean data  = new H5CusinfoReqBean();
        data.setCusid(87);
        commonTest(urlHeader,new Gson().toJson(data));
    }
    
    /**
     * 通过用户id查未支付订单
     */
    @Test
    public void h5getWaitpayOrderByCusId() {
        urlHeader+="h5order/h5getWaitOrderByCusId";
        H5CusinfoReqBean data  = new H5CusinfoReqBean();
        data.setCusid(4);
        commonTest(urlHeader,new Gson().toJson(data));
    }
    
    /**
     * 通过用户id查已完成订单
     */
    @Test
    public void h5getFinishOrderByCusId() {
    	urlHeader+="h5order/h5getFinishOrderByCusId";
    	H5CusinfoReqBean data  = new H5CusinfoReqBean();
    	data.setCusid(4);
    	commonTest(urlHeader,new Gson().toJson(data));
    }
    
    /**
     * 通过用户id查进行中订单
     */
    @Test
    public void h5getOngoingOrderByCusId() {
    	urlHeader+="h5order/h5getOngoingOrderByCusId";
    	H5CusinfoReqBean data  = new H5CusinfoReqBean();
    	data.setCusid(4);
    	commonTest(urlHeader,new Gson().toJson(data));
    }
    /**
     * 通过用户id查询退款订单
     */
    @Test
    public void h5getRefundOrderByCusId() {
    	urlHeader+="h5order/h5getRefundOrderByCusId";
    	H5CusinfoReqBean data  = new H5CusinfoReqBean();
    	data.setCusid(4);
    	commonTest(urlHeader,new Gson().toJson(data));
    }
    
    /**
     * 查询历史地址信息
     */
    @Test
    public void batchSaveFetch() {
    	urlHeader+="h5order/batchSaveFetch";
    	commonTest(urlHeader,new Gson().toJson(null));
    }

    
    /**
     * 查询历史地址信息
     */
    @Test
    public void B() {
        Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
        Map<String, String> map1 = new HashMap<String, String>();
        map1.put("mchid", "abc");
        map1.put("s_pappid", "bcd");
        map.put("paymch_info", map1);
        
        System.out.println(JSONObject.toJSONString(map));
    }
    
    @Test
    public void BX() {
    	String A = null;
    	String B = "A";
    	String srcaddressgps = "{'lng':'" + A +"','lat':'"+B+"'}"; 
    	
    	System.out.println(srcaddressgps);
    }
    
    /**
	 * @desc 税基生成6位数
	 * @auther zhangjj
	 * @date 2018年4月18日
	 */
	private static String getItemID(int num) {
        String val = "";
        Random random = new Random();
        for ( int i = 0; i < num ; i++ )
        {
            String str = random.nextInt( 2 ) % 2 == 0 ? "num" : "char";
            if ( "char".equalsIgnoreCase( str ) )
            { // 产生字母
                int nextInt = random.nextInt( 2 ) % 2 == 0 ? 65 : 97;
                // System.out.println(nextInt + "!!!!"); 1,0,1,1,1,0,0
                val += (char) ( nextInt + random.nextInt( 26 ) );
            }
            else if ( "num".equalsIgnoreCase( str ) )
            { // 产生数字
                val += String.valueOf( random.nextInt( 10 ) );
            }
        }
        return val;
    }
	
	public void commonTest(String url,String data) {
		AppRequestBean bean = new AppRequestBean();
		bean.setUser("wojiaotangqiming");
		bean.setKey("JPWEIXIN");
		bean.setTimestamp("" + System.currentTimeMillis());
		
		bean.setSign(MD5.md5("jingpei" + bean.getUser() + bean.getKey()
				+ bean.getTimestamp()));
		bean.setData(data);
		Gson gson = new Gson();
		String json = gson.toJson(bean);
		System.out.println(json);
		try {
			String res = HttpClientUtil.doPostJson(url, json);
			System.out.println(res);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
