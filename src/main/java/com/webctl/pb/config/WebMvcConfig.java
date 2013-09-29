package com.webctl.pb.config;

import java.util.List;

import javax.inject.Inject;

import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.handler.AbstractHandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.webctl.pb.config.resolver.PbExceptionResolver;
import com.webctl.pb.config.resolver.PbJsonHttpMessageConverter;

/**
 * 
 * webmvc 관련 설정 
 * 
 * @author josw
 *
 */
@Configuration
@ComponentScan(basePackages = { "com.webctl.pb" }, includeFilters = { @ComponentScan.Filter(Controller.class) }, useDefaultFilters = false)
@Import({ThymeleafConfig.class})
@PropertySource(value = "classpath:config.properties")
public class WebMvcConfig extends WebMvcConfigurationSupport {

	@Inject
	Environment env;

	@Inject
	ApplicationContext applicationContext;

	@Override
	public RequestMappingHandlerMapping requestMappingHandlerMapping() {
		RequestMappingHandlerMapping requestMappingHandlerMapping = super.requestMappingHandlerMapping();
		requestMappingHandlerMapping.setUseSuffixPatternMatch(false);
		requestMappingHandlerMapping.setUseTrailingSlashMatch(false);
		return requestMappingHandlerMapping;
	}

	@Override
	protected void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {

		exceptionResolvers.add(new PbExceptionResolver());

		super.configureHandlerExceptionResolvers(exceptionResolvers);
	}

	@Override
	protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

		converters.add(new PbJsonHttpMessageConverter());
		converters.add(new StringHttpMessageConverter());
		converters.add(new ByteArrayHttpMessageConverter());
		// converters.add(new SourceHttpMessageConverter());
		converters.add(new AllEncompassingFormHttpMessageConverter());
		converters.add(new FormHttpMessageConverter());

		super.configureMessageConverters(converters);
	}

	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/js/**").addResourceLocations("/js/");
		registry.addResourceHandler("/css/**").addResourceLocations("/css/");
		registry.addResourceHandler("/img/**").addResourceLocations("/img/");
		super.addResourceHandlers(registry);
	}

	@Override
	public HandlerMapping resourceHandlerMapping() {
		AbstractHandlerMapping handlerMapping = (AbstractHandlerMapping) super.resourceHandlerMapping();
		handlerMapping.setOrder(-1);
		return handlerMapping;
	}

	@Override
	protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		super.addArgumentResolvers(argumentResolvers);
	}


	/**
	 * 다국어 지원 
	 * message 의 위치를 WEB-INF 로 변경 
	 * 
	 * @return
	 */
	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setCacheSeconds(1);
		// messageSource.setResourceLoader(applicationContext);
		messageSource.setBasenames("WEB-INF/localization/messages");
		// messageSource.setUseCodeAsDefaultMessage(true);

		return messageSource;
	}

}
