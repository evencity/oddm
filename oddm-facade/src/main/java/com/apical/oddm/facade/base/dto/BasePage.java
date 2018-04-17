package com.apical.oddm.facade.base.dto;

import java.util.ArrayList;
import java.util.List;

/** 
 * @description 类的描述：
 * @author  作者 : zhuzh
 * @date 创建时间：2016年10月25日 上午11:46:37 
 * @version 1.0 
 */

public class BasePage<T>{
	/**
	 * 分页的起始页
	 */
	private int offset;
	/**
	 * 总记录数
	 */
	private Long total = 0L;
	/**
	 * 分页的数据
	 */
	private List<T> rows = new ArrayList();
	
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	@Override
	public String toString() {
		final int maxLen = 10;
		return "BasePage [offset=" + offset + ", total=" + total + ", rows=" + (rows != null ? rows.subList(0, Math.min(rows.size(), maxLen)) : null) + "]";
	}
	
}
