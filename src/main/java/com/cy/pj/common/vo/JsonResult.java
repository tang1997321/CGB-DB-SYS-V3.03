package com.cy.pj.common.vo;

import com.cy.pj.common.util.ShiroUtil;
import lombok.Data;

import java.io.Serializable;

/**
 * 基于此对象封装控制层对象的响应结果,在此对象中应该包含返回客户端的数据以及一个状态码和状态信息
 */
@Data
public class JsonResult implements Serializable {
	private static final long serialVersionUID = -5761551392856441745L;
	/*状态码*/
	private int state = 1;//1 ok,0 error
	/*状态码对应的信息*/
	private String message = "ok";
	/*正常数据*/
	private Object data;
	
	public JsonResult() {
	}
	
	public JsonResult(String message) {
		this.message = message;
	}
	
	public JsonResult(Object data) {
		this.data = data;
	}
	
	public JsonResult(Throwable e) {
		this.state = 0;
		this.message = e.getMessage();
	}
}
