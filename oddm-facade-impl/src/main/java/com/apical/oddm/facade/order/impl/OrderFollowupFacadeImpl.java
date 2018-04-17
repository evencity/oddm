package com.apical.oddm.facade.order.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apical.oddm.application.order.OrderFollowupServiceI;
import com.apical.oddm.application.order.OrderInfoServiceI;
import com.apical.oddm.application.sys.SysConfigServiceI;
import com.apical.oddm.core.constant.OrderFollowupConst;
import com.apical.oddm.core.model.order.OrderFollowup;
import com.apical.oddm.core.model.order.OrderInfo;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.page.SystemContext;
import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.base.dto.BasePage;
import com.apical.oddm.facade.order.OrderFollowupAlterFacade;
import com.apical.oddm.facade.order.OrderFollowupFacade;
import com.apical.oddm.facade.order.command.OrderFollowupCommand;
import com.apical.oddm.facade.order.command.OrderInfoCommand;
import com.apical.oddm.facade.order.dto.OrderFollowupDTO;
import com.apical.oddm.facade.order.dto.OrderInfoDTO;
import com.apical.oddm.facade.util.TimeUtil;

/**
 * @description 类的描述：
 * @author 作者 : zhuzh
 * @date 创建时间：2016年10月31日 下午5:51:25
 * @version 1.0
 */
@Component("orderFollowupFacade")
public class OrderFollowupFacadeImpl implements OrderFollowupFacade {

	@Autowired
	private OrderFollowupAlterFacade orderFollowupAlterFacade;

	@Autowired
	private OrderFollowupServiceI orderFollowupService;

	@Autowired
	private OrderInfoServiceI orderInfoService;

	@Autowired
	private SysConfigServiceI sysConfigService;

	@Override
	public BasePage<OrderFollowupDTO> dataGrid(OrderInfoCommand orderInfoCommand, PageCommand pageCommand) {
		// TODO Auto-generated method stub
		SystemContext.setPageOffset(pageCommand.getPage());
		SystemContext.setPageSize(pageCommand.getRows());
		SystemContext.setOrder(pageCommand.getOrder());
		SystemContext.setSort("t." + pageCommand.getSort());
		Pager<OrderFollowup> dataGrid = null;
		if (orderInfoCommand != null) {
			OrderInfo orderInfoQuery = new OrderInfo();
			/*orderInfoQuery.setOrderNo(orderInfoCommand.getOrderNo());
			orderInfoQuery.setClientName(orderInfoCommand.getClientName());
			orderInfoQuery.setProductClient(orderInfoCommand.getProductClient());
			orderInfoQuery.setMerchandiser(orderInfoCommand.getMerchandiser());*/
			BeanUtils.copyProperties(orderInfoCommand, orderInfoQuery);
			if (orderInfoCommand.getMerchandiserId() != null) {
				orderInfoQuery.setMerchandiserId(orderInfoCommand.getMerchandiserId());
			}
			if (orderInfoCommand.getSellerId() != null) {
				orderInfoQuery.setSellerId(orderInfoCommand.getSellerId());
			}
			dataGrid = orderFollowupService.dataGridByOrderInfo(orderInfoQuery, null);
		}
		BasePage<OrderFollowupDTO> basePage = new BasePage<OrderFollowupDTO>();
		List<OrderFollowupDTO> list = new ArrayList<OrderFollowupDTO>();
		if (dataGrid != null) {
			if (dataGrid.getTotal() > 0) {
				for (OrderFollowup orderFollowup : dataGrid.getRows()) {
					OrderFollowupDTO orderFollowupDTO = new OrderFollowupDTO();
					BeanUtils.copyProperties(orderFollowup, orderFollowupDTO);
					if (orderFollowup.getUpdatetime() != null) {
						orderFollowupDTO.setUpdatetime(TimeUtil.timestampToStrings(orderFollowup.getUpdatetime()));
					}
					if (orderFollowup.getDateClient() != null) {
						orderFollowupDTO.setDateClient(TimeUtil.dateToString(orderFollowup.getDateClient()));
					}
					if (orderFollowup.getDateFactory() != null) {
						orderFollowupDTO.setDateFactory(TimeUtil.dateToString(orderFollowup.getDateFactory()));
					}
					if (orderFollowup.getOrderInfo() != null) {
						OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
						BeanUtils.copyProperties(orderFollowup.getOrderInfo(), orderInfoDTO);
						orderFollowupDTO.setOrderInfoDTO(orderInfoDTO);
					}

					list.add(orderFollowupDTO);
				}
				basePage.setRows(list);
				basePage.setTotal(dataGrid.getTotal());
			}
		}
		return basePage;
	}

