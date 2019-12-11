package com.cy.pj.sys.dao;

import com.cy.pj.sys.entity.SysLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysLogDao {
	/**
	 * 基于记录id执行删除业务(有些公司,记录不会直接删除,二是在删除时修改其状态)
	 *
	 * @param ids
	 * @return
	 */
	int deleteObjects(@Param("ids") Integer... ids);
	
	
	/**
	 * 基于条件查询当前页要呈现的数据
	 *
	 * @param username   查询条件
	 * @return 当前页对应的日志记录
	 */
	List<SysLog> findPageObjects(@Param("username") String username);//param3
	int insertObject(SysLog entity);
}
