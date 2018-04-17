package com.apical.oddm.facade.order.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apical.oddm.application.order.OrderFittingServiceI;
import com.apical.oddm.application.order.OrderHardwareServiceI;
import com.apical.oddm.application.order.OrderInfoServiceI;
import com.apical.oddm.application.order.OrderOSServiceI;
import com.apical.oddm.application.order.OrderPackingServiceI;
import com.apical.oddm.application.order.OrderShellServiceI;
import com.apical.oddm.core.model.document.Document;
import com.apical.oddm.core.model.order.OrderFitting;
import com.apical.oddm.core.model.order.OrderHardware;
import com.apical.oddm.core.model.order.OrderInfo;
import com.apical.oddm.core.model.order.OrderOS;
import com.apical.oddm.core.model.order.OrderPacking;
import com.apical.oddm.core.model.order.OrderShell;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.page.SystemContext;
import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.base.dto.BasePage;
import com.apical.oddm.facade.document.dto.DocumentDTO;
import com.apical.oddm.facade.order.OrderInfoFacade;
import com.apical.oddm.facade.order.command.OrderFittingCommand;
import com.apical.oddm.facade.order.command.OrderHardwareCommand;
import com.apical.oddm.facade.order.command.OrderInfoCommand;
import com.apical.oddm.facade.order.command.OrderOSCommand;
import com.apical.oddm.facade.order.command.OrderPackingCommand;
import com.apical.oddm.facade.order.command.OrderShellCommand;
import com.apical.oddm.facade.order.dto.OrderFittingDTO;
import com.apical.oddm.facade.order.dto.OrderHardwareDTO;
import com.apical.oddm.facade.order.dto.OrderInfoDTO;
import com.apical.oddm.facade.order.dto.OrderOSDTO;
import com.apical.oddm.facade.order.dto.OrderPackingDTO;
import com.apical.oddm.facade.order.dto.OrderShellDTO;
import com.apical.oddm.facade.util.TimeUtil;

/** 
 * @description 类的描述：
 * @author  作者 : zhuzh
 * @date 创建时间：2016年10月31日 下午5:51:25 
 * @version 1.0 
 */
@Component("orderInfoFacade")
public class OrderInfoFacadeImpl implements OrderInfoFacade{

	@Autowired 
	private OrderInfoServiceI orderInfoService;
	
	@Autowired 
	private OrderOSServiceI orderOSService;
	
	@Autowired 
	private OrderPackingServiceI orderPackingService;
	
	@Autowired 
	private OrderHardwareServiceI orderHardwareService;
	
	@Autowired 
	private OrderFittingServiceI orderFittingService;
	
	@Autowired
	private OrderShellServiceI orderShellService;
	
