package com.webctl.pb.config.resolver;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lombok.ToString;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.webctl.pb.commons.CommonException;
import com.webctl.pb.commons.ErrorCode;


/**
 * exception을 json 으로 serialize 하기 위한 클래스
 * 
 */
@JsonSerialize
@ToString
public class ExceptionJsonContainer extends ResultJsonContainer implements Serializable {

	private static final long serialVersionUID = 8105710102448441898L;

	private Map<String, ?> errParamMap = null;

	public ExceptionJsonContainer(CommonException ex) {

		super(ex.getExceptionCode());
		this.errParamMap = ex.getErrorParamMap();
	}
	
	public ExceptionJsonContainer(ErrorCode errorCode, String msg) {

		super(errorCode);
		Map<String, String> errorMessage = new HashMap<String, String>();
		errorMessage.put("msg", msg);
		this.errParamMap = errorMessage;
	}

	public ExceptionJsonContainer(ErrorCode errorCode) {
		super(errorCode);
	}

	public Map<String, ?> getError_params() {
		return errParamMap;
	}

	// exception은 value 라는 필드가 필요없기 때문에 json ignore.
	@Override
	@JsonIgnore
	public Object getValue() {
		return null;
	}

}
