package com.returnp.pointback.service;

import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.returnp.pointback.dto.command.api.ApiRequest;
import com.returnp.pointback.dto.response.ObjectResponse;
import com.returnp.pointback.dto.response.ReturnpBaseResponse;
import com.returnp.pointback.util.Aes256Crypto;
import com.returnp.pointback.util.BASE64Util;

@Service
public class EncryptServiceImpl implements EncryptService {

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
	public ApiRequest decode(String encyptKey, String data) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			String str = Aes256Crypto.decode(BASE64Util.decodeString(data), encyptKey);
			System.out.println("복호화 문자열");
			System.out.println(str);
			//mapper.readValue(data, new TypeReference<ObjectResponse<Map<String, Object>>>() {});
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
