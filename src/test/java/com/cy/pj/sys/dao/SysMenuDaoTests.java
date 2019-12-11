package com.cy.pj.sys.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class SysMenuDaoTests {
	@Autowired(required = false)
	private SysMenuDao sysMenuDao;
	
	@Test
	public void testFindPageObjects01() {
		long t1 = System.currentTimeMillis();
		List<Map<String, Object>> list = sysMenuDao.findObjects();
		long t2 = System.currentTimeMillis();
		System.out.println(t2 - t1);
		list.forEach(System.out::println);
	}
	
	@Test
	public void testFindPageObjects02() {
		Instant start = Instant.now();//JDK8
		List<Map<String, Object>> list = sysMenuDao.findObjects();
		Instant end = Instant.now();
		System.out.println("time:" + Duration.between(start, end).toMillis());
		list.forEach(System.out::println);
	}
}
