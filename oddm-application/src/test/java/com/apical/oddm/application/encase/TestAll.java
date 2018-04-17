package com.apical.oddm.application.encase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.apical.oddm.core.model.encase.EncaseInfo;
import com.apical.oddm.core.model.encase.InvoiceInfo;
import com.apical.oddm.core.model.page.Pager;

@RunWith(SpringJUnit4ClassRunner.class)//让junit工作在spring环境中
@ContextConfiguration("/configs/spring.xml")//在classes中spring的配置文件
public class TestAll {
	 
	@Autowired
	InvoiceInfoServiceI invoiceInfoService;
	@Autowired
	EncaseInfoServiceI encaseInfoService;
	
	@Test
	public void testInvoiceInfoServiceI() {
		InvoiceInfo invoiceInfo = new InvoiceInfo();
	//	invoiceInfo.setBrand("1");
		Pager<InvoiceInfo> dataGrid = invoiceInfoService.dataGrid(invoiceInfo);
		for (InvoiceInfo i : dataGrid.getRows()) {
			System.out.println(i.getQtys()+""+i.getAmount());
		}
		System.out.println(dataGrid.getTotal());
	}
	
	@Test
	public void testEncaseInfoServiceI() {
		EncaseInfo encaseInfo = new EncaseInfo();
		Pager<EncaseInfo> dataGrid = encaseInfoService.dataGrid(encaseInfo);
		for (EncaseInfo i : dataGrid.getRows()) {
			System.out.println(i.getQtys()+""+i.getId());
		}
	}
}
