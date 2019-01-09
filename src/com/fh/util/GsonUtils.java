package com.fh.util;

import com.google.gson.Gson;

/**
 * Gson工具类的封装
 * 
 * @author 13053
 * 
 */
public class GsonUtils {

	public static <T> T jsonToObject(String jsonString, Class<T> cls) {
		T t = null;
		Gson gson = new Gson();
		t = gson.fromJson(jsonString, cls);
		return t;
	}
}
