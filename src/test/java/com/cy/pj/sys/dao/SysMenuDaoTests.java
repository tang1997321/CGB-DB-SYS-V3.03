package com.cy.pj.sys.dao;

import com.cy.pj.sys.entity.SysRole;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
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
	@Autowired
	private SqlSessionFactory sqlSessionFactory;

	public void findRoles01() {
		//1.获取sqlSession对象(DefaultSqlSession-线程不安全)
		SqlSession sqlSession=sqlSessionFactory.openSession();
		System.out.println("sqlSession="+sqlSession);
		//2.执行查询操作
		String statement="com.cy.pj.sys.dao.SysRoleDao.findPageObjects";
		Map<String,Object> parameterMap=new HashMap<>();
		parameterMap.put("name", "");
		parameterMap.put("startIndex", 0);
		parameterMap.put("pageSize", 3);
		List<SysRole> list=sqlSession.selectList(statement, parameterMap);
		System.out.println("list.size="+list.size());
		//3.释放资源
		sqlSession.close();
	}
	public void findRoles02() {
		String statement="com.cy.pj.sys.dao.SysRoleDao.findPageObjects";
		Map<String,Object> parameterMap=new HashMap<>();
		parameterMap.put("name", "");
		parameterMap.put("startIndex", 0);
		parameterMap.put("pageSize", 3);
		List<SysRole> list=
				sqlSessionTemplate.selectList(statement, parameterMap);
		System.out.println(list.size());
		//sqlSessionTemplate.commit();不需要
		//sqlSessionTemplate.close(); 我们不关
	}
	//直接实使用SqlSession（实现类为DefaultSqlSession）执行数据访问操作
	@Test
	public void testFindRoles01() {//此方法运行在主线程
		System.out.println("thread.name->"+Thread.currentThread().getName());
		findRoles01();
		findRoles01();
	}
	//使用SqlSessionTemplate(线程安全)执行数据访问操作
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;//实现了SqlSession接口
	@Test
	public void testFindRoles02() {
		System.out.println("thread.name->"+Thread.currentThread().getName());
		findRoles02();
		findRoles02();
		findRoles02();
	}
}
