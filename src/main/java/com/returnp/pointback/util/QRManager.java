package com.returnp.pointback.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.RandomStringUtils;

import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

public class QRManager {
	public static class QRCmd {
		public static final String GEN_JOIN_RECOM_QR = "10000";
		public static final String GEN_PRODUCT_QR = "10001";
		public static final String GEN_GIFT_QR = "10002";
		public static final String EXE_JOIN_WITH_RECOM = "20000";
		
		/*900 ~ 999 : 상품권 관련 기능*/
		public static final String ACC_BY_GIFTCARD= "900";
		public static final String PAY_BY_GIFTCARD= "901";
	}
	
	public static String QR_MAP_KEY_D = "D";
	public static String QR_MAP_KEY_F = "F";
	public static String QR_MAP_SEP_CREDIT = "!";
	public static String QR_MAP_SEP_CASH = "@";
	public static String QR_VAN_CODE_1= "1";
	public static String CHARSET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"; 

	
	/**
	 * KICC  전용 QR 포캣 파싱 
	 * @param queryQR
	 * @return
	 * @throws ParseException
	 */
	public static HashMap<String, String> parseQRToMap(String queryQR) throws ParseException{
		HashMap<String, String> queryMap = Util.queryToMap(queryQR);
		if (!QRManager.beforeValidateQR(queryMap)) {
			return null;
		}
		
		HashMap<String, String> qrMap = new HashMap<String, String>();
		String encData = queryMap.get(QRManager.QR_MAP_KEY_D);
		String sep   = encData.contains(QRManager.QR_MAP_SEP_CREDIT) ? 
				QRManager.QR_MAP_SEP_CREDIT  :  (encData.contains(QRManager.QR_MAP_SEP_CASH) ? QRManager.QR_MAP_SEP_CASH : "NOT" );
		
		if (sep.equals("NOT")) {
			return null;
		}
		
		String[] encArr =  encData.split(sep);
		if (encArr.length != 5) return null;
		
		long field1 = AntiLogarithm62.get62CharDecode(encArr[0]);
		long field2 = AntiLogarithm62.get62CharDecode(encArr[1]);
		long field3 = AntiLogarithm62.get62CharDecode(encArr[2]);
		long field4 = AntiLogarithm62.get62CharDecode(encArr[3]);
		long field5 = AntiLogarithm62.get62CharDecode(encArr[4]);
		
		String qrPText = 
			String.format("%09d", field1)+ 
			String.format("%09d", field2) + 
			String.format("%09d", field3) + 
			String.format("%09d", field4) +
			String.format("%04d", field5);
		System.out.println("----------------------KICC 큐알 적립 원본 데이타--------------------");
		System.out.println(qrPText);
		System.out.println("------------------------------------------------------------------------");
		/*VAN 시간을 내부 포맷으로 변경*/
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		GregorianCalendar calendar = new GregorianCalendar(
			Integer.valueOf("20"+qrPText.substring(0, 12).substring(0, 2)),      //연
			Integer.valueOf(qrPText.substring(0, 12).substring(4, 6)) -1,  //월
			Integer.valueOf(qrPText.substring(0, 12).substring(8, 10)),    //일
			Integer.valueOf(qrPText.substring(0, 12).substring(10, 12)),  //시
			Integer.valueOf(qrPText.substring(0, 12).substring(6, 8)),      //분
			Integer.valueOf(qrPText.substring(0, 12).substring(2, 4))       //초
		);
		Date date = calendar.getTime();
		
		/* 
		 * 인코딩된 데이타를 디크립트 후 맵 반환
		 * map key value
		 * pat : 승인 시간
		 * pan : 승인 번호
		 * af_id : 가맹점 번호
		 * pam : 승인 금액
		 * pas : 승인 상태  (1 : 결제 승인, 0 : 결제 승인 취소)
		 * pas_str : 승인 상태에 따른 추가된 문자열
		 * */
		
	/*	System.out.println("-------------------------------QR Manager KICC QR 파싱 데이타--------------------------------------");
		System.out.println(qrPText);
		System.out.println("--------------------------------------------------------------------------------------");*/
		try {
			qrMap.put("paymentRouterType", "VAN");  //라우터 타입
			qrMap.put("paymentRouterName", "KICC");  //밴사명
			qrMap.put("pat", sdf2.format(date));  //승인시간
			qrMap.put("pan",  qrPText.substring(23, 27) + qrPText.substring(36) + qrPText.substring(12, 16));   // 승인 번호
			qrMap.put("af_id", qrPText.substring(16, 23));  // 가맹점 번호
			qrMap.put("pam", String.valueOf(Integer.valueOf(qrPText.substring(27, 35))));    //승인 금액
			qrMap.put("pas", qrPText.substring(35,36));    //승인 상태 
			qrMap.put("pas_str", qrPText.substring(35,36).equals("0") ? "승인 완료" : "승인 취소");    //승인 상태 
			qrMap.put("pay_type_str", sep.equals(QRManager.QR_MAP_SEP_CREDIT ) ? "신용카드 결제" : "현금 결제");    //1 : 신용카드 2 : 현금 결제 
			qrMap.put("pay_type", sep.equals(QRManager.QR_MAP_SEP_CREDIT ) ? "1" : "2");    //1 : 신용카드 2 : 현금 결제 
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}  
		return qrMap;
	}

