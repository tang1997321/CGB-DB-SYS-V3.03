package com.cy.pj.sys.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class DaoCacheTests {
	@Autowired
	private SqlSessionFactory sqlSessionFactory;
	//默认一级缓存为SqlSession对象私有,多个SqlSession之间不会共享一个空间
	@Test
	public void testFirstCache() {
		//1.创建session对象
		SqlSession session = sqlSessionFactory.openSession();
		//2.访问数据库
		List<Object> records01 = session.selectList("com.cy.pj.sys.dao.SysMenuDao.findObjects");
		session = sqlSessionFactory.openSession();
		List<Object> records02 = session.selectList("com.cy.pj.sys.dao.SysMenuDao.findObjects");
		//3.释放资源
		session.close();
	}
	@Test
	public void testSecondCache(){
		//1.创建session对象
		SqlSession session = sqlSessionFactory.openSession();
		//2.访问数据库
		List<Object> records01 = session.selectList("com.cy.pj.sys.dao.SysMenuDao.findObjects");
		session = sqlSessionFactory.openSession();
		List<Object> records02 = session.selectList("com.cy.pj.sys.dao.SysMenuDao.findObjects");
		//3.释放资源
		session.close();
	}
}
