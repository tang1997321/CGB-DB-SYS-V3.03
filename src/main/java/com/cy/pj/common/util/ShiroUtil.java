package com.cy.pj.common.util;

import com.cy.pj.sys.entity.SysUser;
import org.apache.shiro.SecurityUtils;

public class ShiroUtil {
	public static String getUsername(){
		return getLoginUser().getUsername();
	}
	public static SysUser getLoginUser(){
		return (SysUser) SecurityUtils.getSubject().getPrincipal();
	}
}
