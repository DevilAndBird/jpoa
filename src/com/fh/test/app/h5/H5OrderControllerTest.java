package com.fh.test.app.h5;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.internal.runners.statements.FailOnTimeout;
import org.springframework.beans.factory.annotation.Autowired;

import com.fh.common.constant_enum.MAILING_WAY;
import com.fh.common.constant_enum.ORDER_ADDRESS_TYPE;
import com.fh.common.constant_enum.ORDER_CHANNEL;
import com.fh.entity.app.AppRequestBean;
import com.fh.entity.auxiliary.coupon.CouponInfo;
import com.fh.entity.h5.H5Counponinfo;
import com.fh.entity.h5.H5OrderAddress;
import com.fh.entity.h5.H5OrderInfo;
import com.fh.entity.h5.H5OrderInsure;
import com.fh.entity.h5.H5OrderMain;
import com.fh.entity.h5.H5OrderNotesInfo;
import com.fh.entity.h5.H5OrderSenderReceiver;
import com.fh.entity.h5.H5UseCouponReqBean;
import com.fh.service.auxiliary.coupon.CouponInfoService;
import com.fh.service.order.orderinfo.OrderMainService;
import com.fh.test.base.BaseTest;
import com.fh.util.HttpClientUtil;
import com.fh.util.MD5;
import com.google.gson.Gson;

public class H5OrderControllerTest extends BaseTest {
	//public   String urlHeader = "http://localhost/jpoa/";// 地址头
//	public  String urlHeader = "http://localhost:8080/jpoa/";//测试服务器
	
	@Autowired
	private CouponInfoService couponInfoService;
	@Autowired
	private OrderMainService orderMainService;
	
	@Test
	public void TestUseCoupon() throws Exception {
		/*System.out.print(couponInfoService.useCoupon("3652", 59f));*/
		Float discountamount = 234.12f;
		Float totalmoney = 111111.0f;
		new BigDecimal((double)discountamount).setScale(2, BigDecimal.ROUND_UP).floatValue();
		new BigDecimal((double)(totalmoney- discountamount)).setScale(2, BigDecimal.ROUND_UP).floatValue();
	}
	
	
	
	/**
	 * @desc 保存订单信息
	 * @auther zhangjj
	 * @date 2018年10月24日
	 */
	@Test
	public void TestSaveOrder() throws Exception {
		H5OrderMain order = new H5OrderMain();
		
		H5OrderAddress addr = new H5OrderAddress();
		addr.setSrcaddrtype(ORDER_ADDRESS_TYPE.AIRPORTCOUNTER.getValue());
		addr.setSrcprovid( "310000" );
		addr.setSrccityid( "310100" );
		addr.setSrcdistrictid( "310111" );
		addr.setSrcprovname( "上海" );
		addr.setSrccityname( "上海市" );
		addr.setSrcdistrictname( "普陀" );
		addr.setSrcgps("{'lng':'121.32707','lat':'31.200373'}");
		addr.setSrcaddress("虹桥机场T2航站楼到达层28号行李转盘对面");
		addr.setDestaddrtype(ORDER_ADDRESS_TYPE.AIRPORTCOUNTER.getValue());
		addr.setDestprovid( "310000" );
		addr.setDestcityid( "310100" );
		addr.setDestdistrictid( "310112" );
		addr.setDestprovname( "上海" );
		addr.setDestcityname( "上海市" );
		addr.setDestdistrictname( "浦东新区" );
		addr.setDestaddress( "浦东机场T2航站楼出发层27号门外" );
		addr.setScrlandmark("浦东机场T2航站楼出发层27号门外");
		addr.setDestgps("{'lng':'121.32707','lat':'31.200373'}");
		addr.setSrcistransitgps(false);
		addr.setDestistransitgps(false);
		addr.setDestlandmark("浦东国际机场");
		order.setOrderAddr( addr );
		
		H5OrderInsure insure = new H5OrderInsure();
		insure.setInsureCode( "B003" );
		order.setOrderInsure( insure );
		
		H5OrderInfo orderInfo = new H5OrderInfo();
		orderInfo.setChannel(ORDER_CHANNEL.WINXIN_GS.getValue());
		orderInfo.setCusid( "4" );
		orderInfo.setCutmoney( (float)0 );
		orderInfo.setTotalmoney( (float)89 );
		orderInfo.setActualmoney( (float)89 );
		orderInfo.setNum( 1 );
		orderInfo.setFlightno( "CZ9909" );
		orderInfo.setMailingway(MAILING_WAY.ONESELF.getValue());
		orderInfo.setBackway(MAILING_WAY.ONESELF.getValue());
		orderInfo.setTaketime("2018-04-18 15:00:00");
		orderInfo.setSendtime("2018-04-18 19:00:00");
		order.setOrderMain( orderInfo );
		
		H5OrderSenderReceiver sr = new H5OrderSenderReceiver();
		sr.setSendername( "好帅才" );
		sr.setReceiveridno( "442401199401130000" );
		sr.setReceivername( "好帅才" );
		sr.setReceiverphone( "18000000000" );
		sr.setSenderidno( "442401199401130000" );
		sr.setSenderphone( "18000000000" );
		sr.setIsreceiverself( 1 );
		order.setSenderReceiver( sr );
		
		H5OrderNotesInfo h5OrderNotesInfo = new H5OrderNotesInfo();
		h5OrderNotesInfo.setAddusername("张桑");
		h5OrderNotesInfo.setNotes("黑色的箱子");
		order.setH5OrderNotesInfo(h5OrderNotesInfo);
		
		H5Counponinfo h5Counponinfo = new H5Counponinfo();
		h5Counponinfo.setCouponcode("343334");
//		order.setH5Counponinfo(h5Counponinfo);



		orderMainService.saveH5Order(order);
	}
	
	public void commonTest(String url,String data) {
		AppRequestBean bean = new AppRequestBean();
		bean.setUser("130354218011");
		bean.setKey("admin444");
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
	}}
