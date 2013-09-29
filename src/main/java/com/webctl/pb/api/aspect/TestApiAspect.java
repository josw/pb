package com.webctl.pb.api.aspect;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




@Aspect
public class TestApiAspect {
	
	private final static Logger logger = LoggerFactory.getLogger(TestApiAspect.class);
	
	@Inject
	private Provider<HttpServletRequest> requestProvider;
	
	@Around("@annotation(testApi)")
	public Object addResultCode(ProceedingJoinPoint pjp, TestApi testApi) throws Throwable {
		HttpServletRequest request = requestProvider.get();
		
		logger.info(request.getRemoteAddr());
		
		return pjp.proceed();
	}
}
