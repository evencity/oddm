package com.apical.oddm.core.dao.sale;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.apical.oddm.core.dao.basedao.BaseDaoImpl;
import com.apical.oddm.core.model.sale.SalePoList;

@Repository("salePoListDao")
public class SalePoListDaoImpl extends BaseDaoImpl<SalePoList> implements SalePoListDaoI {

	@Override
	public List<SalePoList> getByPoId(int poId) {
		String hql = "select t from SalePoList t where t.salePo.id=?";
		return this.list(hql, poId);
	}

}
