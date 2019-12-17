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
import java.util.HashMap;
import java.util.Map;

public class AESTest {

	static String key = "abcdef0123456789";  //16位
	static String iv = "0123456789abcdef";  //16位

	/**
	 *
	 */
	@Test
	public void aesMethodsTest() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("flightno", "MU5349");
		map.put("flight_take_off", "2019-12-18 17:30");
		map.put("flight_fall_time", "2019-12-18 18:30");
		map.put("cusname", "戴恩");
		map.put("cusidno", "330326199501020409");
		map.put("cusiphone", "18752066145");
		map.put("consignweight", "45");
		map.put("lugnum", "378184718711");
		// json 数据
		String plainText = new Gson().toJson(map);
		// {"flightno":"MU1234","cusiphone":"13035421800","flight_take_off":"2019-12-17 19:35","cusidno":"342401199401137913","consignweight":"30","cusname":"戴恩","flight_fall_time":"2019-12-17 18:00"}

		// 加密,
		System.out.println(encryptAES(plainText));
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
