package com.apical.oddm.core.dao.sys;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.apical.oddm.core.model.sys.User;

@RunWith(SpringJUnit4ClassRunner.class)//让junit工作在spring环境中
@ContextConfiguration("/configs/spring.xml")//在classes中spring的配置文件
//transactionManager表示在spring配置文件中所声明的事务对象
//defaultRollback=true表示操作会自动回滚，这样你在单元测试中所作的操作都不会影响数据库中的数据
@TransactionConfiguration(transactionManager="txManager", defaultRollback=true)
@Transactional
public class TestOrganization {
	/**
	 * 能够自动将IUserDao对象注入到测试单元中
	 */
	@Autowired
	private OrganizationDaoI organizationDao;
	
	@Test
	public void testLoad() {
		/*
		 * 由于使用了Transactional标签，如果使用hibernate整个操作都是在一个session中
		 * 所以不存在延迟加载的问题*/
		/*User u = userDao.load(1);
		u.setNickname("ddd");
		//此时可以完成更新，但是当测试结束不会影响数据库
		userDao.update(u);
		System.out.println(u.getNickname());
		*/
		/*Organization org = new Organization("总经办3", "icon-company", 1, null, null);
		organizationDao.add(org);*/
		//测试普通的sql查询返回数组，但是没有属性映射，所以用不了
		/*List<Object[]> treeGrid2 = organizationDao.treeGrid2();
		System.out.println("treeGrid2treeGrid2treeGrid2treeGrid2 "+treeGrid2);
		for (Object obj : treeGrid2) {
			Object[] o = (Object[]) obj;
			for (Object s : o) {
				System.out.print(s);
			}
			System.out.println();
		}*/
		//测试sq.setResultTransformer(Transformers.aliasToBean(clz));成功，DtoOrganization类必须和数据库字段一至，大小写也要一样，Date类型用util的
		//Could not find setter for NAME on class  大小写报错
		/*List<OrganizationTest> treeGrid2 = organizationDao.treeGridTest();
		System.out.println("treeGrid2treeGrid2treeGrid2treeGrid2 "+treeGrid2);
		for (OrganizationTest obj : treeGrid2) {
			System.out.println(obj.getName());
		}*/
	}
}
