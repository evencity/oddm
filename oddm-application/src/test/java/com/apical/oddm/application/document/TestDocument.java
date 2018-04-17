package com.apical.oddm.application.document;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.apical.oddm.core.model.document.Document;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.page.SystemContext;

@RunWith(SpringJUnit4ClassRunner.class)//让junit工作在spring环境中
@ContextConfiguration("/configs/spring.xml")//在classes中spring的配置文件
public class TestDocument {
	@Autowired
	private DocumentServiceI documentService;

	@Test
	public void testDocumentServiceImpl() {
		//查
		//Pager<Document> dataAuditByBizName = documentService.dataAuditByBizName(1, 1);
		
		//查
	/*	OrderInfo orderInfo = new OrderInfo();
		orderInfo.setOrderNo("WS16090069");
		HashSet<Integer> states = new HashSet<Integer> ();
		states.add(3);
		Pager<Document> dataGrid = documentService.dataGrid(orderInfo, states);
		System.out.println(dataGrid);*/
		//查
		/*Document existDocument = documentService.isExistDocument(1645, "机器说明书");
		System.out.println(existDocument.getNameMt());*/
		
		
		Document document = new Document();
		Set<Integer> states = new HashSet<Integer>();
		//SystemContext.setPageSize(20);
		
		//SystemContext.setCurrUserId(61); //查询当前用户所有
		
		document.setUnread(1);//查询当前用户未读
		SystemContext.setCurrUserId(61); 

		Pager<Document> dataGrid = documentService.dataGrid(document, states);
		for (Document d : dataGrid.getRows()) {
			//if (d.getUnread()!=null)
			System.out.println(d.getUnread()+"\t"+d.getPath()+"\t"+d.getOrderNo()+"\t"+d.getProductClient());
		}
	}
}
