package com.fh.test.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fh.entity.customer.CusCouponInfo;
import com.fh.service.customer.CusCouponInfoService;
import com.fh.test.base.BaseTest;

public class CusCouponInfoServiceTest extends BaseTest {

	@Autowired
	private CusCouponInfoService couponInfoService;
	
	@Test
	public void findCouponinfoByOrdernoTest() throws Exception {
		CusCouponInfo cusCouponInfo = couponInfoService.findCouponinfoByOrderno("JPWX20181024102849988");
		System.out.println(cusCouponInfo);
	}
	
	@Test
	public void useCouponTest() throws Exception {
		couponInfoService.useCoupon("JPWX20181024102849988");
	}
	
	@Test
	public void cutCouponTest() throws Exception {
		couponInfoService.recallUseCoupon("JPWX20181024102849988");
	}
}
