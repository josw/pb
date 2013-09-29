package com.webctl.pb.commons;

public enum ErrorCode {
	OK("000"),
	LOGIN_REQUIRED("100"),
	REQ_WRONG("200"),
	ERR_EXEC("300"),	
	;
	
	public String code;
	ErrorCode(String code) {
		this.code = code;
	}
}