	@Override
	public OrderFollowupDTO get(Integer orderFollowupId) {
		OrderFollowupDTO orderFollowupDTO = new OrderFollowupDTO();
		if (orderFollowupId != null) {
			OrderFollowup orderFollowup = orderFollowupService.get(orderFollowupId);
			if (orderFollowup != null) {
				BeanUtils.copyProperties(orderFollowup, orderFollowupDTO);
				if (orderFollowup.getDateClient() != null) {
					orderFollowupDTO.setDateClient(TimeUtil.dateToString(orderFollowup.getDateClient()));
				}
				if (orderFollowup.getDateFactory() != null) {
					orderFollowupDTO.setDateFactory(TimeUtil.dateToString(orderFollowup.getDateFactory()));
				}
				//System.out.println(orderFollowup.getDateClient()+"::"+orderFollowup.getDateFactory()+"::"+orderFollowupDTO.getDateFactory()+"::"+orderFollowup.getDateClient());
				if (orderFollowup.getOrderInfo() != null) {
					OrderInfo orderInfo = orderFollowup.getOrderInfo();
					OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
					BeanUtils.copyProperties(orderInfo, orderInfoDTO);
					orderFollowupDTO.setOrderInfoDTO(orderInfoDTO);
				}
			}
			return orderFollowupDTO;
		}
		return orderFollowupDTO;
	}

