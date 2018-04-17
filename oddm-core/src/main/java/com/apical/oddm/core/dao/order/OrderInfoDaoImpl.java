package com.apical.oddm.core.dao.order;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.apical.oddm.core.constant.OrderInfoConst;
import com.apical.oddm.core.dao.basedao.BaseDaoImpl;
import com.apical.oddm.core.model.order.OrderInfo;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.page.SystemContext;
import com.apical.oddm.core.vo.order.OrderInfoAllYearVo;
import com.apical.oddm.core.vo.order.OrderInfoYearVo;
import com.apical.oddm.infra.util.OddmDateUtil;

@Repository("orderInfoDao")
public class OrderInfoDaoImpl extends BaseDaoImpl<OrderInfo> implements OrderInfoDaoI {

/*	@Override
	public Pager<OrderInfo> dataGrid() {
		String hql = "select t from OrderInfo t order by t.timestamp desc";
		return this.find(hql);
	}*/

/*	@Override
	public Pager<OrderInfo> dataGridByDateOrder(Date startDate, Date endDate) {
		String hql = "select t from OrderInfo t where t.dateOrder>=:startDate and t.dateOrder<=:endDate order by t.dateOrder desc";
		Map<String, Object> params = new HashMap<String, Object>(2);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		return this.findByAlias(hql, params);
	}*/

