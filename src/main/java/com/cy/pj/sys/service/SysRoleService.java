package com.cy.pj.sys.service;

import com.cy.pj.common.vo.PageObject;
import com.cy.pj.sys.entity.SysRole;
import com.cy.pj.sys.vo.CheckBox;
import com.cy.pj.sys.vo.SysRoleMenuVo;

import java.util.List;

public interface SysRoleService {
	int deleteObject(Integer id);
	PageObject<SysRole> findPageObjects(String name, Integer pageCurrent);
	int saveObject(SysRole entity, Integer[] menuIds);
	SysRoleMenuVo findObjectById(Integer id) ;
	int updateObject(SysRole entity, Integer[] menuIds);
	List<CheckBox> findObjects();
}
