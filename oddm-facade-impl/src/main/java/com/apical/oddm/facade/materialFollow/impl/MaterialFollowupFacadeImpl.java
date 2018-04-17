package com.apical.oddm.facade.materialFollow.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apical.oddm.application.order.MaterialFollowupServiceI;
import com.apical.oddm.application.order.OrderInfoServiceI;
import com.apical.oddm.core.constant.MaterialFollowupConst;
import com.apical.oddm.core.model.order.MaterialFollowup;
import com.apical.oddm.core.model.order.OrderInfo;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.page.SystemContext;
import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.base.dto.BasePage;
import com.apical.oddm.facade.materialFollow.MaterialFollowupAltFacade;
import com.apical.oddm.facade.materialFollow.MaterialFollowupFacade;
import com.apical.oddm.facade.materialFollow.command.MaterialFollowupCommand;
import com.apical.oddm.facade.materialFollow.dto.MaterialFollowupDTO;
import com.apical.oddm.facade.order.command.OrderInfoCommand;
import com.apical.oddm.facade.order.dto.OrderInfoDTO;
import com.apical.oddm.facade.util.TimeUtil;



/**
 * @author zzh
 * 2016-10-16
 */
@Component("materialFollowupFacade")
public class MaterialFollowupFacadeImpl implements MaterialFollowupFacade{

	@Autowired
	private MaterialFollowupServiceI materialFollowupService;
	@Autowired
	private OrderInfoServiceI orderInfoService;
	
	@Autowired
	private MaterialFollowupAltFacade materialFollowupAltFacade;
	
	@Override
	public BasePage<MaterialFollowupDTO> dataGrid(PageCommand pageCommand) {
		// TODO Auto-generated method stub
		SystemContext.setPageOffset(pageCommand.getPage());
		SystemContext.setPageSize(pageCommand.getRows());
		SystemContext.setOrder(pageCommand.getOrder());
		SystemContext.setSort("t."+pageCommand.getSort());
		Pager<MaterialFollowup> dataGrid = materialFollowupService.dataGrid();
		BasePage<MaterialFollowupDTO> basePage = new BasePage<MaterialFollowupDTO>();
		SystemContext.setPageOffset(null);
		SystemContext.setPageSize(null);
		if(dataGrid != null){
			if(dataGrid.getRows().size() > 0){
				List<MaterialFollowupDTO> list = new ArrayList<MaterialFollowupDTO>();
				for(MaterialFollowup materialFollowup : dataGrid.getRows()){
					MaterialFollowupDTO materialFollowupDTO = new MaterialFollowupDTO();
					BeanUtils.copyProperties(materialFollowup, materialFollowupDTO);
					
					list.add(materialFollowupDTO);
				}
				basePage.setRows(list);
				basePage.setTotal(dataGrid.getTotal());
			}
		}
		return basePage;
	}

	@Override
	public BasePage<MaterialFollowupDTO> dataGrid(Set<Integer> states) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BasePage<MaterialFollowupDTO> dataGridByOrderInfo(OrderInfoCommand orderInfoCommand,PageCommand pageCommand) {
		// TODO Auto-generated method stub
		SystemContext.setPageOffset(pageCommand.getPage());
		SystemContext.setPageSize(pageCommand.getRows());
		SystemContext.setOrder(pageCommand.getOrder());
		SystemContext.setSort("t."+pageCommand.getSort());
		OrderInfo orderInfoQuery = new OrderInfo();
		BeanUtils.copyProperties(orderInfoCommand, orderInfoQuery);
		//System.out.println("orderQuery"+orderInfoQuery.getSeller()+orderInfoQuery.getMerchandiser());
		Pager<MaterialFollowup> dataGrid = materialFollowupService.dataGridByOrderInfo(orderInfoQuery,null);
		BasePage<MaterialFollowupDTO> basePage = new BasePage<MaterialFollowupDTO>();
		if(dataGrid != null){
			if(dataGrid.getRows().size() > 0){
				List<MaterialFollowupDTO> list = new ArrayList<MaterialFollowupDTO>();
				for(MaterialFollowup materialFollowup : dataGrid.getRows()){
					MaterialFollowupDTO materialFollowupDTO = new MaterialFollowupDTO();
					BeanUtils.copyProperties(materialFollowup, materialFollowupDTO);
					materialFollowupDTO.setDateCommit(TimeUtil.dateToString(materialFollowup.getDateCommit()));
					materialFollowupDTO.setDateDeliver(TimeUtil.dateToString(materialFollowup.getDateDeliver()));
					materialFollowupDTO.setDateFinish(TimeUtil.dateToString(materialFollowup.getDateFinish()));
					materialFollowupDTO.setDateOnline(TimeUtil.dateToString(materialFollowup.getDateOnline()));
					materialFollowupDTO.setDateSubmit(TimeUtil.dateToString(materialFollowup.getDateSubmit()));
					if(materialFollowup.getOrderInfo() != null){
						OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
						orderInfoDTO.setId(materialFollowup.getOrderInfo().getId());
						orderInfoDTO.setOrderNo(materialFollowup.getOrderInfo().getOrderNo());
						orderInfoDTO.setSeller(materialFollowup.getOrderInfo().getSeller());
						orderInfoDTO.setMerchandiser(materialFollowup.getOrderInfo().getMerchandiser());
						orderInfoDTO.setClientName(materialFollowup.getOrderInfo().getClientName());
						materialFollowupDTO.setOrderInfoDTO(orderInfoDTO);
					}
					list.add(materialFollowupDTO);
				}
				basePage.setRows(list);
				basePage.setTotal(dataGrid.getTotal());
			}
		}
		return basePage;
	}

