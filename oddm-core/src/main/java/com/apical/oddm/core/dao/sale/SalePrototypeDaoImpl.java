package com.apical.oddm.core.dao.sale;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.apical.oddm.core.dao.basedao.BaseDaoImpl;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.page.SystemContext;
import com.apical.oddm.core.model.sale.SalePrototype;

@Repository("salePrototypeDao")
public class SalePrototypeDaoImpl extends BaseDaoImpl<SalePrototype> implements SalePrototypeDaoI {

	@Override
	public Pager<SalePrototype> dataGrid() {
		String hql = "select t from SalePrototype t order by t.updatetime desc";
		return this.find(hql);
	}

	private String getHql(SalePrototype salePrototype, Map<String, Object> params ) {
		String hql = "select t from SalePrototype t where 1=1";
		if (salePrototype != null) {
			if (StringUtils.isNotBlank(salePrototype.getClientName())) {
				hql +=" and t.clientName like :clientName";
				params.put("clientName", "%"+salePrototype.getClientName()+"%");
			}
			if (StringUtils.isNotBlank(salePrototype.getDistrict())) {
				hql +=" and t.district like :district";
				params.put("district", "%"+salePrototype.getDistrict()+"%");
			}
			if (StringUtils.isNotBlank(salePrototype.getProductName())) {
				hql +=" and t.productName like :productName";
				params.put("productName", "%"+salePrototype.getProductName()+"%");
			}
			if (salePrototype.getDateSendStart() != null  && salePrototype.getDateSendEnd() != null) {
				hql +=" and t.dateSend>=:startDate and t.dateSend<=:endDate";
				params.put("startDate", salePrototype.getDateSendStart());
				params.put("endDate", salePrototype.getDateSendEnd());
			} else {
				if (salePrototype.getDateSendStart() != null) {
					hql +=" and t.dateSend>=:startDate";
					params.put("startDate", salePrototype.getDateSendStart());
				}
				if (salePrototype.getDateSendEnd() != null) {
					hql +=" and t.dateSend<=:endDate";
					params.put("endDate", salePrototype.getDateSendEnd());
				}
			}
		}
		hql += " order by t.uploadtime desc";
		return hql;
	}

	@Override
	public Pager<SalePrototype> dataGridByDateSend(Date startDate, Date endDate) {
		String hql = "select t from SalePrototype t where t.dateSend>=:startDate and t.dateSend<=:endDate order by t.dateSend desc";
		Map<String, Object> params = new HashMap<String, Object>(2);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		return this.findByAlias(hql, params);
	}

	@Override
	public Pager<SalePrototype> dataGrid(SalePrototype salePrototype) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = getHql(salePrototype, params);
		return this.findByAlias(hql, params);
	}

	@Override
	public List<SalePrototype> list(SalePrototype salePrototype) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = getHql(salePrototype, params);
		SystemContext.setSort("t.dateSend");
		SystemContext.setOrder("asc");
		return this.listByAlias(hql, params);
	}

}
