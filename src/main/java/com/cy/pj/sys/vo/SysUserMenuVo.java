package com.cy.pj.sys.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 借助此类对象封装用户(登录用户)对应的菜单信息
 */
@Data
public class SysUserMenuVo implements Serializable {//XxxDao/SysUserService/PageController
	private static final long serialVersionUID = -6532648335943768546L;
	private Integer id;
	private String name;
	private String url;
	private List<SysUserMenuVo> childMenus;
	
}
