package com.cy.pj.sys.service.impl;

import com.cy.pj.common.annotation.RequestLog;
import com.cy.pj.common.config.PageProperties;
import com.cy.pj.common.exception.ServiceException;
import com.cy.pj.common.util.Assert;
import com.cy.pj.common.vo.PageObject;
import com.cy.pj.sys.dao.SysRoleDao;
import com.cy.pj.sys.dao.SysRoleMenuDao;
import com.cy.pj.sys.dao.SysUserRoleDao;
import com.cy.pj.sys.entity.SysRole;
import com.cy.pj.sys.service.SysRoleService;
import com.cy.pj.sys.vo.CheckBox;
import com.cy.pj.sys.vo.SysRoleMenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysRoleServiceImpl implements SysRoleService {
	@Autowired(required = false)
	private SysRoleDao sysRoleDao;
	@Autowired
	private PageProperties pageProperties;
	@Autowired(required = false)
	private SysRoleMenuDao sysRoleMenuDao;
	@Autowired(required = false)
	private SysUserRoleDao sysUserRoleDao;
	
	@Override
	public int deleteObject(Integer id) {
		//1.参数校验
		Assert.isValid(id != null && id > 0, "请先选择");
		//2.删除关联数据
		sysRoleMenuDao.deleteById("sys_role_menus", "menu_id", id);
		sysUserRoleDao.deleteById("sys_user_roles", "role_id", id);
		//3.删除自身数据并校验结果
		int rows = sysRoleDao.deleteObject(id);
		if (rows == 0)
			throw new ServiceException("记录不存在");
		//4.返回结果
		return rows;
	}
	
	@Override
	public PageObject findPageObjects(String name, Integer pageCurrent) {
		//1.参数校验
		Assert.isValid(pageCurrent != null && pageCurrent > 0, "页码值无效");
		//2.查询总记录数并校验
		int rowCount = sysRoleDao.getRowCount(name);
		if (rowCount == 0)
			throw new ServiceException("记录不存在");
		//3.查询当前页日志记录
		int pageSize = pageProperties.getPageSize();
		int startIndex = (pageCurrent - 1) * pageSize;
		List<SysRole> records = sysRoleDao.findPageObjects(name, startIndex, pageSize);
		//4.封装结果并返回
		return new PageObject(records, pageCurrent, rowCount, pageSize);
	}
	
	@Override
	public int saveObject(SysRole entity, Integer[] menuIds) {
		//1.校验参数
		Assert.isNull(entity, "保存对象不能为空");
		Assert.isEmpty(entity.getName(), "角色名不允许为空");
		Assert.isEmpty(menuIds, "必须为角色分配权限");
		//2.添加数据
		int rows = sysRoleDao.insertObject(entity);
		sysRoleMenuDao.insertObjects(entity.getId(), menuIds);
		//3.返回结果
		return rows;
	}
	
	@Override
	public SysRoleMenuVo findObjectById(Integer id) {
		//1.合法性验证
		Assert.isValid(id != null && id > 0, "id的值不合法");
		//2.基于id查询角色自身的信息
		SysRoleMenuVo rm = sysRoleDao.findObjectById(id);
		System.out.println(rm);
		//3.验证结果并返回
		if (rm == null)
			throw new ServiceException("此记录已经不存在");
		//4.返回查询结果
		return rm;
	}
	
	@Override
	public int updateObject(SysRole entity, Integer[] menuIds) {
		//1.校验参数
		Assert.isNull(entity, "保存对象不能为空");
		Assert.isEmpty(entity.getName(), "角色名不允许为空");
		Assert.isEmpty(menuIds, "必须为角色分配权限");
		//2.添加数据
		int rows = sysRoleDao.updateObject(entity);
		sysRoleMenuDao.deleteById("sys_role_menus", "role_id", entity.getId());
		sysRoleMenuDao.insertObjects(entity.getId(), menuIds);
		//3.返回结果
		return rows;
	}
	
	@Override
	public List<CheckBox> findObjects() {
		return sysRoleDao.findObjects();
	}
}
