package com.apical.oddm.application.sale;

import com.apical.oddm.application.base.BaseServiceI;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.sale.SalePoAlt;


/**
 * 内部订单变更记录表操作接口
 * @author lgx
 * 2016-10-16
 */
public interface SalePoAlterServiceI extends BaseServiceI<SalePoAlt> {

	/**
	 * 分页获取
	 * @param poId
	 * @return 
	 */
	public Pager<SalePoAlt> dataGrid(int poId);
	
}
