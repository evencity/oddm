package com.apical.oddm.core.dao.sale;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.apical.oddm.core.dao.basedao.BaseDaoImpl;
import com.apical.oddm.core.model.order.OrderInfo;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.page.SystemContext;
import com.apical.oddm.core.model.sale.SaleOut;
import com.apical.oddm.infra.util.OddmDateUtil;

@Repository("saleOutDao")
public class SaleOutDaoImpl extends BaseDaoImpl<SaleOut> implements SaleOutDaoI {

/*	@Override
	public Pager<SaleOut> dataGrid() {
		String hql = "select t from SaleOut t join fetch t.orderInfo o order by o.dateOrder desc";
		return this.find(hql);
	}*/

	@Override
	public SaleOut getDetail(int id) {
		String sql = "select t.id, t.order_id as orderId, t.timestamp, t.updatetime,"
				+ " t.group_ as group, t.shipment_no as shipmentNo, t.amount, t.currency,"
				+ " t.status, t.date_inspection as dateInspection, t.date_shipment as dateShipment,"
				+ " t.freight_forwarding as freightForwarding, t.schedule_so as scheduleSo,"
				+ " t.payed, t.date_pl as datePl, t.date_ci as date_ci,"
				+ " o.order_no as orderNo, o.product_factory as productFactory, o.product_client as productClient,"
				+ " o.client_name as clientName,  o.client_name_code as clientNameCode, o.quantity, o.district,"
				+ " o.date_order as dateOrder, o.seller, o.merchandiser, o.seller_id as sellerId, o.merchandiser_id as merchandiserId,"
				+ " pt.name as productType"
				+ " from sale_out t"
				+ " left join order_info o on t.order_id=o.id"
				+ " left join product p on o.product_factory=p.name"
				+ " left join product_type pt on pt.id=p.type where t.id=?";
		 List<SaleOut> listBySql = this.listBySql(sql, id, SaleOut.class, false);
		 if (!listBySql.isEmpty()) {
			 return listBySql.get(0);
		 }
		 return null;
	}
	
	@Override
	public SaleOut getByOrderId(int orderId, boolean lazy) {
		String sql = null;
		if (lazy) {
			sql = "select t.id, t.order_id as orderId, t.timestamp, t.updatetime,"
					+ " t.group_ as group, t.shipment_no as shipmentNo, t.amount, t.currency,"
					+ " t.status, t.date_inspection as dateInspection, t.date_shipment as dateShipment,"
					+ " t.freight_forwarding as freightForwarding, t.schedule_so as scheduleSo,"
					+ " t.payed, t.date_pl as datePl, t.date_ci as date_ci"
					+ " from sale_out t"
					+ " where t.order_id=?";
		} else {
			sql = "select t.id, t.order_id as orderId, t.timestamp, t.updatetime,"
					+ " t.group_ as group, t.shipment_no as shipmentNo, t.amount, t.currency,"
					+ " t.status, t.date_inspection as dateInspection, t.date_shipment as dateShipment,"
					+ " t.freight_forwarding as freightForwarding, t.schedule_so as scheduleSo,"
					+ " t.payed, t.date_pl as datePl, t.date_ci as date_ci,"
					+ " o.order_no as orderNo, o.product_factory as productFactory, o.product_client as productClient,"
					+ " o.client_name as clientName,  o.client_name_code as clientNameCode, o.quantity, o.district,"
					+ " o.date_order as dateOrder, o.seller, o.merchandiser, o.seller_id as sellerId, o.merchandiser_id as merchandiserId,"
					+ " pt.name as productType"
					+ " from sale_out t"
					+ " left join order_info o on t.order_id=o.id"
					+ " left join product p on o.product_factory=p.name"
					+ " left join product_type pt on pt.id=p.type where o.id=?";
		}
		List<SaleOut> listBySql = this.listBySql(sql, orderId, SaleOut.class, false);
		 if (!listBySql.isEmpty()) {
			 return listBySql.get(0);
		 }
		return null;
	}
	