	@Override
	public OrderInfoDTO getOrderInfoDTO(int id) {
				OrderInfo orderInfo = orderInfoService.getOrderInfo(id);
		OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
		BeanUtils.copyProperties(orderInfo, orderInfoDTO);
		orderInfoDTO.setDateDelivery(TimeUtil.dateToString(orderInfo.getDateDelivery()));
		orderInfoDTO.setDateExamine(TimeUtil.dateToString(orderInfo.getDateExamine()));
		orderInfoDTO.setDateOrder(TimeUtil.dateToString(orderInfo.getDateOrder()));
		orderInfoDTO.setTimestamp(TimeUtil.timestampToStringDetaile(orderInfo.getTimestamp()));
		if(orderInfo.getOrderInfo() != null){
			orderInfoDTO.setPid(orderInfo.getOrderInfo().getId());
			OrderInfo orderInfoOld = orderInfoService.get(orderInfo.getOrderInfo().getId());
			OrderInfoDTO orderInfoDTOOld = new OrderInfoDTO();
			BeanUtils.copyProperties(orderInfoOld, orderInfoDTOOld);
			orderInfoDTOOld.setOrderNo(orderInfoOld.getOrderNo());
			orderInfoDTO.setOrderInfoDTO(orderInfoDTOOld);
		}
		
		//软件信息
		if(orderInfo.getOrderOs() != null){
			Set<OrderOS> orderOss = orderInfo.getOrderOs();
			OrderOSDTO orderOSDTO = new OrderOSDTO();
			for(OrderOS os : orderOss){
				BeanUtils.copyProperties(os, orderOSDTO);
			}
			orderInfoDTO.setOrderOSDTO(orderOSDTO);
		}
		
		//配件
		if(orderInfo.getOrderFittings() != null ){
			Set<OrderFitting> orderFittings = orderInfo.getOrderFittings();
			Set<OrderFittingDTO> orderFittingDTOs = new HashSet<OrderFittingDTO>();
			for(OrderFitting orderFitting : orderFittings){
				OrderFittingDTO orderFittingDTO = new OrderFittingDTO();
				BeanUtils.copyProperties(orderFitting, orderFittingDTO);
				orderFittingDTOs.add(orderFittingDTO);
			}
			orderInfoDTO.setOrderFittingDTOs(orderFittingDTOs);
		}
		
		
		//包材
		if(orderInfo.getOrderPackings() != null){
			Set<OrderPacking> orderPackings = orderInfo.getOrderPackings();
			Set<OrderPackingDTO> orderPackingDTOs = new HashSet<OrderPackingDTO>();
			for(OrderPacking orderPacking: orderPackings){
				OrderPackingDTO orderPackingDTO = new OrderPackingDTO();
				BeanUtils.copyProperties(orderPacking, orderPackingDTO);
				orderPackingDTOs.add(orderPackingDTO);
			}
			orderInfoDTO.setOrderPackingDTOs(orderPackingDTOs);
		}
		
		
		//裸机
		if(orderInfo.getOrderHardwares() != null){
			Set<OrderHardware> orderHardwares = orderInfo.getOrderHardwares();
			Set<OrderHardwareDTO> orderHardwareDTOs = new HashSet<OrderHardwareDTO>();
			for(OrderHardware orderHardware: orderHardwares){
				OrderHardwareDTO orderHardwareDTO = new OrderHardwareDTO();
				BeanUtils.copyProperties(orderHardware, orderHardwareDTO);
				orderHardwareDTOs.add(orderHardwareDTO);
			}
			orderInfoDTO.setOrderHardwareDTOs(orderHardwareDTOs);
		}
		
		
		//外壳
		if(orderInfo.getOrderShells() != null){
			Set<OrderShell> orderShells = orderInfo.getOrderShells();
			Set<OrderShellDTO> orderShellDTOs = new HashSet<OrderShellDTO>();
			for(OrderShell orderShell: orderShells){
				OrderShellDTO orderShellDTO = new OrderShellDTO();
				BeanUtils.copyProperties(orderShell, orderShellDTO);
				orderShellDTOs.add(orderShellDTO);
			}
			orderInfoDTO.setOrderShellDTOs(orderShellDTOs);
		}
		
		
		return orderInfoDTO;
	}

	@Override
	public Boolean getByOrderNo(String orderNo) {
				OrderInfo orderInfo = orderInfoService.getByOrderNo(orderNo);
		if(orderInfo != null){
			return true;
		}
		return false;
	}