	/**
	 * KICC 외의 일반 범용 밴사 QR 데이타 파싱 
	 * @param queryQR
	 * @return
	 * @throws ParseException
	 */
	public static HashMap<String, String> parseCommonQRToMap(String queryQR) throws ParseException{
		HashMap<String, String> queryMap = Util.queryToMap(queryQR);
	/*	if (!QRManager.beforeValidateQR(queryMap)) {
			return null;
		}*/
		
		HashMap<String, String> qrMap = new HashMap<String, String>();
		String encData = queryMap.get("D");
		String sep   = encData.contains(QRManager.QR_MAP_SEP_CREDIT) ? 
				QRManager.QR_MAP_SEP_CREDIT  :  (encData.contains(QRManager.QR_MAP_SEP_CASH) ? QRManager.QR_MAP_SEP_CASH : "NOT" );
		
		if (sep.equals("NOT")) {
			return null;
		}
		
		String[] encArr =  encData.split(sep);
		if (encArr.length != 5) return null;
		
		long field1 = AntiLogarithm62.get62CharDecode(encArr[0]);
		long field2 = AntiLogarithm62.get62CharDecode(encArr[1]);
		long field3 = AntiLogarithm62.get62CharDecode(encArr[2]);
		long field4 = AntiLogarithm62.get62CharDecode(encArr[3]);
		String af_id  = encArr[4];
		
		String qrPText = 
			String.format("%09d", field1)+ 
			String.format("%09d", field2) + 
			String.format("%09d", field3) + 
			String.format("%06d", field4);
		
		/*VAN 시간을 내부 포맷으로 변경*/
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		GregorianCalendar calendar = new GregorianCalendar(
			Integer.valueOf("20"+qrPText.substring(0, 12).substring(0, 2)),      //연
			Integer.valueOf(qrPText.substring(0, 12).substring(4, 6)) -1,  //월
			Integer.valueOf(qrPText.substring(0, 12).substring(8, 10)),    //일
			Integer.valueOf(qrPText.substring(0, 12).substring(10, 12)),  //시
			Integer.valueOf(qrPText.substring(0, 12).substring(6, 8)),      //분
			Integer.valueOf(qrPText.substring(0, 12).substring(2, 4))       //초
		);
		Date date = calendar.getTime();
		
		/* 
		 * 인코딩된 데이타를 디크립트 후 맵 반환
		 * map key value
		 * pat : 승인 시간
		 * pan : 승인 번호
		 * af_id : 가맹점 번호
		 * pam : 승인 금액
		 * pas : 승인 상태  (1 : 결제 승인, 0 : 결제 승인 취소)
		 * pas_str : 승인 상태에 따른 추가된 문자열
		 * */
		
		System.out.println("-------------------------------QR Manager Common QR 파싱 데이타--------------------------------------");
		System.out.println(qrPText);
		System.out.println("제품 번호 " + af_id);
		System.out.println("---------------------------------------------------------------------------------------------------------------");
		
		try {
			qrMap.put("paymentRouterType", "VAN");  //라우터 타입
			qrMap.put("paymentRouterName", queryMap.get("V"));  //밴사명
			qrMap.put("seq", queryMap.get("S"));  //밴사 트랜잭션 고유 코드
			qrMap.put("af_id", af_id.trim());  // 가맹점 단말 제품 번호 , TID
			qrMap.put("pat", sdf2.format(date));  //승인시간
			qrMap.put("pan", qrPText.substring(12, 16) + qrPText.substring(24, 28) + qrPText.substring(29));   // 승인 번호
			qrMap.put("pam", String.valueOf(Integer.valueOf(qrPText.substring(16, 24))));    //승인 금액
			qrMap.put("pas", qrPText.substring(28,29));    //승인 상태 
			qrMap.put("pas_str", qrPText.substring(28,29).equals("0") ? "승인 완료" : "승인 취소");    //승인 상태 
			qrMap.put("pay_type_str", sep.equals(QRManager.QR_MAP_SEP_CREDIT ) ? "신용카드 결제" : "현금 결제");    //1 : 신용카드 2 : 현금 결제 
			qrMap.put("pay_type", sep.equals(QRManager.QR_MAP_SEP_CREDIT ) ? "1" : "2");    //1 : 신용카드 2 : 현금 결제 
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}  
		return qrMap;
	}
	
