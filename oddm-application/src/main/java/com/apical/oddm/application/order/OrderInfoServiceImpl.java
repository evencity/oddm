package com.apical.oddm.application.order;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apical.oddm.application.base.BaseServiceImpl;
import com.apical.oddm.core.dao.bom.BomInfoDaoI;
import com.apical.oddm.core.dao.document.DocumentDaoI;
import com.apical.oddm.core.dao.order.MaterialFollowupDaoI;
import com.apical.oddm.core.dao.order.OrderFollowupDaoI;
import com.apical.oddm.core.dao.order.OrderInfoDaoI;
import com.apical.oddm.core.dao.order.OrderUnreadDaoI;
import com.apical.oddm.core.dao.produce.ManufactureDaoI;
import com.apical.oddm.core.dao.produce.OrderPrototypeDaoI;
import com.apical.oddm.core.dao.produce.OrderRecordDaoI;
import com.apical.oddm.core.dao.sale.SaleInfoDaoI;
import com.apical.oddm.core.dao.sale.SalePoDaoI;
import com.apical.oddm.core.model.bom.BomInfo;
import com.apical.oddm.core.model.document.Document;
import com.apical.oddm.core.model.order.MaterialFollowup;
import com.apical.oddm.core.model.order.OrderFollowup;
import com.apical.oddm.core.model.order.OrderInfo;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.produce.Manufacture;
import com.apical.oddm.core.model.produce.ManufactureOs;
import com.apical.oddm.core.model.produce.OrderPrototype;
import com.apical.oddm.core.model.produce.OrderRecord;
import com.apical.oddm.core.model.sale.SaleInfo;
import com.apical.oddm.core.model.sale.SalePo;
import com.apical.oddm.core.vo.order.OrderInfoAllYearVo;
import com.apical.oddm.core.vo.order.OrderInfoYearVo;

@Service("orderInfoService")
public class OrderInfoServiceImpl extends BaseServiceImpl<OrderInfo> implements OrderInfoServiceI {

	@Autowired
	private OrderInfoDaoI orderInfoDao;
	
	@Autowired
	private BomInfoDaoI bomInfoDao;
	
	@Autowired
	private DocumentDaoI documentDao;
	
	@Autowired
	private MaterialFollowupDaoI materialFollowupDao;
	
	@Autowired
	private OrderFollowupDaoI orderFollowupDao;
	
	@Autowired
	private SalePoDaoI salePoDao;

	@Autowired
	private OrderRecordDaoI orderRecordDao;
	
	@Autowired
	private OrderPrototypeDaoI orderPrototypeDao;//首件确认书
	
	@Autowired
	private ManufactureDaoI manufactureDao;//生产任务书
	
	@Autowired
	private OrderUnreadDaoI orderUnreadDao;//订单未读表
	
	@Autowired
	private SaleInfoDaoI saleInfoDao;
	
/*	@Override
	public Pager<OrderInfo> dataGrid() {
		return orderInfoDao.dataGrid();
	}*/

	@Override
	public Pager<OrderInfo> dataGrid(OrderInfo orderInfo, Set<Integer> states) {
		return orderInfoDao.dataGrid(orderInfo, states);
	}
	@Override
	public Pager<OrderInfo> dataGridStatistics(OrderInfo orderInfo, Set<Integer> states) {
		return orderInfoDao.dataGridStatistics(orderInfo, states);
	}

/*	@Override
	public Pager<OrderInfo> dataGridByDateOrder(Date startDate, Date endDate) {
		return orderInfoDao.dataGridByDateOrder(startDate, endDate);
	}
*/
	@Override
	public OrderInfo getOrderInfo(int id) {
		return orderInfoDao.getOrderInfo(id);
	}
	
/*	@Override
	public Pager<OrderInfo> dataGrid(Set<Integer> states) {
		return orderInfoDao.dataGrid(states);
	}*/