	@Override
	public List<OrderInfoDTO> getAllDocumentByOrderId(int orderId) {
				List<OrderInfo> allDocumentByOrderId = orderInfoService.getAllDocumentByOrderId(orderId);
		List<OrderInfoDTO> list = new ArrayList<OrderInfoDTO>();
		if(allDocumentByOrderId != null){
			for(OrderInfo orderInfo : allDocumentByOrderId){
				OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
				BeanUtils.copyProperties(orderInfo, orderInfoDTO);
				orderInfoDTO.setDateDelivery(TimeUtil.dateToString(orderInfo.getDateDelivery()));
				orderInfoDTO.setDateExamine(TimeUtil.dateToString(orderInfo.getDateExamine()));
				orderInfoDTO.setDateOrder(TimeUtil.dateToString(orderInfo.getDateOrder()));
				orderInfoDTO.setTimestamp(TimeUtil.timestampToStringDetaile(orderInfo.getTimestamp()));
				if(orderInfo.getDocuments() != null){
					if(orderInfo.getDocuments().size() > 0){
						Set<DocumentDTO> documentDTOs = new HashSet<DocumentDTO>();
						for(Document document : orderInfo.getDocuments()){
							DocumentDTO documentDTO = new DocumentDTO();
							BeanUtils.copyProperties(document, documentDTO);
							if(document.getUploadtime() != null){
								documentDTO.setUploadtime(TimeUtil.dateToStringDetaile(document.getUploadtime()));
							}
							documentDTOs.add(documentDTO);
						}
						orderInfoDTO.setDocumentDTOs(documentDTOs);
					}
				}
				list.add(orderInfoDTO);
			}
		}
		System.out.println(list);
		return list;
	}

	@Override
	public BasePage<OrderInfoDTO> dataGrid(Set<Integer> states) {
				return null;
	}

