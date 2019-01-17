package com.returnp.pointback.dto;

public class CodeKeyValuePair {
	public String key;
	public String value;
	public String desc;
	public String useInAdmin;
	public String useInWeb;
	
	public String getUseInAdmin() {
		return useInAdmin;
	}

	public void setUseInAdmin(String useInAdmin) {
		this.useInAdmin = useInAdmin;
	}

	public String getUseInWeb() {
		return useInWeb;
	}

	public void setUseInWeb(String useInWeb) {
		this.useInWeb = useInWeb;
	}

	public String getKey() {
		return key;
	}
	
	public CodeKeyValuePair() {}
	public CodeKeyValuePair(String key, String value, String useInAdmin, String useInWeb, String desc) {
		this.key = key;
		this.value = value;
		this.useInAdmin = useInAdmin;
		this.useInWeb = useInWeb;
		this.desc = desc;
		
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	
	
}
