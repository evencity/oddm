package com.apical.oddm.core.dao.encase;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.apical.oddm.core.dao.basedao.BaseDaoImpl;
import com.apical.oddm.core.model.encase.InvoiceInfo;
import com.apical.oddm.core.model.page.Pager;

@Repository("invoiceInfoDao")
public class InvoiceInfoDaoImpl extends BaseDaoImpl<InvoiceInfo> implements InvoiceInfoDaoI {

	@Override
	public Pager<InvoiceInfo> dataGrid(InvoiceInfo invoiceInfo) {
		Map<String, Object> params = new HashMap<String, Object>(2);
		String sql = "select t.id, t.name,"
				+ " t.company_name as companyName,"
				+ " t.company_addr as companyAddr,"
				+ " t.tel, t.to_, t.address, t.fax,"
				+ " t.date_invoice as dateInvoice, t.pi_no as piNo, t.currency,"
				+ " t.timestamp, t.updatetime,"
				+ " t.brand, t.shipping_method as shippingMethod,"
				+ " t.incoterms, t.origion, t.payment,"
				+ " sum(e.qty) as qtys,"
				+ " sum(e.qty * e.unit_price) as amount"
				+ " from invoice_info t"
				+ " left join invoice_list e on t.id = e.invoiceid"
				+ " where 1=1";
		if (invoiceInfo != null) {
			if (StringUtils.isNotBlank(invoiceInfo.getName())) {
				sql +=" and t.name like :name";
				params.put("name", "%"+invoiceInfo.getName()+"%");
			}
			if (StringUtils.isNotBlank(invoiceInfo.getPiNo())) {
				sql +=" and t.pi_no like :pi_no";
				params.put("pi_no", "%"+invoiceInfo.getPiNo()+"%");
			}
			if (StringUtils.isNotBlank(invoiceInfo.getBrand())) {
				sql +=" and t.brand like :brand";
				params.put("brand", "%"+invoiceInfo.getBrand()+"%");
			}
			if (StringUtils.isNotBlank(invoiceInfo.getOrigion())) {
				sql +=" and t.origion like :origion";
				params.put("origion", "%"+invoiceInfo.getOrigion()+"%");
			}
			if (invoiceInfo.getDateInvoiceStart() != null  && invoiceInfo.getDateInvoiceEnd() != null) {
				sql +=" and t.date_invoice>=:startDate and t.date_invoice<=:endDate";
				params.put("startDate", invoiceInfo.getDateInvoiceStart());
				params.put("endDate", invoiceInfo.getDateInvoiceEnd());
			} else {
				if (invoiceInfo.getDateInvoiceStart() != null) {
					sql +=" and t.date_invoice>=:startDate";
					params.put("startDate", invoiceInfo.getDateInvoiceStart());
				}
				if (invoiceInfo.getDateInvoiceEnd() != null) {
					sql +=" and t.date_invoice<=:endDate";
					params.put("endDate", invoiceInfo.getDateInvoiceEnd());
				}
			}
		}
		sql += " group by t.id order by t.id desc";
		return this.findByAliasSql(sql, params, InvoiceInfo.class, false);
	}
	
	@Override
	public InvoiceInfo getDetail(int invoiceInfoId) {
		String hql = "select t from InvoiceInfo t left join fetch t.invoiceLists e where t.id=?";
		return (InvoiceInfo) this.queryObject(hql, invoiceInfoId);
	}

	@Override
	public InvoiceInfo getLatestRow() {
		String hql = "select t from InvoiceInfo t order by t.id desc";
		return (InvoiceInfo) this.queryObject(hql);
	}

}
