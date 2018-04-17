package com.apical.oddm.core.dao.produce;

import org.springframework.stereotype.Repository;

import com.apical.oddm.core.dao.basedao.BaseDaoImpl;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.produce.OrderRecordType;

@Repository("orderRecordTypeDao")
public class OrderRecordTypeDaoImpl extends BaseDaoImpl<OrderRecordType> implements OrderRecordTypeDaoI {

	@Override
	public Pager<OrderRecordType> dataGrid() {
		String hql = "select t from OrderRecordType t";
		return this.find(hql);
	}

	@Override
	public OrderRecordType getByName(String name) {
		String hql = "select t from OrderRecordType t where t.name=?";
		return (OrderRecordType) this.queryObject(hql, name);
	}

}
