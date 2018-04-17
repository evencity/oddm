package com.apical.oddm.core.dao.sys;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.apical.oddm.core.model.sys.Gender;
import com.apical.oddm.core.model.sys.UserTest;

@RunWith(SpringJUnit4ClassRunner.class)//让junit工作在spring环境中
@ContextConfiguration("/configs/spring.xml")//在classes中spring的配置文件
//transactionManager表示在spring配置文件中所声明的事务对象
//defaultRollback=true表示操作会自动回滚，这样你在单元测试中所作的操作都不会影响数据库中的数据
//defaultRollback=true 不会执行插入、更新操作
@TransactionConfiguration(transactionManager="txManager", defaultRollback=false)
@Transactional
public class TestUserDao {
	/**
	 * 能够自动将IUserDao对象注入到测试单元中
	 */
	@Autowired
	private UserTestDaoI userTestDao;
	
	@Autowired
	private UserDaoI userDao;
	
	@Test
	public void testLoad() {
		UserTest userTest = userTestDao.get(1);
		userTest.setSex(Gender.female);
		userTestDao.update(userTest);
	}
	
/*	@Test
	public void testLoad2() {
		User user = userDao.get(1);
		user.setLoginname("admin2");
		userDao.update(user);
		System.out.println(user.getPassword());
		
	}*/
}
