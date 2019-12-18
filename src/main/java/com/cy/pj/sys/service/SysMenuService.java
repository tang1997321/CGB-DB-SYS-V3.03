package com.cy.pj.sys.service;

import com.cy.pj.common.vo.Node;
import com.cy.pj.sys.entity.SysMenu;
import com.cy.pj.sys.vo.SysRoleMenuVo;

import java.util.List;
import java.util.Map;

public interface SysMenuService {
	int deleteObject(Integer id);
	
	List<Map<String, Object>> findObjects();
	
	List<Node> findZtreeMenuNodes();
	
	/**
	 * 保存菜单数据
	 *
	 * @param entity
	 * @return
	 */
	int saveObject(SysMenu entity);
	
	/**
	 * 更新菜单数据
	 *
	 * @param entity
	 * @return
	 */
	int updateObject(SysMenu entity);
	
	List<SysRoleMenuVo> findUserMenusByUserId(Integer id);
}
