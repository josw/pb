package com.webctl.pb.config.resolver;

import java.io.IOException;

import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;


/**
 * result에 result code 와 result message를 넣어줌
 * 
 */
public class PbJsonHttpMessageConverter extends MappingJacksonHttpMessageConverter {
	
	@Override
	protected void writeInternal(Object o, HttpOutputMessage outputMessage) throws IOException,
			HttpMessageNotWritableException {

			super.writeInternal(new ResultJsonContainer(o), outputMessage);	
	}
}
