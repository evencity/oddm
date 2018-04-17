package com.apical.oddm.core.dao.encase;

import com.apical.oddm.core.dao.basedao.BaseDaoI;
import com.apical.oddm.core.model.encase.InvoiceInfo;
import com.apical.oddm.core.model.page.Pager;

/**
 * 发票信息表dao操作接口
 * @author lgx
 * 2016-10-16
 */
public interface InvoiceInfoDaoI extends BaseDaoI<InvoiceInfo> {

	/**
	 * 获取分页数组结果集
	 * @return 返回的page.getRows()为一个Object[]数组（不含InvoiceList），此数组为{InvoiceInfo,产品总数,总额}
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