	public static byte[] genQRCode(String qrText) throws IOException, WriterException{
		Charset charset = Charset.forName("UTF-8");
		CharsetEncoder encoder = charset.newEncoder();

		String data = null;
		String qrAccessUrl = null;
		
		int qrcodeColor = 0x00000000;
		int backgroundColor = 0xFFFFFFFF;
		int h = 500	;
		int w = 500;
		
		
		byte[] b = null;
		try {
			ByteBuffer bbuf = encoder.encode(CharBuffer.wrap(qrText));
			b = bbuf.array();
		} catch (CharacterCodingException e) {
			e.printStackTrace();
			throw e;
		}
		
		try {
			data = new String(b, "UTF-8");
			BitMatrix matrix = null;
			com.google.zxing.Writer writer = new MultiFormatWriter();

			try {
				Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>(2);
				hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
				matrix = writer.encode(data, com.google.zxing.BarcodeFormat.QR_CODE, w, h, hints);
			} catch (com.google.zxing.WriterException e) {
				e.printStackTrace();
				throw e;
			}
			
			MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(qrcodeColor, backgroundColor);
			BufferedImage bImage;
			ByteArrayOutputStream baos;
			byte[] qrImgBytes; 
			try {
				bImage = MatrixToImageWriter.toBufferedImage(matrix, matrixToImageConfig);
				baos = new ByteArrayOutputStream();
				ImageIO.write(bImage, "png", baos);
				baos.flush();
				qrImgBytes = baos.toByteArray();
				baos.flush();
				return qrImgBytes;
			} catch (IOException e) {
				e.printStackTrace();
				throw e;
			}

		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public static String genQRCode(String savedir, String webRoot, String qrText, String fileName) throws Exception {
		Charset charset = Charset.forName("UTF-8");
		CharsetEncoder encoder = charset.newEncoder();

		String data = null;
		String qrAccessUrl = null;
		
		int qrcodeColor = 0x00000000;
		int backgroundColor = 0xFFFFFFFF;
		int h = 500	;
		int w = 500;
		
		File qrTempDir = new File(savedir);
		if (!qrTempDir.exists()) {
			qrTempDir.mkdirs();
		}
		
		byte[] b = null;
		try {
			ByteBuffer bbuf = encoder.encode(CharBuffer.wrap(qrText));
			b = bbuf.array();
		} catch (CharacterCodingException e) {
			e.printStackTrace();
			throw e;
		}
		
		String ext = "png";
		String qrFileTempNm = fileName != null ? fileName :  String.format("%s.%s", RandomStringUtils.randomAlphanumeric(10), ext);
		String filePath = savedir + "/" + qrFileTempNm;
		
		try {
			data = new String(b, "UTF-8");
			BitMatrix matrix = null;
			com.google.zxing.Writer writer = new MultiFormatWriter();

			try {
				Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>(2);
				hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
				matrix = writer.encode(data, com.google.zxing.BarcodeFormat.QR_CODE, w, h, hints);
			} catch (com.google.zxing.WriterException e) {
				e.printStackTrace();
				throw e;
			}
			File file = new File(filePath);
			MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(qrcodeColor, backgroundColor);
			try {
				MatrixToImageWriter.writeToStream(matrix, "PNG", new FileOutputStream(file), matrixToImageConfig);
			} catch (IOException e) {
				e.printStackTrace();
				throw e;
			}

			qrAccessUrl = webRoot + "/" + qrFileTempNm;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
		return qrAccessUrl;
	}
	
	/*전처리 검증*/
	public static Boolean beforeValidateQR(HashMap<String, String> queryMap){
		if (!queryMap.containsKey(QRManager.QR_MAP_KEY_F) || !queryMap.containsKey(QRManager.QR_MAP_KEY_D) ||
				queryMap.get(QRManager.QR_MAP_KEY_F) == null || queryMap.get(QRManager.QR_MAP_KEY_D) == null) {
			return false;
		}
		String qr_f =  queryMap.get(QRManager.QR_MAP_KEY_F);
		String qr_d = queryMap.get(QRManager.QR_MAP_KEY_D);
		
		if (!QRManager.QR_VAN_CODE_1.equals(qr_f.trim())) return false;
		return true;
	}
	
	public static Boolean afterValidateQR(String field1, String field2, String field3, String field4, String filed5){
	/*	if (field1.length() == 5 && 
				field2.length() == 5 && 
				field3.length() == 5 &&
				(field4.length() >=3 && 
				field4.length() <=5 )) {
			return true;
		}else {
			return false;
		}*/
		return true;
	}
			
	public static Boolean validateQR(String data62 ) {
		return true;
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(AntiLogarithm62.get62CharEncode(182508351L));
		System.out.println(AntiLogarithm62.get62CharEncode(520000000L));
		System.out.println(AntiLogarithm62.get62CharEncode(50000718L));
		System.out.println(AntiLogarithm62.get62CharEncode(403702L));
		System.out.println("---------------------------------------------------------------------");
		
		System.out.println(AntiLogarithm62.get62CharDecode(AntiLogarithm62.get62CharEncode(182508351L)));
		System.out.println(AntiLogarithm62.get62CharDecode(AntiLogarithm62.get62CharEncode(520000000L)));
		System.out.println(AntiLogarithm62.get62CharDecode(AntiLogarithm62.get62CharEncode(50000718L)));
		System.out.println(AntiLogarithm62.get62CharDecode(AntiLogarithm62.get62CharEncode(403702L)));
		
		QRManager.genQRCode(
			"C:\\Users\\topayc\\returnp\\van\\kftc\\qr", 
			"/", 
			"https://ppb.rp.com/v/qr?V=KFTC&S=5&D=clML5!zbRKM!3nNtc!1H1k!K906B92001", 
			"credit_qr");
		QRManager.genQRCode(
				"C:\\Users\\topayc\\returnp\\van\\kftc\\qr", 
				"/", 
				"https://ppb.rp.com/v/qr?V=KFTC&S=5&D=clML5@zbRKM@3nNtc@1H1k@K906B92001", 
				"cash_qr");
	/*	System.out.println("---------------------------------------------------------------------");
		System.out.println(AntiLogarithm62.get62CharEncode(182508351L));
		System.out.println(AntiLogarithm62.get62CharEncode(520000012L));
		System.out.println(AntiLogarithm62.get62CharEncode(345677184L));
		System.out.println(AntiLogarithm62.get62CharEncode(500000L));
		System.out.println(AntiLogarithm62.get62CharEncode(3702L));
		
		System.out.println("---------------------------------------------------------------------");
		System.out.println(AntiLogarithm62.get62CharDecode(AntiLogarithm62.get62CharEncode(182508351L)));
		System.out.println(AntiLogarithm62.get62CharDecode(AntiLogarithm62.get62CharEncode(520000012L)));
		System.out.println(AntiLogarithm62.get62CharDecode(AntiLogarithm62.get62CharEncode(345677184L)));
		System.out.println(AntiLogarithm62.get62CharDecode(AntiLogarithm62.get62CharEncode(500000L)));
		System.out.println(AntiLogarithm62.get62CharDecode(AntiLogarithm62.get62CharEncode(3702L)));*/
	}
}