	@Override
	public BasePage<MaterialFollowupDTO> dataGridByMaterialFollowup(MaterialFollowupCommand materialFollowupCommand,PageCommand pageCommand) {
		// TODO Auto-generated method stub
		SystemContext.setPageOffset(pageCommand.getPage());
		SystemContext.setPageSize(pageCommand.getRows());
		SystemContext.setOrder(pageCommand.getOrder());
		SystemContext.setSort("t."+pageCommand.getSort());
		Pager<MaterialFollowup> dataGrid = materialFollowupService.dataGrid();
		BasePage<MaterialFollowupDTO> basePage = new BasePage<MaterialFollowupDTO>();
		if(dataGrid != null){
			if(dataGrid.getRows().size() > 0){
				List<MaterialFollowupDTO> list = new ArrayList<MaterialFollowupDTO>();
				for(MaterialFollowup materialFollowup : dataGrid.getRows()){
					MaterialFollowupDTO materialFollowupDTO = new MaterialFollowupDTO();
					BeanUtils.copyProperties(materialFollowup, materialFollowupDTO);
					
					list.add(materialFollowupDTO);
				}
				basePage.setRows(list);
				basePage.setTotal(dataGrid.getTotal());
			}
		}
		return basePage;
	}

	@Override
	public BasePage<MaterialFollowupDTO> dataGridByDateDeliver(Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BasePage<MaterialFollowupDTO> dataGridByDateCommit(Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MaterialFollowupDTO get(Integer id) {
		// TODO Auto-generated method stub
		if(id != null){
			MaterialFollowupDTO materialFollowupDTO = new MaterialFollowupDTO();
			MaterialFollowup materialFollowup = materialFollowupService.getMaterialFollowup(id);
			if(materialFollowup != null){
				BeanUtils.copyProperties(materialFollowup, materialFollowupDTO);
				if(materialFollowup.getDateCommit() != null){
					materialFollowupDTO.setDateCommit(TimeUtil.dateToString(materialFollowup.getDateCommit()));
				}
				if(materialFollowup.getDateDeliver() != null){
					materialFollowupDTO.setDateDeliver(TimeUtil.dateToString(materialFollowup.getDateDeliver()));	
				}
				if(materialFollowup.getDateFinish() != null){
					materialFollowupDTO.setDateFinish(TimeUtil.dateToString(materialFollowup.getDateFinish()));
				}
				if(materialFollowup.getDateOnline()!= null){
					materialFollowupDTO.setDateOnline(TimeUtil.dateToString(materialFollowup.getDateOnline()));
				}
				if(materialFollowup.getDateSubmit() != null){
					materialFollowupDTO.setDateSubmit(TimeUtil.dateToString(materialFollowup.getDateSubmit()));
				}
				if(materialFollowup.getOrderInfo() != null){
					OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
					BeanUtils.copyProperties(materialFollowup.getOrderInfo(), orderInfoDTO);
					materialFollowupDTO.setOrderInfoDTO(orderInfoDTO);		
				}
			}
			return materialFollowupDTO;
		}
		return null;
	}

	@Override
	public Integer add(MaterialFollowupCommand materialFollowupCommand) {
		// TODO Auto-generated method stub
		if(materialFollowupCommand != null){
			MaterialFollowup materialFollowup = new MaterialFollowup();
			BeanUtils.copyProperties(materialFollowupCommand, materialFollowup);
			
			if(materialFollowupCommand.getDateCommit() != null){
				materialFollowup.setDateCommit(TimeUtil.datToDate(materialFollowupCommand.getDateCommit()));
			}
			if(materialFollowupCommand.getDateDeliver() != null){
				materialFollowup.setDateDeliver(TimeUtil.datToDate(materialFollowupCommand.getDateDeliver()));
			}
			if(materialFollowupCommand.getDateFinish() != null){
				materialFollowup.setDateFinish(TimeUtil.datToDate(materialFollowupCommand.getDateFinish()));
			}
			if(materialFollowupCommand.getDateOnline() != null){
				materialFollowup.setDateOnline(TimeUtil.datToDate(materialFollowupCommand.getDateOnline()));
			}
			if(materialFollowupCommand.getDateSubmit() != null){
				materialFollowup.setDateSubmit(TimeUtil.datToDate(materialFollowupCommand.getDateSubmit()));
			}
			//设置正式提交状态
			materialFollowup.setState(MaterialFollowupConst.save);
			if(materialFollowupCommand.getOrderId() != null){
				OrderInfo orderInfo = new OrderInfo(materialFollowupCommand.getOrderId());
				materialFollowup.setOrderInfo(orderInfo);
			}
			Serializable add = materialFollowupService.add(materialFollowup);
			if(add != null){
				return (Integer)add;
			}else {
				return null;
			}
		}
		return null;
	}

	@Override
	public void edit(MaterialFollowupCommand materialFollowupCommand, Integer currUserI) {
		if(materialFollowupCommand != null){
			//System.out.println(materialFollowupCommand);
			MaterialFollowup materialFollowup = materialFollowupService.get(materialFollowupCommand.getId());
			
			//变更记录，一天后才产生变更记录
			MaterialFollowup materialFollowup1 = null;
			if (materialFollowup.getState() > MaterialFollowupConst.temporarysave) {//暂存状态不需要计算变更记录
				//materialFollowup1 = materialFollowupService.get(materialFollowupCommand.getId());
				materialFollowup1 = new MaterialFollowup();
				BeanUtils.copyProperties(materialFollowup, materialFollowup1);
			}
			BeanUtils.copyProperties(materialFollowupCommand, materialFollowup);
			//System.out.println(">>>>>"+materialFollowup.getOrderInfo().getOrderNo()+materialFollowup.getMtCode());
			materialFollowupService.edit(materialFollowup);
			//计算变更记录放在最后
			materialFollowupAltFacade.addEditRecord(materialFollowup1, materialFollowupCommand, currUserI);
		}
	}

	@Override
	public void delete(Integer id) {
		if(id != null){
			materialFollowupService.delete(id);
		}
	}

	@Override
	public Boolean getByOrder(OrderInfoCommand orderInfoCommand) {
		if(orderInfoCommand != null &&  orderInfoCommand.getOrderNo() != null){
			OrderInfo orderInfoQuery = new OrderInfo();
			if(orderInfoCommand.getOrderNo() != null){
				orderInfoQuery.setOrderNo(orderInfoCommand.getOrderNo());
				OrderInfo orderInfo = orderInfoService.getByOrder(orderInfoQuery);
				if(orderInfo != null){
					return true;
				}else {
					return false;
				} 
			}
			return false;
		}
		return false;
	}

	@Override
	public OrderInfoDTO getByOrderDTO(OrderInfoCommand orderInfoCommand,Boolean hasRoles) {
		
		if(orderInfoCommand != null &&  orderInfoCommand.getOrderNo() != null){
			OrderInfo orderInfoQuery = new OrderInfo();
			if(orderInfoCommand.getOrderNo() != null){
				orderInfoQuery.setOrderNo(orderInfoCommand.getOrderNo());
				OrderInfo orderInfo = orderInfoService.getByOrder(orderInfoQuery);
				if(orderInfo != null ){
					OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
					BeanUtils.copyProperties(orderInfo, orderInfoDTO);
					if (hasRoles != null && !hasRoles) {
						orderInfoDTO.setClientName("");
						orderInfoDTO.setClientBrand("");
					}
					return orderInfoDTO;
				}
			}
			return null;
		} 
		return null;
	}

	@Override
	public List<MaterialFollowupDTO> listAll(OrderInfoCommand orderInfoCommand, Set<Integer> states) {
		// TODO Auto-generated method stub
		OrderInfo orderInfoQuery = new OrderInfo();
		if(orderInfoCommand != null){
			BeanUtils.copyProperties(orderInfoCommand, orderInfoQuery);
			if(orderInfoCommand.getDateOrderStart() != null){
				orderInfoQuery.setDateOrderStart(TimeUtil.stringToDate(orderInfoCommand.getDateOrderStart()));
			}
			if(orderInfoCommand.getDateOrderEnd() != null){
				orderInfoQuery.setDateOrderEnd(TimeUtil.stringToDate(orderInfoCommand.getDateOrderEnd()));
			}
		}
		List<MaterialFollowup> listAll = materialFollowupService.listAll(orderInfoQuery, states,false);
		
		List<MaterialFollowupDTO>  materialFollowupDTOs = new ArrayList<MaterialFollowupDTO>();
		if(listAll != null && listAll.size() > 0){
			for(MaterialFollowup materialFollowup : listAll){
				MaterialFollowupDTO materialFollowupDTO = new MaterialFollowupDTO();
				BeanUtils.copyProperties(materialFollowup, materialFollowupDTO);
				if(materialFollowup.getDateCommit() != null){
					materialFollowupDTO.setDateCommit(TimeUtil.dateToString(materialFollowup.getDateCommit()));
				}
				if(materialFollowup.getDateDeliver() != null){
					materialFollowupDTO.setDateDeliver(TimeUtil.dateToString(materialFollowup.getDateDeliver()));
				}
				if(materialFollowup.getDateFinish() != null){
					materialFollowupDTO.setDateFinish(TimeUtil.dateToString(materialFollowup.getDateFinish()));
				}
				if(materialFollowup.getDateOnline() != null){
					materialFollowupDTO.setDateOnline(TimeUtil.dateToString(materialFollowup.getDateOnline()));
				}
				if(materialFollowup.getDateSubmit() != null){
					materialFollowupDTO.setDateSubmit(TimeUtil.dateToString(materialFollowup.getDateSubmit()));
				}
				if(materialFollowup.getOrderInfo() != null){
					OrderInfo orderInfo = materialFollowup.getOrderInfo();
					OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
					BeanUtils.copyProperties(orderInfo, orderInfoDTO);
					
					materialFollowupDTO.setOrderInfoDTO(orderInfoDTO);
				}
				materialFollowupDTOs.add(materialFollowupDTO);
			}
		}
		return materialFollowupDTOs;
	}

	@Override
	public List<MaterialFollowupDTO> listAllByOrderId(OrderInfoCommand orderInfoCommand, Set<Integer> states) {
		// TODO Auto-generated method stub
		OrderInfo orderInfoQuery = new OrderInfo();
		if(orderInfoCommand != null){
			BeanUtils.copyProperties(orderInfoCommand, orderInfoQuery);
			if(orderInfoCommand.getDateOrderStart() != null){
				orderInfoQuery.setDateOrderStart(TimeUtil.stringToDate(orderInfoCommand.getDateOrderStart()));
			}
			if(orderInfoCommand.getDateOrderEnd() != null){
				orderInfoQuery.setDateOrderEnd(TimeUtil.stringToDate(orderInfoCommand.getDateOrderEnd()));
			}
		}
		List<MaterialFollowup> listAll = materialFollowupService.listAll(orderInfoQuery, states,false);
		
		List<MaterialFollowupDTO>  materialFollowupDTOs = new ArrayList<MaterialFollowupDTO>();
		if(listAll != null && listAll.size() > 0){
			for(MaterialFollowup materialFollowup : listAll){
				MaterialFollowupDTO materialFollowupDTO = new MaterialFollowupDTO();
				BeanUtils.copyProperties(materialFollowup, materialFollowupDTO);
				if(materialFollowup.getDateCommit() != null){
					materialFollowupDTO.setDateCommit(TimeUtil.dateToString(materialFollowup.getDateCommit()));
				}
				if(materialFollowup.getDateDeliver() != null){
					materialFollowupDTO.setDateDeliver(TimeUtil.dateToString(materialFollowup.getDateDeliver()));
				}
				if(materialFollowup.getDateFinish() != null){
					materialFollowupDTO.setDateFinish(TimeUtil.dateToString(materialFollowup.getDateFinish()));
				}
				if(materialFollowup.getDateOnline() != null){
					materialFollowupDTO.setDateOnline(TimeUtil.dateToString(materialFollowup.getDateOnline()));
				}
				if(materialFollowup.getDateSubmit() != null){
					materialFollowupDTO.setDateSubmit(TimeUtil.dateToString(materialFollowup.getDateSubmit()));
				}
				if(materialFollowup.getOrderInfo() != null){
					OrderInfo orderInfo = materialFollowup.getOrderInfo();
					OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
					BeanUtils.copyProperties(orderInfo, orderInfoDTO);
					
					materialFollowupDTO.setOrderInfoDTO(orderInfoDTO);
				}
				materialFollowupDTOs.add(materialFollowupDTO);
			}
		}else{
			if(orderInfoCommand.getId() != null){
				OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
				OrderInfo orderInfo2 = orderInfoService.get(orderInfoCommand.getId());
				BeanUtils.copyProperties(orderInfo2, orderInfoDTO);
				MaterialFollowupDTO materialFollowupDTO = new MaterialFollowupDTO();
				materialFollowupDTO.setOrderInfoDTO(orderInfoDTO);
				materialFollowupDTOs.add(materialFollowupDTO);
			}
		}
		return materialFollowupDTOs;
	}
	
	@Override
	public BasePage<OrderInfoDTO> dataGridForState(OrderInfoCommand orderInfoCommand, PageCommand pageCommand, Set<Integer> states,Boolean hasRoles) {

		SystemContext.setPageOffset(pageCommand.getPage());
		SystemContext.setPageSize(pageCommand.getRows());
		SystemContext.setOrder(pageCommand.getOrder());
		SystemContext.setSort(pageCommand.getSort());
		Pager<OrderInfo> dataGrid = null;
		OrderInfo orderInfoquery = new OrderInfo();
		orderInfoquery.setUnread(orderInfoCommand.getIsRead());
		BeanUtils.copyProperties(orderInfoCommand, orderInfoquery);
		dataGrid = orderInfoService.dataGrid(orderInfoquery, states);
		/*for(Integer integer : states){
			System.out.println(".............."+integer);
		}*/
		BasePage<OrderInfoDTO> basePage = new BasePage<OrderInfoDTO>();
		if (dataGrid.getTotal() != 0) {
			if (dataGrid.getRows().size() > 0) {
				List<OrderInfoDTO> list = new ArrayList<OrderInfoDTO>();
				// System.out.println("查看订单----角色："+hasRoles);
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

	@Override
	public Boolean checkExcitByOrder(OrderInfoCommand orderInfoCommand, Set<Integer> states) {
		if(orderInfoCommand != null){
			OrderInfo orderInfoQuery = new OrderInfo();
			BeanUtils.copyProperties(orderInfoCommand, orderInfoQuery);
			List<MaterialFollowup> listAll = materialFollowupService.listAll(orderInfoQuery, states, true);
			if(listAll != null && listAll.size() > 0){
				return true;
			}
			return false;
		}
		return false;
	}

	@Override
	public Integer qualityTotal(Integer orderId) {
		// TODO Auto-generated method stub
		if(orderId != null){
			Integer total = 0;
			OrderInfo orderInfo = new OrderInfo(orderId);
			List<MaterialFollowup> listAll = materialFollowupService.listAll(orderInfo, null, false);
			
			if(listAll != null && listAll.size() > 0){
				for(MaterialFollowup materialFollowup : listAll){
					if(materialFollowup.getQuality() != null){
						total += materialFollowup.getQuality(); 
					}
					
				}
			}
			return total;
		}
		return null;
	}

}
