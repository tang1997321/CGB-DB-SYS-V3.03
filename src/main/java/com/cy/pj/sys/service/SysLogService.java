package com.cy.pj.sys.service;

import com.cy.pj.common.vo.PageObject;
import com.cy.pj.sys.entity.SysLog;

public interface SysLogService {
	PageObject<SysLog> findPageObjects(String username, Integer pageCurrent);
	
	int deleteObjects(Integer... ids);
}
