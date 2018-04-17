package com.apical.oddm.application.sys;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apical.oddm.application.base.BaseServiceImpl;
import com.apical.oddm.core.dao.sys.OrganizationDaoI;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.sys.Organization;

@Service("organizationService")
public class OrganizationServiceImpl extends BaseServiceImpl<Organization> implements OrganizationServiceI {

	@Autowired
	private OrganizationDaoI organizationDao;
	
	@Override
	public List<Organization> treeGrid() {
		return organizationDao.treeGrid();
	}

	@Override
	public Serializable add(Organization organization) {
		return organizationDao.add(organization);
	}

	@Override
	public void delete(int id) {
		organizationDao.delete(id);
	}

	@Override
	public void edit(Organization organization) {
		organizationDao.update(organization);
	}

	@Override
	public Organization get(int id) {
		return organizationDao.get(id);
	}

	@Override
	public Pager<Organization> dataGrid() {
		return organizationDao.dataGrid();
	}

	@Override
	public List<Organization> treeGrid2() {
		return organizationDao.treeGrid2();
	}

	@Override
	public Organization getChildren(int id) {
		return organizationDao.getChildren(id);
	}
}
