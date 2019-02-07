package com.returnp.pointback.service;

import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.PostConstruct;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.returnp.pointback.dto.command.api.ApiRequest;
import com.returnp.pointback.dto.response.ArrayResponse;
import com.returnp.pointback.dto.response.ObjectResponse;
import com.returnp.pointback.dto.response.ReturnpBaseResponse;
import com.returnp.pointback.dto.response.StringResponse;
import com.returnp.pointback.util.Aes256Crypto;
import com.returnp.pointback.util.BASE64Util;

@Service
public class ApiResponseServiceImpl implements ApiResponseService {

	@Autowired Environment env;
	Boolean responseEncrypting = false;
	
	@PostConstruct
	public void init() {
		System.out.println("#### ApiResponseServiceImpl initialize .......");
		System.out.println("responseEncrypting : " + env.getProperty("responseEncrypting"));
		this.responseEncrypting = env.getProperty("responseEncrypting").equals("Y") ? true : false;
	}
	
	/* 
	 * 응답용 암호화 문자열 생성
	 * 1.ReturnpBaseResponse를 JSON 문자열로 변환 
	 * 3.해당 고유키로 암호화
	 * 2.BASE62 encoding
	 */
	@Override
	public String encode(String encyptKey, ReturnpBaseResponse res) {
		try {
			System.out.println("encrypt");
			ObjectMapper mapper = new ObjectMapper();
			
			System.out.println("응답 원래 문자열");
			System.out.println(mapper.writeValueAsString(res));
			
			return BASE64Util.encodeString(Aes256Crypto.encode(mapper.writeValueAsString(res), encyptKey));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/* 
	 * 요청 문자열을 복호화 
	 * 1.해당 고유 키로 복호화
	 * 2.BASE64 Decoding
	 * 3.ApiRequest 오브젝트로 매핑
	 */
	@Override
	public String decode(String data, String encyptKey) {
		try {
			String str = Aes256Crypto.decode(BASE64Util.decodeString(data), encyptKey);
			System.out.println("복호화 문자열");
			System.out.println(str);
			return str;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String generateResponse(HashMap<String, Object> base, String key) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			String response = mapper.writeValueAsString(base);
			
			System.out.println("## 원문 문자열");
			System.out.println(response);
			
			if (this.responseEncrypting) {
				System.out.println("## 암호화된 문자열");
				response = BASE64Util.encodeString(Aes256Crypto.encode(response, key));
				System.out.println(response);
			}
			return response;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String generateResponse(String base, String key) {
		try {
			System.out.println("## 원문 문자열");
			System.out.println(base);
			
			if (this.responseEncrypting) {
				System.out.println("## 암호화된 문자열");
				base = BASE64Util.encodeString(Aes256Crypto.encode(base, key));
				System.out.println(base);
			}
			return base;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String generateResponse(ArrayList<HashMap<String, Object>> base, String key) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			String response = mapper.writeValueAsString(base);
			
			System.out.println("## 원문 문자열");
			System.out.println(response);
			
			if (this.responseEncrypting) {
				System.out.println("## 암호화된 문자열");
				response = BASE64Util.encodeString(Aes256Crypto.encode(response, key));
				System.out.println(response);
			}
			return response;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public StringResponse generateResponse(ReturnpBaseResponse base, String key) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			StringResponse stringReponse = new StringResponse();
			stringReponse.setMessage(base.getMessage());
			stringReponse.setResult(base.getResult());
			stringReponse.setResultCode(base.getResultCode());
			stringReponse.setSummary(base.getSummary());
			
			String plain = null;
			if (base instanceof ObjectResponse<?>) {
				plain = mapper.writeValueAsString(((ObjectResponse<?>)base).getData());
				stringReponse.setData(BASE64Util.encodeString(Aes256Crypto.encode(plain, key)));
			}

			if (base instanceof ArrayResponse<?>) {
				plain = mapper.writeValueAsString(((ArrayResponse<?>)base).getData());
				stringReponse.setData(BASE64Util.encodeString(Aes256Crypto.encode(plain, key)));
			}
			
			return stringReponse;
			/*String response = mapper.writeValueAsString(stringReponse);*/
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
