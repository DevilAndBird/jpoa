package com.fh.sms;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DSResult {

	// 定义jackson对象
		private static final ObjectMapper MAPPER = new ObjectMapper();

		// 响应业务状态
		private Integer status;

		// 响应消息
		private String msg;

		// 响应中的数据
		private Object data;

		public static DSResult build(Integer status, String msg, Object data) {
			return new DSResult(status, msg, data);
		}

		public static DSResult ok(Object data) {
			return new DSResult(data);
		}

		public static DSResult ok() {
			return new DSResult(null);
		}

		public DSResult() {

		}

		public static DSResult build(Integer status, String msg) {
			return new DSResult(status, msg, null);
		}

		public DSResult(Integer status, String msg, Object data) {
			this.status = status;
			this.msg = msg;
			this.data = data;
		}

		public DSResult(Object data) {
			this.status = 200;
			this.msg = "OK";
			this.data = data;
		}

		public Integer getStatus() {
			return status;
		}

		public void setStatus(Integer status) {
			this.status = status;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}

		public Object getData() {
			return data;
		}

		public void setData(Object data) {
			this.data = data;
		}

		/**
		 * 将json结果集转化为DSResult对象
		 * 
		 * @param jsonData
		 *            json数据
		 * @param clazz
		 *            DSResult中的object类型
		 * @return DSResult 自定义返回值类型
		 */
		public static DSResult formatToPojo(String jsonData, Class<?> clazz) {
			try {
				if (clazz == null) {
					return MAPPER.readValue(jsonData, DSResult.class);
				}
				JsonNode jsonNode = MAPPER.readTree(jsonData);
				JsonNode data = jsonNode.get("data");
				Object obj = null;
				if (clazz != null) {
					if (data.isObject()) {
						obj = MAPPER.readValue(data.traverse(), clazz);
					} else if (data.isTextual()) {
						obj = MAPPER.readValue(data.asText(), clazz);
					}
				}
				return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj);
			} catch (Exception e) {
				return null;
			}
		}

		/**
		 * 没有object对象的转化
		 * 
		 * @param json  json字符串
		 * @return DSResult 自定义返回值类型
		 */
		public static DSResult format(String json) {
			try {
				return MAPPER.readValue(json, DSResult.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		/**
		 * Object是集合转化
		 * 
		 * @param jsonData
		 *            json数据
		 * @param clazz
		 *            集合中的类型
		 * @return DSResult 自定义返回值类型O
		 */
		public static DSResult formatToList(String jsonData, Class<?> clazz) {
			try {
				JsonNode jsonNode = MAPPER.readTree(jsonData);
				JsonNode data = jsonNode.get("data");
				Object obj = null;
				if (data.isArray() && data.size() > 0) {
					obj = MAPPER.readValue(data.traverse(),
							MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
				}
				return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj);
			} catch (Exception e) {
				return null;
			}
		}

		@Override
		public String toString() {
			return "DSResult [status=" + status + ", msg=" + msg + ", data=" + data + "]";
		}
		
		
}
