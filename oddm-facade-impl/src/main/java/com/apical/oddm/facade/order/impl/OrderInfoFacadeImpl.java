package com.apical.oddm.facade.order.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apical.oddm.application.order.OrderFittingServiceI;
import com.apical.oddm.application.order.OrderHardwareServiceI;
import com.apical.oddm.application.order.OrderInfoServiceI;
import com.apical.oddm.application.order.OrderOSServiceI;
import com.apical.oddm.application.order.OrderPackingServiceI;
import com.apical.oddm.application.order.OrderShellServiceI;
import com.apical.oddm.application.order.OrderUnreadServiceI;
import com.apical.oddm.core.constant.OrderInfoConst;
import com.apical.oddm.core.model.document.Document;
import com.apical.oddm.core.model.order.OrderFitting;
import com.apical.oddm.core.model.order.OrderHardware;
import com.apical.oddm.core.model.order.OrderInfo;
import com.apical.oddm.core.model.order.OrderOS;
import com.apical.oddm.core.model.order.OrderPacking;
import com.apical.oddm.core.model.order.OrderShell;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.page.SystemContext;
import com.apical.oddm.core.model.produce.ManufactureShell;
import com.apical.oddm.core.vo.order.OrderInfoAllYearVo;
import com.apical.oddm.core.vo.order.OrderInfoYearVo;
import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.base.dto.BasePage;
import com.apical.oddm.facade.document.dto.DocumentDTO;
import com.apical.oddm.facade.order.OrderInfoAltFacade;
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
import com.apical.oddm.facade.order.dto.OrderInfoYearDto;
import com.apical.oddm.facade.order.dto.OrderOSDTO;
import com.apical.oddm.facade.order.dto.OrderPackingDTO;
import com.apical.oddm.facade.order.dto.OrderShellDTO;
import com.apical.oddm.facade.pool.ThreadPoolBiz;
import com.apical.oddm.facade.util.TimeUtil;

/**
 * @description 类的描述：
 * @author 作者 : zhuzh
 * @date 创建时间：2016年10月31日 下午5:51:25
 * @version 1.0
 */
@Component("orderInfoFacade")
public class OrderInfoFacadeImpl implements OrderInfoFacade {

	private static final Logger log = LoggerFactory.getLogger(OrderInfoFacadeImpl.class);

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

	@Autowired
	private OrderInfoAltFacade orderInfoAltFacade;

	@Autowired
	private OrderUnreadServiceI orderUnreadService;

