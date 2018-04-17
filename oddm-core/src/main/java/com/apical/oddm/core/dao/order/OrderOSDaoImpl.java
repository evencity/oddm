package com.apical.oddm.core.dao.order;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.apical.oddm.core.dao.basedao.BaseDaoImpl;
import com.apical.oddm.core.model.order.OrderOS;

@Repository("orderOSDao")
public class OrderOSDaoImpl extends BaseDaoImpl<OrderOS> implements OrderOSDaoI {

	@Override
	public OrderOS getOrderOsByGui(String gui) {
		String hql = "select t from OrderOS t where t.gui=? order by t.updatetime desc";
		return (OrderOS) this.queryObject(hql, gui);
	}

	@Override
	public OrderOS getOrderOsByOrderId(int orderId) {
		String hql = "select t from OrderOS t join t.orderOS o where o.id=?";
		return (OrderOS) this.queryObject(hql, orderId);
	}

	@Override
	public List<Object> listTop(OrderOS orderOS) {
		String sql = null;
		if (orderOS.getGui() == "") {
			sql = "select distinct(t.gui) from order_os t where t.gui is not null order by t.id desc";
			return this.listPageSql(sql, null, null, null, false);
		}
		if (orderOS.getLangOs() == "") {
			sql = "select distinct(t.lang_os) from order_os t where t.lang_os is not null order by t.id desc";
			return this.listPageSql(sql, null, null, null, false);
		}
		if (orderOS.getLangDefault() == "") {
			sql = "select distinct(t.lang_default) from order_os t where t.lang_default is not null order by t.id desc";
			return this.listPageSql(sql, null, null, null, false);
		}
		if (orderOS.getTimezone() == "") {
			sql = "select distinct(t.timezone) from order_os t where t.timezone is not null order by t.id desc";
			return this.listPageSql(sql, null, null, null, false);
		}
		if (org.apache.commons.lang.StringUtils.isNotBlank(orderOS.getGui())) {
			sql = "select distinct(t.gui) from order_os t where t.gui like ? order by t.id desc";
			return this.listPageSql(sql, "%"+orderOS.getGui()+"%", null, null, false);
		}
		if (org.apache.commons.lang.StringUtils.isNotBlank(orderOS.getLangOs())) {
			sql = "select distinct(t.lang_os) from order_os t where t.lang_os like ? order by t.id desc";
			return this.listPageSql(sql, "%"+orderOS.getLangOs()+"%", null, null, false);
		}
		if (org.apache.commons.lang.StringUtils.isNotBlank(orderOS.getLangDefault())) {
			sql = "select distinct(t.lang_default) from order_os t where t.lang_default like ? order by t.id desc";
			return this.listPageSql(sql, "%"+orderOS.getLangDefault()+"%", null, null, false);
		}
		if (org.apache.commons.lang.StringUtils.isNotBlank(orderOS.getTimezone())) {
			sql = "select distinct(t.timezone) from order_os t where t.timezone like ? order by t.id desc";
			return this.listPageSql(sql, "%"+orderOS.getTimezone()+"%", null, null, false);
		}
		return null;		
	}

}
