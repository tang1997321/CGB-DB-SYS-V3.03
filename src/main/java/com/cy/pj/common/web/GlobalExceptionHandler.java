package com.cy.pj.common.web;

import com.cy.pj.common.vo.JsonResult;
import org.springframework.web.bind.annotation.*;

/**
 * @ControllerAdvice 注解描述的类为全局异常处理类,此类可以定义全局的异常处理类
 */
//@ControllerAdvice
//@ResponseBody
@RestControllerAdvice//=@ControllerAdvice+@ResponseBody555555555555555
public class GlobalExceptionHandler {
	@ExceptionHandler(RuntimeException.class)
	public JsonResult doHandleRunTimeException(RuntimeException e) {
		System.out.println("==globa==");
		e.printStackTrace();
		return new JsonResult(e);
	}
}