	/**
	 * 生成sql
	 * @param saleOut
	 * @param params
	 * @return
	 */
	private String getSql(SaleOut saleOut, Map<String, Object> params) {
		if ("id".equals(SystemContext.getSort())) {
			SystemContext.setSort("t.id");
		}
		String sql = "select t.id, t.order_id as orderId, t.timestamp, t.updatetime,"
				+ " t.group_ as group_, t.shipment_no as shipmentNo, t.amount, t.currency,"
				+ " t.status, t.date_inspection as dateInspection, t.date_shipment as dateShipment,"
				+ " t.freight_forwarding as freightForwarding, t.schedule_so as scheduleSo,"
				+ " t.payed, t.date_pl as datePl, t.date_ci as date_ci,"
				+ " o.order_no as orderNo, o.product_factory as productFactory, o.product_client as productClient,"
				+ " o.client_name as clientName,  o.client_name_code as clientNameCode, o.quantity, o.district,"
				+ " o.date_order as dateOrder, o.seller, o.merchandiser, o.seller_id as sellerId, o.merchandiser_id as merchandiserId,"
				+ " pt.name as productType"
				+ " from sale_out t"
				+ " left join order_info o on t.order_id=o.id"
				+ " left join product p on o.product_factory=p.name"
				+ " left join product_type pt on pt.id=p.type where 1=1";
		if (saleOut != null) {
			if (StringUtils.isNotBlank(saleOut.getProductType())) {
				sql +=" and pt.name=:ptname";
				params.put("ptname", saleOut.getProductType());
			}
			if (StringUtils.isNotBlank(saleOut.getCurrency())) {
				sql +=" and t.currency=:currency";
				params.put("currency", saleOut.getCurrency());
			}
			if (saleOut.getDateShipmentStart() != null  && saleOut.getDateShipmentEnd() != null) {
				sql +=" and o.date_shipment>=:startDateShipment and o.date_shipment<=:endDateShipment";
				params.put("startDateShipment", saleOut.getDateShipmentStart());
				params.put("endDateShipment", saleOut.getDateShipmentEnd());
			} else {
				if (saleOut.getDateShipmentStart() != null) {
					sql +=" and o.date_shipment>=:startDateShipment";
					params.put("startDateShipment", saleOut.getDateShipmentStart());
				}
				if (saleOut.getDateShipmentEnd() != null) {
					sql +=" and o.date_shipment<=:endDateShipment";
					params.put("endDateShipment", saleOut.getDateShipmentEnd());
				}
			}
			if (StringUtils.isNotBlank(saleOut.getDateShipmentMonth())) {
				try {
					//获取每月的1号
					Date startDate = OddmDateUtil.dayParse(saleOut.getDateShipmentMonth()+"-1");
					Date endDate = OddmDateUtil.getMonthMaxDay(saleOut.getDateShipmentMonth());
					sql +=" and o.date_shipment>=:startDateShipment";
					sql +=" and o.date_shipment<=:endDateShipment";
					params.put("startDateShipment", startDate);
					params.put("endDateShipment", endDate);
				} catch (ParseException e) {
					throw new RuntimeException("解析月份异常:"+saleOut.getDateShipmentMonth());
				}
			}
			
			OrderInfo orderInfo = saleOut.getOrderInfo();
			if (orderInfo != null) {
				if (StringUtils.isNotBlank(orderInfo.getOrderNo())) {
					sql +=" and o.order_no like :orderNo";
					params.put("orderNo", "%"+orderInfo.getOrderNo()+"%");
				}
				if (StringUtils.isNotBlank(orderInfo.getClientName())) {
					sql +=" and o.client_name like :clientName";
					params.put("clientName", "%"+orderInfo.getClientName()+"%");
				}
				if (StringUtils.isNotBlank(orderInfo.getClientNameCode())) {
					sql +=" and o.client_name_code like :clientNameCode";
					params.put("clientNameCode", "%"+orderInfo.getClientNameCode()+"%");
				}
				if (StringUtils.isNotBlank(orderInfo.getDistrict())) {
					sql +=" and o.district like :district";
					params.put("district", "%"+orderInfo.getDistrict()+"%");
				}
				if (StringUtils.isNotBlank(orderInfo.getProductFactory())) {
					sql +=" and o.product_factory like :productFactory";
					params.put("productFactory", "%"+orderInfo.getProductFactory()+"%");
				}
				if (StringUtils.isNotBlank(orderInfo.getProductClient())) {
					sql +=" and o.product_client like :productClient";
					params.put("productClient", "%"+orderInfo.getProductClient()+"%");
				}
				if (StringUtils.isNotBlank(orderInfo.getClientBrand())) {
					sql +=" and o.client_brand like :clientBrand";
					params.put("clientBrand", "%"+orderInfo.getClientBrand()+"%");
				}
				if (StringUtils.isNotBlank(orderInfo.getSeller())) {
					sql +=" and o.seller=:seller";
					params.put("seller", orderInfo.getSeller());
				}
				if (StringUtils.isNotBlank(orderInfo.getMerchandiser())) {
					sql +=" and o.merchandiser=:merchandiser";
					params.put("merchandiser", orderInfo.getMerchandiser());
				}
				if (orderInfo.getSellerId() != null && orderInfo.getMerchandiserId() != null) {
					sql +=" and (o.seller_id=:sellerId";
					params.put("sellerId", orderInfo.getSellerId());
					sql +=" or o.merchandiser_id=:merchandiserId)";
					params.put("merchandiserId", orderInfo.getMerchandiserId());
				} else {
					if (orderInfo.getSellerId() != null) {
						sql +=" and o.seller_id=:sellerId";
						params.put("sellerId", orderInfo.getSellerId());
					}
					if (orderInfo.getMerchandiserId() != null) {
						sql +=" and o.merchandiser_id=:merchandiserId";
						params.put("merchandiserId", orderInfo.getMerchandiserId());
					}
				}
				if (StringUtils.isNotBlank(orderInfo.getDateOrderMonth())) {
					try {
						//获取每月的1号
						Date startDate = OddmDateUtil.dayParse(orderInfo.getDateOrderMonth()+"-1");
						Date endDate = OddmDateUtil.getMonthMaxDay(orderInfo.getDateOrderMonth());
						sql +=" and o.date_Order>=:startDateOrder";
						sql +=" and o.date_Order<=:endDateOrder";
						params.put("startDateOrder", startDate);
						params.put("endDateOrder", endDate);
					} catch (ParseException e) {
						throw new RuntimeException("解析月份异常:"+orderInfo.getDateOrderMonth());
					}
				}
			}
		}
		sql += " order by t.date_shipment desc";
		return sql;
	}
	
