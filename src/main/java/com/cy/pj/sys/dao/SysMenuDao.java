package com.cy.pj.sys.dao;

import com.cy.pj.common.vo.Node;
import com.cy.pj.sys.entity.SysMenu;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysMenuDao {
	int updateObject(SysMenu entity);
	/**
	 * 数据的持久化
	 * @param entity 被持久化的对象
	 * @return 更新的行数
	 */
	int insertObject(SysMenu entity);
	
	/**
	 * 查询菜单节点信息(包含id,name,parentId)
	 *
	 * @return
	 */
	@Select("select id,name,parentId from sys_menus")
	List<Node> findZtreeMenuNodes();
	
	/**
	 * 统计当前菜单中子菜单的数量
	 *
	 * @param id 当前菜单id
	 * @return 子菜单数量
	 */
	@Select("select count(*) from sys_menus where parentid=#{id}")
	int getChildCount(Integer id);
	
	/**
	 * 基于id删除当前菜单
	 *
	 * @param id
	 * @return
	 */
	@Delete("delete from sys_menus where id=#{id}")
	int deleteObject(Integer id);
	
	/**
	 * 查询所有菜单以及对应上级菜单名称,一行记录映射为一个map对象(key为表中字段名,value为每行记录字段对应的值)
	 *
	 * @return
	 */
	List<Map<String, Object>> findObjects();
	
	/**
	 * 基于菜单id查找权限标识
	 * @param menuIds
	 * @return
	 */
	List<String> findPermissions(
			@Param("menuIds")
					Integer[] menuIds);
}
