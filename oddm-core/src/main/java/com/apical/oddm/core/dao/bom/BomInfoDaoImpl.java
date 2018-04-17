package com.apical.oddm.core.dao.bom;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import com.apical.oddm.core.dao.basedao.BaseDaoImpl;
import com.apical.oddm.core.model.bom.BomInfo;
import com.apical.oddm.core.model.order.OrderInfo;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.page.SystemContext;

@Repository("bomInfoDao")
public class BomInfoDaoImpl extends BaseDaoImpl<BomInfo> implements BomInfoDaoI {

	@Override
	public BomInfo getBomDetailById(int id) {
		String hql = "select t from BomInfo t join fetch t.orderInfo o left join fetch t.bomMaterialRefs p left join fetch p.contacts where t.id=? order by p.type,p.seq asc";
		return (BomInfo) this.queryObject(hql, id);
	}
	
	@Override
	public BomInfo getBom(BomInfo bomInfo, boolean lazy) {
		Map<String, Object> params = new HashMap<String, Object>(1);
		String hql = "";
		if (lazy) {
			hql = "select t from BomInfo t join t.orderInfo o where 1=1";
		} else {
			hql = "select t from BomInfo t join fetch t.orderInfo o left join fetch t.bomMaterialRefs p left join fetch p.contacts where 1=1";
		}
		if (bomInfo != null) {
			if (StringUtils.isNotBlank(bomInfo.getMaterialCode())) {
				hql += " and t.materialCode=:materialCode";
				params.put("materialCode", bomInfo.getMaterialCode());
			}
			if (bomInfo.getOrderInfo() != null) {
				if (bomInfo.getOrderInfo().getId() != null) {
					hql += " and o.id=:orderId";
					params.put("orderId", bomInfo.getOrderInfo().getId());
				}
				if (StringUtils.isNotBlank(bomInfo.getOrderInfo().getOrderNo())) {
					hql += " and o.orderNo=:orderNo";
					params.put("orderNo", bomInfo.getOrderInfo().getOrderNo());
				}
			}
		} else {
			return null;
		}
		return (BomInfo) this.queryObjectByAlias(hql, params);
	}
	
	@Override
	public Pager<BomInfo> dataGrid() {
		String hql = "select t from BomInfo t join fetch t.orderInfo o order by t.updatetime desc";
		return this.find(hql);
	}

