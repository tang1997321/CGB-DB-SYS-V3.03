package com.cy.pj.sys.controller;

import com.cy.pj.common.util.ShiroUtil;
import com.cy.pj.sys.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 基于此controller呈现页面
 */
@Controller
@RequestMapping("/")
public class PageController {
	private AtomicLong al = new AtomicLong(0);
	@Autowired
	private SysMenuService sysMenuService;
	
	public PageController() {
		//此类型的对象线程安全?(如何保证的?底层CAS算法-借助CPU硬件优势)
		System.out.println("PageController");
	}
	
	//rest风格(一种软件架构编码风格)的url映射
	//{}表为rest表达式
	//@pathVariable用于告诉spring mvc参数值从url中获取
	@RequestMapping("{module}/{moduleUI}")
	public String doModuleUI(@PathVariable String moduleUI) {
		return "sys/" + moduleUI;
	}
	
	//	@RequestMapping("menu/menu_list")
//	public String doMenuUI() {
//		return "sys/menu_list";
//	}
	@RequestMapping("doLoginUI")
	public String doLoginUI() {
		return "login";
	}
	
	@RequestMapping("doIndexUI")
	public String doGoodsUI(Model model) {
		//记录页面访问次数(原子性)
		String tName = Thread.currentThread().getName();
		System.out.println("thread.tname=" + tName);
		System.out.println(al.incrementAndGet());
		model.addAttribute("username", ShiroUtil.getUsername());
		model.addAttribute("userMenus", sysMenuService.findUserMenusByUserId(ShiroUtil.getLoginUser().getId()));
		return "starter";
	}

//	@RequestMapping("log/log_list")
//	public String doLogUI() {
//		return "sys/log_list";
//	}
	
	@RequestMapping("doPageUI")
	public String doPageUI() {
		return "common/page";
	}
}
