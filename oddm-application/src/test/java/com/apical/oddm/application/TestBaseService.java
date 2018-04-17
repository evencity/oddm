package com.apical.oddm.application;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.apical.oddm.application.base.BaseServiceI;
import com.apical.oddm.core.model.material.MaterialBare;

@RunWith(SpringJUnit4ClassRunner.class)//让junit工作在spring环境中
@ContextConfiguration("/configs/spring.xml")//在classes中spring的配置文件
public class TestBaseService {
	@Autowired
	private BaseServiceI<MaterialBare> baseService;
	@Test
	public void testBaseServiceI() {
		//测试出错，
		//java.lang.ClassCastException: java.lang.Class cannot be cast to java.lang.reflect.ParameterizedType
		MaterialBare materialBare = baseService.get(1);
		System.out.println(materialBare.getName());
	}
}