	@Override
	public Pager<OrderInfo> dataGrid(OrderInfo orderInfo, Set<Integer> states) {
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "";
		if (SystemContext.getCurrUserId() != null && SystemContext.getCurrUserId() != 0) {
			if (orderInfo != null && orderInfo.isUnread()) {//当前用户未读
				sql = "SELECT t.id, t.orderid_previous AS pid, t.id AS unread,"
						+ " t.order_no AS orderNo, t.seller_id AS sellerId, t.seller,"
						+ " t.merchandiser_id AS merchandiserId, t.merchandiser, t.product_factory AS productFactory,"
						+ " t.product_client AS productClient, t.quantity, t.client_name AS clientName,"
						+ " t.client_name_code AS clientNameCode, t.district, t.date_order AS dateOrder, t.date_delivery AS dateDelivery,"
						+ " t.date_examine AS dateExamine, t.place_delivery AS placeDelivery,"
						+ " t.payment, t.order_spare AS orderSpare, t.timestamp, t.updatetime, t.state"
						+ " from order_info t RIGHT JOIN order_unread u ON t.id = u.order_id WHERE u.user_id=:curruserid";
				params.put("curruserid", SystemContext.getCurrUserId());
				SystemContext.removeCurrUserId();
			} else {//当前用户所有
				sql = "SELECT t.id, t.orderid_previous AS pid, (SELECT u.user_id FROM order_unread u WHERE u.user_id="+SystemContext.getCurrUserId()+" AND u.order_id=t.id) AS unread,"
						+ " t.order_no AS orderNo, t.seller_id AS sellerId, t.seller,"
						+ " t.merchandiser_id AS merchandiserId, t.merchandiser, t.product_factory AS productFactory,"
						+ " t.product_client AS productClient, t.quantity, t.client_name AS clientName,"
						+ " t.client_name_code AS clientNameCode, t.district, t.date_order AS dateOrder,"
						+ " t.date_examine AS dateExamine, t.date_delivery AS dateDelivery, t.place_delivery AS placeDelivery,"
						+ " t.payment, t.order_spare AS orderSpare, t.timestamp, t.updatetime, t.state"
						+ " from order_info t WHERE 1=1";
			}
		} else {
			sql = "SELECT t.id, t.orderid_previous AS pid,"
					+ " t.order_no AS orderNo, t.seller_id AS sellerId, t.seller,"
					+ " t.merchandiser_id AS merchandiserId, t.merchandiser, t.product_factory AS productFactory,"
					+ " t.product_client AS productClient, t.quantity, t.client_name AS clientName,"
					+ " t.client_name_code AS clientNameCode, t.district, t.date_order AS dateOrder,"
					+ " t.date_examine AS dateExamine, t.date_delivery AS dateDelivery, t.place_delivery AS placeDelivery,"
					+ " t.payment, t.order_spare AS orderSpare, t.timestamp, t.updatetime, t.state"
					+ " from order_info t WHERE 1=1";
		}
		if (states != null && !states.isEmpty()) {
			sql +=" and t.state in ("
			+ org.springframework.util.StringUtils.collectionToDelimitedString(states, ",") + ")";
		}
		if (orderInfo != null) {
			if (StringUtils.isNotBlank(orderInfo.getClientName())) {
				sql +=" and t.client_name like :clientName";
				params.put("clientName", "%"+orderInfo.getClientName()+"%");
			}
			if (StringUtils.isNotBlank(orderInfo.getClientNameCode())) {
				sql +=" and t.client_name_code like :clientNameCode";
				params.put("clientNameCode", "%"+orderInfo.getClientNameCode()+"%");
			}
			if (StringUtils.isNotBlank(orderInfo.getProductFactory())) {
				sql +=" and t.product_factory like :productFactory";
				params.put("productFactory", "%"+orderInfo.getProductFactory()+"%");
			}
			if (StringUtils.isNotBlank(orderInfo.getProductClient())) {
				sql +=" and t.product_client like :productClient";
				params.put("productClient", "%"+orderInfo.getProductClient()+"%");
			}
			if (StringUtils.isNotBlank(orderInfo.getClientBrand())) {
				sql +=" and t.client_brand like :clientBrand";
				params.put("clientBrand", "%"+orderInfo.getClientBrand()+"%");
			}
			if (StringUtils.isNotBlank(orderInfo.getSeller())) {
				sql +=" and t.seller like :seller";
				params.put("seller", "%"+orderInfo.getSeller()+"%");
			}
			if (StringUtils.isNotBlank(orderInfo.getMerchandiser())) {
				sql +=" and t.merchandiser like :merchandiser";
				params.put("merchandiser", "%"+orderInfo.getMerchandiser()+"%");
			}
			if (StringUtils.isNotBlank(orderInfo.getOrderNo())) {
				sql +=" and t.order_no like :orderNo";
				params.put("orderNo", "%"+orderInfo.getOrderNo()+"%");
			}
			if (orderInfo.getSellerId() != null && orderInfo.getMerchandiserId() != null) {
				sql +=" and (t.seller_id=:sellerId";
				params.put("sellerId", orderInfo.getSellerId());
				sql +=" or t.merchandiser_id=:merchandiserId)";
				params.put("merchandiserId", orderInfo.getMerchandiserId());
			} else {
				if (orderInfo.getSellerId() != null) {
					sql +=" and t.seller_id=:sellerId";
					params.put("sellerId", orderInfo.getSellerId());
				}
				if (orderInfo.getMerchandiserId() != null) {
					sql +=" and t.merchandiser_id=:merchandiserId";
					params.put("merchandiserId", orderInfo.getMerchandiserId());
				}
			}
		}
		sql += " order by t.timestamp desc";
		//hql排序字段得转换成sql
		String sort = SystemContext.getSort();
		String order = SystemContext.getOrder();
		if ("updatetime".equals(sort) || "orderNo".equals(sort)) {
			if ("asc".equals(order)) {
				SystemContext.setOrder("desc");
			} else {
				SystemContext.setOrder("asc");
			}
		}
		return this.findByAliasSql(sql, params, OrderInfo.class, false);
	}
	
