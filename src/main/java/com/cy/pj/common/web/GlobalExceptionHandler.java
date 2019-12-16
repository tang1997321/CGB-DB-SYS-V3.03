package com.cy.pj.common.web;

import com.cy.pj.common.vo.JsonResult;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @ControllerAdvice 注解描述的类为全局异常处理类, 此类可以定义全局的异常处理类
 */
//@ControllerAdvice
//@ResponseBody
@RestControllerAdvice//=@ControllerAdvice+@ResponseBody
public class GlobalExceptionHandler {
	@ExceptionHandler(RuntimeException.class)
	public JsonResult doHandleRunTimeException(RuntimeException e) {
		System.out.println("==globa==");
		e.printStackTrace();
		return new JsonResult(e);
	}
	
	@ExceptionHandler(ShiroException.class)
	public JsonResult doHandleShiroException(
			ShiroException e) {
		JsonResult r = new JsonResult();
		r.setState(0);
		if (e instanceof UnknownAccountException) {
			r.setMessage("账户不存在");
		} else if (e instanceof LockedAccountException) {
			r.setMessage("账户已被禁用");
		} else if (e instanceof IncorrectCredentialsException) {
			r.setMessage("密码不正确");
		} else if (e instanceof AuthorizationException) {
			r.setMessage("没有此操作权限");
		} else {
			r.setMessage("系统维护中");
		}
		e.printStackTrace();
		return r;
	}
}
