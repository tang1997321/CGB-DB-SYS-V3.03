package com.cy.pj.sys.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 日志持久化对象(po-persistent object)
 * 建议:java中所有用于封装的对象都实现序列化接口便于后续进行扩展
 */
@Data
public class SysLog implements Serializable{
	/**
	 * 序列化id,对象序列化时的唯一标识,不定义此id对象序列化时,也会自动生成一个id(这个id的生成基于类的结构信息自动生成),他会与对象对应的字节存储在一起,但是假如类的结构在反序列化时已经发生变化,可以保证原有字节能够正常的反序列化
	 */
	private static final long serialVersionUID = -7805612292944721867L;
	private Integer id;
	//用户名
	private String username;
	//用户操作
	private String operation;
	//请求方法
	private String method;
	//请求参数
	private String params;
	//执行时长
	private Long time;
	//ip地址
	private String ip;
	//创建日期
	private Date createdTime;
}
