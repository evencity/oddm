package com.apical.oddm.application.encase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apical.oddm.application.base.BaseServiceImpl;
import com.apical.oddm.core.dao.encase.InvoiceInfoDaoI;
import com.apical.oddm.core.model.encase.InvoiceInfo;
import com.apical.oddm.core.model.page.Pager;

@Service("invoiceInfoService")
public class InvoiceInfoServiceImpl extends BaseServiceImpl<InvoiceInfo> implements InvoiceInfoServiceI {

	@Autowired
	private InvoiceInfoDaoI invoiceInfoDao;
	
	@Override
	public Pager<InvoiceInfo> dataGrid(InvoiceInfo invoiceInfo) {
		return invoiceInfoDao.dataGrid(invoiceInfo);
	}

	@Override
	public InvoiceInfo getDetail(int invoiceInfoId) {
		return invoiceInfoDao.getDetail(invoiceInfoId);
	}

	@Override
	public InvoiceInfo getLatestRow() {
		return invoiceInfoDao.getLatestRow();
	}

}
