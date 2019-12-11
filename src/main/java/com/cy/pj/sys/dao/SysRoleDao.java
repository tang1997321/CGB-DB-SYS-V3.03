package com.cy.pj.sys.dao;

import com.cy.pj.sys.entity.SysRole;
import com.cy.pj.sys.vo.CheckBox;
import com.cy.pj.sys.vo.SysRoleMenuVo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface SysRoleDao {
	/**
	 * 基于条件(模糊查询)统计角色总数
	 * @param name
	 * @return 记录总数
	 */
	int getRowCount(@Param("name") String name);
	
	/**
	 * 基于条件查询当前页要呈现的数据
	 * @param name
	 * @param startIndex
	 * @param pageSize
	 * @return 当前页呈现的数据
	 */
	List<SysRole> findPageObjects(@Param("name") String name, @Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize);
	
	/**
	 * 基于id删除角色
	 * @param id
	 * @return
	 */
	@Delete("delete from sys_roles where id =#{id}")
	int deleteObject(Integer id);
	
	/**
	 * 写入自身信息
	 * @param entity
	 * @return
	 */
	int insertObject(SysRole entity);
	
	/**
	 * 通过id查询信息
	 * @param id
	 * @return
	 */
	SysRoleMenuVo findObjectById(Integer id);
	
	int updateObject(SysRole entity);
	
	/**
	 * 查询所有角色的id,name
	 * @return
	 */
	@Select("select id,name from sys_roles")
	List<CheckBox> findObjects();
}
