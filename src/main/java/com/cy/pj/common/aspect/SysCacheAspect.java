package com.cy.pj.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SysCacheAspect {
	
	@Around("@annotation(com.cy.pj.common.annotation.RequiredCache)")
	public Object around(ProceedingJoinPoint jp) throws Throwable{
		System.out.println("get data from cache");
		Object obj = jp.proceed();
		return obj;
	}
}
