package com.apical.oddm.application.sale;

import java.util.Date;
import java.util.List;

import com.apical.oddm.application.base.BaseServiceI;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.sale.SalePrototype;


/**
 * 样机汇总表操作接口
 * @author lgx
 * 2016-11-1
 */
public interface SalePrototypeServiceI extends BaseServiceI<SalePrototype> {
	/**
	 * 分页获取样机汇总列表
	 * @return 
	 */
	public Pager<SalePrototype> dataGrid();
	
	/**
	 * 分页获取（通过下面参数）样机汇总列表
	 * @param salePrototype.setClientName() 客户名称
	 * @param salePrototype.setProductName() 产品名称
	 * @return 
	 */
	public Pager<SalePrototype> dataGrid(SalePrototype salePrototype);
	
	/**
	 * 通过送样日期分页获取样机汇总列表
	 * @param startDate
	 * @param endDate
	 * @return 
	 */
	public Pager<SalePrototype> dataGridByDateSend(Date startDate, Date endDate);

	public List<SalePrototype> list(SalePrototype saleProto);
}
