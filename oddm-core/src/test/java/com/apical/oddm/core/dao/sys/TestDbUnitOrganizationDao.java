package com.apical.oddm.core.dao.sys;

import java.io.IOException;
import java.sql.SQLException;

import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.SessionHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.apical.oddm.core.dao.sys.OrganizationDaoI;
import com.apical.oddm.core.model.sys.Organization;
import com.apical.oddm.core.util.AbstractDbUnitTestCase;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/configs/spring.xml")
public class TestDbUnitOrganizationDao extends AbstractDbUnitTestCase{
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private OrganizationDaoI organizationDao;
	
	@Before
	public void setUp() throws SQLException, IOException, DatabaseUnitException {
		//此时最好不要使用Spring的Transactional来管理，因为dbunit是通过jdbc来处理connection，再使用spring在一些编辑操作中会造成事务shisu
		Session s = sessionFactory.openSession();
		TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(s));
		this.backupAllTable();
		IDataSet ds = createDateSet("dbunit");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon,ds);
	}
	
	@Test
	public void addOrg() {
		Organization org = new Organization("品质部222", "icon-company", 1, null, null);
		organizationDao.add(org);
	}
	
	@After
	public void tearDown() throws DatabaseUnitException, SQLException, IOException {
		SessionHolder holder = (SessionHolder) TransactionSynchronizationManager.getResource(sessionFactory);
		Session s = holder.getSession(); 
		s.flush();
		TransactionSynchronizationManager.unbindResource(sessionFactory);
		this.resumeTable();
	}
}
