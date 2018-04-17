package com.apical.oddm.facade.sale.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apical.oddm.application.order.OrderInfoServiceI;
import com.apical.oddm.application.sale.SaleOutServiceI;
import com.apical.oddm.core.model.order.OrderInfo;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.sale.SaleOut;
import com.apical.oddm.facade.base.dto.BasePage;
import com.apical.oddm.facade.order.command.OrderInfoCommand;
import com.apical.oddm.facade.order.dto.OrderInfoDTO;
import com.apical.oddm.facade.sale.out.SaleOutDTO;
import com.apical.oddm.facade.sale.out.SaleOutFacadeI;
import com.apical.oddm.facade.util.TimeUtil;

@Component("saleOutFacade")
public class SaleOutFacadeImpl implements SaleOutFacadeI {

	@Autowired
	private SaleOutServiceI saleOutService;
	@Autowired
	private OrderInfoServiceI orderInfoService;
	
	@Override
	public Pager<SaleOutDTO> dataGrid(SaleOutDTO saleOutCmd) {
		SaleOut saleOutQuery = new SaleOut();
		if(saleOutCmd != null ){
			BeanUtils.copyProperties(saleOutCmd, saleOutQuery);
		}
		Pager<SaleOut> dataGrid = saleOutService.dataGrid(saleOutQuery);
		Pager<SaleOutDTO> pager = new Pager<SaleOutDTO>();
		if(dataGrid != null && dataGrid.getSize() > 0 && dataGrid.getRows() != null){
			List<SaleOutDTO> list = new ArrayList<SaleOutDTO>();
			for(SaleOut saleOut : dataGrid.getRows()){
				SaleOutDTO saleOutDTO = new SaleOutDTO();
				BeanUtils.copyProperties(saleOutDTO, saleOut);
				list.add(saleOutDTO);
			}
			pager.setRows(list);
			pager.setTotal(dataGrid.getTotal());
		}
		return pager;
	}

	@Override
	public List<SaleOutDTO> list(SaleOutDTO saleOutDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BasePage<OrderInfoDTO> dataGridForState(OrderInfoCommand orderInfoCommand,Set<Integer> states,Boolean hasRoles) {

		Pager<OrderInfo> dataGrid = null;
		OrderInfo orderInfoquery = new OrderInfo();
		orderInfoquery.setUnread(orderInfoCommand.getIsRead());
		BeanUtils.copyProperties(orderInfoCommand, orderInfoquery);
		dataGrid = orderInfoService.dataGrid(orderInfoquery, states);
		BasePage<OrderInfoDTO> basePage = new BasePage<OrderInfoDTO>();
		if (dataGrid.getTotal() != 0) {
			if (dataGrid.getRows().size() > 0) {
				List<OrderInfoDTO> list = new ArrayList<OrderInfoDTO>();
				if (hasRoles != null && hasRoles) {
					for (OrderInfo orderInfo : dataGrid.getRows()) {
						OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
						BeanUtils.copyProperties(orderInfo, orderInfoDTO);
						orderInfoDTO.setIsRead(orderInfo.getUnread());
						orderInfoDTO.setClientNameCode(orderInfo.getClientName());
						orderInfoDTO.setClientName("");

						if (orderInfo.getDateDelivery() != null) {
							orderInfoDTO.setDateDelivery(TimeUtil.dateToString(orderInfo.getDateDelivery()));
						}
						if (orderInfo.getDateExamine() != null) {
							orderInfoDTO.setDateExamine(TimeUtil.dateToString(orderInfo.getDateExamine()));
						}
						if (orderInfo.getDateOrder() != null) {
							orderInfoDTO.setDateOrder(TimeUtil.dateToString(orderInfo.getDateOrder()));
						}
						if (orderInfo.getUpdatetime() != null) {
							orderInfoDTO.setUpdatetime(TimeUtil.timestampToStringDetaile(orderInfo.getUpdatetime()));
						}

						// orderInfoDTO.setTimestamp(TimeUtil.timestampToStringDetaile(orderInfo.getTimestamp()));

						list.add(orderInfoDTO);
					}
				} else {
					for (OrderInfo orderInfo : dataGrid.getRows()) {
						OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
						BeanUtils.copyProperties(orderInfo, orderInfoDTO);
						orderInfoDTO.setIsRead(orderInfo.getUnread());
						orderInfoDTO.setClientName("");
						if (orderInfo.getDateDelivery() != null) {
							orderInfoDTO.setDateDelivery(TimeUtil.dateToString(orderInfo.getDateDelivery()));
						}
						if (orderInfo.getDateExamine() != null) {
							orderInfoDTO.setDateExamine(TimeUtil.dateToString(orderInfo.getDateExamine()));
						}
						if (orderInfo.getDateOrder() != null) {
							orderInfoDTO.setDateOrder(TimeUtil.dateToString(orderInfo.getDateOrder()));
						}
						if (orderInfo.getUpdatetime() != null) {
							orderInfoDTO.setUpdatetime(TimeUtil.timestampToStringDetaile(orderInfo.getUpdatetime()));
						}
						list.add(orderInfoDTO);
					}
				}

				basePage.setTotal(dataGrid.getTotal());
				basePage.setRows(list);
			}
		}
		return basePage;
	
	}
}
