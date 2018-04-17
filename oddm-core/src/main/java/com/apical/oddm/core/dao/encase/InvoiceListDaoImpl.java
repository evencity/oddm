package com.apical.oddm.core.dao.encase;

import org.springframework.stereotype.Repository;

import com.apical.oddm.core.dao.basedao.BaseDaoImpl;
import com.apical.oddm.core.model.encase.InvoiceList;

@Repository("invoiceListDao")
public class InvoiceListDaoImpl extends BaseDaoImpl<InvoiceList> implements InvoiceListDaoI {

}
