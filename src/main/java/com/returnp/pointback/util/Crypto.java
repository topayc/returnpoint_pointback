package com.returnp.pointback.util;

import java.security.MessageDigest;

import org.apache.commons.codec.binary.Base64;

public class Crypto {	
	
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
	
	// Table for Base64 encoding
	static private final char base64_code[] = {
		'.', '/', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
		'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
		'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
		'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
		'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5',
		'6', '7', '8', '9'
	};
	
	private static byte char64(char x) {
		if ((int)x < 0 || (int)x > index_64.length)
			return -1;
		return index_64[(int)x];
	}
	
	// Table for Base64 decoding
	static private final byte index_64[] = {
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, 0, 1, 54, 55,
		56, 57, 58, 59, 60, 61, 62, 63, -1, -1,
		-1, -1, -1, -1, -1, 2, 3, 4, 5, 6,
		7, 8, 9, 10, 11, 12, 13, 14, 15, 16,
		17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27,
		-1, -1, -1, -1, -1, -1, 28, 29, 30,
		31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
		41, 42, 43, 44, 45, 46, 47, 48, 49, 50,
		51, 52, 53, -1, -1, -1, -1, -1
	};
		
	
		
	public static String encode_base64(byte d[], int len)
		throws IllegalArgumentException {
		int off = 0;
		StringBuffer rs = new StringBuffer();
		int c1, c2;

		if (len <= 0 || len > d.length)
			throw new IllegalArgumentException ("Invalid len");

		while (off < len) {
			c1 = d[off++] & 0xff;
			rs.append(base64_code[(c1 >> 2) & 0x3f]);
			c1 = (c1 & 0x03) << 4;
			if (off >= len) {
				rs.append(base64_code[c1 & 0x3f]);
				break;
			}
			c2 = d[off++] & 0xff;
			c1 |= (c2 >> 4) & 0x0f;
			rs.append(base64_code[c1 & 0x3f]);
			c1 = (c2 & 0x0f) << 2;
			if (off >= len) {
				rs.append(base64_code[c1 & 0x3f]);
				break;
			}
			c2 = d[off++] & 0xff;
			c1 |= (c2 >> 6) & 0x03;
			rs.append(base64_code[c1 & 0x3f]);
			rs.append(base64_code[c2 & 0x3f]);
		}
		return rs.toString();
	}
	
	public static byte[] decode_base64(String s, int maxolen)
		throws IllegalArgumentException {
		StringBuffer rs = new StringBuffer();
		int off = 0, slen = s.length(), olen = 0;
		byte ret[];
		byte c1, c2, c3, c4, o;

		if (maxolen <= 0)
			throw new IllegalArgumentException ("Invalid maxolen");

		while (off < slen - 1 && olen < maxolen) {
			c1 = char64(s.charAt(off++));
			c2 = char64(s.charAt(off++));
			if (c1 == -1 || c2 == -1)
				break;
			o = (byte)(c1 << 2);
			o |= (c2 & 0x30) >> 4;
			rs.append((char)o);
			if (++olen >= maxolen || off >= slen)
				break;
			c3 = char64(s.charAt(off++));
			if (c3 == -1)
				break;
			o = (byte)((c2 & 0x0f) << 4);
			o |= (c3 & 0x3c) >> 2;
			rs.append((char)o);
			if (++olen >= maxolen || off >= slen)
				break;
			c4 = char64(s.charAt(off++));
			o = (byte)((c3 & 0x03) << 6);
			o |= c4;
			rs.append((char)o);
			++olen;
		}

		ret = new byte[olen];
		for (off = 0; off < olen; off++)
			ret[off] = (byte)rs.charAt(off);
		return ret;
	}
	
	
	/**
	 * 데이터를 암호화하는 기능
	 * 
	 * @param byte[] data 암호화할 데이터
	 * @return String result 암호화된 데이터
	 * @exception Exception
	 */
	public static String encodeBinary(byte[] data) throws Exception
	{
		if (data == null) {
			return "";
		}

		return new String(Base64.encodeBase64(data));
	}

	/**
	 * 데이터를 암호화하는 기능
	 * 
	 * @param String data 암호화할 데이터
	 * @return String result 암호화된 데이터
	 * @exception Exception
	 */
	public static String encode(String data) throws Exception 
	{
		return encodeBinary(data.getBytes());
	}
	
	/**
	 * 데이터를 복호화하는 기능
	 * 
	 * @param String data 복호화할 데이터
	 * @return String result 복호화된 데이터
	 * @exception Exception
	 */
	public static byte[] decodeBinary(String data) throws Exception 
	{
		return Base64.decodeBase64(data.getBytes());
	}

	/**
	 * 데이터를 복호화하는 기능
	 * 
	 * @param String data 복호화할 데이터
	 * @return String result 복호화된 데이터
	 * @exception Exception
	 */
	public static String decode(String data) throws Exception 
	{
		return new String(decodeBinary(data));
	}
}
