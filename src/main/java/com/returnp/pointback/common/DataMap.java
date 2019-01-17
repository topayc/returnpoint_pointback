package com.returnp.pointback.common;

import java.util.HashMap;

public class DataMap extends HashMap<String, Object>{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 생성자
	 */
	public DataMap() {
	}

	/**
	 * DB의 int 값 획득
	 *
	 * @param key the key
	 * @return the db int
	 */
	public int getDbInt(String key) {
		return getInt(key.toUpperCase());
	}
	
	/**
	 * int값 획득
	 *
	 * @param key the key
	 * @return the int
	 */
	public int getInt(String key) {
		int i = 0;
		Object o = get(key);
		if (o == null) return i;

		String s = o.toString().trim().replace(",", "");
		try {
			i = Integer.parseInt(s);
		} catch (NumberFormatException e) {
		}
		
		return i;
	}
	
	/**
	 * 최소값이 적용된 int값 획득
	 *
	 * @param key the key
	 * @param min the min
	 * @return the int
	 */
	public int getInt(String key, int min) {
		int i = getInt(key);
		return (i < min) ? min : i;
	}

	/**
	 * 스트링값 획득
	 *
	 * @param key the key
	 * @param def the def
	 * @return the str
	 */
	public String getStr(String key, String def) {
		String val = getStr(key);
		return val.equals("") ? def : val;
	}
	
	public void putDb(String key, Object value) {
		put(key.toUpperCase(), value == null ? "" : value);
	}

	
	/**
	 * 스트링값 획득
	 *
	 * @param key the key
	 * @return the str
	 */
	public String getStr(String key) {
		String s = "";
		Object o = get(key);
		if (o == null) return s;

		return o.toString();
	}
	
	/**
	 * Trim된 스트링 획득
	 *
	 * @param key the key
	 * @return the str trim
	 */
	public String getStrTrim(String key) {
		return getStr(key).trim();
	}

	/**
	 * 더블형 획득
	 *
	 * @param key the key
	 * @return the double
	 */
	public double getDouble(String key) {
		double d = 0;
		Object o = get(key);
		if (o == null) return d;

		String s = o.toString().trim().replace(",", "");
		try {
			d = Double.valueOf(s).doubleValue();
		} catch (NumberFormatException e) {
		}
		
		return d;
	}

	/**
	 * 디비의 날짜형 스트링 획득
	 *
	 * @param key the key
	 * @return the db date str
	 */
	public String getDbDateStr(String key) {
		return getDateStr(key.toUpperCase());
	}
	
	/**
	 * 날짜형 스트링 획득
	 *
	 * @param key the key
	 * @return the date str
	 */
	public String getDateStr(String key) {
		String s = "";
		Object o = get(key);
		if (o == null) return s;

		s = o.toString();
		if (s.length() > 19) s = s.substring(0, 19);
		return s;
	}

}
