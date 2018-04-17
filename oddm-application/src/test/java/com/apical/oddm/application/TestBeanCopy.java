package com.apical.oddm.application;

import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.apical.oddm.application.order.OrderInfoServiceI;
import com.apical.oddm.core.model.order.OrderInfo;
import com.apical.oddm.core.model.order.OrderOS;
import com.apical.oddm.core.model.order.OrderShell;

/**
 * 测试对象是否是深度复制
 * ②继承自java.lang.Object类的clone()方法是浅复制
 * @author lgx
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)//让junit工作在spring环境中
@ContextConfiguration("/configs/spring.xml")//在classes中spring的配置文件
public class TestBeanCopy {
	@Autowired
	private OrderInfoServiceI orderInfoService;
	
	@Test
	public void testorderInfoService2() {
		OrderInfo orderInfo = orderInfoService.getOrderInfo(1403);
		System.out.println(orderInfo.hashCode());

		//变更记录计算
		OrderInfo orderInfoBefore = new OrderInfo();
		System.out.println(orderInfoBefore.hashCode());
		BeanUtils.copyProperties(orderInfo, orderInfoBefore);//这个是浅克隆，子类如果单独new copy则会是原来的引用
		System.out.println(orderInfoBefore.hashCode());
		orderInfo.setOrderNo("123444");
		orderInfo.setOrderShells(null);
		System.out.println("orderInfoBefore.getOrderNo():"+orderInfoBefore.getOrderNo());
		Set<OrderOS> orderOs = orderInfoBefore.getOrderOs();
		System.out.println("GUI:"+orderOs.iterator().next().getGui());
		Set<OrderShell> orderShells = orderInfoBefore.getOrderShells();
		for (OrderShell s : orderShells) {
			System.out.println(s.getName());
		}
	}
}
