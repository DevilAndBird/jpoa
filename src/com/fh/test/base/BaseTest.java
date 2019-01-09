package com.fh.test.base;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@SuppressWarnings("all")
@ContextConfiguration(locations = "classpath:spring/ApplicationContext.xml")
public class BaseTest extends AbstractJUnit4SpringContextTests {

    public Log logger = LogFactory.getLog(getClass());
    
}
