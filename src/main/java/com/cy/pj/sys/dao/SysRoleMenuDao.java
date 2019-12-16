package com.cy.pj.sys.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysRoleMenuDao extends BaseDao {
	/**
	 * 写入角色id和菜单关系数据
	 *
	 * @param roleId
	 * @param menuIds
	 * @return
	 */
	int insertObjects(
			@Param("roleId") Integer roleId,
			@Param("menuIds") Integer[] menuIds);
	
	/**
	 * 基于角色id获取菜单id
	 *
	 * @param roleIds
	 * @return
	 */
	List<Integer> findMenuIdsByRoleIds(
			@Param("roleIds") Integer[] roleIds);
}
