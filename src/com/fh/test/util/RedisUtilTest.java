package com.fh.test.util;

import com.fh.test.base.BaseTest;
import com.fh.util.RedisUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RedisUtilTest extends BaseTest {

	@Autowired
	public RedisUtil redisUtil;
	
	/**
     * 更新城市中心点
     */
    @Test
    public void redisMethodsTest() throws Exception {
		System.out.println(RedisUtil.get("test"));
    }
	
}
