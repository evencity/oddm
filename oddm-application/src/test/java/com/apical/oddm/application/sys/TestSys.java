package com.apical.oddm.application.sys;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.apical.oddm.application.bom.BomInfoServiceI;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.sys.Organization;
import com.apical.oddm.core.model.sys.SysConfig;
import com.apical.oddm.core.model.sys.User;
import com.apical.oddm.infra.encrypt.Encrypt;

@RunWith(SpringJUnit4ClassRunner.class)//让junit工作在spring环境中
@ContextConfiguration("/configs/spring.xml")//在classes中spring的配置文件
//transactionManager表示在spring配置文件中所声明的事务对象
//defaultRollback=true表示操作会自动回滚，这样你在单元测试中所作的操作都不会影响数据库中的数据
/*@TransactionConfiguration(transactionManager="txManager", defaultRollback=true)
@Transactional   放在此可以实现懒加载，在一个事务里面，如果屏蔽则报错：failed to lazily initialize a collection of role*/
public class TestSys {
	/**
	 * 能够自动将IUserDao对象注入到测试单元中
	 */
	@Autowired
	private OrganizationServiceI organizationService;
	@Autowired
	private ResourceServiceI resourceService;
	@Autowired
	private RoleServiceI roleService;
	@Autowired
	private UserServiceI userService;
	@Autowired
	private SysConfigServiceI sysConfigService;
	@Autowired
	private SysNoticeServiceI sysNoticeService;
	@Autowired
	BomInfoServiceI BomInfoServiceI;
	@Test
	public void testSysNoticeServiceI() throws ParseException {
		//通过实践范围查询文档
		/*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startTime = sdf.parse("2016-10-1 00:00:00");
		System.out.println(startTime);
		Date endTime = new Date();
		Pager<SysNotice> dataGrid = sysNoticeService.dataGrid(startTime, endTime);
		for (SysNotice s : dataGrid.getRows()) {
			System.out.println(s.getTitle());
		}*/
		//通过标题查询
//		Pager<SysNotice> dataGrid = BomInfoServiceI.dataGrid();
//		for (SysNotice s : dataGrid.getRows()) {
//			System.out.println(s.getTitle());
//		}
	}
	@Test
	public void testSysConfigServiceI() {
		//增
		try {
			SysConfig sysConfig = new SysConfig();
			sysConfig.setKey("testkey");
			sysConfig.setValue("testvalue");
			sysConfig.setDescription("setDescription");
			sysConfigService.add(sysConfig);
		} catch (DataIntegrityViolationException e) {
			System.out.println(e.getMessage());
			System.out.println(e.getCause().getMessage());
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		//查
		/*SysConfig sysConfig2 = sysConfigService.get("testkey");
		System.out.println(sysConfig2.getValue());
		//改
		sysConfig2.setValue("dddddddddd");
		sysConfigService.edit(sysConfig2);*/
		//删除
		//sysConfigService.delete("testkey");
	}
	
	private static String getRandomStringByLength(int length) {
        String base = "()+-=abcdefghijkmnpqrstuvwxyz23456789!@#$%*!@#$%*ABCDEFGHIJKLMNPQRSTUVWXYZ";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
	public static void main(String[] args) {
		String randomStringByLength = getRandomStringByLength(12);
		System.out.println(randomStringByLength);
	}
	@Test
	public void testUserServiceI() {
		
		//查询能生成订单未读标志的人
		/*List<User> users = userService.listUsersWithOrderResource();
		for (User u: users) {
			System.out.println(u.getUsername());
		}*/
		//查询能审核订单的角色人
		/*List<User> listOrderAuditor = userService.listOrderAuditor("a");
		for (User u : listOrderAuditor) {
			System.out.println(u.getUsername());
		}*/
		//分页查询用户信息
		/*SystemContext.setPageOffset(1);
		SystemContext.setPageSize(10);
		SystemContext.setOrder("desc");
		SystemContext.setSort("t.id");
		Pager<User> dataGrid = u serService.dataGrid();
		System.out.println(dataGrid.getTotal());
		List<User> rows = dataGrid.getRows();
		//List<User> rows = userService.getUserListByOrganization(1);
		for (User u : rows) {
			System.out.println(u.getLoginname()+"|"+u.getOrganization().getId()+"|"+u.getRoles().size());
			System.out.println(u.getAge());
		}*/
		
		
		// 查询用户资源列表
	/*	List<Resource> rows = userService.getUserResource(1);
		for (Resource u : rows) {
			System.out.println(u.getName()+"|"+u.getUrl());
		}*/
		
		//重置密码
		//System.out.println(userService.updatePassword(1));
		//System.out.println(userService.editUserPwd(1, "abc!@#123", "123"));
		
		//加密：
		//a553e9d2-fe51-4f9c-a764-4d5ef062f10c  66377a5e92a19775aa574515ba4ecf3928a7bf171c0535b118bba6f3c7453887
		Pager<User> dataGrid = userService.dataGrid();
		ArrayList<String> list1 = new ArrayList();
		ArrayList<String> list2 = new ArrayList();
		for (User User : dataGrid.getRows()) {
			if (User.getLoginname().equals("Test") || User.getState() == 2 || User.getId() >55) {
				continue;
			}
			String salt = UUID.randomUUID()+"";
			String code = getRandomStringByLength(12);
			//System.out.println(code);
			String password = Encrypt.sha256Hex(code+salt);
			String result1 = "用户名:\t"+User.getLoginname()+"\t新密码:\t"+code+"\t加密后:\t"+ password+"\t加密秘钥:\t"+salt;
			list1.add(result1);
			String result2 = "UPDATE sys_user SET password='"+password+"', salt='"+salt+"' WHERE loginname='"+User.getLoginname()+"';";
			list2.add(result2);
		}
		for (String l1 : list1) {
			System.out.println(l1);
		}
		for (String l2 : list2) {
			System.out.println(l2);
		}
		
		
		
		
		//
		//添加
/*		try {
			User u = new User();
			u.setLoginname("admin2");
			u.setUsername("admin2");
			u.setPassword("123");
			u.setSalt("111");
			Serializable add = userService.add(u);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getCause().getMessage());
		}*/
		//改 
		/*User up = userService.get(1);
		up.setSex(22);
		userService.edit(up);*/
		//通过登录名称查询
	/*	User byLoginName = userService.getByLoginName("test1");
		//User byLoginName = userService.get(1);
		System.out.println(byLoginName.getRoles().iterator().next().getName()+" "+byLoginName.getOrganization().getName());
	*/
		//查
	/*	User user = null;
		//user = userService.get(1);
		user =  userService.getByLoginName("test1");
		System.out.println(user.getPassword());*/
		//查
		//user = userService.get(1);
	/*	Pager<User> byUsername = userService.getByUsername("test2");
		System.out.println(byUsername.getTotal());*/
	}
	@Test
	public void testOrganizationServiceI() {
		//查
		//List<Organization> treeGrid = organizationService.treeGrid();
		List<Organization> treeGrid = organizationService.dataGrid().getRows();
		for (Organization o : treeGrid) {
			if (o.getOrganization() != null) {
				System.out.println(o.getName()+"|"+o.getOrganization().getName());
				for (User u : o.getUsers()) {
					System.out.println("\t"+u.getLoginname()+"\t"+u.getOrganization().getId());
				}
			} else {
				System.out.println(o.getName()+"|"+o.getOrganization());
				for (User u : o.getUsers()) {
					System.out.println("\t"+u.getLoginname()+"\t"+u.getOrganization().getId());
				}
			}
			
			//for(Organization e :o.getOrganizations()) {//懒加载问题，会报错，修改修改为立即加载（不建议，会有性能问题）
			//	System.out.println(" 子树"+e.getName()+"|"+e.getOrganization().getId());
			//}
		}
		//增
	/*	Organization organization = new Organization();
		organization.setIcon("icon-folder");
		organization.setName("test2222");
		organization.setSeq(0);
		//organization.setOrganization(organizationService.get(1));

		Organization pid = new Organization(1);
		organization.setOrganization(pid);
		organizationService.add(organization);*/
		//改
		/*Organization organization = organizationService.get(38);
		Organization organization2 = organizationService.get(44);
		organization2.getOrganizations().add(organization);
		System.out.println(organization2.getName());
		organization2.setName("test2");
		organizationService.edit(organization2);*/
	}
	@Test
	public void testResourceServiceI() {
		//查
		/*List<Resource> treeGrid = resourceService.treeGrid();
		System.out.println(treeGrid.size());
		for (Resource r : treeGrid) {
			if(r.getResource() != null) {
				System.out.println(r.getName()+"--------------父菜单："+r.getResource().getName());
			} else {
				System.out.println(r.getName()+"--------------父菜单"+r.getResource());
			}
		}*/
		
		//查
	/*	List<Resource> treeGrid = resourceService.treeGrid();
		System.out.println(treeGrid.size());
		for (Resource r : treeGrid) {
			System.out.println("--------------"+r.getRoles().iterator().next().getName());
		}*/
		//查
	/*	Resource resource = resourceService.get(1);
		System.out.println(resource.getName()+"||||");*/
		//System.out.println(resource.getName()+"||||"+resource.getResource().getName());
		//改
		/*Resource resource = resourceService.get(2);
		System.out.println(resource.getName());
		resource.setName("代码演示");
		resourceService.edit(resource);*/
		//改 测试@DynamicUpdate(true)生效
		/*Resource resource = new Resource();
		resource.setId(2);
		resource.setName("代码演示2");
		resourceService.saveOrUpdateName(resource);*/
		
		resourceService.delete(212);
		resourceService.delete(213);

		resourceService.delete(214);
		resourceService.delete(215);

	}
	
	@Test
	public void testRoleServiceI() {
		//测试角色授权
		/*Set<Integer> resourceIds = new HashSet<Integer>();
		resourceIds.add(13);
		resourceIds.add(131);
		resourceIds.add(132);
		resourceIds.add(133);
		resourceIds.add(134);
		resourceIds.add(135);
		System.out.println(StringUtils.collectionToDelimitedString(resourceIds, ","));
		roleService.grant(2, resourceIds);*/
		//删除角色，经测试不会删除角色关联的用户，会删除与用户、与资源关联的中间表	
		/*Set<User> users = roleService.getRoleUser(1);
		if (users != null) {
			for (User u:users) {
				System.out.println(u.getUsername());
			}
		}*/
		//删除
		//roleService.delete(2);
		//查
	/*	Set<Resource> roleResource = roleService.getRoleResource(1);
		if (roleResource != null) {
			for (Resource u:roleResource) {
				System.out.println(u.getName());
			}
		}*/
		//
		/*Role role = new Role();
		role.setId(3);
		role.setName("55555555");
		roleService.edit(role);*/
	}
}
