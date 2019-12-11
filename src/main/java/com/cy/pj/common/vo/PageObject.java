package com.cy.pj.common.vo;

import lombok.Data;

import java.util.List;
@Data
public class PageObject<T> {//泛型类
	private List<T> records;
	private Integer rowCount=0;
	private Integer pageCount=0;
	private Integer pageCurrent=1;
	private Integer pageSize=3;
	
	public PageObject() {
	}
	
	public PageObject(List<T> records, Integer pageCurrent, Integer rowCount, Integer pageSize) {
		this.records = records;
		this.rowCount = rowCount;
		this.pageCurrent = pageCurrent;
		this.pageSize = pageSize;
//		this.pageCount=rowCount/pageSize;
//		if (rowCount%pageSize!=0)
//			this.pageCount++;
		this.pageCount=(rowCount-1)/pageSize+1;
	}
}