	@Override
	public Pager<BomInfo> dataGridByBomInfo(BomInfo bomInfo, Set<Integer> states) {
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "";
		if (SystemContext.getCurrUserId() != null && SystemContext.getCurrUserId() != 0) {
			if (bomInfo != null && bomInfo.isUnread()) {//当前用户未读
				sql = "select t.id, t.id as unread,"
						+ " t.order_id as orderId,"
						+ " t.maker, t.date_ as date, t.version, t.state,"
						+ " t.material_code as materialCode, t.product_name as productName,"
						+ " t.specification, t.brand, t.description, t.timestamp, t.updatetime,"
						+ " o.order_no AS orderNo, o.seller_id AS sellerId, o.seller,"
						+ " o.merchandiser_id AS merchandiserId, o.merchandiser, o.product_factory AS productFactory,"
						+ " o.product_client AS productClient, o.quantity, o.client_name AS clientName,"
						+ " o.client_name_code AS clientNameCode, o.district, o.date_order AS dateOrder, o.date_delivery AS dateDelivery"
						+ " from order_info o join bom_info t on o.id = t.order_id RIGHT JOIN bom_unread u ON t.id = u.bom_id WHERE u.user_id=:curruserid";
				params.put("curruserid", SystemContext.getCurrUserId());
				SystemContext.removeCurrUserId();
			} else {//当前用户所有
				sql = "select t.id,"
						+ " (SELECT u.user_id FROM bom_unread u WHERE u.user_id="+SystemContext.getCurrUserId()+" AND u.bom_id=t.id) AS unread,"
						+ " t.order_id as orderId,"
						+ " t.maker, t.date_ as date, t.version, t.state,"
						+ " t.material_code as materialCode, t.product_name as productName,"
						+ " t.specification, t.brand, t.description, t.timestamp, t.updatetime,"
						+ " o.order_no AS orderNo, o.seller_id AS sellerId, o.seller,"
						+ " o.merchandiser_id AS merchandiserId, o.merchandiser, o.product_factory AS productFactory,"
						+ " o.product_client AS productClient, o.quantity, o.client_name AS clientName,"
						+ " o.client_name_code AS clientNameCode, o.district, o.date_order AS dateOrder, o.date_delivery AS dateDelivery"
						+ " from order_info o join bom_info t on o.id = t.order_id WHERE 1=1";
			}
		} else {//查询所有用户，不返回未读信息
			sql = "select t.id,"
					+ " t.order_id as orderId,"
					+ " t.maker, t.date_ as date, t.version, t.state,"
					+ " t.material_code as materialCode, t.product_name as productName,"
					+ " t.specification, t.brand, t.description, t.timestamp, t.updatetime,"
					+ " o.order_no AS orderNo, o.seller_id AS sellerId, o.seller,"
					+ " o.merchandiser_id AS merchandiserId, o.merchandiser, o.product_factory AS productFactory,"
					+ " o.product_client AS productClient, o.quantity, o.client_name AS clientName,"
					+ " o.client_name_code AS clientNameCode, o.district, o.date_order AS dateOrder, o.date_delivery AS dateDelivery"
					+ " from order_info o join bom_info t on o.id = t.order_id WHERE 1=1";
		}
		if (states != null && !states.isEmpty()) {
			sql +=" and t.state in ("
			+ org.springframework.util.StringUtils.collectionToDelimitedString(states, ",") + ")";
		}
		if (bomInfo.getOrderInfo() != null) {
			OrderInfo orderInfo = bomInfo.getOrderInfo();
			if (orderInfo.getId() != null) {
				sql +=" and o.id=:orderId";
				params.put("orderId", orderInfo.getId());
			}
			if (StringUtils.isNotBlank(orderInfo.getOrderNo())) {
				sql +=" and o.order_no like :orderNo";
				params.put("orderNo", "%"+orderInfo.getOrderNo()+"%");
			}
			if (StringUtils.isNotBlank(orderInfo.getProductClient())) {
				sql +=" and o.product_client like :productClient";
				params.put("productClient", "%"+orderInfo.getProductClient()+"%");
			}
			if (StringUtils.isNotBlank(orderInfo.getClientName())) {
				sql +=" and o.client_name like :clientName";
				params.put("clientName", "%"+orderInfo.getClientName()+"%");
			}
			if (StringUtils.isNotBlank(orderInfo.getSeller())) {
				sql +=" and o.seller like :seller";
				params.put("seller", "%"+orderInfo.getSeller()+"%");
			}
			if (StringUtils.isNotBlank(orderInfo.getMerchandiser())) {
				sql +=" and o.merchandiser like :merchandiser";
				params.put("merchandiser", "%"+orderInfo.getMerchandiser()+"%");
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
		}
		if (bomInfo != null) {
			if (StringUtils.isNotBlank(bomInfo.getMaker())) {
				sql +=" and t.maker=:maker";
				params.put("maker", bomInfo.getMaker());
			}
			if (StringUtils.isNotBlank(bomInfo.getMaterialCode())) {
				sql +=" and t.material_code like :materialCode";
				params.put("materialCode", "%"+bomInfo.getMaterialCode()+"%");
			}
			if (StringUtils.isNotBlank(bomInfo.getProductName())) {
				sql +=" and t.product_name like :productName";
				params.put("productName", "%"+bomInfo.getProductName()+"%");
			}
		}
		sql += " order by t.id desc";
		String sort = SystemContext.getSort();
		String order = SystemContext.getOrder();
		if ("t.updatetime".equals(sort)) {
			if ("asc".equals(order)) {
				SystemContext.setOrder("desc");
			} else {
				SystemContext.setOrder("asc");
			}
		} else if ("t.orderNo".equals(sort)) {
			SystemContext.setSort("o.order_no");
		} else if ("t.poNo".equals(sort)) {
			SystemContext.setSort("t.po_no");
		}
		Pager<BomInfo> pager = this.findByAliasSql(sql, params, BomInfo.class, false);
		if (pager.getRows() != null && !pager.getRows().isEmpty()) {
			for (BomInfo b : pager.getRows()) {
				OrderInfo o = new OrderInfo();
				BeanUtils.copyProperties(b, o);
				o.setDescription(null);
				o.setState(null);
				o.setId(b.getOrderId());
				b.setOrderInfo(o);
				//System.err.println("up2" +o.getOrderNo());
			}
		}
		return pager;
	}
	
	@Override
	public Pager<BomInfo> dataGridByDateCreate(Date startDate, Date endDate) {
		String hql = "select t from BomInfo t join fetch t.orderInfo o where t.timestamp>=:startDate and t.timestamp<=:endDate order by t.timestamp desc";
		Map<String, Object> params = new HashMap<String, Object>(2);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		return this.findByAlias(hql, params);
	}

	@Override
	public Pager<BomInfo> dataGridByDateUpdate(Date startDate, Date endDate) {
		String hql = "select t from BomInfo t join fetch t.orderInfo o where t.updatetime>=:startDate and t.updatetime<=:endDate order by t.updatetime desc";
		Map<String, Object> params = new HashMap<String, Object>(2);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		return this.findByAlias(hql, params);
	}

	@Override
	public BomInfo getBomByMaterialCode(String materialCode) {
		String hql = "select t from BomInfo t where t.materialCode=?";
		return (BomInfo) this.queryObject(hql, materialCode);
	}

	@Override
	public BomInfo getBomByOrderNo(String orderNO) {
		String hql = "select t from BomInfo t join fetch t.orderInfo o where o.orderNo=?";
		return (BomInfo) this.queryObject(hql, orderNO);
	}

	@Override
	public void updateUpdateTime(int id) {
		String sql = "update bom_info set updatetime=now() where id="+id;
		this.updateBySql(sql);
	}
}
