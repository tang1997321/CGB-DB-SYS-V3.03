package com.cy.pj.common.config;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.LinkedHashMap;

/**
 * shiro框架配置类
 */
@Configuration
public class SpringShiroConfig {
	/**
	 * shiro框架的核心安全管理器对象
	 *
	 * @bean 注解描述的方法, 其返回值会交给spring管理, spring存储
	 * 此对象时,默认会以方法名为key
	 */
	@Bean
	public SecurityManager securityManager(Realm realm) {
		DefaultWebSecurityManager sManager = new DefaultWebSecurityManager();
		sManager.setRealm(realm);
		return sManager;
	}
	
	/**
	 * 配置过滤器工厂(通过此工厂创建大量过滤器,通过过滤器对请求进行过滤)
	 */
	@Bean
	public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
		//创建过滤器工厂bean
		ShiroFilterFactoryBean fBean = new ShiroFilterFactoryBean();
		//设置安全管理器
		fBean.setSecurityManager(securityManager);
		//设置url
		fBean.setLoginUrl("/doLoginUI");
		//定义map指定请求过滤规则(哪些资源允许匿名访问,哪些必须认证访问)
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		//静态资源允许匿名访问:"anon"
		map.put("/bower_components/**", "anon");
		map.put("/build/**", "anon");
		map.put("/dist/**", "anon");
		map.put("/plugins/**", "anon");
		map.put("/user/doLogin", "anon");
		map.put("/doLogout", "logout");//logout为退出时使用
		//除了匿名访问的资源,其它都要认证("authc")后访问
		map.put("/**", "authc");
		fBean.setFilterChainDefinitionMap(map);
		return fBean;
	}
	
//	//配置bean对象的生命周期管理(SpringBoot可以不配置)。
//	@Bean
//	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
//		return new LifecycleBeanPostProcessor();
//	}
//
//	//通过如下配置要为目标业务对象创建代理对象（SpringBoot中可省略）。
//	@DependsOn("lifecycleBeanPostProcessor")
//	@Bean
//	public DefaultAdvisorAutoProxyCreator newDefaultAdvisorAutoProxyCreator() {
//		return new DefaultAdvisorAutoProxyCreator();
//	}
	
	//配置授权的advisor,通过此advisor告诉框架底层,要为指定的对象创建代理对象,然后对指定业务方法进行授权检查
	@Bean
	public AuthorizationAttributeSourceAdvisor newAuthorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
		advisor.setSecurityManager(securityManager);
		return advisor;
	}
}