	@Override
	public void delete(int id) {
		orderUnreadDao.deleteAllOrderId(id);//删除所有未读记录
		super.delete(id);
	}

	@Override
	public List<OrderInfo> getAllDocumentByOrderId(int orderId) {
		List<OrderInfo> list = new LinkedList<OrderInfo>();
		OrderInfo orderInfoAndDocument = orderInfoDao.getOrderInfoAndDocument(orderId);
		if (orderInfoAndDocument != null) {
			list.add(orderInfoAndDocument);
			while (orderInfoAndDocument.getOrderInfo() != null) {
				orderInfoAndDocument = orderInfoDao.getOrderInfoAndDocument(orderInfoAndDocument.getOrderInfo().getId());
				list.add(orderInfoAndDocument);
			}
			return list;
		} else {
			return null;
		}
		//return getOrder(list, orderId);
	}
	
	@Override
	public void deleteAll(int id) {
		orderUnreadDao.deleteAllOrderId(id);//删除所有未读记录
		OrderInfo orderInfo = this.get(id);
		
		Set<Manufacture> manufactures = orderInfo.getManufactures();
		//删除生产任务书
		if (manufactures != null) {
			for (Manufacture manufacture : manufactures) {
				manufactureDao.delete(manufacture.getId());
			}
		}
		Set<BomInfo> bomInfos = orderInfo.getBomInfos();
		//删除bom
		if (bomInfos != null) {
			for (BomInfo bomInfo : bomInfos) {
				bomInfoDao.delete(bomInfo.getId());
			}
		}
		//
		//删除文档
		Set<Document> documents = orderInfo.getDocuments();
		if (documents != null) {
			for (Document document : documents) {
				/*		OrderInfo orderInfo = this.get(id);
				Set<Document> documents = orderInfo.getDocuments();
				for (Document document : documents) {//先删除文档，这里后面改成阿里云api
					if (document.getPath() != null) {
						File file = new File(document.getPath());
						if (file.exists()) {
							file.delete();
						}
					}
				}*/
				documentDao.delete(document.getId());
			}
		}
		//删除物料跟进
		Set<MaterialFollowup> materialFollowups = orderInfo.getMaterialFollowups();
		if (materialFollowups != null) {
			for (MaterialFollowup materialFollowup : materialFollowups) {
				materialFollowupDao.delete(materialFollowup);
			}
		}
		//删除订单跟进
		Set<OrderFollowup> orderFollowups = orderInfo.getOrderFollowups();
		if (orderFollowups != null) {
			for (OrderFollowup orderFollowup : orderFollowups) {
				orderFollowupDao.delete(orderFollowup);
			}
		}
		//删除内部订单
		Set<SalePo> salePos = orderInfo.getSalePos();
		if (salePos != null) {
			for (SalePo salePo : salePos) {
				salePoDao.delete(salePo.getId());
			}
		}
		//删除销售信息
		Set<SaleInfo> saleInfos = orderInfo.getSaleInfo();
		if (saleInfos != null) {
			for (SaleInfo saleInfo : saleInfos) {
				saleInfoDao.delete(saleInfo);
			}
		}
		//删除履历
		Set<OrderRecord> orderRecords = orderInfo.getOrderRecords();
		if (orderRecords != null) {
			for (OrderRecord orderRecord : orderRecords) {
				orderRecordDao.delete(orderRecord);
			}
		}
		//删除首件确认书
		Set<OrderPrototype> orderPrototypes = orderInfo.getOrderPrototypes();
		if (orderPrototypes != null) {
			for (OrderPrototype orderPrototype : orderPrototypes) {
				orderPrototypeDao.delete(orderPrototype);
			}
		}
		this.delete(id);
	}

	@Override
	public OrderInfo getByOrder(OrderInfo orderInfo) {
		return orderInfoDao.getByOrder(orderInfo);
	}

	@Override
	public OrderInfo getByProductFactory(String productFactory) {
		return orderInfoDao.getByProductFactory(productFactory);
	}