	@Override
	public OrderInfoDTO getOrderInfoDTO(int id, Boolean hasRoles,Integer currUserId) {
		OrderInfo orderInfo = orderInfoService.getOrderInfo(id);
		if (currUserId != null && currUserId != 0 && orderInfo.getState() >= OrderInfoConst.approved) {
			orderUnreadService.delete(currUserId, id);
			//SystemContext.removeCurrUserId();
		}
		OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
		BeanUtils.copyProperties(orderInfo, orderInfoDTO);
		orderInfoDTO.setDateDelivery(TimeUtil.dateToString(orderInfo.getDateDelivery()));
		orderInfoDTO.setDateExamine(TimeUtil.dateToString(orderInfo.getDateExamine()));
		orderInfoDTO.setDateOrder(TimeUtil.dateToString(orderInfo.getDateOrder()));
		orderInfoDTO.setTimestamp(TimeUtil.timestampToStringDetaile(orderInfo.getTimestamp()));
		if (hasRoles != null && !hasRoles) {
			orderInfoDTO.setClientName("");
			orderInfoDTO.setClientBrand("");
		}
		if (orderInfo.getOrderInfo() != null) {
			orderInfoDTO.setPid(orderInfo.getOrderInfo().getId());
			OrderInfo orderInfoOld = orderInfoService.get(orderInfo.getOrderInfo().getId());
			OrderInfoDTO orderInfoDTOOld = new OrderInfoDTO();
			BeanUtils.copyProperties(orderInfoOld, orderInfoDTOOld);
			orderInfoDTOOld.setOrderNo(orderInfoOld.getOrderNo());
			orderInfoDTO.setOrderInfoDTO(orderInfoDTOOld);
		}

		// 软件信息
		if (orderInfo.getOrderOs() != null) {
			Set<OrderOS> orderOss = orderInfo.getOrderOs();
			OrderOSDTO orderOSDTO = new OrderOSDTO();
			for (OrderOS os : orderOss) {
				BeanUtils.copyProperties(os, orderOSDTO);
			}
			orderInfoDTO.setOrderOSDTO(orderOSDTO);
		}

		// 配件
		if (orderInfo.getOrderFittings() != null) {
			Set<OrderFitting> orderFittings = orderInfo.getOrderFittings();
			Set<OrderFittingDTO> orderFittingDTOs = new TreeSet<OrderFittingDTO>((Comparator<? super OrderFittingDTO>) new Comparator<OrderFittingDTO>() {
				@Override
				public int compare(OrderFittingDTO o1, OrderFittingDTO o2) {
					return o1.getName().compareTo(o2.getName());
				}
			});
			for (OrderFitting orderFitting : orderFittings) {
				OrderFittingDTO orderFittingDTO = new OrderFittingDTO();
				BeanUtils.copyProperties(orderFitting, orderFittingDTO);
				orderFittingDTOs.add(orderFittingDTO);
			}
			orderInfoDTO.setOrderFittingDTOs(orderFittingDTOs);
		}

		// 包材
		if (orderInfo.getOrderPackings() != null) {
			Set<OrderPacking> orderPackings = orderInfo.getOrderPackings();
			Set<OrderPackingDTO> orderPackingDTOs = new TreeSet<OrderPackingDTO>((Comparator<? super OrderPackingDTO>) new Comparator<OrderPackingDTO>() {
				@Override
				public int compare(OrderPackingDTO o1, OrderPackingDTO o2) {
					return o1.getName().compareTo(o2.getName());
				}
			});
			for (OrderPacking orderPacking : orderPackings) {
				OrderPackingDTO orderPackingDTO = new OrderPackingDTO();
				BeanUtils.copyProperties(orderPacking, orderPackingDTO);
				orderPackingDTOs.add(orderPackingDTO);
			}
			orderInfoDTO.setOrderPackingDTOs(orderPackingDTOs);
		}

		// 裸机
		if (orderInfo.getOrderHardwares() != null) {
			Set<OrderHardware> orderHardwares = orderInfo.getOrderHardwares();
			Set<OrderHardwareDTO> orderHardwareDTOs = new TreeSet<OrderHardwareDTO>((Comparator<? super OrderHardwareDTO>) new Comparator<OrderHardwareDTO>() {
				@Override
				public int compare(OrderHardwareDTO o1, OrderHardwareDTO o2) {
					return o1.getName().compareTo(o2.getName());
				}
			});
			for (OrderHardware orderHardware : orderHardwares) {
				OrderHardwareDTO orderHardwareDTO = new OrderHardwareDTO();
				BeanUtils.copyProperties(orderHardware, orderHardwareDTO);
				orderHardwareDTOs.add(orderHardwareDTO);
			}
			orderInfoDTO.setOrderHardwareDTOs(orderHardwareDTOs);
		}

		// 外壳
		if (orderInfo.getOrderShells() != null) {
			Set<OrderShell> orderShells = orderInfo.getOrderShells();
			Set<OrderShellDTO> orderShellDTOs = new TreeSet<OrderShellDTO>((Comparator<? super OrderShellDTO>) new Comparator<OrderShellDTO>() {
				@Override
				public int compare(OrderShellDTO o1, OrderShellDTO o2) {
					return o1.getName().compareTo(o2.getName());
				}
			});
			for (OrderShell orderShell : orderShells) {
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
		if (orderInfo != null) {
			return true;
		}
		return false;
	}

	@Override
	public List<OrderInfoDTO> getAllDocumentByOrderId(Integer orderId) {
		List<OrderInfo> allDocumentByOrderId = orderInfoService.getAllDocumentByOrderId(orderId);
		List<OrderInfoDTO> list = new ArrayList<OrderInfoDTO>();
		if (allDocumentByOrderId != null) {
			for (OrderInfo orderInfo : allDocumentByOrderId) {
				OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
				BeanUtils.copyProperties(orderInfo, orderInfoDTO);
				orderInfoDTO.setDateDelivery(TimeUtil.dateToString(orderInfo.getDateDelivery()));
				orderInfoDTO.setDateExamine(TimeUtil.dateToString(orderInfo.getDateExamine()));
				orderInfoDTO.setDateOrder(TimeUtil.dateToString(orderInfo.getDateOrder()));
				orderInfoDTO.setTimestamp(TimeUtil.timestampToStringDetaile(orderInfo.getTimestamp()));
				if (orderInfo.getDocuments() != null) {
					if (orderInfo.getDocuments().size() > 0) {
						Set<DocumentDTO> documentDTOs = new TreeSet<DocumentDTO>((Comparator<? super DocumentDTO>) new Comparator<DocumentDTO>() {
							@Override
							public int compare(DocumentDTO o1, DocumentDTO o2) {
								return o2.getId().compareTo(o1.getId());
							}
						});
						for (Document document : orderInfo.getDocuments()) {
							DocumentDTO documentDTO = new DocumentDTO();
							BeanUtils.copyProperties(document, documentDTO);
							if (document.getUploadtime() != null) {
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
		return list;
	}

	@Override
	public BasePage<OrderInfoDTO> dataGrid(Set<Integer> states) {
		return null;
	}

	@Override
	public BasePage<OrderInfoDTO> dataGrid(OrderInfoCommand orderInfoCommand, PageCommand pageCommand, Set<Integer> states, Boolean hasRoles) {
		SystemContext.setPageOffset(pageCommand.getPage());
		SystemContext.setPageSize(pageCommand.getRows());
		SystemContext.setOrder(pageCommand.getOrder());
		SystemContext.setSort(pageCommand.getSort());
		Pager<OrderInfo> dataGrid = null;
		OrderInfo orderInfoquery = new OrderInfo();
		orderInfoquery.setUnread(orderInfoCommand.getIsRead());
		BeanUtils.copyProperties(orderInfoCommand, orderInfoquery);
		dataGrid = orderInfoService.dataGrid(orderInfoquery, states);
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
	public BasePage<OrderInfoDTO> dataGrid(Date startDate, Date endDate, PageCommand pageCommand) {
		return null;
	}

	@Override
	public Boolean add(OrderInfoCommand orderInfoCommand) {
		// System.out.println(".add......"+orderInfoCommand);
		OrderInfo orderInfo = new OrderInfo();
		BeanUtils.copyProperties(orderInfoCommand, orderInfo);
		// System.out.println("....order."+orderInfo);
		orderInfo.setDateDelivery(TimeUtil.datToDate(orderInfoCommand.getDateDelivery()));
		orderInfo.setDateExamine(TimeUtil.datToDate(orderInfoCommand.getDateExamine()));
		orderInfo.setDateOrder(TimeUtil.datToDate(orderInfoCommand.getDateOrder()));
		// 软件信息
		OrderOS orderOS = new OrderOS();
		OrderOSCommand orderOSCommand = orderInfoCommand.getOrderOSCommand();
		orderOSCommand.setIsrepeat(1);
		BeanUtils.copyProperties(orderOSCommand, orderOS);
		orderOS.setOrderInfo(orderInfo);
		Set<OrderOS> orderOsSet = new HashSet<OrderOS>();
		orderOsSet.add(orderOS);
		orderInfo.setOrderOs(orderOsSet);

		// 定单硬件物料
		Set<OrderHardwareCommand> orderHardwareCommands = orderInfoCommand.getOrderHardwareCommands();
		Set<OrderHardware> orderHardwares = new HashSet<OrderHardware>();
		for (OrderHardwareCommand orderHardwareCommand : orderHardwareCommands) {
			OrderHardware orderHardware = new OrderHardware();
			BeanUtils.copyProperties(orderHardwareCommand, orderHardware);
			orderHardware.setIsNew(2);
			orderHardware.setOrderInfo(orderInfo);
			orderHardwares.add(orderHardware);
		}
		orderInfo.setOrderHardwares(orderHardwares);

		// 外壳
		Set<OrderShellCommand> orderShellCommands = orderInfoCommand.getOrderShellCommands();
		Set<OrderShell> orderShells = new HashSet<OrderShell>();
		for (OrderShellCommand orderShellCommand : orderShellCommands) {
			OrderShell orderShell = new OrderShell();
			BeanUtils.copyProperties(orderShellCommand, orderShell);
			orderShell.setIsNew(2);
			orderShell.setOrderInfo(orderInfo);
			orderShells.add(orderShell);
		}
		orderInfo.setOrderShells(orderShells);

		// 包材
		Set<OrderPackingCommand> orderPackingCommands = orderInfoCommand.getOrderPackingCommands();
		Set<OrderPacking> orderPackings = new HashSet<OrderPacking>();
		for (OrderPackingCommand orderPackingCommand : orderPackingCommands) {
			OrderPacking orderPacking = new OrderPacking();
			BeanUtils.copyProperties(orderPackingCommand, orderPacking);
			orderPacking.setIsNew(2);
			orderPacking.setOrderInfo(orderInfo);
			orderPackings.add(orderPacking);
		}
		orderInfo.setOrderPackings(orderPackings);

		// 配件
		Set<OrderFittingCommand> orderFittingCommands = orderInfoCommand.getOrderFittingCommands();
		Set<OrderFitting> orderFittings = new HashSet<OrderFitting>();
		for (OrderFittingCommand orderFittingCommand : orderFittingCommands) {
			OrderFitting orderFitting = new OrderFitting();
			BeanUtils.copyProperties(orderFittingCommand, orderFitting);
			orderFitting.setIsNew(2);
			orderFitting.setOrderInfo(orderInfo);
			orderFittings.add(orderFitting);
		}
		orderInfo.setOrderFittings(orderFittings);

		// 用户
		/*
		 * User user = new User(); user.setId(orderInfoCommand.getUserId()); orderInfo.setUser(user);
		 */
		// 更新订单状态
		orderInfo.setState(OrderInfoConst.save);
		Serializable add = orderInfoService.add(orderInfo);
		if (add != null) {
		/*	try {
				orderInfoService.addOtherEntity((Integer) add);
			} catch (Exception e) {
				log.error("触发生成其他对象异常", e);
			}*/
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void edit(OrderInfoCommand orderInfoCommand, Integer currUserId) {
		// System.out.println(".edit......" + orderInfoCommand);
		OrderInfo orderInfo = orderInfoService.getOrderInfo(orderInfoCommand.getId());

		// 计算变更记录用到
		OrderInfo orderInfo1 = null;
		if (orderInfo.getState() >= OrderInfoConst.approved || orderInfo.getPid() != null) {// 状态审核通过前修改订单不计算变更记录,父id不为null则是翻单，要生成变更记录
			orderInfo1 = orderInfoService.getOrderInfo(orderInfoCommand.getId());
		}

		BeanUtils.copyProperties(orderInfoCommand, orderInfo);
		if (!StringUtils.equals(TimeUtil.dayFmat.format(orderInfoCommand.getDateDelivery()), TimeUtil.dayFmat.format(orderInfoCommand.getDateDelivery())))
			orderInfo.setDateDelivery(TimeUtil.datToDate(orderInfoCommand.getDateDelivery()));
		if (!StringUtils.equals(TimeUtil.dayFmat.format(orderInfoCommand.getDateExamine()), TimeUtil.dayFmat.format(orderInfoCommand.getDateExamine())))
			orderInfo.setDateExamine(TimeUtil.datToDate(orderInfoCommand.getDateExamine()));
		if (!StringUtils.equals(TimeUtil.dayFmat.format(orderInfoCommand.getDateOrder()), TimeUtil.dayFmat.format(orderInfoCommand.getDateOrder())))
			orderInfo.setDateOrder(TimeUtil.datToDate(orderInfoCommand.getDateOrder()));
		// 软件信息
		if (orderInfoCommand.getOrderOSCommand() != null) {
			Set<OrderOS> orderOsSet = orderInfo.getOrderOs();
			OrderOS orderOS = null;
			if (orderOsSet == null) {
				orderOsSet = new HashSet<OrderOS>();
				orderOS = new OrderOS();
				orderOS.setOrderInfo(orderInfo);
				orderOsSet.add(orderOS);
				orderInfo.setOrderOs(orderOsSet);
			} else {
				if (!orderOsSet.isEmpty()) {
					orderOS = orderOsSet.iterator().next();
				} else {
					orderOS = new OrderOS();
					orderOS.setOrderInfo(orderInfo);
					orderOsSet.add(orderOS);
				}
			}
			OrderOSCommand orderOSCommand = orderInfoCommand.getOrderOSCommand();
			BeanUtils.copyProperties(orderOSCommand, orderOS);
		}

		// 定单硬件物料
		if (orderInfoCommand.getOrderHardwareCommands() != null && !orderInfoCommand.getOrderHardwareCommands().isEmpty()) {
			Set<OrderHardwareCommand> orderHardwareCommands = orderInfoCommand.getOrderHardwareCommands();
			Set<OrderHardware> orderHardwares = orderInfo.getOrderHardwares();//
			if (orderHardwares == null) {
				orderHardwares = new HashSet<OrderHardware>();
				orderInfo.setOrderHardwares(orderHardwares);
			}
			Map<Integer, OrderHardware> tempMap = new HashMap<>();
			for (OrderHardware orderHardware : orderHardwares) {
				tempMap.put(orderHardware.getId(), orderHardware);
			}
			for (OrderHardwareCommand orderHardwareCommand : orderHardwareCommands) {
				if (orderHardwareCommand.getId() == null) {// 增加
					OrderHardware orderHardware = new OrderHardware();
					BeanUtils.copyProperties(orderHardwareCommand, orderHardware);
					orderHardware.setOrderInfo(orderInfo);
					orderHardwares.add(orderHardware);
				} else {// 删除或者修改
					if (tempMap.get(orderHardwareCommand.getId()) != null) {// 修改操作
						BeanUtils.copyProperties(orderHardwareCommand, tempMap.get(orderHardwareCommand.getId()));
						tempMap.remove(orderHardwareCommand.getId());// 移除掉修改的，剩下的就应该删除操作
					} else {// 增加，多人操作可能会导致：A先查出，B删除，A再插入。
						OrderHardware orderHardware = new OrderHardware();
						BeanUtils.copyProperties(orderHardwareCommand, orderHardware);
						orderHardware.setOrderInfo(orderInfo);
						orderHardwares.add(orderHardware);
					}
				}
				// System.err.println("定单硬件物料"+orderHardwareCommand.getName());
			}
			// System.err.println("定单硬件物料size"+orderHardwareCommands.size());
			for (OrderHardware h : tempMap.values()) {// 删除操作，级联删除起作用的前提是关联的集合对象不能又一次指向新的引用，必须在原有的集合里操作新增、删除、清空元素，像上面的setXXX(null)的方法等是起步到级联删除作用的，大概是Hibernate自认自己原生的集合对象吧。自己New的放进行级联删除无效！
				orderHardwares.remove(h);
			}
			/*
			 * for (Integer h : tempMap.keySet()) {//删除操作，但是orderHardwares的还存在，则又有重新增加 orderHardwareService.delete(h); System.out.println("删除 "+tempMap.get
			 * (h).getId()+"\t"+tempMap.get(h).getName()); }
			 */
		}else{
			Set<OrderHardware> orderHardwares = orderInfo.getOrderHardwares();//
			if(orderHardwares != null && orderHardwares.size() > 0){
				orderHardwares.clear();
			}
		}

		// 外壳
		if (orderInfoCommand.getOrderShellCommands() != null && !orderInfoCommand.getOrderShellCommands().isEmpty()) {
			Set<OrderShellCommand> orderShellCommands = orderInfoCommand.getOrderShellCommands();
			Set<OrderShell> orderShells = orderInfo.getOrderShells();//
			if (orderShells == null) {
				orderShells = new HashSet<OrderShell>();
				orderInfo.setOrderShells(orderShells);
			}
			Map<Integer, OrderShell> tempMap = new HashMap<>();
			for (OrderShell orderShell : orderShells) {
				tempMap.put(orderShell.getId(), orderShell);
			}
			for (OrderShellCommand orderShellCommand : orderShellCommands) {
				if (orderShellCommand.getId() == null) {// 增加
					OrderShell orderShell = new OrderShell();
					BeanUtils.copyProperties(orderShellCommand, orderShell);
					orderShell.setOrderInfo(orderInfo);
					orderShells.add(orderShell);
				} else {// 删除或者修改
					if (tempMap.get(orderShellCommand.getId()) != null) {// 修改操作
						BeanUtils.copyProperties(orderShellCommand, tempMap.get(orderShellCommand.getId()));
						tempMap.remove(orderShellCommand.getId());// 移除掉修改的，剩下的就应该删除操作
					} else {// 增加，多人操作可能会导致：A先查出，B删除，A再插入。
						OrderShell orderShell = new OrderShell();
						BeanUtils.copyProperties(orderShellCommand, orderShell);
						orderShell.setOrderInfo(orderInfo);
						orderShells.add(orderShell);
					}
				}
			}
			for (OrderShell h : tempMap.values()) {// 删除操作，级联删除起作用的前提是关联的集合对象不能又一次指向新的引用，必须在原有的集合里操作新增、删除、清空元素，像上面的setXXX(null)的方法等是起步到级联删除作用的，大概是Hibernate自认自己原生的集合对象吧。自己New的放进行级联删除无效！
				orderShells.remove(h);
			}
		}else{
			Set<OrderShell> orderShells = orderInfo.getOrderShells();//
			if(orderShells != null && orderShells.size() > 0){
				orderShells.clear();
			}
		}

		// 包材
		if (orderInfoCommand.getOrderPackingCommands() != null && !orderInfoCommand.getOrderPackingCommands().isEmpty()) {
			Set<OrderPackingCommand> orderPackingCommands = orderInfoCommand.getOrderPackingCommands();
			Set<OrderPacking> orderPackings = orderInfo.getOrderPackings();//
			if (orderPackings == null) {
				orderPackings = new HashSet<OrderPacking>();
				orderInfo.setOrderPackings(orderPackings);
			}
			Map<Integer, OrderPacking> tempMap = new HashMap<>();
			for (OrderPacking orderPacking : orderPackings) {
				tempMap.put(orderPacking.getId(), orderPacking);
			}
			for (OrderPackingCommand orderPackingCommand : orderPackingCommands) {
				if (orderPackingCommand.getId() == null) {// 增加
					OrderPacking orderPacking = new OrderPacking();
					BeanUtils.copyProperties(orderPackingCommand, orderPacking);
					orderPacking.setOrderInfo(orderInfo);
					orderPackings.add(orderPacking);
				} else {// 删除或者修改
					if (tempMap.get(orderPackingCommand.getId()) != null) {// 修改操作
						BeanUtils.copyProperties(orderPackingCommand, tempMap.get(orderPackingCommand.getId()));
						tempMap.remove(orderPackingCommand.getId());// 移除掉修改的，剩下的就应该删除操作
					} else {// 增加，多人操作可能会导致：A先查出，B删除，A再插入。
						OrderPacking orderPacking = new OrderPacking();
						BeanUtils.copyProperties(orderPackingCommand, orderPacking);
						orderPacking.setOrderInfo(orderInfo);
						orderPackings.add(orderPacking);
					}
				}
			}
			for (OrderPacking h : tempMap.values()) {// 删除操作，级联删除起作用的前提是关联的集合对象不能又一次指向新的引用，必须在原有的集合里操作新增、删除、清空元素，像上面的setXXX(null)的方法等是起步到级联删除作用的，大概是Hibernate自认自己原生的集合对象吧。自己New的放进行级联删除无效！
				orderPackings.remove(h);
			}
		}else{
			Set<OrderPacking> orderPackings = orderInfo.getOrderPackings();//
			if(orderPackings != null && orderPackings.size() > 0){
				orderPackings.clear();
			}
		}

		// 配件
		if (orderInfoCommand.getOrderFittingCommands() != null && !orderInfoCommand.getOrderFittingCommands().isEmpty()) {
			Set<OrderFittingCommand> orderFittingCommands = orderInfoCommand.getOrderFittingCommands();
			Set<OrderFitting> orderFittings = orderInfo.getOrderFittings();//
			if (orderFittings == null) {
				orderFittings = new HashSet<OrderFitting>();
				orderInfo.setOrderFittings(orderFittings);
			}
			Map<Integer, OrderFitting> tempMap = new HashMap<>();
			for (OrderFitting orderHardware : orderFittings) {
				tempMap.put(orderHardware.getId(), orderHardware);
			}
			for (OrderFittingCommand orderFittingCommand : orderFittingCommands) {
				if (orderFittingCommand.getId() == null) {// 增加
					OrderFitting orderHardware = new OrderFitting();
					BeanUtils.copyProperties(orderFittingCommand, orderHardware);
					orderHardware.setOrderInfo(orderInfo);
					orderFittings.add(orderHardware);
				} else {// 删除或者修改
					if (tempMap.get(orderFittingCommand.getId()) != null) {// 修改操作
						BeanUtils.copyProperties(orderFittingCommand, tempMap.get(orderFittingCommand.getId()));
						tempMap.remove(orderFittingCommand.getId());// 移除掉修改的，剩下的就应该删除操作
					} else {// 增加，多人操作可能会导致：A先查出，B删除，A再插入。
						OrderFitting orderHardware = new OrderFitting();
						BeanUtils.copyProperties(orderFittingCommand, orderHardware);
						orderHardware.setOrderInfo(orderInfo);
						orderFittings.add(orderHardware);
					}
				}
			}
			for (OrderFitting h : tempMap.values()) {// 删除操作，级联删除起作用的前提是关联的集合对象不能又一次指向新的引用，必须在原有的集合里操作新增、删除、清空元素，像上面的setXXX(null)的方法等是起步到级联删除作用的，大概是Hibernate自认自己原生的集合对象吧。自己New的放进行级联删除无效！
				orderFittings.remove(h);
			}
		}else{
			Set<OrderFitting> orderFittings = orderInfo.getOrderFittings();//
			if(orderFittings != null && orderFittings.size() > 0){
				orderFittings.clear();
			}
		}
		orderInfoService.edit(orderInfo);
		// 变更记录计算,得放在最后，否则添加失败就麻烦了
		orderInfoAltFacade.addEditRecord(orderInfo1, orderInfoCommand, currUserId);
	}

	@Override
	public OrderInfoDTO getByProductFactory(String productFactory) {
		OrderInfo orderInfo = orderInfoService.getByProductFactory(productFactory);
		OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
		if (orderInfo != null) {
			BeanUtils.copyProperties(orderInfo, orderInfoDTO);
			orderInfoDTO.setDateDelivery(TimeUtil.dateToString(orderInfo.getDateDelivery()));
			orderInfoDTO.setDateExamine(TimeUtil.dateToString(orderInfo.getDateExamine()));
			orderInfoDTO.setDateOrder(TimeUtil.dateToString(orderInfo.getDateOrder()));
			orderInfoDTO.setTimestamp(TimeUtil.timestampToStringDetaile(orderInfo.getTimestamp()));
			/*
			 * if (orderInfo.getOrderInfo() != null) { orderInfoDTO.setPid(orderInfo.getOrderInfo().getId()); OrderInfo orderInfoOld = orderInfoService.get(orderInfo
			 * .getOrderInfo().getId()); OrderInfoDTO orderInfoDTOOld = new OrderInfoDTO(); BeanUtils.copyProperties(orderInfoOld, orderInfoDTOOld);
			 * orderInfoDTOOld.setOrderNo(orderInfoOld.getOrderNo()); orderInfoDTO.setOrderInfoDTO(orderInfoDTOOld); }
			 */

			// 软件信息
			if (orderInfo.getOrderOs() != null) {
				Set<OrderOS> orderOss = orderInfo.getOrderOs();
				OrderOSDTO orderOSDTO = new OrderOSDTO();
				for (OrderOS os : orderOss) {
					BeanUtils.copyProperties(os, orderOSDTO);
				}
				orderInfoDTO.setOrderOSDTO(orderOSDTO);
			}

			// 用户
			/*
			 * if (orderInfo.getUser() != null){ User user = orderInfo.getUser(); UserDTO userDTO = new UserDTO(); BeanUtils.copyProperties(user, userDTO);
			 * orderInfoDTO.setUserDTO(userDTO); }
			 */

			// 配件
			if (orderInfo.getOrderFittings() != null) {
				Set<OrderFitting> orderFittings = orderInfo.getOrderFittings();
				Set<OrderFittingDTO> orderFittingDTOs = new TreeSet<OrderFittingDTO>((Comparator<? super OrderFittingDTO>) new Comparator<OrderFittingDTO>() {
					@Override
					public int compare(OrderFittingDTO o1, OrderFittingDTO o2) {
						return o1.getName().compareTo(o2.getName());
					}
				});
				for (OrderFitting orderFitting : orderFittings) {
					OrderFittingDTO orderFittingDTO = new OrderFittingDTO();
					BeanUtils.copyProperties(orderFitting, orderFittingDTO);
					orderFittingDTOs.add(orderFittingDTO);
				}
				orderInfoDTO.setOrderFittingDTOs(orderFittingDTOs);
			}

			// 包材
			if (orderInfo.getOrderPackings() != null) {
				Set<OrderPacking> orderPackings = orderInfo.getOrderPackings();
				Set<OrderPackingDTO> orderPackingDTOs = new TreeSet<OrderPackingDTO>((Comparator<? super OrderPackingDTO>) new Comparator<OrderPackingDTO>() {
					@Override
					public int compare(OrderPackingDTO o1, OrderPackingDTO o2) {
						return o1.getName().compareTo(o2.getName());
					}
				});
				for (OrderPacking orderPacking : orderPackings) {
					OrderPackingDTO orderPackingDTO = new OrderPackingDTO();
					BeanUtils.copyProperties(orderPacking, orderPackingDTO);
					orderPackingDTOs.add(orderPackingDTO);
				}
				orderInfoDTO.setOrderPackingDTOs(orderPackingDTOs);
			}

			// 裸机
			if (orderInfo.getOrderHardwares() != null) {
				Set<OrderHardware> orderHardwares = orderInfo.getOrderHardwares();
				Set<OrderHardwareDTO> orderHardwareDTOs = new TreeSet<OrderHardwareDTO>((Comparator<? super OrderHardwareDTO>) new Comparator<OrderHardwareDTO>() {
					@Override
					public int compare(OrderHardwareDTO o1, OrderHardwareDTO o2) {
						return o1.getName().compareTo(o2.getName());
					}
				});
				for (OrderHardware orderHardware : orderHardwares) {
					OrderHardwareDTO orderHardwareDTO = new OrderHardwareDTO();
					BeanUtils.copyProperties(orderHardware, orderHardwareDTO);
					orderHardwareDTOs.add(orderHardwareDTO);
				}
				orderInfoDTO.setOrderHardwareDTOs(orderHardwareDTOs);
			}

			// 外壳
			if (orderInfo.getOrderShells() != null) {
				Set<OrderShell> orderShells = orderInfo.getOrderShells();
				Set<OrderShellDTO> orderShellDTOs = new TreeSet<OrderShellDTO>((Comparator<? super OrderShellDTO>) new Comparator<OrderShellDTO>() {
					@Override
					public int compare(OrderShellDTO o1, OrderShellDTO o2) {
						return o1.getName().compareTo(o2.getName());
					}
				});
				for (OrderShell orderShell : orderShells) {
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
	public OrderInfoDTO getBaseOrderInfoDTO(int id, Boolean hasRoles) {
		OrderInfo orderInfo = orderInfoService.get(id);
		OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
		BeanUtils.copyProperties(orderInfo, orderInfoDTO);
		if (!hasRoles) {
			orderInfoDTO.setClientName("");
			orderInfoDTO.setClientBrand("");
		}
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
		if (clientName != null) {
			if ("".equals(clientName)) {
				clientName = "";
			}
			orderInfo.setClientName(clientName);
			List<Object> listTop = orderInfoService.listTop(orderInfo);
			if (listTop != null && !listTop.isEmpty()) {
				for (Object object : listTop) {
					list.add(object + "");
				}
			}
		}

		return list;
	}

	@Override
	public List<String> listTopClientBrand(String clientBrand) {
		
		OrderInfo orderInfo = new OrderInfo();
		List<String> list = new ArrayList<String>();
		if (clientBrand != null) {
			if ("".equals(clientBrand)) {
				clientBrand = "";
			}
			orderInfo.setClientBrand(clientBrand);
			List<Object> listTop = orderInfoService.listTop(orderInfo);
			if (listTop != null && !listTop.isEmpty()) {
				for (Object object : listTop) {
					list.add(object + "");
				}
			}
		}

		return list;
	}

	@Override
	public List<String> listTopDistrict(String district) {
		OrderInfo orderInfo = new OrderInfo();
		List<String> list = new ArrayList<String>();
		if (district != null) {
			if ("".equals(district)) {
				district = "";
			}
			orderInfo.setDistrict(district);
			List<Object> listTop = orderInfoService.listTop(orderInfo);
			if (listTop != null && !listTop.isEmpty()) {
				for (Object object : listTop) {
					list.add(object + "");
				}
			}
		}

		return list;
	}

	@Override
	public List<String> listTopGui(String gui) {
		OrderOS orderOS = new OrderOS();
		List<String> list = new ArrayList<String>();
		if (gui != null) {
			if ("".equals(gui)) {
				gui = "";
			}
			orderOS.setGui(gui);
			List<Object> listTop = orderOSService.listTop(orderOS);
			if (listTop != null && !listTop.isEmpty()) {
				for (Object object : listTop) {
					list.add(object + "");
				}
			}
		}

		return list;
	}

	@Override
	public List<String> listTopLangOs(String langOs) {
		OrderOS orderOS = new OrderOS();
		List<String> list = new ArrayList<String>();
		if (langOs != null) {
			if ("".equals(langOs)) {
				langOs = "";
			}
			orderOS.setLangOs(langOs);
			List<Object> listTop = orderOSService.listTop(orderOS);
			if (listTop != null && !listTop.isEmpty()) {
				for (Object object : listTop) {
					list.add(object + "");
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
		if (langDefault != null) {
			if ("".equals(langDefault)) {
				langDefault = "";
			}
			orderOS.setLangDefault(langDefault);
			List<Object> listTop = orderOSService.listTop(orderOS);
			if (listTop != null && !listTop.isEmpty()) {
				for (Object object : listTop) {
					list.add(object + "");
				}
			}
		}

		return list;
	}

	@Override
	public List<String> listTopTimezone(String timezone) {
		OrderOS orderOS = new OrderOS();
		List<String> list = new ArrayList<String>();
		if (timezone != null) {
			if ("".equals(timezone)) {
				timezone = "";
			}
			// System.out.println(timezone);
			orderOS.setTimezone(timezone);
			// orderOS.setTimezone("GMT+7");
			List<Object> listTop = orderOSService.listTop(orderOS);
			if (listTop != null && !listTop.isEmpty()) {
				for (Object object : listTop) {
					list.add(object + "");
				}
			}
		}

		return list;
	}

	@Override
	public Boolean addSeller(Integer orderId, Integer sellerId, String seller) {
		if (orderId != null && sellerId != null && seller != null && !"".equals(seller)) {
			OrderInfo orderInfo = orderInfoService.get(orderId);
			orderInfo.setSeller(seller);
			orderInfo.setSellerId(sellerId);
			orderInfo.setState(OrderInfoConst.submitaudit);
			orderInfoService.edit(orderInfo);
			return true;
		}
		return false;
	}

	@Override
	public Boolean orderReview(Integer orderId, Integer state, Integer currUserId) {
		// state == 1 通过，把状态设置为5审核通过,state == 2 不 通过，把状态设置为4审核驳回
		if (orderId != null && state != null) {
			OrderInfo orderInfo = orderInfoService.get(orderId);
			if (state == 1) {
				orderInfo.setState(OrderInfoConst.approved);
			}
			if (state == 2) {
				orderInfo.setState(OrderInfoConst.rejected);
			}
			orderInfoService.edit(orderInfo);
			if (orderInfo.getState() == OrderInfoConst.approved) {// 务必放在最后，现在增加成功在调用
				orderInfoAltFacade.addOrderUnreadInfo(orderInfo.getOrderNo(), currUserId == null ? 0 : currUserId, orderId, 1);// 给其他拥有订单菜单权限的用户增加订单未读信息
				
				ThreadPoolBiz.getInstance().execute(new Runnable() {
					@Override
					public void run() {
						try {//触发生成其他记录
							orderInfoService.addOtherEntity(orderId);
						} catch (Exception e) {
							log.error("触发生成其他对象异常", e);
						}
					}
					
				});
			}
			return true;
		}
		return false;
	}

	@Override
	public Boolean addNewOrder(OrderInfoCommand orderInfoCommand, Integer currUserId) {
		// System.out.println(".add......"+orderInfoCommand);
		OrderInfo orderInfo = new OrderInfo();
		BeanUtils.copyProperties(orderInfoCommand, orderInfo);
		// System.out.println("....order."+orderInfo);
		orderInfo.setDateDelivery(TimeUtil.datToDate(orderInfoCommand.getDateDelivery()));
		orderInfo.setDateExamine(TimeUtil.datToDate(orderInfoCommand.getDateExamine()));
		orderInfo.setDateOrder(TimeUtil.datToDate(orderInfoCommand.getDateOrder()));
		// 软件信息
		OrderOS orderOS = new OrderOS();
		OrderOSCommand orderOSCommand = orderInfoCommand.getOrderOSCommand();
		OrderInfo reOrderInfo = null;
		if (orderInfoCommand.getPid() != null) { // 说明是翻单
			reOrderInfo = orderInfoService.getOrderInfo(orderInfoCommand.getPid());//翻单无论是否审核过，都得记录变更记录
			orderInfo.setOrderInfo(new OrderInfo(orderInfoCommand.getPid()));
			// 新软件直接设置2
			/*orderOSCommand.setIsrepeat(2);*/
		} else {
			/*orderOSCommand.setIsrepeat(1);*/
		}
		BeanUtils.copyProperties(orderOSCommand, orderOS);
		orderOS.setOrderInfo(orderInfo);
		Set<OrderOS> orderOsSet = new HashSet<OrderOS>();
		orderOsSet.add(orderOS);
		orderInfo.setOrderOs(orderOsSet);

		// 定单硬件物料
		Set<OrderHardwareCommand> orderHardwareCommands = orderInfoCommand.getOrderHardwareCommands();
		Set<OrderHardware> orderHardwares = new HashSet<OrderHardware>();
		for (OrderHardwareCommand orderHardwareCommand : orderHardwareCommands) {
			OrderHardware orderHardware = new OrderHardware();
			BeanUtils.copyProperties(orderHardwareCommand, orderHardware);
			orderHardware.setOrderInfo(orderInfo);
			orderHardwares.add(orderHardware);
		}
		orderInfo.setOrderHardwares(orderHardwares);

		// 外壳
		Set<OrderShellCommand> orderShellCommands = orderInfoCommand.getOrderShellCommands();
		Set<OrderShell> orderShells = new HashSet<OrderShell>();
		for (OrderShellCommand orderShellCommand : orderShellCommands) {
			OrderShell orderShell = new OrderShell();
			BeanUtils.copyProperties(orderShellCommand, orderShell);
			orderShell.setOrderInfo(orderInfo);
			orderShells.add(orderShell);
		}
		orderInfo.setOrderShells(orderShells);

		// 包材
		Set<OrderPackingCommand> orderPackingCommands = orderInfoCommand.getOrderPackingCommands();
		Set<OrderPacking> orderPackings = new HashSet<OrderPacking>();
		for (OrderPackingCommand orderPackingCommand : orderPackingCommands) {
			OrderPacking orderPacking = new OrderPacking();
			BeanUtils.copyProperties(orderPackingCommand, orderPacking);
			orderPacking.setOrderInfo(orderInfo);
			orderPackings.add(orderPacking);
		}
		orderInfo.setOrderPackings(orderPackings);

		// 配件
		Set<OrderFittingCommand> orderFittingCommands = orderInfoCommand.getOrderFittingCommands();
		Set<OrderFitting> orderFittings = new HashSet<OrderFitting>();
		for (OrderFittingCommand orderFittingCommand : orderFittingCommands) {
			OrderFitting orderFitting = new OrderFitting();
			BeanUtils.copyProperties(orderFittingCommand, orderFitting);
			orderFitting.setOrderInfo(orderInfo);
			orderFittings.add(orderFitting);
		}
		orderInfo.setOrderFittings(orderFittings);

		// 用户
		/*
		 * User user = new User(); user.setId(orderInfoCommand.getUserId()); orderInfo.setUser(user);
		 */
		// 更新订单状态
		orderInfo.setState(2);
		Serializable add = orderInfoService.add(orderInfo);
		if (add != null) {
			/*try {
				orderInfoService.addOtherEntity((Integer) add);
			} catch (Exception e) {
				log.error("触发生成其他对象异常", e);
			}*/
			reOrderInfo.setId((Integer)add);//如果不设置，则生成的变更记录会在前一单
			orderInfoAltFacade.addAddRecord(reOrderInfo, orderInfoCommand, currUserId);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void changeMerchandiser(OrderInfoCommand orderInfoCommand) {
		
		if (orderInfoCommand != null) {
			if (orderInfoCommand.getId() != null && orderInfoCommand.getMerchandiserId() != null && orderInfoCommand.getMerchandiserId() != null) {
				OrderInfo orderInfo = orderInfoService.get(orderInfoCommand.getId());
				if (orderInfo != null) {
					orderInfo.setMerchandiser(orderInfoCommand.getMerchandiser());
					orderInfo.setMerchandiserId(orderInfoCommand.getMerchandiserId());
				}
				orderInfoService.edit(orderInfo);
			}
		}
	}

	@Override
	public void changeSeller(OrderInfoCommand orderInfoCommand) {
		
		if (orderInfoCommand != null) {
			if (orderInfoCommand.getId() != null && orderInfoCommand.getSellerId() != null && orderInfoCommand.getSellerId() != null) {
				OrderInfo orderInfo = orderInfoService.get(orderInfoCommand.getId());
				if (orderInfo != null) {
					orderInfo.setSeller(orderInfoCommand.getSeller());
					orderInfo.setSellerId(orderInfoCommand.getSellerId());
				}
				orderInfoService.edit(orderInfo);
			}
		}
	}

	@Override
	public void delete(Integer id) {
		
		if(id != null){
			orderInfoService.delete(id);
		}
	}

	@Override
	public BasePage<OrderInfoDTO> dataGridStatistics(
			OrderInfoCommand orderInfoCommand, PageCommand pageCommand,
			Set<Integer> states) {
		SystemContext.setPageOffset(pageCommand.getPage());
		SystemContext.setPageSize(pageCommand.getRows());
		SystemContext.setOrder(pageCommand.getOrder());
		SystemContext.setSort(pageCommand.getSort());
		Pager<OrderInfo> dataGrid = null;
		OrderInfo orderInfoquery = new OrderInfo();
		BeanUtils.copyProperties(orderInfoCommand, orderInfoquery);
		dataGrid = orderInfoService.dataGridStatistics(orderInfoquery, states);
		BasePage<OrderInfoDTO> basePage = new BasePage<OrderInfoDTO>();
		if (dataGrid.getTotal() != 0) {
			if (dataGrid.getRows().size() > 0) {
				List<OrderInfoDTO> list = new ArrayList<OrderInfoDTO>();
				// System.out.println("查看订单----角色："+hasRoles);
				Calendar calendar = Calendar.getInstance();
				calendar.setFirstDayOfWeek(Calendar.MONDAY);//设置星期一为一周的第一天。
				for (OrderInfo orderInfo : dataGrid.getRows()) {
					OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
					BeanUtils.copyProperties(orderInfo, orderInfoDTO);
					orderInfoDTO.setIsRead(orderInfo.getUnread());

					if (orderInfo.getDateDelivery() != null) {
						orderInfoDTO.setDateDelivery(TimeUtil.dateToString(orderInfo.getDateDelivery()));
					}
					if (orderInfo.getDateExamine() != null) {
						orderInfoDTO.setDateExamine(TimeUtil.dateToString(orderInfo.getDateExamine()));
					}
					if (orderInfo.getDateOrder() != null) {
						orderInfoDTO.setDateOrder(TimeUtil.dateToString(orderInfo.getDateOrder()));
						calendar.setTime(orderInfo.getDateOrder());
						int weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH);
						orderInfoDTO.setDateOrderWeek("第"+weekOfMonth+"周");
					}
					if (orderInfo.getUpdatetime() != null) {
						orderInfoDTO.setUpdatetime(TimeUtil.timestampToStringDetaile(orderInfo.getUpdatetime()));
					}

					// orderInfoDTO.setTimestamp(TimeUtil.timestampToStringDetaile(orderInfo.getTimestamp()));

					list.add(orderInfoDTO);
				}
				basePage.setTotal(dataGrid.getTotal());
				basePage.setRows(list);
			}
		}
		return basePage;
	}

	/*
	 * List<OrderInfoYearVo> 原始数据格式
	 * month  seller  qauntitys
	 * 1           徐细梅        541
	 * 1	   李晓磊        1700
	 * 2 	   徐细梅        222
	 * 2	   李晓磊        432
	 * 3	   徐细梅        2456
	 * 3	   李晓磊        3330
	 * */
	@Override
	public List<OrderInfoYearDto> getOrderInfoYearSellerVoList(int type, String year) {
		List<OrderInfoYearVo> voList = orderInfoService.getOrderInfoYearVoList(type, year);
		//long startTime = System.currentTimeMillis();
		if (voList == null) return null;
		List<OrderInfoYearDto> dtoList = new LinkedList<OrderInfoYearDto>();
		HashMap<String, Map<Integer, Integer>> sellerMap = new HashMap<String, Map<Integer, Integer>>();
		HashMap<Integer, Integer> monthMap = null;
		int totals = 0;
		for (OrderInfoYearVo vo : voList) {
			if (vo.getQuantitys() != null) totals += vo.getQuantitys();
			if (sellerMap.get(vo.getName()) == null) {
				monthMap = new HashMap<Integer, Integer>();
				sellerMap.put(vo.getName(), monthMap);
			}
			sellerMap.get(vo.getName()).put(vo.getMonth(), vo.getQuantitys());
		}
		OrderInfoYearDto dto = null;
		int total = 0 ;
		int value = 0;
		Map<Integer, Integer> tempMap = null;
		for (Entry<String, Map<Integer, Integer>> entry : sellerMap.entrySet()) {
			//System.out.println("key= " + entry.getKey() + "and  value= "+ entry.getValue());
			dto = new OrderInfoYearDto();
			dto.setName(entry.getKey());
			tempMap = entry.getValue();
			value = tempMap.get(1)==null?0:tempMap.get(1);
			total = 0;
			total += value;
			dto.setQuantitysM1(value);
			value = tempMap.get(2)==null?0:tempMap.get(2);
			total += value;
			dto.setQuantitysM2(value);
			value = tempMap.get(3)==null?0:tempMap.get(3);
			total += value;
			dto.setQuantitysM3(value);
			value = tempMap.get(4)==null?0:tempMap.get(4);
			total += value;
			dto.setQuantitysM4(value);
			value = tempMap.get(5)==null?0:tempMap.get(5);
			total += value;
			dto.setQuantitysM5(value);
			value = tempMap.get(6)==null?0:tempMap.get(6);
			total += value;
			dto.setQuantitysM6(value);
			value = tempMap.get(7)==null?0:tempMap.get(7);
			total += value;
			dto.setQuantitysM7(value);
			value = tempMap.get(8)==null?0:tempMap.get(8);
			total += value;
			dto.setQuantitysM8(value);
			value = tempMap.get(9)==null?0:tempMap.get(9);
			total += value;
			dto.setQuantitysM9(value);
			value = tempMap.get(10)==null?0:tempMap.get(10);
			total += value;
			dto.setQuantitysM10(value);
			value = tempMap.get(11)==null?0:tempMap.get(11);
			total += value;
			dto.setQuantitysM11(value);
			value = tempMap.get(12)==null?0:tempMap.get(12);
			total += value;
			dto.setQuantitysM12(value);
			dto.setTotal(total);
			dto.setTotals(totals);
			dtoList.add(dto);
		}
		//自定义按照一年总数合计排序
        Collections.sort(dtoList,new Comparator<Object>(){
            @Override
            public int compare(Object o1, Object o2) {
            	OrderInfoYearDto d1 = (OrderInfoYearDto)o1;
            	OrderInfoYearDto d2 = (OrderInfoYearDto)o2;
                return d2.getTotal().compareTo(d1.getTotal());
            }
         });
		//3毫秒内
		//System.err.println("shyong shijian "+( System.currentTimeMillis() - startTime));
		return dtoList;
	}
	
	@Override
	public List<OrderInfoAllYearVo> getOrderInfoAllYearVoList(String yearStart, String yearEnd) {
		return orderInfoService.getOrderInfoAllYearVoList(yearStart, yearEnd);
	}
}
