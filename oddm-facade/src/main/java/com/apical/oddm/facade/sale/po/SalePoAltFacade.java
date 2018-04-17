package com.apical.oddm.facade.sale.po;

import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.sale.SalePo;
import com.apical.oddm.facade.sale.po.cmd.SalePoCmd;
import com.apical.oddm.facade.sale.po.dto.SalePoAltDto;

/**
 * 计算变更记录
 * @author lgx
 * 2016-1-16
 */
public interface SalePoAltFacade {

	public Pager<SalePoAltDto> dataGrid(Integer poId);

	/**
	 * 计算变更记录仪
	 * @param salePoBefore
	 * @param salePoCmd
	 * @param currUserId
	 */
	public void addEditRecord(SalePo salePoBefore,
			SalePoCmd salePoCmd, Integer currUserId);
	
	/**
	 * 增加内部订单未读记录
	 * @param currUserId 当前用户id
	 * @param poId 
	 */
	public void addSalePoUnreadInfo(int currUserId, int poId);
}
