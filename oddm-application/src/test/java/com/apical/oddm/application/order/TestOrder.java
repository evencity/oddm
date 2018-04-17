package com.apical.oddm.application.order;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.apical.oddm.core.model.order.MaterialFollowup;
import com.apical.oddm.core.model.order.OrderFollowup;
import com.apical.oddm.core.model.order.OrderInfo;
import com.apical.oddm.core.model.order.OrderInfoAlt;
import com.apical.oddm.core.model.order.OrderOS;
import com.apical.oddm.core.model.order.OrderShell;
import com.apical.oddm.core.model.order.OrderUnread;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.page.SystemContext;
import com.apical.oddm.core.vo.order.OrderInfoYearVo;

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
	private OrderHardwareServiceI orderHardwareService;
	
	@Autowired
	private OrderFollowupServiceI orderFollowupService;
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired
	MaterialFollowupServiceI materialFollowupService;
	@Autowired
	OrderUnreadServiceI orderReadService;
	
	@Test
	public void testorderReadService() {
		OrderUnread or = new OrderUnread(61, 1650);
		orderReadService.add(or);
		
	}
	@Test
	public void testOrderHardwareServiceI() {
		orderHardwareService.delete(7750);
	}
	@Test
	public void testorderInfoService2() {
		//查
		/*List<OrderInfoYearSellerVo> lists = orderInfoService.getOrderInfoYearSellerVoList("2016");
		for (OrderInfoYearSellerVo o : lists) {
			System.out.println(o.getMonth()+"  "+o.getSeller()+"  "+o.getQauntitys());
		}*/
		
		//查
		List<OrderInfoYearVo> voList = orderInfoService.getOrderInfoYearVoList(1, "2016");
		HashMap<String, Map<Integer, Integer>> sellerMap = new HashMap<String, Map<Integer, Integer>>();
		HashMap<Integer, Integer> monthMap = null;
		for (OrderInfoYearVo vo : voList) {
			if (sellerMap.get(vo.getName()) == null) {
				monthMap = new HashMap<Integer, Integer>();
				sellerMap.put(vo.getName(), monthMap);
			}
			sellerMap.get(vo.getName()).put(vo.getMonth(), vo.getQuantitys());
		}
		for (Entry<String, Map<Integer, Integer>> entry : sellerMap.entrySet()) {
			System.out.println("key= " + entry.getKey() + "and  value= "+ entry.getValue());
			
		}
		
		//删除全部
		//orderInfoService.deleteAll(1767);
		//查询前10行
	/*	OrderInfo orderInfo = new OrderInfo();
		orderInfo.setClientName("丽");
		//orderInfo.setClientName("");
		//orderInfo.setDistrict("克");
		//orderInfo.setDistrict("");
		List<Object> listTop = orderInfoService.listTop(orderInfo);
		System.out.println(listTop.size());
		for (Object o: listTop) {
			System.out.println(o);
		}*/
		//查询 添加用户未读和已读
		/*OrderInfo orderInfo = new OrderInfo();
		//orderInfo.setOrderNo("xxhhhhhhhh");
		//orderInfo.setSellerId(61);
		//orderInfo.setMerchandiserId(61);
		SystemContext.setCurrUserId(61);//如不set当前用户id，则不要取getRead，否则报懒加载错误
		Set<Integer> states = new HashSet();
		orderInfo.setUnread(1);//要和这个：SystemContext.setCurrUserId(61);一起用
		//states.add(3);
		SystemContext.setPageSize(20);
		Pager<OrderInfo> dataGrid = orderInfoService.dataGrid(orderInfo, states);
		for (OrderInfo o : dataGrid.getRows()) {
			System.out.println(o.getId()+"\t"+o.getOrderNo()+"\t"+o.getMerchandiserId()+"\t"+o.getUnread());
		}
		*/
		//测试getOrderInfo 删除订单已读状态
		/*orderInfoService.getOrderInfo(1650);
		orderReadService.delete(61,1650);*/
	}
	
	@Test
	public void testMaterialFollowupServiceI() {
		OrderInfo order = new OrderInfo();
		Pager<MaterialFollowup> dataGridByUserId = materialFollowupService.dataGridByOrderInfo(order, null);
		System.out.println(dataGridByUserId.getRows().get(0).getMtCode());
	}
	
	@Test
	public void testOrderFollowupServiceI() throws ParseException {
		/*OrderFollowup orderFollowup2 = orderFollowupService.get(42);
		orderFollowup2.setBootLogo(1);
		System.err.println(orderFollowup2.getTimestamp());
		System.err.println((System.currentTimeMillis()-orderFollowup2.getTimestamp().getTime())>5*60*60*1000);

		orderFollowupService.edit(orderFollowup2);*/
		//查询
		OrderInfo orderInfo = new OrderInfo();
		/*Date date = new Date();
		Date parsestart = df.parse("2016-12-12");
		Date parsesend = df.parse("2016-12-12");

		orderInfo.setDateOrderStart(parsestart);
		orderInfo.setDateOrderEnd(parsesend);*/
		List<OrderFollowup> listAll = orderFollowupService.listAll(orderInfo, null);
	/*	Pager<OrderFollowup> dataGridByOrderInfo = orderFollowupService.dataGridByOrderInfo(orderInfo, null);
		listAll = dataGridByOrderInfo.getRows();*/
		int i = 0;
		for(OrderFollowup f : listAll) {
			i++;
			System.out.println(f.getOrderInfo().getOrderNo());
		}
		System.out.println("iiiiiiiiiiiiiii "+i);
	}
	@Test
	public void testOrderInfoServiceI() throws ParseException {
		//编辑 1
	/*	OrderInfo orderInfo = orderInfoService.get(75);
		//orderInfo.setClientName("小梅2");
		orderInfoService.edit(orderInfo);*/
		
		//编辑 2 不能用get(id)接口 这个是慢加载的，否则orderInfo.getOrderOs();会报错
		/*OrderInfo orderInfo = orderInfoService.getOrderInfo(75);
		//orderInfo.setClientName("小ssssdd梅");
		
		OrderOS orderOS = null;
		Set<OrderOS> orderOs = orderInfo.getOrderOs();
		if (orderOs != null && !orderOs.isEmpty()) {
			orderOS = orderOs.iterator().next();
		} else {
			orderOS = new OrderOS();
		}
		orderOS.setGui("Wincde ");
	    Set<OrderOS> orderOsSet = new HashSet<OrderOS>();
		orderOsSet.add(orderOS);
		orderInfo.setOrderOs(orderOsSet);
		orderInfoService.edit(orderInfo);*/
		
		//编辑 3 ，每个接口单独调用
	/*	OrderInfo orderInfo = orderInfoService.get(1);
		orderInfo.setClientName("小d梅");
		orderInfoService.edit(orderInfo);
		//OrderOS orderOS = orderOSService.getOrderOsByOrderId(15);//通过订单id查，不建议
		OrderOS orderOS = orderOSService.get(1);//最好都是第一次查询订单后，缓存本对象，再直接改
		orderOS.setGui("xxxx");
		orderOSService.edit(orderOS);*/
		
		//编辑 4  级联增加子表
		/*OrderInfo orderInfo = orderInfoService.getOrderInfo(75);
		orderInfo.setClientName("小ssssdd梅2");
		Set<OrderShell> orderShells = orderInfo.getOrderShells();
		OrderShell sh1 = new OrderShell();sh1.setColor("lgx");sh1.setOrderInfo(orderInfo);sh1.setSilkScreen(1);
		orderShells.add(sh1);
		orderInfoService.edit(orderInfo);*/
		
		//编辑 4  级联删除字表某项
		/*OrderInfo orderInfo = orderInfoService.getOrderInfo(75);
		//orderInfo.setClientName("小发大水梅2");
		
		Set<OrderShell> orderShells = orderInfo.getOrderShells();
		for (OrderShell sh : orderShells) {
			if (sh.getId() == 1) {
				sh.setColor("222");
				orderShells.remove(sh);
				System.out.println("remove");
				break;
			}
		}

//		Set<OrderShell> orderShellsCmd = new HashSet<OrderShell>();
//		OrderShell cmd1 = new OrderShell();
//		OrderShell cmd2 = new OrderShell();
		
		orderInfoService.edit(orderInfo);*/
				
		//查
		/*OrderInfo order = new OrderInfo();
		order.setSeller("李晓磊");
		Pager<OrderInfo> dataGrid = orderInfoService.dataGrid(order,null);
		
		for (OrderInfo orderInfo : dataGrid.getRows()) {
			System.out.print(orderInfo.getSeller()+" || ");
			System.out.println(orderInfo.getOrderNo());

		}*/
		//查
		OrderInfo orderInfo = orderInfoService.getOrderInfo(1633);
		//System.out.println(orderInfo.getOrderNo());
//		for (OrderHardware shell :orderInfo.getOrderHardwares()) {
//			System.out.println(shell.getName()+".....");
//		}
		 Set<OrderOS> orderOs = orderInfo.getOrderOs();
		 System.out.println("orderOsorderOsorderOsorderOsorderOsorderOs:"+orderOs.size()+"\t"+(orderOs == null));
		//查
		/*OrderInfo orderInfo = orderInfoService.get(2);
		System.out.println(orderInfo.getOrderNo());
		System.out.println(orderInfo.getOrderInfo().getId()); //得关闭lazy
*/		/*System.out.println(orderInfo.getOrderInfo().getOrderInfo().getOrderNo());*/

		//查
	/*	List<OrderInfo> dataHistoryByOrderId = orderInfoService.getAllDocumentByOrderId(1);
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
		//触发生成其他对象
		/*boolean addOtherEntity = orderInfoService.addOtherEntity(1120);
		System.out.println(addOtherEntity);*/
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
		List<OrderInfoAlt> dataGrid = orderAltinfoService.listGrid(1);
		for (OrderInfoAlt alt : dataGrid) {
			//OrderInfo orderInfo = alt.getOrderInfo();
			//System.out.println(orderInfo.getClientName());
			System.out.println(alt.getAlteritem());
		}
	}
}
