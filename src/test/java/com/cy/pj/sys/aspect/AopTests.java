package com.cy.pj.sys.aspect;

import com.cy.pj.common.vo.PageObject;
import com.cy.pj.sys.service.SysUserService;
import com.cy.pj.sys.vo.SysUserDeptVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AopTests {
	@Autowired
	private SysUserService sysUserService;
	@Test
	public void testSysUserService() {
		PageObject<SysUserDeptVo> po=
				sysUserService.findPageObjects("admin",1);
		System.out.println("rowCount:"+po.getRowCount());
	}
	
}
