package com.apical.oddm.core.dao.produce;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.apical.oddm.core.dao.basedao.BaseDaoImpl;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.page.SystemContext;
import com.apical.oddm.core.model.produce.Manufacture;

@Repository("manufactureDao")
public class ManufactureDaoImpl extends BaseDaoImpl<Manufacture> implements ManufactureDaoI {

	@Override
	public Pager<Manufacture> dataGrid() {
		String hql = "select t from Manufacture t join fetch t.orderInfo o order by t.updatetime desc";
		return this.find(hql);
	}

	/*@Override
	public Pager<Manufacture> dataGrid(Manufacture manufacture, Set<Integer> states) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "select t from Manufacture t where 1=1";
		if (manufacture != null) {
			if (StringUtils.isNotBlank(manufacture.getOrderNo())) {
				hql +=" and t.orderNo=:orderNo";
				params.put("orderNo", manufacture.getOrderNo());
			}
			if (StringUtils.isNotBlank(manufacture.getClientName())) {
				hql +=" and t.clientName=:clientName";
				params.put("clientName", manufacture.getClientName());
			}
		}
		if (states != null && !states.isEmpty()) {
			hql +=" and t.state in ("
			+ org.springframework.util.StringUtils.collectionToDelimitedString(states, ",") + ")";
		}
		hql += " order by t.updatetime desc";
		return this.findByAlias(hql, params);
	}*/
	@Override
	public Pager<Manufacture> dataGrid(Manufacture manufacture, Set<Integer> states) {
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "";
		if (SystemContext.getCurrUserId() != null && SystemContext.getCurrUserId() != 0) {
			if (manufacture != null && manufacture.isUnread()) {//当前用户未读
				sql = "SELECT t.id, t.id AS unread,"
						+ " t.version,"
						+ " t.order_id as orderId,"
						+ " o.order_no AS orderNo,"
						+ " o.client_name AS clientName,"
						+ " t.date_shipment AS dateShipment,"
						+ " t.drafter,"
						+ " t.auditor,"
						+ " t.date_issue AS dateIssue,"
						+ " t.approver,"
						+ " t.remark,"
						+ " t.notice,"
						+ " t.timestamp, t.updatetime, t.state"
						+ " from order_info o join order_manufacture t on o.id = t.order_id RIGHT JOIN order_mft_unread u ON t.id = u.mft_id WHERE u.user_id=:curruserid";
				params.put("curruserid", SystemContext.getCurrUserId());
				SystemContext.removeCurrUserId();
			} else {//当前用户所有
				sql = "SELECT t.id, (SELECT u.user_id FROM order_mft_unread u WHERE u.user_id="+SystemContext.getCurrUserId()+" AND u.mft_id=t.id) AS unread,"
						+ " t.version,"
						+ " t.order_id as orderId,"
						+ " o.order_no AS orderNo,"
						+ " o.client_name AS clientName,"
						+ " t.date_shipment AS dateShipment,"
						+ " t.drafter,"
						+ " t.auditor,"
						+ " t.date_issue AS dateIssue,"
						+ " t.approver,"
						+ " t.remark,"
						+ " t.notice,"
						+ " t.timestamp, t.updatetime, t.state"
						+ " from order_info o join order_manufacture t on o.id = t.order_id WHERE 1=1";
			}
		} else {
			sql = "SELECT t.id,"
					+ " t.version,"
					+ " t.order_id as orderId,"
					+ " o.order_no AS orderNo,"
					+ " o.client_name AS clientName,"
					+ " t.date_shipment AS dateShipment,"
					+ " t.drafter,"
					+ " t.auditor,"
					+ " t.date_issue AS dateIssue,"
					+ " t.approver,"
					+ " t.remark,"
					+ " t.notice,"
					+ " t.timestamp, t.updatetime, t.state"
					+ " from order_info o join order_manufacture t on o.id = t.order_id WHERE 1=1";
		}
		if (states != null && !states.isEmpty()) {
			sql +=" and t.state in ("
			+ org.springframework.util.StringUtils.collectionToDelimitedString(states, ",") + ")";
		}
		if (manufacture != null) {
			if (StringUtils.isNotBlank(manufacture.getClientName())) {
				sql +=" and o.client_name like :clientName";
				params.put("clientName", "%"+manufacture.getClientName()+"%");
			}
			if (StringUtils.isNotBlank(manufacture.getOrderNo())) {
				sql +=" and o.order_no like :orderNo";
				params.put("orderNo", "%"+manufacture.getOrderNo()+"%");
			}
		}
		sql += " order by t.timestamp desc";
		String sort = SystemContext.getSort();
		String order = SystemContext.getOrder();
		if ("t.updatetime".equals(sort)) {
			if ("asc".equals(order)) {
				SystemContext.setOrder("desc");
			} else {
				SystemContext.setOrder("asc");
			}
		} else if ("t.orderNo".equals(sort)) {
			SystemContext.setSort("orderNo");
		} else if ("t.clientName".equals(sort)) {
			SystemContext.setSort("clientName");
		}
		return this.findByAliasSql(sql, params, Manufacture.class, false);
	}
	@Override
	public Pager<Manufacture> dataGridByDateIssue(Date startDate, Date endDate) {
		String hql = "select t from Manufacture t join fetch t.orderInfo o where t.dateIssue>=:startDate and t.dateIssue<=:endDate order by t.dateIssue desc";
		Map<String, Object> params = new HashMap<String, Object>(2);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		return this.findByAlias(hql, params);
	}

	@Override
	public Pager<Manufacture> dataGridByDateUpdate(Date startDate, Date endDate) {
		String hql = "select t from Manufacture t join fetch t.orderInfo o where t.updatetime>=:startDate and t.updatetime<=:endDate order by t.updatetime desc";
		Map<String, Object> params = new HashMap<String, Object>(2);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		return this.findByAlias(hql, params);
	}
	
	@Override
	public Manufacture getManufacture(int id) {
		String hql = "select t from Manufacture t join fetch t.orderInfo o "
				+ "left join fetch t.orderMftFittings "
				+ "left join fetch t.orderMftOs "
				+ "left join fetch t.orderMftPackages p left join fetch p.orderMftPackageTitle "
				+ "left join fetch t.orderMftShells "
				+ "where t.id=?";
		return (Manufacture) this.queryObject(hql, id);
	}

	/*@Override
	public Pager<Manufacture> dataGrid(Set<Integer> states) {
		return this.find("select t from Manufacture t where t.state in ("
				+ org.springframework.util.StringUtils.collectionToDelimitedString(states, ",") + ") order by t.updatetime desc");
	}*/

	@Override
	public Manufacture getManufacture(Manufacture manufacture, boolean lazy) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql;
		if (lazy) {//慢
			hql = "select t from Manufacture t join fetch t.orderInfo o where 1=1";
		} else {
			hql = "select t from Manufacture t join fetch t.orderInfo o "
					+ "left join fetch t.orderMftFittings "
					+ "left join fetch t.orderMftOs "
					+ "left join fetch t.orderMftPackages p left join fetch p.orderMftPackageTitle "
					+ "left join fetch t.orderMftShells where 1=1";
		}
		if (manufacture != null) {
			if (StringUtils.isNotBlank(manufacture.getOrderNo())) {
				hql +=" and o.orderNo=:orderNo";
				params.put("orderNo", manufacture.getOrderNo());
			}
		}
		return (Manufacture) this.queryObjectByAlias(hql, params);

	}

	@Override
	public void updateUpdateTime(Integer id) {
		String sql = "update order_manufacture set updatetime=now() where id="+id;
		this.updateBySql(sql);		
	}
}