	@Override
	public boolean addOtherEntity(int orderId) {
		OrderInfo orderInfo = orderInfoDao.get(orderId);
		//触发生成bom
		BomInfo bomInfo = new BomInfo();
		bomInfo.setOrderInfo(orderInfo);
		bomInfo.setState(1);
		bomInfoDao.add(bomInfo);
		//订单跟进
		OrderFollowup orderFollowup = new OrderFollowup();
		orderFollowup.setOrderInfo(orderInfo);
		orderFollowup.setState(1);
		orderFollowup.setUuid(1);
		orderFollowup.setSpecification(1);
		orderFollowup.setBootLogo(1);
		orderFollowup.setCarton(1);
		orderFollowup.setMap(1);
		orderFollowup.setShell(1);
		orderFollowup.setMembrane(1);
		orderFollowup.setFitting(1);
		orderFollowup.setSorfware(1);
		orderFollowup.setHardware(1);
		orderFollowup.setColorbox(1);
		orderFollowup.setInspection(1);
		orderFollowup.setPreFile(1);
		orderFollowup.setTags(1);
		orderFollowup.setPacking(1);
		orderFollowup.setAgency(1);
		orderFollowup.setPayment(1);
		orderFollowupDao.add(orderFollowup);
		//物料交期
		MaterialFollowup materialFollowup = new MaterialFollowup();
		materialFollowup.setOrderInfo(orderInfo);
		materialFollowup.setMtCode("");
		materialFollowup.setState(1);
		materialFollowupDao.add(materialFollowup);
		//触发生成生产任务书
		Manufacture manufacture = new Manufacture();
		manufacture.setOrderInfo(orderInfo);
		manufacture.setState(1);
		if (orderInfo.getOrderOs() != null ) {
			if (!orderInfo.getOrderOs().isEmpty()) {
				String uuid = orderInfo.getOrderOs().iterator().next().getUuid();
				Set<ManufactureOs> orderMftOs = new HashSet<ManufactureOs>();
				ManufactureOs os = new ManufactureOs();
				os.setOrderManufacture(manufacture);
				os.setUuid(uuid);
				orderMftOs.add(os);
				manufacture.setOrderMftOs(orderMftOs);
			}
		}
		manufactureDao.add(manufacture);
		//触发生成销售信息
		SaleInfo saleInfo = new SaleInfo();
		saleInfo.setOrderInfo(orderInfo);
		saleInfo.setQuantitySale(0);
		saleInfo.setSpare(0);
		saleInfoDao.add(saleInfo);
		
		return true;
	}

	@Override
	public List<Object> listTop(OrderInfo orderInfo) {
		return orderInfoDao.listTop(orderInfo);
	}

	@Override
	public OrderInfo getByOrderNo(String orderNo) {
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setOrderNo(orderNo);
		return orderInfoDao.getByOrder(orderInfo);
	}

	@Override
	public void updateUpdateTime(int id) {
		orderInfoDao.updateUpdateTime(id);
	}
	@Override
	public List<OrderInfoYearVo> getOrderInfoYearVoList(int type, String year) {
		return orderInfoDao.getOrderInfoYearVoList(type, year);
	}
	@Override
	public List<OrderInfoAllYearVo> getOrderInfoAllYearVoList(String yearStart,
			String yearEnd) {
		return orderInfoDao.getOrderInfoAllYearVoList(yearStart, yearEnd);
	}
	
	//递归查询，但是查询出来的结果集过多，但是会有双倍的sql开销，舍弃！！
/*	private List<OrderInfo> getOrder(List<OrderInfo> list, int orderId) {
		OrderInfo orderInfo = orderInfoDao.get(orderId);
		if (orderInfo != null) {
			orderInfo.getDocuments().isEmpty();//快加载
			list.add(orderInfo);
			if (orderInfo.getOrderInfo() != null) {
				getOrder(list, orderInfo.getOrderInfo().getId());
			}
		}
		return list;
	}*/
}
