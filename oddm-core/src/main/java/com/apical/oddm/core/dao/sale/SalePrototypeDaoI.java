package com.apical.oddm.core.dao.sale;

import java.util.Date;
import java.util.List;

import com.apical.oddm.core.dao.basedao.BaseDaoI;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.sale.SalePrototype;

/**
 * 样机汇总表dao操作接口
 * @author lgx
 * 2016-11-1
 */
public interface SalePrototypeDaoI extends BaseDaoI<SalePrototype> {

	/**
	 * 分页获取样机汇总列表
	 * @return 
	 */
	public Pager<SalePrototype> dataGrid();
	
	/**
	 * 分页获取样机汇总列表
	 * @param salePrototype
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
