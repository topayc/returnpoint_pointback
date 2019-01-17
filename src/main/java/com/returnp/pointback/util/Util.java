package com.returnp.pointback.util;

import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 공통함수를 위한  클래스.
 *
 * @version 1.0
 */
public class Util {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(Util.class);
	
	private static final int NOPOS = -1;


	public static void  copyRPmapToMap(HashMap<String, String> map1, HashMap<String, String> map2) {
		Set<String> set = map1.keySet();
		for (String s:set) {
			map2.put(s, map1.get(s));
		}
	}

	public static String sha(String password) {
		try {
	        MessageDigest digest = MessageDigest.getInstance("SHA-256");
	        byte[] hash = digest.digest(password.getBytes("UTF-8"));
	            StringBuffer hexString = new StringBuffer();
	 
	        for (int i = 0; i < hash.length; i++) {
	                String hex = Integer.toHexString(0xff & hash[i]);
	                if (hex.length() == 1)
	                    hexString.append('0');
	            hexString.append(hex);
	        }
	        
	        return hexString.toString() ;
		} catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
		}
	}
	


	public static HashMap<String, String> queryToMap(String queryParam){
		HashMap<String, String> map = new HashMap<String, String>();
		String[] params = queryParam.split("&");
		for (int i = 0; i < params.length; i++) {
			String key = params[i].split("=")[0].trim();
			String value = params[i].split("=")[1].trim();
			map.put(key, value);
		}
		return map;
	}

	public static String reverseString(String s) {
	    return ( new StringBuffer(s) ).reverse().toString();
	  }
	
	
	public static HashMap<String, String> parseQRtoMap(HashMap<String, String> map, String key) {
		HashMap<String, String> qrMap = new HashMap<String, String>();
		String encData = map.get(key);
		String[] encArr = encData.split("!");
		
		String field1 = encArr[0].trim();
		String field2 = encArr[1].trim();
		
		String qrPText = 
			AntiLogarithm62.convertBase62toBase10(AntiLogarithm62.strEachReverse(field1)) +
			AntiLogarithm62.convertBase62toBase10(AntiLogarithm62.strEachReverse(field2));
		
		/*VAN 시간을 내부 포맷으로 변경*/
		SimpleDateFormat sdf1= new SimpleDateFormat("yyssMMmmDDhh");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		System.out.println("승인 시간");
		try {
			System.out.println(qrPText.substring(0, 12));
			System.out.println(sdf1.parse(qrPText.substring(0, 12)));
			System.out.println(sdf2.format(sdf1.parse(qrPText.substring(0, 12))));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			qrMap.put("pat", sdf2.format(sdf1.parse(qrPText.substring(0, 12))));  //승인시간
			qrMap.put("pan", qrPText.substring(12, 16) + qrPText.substring(23, 27));   // 승인 번호
			qrMap.put("af_id", qrPText.substring(16, 23));  // 가맹점 번호
			qrMap.put("pam", String.valueOf(Integer.valueOf(qrPText.substring(27, 35))));    //승인 금액
			qrMap.put("pas", qrPText.substring(35));    //승인 상태 
			qrMap.put("pas_str", qrPText.substring(35).equals("0") ? "승인 완료" : "승인 취소");    //승인 상태 
		} catch (ParseException e) {
			e.printStackTrace();
		}  
		return qrMap;
	}
	
	public static String convertBase62toBase10(String base62) {
		if (base62 == null || base62.length() < 1) {
			return null;
		}

		int len = base62.length();
		double decimalValue = 0;
		int value = 0;

		for (int i = len - 1; i >= 0; i--) {
			char oneChar = base62.charAt(i);
			if (Character.isDigit(oneChar)) {
				value = Integer.parseInt(String.valueOf(base62.charAt(i)));
				decimalValue = decimalValue + (value * Math.pow(62, (len - 1) - i));
			} else {
				byte a = (byte) (oneChar);
				if (a <= 90) {
					value = (a - 65) + 10;
				} else if (a <= 122) {
					value = (a - 97) + 36;
				}
				decimalValue = decimalValue + (value * Math.pow(62, (len - 1) - i));
			}
		}

		return String.valueOf((long) decimalValue);
	}
}