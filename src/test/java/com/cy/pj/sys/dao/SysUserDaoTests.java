package com.cy.pj.sys.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SysUserDaoTests {
	@Autowired(required = false)
	private SysUserDao sysUserDao;
	
	@Test
	public void testIsExists() {
		int rows = sysUserDao.isExist("sys_users","username", "admin");
		System.out.println(rows);
	}
}
