package com.fh.util;

import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;


public class ExceptionUtil {
    /**
     * 
     * <p>
     * Description:判断flag是否为true
     * </p>
     * 
     * @author zhangjj_sinosoft
     * @Date 2016-11-8
     * @Version 1.0.0
     * @param
     * @return
     */
    public static void checkBoolean(boolean flag, String errorMessage) {
        if (flag) {
            throw new RulesCheckedException(errorMessage);
        }
    }

    /**
     * <p>
     * Description: 校验参数是否为null
     * </p>
     * 
     * @author zhangjj_sinosoft
     * @Date 2016年10月22日 上午9:57:22
     * @Version 1.0.0
     * @param reference
     * @param errorMessage
     */

    public static void checkNotNull(Object reference, String errorMessage) {
        if (reference == null) {
            throw new RulesCheckedException(errorMessage);
        }
    }

    /**
     * <p>
     * Description: 校验参数是否为空/p>
     * 
     * @author zhangjj_sinosoft
     * @Date 2016年10月22日 上午9:57:38
     * @Version 1.0.0
     * @param reference
     * @param errorMessage
     */
    public static void checkNotEmpty(String reference, String errorMessage) {
        if (StringUtils.isBlank(reference)) {
            throw new RulesCheckedException(errorMessage);
        }
    }

    /**
     * <p>
     * Description: 校验参数是否为空/p>
     * 
     * @author zhangjj_sinosoft
     * @Date 2016年10月22日 上午9:57:38
     * @Version 1.0.0
     * @param reference
     * @param errorMessage
     */
    public static void checkNotCollectNotEmpty(Collection<?> reference, String errorMessage) {
        if (CollectionUtils.isEmpty(reference)) {
            throw new RulesCheckedException(errorMessage);
        }
    }

    /**
     * 
     * 判断flag是否为false
     * 
     * @author zhangjj_sinosoft
     * @Date 2016-11-8
     * @Version 1.0.0
     * @param
     * @return
     */
    public static void isFalse(boolean flag, String message) {
        if (flag) {
            throw new RulesCheckedException(message);
        }
    }

    /**
     * 断言是真,否则报规则校验异常
     * 
     * @history: 2017年7月10日
     * @author: wangpeng_sinosoft
     * @param flag
     * @param message
     */
    public static void isTrue(boolean flag, String message) {
        if (!flag) {
            throw new RulesCheckedException(message);
        }
    }

}
