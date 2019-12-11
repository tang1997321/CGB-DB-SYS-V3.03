package com.cy.pj.common.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class SysLogAspect {
	@Pointcut("bean(sysUserServiceImpl)")
	public void logPointCut(){}
	
	@Around("logPointCut()")
	public Object around(ProceedingJoinPoint jp) throws Throwable{
		try {
			log.info("start:"+System.currentTimeMillis());
			Object result=jp.proceed();//调用下一个切面方法或目标方法
			log.info("after:"+System.currentTimeMillis());
			return result;
		} catch (Throwable e) {
			log.error(e.getMessage());
			throw e;
		}
	}
}
