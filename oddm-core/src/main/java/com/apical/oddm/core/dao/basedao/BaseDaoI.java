package com.apical.oddm.core.dao.basedao;

import java.io.Serializable;

import org.hibernate.ReplicationMode;


/**
 * 公共的DAO处理对象，这个对象中包含了Hibernate的所有基本操作和对SQL的操作
 * @author lgx
 * 2016-10-11
 * @param <T>
 */
public interface BaseDaoI<T> {
	/**
	 * 添加对象
	 * @param t
	 * @return
	 */
	public Serializable add(T t);
	/**
	 * 更新对象
	 * @param t
	 */
	public void update(T t);
	
	/**
	 * 保存或则更新对象
	 * @param t
	 */
	public void saveOrUpdate(T t);
	
	/**
	 * 保存或则更新对象
	 * @param t
	 */
	public T merge(T t);
	
	
	/**
	 * 编辑对象
	 * @param t
	 */
	public void replicate(T t, ReplicationMode replicationMode);
	
	/**
	 * 根据id删除对象
	 * @param id
	 */
	public void delete(int id);
	/**
	 * 根据对象删除对象
	 * @param t
	 */
	public void delete(T t);
	/**
	 * 根据id加载对象
	 * @param id
	 * @return
	 */
	public T get(int id);
	
/*	*//**
	 * 命名查询，对应实体类的如：@NamedQuery(name="OrderFitting.findAll", query="SELECT o FROM OrderFitting o")
	 * @param t
	 * @return
	 *//*
	public List<T> getNamedQuery(T t);*/ //因为只有部分支持，所以不能写在这里
}

