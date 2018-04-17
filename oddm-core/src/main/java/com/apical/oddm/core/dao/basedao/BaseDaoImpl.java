package com.apical.oddm.core.dao.basedao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.ReplicationMode;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.page.SystemContext;

/**
 * @author lgx
 * 2016-10-11
 */
@SuppressWarnings("unchecked")
@Repository("baseDao")
public class BaseDaoImpl<T> implements BaseDaoI<T> {
	
	@Autowired
	private SessionFactory sessionFactory;
	/**
	 * 创建一个Class的对象来获取泛型的class
	 */
	private Class<?> clz;
	
	public Class<?> getClz() {
		if (clz==null) {
			//获取泛型的Class对象
			clz = ((Class<?>)
					(((ParameterizedType)(this.getClass().getGenericSuperclass())).getActualTypeArguments()[0]));
		}
		return clz;
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	/*@Inject  //同上面的@Autowired，或者在这些@Autowired也行
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}*/
	
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/* (non-Javadoc)
	 * @see com.apical.oddm.core.dao.basedao.BaseDaoI#load(int)
	 */
	@Override
	public T get(int id) {
		//注意load方式是懒加载
		return (T)getSession().get(getClz(), id);
	}

	/* (non-Javadoc)
	 * @see com.apical.oddm.core.dao.basedao.BaseDaoI#add(java.lang.Object)
	 */
	@Override
	public Serializable add(T t) {
		return getSession().save(t);
	}

	/* (non-Javadoc)
	 * @see com.apical.oddm.core.dao.basedao.BaseDaoI#update(java.lang.Object)
	 */
	@Override
	public void update(T t) {
		getSession().update(t);
	}
	
	/**
	 * 更新或者插入对象
	 * @param t
	 */
	@Override
	public void saveOrUpdate(T t) {
		getSession().saveOrUpdate(t);
	}

	@Override
	public void replicate(T t, ReplicationMode replicationMode) {
		getSession().replicate(t, replicationMode);;
	}
	
	/* (non-Javadoc)
	 * @see com.apical.oddm.core.dao.basedao.BaseDaoI#delete(int)
	 */
	@Override
	public void delete(int id) {
		getSession().delete(this.get(id));
	}
	
	@Override
	public T merge(T t) {
		return (T) getSession().merge(t);
	}
	
	/**
	 * 直接通过对象删除
	 * @param t
	 */
	public void delete(T t) {
		getSession().delete(t);
	}
	/**
	 * 命名查询，对应实体类的如：@NamedQuery(name="OrderFitting.findAll", query="SELECT o FROM OrderFitting o")
	 * @param t
	 * @return
	 */
	public List<T> getNamedQuery(Class<? extends Object>  t) {
		return getSession().getNamedQuery(t.getSimpleName()+".findAll").list();
	}
	
	public List<T> list(String hql, Object[] args) {
		return this.list(hql, args, null);
	}

	public List<T> list(String hql, Object arg) {
		return this.list(hql, new Object[]{arg});
	}

	public List<T> list(String hql) {
		return this.list(hql,null);
	}
	/**
	 * 获取表总数，分页的时候用到
	 * @param hql 可以为sql或者hql
	 * @param isHql
	 * @return
	 */
	private String getCountHql(String hql,boolean isHql) {
		if (hql.lastIndexOf(" order by ") != -1) {//删掉排序提高效率
			hql = hql.substring(0, hql.lastIndexOf(" order by "));
		}
		hql = hql.substring(hql.indexOf("from"));
		if (isHql) {
			hql = hql.replaceAll("fetch", "");
		} else {//sql(目前hql还没用到，以后考虑hql也过滤掉分组)分页，如果是分组不再包一层查询，则会查询的报错org.hibernate.NonUniqueResultException: query did not return a unique result
			if(hql.contains("group by")) {
				hql = "from (select count(*) "+hql+") as groupCount";
			}
		}
		return "select count(*) "+hql;
	}

	/**
	 * 获取排序hql
	 * @param hql
	 * @return
	 */
	private String initSort(String hql) {
		String order = SystemContext.getOrder();
		String sort = SystemContext.getSort();
		if (sort!=null && !"".equals(sort.trim())) {
			if (hql.lastIndexOf(" order by ") != -1) {//如果sql有排序了，而外界有传进来排序，则删掉原来的排序
				hql = hql.substring(0, hql.lastIndexOf(" order by "));
			}
			hql+=" order by "+sort;
			if (!"desc".equals(order)) hql+=" asc";
			else hql+=" desc";
		}
		SystemContext.setOrder(null);
		SystemContext.setSort(null);
		return hql;
	}
	
	/**
	 * 通过键值对参数设置查询条件
	 * 例如：select ur.user from UserRole ur where ur.name=:var1 and ur.role.roleType=:var2"，
	 * 然后通过Map传入参数：query.setParameter(var1, "张三");query.setParameter(var2, "审核员")
	 * 
	 * @param query
	 * @param alias Map键值对（别名：值）
	 */
	@SuppressWarnings("rawtypes")
	private void setAliasParameter(Query query,Map<String,Object> alias) {
		if (alias!=null) {
			Set<String> keys = alias.keySet();
			for(String key:keys) {
				Object val = alias.get(key);
				if (val instanceof Collection) {
					//查询条件是列表
					query.setParameterList(key, (Collection)val);
				} else {
					query.setParameter(key, val);
				}
			}
		}
	}
	
	/**
	 * 通过数组顺序设置查询条件
	 * 例如：select ur.user from UserRole ur where ur.name=? and ur.role.roleType=?"，
	 * 然后数组按顺序传入参数，这种方式容易出错，可以用别名方式传参，也就是Map形式
	 * 
	 * @param query
	 * @param args 顺序数据
	 */
	private void setParameter(Query query,Object[] args) {
		if (args!=null && args.length>0) {
			int index = 0;
			for(Object arg:args) {
				query.setParameter(index++, arg);
			}
		}
	}

	/**
	 * 通过hibernate hql语言操作数据库，获取列表
	 * @param hql 传入hibernate hql
	 * @param args 传入查询条件以顺序数据方式（和下面两则择一）
	 * @param alias 传入查询条件以别名方式（和上面两则择一）
	 * @return
	 */
	//没有经过分页设置，如果1对多则会返回重复数据，慎用（得加distinct 字段）
	public List<T> list(String hql, Object[] args, Map<String, Object> alias) {
		hql = initSort(hql);
		Query query = getSession().createQuery(hql);
		setAliasParameter(query, alias);
		setParameter(query, args);
		return query.list(); //会有重复数据，而经过set Page后才没
	}

	/**
	 * 获取前几条数据
	 * @param hql
	 * @param args
	 * @param alias
	 * @return
	 */
	//不会有重复数据，但是注意这里限制了15行
	public List<T> listPage(String hql, Object arg, Map<String, Object> alias) {
		hql = initSort(hql);
		Query query = getSession().createQuery(hql);
		setAliasParameter(query, alias);
		if (arg != null) {
			Object[] args = new Object[]{arg};
			setParameter(query, args);
		}
		Integer pageSize = SystemContext.getPageSize();
		Integer pageOffset = SystemContext.getPageOffset();
		if (pageOffset==null || pageOffset<1) pageOffset = 1;//默认从第1页开始
		if (pageSize==null || pageSize<0) pageSize = 15; //默认查询前10个
		query.setFirstResult((pageOffset-1)*pageSize).setMaxResults(pageSize);//得建议，数据库默认是0为第一页
		SystemContext.setPageSize(null);
		SystemContext.setPageOffset(null);
		
		return query.list();
	}
	
	/**
	 * 获取前几条数据
	 * @param sql
	 * @param args
	 * @param alias
	 * @return
	 */
	public List<Object> listPageSql(String sql, Object arg, Map<String, Object> alias, Class<?> clz, boolean hasEntity) {
		sql = initSort(sql);
		SQLQuery query = getSession().createSQLQuery(sql);
		setAliasParameter(query, alias);
		if (arg != null) {
			Object[] args = new Object[]{arg};
			setParameter(query, args);
		}
		Integer pageSize = SystemContext.getPageSize();
		Integer pageOffset = SystemContext.getPageOffset();
		if (pageOffset==null || pageOffset<1) pageOffset = 1;//默认从第1页开始
		if (pageSize==null || pageSize<0) pageSize = 10; //默认查询前10个
		if (clz != null) {
			if (hasEntity) {
				query.addEntity(clz);
			} else {
				query.setResultTransformer(Transformers.aliasToBean(clz));
			}
		}
		query.setFirstResult((pageOffset-1)*pageSize).setMaxResults(pageSize);//得建议，数据库默认是0为第一页
		SystemContext.setPageSize(null);
		SystemContext.setPageOffset(null);
		
		return query.list();
	}
	
	public List<T> listByAlias(String hql, Map<String, Object> alias) {
		return this.list(hql, null, alias);
	}

	public Pager<T> find(String hql, Object[] args) {
		return this.find(hql, args, null);
	}

	public Pager<T> find(String hql, Object args) {
		return this.find(hql, new Object[]{args});
	}

	public Pager<T> find(String hql) {
		return this.find(hql,null);
	}
	
	@SuppressWarnings("rawtypes")
	private void setPagers(Query query,Pager pages) {
		Integer pageSize = SystemContext.getPageSize();
		Integer pageOffset = SystemContext.getPageOffset();
		if (pageOffset==null || pageOffset<1) pageOffset = 1;//默认从第1页开始
		if (pageSize==null || pageSize<0) pageSize = Integer.MAX_VALUE; //默认查询全部
		pages.setOffset(pageOffset);
		pages.setSize(pageSize);
		query.setFirstResult((pageOffset-1)*pageSize).setMaxResults(pageSize);//得建议，数据库默认是0为第一页
		SystemContext.setPageSize(null);
		SystemContext.setPageOffset(null);
	}
	
	/**
	 * 分页获取数据列表，记得设置ThreadLocal传入排序等
	 * @param hql hibernate hql语言
	 * @param args 传入查询条件以顺序数据方式（和下面两则择一）
	 * @param alias 传入查询条件以别名方式（和上面两则择一）
	 * @return
	 */
	public Pager<T> find(String hql, Object[] args, Map<String, Object> alias) {
		hql = initSort(hql);
		String cq = getCountHql(hql,true);
		//System.out.println(cq+"cqcqcqcqcqcqcqcqcqcqcqcqcqcqcq");
		Query cquery = getSession().createQuery(cq);
		Query query = getSession().createQuery(hql);
		//设置别名参数
		setAliasParameter(query, alias);
		setAliasParameter(cquery, alias);
		//设置参数
		setParameter(query, args);
		setParameter(cquery, args);
		Pager<T> pages = new Pager<T>();
		setPagers(query,pages);
		List<T> datas = query.list(); //经测试不会有重复数据，经过setPagers(query,pages)后就不会有重复数据，不知为什么，
		pages.setRows(datas);
		long total = (Long)cquery.uniqueResult();
		//System.out.println(total+"totaltotaltotaltotaltotaltotaltotaltotaltotaltotal");
		pages.setTotal(total);
		return pages;
	}

	
	/**
	 * 查询特殊分页，因为left join问题（如果1对多）会多总数
	 * @param hql
	 * @return
	 */
	public Pager<T> findSpecial(String hql) {
		hql = initSort(hql);
		String e = hql.substring(hql.indexOf("from")+5);
		e = e.substring(0, e.indexOf(" "));
		String cq = "select count(*) from "+e;
		if (cq.lastIndexOf(" order by ") != -1) {//删掉排序提高效率
			cq = cq.substring(0, cq.lastIndexOf(" order by "));
		}
		Query cquery = getSession().createQuery(cq);
		Query query = getSession().createQuery(hql);
		Pager<T> pages = new Pager<T>();
		setPagers(query,pages);
		List<T> datas = query.list();
		pages.setRows(datas);
		long total = (Long)cquery.uniqueResult();
		pages.setTotal(total);
		return pages;
	}
	
	//返回分页结果集，其中数据为数组形式
	public Pager<Object[]> findArray(String hql/*, Object[] args, Map<String, Object> alias*/) {
		hql = initSort(hql);
		Query query = getSession().createQuery(hql);
		/*setAliasParameter(query, alias);//设置别名参数
		setParameter(query, args);*/
		
		//String cq = getCountHql(hql,true); //group和count一起用，则count返回的时候是单独 统计group分组的行数，则不是总行数
		String e = hql.substring(hql.indexOf("from")+5);
		e = e.substring(0, e.indexOf(" "));
		String cq = "select count(*) from "+e;
		if (cq.lastIndexOf(" order by ") != -1) {//删掉排序提高效率
			cq = cq.substring(0, cq.lastIndexOf(" order by "));
		}
		
		Query cquery = getSession().createQuery(cq);
		/*setAliasParameter(cquery, alias);//设置参数
		setParameter(cquery, args);*/
		
		Pager<Object[]> pages = new Pager<Object[]>();
		setPagers(query,pages);
		List<Object[]> datas = query.list();
		pages.setRows(datas);
		long total = (Long) cquery.uniqueResult();
		pages.setTotal(total);
		return pages;
	}
	public Pager<T> findByAlias(String hql, Map<String, Object> alias) {
		return this.find(hql,null, alias);
	}

	/**
	 * 通过hql更新、删除表数据
	 * @param hql
	 * @param args
	 */
	public int updateByHql(String hql, Object[] args) {
		Query query = getSession().createQuery(hql);
		setParameter(query, args);
		return query.executeUpdate();
	}

	public int updateByHql(String hql, Object arg) {
		return this.updateByHql(hql,new Object[]{arg});
	}

	public int updateByHql(String hql) {
		return this.updateByHql(hql,null);
	}

	/**
	 * 通过标准sql更新或者删除
	 * @param sql
	 * @return
	 */
	public int updateBySql(String sql) {
		SQLQuery q = this.getSession().createSQLQuery(sql);
		return q.executeUpdate();
	}

	/**
	 * 通过标准sql指定别名参数更新
	 * @param sql
	 * @param params
	 * @return
	 */
	public int updateBySql(String sql, Map<String, Object> params) {
		SQLQuery q = this.getSession().createSQLQuery(sql);
		setAliasParameter(q,params);
		return q.executeUpdate();
	}
	
/*	public List<Object[]> findBySql(String sql) { //可以用listBySql（选择传某参数）方法进行替换
		SQLQuery q = this.getSession().createSQLQuery(sql);
		return q.list();
	}*/
	
	/**
	 * 支持标准sql语法查询
	 * @param sql 标准sql语法
	 * @param args
	 * @param alias
	 * @param clz
	 * @param hasEntity
	 * @return
	 */
	public <N extends Object>List<N> listBySql(String sql, Object[] args,
			Map<String, Object> alias, Class<?> clz, boolean hasEntity) {
		sql = initSort(sql);
		SQLQuery sq = getSession().createSQLQuery(sql);
		setAliasParameter(sq, alias);
		setParameter(sq, args);
		if (clz != null) {
			if (hasEntity) {
				sq.addEntity(clz);
			} else {
				sq.setResultTransformer(Transformers.aliasToBean(clz));
			}
		}
		return sq.list();
	}

/*	public List<Object> listObjectBySql(String sql, Object[] args,
			Map<String, Object> alias, Class<?> clz, boolean hasEntity) {
		sql = initSort(sql);
		SQLQuery sq = getSession().createSQLQuery(sql);
		setAliasParameter(sq, alias);
		setParameter(sq, args);
		if (clz != null) {
			if (hasEntity) {
				sq.addEntity(clz);
			} else {
				sq.setResultTransformer(Transformers.aliasToBean(clz));
			}
		}
		return sq.list();
	}*/
	
	public <N extends Object>List<N> listBySql(String sql, Object[] args, Class<?> clz,
			boolean hasEntity) {
		return this.listBySql(sql, args, null, clz, hasEntity);
	}

	public <N extends Object>List<N> listBySql(String sql, Object arg, Class<?> clz,
			boolean hasEntity) {
		return this.listBySql(sql, new Object[]{arg}, clz, hasEntity);
	}

	public <N extends Object>List<N> listBySql(String sql, Class<?> clz, boolean hasEntity) {
		return this.listBySql(sql, null, clz, hasEntity);
	}

	public <N extends Object>List<N> listByAliasSql(String sql, Map<String, Object> alias,
			Class<?> clz, boolean hasEntity) {
		return this.listBySql(sql, null, alias, clz, hasEntity);
	}

	public <N extends Object>Pager<N> findBySql(String sql, Object[] args, Class<?> clz,
			boolean hasEntity) {
		return this.findBySql(sql, args, null, clz, hasEntity);
	}

	public <N extends Object>Pager<N> findBySql(String sql, Object arg, Class<?> clz,
			boolean hasEntity) {
		return this.findBySql(sql, new Object[]{arg}, clz, hasEntity);
	}

	public <N extends Object>Pager<N> findBySql(String sql, Class<?> clz, boolean hasEntity) {
		return this.findBySql(sql, null, clz, hasEntity);
	}

	/**
	 * 通过数据库的标准sql语法获取分页，记得设置ThreadLocal传入排序等
	 * @param sql 标准sql语法
	 * @param args
	 * @param alias
	 * @param clz
	 * @param hasEntity
	 * @return
	 */
	public <N extends Object>Pager<N> findBySql(String sql, Object[] args,
			Map<String, Object> alias, Class<?> clz, boolean hasEntity) {
		sql = initSort(sql);
		String cq = getCountHql(sql,false);//获取表总数
		SQLQuery sq = getSession().createSQLQuery(sql);
		SQLQuery cquery = getSession().createSQLQuery(cq);
		//选择参数条件
		setAliasParameter(sq, alias);
		setAliasParameter(cquery, alias);
		setParameter(sq, args);
		setParameter(cquery, args);
		
		Pager<N> pages = new Pager<N>();
		setPagers(sq, pages);
		if (clz != null) {
			if (hasEntity) {
				//List cats = sess.createSQLQuery( " select * from cats " ).addEntity(Cat. class ).list();
				sq.addEntity(clz);//设置查询返回的实体
			} else {
				sq.setResultTransformer(Transformers.aliasToBean(clz));//可以用来转成VO对象（普通对象）
				//当我们不加这个方法时，查出来的list是一个没有跟字段对应，即["a","b","c"]，如果加上setResultTransformer这个方法，list里面的元素就会成为一个跟数据库字段[a:"a",b:"b"]
			 /* (1)这种转换实体类很严格,必须连属性名字要和数据库字段高度一致(注解此时没很大作用用. 
			    (2)实体类可以比表字段少一些字段.*/
			}
		}
		List<N> datas = sq.list();
		pages.setRows(datas);
		long total = ((BigInteger)cquery.uniqueResult()).longValue();
		pages.setTotal(total);
		return pages;
	}

	public <N extends Object>Pager<N> findByAliasSql(String sql, Map<String, Object> alias,
			Class<?> clz, boolean hasEntity) {
		return this.findBySql(sql, null, alias, clz, hasEntity);
	}

	/**
	 * 通过hql查询单行
	 * @param hql
	 * @param args
	 * @param alias
	 * @return
	 */
	public Object queryObject(String hql, Object[] args,
			Map<String, Object> alias) {
		Query query = getSession().createQuery(hql);
		setAliasParameter(query, alias);
		setParameter(query, args);
		query.setFirstResult(0); //设计返回单行，实际测试结果为：mysql会查询出批量数据，这里只会返回第一行
		query.setMaxResults(1); // limit 0,1
		return query.uniqueResult();
	}

	public Object queryObjectByAlias(String hql, Map<String, Object> alias) {
		return this.queryObject(hql,null,alias);
	}

	public Object queryObject(String hql, Object[] args) {
		return this.queryObject(hql, args,null);
	}

	public Object queryObject(String hql, Object arg) {
		return this.queryObject(hql, new Object[]{arg});
	}

	public Object queryObject(String hql) {
		return this.queryObject(hql,null);
	}
	
	/**
	 * 批量插入
	 * @param list
	 */
	public void saveBatch(List<T> list) {
		Session session = this.getSession();
		for ( int i=0,len=list.size(); i<len; i++ ) {
			session.merge(list.get(i));//saveOrUpdate、merge没区别，先根据主键查出记录进行比较，有更新则更新，无更新则不更新，有新数据则插入，注意得在一个session中才行
			if (i % 20 == 0) {
				session.flush();
				session.clear(); //将本批插入的对象立即写入数据库并释放内存
			}
		}
	}

}
