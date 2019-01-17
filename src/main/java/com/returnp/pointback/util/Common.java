package com.returnp.pointback.util;

import java.util.ArrayList;

import com.returnp.pointback.dto.CodeKeyValuePair;

public class Common {
	public static CodeKeyValuePair getNodeType(ArrayList<CodeKeyValuePair>codeKeyValues, String code) {
		for(CodeKeyValuePair data : codeKeyValues) {
			if (data.getKey().equals(code)) {
				return data;
			}
		}
		return null;
	}
}