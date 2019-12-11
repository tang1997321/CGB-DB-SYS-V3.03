package com.cy.pj.sys.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysRoleMenuDao extends BaseDao{
	/**
	 * 写入角色和菜单关系数据
	 * @param roleId
	 * @param menuIds
	 * @return
	 */
	int insertObjects(
			@Param("roleId") Integer roleId,
			@Param("menuIds") Integer[] menuIds);
	
}
