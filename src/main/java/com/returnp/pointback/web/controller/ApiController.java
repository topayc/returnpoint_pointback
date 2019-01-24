package com.returnp.pointback.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.returnp.pointback.dto.response.BaseResponse;
import com.returnp.pointback.web.message.MessageUtils;

@Controller
@RequestMapping("/v1/api")
public class ApiController extends ApplicationController{
	
	private static final Logger logger = LoggerFactory.getLogger(ApiController.class);
	
	@Autowired MessageUtils messageUtils;
	@Autowired Environment env;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(df, false));
	}
	
	/**
	 * 요청된 데이터에 대한 임시 저장 
	 * 분산 서버에서 사용하는 일종의 캐시 컨트롤러
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/save_temp_data", method = RequestMethod.POST)
	public BaseResponse saveTempData() {
		BaseResponse res = new BaseResponse();
		return res;
	}

	/**
	 * 회원 가입
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/singUp", method = RequestMethod.POST)
	public BaseResponse signUp() {
		BaseResponse res = new BaseResponse();
		return res;
	}
	
	/**
	 * 회원 가입 여부 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/chech_sign_up", method = RequestMethod.POST)
	public BaseResponse checkSignUp() {
		BaseResponse res = new BaseResponse();
		return res;
	}
	
	
	/**
	 * 회원 삭제
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete_member", method = RequestMethod.POST)
	public BaseResponse deleteMember() {
		BaseResponse res = new BaseResponse();
		return res;
	}
	
	/**
	 * 회원 정보 수정 및 업데이트 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/modify_member", method = RequestMethod.POST)
	public BaseResponse modifyMember() {
		BaseResponse res = new BaseResponse();
		return res;
	}
	
	
	/**
	 * 회원 정보 가져오기
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get_member_info", method = RequestMethod.POST)
	public BaseResponse getMemberInfo() {
		BaseResponse res = new BaseResponse();
		return res;
	}
	
	/**
	 * 중복 검사 처리 
	 * 전화번호, 이메일, 이름 등  
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/check_duplicated", method = RequestMethod.POST)
	public BaseResponse checkEmailDuplicated() {
		BaseResponse res = new BaseResponse();
		return res;
	}
	
	/**
	 * 결제 금액에 따른 적립 처리 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/execute_accumulate", method = RequestMethod.POST)
	public BaseResponse executeAccumualte() {
		BaseResponse res = new BaseResponse();
		return res;
	}
	
	/**
	 * 결제 금액에 따른 적립 취소
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/cancel_accumulate", method = RequestMethod.POST)
	public BaseResponse cancelAccumulatge() {
		BaseResponse res = new BaseResponse();
		return res;
	}
	
	/**
	 * 지원 언어 조회
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get_lang", method = RequestMethod.POST)
	public BaseResponse getLanguate() {
		BaseResponse res = new BaseResponse();
		return res;
	}
	
	/**
	 * RPAY 사용 내역
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get_rpay_history", method = RequestMethod.POST)
	public BaseResponse historyRpay() {
		BaseResponse res = new BaseResponse();
		return res;
	}
	
	/**
	 * R POINT	 적립 내역
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get_rpoint_history", method = RequestMethod.POST)
	public BaseResponse historyAccumulateRpoint() {
		BaseResponse res = new BaseResponse();
		return res;
	}
	
	/**
	 * 정책 제공
	 * 가맹 쇼핑몰에서 구매히 결제 적립율을 알 필요가 있기 때문에 제공 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get_rpoint_history", method = RequestMethod.POST)
	public BaseResponse getAccmulateRate() {
		BaseResponse res = new BaseResponse();
		return res;
	}
}
