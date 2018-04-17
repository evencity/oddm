package com.apical.oddm.core.dao.produce;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.apical.oddm.core.dao.basedao.BaseDaoImpl;
import com.apical.oddm.core.model.produce.OrderPrototypeDetial;

@Repository("orderPrototypeDetialDao")
public class OrderPrototypeDetialDaoImpl extends BaseDaoImpl<OrderPrototypeDetial> implements OrderPrototypeDetialDaoI {

	@Override
	public List<OrderPrototypeDetial> getByProId(int proId) {
		String hql = "select t from OrderPrototypeDetial t where t.orderPrototype.id=?";
		return this.list(hql, proId);
	}

}
