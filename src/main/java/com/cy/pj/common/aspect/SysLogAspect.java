package com.cy.pj.common.aspect;

import com.cy.pj.common.annotation.RequestLog;
import com.cy.pj.common.util.IPUtils;
import com.cy.pj.sys.entity.SysLog;
import com.cy.pj.sys.service.SysLogService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * @Aspect 注解描述的类型为一个切面类型
 * @Component 注解描述的类为spring中的一个bean对象类型(一般组件)
 * 说明:对于一个切面对象而言,可以理解为封装特定功能的一个扩展业务对象,此对象中通常会定义:
 * 1)切入点(PointCut):织入扩展功能的一个点(通常为目标方法集合)
 * 2)通知(Advice):是用于进行功能扩展的业务方法
 */
//@Order(1)//切面的优先级,数字越小,优先级越高
@Aspect
@Component
@Slf4j
public class SysLogAspect {
	@Autowired
	private SysLogService sysLogService;
	
	//private Logger log=Logger.getLogger(SysLogAspect.class);
	//public void logPointCut(){}//方法名随意
	//bean(bean的名字)为切入点表达式
	//bean(*ServiceImpl)名字末尾为ServiceImpl
	@Pointcut("@annotation(com.cy.pj.common.annotation.RequestLog)")
	public void doPointCut() {
	}
	
	@Around("doPointCut()")
	public Object around(ProceedingJoinPoint jp) throws Throwable {
		long t1 = System.currentTimeMillis();
		Object result = jp.proceed();//调用下一个切面方法或目标方法
		long t2 = System.currentTimeMillis();
		//将用户行为信息存储到数据库中
		saveLog(jp, t2 - t1);
		return result;
	}
	
	//将用户星系存储到数据库中
	private void saveLog(ProceedingJoinPoint jp, long time) throws NoSuchMethodException, JsonProcessingException {
		//1.获取用户行为日志(谁(ip+用户名)在什么时间访问了什么方法,传递什么参数,执行时间,执行什么参数)
		//2.1获取目标方法
		//2.1.1获取方法签名(记录的是目标方法的签名)
		MethodSignature ms = (MethodSignature) jp.getSignature();
		//2.1.2获取目标类型的字节码对象
		Class<?> targetClass = jp.getTarget().getClass();
		//2.1.3获取目标方法对象
		Method targetMethod = targetClass.getDeclaredMethod(ms.getName(), ms.getParameterTypes());
		//2.1.4获取目标方法上的注解
		RequestLog rLog = targetMethod.getAnnotation(RequestLog.class);
		//2.1.5获取操作名
		String operation = rLog.operation();
		//2.1.6获取方法全名
		String dType = targetClass.getName();
		String methodName = ms.getName();
		String targetClassMethod = dType + "." + methodName;
		//2.2获取方法参数
		//String params = Arrays.toString(jp.getArgs());
		//值为json格式会自动转换
		String params = new ObjectMapper().writeValueAsString(jp.getArgs());
		//2.对信息进行封装(SysLog)
		SysLog log = new SysLog();
		log.setIp(IPUtils.getIpAddr());
		log.setUsername("admin");
		log.setOperation(operation);
		log.setMethod(targetClassMethod);
		log.setParams(params);
		log.setTime(time);
		log.setCreatedTime(new Date());
		//3.将信息写入到数据库
//		new Thread(() -> sysLogService.saveObject(log)).start();
		sysLogService.saveObject(log);
	}
}
