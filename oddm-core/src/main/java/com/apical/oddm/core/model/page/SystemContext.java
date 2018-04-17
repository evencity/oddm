package com.apical.oddm.core.model.page;

/**
 * 用来传递列表对象的ThreadLocal数据
 * ThreadLocal 存放数据用的
 * @author Administrator
 *
 */
public class SystemContext {
	/**
	 * 分页大小
	 */
	private static ThreadLocal<Integer> pageSize = new ThreadLocal<Integer>();
	/**
	 * 分页的起始页
	 */
	private static ThreadLocal<Integer> pageOffset = new ThreadLocal<Integer>();

	private static ThreadLocal<String> sort = new ThreadLocal<String>();
	
	private static ThreadLocal<String> order = new ThreadLocal<String>();
	
	/**
	 * /当前用户id，供传查询用
	 */
	private static ThreadLocal<Integer> currUserId = new ThreadLocal<Integer>(); 
	//保留
	/*private static ThreadLocal<String> realPath = new ThreadLocal<String>();
	
	public static String getRealPath() {
		return realPath.get();//内部源码实际是个map，会map.getEntry(this) 获取当前对象键（键就是当前对象的引用）对应的值
	}
	public static void setRealPath(String _realPath) {
		SystemContext.realPath.set(_realPath);
	}
	public static void removeRealPath() {
		realPath.remove();
	}
	*/
	public static Integer getPageSize() {
		return pageSize.get();
	}
	public static void setPageSize(Integer _pageSize) {
		pageSize.set(_pageSize);
	}
	public static Integer getPageOffset() {
		return pageOffset.get();
	}
	public static void setPageOffset(Integer _pageOffset) {
		pageOffset.set(_pageOffset);
	}
	public static String getSort() {
		return sort.get();
	}
	
	/**
	 * 列表的排序字段 SystemContext.setSort(t.id); //排序字段参考dao hql或者sql
	 * @param _sort
	 */
	public static void setSort(String _sort) {
		SystemContext.sort.set(_sort);
	}
	public static String getOrder() {
		return order.get();
	}
	/**
	 * 列表的排序方式 SystemContext.setOrder(desc); //或者asc
	 * @param _order
	 */
	public static void setOrder(String _order) {
		SystemContext.order.set(_order);
	}
	
	public static void removePageSize() {
		pageSize.remove();
	}
	
	public static void removePageOffset() {
		pageOffset.remove();
	}
	
	public static void removeSort() {
		sort.remove();
	}
	
	public static void removeOrder() {
		order.remove();
	}
	
	public static Integer getCurrUserId() {
		return currUserId.get();
	}

	/**
	 * @param currUserId 当前登录的用户id
	 */
	public static void setCurrUserId(Integer _currUserId) {
		currUserId.set(_currUserId);
	}
	
	public static void removeCurrUserId() {
		currUserId.remove();
	}
}
