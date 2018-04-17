package com.apical.oddm.core.dao.order;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.apical.oddm.core.dao.basedao.BaseDaoImpl;
import com.apical.oddm.core.model.order.OrderHardware;

@Repository("orderHardwareDao")
public class OrderHardwareDaoImpl extends BaseDaoImpl<OrderHardware> implements OrderHardwareDaoI {

	@Override
	public List<OrderHardware> dataGrid(int orderId) {
		String hql = "select t from OrderHardware t join t.orderInfo o where o.id=?";
		return this.list(hql, orderId);
	}

}
