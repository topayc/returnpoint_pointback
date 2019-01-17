package com.returnp.pointback.code;

import java.util.Date;

public class CodeGenerator {
	public static String generatorRecommenderCode(String key) {
		//return key + "_RC_" + new Date ().getTime(); 
		return "REC_" + new Date ().getTime(); 
	}

	public static String generatorMemberCode(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	public static String generatorBranchCode(String email) {
		// TODO Auto-generated method stub
		return "BRC_" + new Date ().getTime(); 
	}

	public static String generatorAgencyCode(String email) {
		// TODO Auto-generated method stub
		return "AGC_" + new Date ().getTime(); 
	}

	public static String generatorAffiliateCode(String email) {
		// TODO Auto-generated method stub
		return "AFC_" + new Date ().getTime(); 
	}

	public static String generatorSaleManagerCode(String email) {
		// TODO Auto-generated method stub
		return "SMC_" + new Date ().getTime(); 
	}

	public static String generatorSoleDistCode(Object object) {
		// TODO Auto-generated method stub
		return "SDC_" + new Date ().getTime(); 
	}
}