	@Override
	public Boolean add(OrderFollowupCommand orderFollowupCommand) {
		// TODO Auto-generated method stub
		if (orderFollowupCommand != null) {
			if (orderFollowupCommand.getOrderId() != null) {
				OrderInfo orderInfo = new OrderInfo(orderFollowupCommand.getOrderId());
				OrderFollowup orderFollowup = new OrderFollowup();
				BeanUtils.copyProperties(orderFollowupCommand, orderFollowup);
				orderFollowup.setOrderInfo(orderInfo);
				orderFollowup.setState(OrderFollowupConst.save);
				Serializable add = orderFollowupService.add(orderFollowup);
				if (add != null) {
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}

	@Override
	public void edit(OrderFollowupCommand orderFollowupCommand, Integer currUserId) {
		// TODO Auto-generated method stub
		if (orderFollowupCommand != null && currUserId != null) {
			if (orderFollowupCommand != null) {
				OrderFollowup orderFollowup = orderFollowupService.get(orderFollowupCommand.getId());

				// 变更记录，一天后才产生变更记录
				OrderFollowup orderFollowup1 = null;
				if (orderFollowup.getState() > OrderFollowupConst.temporarysave) {// 暂存状态不需要计算变更记录
					// orderFollowup1 = orderFollowupService.get(orderFollowupCommand.getId());
					orderFollowup1 = orderFollowupService.get(orderFollowupCommand.getId());
				}
				BeanUtils.copyProperties(orderFollowupCommand, orderFollowup);
				if (orderFollowupCommand.getDateClient() != null) {
					orderFollowup.setDateClient(TimeUtil.datToDate(orderFollowupCommand.getDateClient()));
				}
				if (orderFollowupCommand.getDateFactory() != null) {
					orderFollowup.setDateFactory(TimeUtil.datToDate(orderFollowupCommand.getDateFactory()));
				}
				orderFollowupService.edit(orderFollowup);
				// 计算变更记录放在最后
				orderFollowupAlterFacade.addEditRecord(orderFollowup1, orderFollowupCommand, currUserId);
			}
		}

	}

	@SuppressWarnings("deprecation")
	@Override
	public void delete(Integer orderFollowupId) {
		// TODO Auto-generated method stub
		if(orderFollowupId != null){
			orderFollowupService.delete(orderFollowupId);
		}
	}

	@Override
	public OrderFollowupDTO getByOrderInfo(OrderInfoCommand orderInfoCommand) {
		// TODO Auto-generated method stub
		if (orderInfoCommand != null) {
			OrderInfo orderInfoquery = new OrderInfo();
			if (orderInfoCommand.getId() != null) {
				orderInfoquery.setId(orderInfoCommand.getId());
			}
			if (orderInfoCommand.getOrderNo() != null || !"".equals(orderInfoCommand.getOrderNo())) {
				orderInfoquery.setOrderNo(orderInfoCommand.getOrderNo());
			}
			OrderFollowup byOrderInfo = orderFollowupService.getByOrderInfo(orderInfoquery);
			OrderFollowupDTO orderFollowupDTO = new OrderFollowupDTO();
			if (byOrderInfo != null) {
				BeanUtils.copyProperties(byOrderInfo, orderFollowupDTO);
				if (byOrderInfo.getDateClient() != null) {
					orderFollowupDTO.setDateClient(TimeUtil.dateToString(byOrderInfo.getDateClient()));
				}
				if (byOrderInfo.getDateFactory() != null) {
					orderFollowupDTO.setDateFactory(TimeUtil.dateToString(byOrderInfo.getDateFactory()));
				}
				if (byOrderInfo.getOrderInfo() != null) {
					OrderInfo orderInfo = byOrderInfo.getOrderInfo();
					OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
					BeanUtils.copyProperties(orderInfo, orderInfoDTO);
					orderFollowupDTO.setOrderInfoDTO(orderInfoDTO);
				}
			}
			return orderFollowupDTO;

		}
		return null;
	}

	@Override
	public Boolean getByOrderNo(String orderNo) {
		// TODO Auto-generated method stub
		if (orderNo != null && !"".equals(orderNo.trim())) {
			OrderInfo orderInfoQuery = new OrderInfo();
			orderInfoQuery.setOrderNo(orderNo);
			OrderInfo orderInfo = orderInfoService.getByOrder(orderInfoQuery);
			if (orderInfo != null) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	@Override
	public Boolean getByOrder(OrderInfoCommand orderInfoCommand) {
		// TODO Auto-generated method stub
		if (orderInfoCommand != null && orderInfoCommand.getOrderNo() != null) {
			OrderInfo orderInfoQuery = new OrderInfo();
			orderInfoQuery.setOrderNo(orderInfoCommand.getOrderNo());
			if (orderInfoCommand.getMerchandiserId() != null) {
				orderInfoQuery.setMerchandiserId(orderInfoCommand.getMerchandiserId());
			}
			if(orderInfoCommand.getSellerId() != null){
				orderInfoQuery.setSellerId(orderInfoCommand.getSellerId());
			}
			OrderInfo orderInfo = orderInfoService.getByOrder(orderInfoQuery);
			if (orderInfo != null) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	@Override
	public Boolean getByOrderInfo(String orderNo) {
		// TODO Auto-generated method stub
		if (orderNo != null && !"".equals(orderNo.trim())) {
			OrderInfo orderInfo = new OrderInfo();
			orderInfo.setOrderNo(orderNo);
			OrderFollowup orderFollowup = orderFollowupService.getByOrderInfo(orderInfo);
			if (orderFollowup != null) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	@Override
	public OrderInfoDTO getOrderInfoByOrderNo(String orderNo) {
		// TODO Auto-generated method stub
		if (orderNo != null && !"".equals(orderNo.trim())) {
			OrderInfo orderInfoQuery = new OrderInfo();
			orderInfoQuery.setOrderNo(orderNo);
			OrderInfo orderInfo = orderInfoService.getByOrder(orderInfoQuery);
			if (orderInfo != null) {
				OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
				BeanUtils.copyProperties(orderInfo, orderInfoDTO);
				return orderInfoDTO;
			}
		}
		return null;
	}

	@Override
	public void editForState(OrderFollowupCommand orderFollowupCommand) {
		// TODO Auto-generated method stub
		OrderFollowup orderFollowup = orderFollowupService.get(orderFollowupCommand.getId());
		if (orderFollowupCommand != null) {
			BeanUtils.copyProperties(orderFollowupCommand, orderFollowup);

			if (orderFollowupCommand.getDateClient() != null) {
				orderFollowup.setDateClient(TimeUtil.datToDate(orderFollowupCommand.getDateClient()));
			}
			if (orderFollowupCommand.getDateFactory() != null) {
				orderFollowup.setDateFactory(TimeUtil.datToDate(orderFollowupCommand.getDateFactory()));
			}
			orderFollowup.setState(orderFollowupCommand.getState());
			orderFollowupService.edit(orderFollowup);
		}
	}

	@Override
	public List<OrderFollowupDTO> listAll(OrderInfoCommand orderInfoCommand, Set<Integer> states) {

		OrderInfo orderInfoQuery = new OrderInfo();
		if (orderInfoCommand != null) {
			BeanUtils.copyProperties(orderInfoCommand, orderInfoQuery);
			if(orderInfoCommand.getDateOrderStart() != null){
				orderInfoQuery.setDateOrderStart(TimeUtil.stringToDate(orderInfoCommand.getDateOrderStart()));
			}
			if(orderInfoCommand.getDateOrderEnd() != null){
				orderInfoQuery.setDateOrderEnd(TimeUtil.stringToDate(orderInfoCommand.getDateOrderEnd()));
			}
		}
		//System.out.println(orderInfoQuery.getClientName()+"...."+orderInfoQuery.getMerchandiser()+"..."+orderInfoQuery.getSeller()+".."+orderInfoQuery.getDateOrderStart()+orderInfoQuery.getDateOrderEnd());
		List<OrderFollowup> listAll = orderFollowupService.listAll(orderInfoQuery, states);

		List<OrderFollowupDTO> orderFollowupDTOs = new ArrayList<OrderFollowupDTO>();
		if (listAll != null && listAll.size() > 0) {
			for (OrderFollowup orderFollowup : listAll) {
				OrderFollowupDTO orderFollowupDTO = new OrderFollowupDTO();
				BeanUtils.copyProperties(orderFollowup, orderFollowupDTO);
				if (orderFollowup.getUpdatetime() != null) {
					orderFollowupDTO.setUpdatetime(TimeUtil.timestampToStrings(orderFollowup.getUpdatetime()));
				}
				if (orderFollowup.getDateClient() != null) {
					orderFollowupDTO.setDateClient(TimeUtil.dateToStringOrderFollowup(orderFollowup.getDateClient()));
				}
				if (orderFollowup.getDateFactory() != null) {
					orderFollowupDTO.setDateFactory(TimeUtil.dateToStringOrderFollowup(orderFollowup.getDateFactory()));
				}
				//System.out.println("..............."+orderFollowup.getShipmentDatelast());
				if (orderFollowup.getOrderInfo() != null) {
					OrderInfo orderInfo = orderFollowup.getOrderInfo();
					OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
					BeanUtils.copyProperties(orderInfo, orderInfoDTO);

					orderFollowupDTO.setOrderInfoDTO(orderInfoDTO);
				}
				orderFollowupDTOs.add(orderFollowupDTO);
			}
		}
		return orderFollowupDTOs;

	}

}
