package com.fh.util;

import java.util.Map;
/**
 * 
 * 项目名称：jpoa   
 *
 * 类描述：
 * 类名称：com.fh.util.MapObjUtil     
 * 创建人：tangqm
 * 创建时间：2018年10月24日 上午10:07:58   
 * 修改人：
 * 修改时间：2018年10月24日 上午10:07:58   
 * 修改备注：
 */
public class MapObjUtil {

	/**
	 * 
	 * @Title: mapToObject
	 * @Description: Map转换成对象
	 * author：tangqm
	 * 2018年10月24日
	 * @param map
	 * @param beanClass
	 * @return
	 * @throws Exception
	 */
	public static Object mapToObject(Map<String, String> map, Class<?> beanClass)
			throws Exception {
		if (map == null){			
			return null;
		}
		Object obj = beanClass.newInstance();
		org.apache.commons.beanutils.BeanUtils.populate(obj, map);
		return obj;
	}

}
