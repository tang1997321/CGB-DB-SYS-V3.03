package com.cy.pj.common.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @Aspect 注解描述的类型为一个切面类型
 * @Component 注解描述的类为spring中的一个bean对象类型(一般组件)
 * 说明:对于一个切面对象而言,可以理解为封装特定功能的一个扩展业务对象,此对象中通常会定义:
 * 1)切入点(PointCut):植入扩展功能的一个点(通常为目标方法集合)
 * 2)通知(Advice):使用于进行功能扩展的业务方法
 */
@Aspect
@Component
@Slf4j
public class SysLogAspect {
	
	//	public void logPointCut(){}//方法名随意
	@Pointcut("bean(sysUserServiceImpl)")
	public void doPointCut() {
	}
	
	@Around("doPointCut()")
	public Object around(ProceedingJoinPoint jp) throws Throwable {
		try {
			log.info("start:" + System.currentTimeMillis());
			Object result = jp.proceed();//调用下一个切面方法或目标方法
			log.info("after:" + System.currentTimeMillis());
			return result;
		} catch (Throwable e) {
			log.error(e.getMessage());
			throw e;
		}
	}
}
