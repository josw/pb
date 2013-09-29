package com.webctl.pb.config.resolver;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.webctl.pb.commons.ErrorCode;


/**
 * 실행 결과를 결과 코드를 포함한 JSON으로 감싸기 위한 컨테이너
 */
@JsonSerialize
public class ResultJsonContainer {
	
	private ErrorCode code;
	
	private Object value;
	
	
	public ResultJsonContainer(Object value) {
		this();
		this.value = value;
	}
	
	public ResultJsonContainer() {
		this( ErrorCode.OK);
	}
	
	public ResultJsonContainer(ErrorCode code) {
		this.code= code;
	}

	
	public String getResult_code() {
		return code.code;
	}
	
	public String getResult_msg() {
		return code.name();
	}
	
	public Object getValue() {
		return value;
	}
	

}
