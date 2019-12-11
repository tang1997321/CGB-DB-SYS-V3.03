package com.cy.pj.sys.dao;

import com.cy.pj.sys.entity.SysLog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class SysLogDaoTests {
	@Autowired(required = false)
	private SysLogDao sysLogDao;
	
	@Test
	public void testDeleteObjects() {
		int rows = sysLogDao.deleteObjects(100,200,300);
		System.out.println(rows);
	}
	
//	@Test
//	public void testGetRowCount() {
//		int rowCount = sysLogDao.getRowCount("tmooc");
//		System.out.println("rowCount =" + rowCount);
//	}
	
	@Test
	public void testFindPageObjects() {
		List<SysLog> list = sysLogDao.findPageObjects("admin");
//		for (SysLog log : list) {
//			System.out.println(log);
//		}
		list.forEach(System.out::println);
	}
	
}
