package com.cy.pj.sys.dao;

import com.cy.pj.sys.entity.SysUser;
import com.cy.pj.sys.vo.SysUserDeptVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SysUserDao extends BaseDao {
	
	/**
	 * 根据id禁用用户
	 * @param id
	 * @param valid
	 * @param modifiedUser
	 * @return
	 */
	int validById(Integer id, Integer valid, String modifiedUser);
	
	int getRowCount(@Param("username") String username);
	
	/**
	 * 基于用户名、起始索引、页面大小查询数据
	 * @param username
	 * @return
	 */
	List<SysUserDeptVo> findPageObjects(
			@Param("username") String username);
	
	/**
	 * 插入用户数据
	 * @param entity
	 * @return
	 */
	int insertObject(SysUser entity);
	
	/**
	 * 基于用户id查询信息
	 * @param id
	 * @return
	 */
	SysUserDeptVo findObjectById(Integer id);
	
	/**
	 * 基于用户信息更新数据
	 * @param entity
	 * @return
	 */
	int updateObject(SysUser entity);
	
	/**
	 * 基于用户名查找用户信息
	 * @param username
	 * @return
	 */
	@Select("select * from sys_users where username=#{username}")
	SysUser findUserByUserName(String username);
}
