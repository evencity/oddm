package com.apical.oddm.facade.encase.facade;

import java.text.ParseException;

import com.apical.oddm.core.model.encase.InvoiceInfo;
import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.base.dto.BasePage;
import com.apical.oddm.facade.encase.dto.InvoiceInfoDTO;
import com.apical.oddm.facade.order.command.OrderInfoCommand;
import com.apical.oddm.facade.order.dto.OrderInfoDTO;

public interface InvoiceFacade {

	/**
	 * 分页获取
	 * @return 
	 */
	public BasePage<InvoiceInfoDTO> dataGrid(InvoiceInfoDTO invoiceInfoDTO,PageCommand pageCommand) throws ParseException;

	public InvoiceInfoDTO getInvoiceInfoById(Integer id);
	
	public InvoiceInfo getInvoiceById(Integer id);
	public InvoiceInfo get(Integer id);

	public void edit(InvoiceInfoDTO invoice);

	public Integer add(InvoiceInfoDTO invoice);

	public void detele(Integer id);
	
	/**
	 * 通过订单号或者订单信息
	 * 
	 * @param orderInfo.setOrderNo() -- 订单号
	 * @param orderInfo.setMerchandiserId(1) -- 所属跟单id
	 * @param orderInfo.setSellerId(1) -- 所属业务id （特别注意 所属跟单id 和 所属业务id 是或的关系）
	 * @return 订单主体信息，慢加载
	 */
	public OrderInfoDTO getByOrderDTO(OrderInfoCommand orderInfoCommand);
	
}
