package com.cy.pj.sys.service.impl;

import com.cy.pj.common.annotation.RequestLog;
import com.cy.pj.common.config.PageProperties;
import com.cy.pj.common.exception.ServiceException;
import com.cy.pj.common.util.Assert;
import com.cy.pj.common.vo.PageObject;
import com.cy.pj.sys.dao.SysUserDao;
import com.cy.pj.sys.dao.SysUserRoleDao;
import com.cy.pj.sys.entity.SysUser;
import com.cy.pj.sys.service.SysUserService;
import com.cy.pj.sys.vo.SysUserDeptVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
public class SysUserServiceImpl implements SysUserService {
	@Autowired(required = false)
	private SysUserDao sysUserDao;//依赖,DIP原则,依赖倒置,单一职责原则
	@Autowired
	private PageProperties pageProperties;
	@Autowired(required = false)
	private SysUserRoleDao sysUserRoleDao;
	
	@Override
	@RequestLog(operation = "查询用户")
	public PageObject<SysUserDeptVo> findPageObjects(
			String username, Integer pageCurrent) {
		//1.对参数进行校验
		Assert.isValid(pageCurrent != null && pageCurrent > 0, "当前页码值无效");
		//2.查询总记录数并进行校验
		int rowCount = sysUserDao.getRowCount(username);
		if (rowCount == 0)
			throw new ServiceException("没有找到对应记录");
		//3.查询当前页记录
		int pageSize = pageProperties.getPageSize();
		Page<Object> page = PageHelper.startPage(pageCurrent,pageSize);
		List<SysUserDeptVo> records =
				sysUserDao.findPageObjects(username);
		//4.对查询结果进行封装并返回
		return new PageObject<SysUserDeptVo>(records, pageCurrent, rowCount, pageSize);
	}
	
	@Override
	@RequestLog(operation = "参数检查")
	public int validById(Integer id, Integer valid, String modifiedUser) {
		//启动工作线程
		//获取缓存,更新线程
		//获得登录用户
		//获取用户权限
		//检查用户权限中是否包含此方法的权限
		//不包含则抛出异常
		//long t1 = System.currentTimeMillis();
		//1.合法性验证
		Assert.isValid(id != null && id > 0, "参数不合法");
		Assert.isValid(valid == 1 || valid == 0, "参数不合法");
		Assert.isEmpty(modifiedUser, "修改用户不能为空");
		//2.执行禁用或启用操作
		int rows = sysUserDao.validById(id, valid, modifiedUser);
		//3.判定结果,并返回
		if (rows == 0)
			throw new ServiceException("此记录可能已经不存在");
//		long t2 = System.currentTimeMillis();
//		log.info("SysUserServiceImpl.validById execute time {}",(t2-t1));
		//提交事务
		//回滚事务
		//3.返回结果
		return rows;
		
	}
	
	@Override
	@RequestLog(operation = "保存用户")
	public int saveObject(SysUser entity, Integer[] roleIds) {
		//1.参数校验
		Assert.isNull(entity, "保存对象不能为空");
		Assert.isEmpty(entity.getPassword(), "密码不能为空");
		Assert.isEmpty(entity.getUsername(), "用户名不能为空");
		Assert.isEmpty(roleIds, "至少要为用户分配角色");
//		int usernameRows = sysUserDao.isExist("sys_users", "username", entity.getUsername());
//		int emailRows = sysUserDao.isExist("sys_users", "email", entity.getEmail());
//		int mobileRows = sysUserDao.isExist("sys_users", "mobile", entity.getMobile());
//		Assert.isValid(usernameRows == 0, "用户名重复");
//		Assert.isValid(emailRows == 0, "邮箱重复");
//		Assert.isValid(mobileRows == 0, "电话重复");
		//2.保存用户自身信息
		//2.1对密码进行加密
		String source = entity.getPassword();
		String salt = UUID.randomUUID().toString();
		//String newpassword = DigestUtils.md5DigestAsHex((entity.getPassword() + salt).getBytes());
		SimpleHash sh = new SimpleHash(//Shiro框架
				"MD5",//algorithmName 算法名称
				source,//source 未加密的密码
				salt, //盐值
				1);//hashIterations表示加密次数
		//2.2重新存储到entity对象
		entity.setSalt(salt);
		entity.setPassword(sh.toHex());//sh.toHex()将加密的密码转成16进制
		//2.3持久化用户信息
		int rows = sysUserDao.insertObject(entity);
		//3.保存用户角色关系数据
		sysUserRoleDao.insertObjects(entity.getId(), roleIds);
		//4.返回结果
		return rows;
	}
	
	@Override
	@RequestLog(operation = "id查找")
	public Map<String, Object> findObjectById(Integer userId) {
		Assert.isValid(userId != null && userId > 0, "参数数据不合法");
		SysUserDeptVo user = sysUserDao.findObjectById(userId);
		Assert.isNull(user, "此用户已经不存在");
		List<Integer> roleIds = sysUserRoleDao.findRoleIdsByUserId(userId);
		HashMap<String, Object> map = new HashMap<>();
		map.put("user", user);
		map.put("roleIds", roleIds);
		return map;
	}
	
	@Override
	@RequestLog(operation = "更新用户")
	public int updateObject(SysUser entity, Integer[] roleIds) {
		Assert.isNull(entity, "保存对象不能为空");
		Assert.isEmpty(entity.getUsername(), "用户名不能为空");
		Assert.isEmpty(roleIds, "至少要为其分配角色");
//		int usernameRows = sysUserDao.isExist("sys_users", "username", entity.getUsername());
//		int emailRows = sysUserDao.isExist("sys_users", "email", entity.getEmail());
//		int mobileRows = sysUserDao.isExist("sys_users", "mobile", entity.getMobile());
//		Assert.isValid(usernameRows <= 1, "用户名重复");
//		Assert.isValid(emailRows <= 1, "邮箱重复");
//		Assert.isValid(mobileRows <= 1, "电话重复");
		int rows = sysUserDao.updateObject(entity);
		sysUserRoleDao.deleteById("sys_user_roles", "user_id", entity.getId());
		sysUserRoleDao.insertObjects(entity.getId(), roleIds);
		return rows;
	}
	
	@Override
	@RequestLog(operation = "重复值查询")
	public int isExists(String columnName, String columnValue) {
		//1.参数校验
		Assert.isEmpty(columnValue, "字段值不正确");
		//2.基于字段以及值进行统计查询
		int rows = sysUserDao.isExist("sys_users", columnName, columnValue);
		return rows;
	}
}
