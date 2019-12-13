package com.cy.pj.sys.service.impl;

import com.cy.pj.common.annotation.RequestLog;
import com.cy.pj.common.exception.ServiceException;
import com.cy.pj.common.util.Assert;
import com.cy.pj.common.vo.Node;
import com.cy.pj.sys.dao.SysMenuDao;
import com.cy.pj.sys.dao.SysRoleMenuDao;
import com.cy.pj.sys.entity.SysMenu;
import com.cy.pj.sys.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

@Service
public class SysMenuServiceImpl implements SysMenuService {
	@Autowired(required = false)
	private SysMenuDao sysMenuDao;
	@Autowired(required = false)
	private SysRoleMenuDao sysRoleMenuDao;
	
	/**
	 * @CacheEvict 描述方法是,表示要清除缓存
	 * 1)value:表示缓存名称
	 * 2)allEntries:表示清空所有缓存
	 * @param id
	 * @return
	 */
	@CacheEvict(value = "menuCache",allEntries = true)
	@Override
	@RequestLog(operation = "删除菜单")
	public int deleteObject(Integer id) {
		//1.判定参数有效性
		Assert.isValid(id != null && id > 1, "id值无效");
		//2.查询当前菜单是否有子菜单,并进行校验
		int childCount = sysMenuDao.getChildCount(id);
		if (childCount > 0)
			throw new ServiceException("请先删除子菜单");
		//3.删除角色菜单关系数据
		sysRoleMenuDao.deleteById("sys_role_menus","menu_id",id);
		//4.删除菜单自身数据
		int rows = sysMenuDao.deleteObject(id);
		if (rows == 0)
			throw new ServiceException("记录已经不存在");
		//5.返回结果
		return rows;
	}
	
	/**
	 * @Cacheable 注解描述方法是,表示要从cache取数据,cache没有调用业务方法查数据
	 * 查到数据放到cache中(cache底层实现了一个map接口)
	 * 1)value表示cache的名字
	 * @return
	 */
	@Cacheable(value = "menuCache")//放入缓存
	@Override
	@RequestLog(operation = "列出菜单")
	public List<Map<String, Object>> findObjects() {
		List<Map<String, Object>> list = sysMenuDao.findObjects();
		if (list == null || list.size() == 0) {
			throw new ServiceException("没有对应的菜单信息");
		}
		return list;
	}
	
	@Override
	public List<Node> findZtreeMenuNodes() {
		return sysMenuDao.findZtreeMenuNodes();
	}
	
	@Override
	@RequestLog(operation = "保存菜单")
	public int saveObject(SysMenu entity) {
		//1.参数校验
		Assert.isNull(entity,"保存对象不能为空");
		Assert.isEmpty(entity.getName(),"菜单名不能为空");
		//2.持久化数据
		int rows = sysMenuDao.insertObject(entity);
		//3.返回结果
		return rows;
	}
	@Override
	@RequestLog(operation = "更新菜单")
	public int updateObject(SysMenu entity) {
		//1.参数校验
		Assert.isNull(entity,"保存对象不能为空");
		Assert.isEmpty(entity.getName(),"菜单名不能为空");
		//2.持久化数据
		int rows = sysMenuDao.updateObject(entity);
		//3.返回结果
		return rows;
	}
}
