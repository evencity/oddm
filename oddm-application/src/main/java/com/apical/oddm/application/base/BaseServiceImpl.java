package com.apical.oddm.application.base;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;

import com.apical.oddm.core.dao.basedao.BaseDaoI;

/**
 * 业务层增删改成方法实现类
 * @author lgx
 * 2016-10-11
 */
public class BaseServiceImpl<T> implements BaseServiceI<T> {
	
	@Autowired
	private BaseDaoI<T> baseDao;
	
	@Override
	public Serializable add(T t) {
		return baseDao.add(t);
	}

	@Override
	public void edit(T t) {
		//baseDao.update(t);//同样会执行全部update
		//baseDao.saveOrUpdate(t);//由于不在一个session中，会全部执行更新操作语句，效率很差。在事务外和update是一致的
		baseDao.merge(t);//CascadeType.MERGE 否则子表不级联操作， 更新完会再次查询全部出来，可以不在一个事务中判断是否更新，但是多N次查询返回值
		//System.out.println("w de letsss"); ReplicationMode replicationMode
		//baseDao.replicate(t, ReplicationMode.IGNORE);//ReplicationMode.IGNORE 和merge相同
	}

	@Override
	public void delete(int id) {
		baseDao.delete(id);
	}

	@Override
	public T get(int id) {
		return baseDao.get(id);
	}

	@Override
	public void update(T t) {
		baseDao.update(t);
	}
}
