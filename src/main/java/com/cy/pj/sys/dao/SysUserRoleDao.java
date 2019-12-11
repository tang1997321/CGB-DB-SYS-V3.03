package com.cy.pj.sys.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysUserRoleDao extends BaseDao {
	/**
	 * 写入用户和角色的关系数据
	 * @param userId
	 * @param roleIds
	 * @return
	 */
	int insertObjects(
			@Param("userId") Integer userId,
			@Param("roleIds") Integer[] roleIds);
	List<Integer> findRoleIdsByUserId(Integer id);
}
