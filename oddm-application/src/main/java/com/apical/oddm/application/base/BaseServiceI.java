package com.apical.oddm.application.base;

import java.io.Serializable;

/**
 * 业务层增删改成接口，避免重复编写，子类也可以重写此方法（加验证等）
 * @author lgx
 * 2016-10-11
 */
public interface BaseServiceI<T> {

	/**
	 * 添加对象
	 * @param t
	 * @return
	 */
	public Serializable add(T t);
	/**
	 * 编辑对象，在数据库查出的对象前提下修改，如果不变化，则不会执行更新语句
	 * @param t
	 */
	public void edit(T t);//使用merge(t)
	/**
	 * 更新对象
	 * @param t
	 */
	public void update(T t);//使用update(t)
	/**
	 * 根据id删除对象
	 * @param id
	 */
	public void delete(int id);
	/**
	 * 根据id加载对象，处理用户等是懒加载
	 * @param id
	 * @return 懒加载对象
	 */
	public T get(int id);
}