	@Override
	public BasePage<OrderInfoDTO> dataGrid(OrderInfoCommand orderInfoCommand, PageCommand pageCommand, Set<Integer> states,Boolean hasRoles) {
				SystemContext.setPageOffset(pageCommand.getPage());
		SystemContext.setPageSize(pageCommand.getRows());
		SystemContext.setOrder(pageCommand.getOrder());
		SystemContext.setSort("t."+pageCommand.getSort());
		Pager<OrderInfo> dataGrid = null;
		OrderInfo orderInfoquery = new OrderInfo();
		BeanUtils.copyProperties(orderInfoCommand, orderInfoquery);
		dataGrid = orderInfoService.dataGrid(orderInfoquery,states);
		BasePage<OrderInfoDTO> basePage = new BasePage<OrderInfoDTO>();
		if(dataGrid.getTotal() != 0){
			if(dataGrid.getRows().size() > 0){
				List<OrderInfoDTO> list = new  ArrayList<OrderInfoDTO>();
				System.out.println("查看订单----角色："+hasRoles);
				if(hasRoles){
					for(OrderInfo orderInfo:dataGrid.getRows()){
						OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
						BeanUtils.copyProperties(orderInfo, orderInfoDTO);
						orderInfoDTO.setClientNameCode(orderInfo.getClientName());
						orderInfoDTO.setClientName("");
						orderInfoDTO.setDateDelivery(TimeUtil.dateToString(orderInfo.getDateDelivery()));
						orderInfoDTO.setDateExamine(TimeUtil.dateToString(orderInfo.getDateExamine()));
						orderInfoDTO.setDateOrder(TimeUtil.dateToString(orderInfo.getDateOrder()));
					//	orderInfoDTO.setTimestamp(TimeUtil.timestampToStringDetaile(orderInfo.getTimestamp()));
						orderInfoDTO.setUpdatetime(TimeUtil.timestampToStringDetaile(orderInfo.getUpdatetime()));
						list.add(orderInfoDTO);
					}
				}else {
					for(OrderInfo orderInfo:dataGrid.getRows()){
						OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
						BeanUtils.copyProperties(orderInfo, orderInfoDTO);
						orderInfoDTO.setClientName("");
						orderInfoDTO.setDateDelivery(TimeUtil.dateToString(orderInfo.getDateDelivery()));
						orderInfoDTO.setDateExamine(TimeUtil.dateToString(orderInfo.getDateExamine()));
						orderInfoDTO.setDateOrder(TimeUtil.dateToString(orderInfo.getDateOrder()));
						orderInfoDTO.setTimestamp(TimeUtil.timestampToStringDetaile(orderInfo.getTimestamp()));
						orderInfoDTO.setUpdatetime(TimeUtil.timestampToStringDetaile(orderInfo.getUpdatetime()));
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
	public BasePage<OrderInfoDTO> dataGrid(Date startDate, Date endDate, PageCommand pageCommand) {
				return null;
	}

	@Override
	public Boolean add(OrderInfoCommand orderInfoCommand) {
				System.out.println(".add......"+orderInfoCommand);
		OrderInfo orderInfo = new OrderInfo();
		if(orderInfoCommand.getPid() != null){
			//如果pid不为空，则说明是创建翻单
			OrderInfo orderInfoOld =  orderInfoService.getOrderInfo(orderInfoCommand.getPid());
			//OrderInfo orderInfoOld = new OrderInfo(orderInfoCommand.getPid());
			orderInfo.setOrderInfo(orderInfoOld);
		}
		BeanUtils.copyProperties(orderInfoCommand, orderInfo);
		//System.out.println("....order."+orderInfo);
		orderInfo.setDateDelivery(TimeUtil.datToDate(orderInfoCommand.getDateDelivery()));
		orderInfo.setDateExamine(TimeUtil.datToDate(orderInfoCommand.getDateExamine()));
		orderInfo.setDateOrder(TimeUtil.datToDate(orderInfoCommand.getDateOrder()));
		//软件信息
		OrderOS orderOS = new OrderOS();
		OrderOSCommand orderOSCommand = orderInfoCommand.getOrderOSCommand();
		if(orderInfoCommand.getPid() != null){
			OrderInfo orderInfo2 = orderInfoService.get(orderInfoCommand.getPid());
			orderInfo.setOrderInfo(orderInfo2);
			//新软件直接设置
			orderOSCommand.setIsrepeat(1);
		}
		BeanUtils.copyProperties(orderOSCommand, orderOS);
		orderOS.setOrderInfo(orderInfo);
		Set<OrderOS> orderOsSet = new HashSet<OrderOS>();
		orderOsSet.add(orderOS);
		orderInfo.setOrderOs(orderOsSet);
		
		//定单硬件物料
		Set<OrderHardwareCommand> orderHardwareCommands = orderInfoCommand.getOrderHardwareCommands();
		Set<OrderHardware> orderHardwares = new HashSet<OrderHardware>();
		for(OrderHardwareCommand orderHardwareCommand : orderHardwareCommands){
			OrderHardware orderHardware = new OrderHardware();
			BeanUtils.copyProperties(orderHardwareCommand, orderHardware);
			orderHardware.setOrderInfo(orderInfo);
			orderHardwares.add(orderHardware);
		}
		orderInfo.setOrderHardwares(orderHardwares);
		
		//外壳
		Set<OrderShellCommand> orderShellCommands = orderInfoCommand.getOrderShellCommands();
		Set<OrderShell> orderShells = new HashSet<OrderShell>();
		for(OrderShellCommand orderShellCommand : orderShellCommands){
			OrderShell orderShell = new OrderShell();
			BeanUtils.copyProperties(orderShellCommand, orderShell);
			orderShell.setOrderInfo(orderInfo);
			orderShells.add(orderShell);
		}
		orderInfo.setOrderShells(orderShells);
		
		//包材
		Set<OrderPackingCommand> orderPackingCommands = orderInfoCommand.getOrderPackingCommands();
		Set<OrderPacking> orderPackings = new HashSet<OrderPacking>();
		for(OrderPackingCommand orderPackingCommand : orderPackingCommands){
			OrderPacking orderPacking = new OrderPacking();
			BeanUtils.copyProperties(orderPackingCommand, orderPacking);
			orderPacking.setOrderInfo(orderInfo);
			orderPackings.add(orderPacking);
		}
		orderInfo.setOrderPackings(orderPackings);
		
		//配件
		Set<OrderFittingCommand> orderFittingCommands = orderInfoCommand.getOrderFittingCommands();
		Set<OrderFitting> orderFittings = new HashSet<OrderFitting>();
		for(OrderFittingCommand orderFittingCommand : orderFittingCommands){
			OrderFitting orderFitting = new OrderFitting();
			BeanUtils.copyProperties(orderFittingCommand, orderFitting);
			orderFitting.setOrderInfo(orderInfo);
			orderFittings.add(orderFitting);
		}
		orderInfo.setOrderFittings(orderFittings);
		
		//用户
		/*User user = new User();
		user.setId(orderInfoCommand.getUserId());
		orderInfo.setUser(user);*/
		//更新订单状态
		orderInfo.setState(2);
		Serializable add = orderInfoService.add(orderInfo);
		if(add != null){
			orderInfoService.addOtherEntity((Integer)add);
			return true;
		}
		return false;
	}

	@Override
	public void edit(OrderInfoCommand orderInfoCommand) {
				System.out.println(".edit......"+orderInfoCommand);
		OrderInfo orderInfo = orderInfoService.get(orderInfoCommand.getId());
		OrderInfo orderInfoBefore = orderInfo;
		BeanUtils.copyProperties(orderInfoCommand, orderInfo);
		orderInfo.setDateDelivery(TimeUtil.datToDate(orderInfoCommand.getDateDelivery()));
		orderInfo.setDateExamine(TimeUtil.datToDate(orderInfoCommand.getDateExamine()));
		orderInfo.setDateOrder(TimeUtil.datToDate(orderInfoCommand.getDateOrder()));
		//软件信息
		
		OrderOS orderOS = new OrderOS();
		if(orderInfoCommand.getOrderOSCommand() != null){
			OrderOSCommand orderOSCommand = orderInfoCommand.getOrderOSCommand();
			if(orderOSCommand.getId() != null){
				orderOS.setId(orderOSCommand.getId());
				BeanUtils.copyProperties(orderOSCommand, orderOS);
				orderOS.setOrderInfo(orderInfo);
				Set<OrderOS> orderOsSet = new HashSet<OrderOS>();
				orderOsSet.add(orderOS);
				orderInfo.setOrderOs(orderOsSet);
			}
		}
		
		
		//定单硬件物料
		if(orderInfoCommand.getOrderHardwareCommands() != null  && orderInfoCommand.getOrderHardwareCommands().size() > 0){
			Set<OrderHardwareCommand> orderHardwareCommands = orderInfoCommand.getOrderHardwareCommands();
			Set<OrderHardware> orderHardwares = new HashSet<OrderHardware>();
			for(OrderHardwareCommand orderHardwareCommand : orderHardwareCommands){
				OrderHardware orderHardware = new OrderHardware();
				if(orderHardwareCommand.getId() != null){
					orderHardware.setId(orderHardwareCommand.getId());
					BeanUtils.copyProperties(orderHardwareCommand, orderHardware);
					orderHardware.setOrderInfo(orderInfo);
					orderHardwares.add(orderHardware);
				}
			}
			orderInfo.setOrderHardwares(orderHardwares);
		}
		
		
		//外壳
		if(orderInfoCommand.getOrderShellCommands() != null  && orderInfoCommand.getOrderShellCommands().size() > 0){
			Set<OrderShellCommand> orderShellCommands = orderInfoCommand.getOrderShellCommands();
			Set<OrderShell> orderShells = new HashSet<OrderShell>();
			for(OrderShellCommand orderShellCommand : orderShellCommands){
				OrderShell orderShell = new OrderShell();
				if(orderShellCommand.getId() != null){
					orderShell.setId(orderShellCommand.getId());
					BeanUtils.copyProperties(orderShellCommand, orderShell);
					orderShell.setOrderInfo(orderInfo);
					orderShells.add(orderShell);
				}
			}
			orderInfo.setOrderShells(orderShells);
		}
		
		
		//包材
		if(orderInfoCommand.getOrderPackingCommands() != null  && orderInfoCommand.getOrderPackingCommands().size() > 0){
			Set<OrderPackingCommand> orderPackingCommands = orderInfoCommand.getOrderPackingCommands();
			Set<OrderPacking> orderPackings = new HashSet<OrderPacking>();
			for(OrderPackingCommand orderPackingCommand : orderPackingCommands){
				OrderPacking orderPacking = new OrderPacking();
				if(orderPackingCommand.getId() != null){
					orderPacking.setId(orderPackingCommand.getId());
					BeanUtils.copyProperties(orderPackingCommand, orderPacking);
					orderPacking.setOrderInfo(orderInfo);
					orderPackings.add(orderPacking);
				}
			}
			orderInfo.setOrderPackings(orderPackings);
		}
		
		
		//配件
		if(orderInfoCommand.getOrderFittingCommands() != null && orderInfoCommand.getOrderFittingCommands().size() > 0){
			Set<OrderFittingCommand> orderFittingCommands = orderInfoCommand.getOrderFittingCommands();
			Set<OrderFitting> orderFittings = new HashSet<OrderFitting>();
			for(OrderFittingCommand orderFittingCommand : orderFittingCommands){
				OrderFitting orderFitting = new OrderFitting();
				if(orderFittingCommand.getId() != null){
					orderFitting.setId(orderFittingCommand.getId());
					BeanUtils.copyProperties(orderFittingCommand, orderFitting);
					orderFitting.setOrderInfo(orderInfo);
					orderFittings.add(orderFitting);
				}
			}
			orderInfo.setOrderFittings(orderFittings);
		}
		
		
		//用户
		/*if(orderInfoCommand.getUserId() != null){
			User user = new User();
			user.setId(orderInfoCommand.getUserId());
			orderInfo.setUser(user);
		}*/
		
		orderInfoService.edit(orderInfo);
		
	/*	addEditRecord(orderInfoBefore,orderInfoCommand);*/
	}
	
	/**
	 * 
	 * @param orderInfoBefore 编辑前
	 * @param orderInfoCommand  编辑后
	 */

	private void addEditRecord(OrderInfo orderInfoBefore, OrderInfoCommand orderInfoCommand) {
				if(orderInfoBefore != null && orderInfoCommand != null){
			if(orderInfoBefore.getClientName().trim().equals(orderInfoCommand.getClientName())){
				
			}
		}
	}

	@Override
	public OrderInfoDTO getByProductFactory(String productFactory) {
				OrderInfo orderInfo = orderInfoService.getByProductFactory(productFactory);
		OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
		if(orderInfo != null){
			System.out.println("orderInfo不为空");
			BeanUtils.copyProperties(orderInfo, orderInfoDTO);
			orderInfoDTO.setDateDelivery(TimeUtil.dateToString(orderInfo.getDateDelivery()));
			orderInfoDTO.setDateExamine(TimeUtil.dateToString(orderInfo.getDateExamine()));
			orderInfoDTO.setDateOrder(TimeUtil.dateToString(orderInfo.getDateOrder()));
			orderInfoDTO.setTimestamp(TimeUtil.timestampToStringDetaile(orderInfo.getTimestamp()));
			if(orderInfo.getOrderInfo() != null){
				orderInfoDTO.setPid(orderInfo.getOrderInfo().getId());
				OrderInfo orderInfoOld = orderInfoService.get(orderInfo.getOrderInfo().getId());
				OrderInfoDTO orderInfoDTOOld = new OrderInfoDTO();
				BeanUtils.copyProperties(orderInfoOld, orderInfoDTOOld);
				orderInfoDTOOld.setOrderNo(orderInfoOld.getOrderNo());
				orderInfoDTO.setOrderInfoDTO(orderInfoDTOOld);
			}
			
			//软件信息
			if(orderInfo.getOrderOs() != null){
				Set<OrderOS> orderOss = orderInfo.getOrderOs();
				OrderOSDTO orderOSDTO = new OrderOSDTO();
				for(OrderOS os : orderOss){
					BeanUtils.copyProperties(os, orderOSDTO);
				}
				orderInfoDTO.setOrderOSDTO(orderOSDTO);
			}
			
			
			//用户
			/*if(orderInfo.getUser() != null){
				User user = orderInfo.getUser();
				UserDTO userDTO = new UserDTO();
				BeanUtils.copyProperties(user, userDTO);
				orderInfoDTO.setUserDTO(userDTO);
			}*/
			
			
			//配件
			if(orderInfo.getOrderFittings() != null ){
				Set<OrderFitting> orderFittings = orderInfo.getOrderFittings();
				Set<OrderFittingDTO> orderFittingDTOs = new HashSet<OrderFittingDTO>();
				for(OrderFitting orderFitting : orderFittings){
					OrderFittingDTO orderFittingDTO = new OrderFittingDTO();
					BeanUtils.copyProperties(orderFitting, orderFittingDTO);
					orderFittingDTOs.add(orderFittingDTO);
				}
				orderInfoDTO.setOrderFittingDTOs(orderFittingDTOs);
			}
			
			
			//包材
			if(orderInfo.getOrderPackings() != null){
				Set<OrderPacking> orderPackings = orderInfo.getOrderPackings();
				Set<OrderPackingDTO> orderPackingDTOs = new HashSet<OrderPackingDTO>();
				for(OrderPacking orderPacking: orderPackings){
					OrderPackingDTO orderPackingDTO = new OrderPackingDTO();
					BeanUtils.copyProperties(orderPacking, orderPackingDTO);
					orderPackingDTOs.add(orderPackingDTO);
				}
				orderInfoDTO.setOrderPackingDTOs(orderPackingDTOs);
			}
			
			
			//裸机
			if(orderInfo.getOrderHardwares() != null){
				Set<OrderHardware> orderHardwares = orderInfo.getOrderHardwares();
				Set<OrderHardwareDTO> orderHardwareDTOs = new HashSet<OrderHardwareDTO>();
				for(OrderHardware orderHardware: orderHardwares){
					OrderHardwareDTO orderHardwareDTO = new OrderHardwareDTO();
					BeanUtils.copyProperties(orderHardware, orderHardwareDTO);
					orderHardwareDTOs.add(orderHardwareDTO);
				}
				orderInfoDTO.setOrderHardwareDTOs(orderHardwareDTOs);
			}
			
			
			//外壳
			if(orderInfo.getOrderShells() != null){
				Set<OrderShell> orderShells = orderInfo.getOrderShells();
				Set<OrderShellDTO> orderShellDTOs = new HashSet<OrderShellDTO>();
				for(OrderShell orderShell: orderShells){
					OrderShellDTO orderShellDTO = new OrderShellDTO();
					BeanUtils.copyProperties(orderShell, orderShellDTO);
					orderShellDTOs.add(orderShellDTO);
				}
				orderInfoDTO.setOrderShellDTOs(orderShellDTOs);
			}
			
		}
		return orderInfoDTO;
	}

	@Override
	public OrderInfoDTO getBaseOrderInfoDTO(int id) {
				OrderInfo orderInfo = orderInfoService.get(id);
		OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
		BeanUtils.copyProperties(orderInfo, orderInfoDTO);
		return orderInfoDTO;
	}

	@Override
	public OrderInfoDTO getOrderInfoByNo(String OrderNo) {
		OrderInfo orderInfo = orderInfoService.getByOrderNo(OrderNo);
		OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
		BeanUtils.copyProperties(orderInfo, orderInfoDTO);
		return orderInfoDTO;
	}

	@Override
	public List<String> listTopClientName(String clientName) {
				OrderInfo orderInfo = new OrderInfo();
		List<String> list = new ArrayList<String>();
		if(clientName != null){
			if("".equals(clientName)){
				clientName = "";
			}
			orderInfo.setClientName(clientName);
			List<Object> listTop = orderInfoService.listTop(orderInfo);
			if(listTop != null && listTop.size() > 0){
				for (Object object: listTop) {
					list.add(object+"");
				}
			}
		}
		
		return list;
	}

	@Override
	public List<String> listTopDistrict(String district) {
				OrderInfo orderInfo = new OrderInfo();
		List<String> list = new ArrayList<String>();
		if(district != null){
			if("".equals(district)){
				district = "";
			}
			orderInfo.setDistrict(district);
			List<Object> listTop = orderInfoService.listTop(orderInfo);
			if(listTop != null && listTop.size() > 0){
				for(Object object : listTop){
					list.add(object+"");
				}
			}
		}
		
		return list;
	}

	@Override
	public List<String> listTopGui(String gui) {
				OrderOS orderOS = new OrderOS();
		List<String> list = new ArrayList<String>();
		if(gui != null){
			if("".equals(gui)){
				gui = "";
			}
			orderOS.setGui(gui);
			List<Object> listTop = orderOSService.listTop(orderOS);
			if(listTop != null && listTop.size() > 0){
				for(Object object : listTop){
					list.add(object+"");
				}
			}
		}
		
		return list;
	}

	@Override
	public List<String> listTopLangOs(String langOs) {
				OrderOS orderOS = new OrderOS();
		List<String> list = new ArrayList<String>();
		if(langOs != null){
			if("".equals(langOs)){
				langOs = "";
			}
			orderOS.setLangOs(langOs);
			List<Object> listTop = orderOSService.listTop(orderOS);
			if(listTop != null && listTop.size() > 0){
				for(Object object : listTop){
					list.add(object+"");
				}
			}
		}
		orderOS.setGui(langOs);
		
		return list;
	}

	@Override
	public List<String> listTopLangDefault(String langDefault) {
				OrderOS orderOS = new OrderOS();
		List<String> list = new ArrayList<String>();
		if(langDefault != null){
			if("".equals(langDefault)){
				langDefault = "";
			}
			orderOS.setLangDefault(langDefault);
			List<Object> listTop = orderOSService.listTop(orderOS);
			if(listTop != null && listTop.size() > 0){
				for(Object object : listTop){
					list.add(object+"");
				}
			}
		}
		
		return list;
	}

	@Override
	public List<String> listTopTimezone(String timezone) {
				OrderOS orderOS = new OrderOS();
		List<String> list = new ArrayList<String>();
		if(timezone != null){
			if("".equals(timezone)){
				timezone = "";
			}
			System.out.println(timezone);
			orderOS.setTimezone(timezone);
			//orderOS.setTimezone("GMT+7");
			List<Object> listTop = orderOSService.listTop(orderOS);
			if(listTop != null && listTop.size() > 0){
				for(Object object : listTop){
					list.add(object+"");
				}
			}
		}
		
		return list;
	}

	@Override
	public Boolean addSeller(Integer orderId, Integer sellerId, String seller) {
				if(orderId != null && sellerId != null && seller != null && !"".equals(seller)){
			OrderInfo orderInfo = orderInfoService.get(orderId);
			orderInfo.setSeller(seller);
			orderInfo.setSellerId(sellerId);
			orderInfo.setState(3);
			orderInfoService.edit(orderInfo);
			return true;
		}
		return false;
	}

	@Override
	public Boolean orderReview(Integer orderId,Integer state) {
		//state == 1 通过，把状态设置为5审核通过,state == 2 不 通过，把状态设置为4审核驳回
		if(orderId != null && state != null){
			OrderInfo orderInfo = orderInfoService.get(orderId);
			if(state == 1){
				orderInfo.setState(5);
			}
			if(state == 2){
				orderInfo.setState(4);
			}
			orderInfoService.edit(orderInfo);
			return true;
		}
		return false;
	}
}
