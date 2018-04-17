package com.apical.oddm.core.dao.sys;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.apical.oddm.core.dao.basedao.BaseDaoImpl;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.sys.Organization;

@Repository("organizationDao")
public class OrganizationDaoImpl extends BaseDaoImpl<Organization> implements OrganizationDaoI {

	/*@Override//同下
	public List<Organization> treeGrid() { //懒加载会有问题
		String sql = "select * from sys_organization t order by t.seq";
		return this.listBySql(sql, Organization.class, true);
	}*/

	@Override //同上
	public List<Organization> treeGrid() {
		String hql = "select distinct t from Organization t left join fetch t.organization o left join fetch t.users u order by t.seq";
		return this.list(hql);
	}

	@Override
	public Pager<Organization> dataGrid() {
		String hql = "select t from Organization t left join fetch t.organization o left join fetch t.users u order by t.seq";
		return this.find(hql);
	}

	@Override
	public List<Organization> treeGrid2() {
		String hql = "select distinct t from Organization t left join fetch t.organization o left join fetch t.users u where t.organization=null order by t.seq";
		return this.list(hql);
	}

	@Override
	public Organization getChildren(int id) {
		String hql = "select distinct t from Organization t left join fetch t.organizations where t.id=?";
		return (Organization) this.queryObject(hql, id);
	}
	/*public List<Object[]> treeGrid2() {
		String sql = "select * from sys_organization t order by t.seq";
		return this.listBySql(sql, null, false); //可以查出行，但是没有属性对应，进而无法利用，这种情况还是用hql的数组形式较好
	}*/
	
/*	@Override
	public List<OrganizationTest> treeGridTest() {
		String sql = "select * from sys_organization t order by t.seq";
		return this.listBySql(sql, OrganizationTest.class, false); //可以查出行，但是没有属性对应，进而无法利用
	}*/
}
