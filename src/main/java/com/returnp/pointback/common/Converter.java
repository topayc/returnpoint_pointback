package com.returnp.pointback.common;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;

import org.springframework.util.StringUtils;

/**
 * 변환관련 클래스
 */
public class Converter {
	
	/**
	 * null 이면 디폴트 문자열 리턴
	 *
	 * @param val the val
	 * @return the string
	 */
	public static String nvl(String val) {
		return nvl(val, "");
	}

	/**
	 * null 이면 디폴트 문자열 리턴
	 *
	 * @param val the val
	 * @param def the def
	 * @return the string
	 */
	public static String nvl(String val, String def) {
		if (val == null || val.equals("")) return def;
		return val;
	}
	
	/**
	 * 스트링으로 변환
	 *
	 * @param val the val
	 * @return the string
	 */
	public static String toStr(Object val) {
		String result = "";
		if(val != null) result = val.toString();
		return result;
	}
	
	/**
	 * 인티져로 변환
	 *
	 * @param val the val
	 * @return the integer
	 */
	public static Integer toInt(Object val) {
		String v1 = (val == null) ? "0" : val.toString().trim();
		v1 = (v1.equals("")) ? "0" : v1;
		v1 = v1.replaceAll(",", "");
		int i = 0;
		try {
			i = Integer.parseInt(v1);
		} catch (NumberFormatException e) {
			i = 0;
		}

		return i;
	}	


}