	@Override
	public Pager<OrderInfo> dataGridStatistics(OrderInfo orderInfo, Set<Integer> states) {
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "SELECT t.id, t.orderid_previous AS pid,"
				+ " t.order_no AS orderNo, t.seller_id AS sellerId, t.seller,"
				+ " t.merchandiser_id AS merchandiserId, t.merchandiser, t.product_factory AS productFactory,"
				+ " t.product_client AS productClient, t.quantity, t.client_name AS clientName,"
				+ " t.client_name_code AS clientNameCode, t.district, t.date_order AS dateOrder,"
				+ " t.date_examine AS dateExamine, t.date_delivery AS dateDelivery, t.place_delivery AS placeDelivery,"
				+ " t.payment, t.order_spare AS orderSpare, t.timestamp, t.updatetime, t.state,"
				+ " pt.name as productType"
				+ " from order_info t"
				+ " left join product p on t.product_factory=p.name"
				+ " left join product_type pt on pt.id=p.type"
				+ " WHERE 1=1";
		if (states != null && !states.isEmpty()) {
			sql +=" and t.state in ("
			+ org.springframework.util.StringUtils.collectionToDelimitedString(states, ",") + ")";
		}
		if (orderInfo != null) {
			if (StringUtils.isNotBlank(orderInfo.getClientName())) {
				sql +=" and t.client_name like :clientName";
				params.put("clientName", "%"+orderInfo.getClientName()+"%");
			}
			if (StringUtils.isNotBlank(orderInfo.getClientNameCode())) {
				sql +=" and t.client_name_code like :clientNameCode";
				params.put("clientNameCode", "%"+orderInfo.getClientNameCode()+"%");
			}
			if (StringUtils.isNotBlank(orderInfo.getProductFactory())) {
				sql +=" and t.product_factory like :productFactory";
				params.put("productFactory", "%"+orderInfo.getProductFactory()+"%");
			}
			if (StringUtils.isNotBlank(orderInfo.getProductClient())) {
				sql +=" and t.product_client like :productClient";
				params.put("productClient", "%"+orderInfo.getProductClient()+"%");
			}
			if (StringUtils.isNotBlank(orderInfo.getClientBrand())) {
				sql +=" and t.client_brand like :clientBrand";
				params.put("clientBrand", "%"+orderInfo.getClientBrand()+"%");
			}
			if (StringUtils.isNotBlank(orderInfo.getSeller())) {
				sql +=" and t.seller like :seller";
				params.put("seller", "%"+orderInfo.getSeller()+"%");
			}
			if (StringUtils.isNotBlank(orderInfo.getMerchandiser())) {
				sql +=" and t.merchandiser like :merchandiser";
				params.put("merchandiser", "%"+orderInfo.getMerchandiser()+"%");
			}
			if (StringUtils.isNotBlank(orderInfo.getOrderNo())) {
				sql +=" and t.order_no like :orderNo";
				params.put("orderNo", "%"+orderInfo.getOrderNo()+"%");
			}
			if (orderInfo.getSellerId() != null && orderInfo.getMerchandiserId() != null) {
				sql +=" and (t.seller_id=:sellerId";
				params.put("sellerId", orderInfo.getSellerId());
				sql +=" or t.merchandiser_id=:merchandiserId)";
				params.put("merchandiserId", orderInfo.getMerchandiserId());
			} else {
				if (orderInfo.getSellerId() != null) {
					sql +=" and t.seller_id=:sellerId";
					params.put("sellerId", orderInfo.getSellerId());
				}
				if (orderInfo.getMerchandiserId() != null) {
					sql +=" and t.merchandiser_id=:merchandiserId";
					params.put("merchandiserId", orderInfo.getMerchandiserId());
				}
			}
			if (StringUtils.isNotBlank(orderInfo.getDateOrderMonth())) {
				try {
					//获取每月的1号
					Date startDate = OddmDateUtil.dayParse(orderInfo.getDateOrderMonth()+"-1");
					Date endDate = OddmDateUtil.getMonthMaxDay(orderInfo.getDateOrderMonth());
					sql +=" and t.date_order>=:startDate";
					sql +=" and t.date_order<=:endDate";
					params.put("startDate", startDate);
					params.put("endDate", endDate);
				} catch (ParseException e) {
					throw new RuntimeException("解析月份异常:"+orderInfo.getDateOrderMonth());
				}
			}
		}
		sql += " order by t.timestamp desc";
		//hql排序字段得转换成sql
		String sort = SystemContext.getSort();
		String order = SystemContext.getOrder();
		if ("updatetime".equals(sort) || "orderNo".equals(sort)) {
			if ("asc".equals(order)) {
				SystemContext.setOrder("desc");
			} else {
				SystemContext.setOrder("asc");
			}
		}
		return this.findByAliasSql(sql, params, OrderInfo.class, false);
	}

