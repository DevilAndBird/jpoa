package com.fh.test.util;

import com.fh.util.RedisUtil;
import com.google.gson.Gson;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AESTest {

	static String key = "abcdef0123456789";  //16位
	static String iv = "0123456789abcdef";  //16位

	/**
	 *
	 */
	@Test
	public void aesMethodsTest() throws Exception {
		String temp1 = "12-19,FM9332,12:30,14:30,20,夏一,13916540070,330326199501020102,3781185056";
		String temp2 = "12-19,MU5346,14:30,16:55,30,蒋三,18381079158,330326199501020304,3781629310";
		String temp3 = "12-18,MU5362,21:30,23:50,30,蒋三,18381079158,330326199501020506,3781243928";
//		String temp4 = "12-18,MU5358,20:30,22:35,20,袁四,17717890290,330326199501020405,3781847187";
//		String temp5 = "12-18,MU5362,21:30,23:50,20,乔五,13817586253,330326199501020506,3781243928";



		List<String> list = new ArrayList<String>();
		list.add(temp1);
		list.add(temp2);
		list.add(temp3);
//		list.add(temp4);
//		list.add(temp5);

		for(int i= 0; i< list.size(); i++) {
			String temp = list.get(i);
			String[] data = temp.split(",");

			Map<String, String> map = new HashMap<String, String>();
			map.put("flightno", data[1]);
			map.put("flight_take_off", "2019-"+ data[0] + " " + data[2]);
			map.put("flight_fall_time","2019-"+ data[0] + " " + data[3]);
			map.put("cusname", data[5]);
			map.put("cusidno", data[7]);
			map.put("cusiphone",  data[6]);
			map.put("consignweight", data[4]);
			map.put("lugnum", data[8]);
			// json 数据
			String plainText = new Gson().toJson(map);
//			System.out.println(plainText);

			// 加密
			String url = "http://mu.porterme.cn/MU/doortodoor/html/doortodoor.html?param=";
			System.out.println(url + encryptAES(plainText));
		}
	}

	@Test
	public void soloTest() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("flightno", "MU1234");
		map.put("flight_take_off", "2019-12-21 15:30");
		map.put("flight_fall_time", "2019-12-21 17:30");
		map.put("cusname", "戴恩");
		map.put("cusidno", "342401199401137913");
		map.put("cusiphone", "18752066145");
		map.put("consignweight", "30");
		map.put("lugnum", "5545534456656");
        map.put("checkserial_BN", "7894561223");
		// json 数据
		String plainText = new Gson().toJson(map);
//			System.out.println(plainText);

		// 加密
		String url = "http://wx.porterme.cn/MU/doortodoor/html/doortodoor.html?param=";
		System.out.println(url + encryptAES(plainText));
	}

	// 加密
	public static String encryptAES(String data) throws Exception {

		try {

			Cipher cipher = Cipher.getInstance("AES/CBC/NOPadding");   //参数分别代表 算法名称/加密模式/数据填充方式
			int blockSize = cipher.getBlockSize();

			byte[] dataBytes = data.getBytes();
			int plaintextLength = dataBytes.length;
			if (plaintextLength % blockSize != 0) {
				plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
			}

			byte[] plaintext = new byte[plaintextLength];
			System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);

			SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
			IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

			cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
			byte[] encrypted = cipher.doFinal(plaintext);

			return new BASE64Encoder().encode(encrypted);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
