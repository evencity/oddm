package com.apical.oddm.application.manufacture;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.apical.oddm.application.produce.ProductCheckServiceI;
import com.apical.oddm.core.model.produce.ProductCheck;

@RunWith(SpringJUnit4ClassRunner.class)//让junit工作在spring环境中
@ContextConfiguration("/configs/spring.xml")//在classes中spring的配置文件
public class TestMft {
	@Autowired
	private ProductCheckServiceI productCheckServiceI;
	
	@Test
	public void testOrderInfoServiceI() {
		List<ProductCheck> productCheckByProductName = productCheckServiceI.getProductCheckByProductName("A16");
		System.out.println(productCheckByProductName);
		for (ProductCheck p :productCheckByProductName) {
			System.out.println(p.getName());
		}
		
	}
}
