package com.cy.pj.sys.dao;

import com.cy.pj.sys.entity.SysRole;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
public class SysRoleDaoTests {
	@Autowired(required = false)
	private SysRoleDao sysRoleDao;
	
	@Test
	public void testGetRowcount() {
		int rowCount = sysRoleDao.getRowCount("运维");
		log.info("查询到{}条记录" + rowCount);//{}为日志的占位符
	}
	
	@Test
	public void findPageObjects() {
		List<SysRole> records = sysRoleDao.findPageObjects(null, 1, 3);
		records.forEach(System.out::println);
	}
}
