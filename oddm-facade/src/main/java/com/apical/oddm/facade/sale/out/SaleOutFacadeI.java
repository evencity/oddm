package com.apical.oddm.facade.sale.out;

import java.util.List;
import java.util.Set;

import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.facade.base.dto.BasePage;
import com.apical.oddm.facade.order.command.OrderInfoCommand;
import com.apical.oddm.facade.order.dto.OrderInfoDTO;
/**
 * 出货汇总面层接口
 * @author lgx
 * 2016-12-30
 */
public interface SaleOutFacadeI {

	/**
	 * 获取分页列表
	 * @param saleOutDto
	 * @return
	 */
	public Pager<SaleOutDTO> dataGrid(SaleOutDTO saleOutDTO);

	public List<SaleOutDTO> list(SaleOutDTO saleOutDTO);

	public BasePage<OrderInfoDTO>  dataGridForState(OrderInfoCommand orderInfoCommand,Set<Integer> states, Boolean hasRoles);
}
