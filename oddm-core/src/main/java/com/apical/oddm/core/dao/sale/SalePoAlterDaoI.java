package com.apical.oddm.core.dao.sale;

import com.apical.oddm.core.dao.basedao.BaseDaoI;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.sale.SalePoAlt;

/**
 * 内部订单更改记录表dao操作接口
 * @author lgx
 * 2016-10-16
 */
public interface SalePoAlterDaoI extends BaseDaoI<SalePoAlt> {
	/**
	 * 分页获取
	 * @return 
	 */
	public Pager<SalePoAlt> dataGrid(int poId);
}
