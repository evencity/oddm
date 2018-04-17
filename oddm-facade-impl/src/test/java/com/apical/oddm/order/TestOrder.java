package com.apical.oddm.order;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.apical.oddm.application.order.MaterialFollowupServiceI;
import com.apical.oddm.application.order.OrderFollowupServiceI;
import com.apical.oddm.application.order.OrderInfoAlterServiceI;
import com.apical.oddm.application.order.OrderInfoServiceI;
import com.apical.oddm.application.order.OrderOSServiceI;
import com.apical.oddm.application.order.OrderShellServiceI;
import com.apical.oddm.core.model.document.Document;
import com.apical.oddm.core.model.order.MaterialFollowup;
import com.apical.oddm.core.model.order.OrderFollowup;
import com.apical.oddm.core.model.order.OrderInfo;
import com.apical.oddm.core.model.order.OrderInfoAlt;
import com.apical.oddm.core.model.order.OrderOS;
import com.apical.oddm.core.model.order.OrderShell;
import com.apical.oddm.core.model.page.Pager;

@RunWith(SpringJUnit4ClassRunner.class)//让junit工作在spring环境中
@ContextConfiguration("/configs/spring.xml")//在classes中spring的配置文件
public class TestOrder {
	@Autowired
	private OrderOSServiceI orderOSService;
	@Autowired
	private OrderShellServiceI OrderShellService;
	@Autowired
	private OrderInfoServiceI orderInfoService;
	@Autowired
	private OrderInfoAlterServiceI orderAltinfoService;
	@Autowired
	private OrderFollowupServiceI orderFollowupService;
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired
	MaterialFollowupServiceI materialFollowupService;
	@Test
	public void testMaterialFollowupServiceI() {
		OrderInfo order = new OrderInfo();
		Pager<MaterialFollowup> dataGridByUserId = materialFollowupService.dataGridByOrderInfo(order, null);
		System.out.println(dataGridByUserId.getRows().get(0).getMtCode());
	}
	@Test
	public void testOrderFollowupServiceI() {
		OrderFollowup orderFollowup = new OrderFollowup();
		
	}
	@Test
	public void testOrderInfoServiceI() throws ParseException {
		//编辑 2 不能用get(id)接口 这个是慢加载的，否则orderInfo.getOrderOs();会报错
	/*	OrderInfo orderInfo = orderInfoService.getOrderInfo(15);
		orderInfo.setBizName("小ssssdd梅");
		
		OrderOS orderOS = null;
		Set<OrderOS> orderOs = orderInfo.getOrderOs();
		if (orderOs != null && !orderOs.isEmpty()) {
			orderOS = orderOs.iterator().next();
		} else {
			orderOS = new OrderOS();
		}
		orderOS.setGui("Wince 6.0dddd");
	    Set<OrderOS> orderOsSet = new HashSet<OrderOS>();
		orderOsSet.add(orderOS);
		orderInfo.setOrderOs(orderOsSet);
		orderInfoService.edit(orderInfo);*/
		
		//编辑 3 ，每个接口单独调用
		/*OrderInfo orderInfo = orderInfoService.get(15);
		orderInfo.setBizName("小d梅");
		orderInfoService.edit(orderInfo);
		//OrderOS orderOS = orderOSService.getOrderOsByOrderId(15);//通过订单id查，不建议
		OrderOS orderOS = orderOSService.get(4);//最好都是第一次查询订单后，缓存本对象，再直接改
		orderOS.setGui("Wince 6.00");
		orderOSService.edit(orderOS);*/
		
		//查
		/*OrderInfo order = new OrderInfo();
		order.setUser(new User(1));
		Pager<OrderInfo> dataGrid = orderInfoService.dataGrid();
		
		for (OrderInfo orderInfo : dataGrid.getRows()) {
			System.out.println(orderInfo.getUser().getLoginname());
		}*/
		List<OrderInfo> allDocumentByOrderId = orderInfoService.getAllDocumentByOrderId(1);
		System.out.println(allDocumentByOrderId.size());
		for(OrderInfo orderInfo : allDocumentByOrderId){
			Set<Document> documents = orderInfo.getDocuments();
			for(Document document : documents){
				System.out.println(document.getPath());
			}
		}
		//查
		/*OrderInfo orderInfo = orderInfoService.getOrderInfo(42);
		//System.out.println(orderInfo.getOrderNo());
		for (OrderHardware shell :orderInfo.getOrderHardwares()) {
			System.out.println(shell.getName()+".....");
		}
		//查
*/		/*OrderInfo orderInfo = orderInfoService.get(2);
		System.out.println(orderInfo.getOrderNo());
		System.out.println(orderInfo.getOrderInfo().getId()); //得关闭lazy
*/		/*System.out.println(orderInfo.getOrderInfo().getOrderInfo().getOrderNo());*/

		//查
	/*	List<OrderInfo> dataHistoryByOrderId = orderInfoService.getAllDocumentByOrderId(3);
		for (OrderInfo o:dataHistoryByOrderId) {
			System.out.println(o.getOrderNo());
			for (Document d: o.getDocuments()) {
				System.out.println(d.getNameMt()+" | "+d.getUploadtime());
			}

		}*/
		//删除 订单信息（订单查看详细页里面的所有信息，包括主表信息、os、硬件、包材、等）
		//orderInfoService.delete(3);
		//删除 所有和订单关联的
		//orderInfoService.deleteAll(3);
		
		//查————根据机型查最新一个订单信息
	/*	OrderInfo byProductFactory = orderInfoService.getByProductFactory("5003-A");
		System.out.println(byProductFactory.getOrderNo());*/
		
		//增加
		/*	OrderInfo orderInfo = new OrderInfo();
			//orderInfo.setId(3); 这个无用，由数据库递增
			orderInfo.setUser(new User(1));
			orderInfo.setOrderNo("WS16090019");
			//orderInfo.setBomInfos(Set<BomInfo>)
			orderInfo.setClientBrand("Navman");
			orderInfo.setClientName("MiTAC International Corporation.");
			orderInfo.setClientNameCode("code123456");
			orderInfo.setDateDelivery(df.parse("2016-09-02"));
			orderInfo.setDateExamine(df.parse("2016-09-03"));
			orderInfo.setDateOrder(df.parse("2016-09-04"));
			orderInfo.setDescription("1、按神达标准包装验货出货 2、Wince贴纸要贴(客供)，客户型号Navman MOVE-70LM， 3、包材和软件待定");
			orderInfo.setDistrict("台湾");
			//orderInfo.setDocuments(Set<Document>);
			//orderInfo.setMaterialFollowups(Set<MaterialFollowup>);
			//orderInfo.setOrderAltinfos(Set<OrderInfoAlt>);
			//orderInfo.setOrderFittings(Set<OrderFitting>);
			//orderInfo.setOrderFollowups(Set<OrderFollowup>);
			//orderInfo.setOrderHardwares(Set<OrderHardware>);
			orderInfo.setOrderInfo(orderInfoService.get(1));
			
			//orderInfo.setOrderPackings(Set<OrderPacking>);
			//orderInfo.setOrderShells(Set<OrderShell>);
			orderInfo.setOrderSpare("机器/外箱的贴纸要贴上,按神达标准. 2%整机备品，2%配件备品,2%彩盒备品，393台备品，1台船头版");
			orderInfo.setPayment("100%L/C");
			orderInfo.setPlaceDelivery("香港");
			orderInfo.setProductClient("Navman MOVE70LM");
			orderInfo.setProductFactory("5003-A");
			orderInfo.setQuantity(12345);
			orderInfo.setState(1);
			//orderInfo.setTimestamp(Timestamp);
			//orderInfo.setUpdatetime(Timestamp);
		    Set<OrderOS> orderOsSet = new HashSet<OrderOS>();
			OrderOS orderOS = new OrderOS();
			orderOS.setBootLogo("有见文档");
			orderOS.setGui("Wince 6.0");
			orderOS.setIscustom(1);
			orderOS.setIsrepeat(1);
			orderOS.setLangDefault("繁體中文");
			orderOS.setLangOs("24国语言");
			orderOS.setOrderInfo(orderInfo);
			orderOS.setPreFile("无");
			orderOS.setPreMap("无");
			orderOS.setTimezone("GMT+9：00");
			orderOS.setOrderInfo(orderInfo);
			//orderOS.setUpdatetime(Timestamp);
			orderOS.setUuid("32位UUID");
		   //orderOsSet.add(orderOS);
			
			orderInfoService.add(orderInfo);*/
			
			//编辑 1
			/*OrderInfo orderInfo = orderInfoService.get(15);
			orderInfo.setBizName("小梅");
			orderInfoService.edit(orderInfo);*/
	}
	@Test
	public void testOrderOSServiceI() {
		//查
		//OrderOS orderOS = orderOSService.get(1);
		OrderOS orderOS = orderOSService.getOrderOsByGui("Wince 6.0");
		if (orderOS != null) {
			System.out.println(orderOS.getGui()+" | "+orderOS.getUuid());
		}
	}
	@Test
	public void OrderShellServiceI() {
		List<OrderShell> dataGrid = OrderShellService.dataGrid(1);
		if (dataGrid != null) {
			for (OrderShell o : dataGrid)
				System.out.println(o.getName()+" | "+o.getCraft());
		}
	}

	@Test
	public void testOrderAltinfoServiceI() {
		List<OrderInfoAlt> dataGrid = orderAltinfoService.listGrid(56);
		for (OrderInfoAlt alt : dataGrid) {
			//OrderInfo orderInfo = alt.getOrderInfo();
			//System.out.println(orderInfo.getClientName());
			System.out.println(alt.getAlteritem()+".....");
		}
	}
}
