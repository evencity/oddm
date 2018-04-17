package com.apical.oddm.core.dao.encase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)//让junit工作在spring环境中
@ContextConfiguration("/configs/spring.xml")//在classes中spring的配置文件
@TransactionConfiguration(transactionManager="txManager", defaultRollback=false)
@Transactional
public class TestEncaseDao {
	@Autowired
	EncaseInfoDaoI encaseInfoDao;
	@Autowired
	InvoiceInfoDaoI invoiceInfoDao;
	
	@Test
	public void testInvoiceInfoDaoI() {
	}
	@Test
	public void testEncaseInfoDaoI() {
		/*Pager<Object> listObject = encaseInfoDao.getListObject();
		//System.out.println(listObject);
		for (Object o : listObject.getRows()) {
			Object[] b = (Object[]) o;
			for(Object e : b) {
				System.out.println(e);

			}
		}*/
		//查询数组形式，返回的page.getRows()为一个Object[]数组（不含EncaseList），此数组为{EncaseInfo,产品总数,装箱总数}

		//查询最近一行
	/*	EncaseInfo encaseInfo = encaseInfoDao.get();
		System.out.println(encaseInfo.getAddress());*/
	}
	
}