	@Override
	public OrderInfo getOrderInfo(int id) {
		String hql = "select t from OrderInfo t "
				+ "left join fetch t.orderInfo "
				+ "left join fetch t.orderShells "//外壳
				+ "left join fetch t.orderOs "//软件
				+ "left join fetch t.orderHardwares "//硬件和屏
				+ "left join fetch t.orderPackings "//包材
				+ "left join fetch t.orderFittings "//配件
				+ "where t.id=?";
		return (OrderInfo) this.queryObject(hql, id);
	}

	@Override
	public OrderInfo getOrderInfoAndDocument(int id) {
		String hql = "select t from OrderInfo t "
				+ "left join fetch t.orderInfo "
				+ "left join fetch t.documents d "//文档
				+ "where t.id=? order by d.uploadtime asc";
		return (OrderInfo) this.queryObject(hql, id);
	}

	@Override
	public OrderInfo getByOrder(OrderInfo orderInfo) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "select t from OrderInfo t where 1=1";
		if (orderInfo == null) return null;
		if (StringUtils.isNotBlank(orderInfo.getOrderNo())) {
			hql +=" and t.orderNo=:orderNo";
			params.put("orderNo", orderInfo.getOrderNo());
		}
		if (orderInfo.getSellerId() != null && orderInfo.getMerchandiserId() != null) {
			hql +=" and (t.sellerId=:sellerId";
			params.put("sellerId", orderInfo.getSellerId());
			hql +=" or t.merchandiserId=:merchandiserId)";
			params.put("merchandiserId", orderInfo.getMerchandiserId());
		} else {
			if (orderInfo.getSellerId() != null) {
				hql +=" and t.sellerId=:sellerId";
				params.put("sellerId", orderInfo.getSellerId());
			}
			if (orderInfo.getMerchandiserId() != null) {
				hql +=" and t.merchandiserId=:merchandiserId";
				params.put("merchandiserId", orderInfo.getMerchandiserId());
			}
		}
		hql += " order by t.timestamp desc"; //必须得，否则查出旧单
		return (OrderInfo) this.queryObjectByAlias(hql, params);
	}

	@Override
	public OrderInfo getByProductFactory(String productFactory) {
		String hql = "select t from OrderInfo t "
				+ ""
				+ "left join fetch t.orderShells "//外壳
				+ "left join fetch t.orderOs "//软件
				+ "left join fetch t.orderHardwares "//硬件和屏
				+ "left join fetch t.orderPackings "//包材
				+ "left join fetch t.orderFittings "//配件
				+ "where t.productFactory=? order by t.updatetime desc";
		return (OrderInfo) this.queryObject(hql, productFactory);
	}

	@Override
	public Pager<OrderInfo> dataOrderAndDocGrid() {
		String hql = "select t from OrderInfo t left join fetch t.documents d order by t.timestamp desc";
		return this.find(hql);
	}

	@Override
	public List<Object> listTop(OrderInfo orderInfo) {
		String sql = null;
		if (orderInfo.getClientName() == "") {
			sql = "select distinct(t.client_name) from order_info t where t.client_name is not null order by t.id desc";
			return this.listPageSql(sql, null, null, null, false);
		}
		if (orderInfo.getClientBrand() == "") {
			sql = "select distinct(t.client_brand) from order_info t where t.client_brand is not null order by t.id desc";
			return this.listPageSql(sql, null, null, null, false);
		}
		if (orderInfo.getDistrict() == "") {
			sql = "select distinct(t.district) from order_info t where t.district is not null order by t.id desc";
			return this.listPageSql(sql, null, null, null, false);
		}
		if (org.apache.commons.lang.StringUtils.isNotBlank(orderInfo.getClientName())) {
			//if (SystemContext.getPageSize() == null) SystemContext.setPageSize(Integer.MAX_VALUE);
			sql = "select distinct(t.client_name) from order_info t where t.client_name like ? order by t.id desc";
			return this.listPageSql(sql, "%"+orderInfo.getClientName()+"%", null, null, false);
		}
		if (org.apache.commons.lang.StringUtils.isNotBlank(orderInfo.getClientBrand())) {
			//if (SystemContext.getPageSize() == null) SystemContext.setPageSize(Integer.MAX_VALUE);
			sql = "select distinct(t.client_brand) from order_info t where t.client_brand like ? order by t.id desc";
			return this.listPageSql(sql, "%"+orderInfo.getClientBrand()+"%", null, null, false);
		}
		if (org.apache.commons.lang.StringUtils.isNotBlank(orderInfo.getDistrict())) {
			//if (SystemContext.getPageSize() == null) SystemContext.setPageSize(Integer.MAX_VALUE);
			sql = "select distinct(t.district) from order_info t where t.district like ? order by t.id desc";
			return this.listPageSql(sql, "%"+orderInfo.getDistrict()+"%", null, null, false);
		}
		return null;		
	}

	@Override
	public void updateUserIdNull(int userId) {
		String sql = "update order_info set seller_id= null, merchandiser_id=null where seller_id="+userId+" or merchandiser_id="+userId;
		this.updateBySql(sql);
	}

	@Override
	public void updateUpdateTime(int id) {
		String sql = "update order_info set updatetime=now() where id="+id;
		this.updateBySql(sql);
	}

	@Override
	public List<OrderInfoYearVo> getOrderInfoYearVoList(int type, String year) {
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "";
		String column = "seller";
		if (type == OrderInfoYearVo.sellerName) {
			column= "seller";
		} else if (type == OrderInfoYearVo.clientName) {
			column= "client_name";
		} else if (type == OrderInfoYearVo.districtName) {
			column= "district";
		} else if (type == OrderInfoYearVo.productFactory) {
			column= "product_factory";
		}
		if (type == OrderInfoYearVo.productName) {
			sql = "select MONTH(t.date_order) as month,"
					+ " pt.name as name,"
					+ " sum(t.quantity) as quantitysDto"
					+ " from order_info t"
					+ " left join product p on t.product_factory=p.name"
					+ " left join product_type pt on pt.id=p.type"
					+ " where 1=1";
			sql +=" and t.state >=:state";
			params.put("state", OrderInfoConst.approved);
			if (StringUtils.isNotBlank(year)) {
				String endYear = Integer.parseInt(year)+1+"-1-1";
				sql +=" and t.date_order>=:yearStart";
				sql +=" and t.date_order<:yearEnd";
				params.put("yearStart", year+"-1-1");
				params.put("yearEnd", endYear);
			} else {
				return null;
			}
			sql += " group by pt.name, MONTH(t.date_order) order by MONTH(t.date_order) asc";
		} else {
			sql = "select MONTH(t.date_order) as month,"
					+ " t."+column+" as name,"
					+ " sum(t.quantity) as quantitysDto"
					+ " from order_info t where 1=1";
			sql +=" and t.state >=:state";
			params.put("state", OrderInfoConst.approved);
			if (StringUtils.isNotBlank(year)) {
				String endYear = Integer.parseInt(year)+1+"-1-1";
				sql +=" and t.date_order>=:yearStart";
				sql +=" and t.date_order<:yearEnd";
				params.put("yearStart", year+"-1-1");
				params.put("yearEnd", endYear);
			} else {
				return null;
			}
			sql += " group by t."+column+", MONTH(t.date_order) order by MONTH(t.date_order) asc";
		}
		return this.listByAliasSql(sql, params, OrderInfoYearVo.class, false);
	}
	
	@Override
	public List<OrderInfoAllYearVo> getOrderInfoAllYearVoList(String yearStart, String yearEnd) {
		Map<String, Object> params = new HashMap<String, Object>(2);
		String sql = "select date_format(t.date_order, '%Y-%c') as yearMonth,"
					+ " sum(t.quantity) as quantitysDto"
					+ " from order_info t where 1=1";
			sql +=" and t.state >=:state";
			params.put("state", OrderInfoConst.approved);
			if (StringUtils.isBlank(yearStart)) {
				yearStart = "2013-1-1";
			} else {
				yearStart = yearStart+"-1-1";
			}
			sql +=" and t.date_order>=:yearStart";
			params.put("yearStart", yearStart);
			if (StringUtils.isNotBlank(yearEnd)) {
				sql +=" and t.date_order<:endYear";
				params.put("yearEnd", yearEnd+"-1-1");
			}
			sql += " group by date_format(t.date_order, '%Y-%c') order by t.date_order asc";
		return this.listByAliasSql(sql, params, OrderInfoAllYearVo.class, false);
	}
}
