package com.webctl.pb.config;

import java.util.Properties;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.hibernate.ejb.HibernatePersistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import com.jolbox.bonecp.BoneCPDataSource;
import com.webctl.pb.api.aspect.TestApiAspect;

/**
 * application context config
 * 
 * @author josw
 *
 */
@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@PropertySource(value = "classpath:config.properties")
@EnableJpaRepositories("com.webctl.pb")
@ComponentScan(basePackages = { "com.webctl.pb" }, excludeFilters = { @ComponentScan.Filter(Controller.class),
		@ComponentScan.Filter(Configuration.class) })
@EnableAspectJAutoProxy
public class AppConfig {

	@Inject
	Environment env;

	@Bean
	public StandardServletMultipartResolver multipartResolver() {
		return new StandardServletMultipartResolver();
	}


	@Bean
	TestApiAspect testApiAspect() {
		return new TestApiAspect();
	}


	/**
	 * datasource 설정 
	 * ticket 을 제외한 모든 data 사용 용도
	 * bonecp 사용 
	 * 
	 * @return
	 */
	@Bean(destroyMethod = "close")
	public DataSource dataSource() {
		BoneCPDataSource dataSource = new BoneCPDataSource();

		dataSource.setDriverClass(env.getProperty("jdbc.driver"));
		dataSource.setJdbcUrl(env.getProperty("jdbc.url"));
		dataSource.setUsername(env.getProperty("jdbc.username"));
		dataSource.setPassword(env.getProperty("jdbc.userpass"));

		return dataSource;
	}

	/**
	 * jpa 설정 
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 */
	@Bean
	public JpaTransactionManager transactionManager() throws ClassNotFoundException {
		JpaTransactionManager transactionManager = new JpaTransactionManager();

		transactionManager.setEntityManagerFactory(entityManagerFactoryBean().getObject());

		return transactionManager;
	}

	/**
	 * 
	 * jpa - hibernate 설정 
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 */
	@Bean(name = "entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() throws ClassNotFoundException {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

		entityManagerFactoryBean.setDataSource(dataSource());
		entityManagerFactoryBean.setPackagesToScan("com.webctl.pb");
		entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistence.class);

		Properties jpaProterties = new Properties();
		jpaProterties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
		jpaProterties.put("hibernate.format_sql", true);
		jpaProterties.put("hibernate.ejb.naming_strategy", "org.hibernate.cfg.ImprovedNamingStrategy");
		jpaProterties.put("hibernate.show_sql", true);

		entityManagerFactoryBean.setJpaProperties(jpaProterties);

		return entityManagerFactoryBean;
	}

	
	
	
	/**
	 * 
	 * config property 설정 
	 * 
	 * @return
	 */
	@Bean
	public static PropertySourcesPlaceholderConfigurer properties() {
		PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
		Resource[] resources = new ClassPathResource[] { new ClassPathResource("config.properties") };
		pspc.setLocations(resources);
		pspc.setIgnoreUnresolvablePlaceholders(true);
		return pspc;
	}

	
	/**
	 *  cache
	 */
//	@Bean(name = "dataMemcachedSockPool", initMethod = "initialize", destroyMethod = "shutDown")
//	public SockIOPool sockIOPool() {
//		SockIOPool sockIOPool = SockIOPool.getInstance("data");
//		sockIOPool.setServers(env.getProperty("memcached.host.master").split(","));
//		sockIOPool.setNagle(false);
//		sockIOPool.setSocketConnectTO(4000);
//		sockIOPool.setSocketTO(4000);
//		sockIOPool.setInitConn(1);
//		sockIOPool.setMinConn(21);
//		sockIOPool.setMaxConn(10240);
//		sockIOPool.setMaxIdle(21600000);
//		sockIOPool.setAliveCheck(false);
//		sockIOPool.setFailback(false);
//		sockIOPool.setFailover(false);
//
//		return sockIOPool;
//	}
//
//	@Bean(name = "dataMemcachedClient")
//	@DependsOn(value = "dataMemcachedSockPool")
//	public MemCachedClient memCachedClient() {
//		MemCachedClient memCachedClient = new MemCachedClient("data");
//		memCachedClient.setSanitizeKeys(false);
//		return memCachedClient;
//	}

//	@Bean
//	public CacheAspect cacheAspect() {
//
//		CacheAspect cacheAspect = new CacheAspect();
//		cacheAspect.setClient(memCachedClient());
//		cacheAspect.setNamespace(env.getProperty("memcached.namespace"));
//		cacheAspect.setEnabled(env.getProperty("memcached.enabled", Boolean.class));
//		return cacheAspect;
//	}

}
