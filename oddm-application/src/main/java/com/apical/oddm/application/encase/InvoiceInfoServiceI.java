package com.apical.oddm.application.encase;

import com.apical.oddm.application.base.BaseServiceI;
import com.apical.oddm.core.model.encase.InvoiceInfo;
import com.apical.oddm.core.model.page.Pager;


/**
 * 发票信息表操作接口
 * @author lgx
 * 2016-10-16
 */
public interface InvoiceInfoServiceI extends BaseServiceI<InvoiceInfo> {

	/**
	 * 获取分页数组结果集
	 * @param invoiceInfo.setName()
	 * @param invoiceInfo.setPiNo()
	 * @param invoiceInfo.setBrand()
	 * @param invoiceInfo.setOrigion()
	 * @param invoiceInfo.setDateInvoiceStart() invoiceInfo.setDateInvoiceEnd(),根据装箱日期起止查询
	 * @return
	 */
	public Pager<InvoiceInfo> dataGrid(InvoiceInfo invoiceInfo);
	
	/**
	 * 通过发票信息id返回详细信息
	 * @param invoiceInfoId
	 * @return 发票信息和列表
	 */
	public InvoiceInfo getDetail(int invoiceInfoId);
	
	/**
	 * 返回最近的一条记录
	 * @return 只返会发票信息不含列表
	 */
	public InvoiceInfo getLatestRow();
}
