package com.cy.pj.common.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class SysExceptionAspect {
	@AfterThrowing(pointcut = "bean(*ServiceImpl)", throwing = "e")
	public void doHandleException(JoinPoint jp, Exception e) {
		MethodSignature ms = (MethodSignature) jp.getSignature();
		log.error("{}exception msg is {}", ms.getName(), e.getMessage());
	}
}
