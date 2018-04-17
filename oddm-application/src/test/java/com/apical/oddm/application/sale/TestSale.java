package com.apical.oddm.application.sale;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.apical.oddm.core.vo.sale.SaleDeptStatisVo;

@RunWith(SpringJUnit4ClassRunner.class)//让junit工作在spring环境中
@ContextConfiguration("/configs/spring.xml")//在classes中spring的配置文件
public class TestSale {
	
	@Autowired
	private SaleInfoServiceI saleInfoServiceI;
	
	@Test
	public void testSaleInfoServiceI() {
		//查
		List<SaleDeptStatisVo> saleStatisVo = saleInfoServiceI.getSaleStatisVo("2016", "USD");
		for (SaleDeptStatisVo s: saleStatisVo) {
			System.out.println(s.getProductType()+" | "+s.getAmount()+" | "+s.getMonths());
		}
		//查
	/*	Pager<SaleInfoVo> dataGrid = saleInfoServiceI.dataGrid();
		System.out.println(dataGrid.getRows().size());
		for (SaleInfoVo s : dataGrid.getRows()) {
			System.out.println(s.getCurrency());
			System.out.println(s.getOrderNo()+"||" +s.getBizName()+"||"+s.getProductType());
		}
		*/
		//查
/*		SaleInfoVo detail = saleInfoServiceI.getDetail(5);
		System.out.println(detail.getOrderNo());*/
	}
}
