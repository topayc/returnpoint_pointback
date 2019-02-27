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
			ObjectMapper mapper = new ObjectMapper();
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
			return str;
		} catch (Exception e) {
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
			stringReponse.setResponseCode(base.getResponseCode());
			
			String plain = null;
			if (base instanceof ObjectResponse<?>) {
				plain = mapper.writeValueAsString(((ObjectResponse<?>)base).getData());
				stringReponse.setData(BASE64Util.encodeString(Aes256Crypto.encode(plain, key)));
				stringReponse.setTotal(-1);
			}

			else if (base instanceof ArrayResponse<?>) {
				plain = mapper.writeValueAsString(((ArrayResponse<?>)base).getRows());
				stringReponse.setData(BASE64Util.encodeString(Aes256Crypto.encode(plain, key)));
				stringReponse.setTotal(((ArrayResponse<?>)base).getTotal());
			} 
			else  {
				stringReponse.setTotal(-1);
			}
			
			return stringReponse;
			/*String response = mapper.writeValueAsString(stringReponse);*/
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
