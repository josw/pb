package com.webctl.pb.config.resolver;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import com.webctl.pb.commons.CommonException;
import com.webctl.pb.commons.ErrorCode;


/**
 * 예외를 json 형태로 보여주기 위한 exception resolver. 구현 편의를 위해 현재는 모두 다 json 으로 보내도록 했음. 향후 확장이 필요하다면 아래 클래스 구현 참고할 것.
 * org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerExceptionResolver
 * 
 * @author kingori
 * 
 */
public class PbExceptionResolver extends AbstractHandlerExceptionResolver {
	private static final Log logger = LogFactory.getLog(PbExceptionResolver.class);

	private HttpMessageConverter<Object> messageConverter = new MappingJacksonHttpMessageConverter();

	private ModelAndView handleResponseBody(ExceptionJsonContainer returnValue, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// TODO: response status 어떻게 할까?
		// response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		response.setCharacterEncoding("UTF-8");
		HttpOutputMessage outputMessage = new ServletServerHttpResponse(response);
		messageConverter.write(returnValue, MediaType.APPLICATION_JSON, outputMessage);

		return new ModelAndView();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver#doResolveException(javax.servlet.http
	 * .HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
	 */
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		if (logger.isDebugEnabled()) {
			logger.debug(request.getServletPath());
			logger.debug(request.getContextPath());
			logger.debug(request.getPathInfo());
			logger.debug(request.getRequestURI());
		}

		ExceptionJsonContainer result = null;
		if (ex instanceof CommonException)
			result = new ExceptionJsonContainer((CommonException) ex);
		else if (ex instanceof IllegalArgumentException) {
			
			if (ex.getMessage() != null) {
				result = new ExceptionJsonContainer(ErrorCode.REQ_WRONG, ex.getMessage());
			} else
			result = new ExceptionJsonContainer(ErrorCode.REQ_WRONG);
			

		}
		else
			result = new ExceptionJsonContainer(ErrorCode.ERR_EXEC);

		logger.warn("error in process", ex);
		ModelAndView mav = null;
		try {
			mav = handleResponseBody(result, request, response);
		} catch (Exception e) {
			logger.error("error in making error json", e);
		}
		return mav;
	}
}