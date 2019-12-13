package com.cy.pj.sys.dao;

import com.cy.pj.sys.entity.SysRole;
import org.apache.ibatis.annotations.Param;
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
	
	//测试mybatis一级缓存(localC ache):sqlSession对象私有缓存(BaseExecutor)
	@Test
	public void testFirstCache() {//默认一级缓存为SqlSession对象私有,多个SqlSession之间不会共享一个空间
		//1.创建session对象
		SqlSession session = sqlSessionFactory.openSession();
		//2.访问数据库
		List<Object> records01 = session.selectList("com.cy.pj.sys.dao.SysMenuDao.findObjects");
//		session = sqlSessionFactory.openSession();
		List<Object> records02 = session.selectList("com.cy.pj.sys.dao.SysMenuDao.findObjects");
		//3.释放资源
		session.close();
	}
	
	//测试mybatis中的二级缓存;可以多个sqlSession对象共性的缓存,二级缓存对象的key与命名空间有关
	@Test
	public void testSecondCache() {
		//1.创建session对象
		SqlSession session = sqlSessionFactory.openSession();
		//2.访问数据库
		List<Object> records01 = session.selectList("com.cy.pj.sys.dao.SysMenuDao.findObjects");
		session.commit();
//		session.close();//session事务提交或close时才会向二级缓存存数据
		session = sqlSessionFactory.openSession();
		List<Object> records02 = session.selectList("com.cy.pj.sys.dao.SysMenuDao.findObjects");
		System.out.println(records01 == records02);
		//3.释放资源
		session.close();
	}
	@Test
	public void testSecondLevelCache02() {
		SqlSession session1 = sqlSessionFactory.openSession();
		SqlSession session2 = sqlSessionFactory.openSession();
		List<SysRole> selectList1 = session1.selectList("com.cy.pj.sys.dao.SysRoleDao.findPageObjects", "java");
		session1.close();
		List<SysRole> selectList2 = session2.selectList("com.cy.pj.sys.dao.SysRoleDao.findPageObjects", "java");
		session2.close();
		System.out.println(session1 == session2);
	}
}
