package com.cy.pj.common.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;

@Service
@Aspect
public class SysTimeAspect {
	/**
	 * 目标方法
	 */
	@Pointcut("bean(sysMenuServiceImpl)")
	public void doTime() {
	}
	
	/**
	 * 目标方法执行之前
	 *
	 * @param jp
	 */
	@Before("doTime()")
	public void doBefore(JoinPoint jp) {
		System.out.println("time doBefore()");
	}
	
	/**
	 * 目标方法之后
	 */
	@After("doTime()")
	public void doAfter() {
		System.out.println("time doAfter()");
	}
	
	/**
	 * 核心业务正常结束时执行
	 * 说明：假如有after，先执行after,再执行returning
	 */
	@AfterReturning("doTime()")
	public void doAfterReturning() {
		System.out.println("time doAfterReturning");
	}
	
	/**
	 * 核心业务出现异常时执行
	 * 说明：假如有after，先执行after,再执行Throwing
	 */
	@AfterThrowing(value = "doTime()", throwing = "e")
	public void doAfterThrowing(JoinPoint jp, Exception e) {//连接点
		MethodSignature ms = (MethodSignature) jp.getSignature();
		System.out.println(e.getMessage());
		Method targetMethod = ms.getMethod();
		System.out.println("targetMethod=" + targetMethod);
		System.out.println("time doAfterThrowing");
	}
	
	@Around("doTime()")
	public Object doAround(ProceedingJoinPoint jp)
			throws Throwable {
		System.out.println("doAround.before");
		Object obj = jp.proceed();//检查类是否有@Before通知,然后检查是否有下一个切面,没有则执行目标方法
		System.out.println("doAround.after");
		return obj;//检查勒种是否有@After通知,有则执行after
	}
}


