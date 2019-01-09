package com.fh.test.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fh.service.auxiliary.coupon.CouponInfoService;
import com.fh.test.base.BaseTest;

public class CouponInfoServiceTest extends BaseTest {

	@Autowired
	private CouponInfoService couponInfoService;
	
	@Test
	public void addCouponUseNumTest() throws Exception {
		couponInfoService.addCouponUseNum("98745");
	}

}
