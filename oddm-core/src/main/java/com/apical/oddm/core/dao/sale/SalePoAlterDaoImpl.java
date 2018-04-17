package com.apical.oddm.core.dao.sale;

import org.springframework.stereotype.Repository;

import com.apical.oddm.core.dao.basedao.BaseDaoImpl;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.sale.SalePoAlt;

@Repository("salePoAlterDao")
public class SalePoAlterDaoImpl extends BaseDaoImpl<SalePoAlt> implements SalePoAlterDaoI {

	@Override
	public Pager<SalePoAlt> dataGrid(int poId) {
		String hql = "select t from SalePoAlt t join t.salePo o where o.id=? order by t.timestamp desc";
		return this.find(hql, poId);
	}

}
