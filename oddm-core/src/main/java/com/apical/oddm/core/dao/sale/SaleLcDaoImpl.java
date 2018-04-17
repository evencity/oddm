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
import com.apical.oddm.core.model.sale.SaleLc;

@Repository("saleLcDao")
public class SaleLcDaoImpl extends BaseDaoImpl<SaleLc> implements SaleLcDaoI {

	@Override
	public Pager<SaleLc> dataGrid() {
		String hql = "select t from SaleLc t order by t.dateDelivery desc";
		return this.find(hql);
	}

	private String getHql(SaleLc saleLc, Map<String, Object> params ) {
		String hql = "select t from SaleLc t where 1=1";
		if (saleLc != null) {
			if (StringUtils.isNotBlank(saleLc.getInvoiceNo())) {
				hql +=" and t.invoiceNo like :invoiceNo";
				params.put("invoiceNo", "%"+saleLc.getInvoiceNo()+"%");
			}
			if (StringUtils.isNotBlank(saleLc.getCreditNo())) {
				hql +=" and t.creditNo like :creditNo";
				params.put("creditNo", "%"+saleLc.getCreditNo()+"%");
			}
			if (StringUtils.isNotBlank(saleLc.getClientName())) {
				hql +=" and t.clientName like :clientName";
				params.put("clientName", "%"+saleLc.getClientName()+"%");
			}
			if (saleLc.getDateDeliveryStart() != null  && saleLc.getDateDeliveryEnd() != null) {
				hql +=" and t.dateDelivery>=:startDate and t.dateDelivery<=:endDate";
				params.put("startDate", saleLc.getDateDeliveryStart());
				params.put("endDate", saleLc.getDateDeliveryEnd());
			} else {
				if (saleLc.getDateDeliveryStart() != null) {
					hql +=" and t.dateDelivery>=:startDate";
					params.put("startDate", saleLc.getDateDeliveryStart());
				}
				if (saleLc.getDateDeliveryEnd() != null) {
					hql +=" and t.dateDelivery<=:endDate";
					params.put("endDate", saleLc.getDateDeliveryEnd());
				}
			}
		}
		hql += " order by t.dateDelivery desc";
		return hql;
	}

	@Override
	public Pager<SaleLc> dataGridByDateOrder(Date startDate, Date endDate) {
		String hql = "select t from SaleLc t where t.dateDelivery>=:startDate and t.dateDelivery<=:endDate order by t.dateDelivery desc";
		Map<String, Object> params = new HashMap<String, Object>(2);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		return this.findByAlias(hql, params);
	}

	@Override
	public Pager<SaleLc> dataGrid(SaleLc saleLc) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = getHql(saleLc, params);
		return this.findByAlias(hql, params);
	}

	@Override
	public List<SaleLc> list(SaleLc saleLc) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = getHql(saleLc, params);
		SystemContext.setSort("t.dateDelivery");
		SystemContext.setOrder("asc");
		return this.listByAlias(hql, params);
	}
}