	@Override
	public Pager<SaleOut> dataGrid(SaleOut saleOut) {
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = getSql(saleOut, params);
		return this.findByAliasSql(sql, params, SaleOut.class, false);
	}

	@Override
	public List<SaleOut> list(SaleOut saleOut) {
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = getSql(saleOut, params);
		SystemContext.setSort("o.date_shipment");
		SystemContext.setOrder("asc");
		return this.listByAliasSql(sql, params, SaleOut.class, false);
	}
	
/*	@Override
	public List<SaleDeptStatisVo> getSaleStatisVo(String year, String currency) {
		String sql = "select pt.name productType, sum(t.quantity_sale * t.unit_price) amount, DATE_FORMAT(o.date_order, '%Y%m') months from sale_out t left join order_info o on t.order_id=o.id left join product p on o.product_factory=p.name left join product_type pt on pt.id=p.type"
				+ " where t.currency =? and o.product_factory!='' and DATE_FORMAT(o.date_order, '%Y')=?"
				+ " group by months, productType order by o.date_order asc";
		String[] param = {currency, year};
		return this.listBySql(sql, param, SaleDeptStatisVo.class, false);
	}

	@Override
	public List<SaleOutYearVo> getSaleOutYearVoList(int type, String year,
			String currency) {
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "";
		String column = "seller";
		if (type == SaleOutYearVo.sellerName) {
			column= "seller";
		} else if (type == SaleOutYearVo.clientName) {
			column= "client_name";
		} else if (type == SaleOutYearVo.districtName) {
			column= "district";
		} else if (type == SaleOutYearVo.p roductFactory) {
			column= "product_factory";
		}
		if (type == SaleOutYearVo.productName) {
			column= "name";
			sql = "select MONTH(o.date_order) as month,"
					+ " pt."+column+" as name,"
					+ " sum(o.quantity) as quantitysDto,"
					+ " sum(t.quantity_sale) as quantitySalesDto,"
					+ " sum(t.spare) as sparesDto,"
					+ " sum(t.quantity_sale*t.unit_price) as totalsDto"
					+ " from sale_out t"
					+ " left join order_info o on t.order_id=o.id"
					+ " left join product p on o.product_factory=p.name"
					+ " left join product_type pt on pt.id=p.type"
					+ " where 1=1";
				sql +=" and t.currency=:currency";
			if (StringUtils.isNotBlank(currency)) {
				params.put("currency", currency);
			} else {
				params.put("currency", "USD");
			}
			if (StringUtils.isNotBlank(year)) {
				String endYear = Integer.parseInt(year)+1+"-1-1";
				sql +=" and o.date_order>=:yearStart";
				sql +=" and o.date_order<:yearEnd";
				params.put("yearStart", year+"-1-1");
				params.put("yearEnd", endYear);
			} else {
				return null;
			}
			sql += " group by pt."+column+", MONTH(o.date_order) order by MONTH(o.date_order) asc";
		} else {
			sql = "select MONTH(o.date_order) as month,"
					+ " o."+column+" as name,"
					+ " sum(o.quantity) as quantitysDto,"
					+ " sum(t.quantity_sale) as quantitySalesDto,"
					+ " sum(t.spare) as sparesDto,"
					+ " sum(t.quantity_sale*t.unit_price) as totalsDto"
					+ " from sale_out t"
					+ " left join order_info o on t.order_id=o.id"
					+ " where 1=1";
				sql +=" and t.currency=:currency";
			if (StringUtils.isNotBlank(currency)) {
				params.put("currency", currency);
			} else {
				params.put("currency", "USD");
			}
			if (StringUtils.isNotBlank(year)) {
				String endYear = Integer.parseInt(year)+1+"-1-1";
				sql +=" and o.date_order>=:yearStart";
				sql +=" and o.date_order<:yearEnd";
				params.put("yearStart", year+"-1-1");
				params.put("yearEnd", endYear);
			} else {
				return null;
			}
			sql += " group by o."+column+", MONTH(o.date_order) order by MONTH(o.date_order) asc";
		}
		return this.listByAliasSql(sql, params, SaleOutYearVo.class, false);
	}

	@Override
	public List<SaleOutAllYearVo> getSaleOutAllYearVoList(String yearStart,
			String yearEnd, String currency) {
		Map<String, Object> params = new HashMap<String, Object>(2);
		String sql = "select date_format(o.date_order, '%Y-%c') as yearMonth,"
				+ " sum(o.quantity) as quantitysDto,"
				+ " sum(t.quantity_sale) as quantitySalesDto,"
				+ " sum(t.spare) as sparesDto,"
				+ " sum(t.quantity_sale*t.unit_price) as totalsDto"
				+ " from sale_out t left join order_info o on t.order_id = o.id"
				+ " where 1=1";
		sql +=" and t.currency=:currency";
		if (StringUtils.isNotBlank(currency)) {
			params.put("currency", currency);
		} else {
			params.put("currency", "USD");
		}
		if (StringUtils.isBlank(yearStart)) {
			yearStart = "2017-1-1";
		} else {
			yearStart = yearStart+"-1-1";
		}
		sql +=" and o.date_order>=:yearStart";
		params.put("yearStart", yearStart);
		if (StringUtils.isNotBlank(yearEnd)) {
			sql +=" and o.date_order<:endYear";
			params.put("yearEnd", yearEnd+"-1-1");
		}
		sql += " group by date_format(o.date_order, '%Y-%c') order by o.date_order asc";
		return this.listByAliasSql(sql, params, SaleOutAllYearVo.class, false);
	}*/

}
