package com.apical.oddm.core.dao.sys;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.apical.oddm.core.dao.basedao.BaseDaoImpl;
import com.apical.oddm.core.model.sys.Resource;

@Repository("resourceDao")
public class ResourceDaoImpl extends BaseDaoImpl<Resource> implements ResourceDaoI {
	/*
	 * 查出全部菜单及父菜单	
	 * from Resource t left join fetch t.resource order by t.seq相当于select r0.id as id1_1_0_, r1.id as id1_1_1_, r0.timestamp as timestam2_1_0_, r0.description as descript3_1_0_, r0.icon as icon4_1_0_, r0.name as name5_1_0_, r0.pid as pid10_1_0_, r0.resourcetype as resource6_1_0_, r0.seq as seq7_1_0_, r0.state as state8_1_0_, r0.url as url9_1_0_, r1.timestamp as timestam2_1_1_, r1.description as descript3_1_1_, r1.icon as icon4_1_1_, r1.name as name5_1_1_, r1.pid as pid10_1_1_, r1.resourcetype as resource6_1_1_, r1.seq as seq7_1_1_, r1.state as state8_1_1_, r1.url as url9_1_1_ from sys_resource r0 left outer join sys_resource r1 on r0.pid=r1.id order by r0.seq
	 * 查出非空pid菜单及父菜单	
	 * from Resource t join fetch t.resource order by t.seq相当于select r0.id as id1_1_0_, r1.id as id1_1_1_, r0.timestamp as timestam2_1_0_, r0.description as descript3_1_0_, r0.icon as icon4_1_0_, r0.name as name5_1_0_, r0.pid as pid10_1_0_, r0.resourcetype as resource6_1_0_, r0.seq as seq7_1_0_, r0.state as state8_1_0_, r0.url as url9_1_0_, r1.timestamp as timestam2_1_1_, r1.description as descript3_1_1_, r1.icon as icon4_1_1_, r1.name as name5_1_1_, r1.pid as pid10_1_1_, r1.resourcetype as resource6_1_1_, r1.seq as seq7_1_1_, r1.state as state8_1_1_, r1.url as url9_1_1_ from sys_resource r0 inner join sys_resource r1 on r0.pid=r1.id order by r0.seq
	 * 查出全部菜单	
	 * from Resource t order by t.seq相当于select r0.id as id1_1_, r0.timestamp as timestam2_1_, r0.description as descript3_1_, r0.icon as icon4_1_, r0.name as name5_1_, r0.pid as pid10_1_, r0.resourcetype as resource6_1_, r0.seq as seq7_1_, r0.state as state8_1_, r0.url as url9_1_ from sys_resource r0 order by r0.seq
	*/
	@Override
	public List<Resource> treeGrid() {
		//hql的left join相当于sql的left outer join（用这个）
		//hql的join相当于sql的 inner join（会排除空菜单“系统管理”等）
		//String hql = "from Resource t left join fetch t.resource order by t.seq";
		String hql = "from Resource t order by t.seq";
		return this.list(hql);
	}

	@Override
	public List<Resource> getUserResource(int userId) {
		//这里的"fetch"关键字表明roles对象读出以后立即填充到对应的Resource对象Set<Role>集合属性中，fetch具有快速加载作用
		String hql = "select distinct t from Resource t join fetch t.roles role join role.users user where user.id=? order by t.seq";
		return (List<Resource>) this.list(hql, userId);
	}

	@Override
	public List<Resource> listGid(Set<Integer> resorceIds) {
		return this.list("select distinct t from Resource t where t.id in ("
				+ StringUtils.collectionToDelimitedString(resorceIds, ",") + ")");
	}

	@Override
	public List<Resource> treeGrid(Set<Integer> type) {
		String hql = "select t from Resource t left join fetch t.resource where t.resourcetype in ("
				+ StringUtils.collectionToDelimitedString(type, ",") + ") order by t.seq";
		return this.list(hql);
	}

	@Override
	public Resource get(int id) {
		String hql = "select t from Resource t left join fetch t.resource where t.id=?";
		return (Resource) this.queryObject(hql, id);
	}
	
	
}
