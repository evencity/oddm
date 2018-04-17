package com.apical.oddm.facade.base.command;

/** 
 * @description 类的描述：
 * @author  作者 : zhuzh
 * @date 创建时间：2016年10月27日 下午7:07:28 
 * @version 1.0 
 */

public class PageCommand {
	private int page;// 当前页
	private int rows;// 每页显示记录数
	private String sort;// 排序字段
	private String order;// asc/desc
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	@Override
	public String toString() {
		return "PageCommand [page=" + page + ", rows=" + rows + ", sort=" + sort + ", order=" + order + "]";
	}
	
}
