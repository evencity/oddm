package com.apical.oddm.application.bom;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.apical.oddm.core.model.bom.BomInfo;
import com.apical.oddm.core.model.bom.BomMaterialContact;
import com.apical.oddm.core.model.bom.BomMaterialRef;
import com.apical.oddm.core.model.order.OrderInfo;

@RunWith(SpringJUnit4ClassRunner.class)//让junit工作在spring环境中
@ContextConfiguration("/configs/spring.xml")//在classes中spring的配置文件
public class TestBom {
	@Autowired
	private BomMaterialServiceI bomMaterialService;
	@Autowired
	BomMaterialRefServiceI bomMaterialRefService;
	@Autowired
	BomInfoServiceI bomInfoService;
	@Autowired
	BomMaterialContactServiceI bomMaterialContactService;
	@Test
	public void testBomMaterialContactServiceI() {
		/*BomMaterialContact contact = new BomMaterialContact();
		contact.setContacts("张三");
		List<BomMaterialContact> list = bomMaterialContactService.list(contact);
		for (BomMaterialContact b : list) {
			System.out.println(b.getCompany());
		}*/
		
	/*	BomMaterialContact contact = new BomMaterialContact();
		//contact.setContacts("张三");
		Pager<BomMaterialContact> list = bomMaterialContactService.dataGrid(null);
		for (BomMaterialContact b : list.getRows()) {
			System.out.println(b.getCompany());
		}
		System.out.println(list.getTotal());*/
		//编辑
	/*	BomInfo bom = bomInfoService.getBomDetailById(104);
		Set<BomMaterialRef> bomMaterialRefs = bom.getBomMaterialRefs();
		BomMaterialRef BomMaterialRef1 = new BomMaterialRef();
		for (BomMaterialRef s : bomMaterialRefs) {
			if (s.getId()==1) {
				BomMaterialRef1 = s;
			}
		}*/
		/*BomMaterialRef1.setBomInfo(bom);
		BomMaterialRef1.setSeq(1);
		BomMaterialRef1.setBrand("lgxtest");
		BomMaterialRef1.setMaterialCode("65412315446");
		bomMaterialRefs.add(BomMaterialRef1);
		*/
		//BomMaterialRef1.setUsageAmount("55/6668");
		/*BomMaterialRef1.setContacts(null);
		bomInfoService.edit(bom);*/
		
		//查
		BomInfo bomInfo = new BomInfo();
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setId(1773);
		bomInfo.setOrderInfo(orderInfo);
		BomInfo bom = bomInfoService.getBom(bomInfo,false);
		System.out.println(bom.getBomMaterialRefs().size());
	}
	
	
	@Test
	public void testBomMaterialRefServiceI() {
		List<BomMaterialRef> byBomId = bomMaterialRefService.getByBomId(2);
		for (BomMaterialRef b : byBomId) {
			System.out.println(b.getMaterialCode());
		}
	}
	@Test
	public void testBomInfoServiceI() {
		//查
	/*	BomInfo bom = bomInfoService.getBomDetailById(19);
		Set<BomMaterialRef> bomMaterialRefs = bom.getBomMaterialRefs();
		for (BomMaterialRef b : bomMaterialRefs) {
			System.out.println(" 类型："+b.getType()+"\t序号："+b.getSeq());
		}*/
		
		//增加
		BomInfo bomInfo = new BomInfo();
		//bomInfo.setBomInfoAlt(Set<BomInfoAlt>);
		BomMaterialContact contact = new BomMaterialContact();
		contact.setCellphone("11111");
		contact.setCompany("company");
		contact.setContacts("凌国鑫test");
		contact.setFax("fax");
		contact.setTelphone("telP");
		Set<BomMaterialRef> setBomMaterialRef = new HashSet<BomMaterialRef>();
		//物料关联
		BomMaterialRef m1 = new BomMaterialRef();
		m1.setBomInfo(bomInfo);
		m1.setBrand("test");
		//m1.setContacts(BomMaterialContact);
		m1.setDescription("test");
		m1.setMaterialCode("test");
		m1.setProductName("test");
		m1.setSeq(0);
		m1.setSpecification("test");
		m1.setType(1);
		//m1.setUpdatetime(Timestamp);
		m1.setUsageAmount1(1);
		m1.setUsageAmount2(2);
		m1.setContacts(contact);
		
		setBomMaterialRef.add(m1);
		
		bomInfo.setBomMaterialRefs(setBomMaterialRef);
		//bomInfo.setBrand(String);
		//bomInfo.setDescription(String);
		//bomInfo.setId(Integer); //新增不用
		bomInfo.setMaker("TSH");
		bomInfo.setMaterialCode("1102006-0825-1");
		bomInfo.setOrderInfo(new OrderInfo(2));
		bomInfo.setProductName("GPS导航仪");
		bomInfo.setSpecification("5002A/343-SD-5.0-G-4G-128DDR/1-铁灰哑UV-丝印Spirit 7500 LM/神达-WS16080063");
		bomInfo.setState(1);
		//bomInfo.setTimestamp(Timestamp);
		//bomInfo.setUpdatetime(Timestamp);
		bomInfo.setVersion("01");
		bomInfo.setOrderInfo(new OrderInfo(1768));
		bomInfoService.add(bomInfo);
		
		//修改
		/*BomInfo info = bomInfoService.get(1);
		info.setMaker("dddddd");
		bomInfoService.edit(info);*/
		//修改
		/*BomInfo info = bomInfoService.get(1);
		info.setOrderInfo(new OrderInfo(1064));
		info.setMaker("3242");
		bomInfoService.edit(info);*/
		//查
		/*BomInfo bomByOrderNo = bomInfoService.getBomByOrderNo("WS16090014");
		System.out.println(bomByOrderNo.getOrderInfo().getOrderNo());*/
		//查
		/*SystemContext.setPageSize(13);
		Pager<BomInfo> dataGridByBomInfo = bomInfoService.dataGridByBomInfo(null);
		
		System.out.println(dataGridByBomInfo.getTotal());*/
	}
	@Test
	public void testOrderInfoServiceI() {
/*		List<BomMaterial> bomMaterial = new LinkedList<BomMaterial>();
		//
		BomMaterial b1 = new BomMaterial("1970005-0163-0", "7寸普清LCD模组-星源配嘉中", "20810700210247--KR070PB8S-350-3.5-8.5代线/JZ-070-B1320/星源配嘉中", "");
		BomMaterial b2 = new BomMaterial("1970005-0164-0", "7寸高清LCD模组-威耀配帝显", "WY070ML848CP27B/DX0066-070B/威耀配帝显", "");
		BomMaterial b3 = new BomMaterial("1970006-0001-0", "8寸高清LCD模组-星源配鑫佰圆", "20810800300012/ACE-CC8.0A-112/星源配鑫佰圆", "2012.4.9");
		bomMaterial.add(b1);
		bomMaterial.add(b2);
		bomMaterial.add(b3);
//		for (BomMaterial b : bomMaterial) {
//			System.out.println(b.getMtlCode());
//		}
		bomMaterialService.saveBomMaterialBatch(bomMaterial);*/
		
		
		
		
	}
}
