package com.apical.oddm.facade.manufacture.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apical.oddm.application.order.OrderInfoServiceI;
import com.apical.oddm.application.produce.ManufactureFittingServiceI;
import com.apical.oddm.application.produce.ManufactureOsServiceI;
import com.apical.oddm.application.produce.ManufacturePackageServiceI;
import com.apical.oddm.application.produce.ManufacturePackageTitleServiceI;
import com.apical.oddm.application.produce.ManufactureServiceI;
import com.apical.oddm.application.produce.ManufactureShellServiceI;
import com.apical.oddm.application.produce.ManufactureUnreadServiceI;
import com.apical.oddm.core.constant.ManufactureConst;
import com.apical.oddm.core.model.order.OrderInfo;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.page.SystemContext;
import com.apical.oddm.core.model.produce.Manufacture;
import com.apical.oddm.core.model.produce.ManufactureFitting;
import com.apical.oddm.core.model.produce.ManufactureOs;
import com.apical.oddm.core.model.produce.ManufacturePackage;
import com.apical.oddm.core.model.produce.ManufacturePackageTitle;
import com.apical.oddm.core.model.produce.ManufactureShell;
import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.base.dto.BasePage;
import com.apical.oddm.facade.manufacture.ManufactureAltFacade;
import com.apical.oddm.facade.manufacture.ManufactureFacade;
import com.apical.oddm.facade.manufacture.command.ManufactureCommand;
import com.apical.oddm.facade.manufacture.command.ManufactureFittingCommand;
import com.apical.oddm.facade.manufacture.command.ManufactureOsCommand;
import com.apical.oddm.facade.manufacture.command.ManufacturePackageCommand;
import com.apical.oddm.facade.manufacture.command.ManufactureShellCommand;
import com.apical.oddm.facade.manufacture.dto.ManufactureDTO;
import com.apical.oddm.facade.manufacture.dto.ManufactureFittingDTO;
import com.apical.oddm.facade.manufacture.dto.ManufactureOsDTO;
import com.apical.oddm.facade.manufacture.dto.ManufacturePackageDTO;
import com.apical.oddm.facade.manufacture.dto.ManufacturePackageTitleDTO;
import com.apical.oddm.facade.manufacture.dto.ManufactureShellDTO;
import com.apical.oddm.facade.order.command.OrderInfoCommand;
import com.apical.oddm.facade.order.dto.OrderInfoDTO;
import com.apical.oddm.facade.util.TimeUtil;



@Component("manufactureFacade")
public class ManufactureFacadeImpl implements ManufactureFacade {
	
	@Autowired
	private ManufactureServiceI manufactureService;
	
	@Autowired
	private ManufactureAltFacade manufactureAltFacade;
	
	@Autowired
	private ManufactureOsServiceI manufactureOsService;
	
	@Autowired
	private ManufactureShellServiceI manufactureShellService;
	
	@Autowired
	private ManufactureFittingServiceI manufactureFittingService;
	
	@Autowired
	private ManufacturePackageServiceI manufacturePackageService;
	
	@Autowired
	private ManufacturePackageTitleServiceI manufacturePackageTitleService;
	
	@Autowired
	private OrderInfoServiceI orderInfoService;

	@Autowired
	private ManufactureUnreadServiceI manufactureUnreadService;
	
	@Override
	public BasePage<ManufactureDTO> dataGrid(ManufactureCommand manufactureCommand, PageCommand pageCommand) {
		// TODO Auto-generated method stub
		SystemContext.setPageOffset(pageCommand.getPage());
		SystemContext.setPageSize(pageCommand.getRows());
		SystemContext.setOrder(pageCommand.getOrder());
		SystemContext.setSort("t." + pageCommand.getSort());
		Pager<Manufacture> dataGrid = null;
		Manufacture manufactureQuery = new Manufacture();
		if(manufactureCommand != null){
			BeanUtils.copyProperties(manufactureCommand, manufactureQuery);
			//System.out.println(">>>>>>>>>>>>>>"+manufactureQuery.getUnread());
		}
		dataGrid = manufactureService.dataGrid(manufactureQuery, null);
		BasePage<ManufactureDTO> basePage = new BasePage<ManufactureDTO>();
		if(dataGrid != null && dataGrid.getTotal() > 0){
			List<ManufactureDTO> list = new ArrayList<ManufactureDTO>();
			if(dataGrid.getRows() != null && dataGrid.getRows().size() > 0){
				for(Manufacture manufacture : dataGrid.getRows()){
					ManufactureDTO manufactureDTO = new ManufactureDTO();
					BeanUtils.copyProperties(manufacture, manufactureDTO);
					if(manufacture.getUnread() != null){
						manufactureDTO.setUnread(manufacture.getUnread());
						//System.out.println(manufacture.getUnread()+":::::::::::"+manufactureDTO.getUnread());
					}
					
					if (manufacture.getUpdatetime() != null) {
						manufactureDTO.setUpdatetime(TimeUtil.timestampToStringDetaile(manufacture.getUpdatetime()));
					}
					if (manufacture.getDateIssue() != null) {
						manufactureDTO.setDateIssue(TimeUtil.dateToString(manufacture.getDateIssue()));
					}
					if (manufacture.getDateShipment() != null) {
						manufactureDTO.setDateShipment(TimeUtil.dateToString(manufacture.getDateShipment()));
					}
					list.add(manufactureDTO);
				}
				basePage.setTotal(dataGrid.getTotal());
				basePage.setRows(list);
			}
		}
		return basePage;
	}

	@Override
	public BasePage<ManufactureDTO> dataGridByDateIssue(Date startDate, Date endDate, PageCommand pageCommand) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BasePage<ManufactureDTO> dataGridByDateUpdate(Date startDate, Date endDate, PageCommand pageCommand) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ManufactureDTO getManufacture(Integer id,Integer currUserId) {
		if(id != null){
			Manufacture manufacture = manufactureService.getManufacture(id);
			if (currUserId != null && currUserId != 0 && manufacture.getState() >= ManufactureConst.approved) {
				manufactureUnreadService.delete(currUserId, id);
				//SystemContext.removeCurrUserId();
			}
			ManufactureDTO manufactureDTO = new ManufactureDTO();
			if(manufacture != null){
				BeanUtils.copyProperties(manufacture, manufactureDTO);
				if(manufacture.getDateIssue() != null){
					manufactureDTO.setDateIssue(TimeUtil.dateToString(manufacture.getDateIssue()));
				}
				if(manufacture.getDateShipment() != null){
					manufactureDTO.setDateShipment(TimeUtil.dateToString(manufacture.getDateShipment()));
				}
				//软件
				if(manufacture.getOrderMftOs() != null){
					Set<ManufactureOs> orderMftOs = manufacture.getOrderMftOs();
					ManufactureOsDTO manufactureOsDTO = new ManufactureOsDTO();
					for(ManufactureOs manufactureOs : orderMftOs){
						BeanUtils.copyProperties(manufactureOs, manufactureOsDTO);
					}
					manufactureDTO.setManufactureOsDTO(manufactureOsDTO);
				}
				
				//外壳
				if (manufacture.getOrderMftShells() != null) {
					Set<ManufactureShell> manufactureShells = manufacture.getOrderMftShells();
					List<ManufactureShellDTO> manufactureShellDTOs = new ArrayList<ManufactureShellDTO>();
					for(ManufactureShell manufactureShell : manufactureShells){
						ManufactureShellDTO manufactureShellDTO = new ManufactureShellDTO();
						BeanUtils.copyProperties(manufactureShell, manufactureShellDTO);
						//System.out.println(manufactureShell.getPictPaths()+":xx:"+manufactureShellDTO.getPictPaths());
						manufactureShellDTOs.add(manufactureShellDTO);
					}
					manufactureDTO.setManufactureShellDTOs(manufactureShellDTOs);
				}
				
				//配件
				if(manufacture.getOrderMftFittings() != null){
					Set<ManufactureFitting> orderMftFittings = manufacture.getOrderMftFittings();
					List<ManufactureFittingDTO> manufactureFittingDTOs = new ArrayList<ManufactureFittingDTO>();
					for(ManufactureFitting manufactureFitting : orderMftFittings){
						ManufactureFittingDTO manufactureFittingDTO = new ManufactureFittingDTO();
						BeanUtils.copyProperties(manufactureFitting, manufactureFittingDTO);
						//System.out.println(manufactureFitting.getPictPaths()+"::"+manufactureFittingDTO.getPictPaths());
						manufactureFittingDTOs.add(manufactureFittingDTO);
					}
					manufactureDTO.setManufactureFittingDTOs(manufactureFittingDTOs);
				}
				
				//包材
				if(manufacture.getOrderMftPackages() != null){
					Set<ManufacturePackage> manufacturePackages = manufacture.getOrderMftPackages();
					List<ManufacturePackageDTO> manufacturePackageDTOs = new ArrayList<ManufacturePackageDTO>();
					
					
					for(ManufacturePackage manufacturePackage : manufacturePackages){
						ManufacturePackageDTO manufacturePackageDTO = new ManufacturePackageDTO();
						BeanUtils.copyProperties(manufacturePackage, manufacturePackageDTO);
						if(manufacturePackage.getOrderMftPackageTitle() != null){
							ManufacturePackageTitle orderMftPackageTitle = manufacturePackage.getOrderMftPackageTitle();
							ManufacturePackageTitleDTO manufacturePackageTitleDTO = new ManufacturePackageTitleDTO();
							BeanUtils.copyProperties(orderMftPackageTitle, manufacturePackageTitleDTO);
							manufacturePackageDTO.setManufacturePackageTitleDTO(manufacturePackageTitleDTO);
						}
						manufacturePackageDTOs.add(manufacturePackageDTO);
					}
					
					manufactureDTO.setManufacturePackageDTOs(manufacturePackageDTOs);
					/*Map<Integer, List<ManufacturePackageDTO>> map = new TreeMap<Integer, List<ManufacturePackageDTO>>();
					List<ManufacturePackageDTO> list = null;
					if(manufacturePackageDTOs != null && manufacturePackageDTOs.size() > 0){
						for(ManufacturePackageDTO manufacturePackageDTO : manufacturePackageDTOs){
							if(map == null || !map.containsKey(manufacturePackageDTO.getTitleId())){
								list = new ArrayList<ManufacturePackageDTO>();
								list.add(manufacturePackageDTO);
							}else {
								list.add(manufacturePackageDTO);
							}
							map.put(manufacturePackageDTO.getTitleId(), list);
						}
						
						for (Map.Entry<Integer, List<ManufacturePackageDTO>> entry : map.entrySet()) {
							   
							for (ManufacturePackageDTO manufacturePackageDTO : entry.getValue()) {
								System.out.println("key= " + entry.getKey() + " and value= " + manufacturePackageDTO.getDescription());
							}
						}
						
						
						manufactureDTO.setMap(map);
					}*/
					
				}
				
				
				return manufactureDTO;
			}
		}
		return null;
	}

	@Override
	public BasePage<ManufactureDTO> dataGrid(Set<Integer> states) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ManufactureDTO getManufactureBase(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean add(ManufactureCommand manufactureCommand) {
		// TODO Auto-generated method stub
		Manufacture manufacture = new Manufacture();
		//System.out.println(manufactureCommand);
		if(manufactureCommand != null){
			BeanUtils.copyProperties(manufactureCommand, manufacture);
			if(manufactureCommand.getDateIssue() != null){
				manufacture.setDateIssue(TimeUtil.datToDate(manufactureCommand.getDateIssue()));
			}
			if(manufactureCommand.getDateIssue() != null){
				manufacture.setDateIssue(TimeUtil.datToDate(manufactureCommand.getDateIssue()));
			}
			//软件
			if(manufactureCommand.getManufactureOsCommand() != null){
				ManufactureOsCommand manufactureOsCommand = manufactureCommand.getManufactureOsCommand();
				ManufactureOs manufactureOs = new ManufactureOs();
				BeanUtils.copyProperties(manufactureOsCommand, manufactureOs);
				Set<ManufactureOs> set = new LinkedHashSet<ManufactureOs>();
				manufactureOs.setOrderManufacture(manufacture);
				set.add(manufactureOs);
				manufacture.setOrderMftOs(set);
			}
			System.out.println("软件");
			
			//外壳
			if(manufactureCommand.getManufactureShellCommands() != null){
				List<ManufactureShellCommand> manufactureShellCommands = manufactureCommand.getManufactureShellCommands();
				Set<ManufactureShell> manufactureShells = new LinkedHashSet<ManufactureShell>();
				for(ManufactureShellCommand manufactureShellCommand : manufactureShellCommands){
					ManufactureShell manufactureShell = new ManufactureShell();
					
					BeanUtils.copyProperties(manufactureShellCommand, manufactureShell);
					manufactureShell.setOrderManufacture(manufacture);
					manufactureShells.add(manufactureShell);
					//System.out.println("......"+manufactureShell.getCraft());
				}
				manufacture.setOrderMftShells(manufactureShells);
			}
			//System.out.println("外壳");
			//配件
			if(manufactureCommand.getManufactureFittingCommands() != null){
				List<ManufactureFittingCommand> manufactureFittingCommands = manufactureCommand.getManufactureFittingCommands();
				Set<ManufactureFitting> manufactureFittings = new LinkedHashSet<ManufactureFitting>();
				for(ManufactureFittingCommand manufactureFittingCommand : manufactureFittingCommands){
					ManufactureFitting manufactureFitting = new ManufactureFitting();
					BeanUtils.copyProperties(manufactureFittingCommand, manufactureFitting);
					
					manufactureFitting.setOrderManufacture(manufacture);
					manufactureFittings.add(manufactureFitting);
					//System.out.println("...xxx..."+manufactureFitting.getName());
				}
				manufacture.setOrderMftFittings(manufactureFittings);
			}
			//System.out.println("配件");
			//包材
			if(manufactureCommand.getManufacturePackageCommands() != null){
				List<ManufacturePackageCommand> manufacturePackageCommands = manufactureCommand.getManufacturePackageCommands();
				Set<ManufacturePackage> manufacturePackages = new LinkedHashSet<ManufacturePackage>();
				for(ManufacturePackageCommand manufacturePackageCommand : manufacturePackageCommands){
					System.out.println(manufacturePackageCommand);
					ManufacturePackage manufacturePackage = new ManufacturePackage();
					BeanUtils.copyProperties(manufacturePackageCommand, manufacturePackage);
					if(manufacturePackageCommand.getTitleId() != null){
						ManufacturePackageTitle manufacturePackageTitle = new ManufacturePackageTitle(manufacturePackageCommand.getTitleId());
						manufacturePackage.setOrderMftPackageTitle(manufacturePackageTitle);
					}
					//System.out.println("...bbb..."+manufacturePackage.getOrderMftPackageTitle().getName());
					manufacturePackage.setOrderManufacture(manufacture);
					manufacturePackages.add(manufacturePackage);
					
				}
				manufacture.setOrderMftPackages(manufacturePackages);
			}
			//System.out.println("包材");
			//存入数据库
			if(manufacture != null){
				manufacture.setState(ManufactureConst.save);
				Serializable add = manufactureService.add(manufacture);
				if(add != null){
					return true;
				}else {
					return false;
				}
			}
			
		}
		return false;
	}
	
	@Override
	public Integer addManufacture(ManufactureCommand manufactureCommand) {
		// TODO Auto-generated method stub
		if(manufactureCommand != null){
			Manufacture manufacture = new Manufacture();
			BeanUtils.copyProperties(manufactureCommand, manufacture);
			manufacture.setState(ManufactureConst.save);
			manufacture.setOrderInfo(new OrderInfo(manufacture.getOrderId()));
			Serializable add = manufactureService.add(manufacture);
			return (Integer) add;
		}
		return null;
	}
	


	@Override
	public void edit(ManufactureCommand manufactureCommand,Integer currUserId,String currUserName) {
		// TODO Auto-generated method stub
		// 计算变更记录用到
		Manufacture manufacture1 = null;
		
		Manufacture manufacture = null;
		if(manufactureCommand != null){
			if(manufactureCommand.getId() != null){
				manufacture = manufactureService.getManufacture(manufactureCommand.getId());
			}
			
			if (manufacture.getState() >= ManufactureConst.approved) {// 状态审核通过前修改订单不计算变更记录
				manufacture1 = manufactureService.getManufacture(manufactureCommand.getId());		
			}
			BeanUtils.copyProperties(manufactureCommand, manufacture);
			
			//软件信息
			if(manufactureCommand.getManufactureOsCommand() != null){
				Set<ManufactureOs> manufactures = manufacture.getOrderMftOs();
				ManufactureOs manufactureOs = null;
				if (manufactures == null) {
					manufactures = new LinkedHashSet<ManufactureOs>();
					manufactureOs = new ManufactureOs();
					manufactureOs.setOrderManufacture(manufacture);
					manufactures.add(manufactureOs);
					manufacture.setOrderMftOs(manufactures);
				} else {
					if (!manufactures.isEmpty()) {
						manufactureOs = manufactures.iterator().next();
					} else {
						manufactureOs = new ManufactureOs();
						manufactureOs.setOrderManufacture(manufacture);
						manufactures.add(manufactureOs);
					}
				}
				ManufactureOsCommand manufactureOsCommand = manufactureCommand.getManufactureOsCommand();
				BeanUtils.copyProperties(manufactureOsCommand, manufactureOs);
			}
			
			//外壳
			if(manufactureCommand.getManufactureShellCommands() != null && !manufactureCommand.getManufactureShellCommands().isEmpty()){
				List<ManufactureShellCommand> manufactureShellCommands = manufactureCommand.getManufactureShellCommands();
				Set<ManufactureShell> manufactureShells = manufacture.getOrderMftShells();
				//无数据的初始化
				if (manufactureShells == null) {
					manufactureShells = new LinkedHashSet<ManufactureShell>();
					manufacture.setOrderMftShells(manufactureShells);
				}
				//临时存放老数据
				Map<Integer, ManufactureShell> tempMap = new HashMap<>();
				for (ManufactureShell manufactureShell : manufactureShells) {
					tempMap.put(manufactureShell.getId(), manufactureShell);
				}
				
				for(ManufactureShellCommand manufactureShellCommand : manufactureShellCommands){
					if(manufactureShellCommand.getId() == null){//新增
						ManufactureShell manufactureShell = new ManufactureShell();
						BeanUtils.copyProperties(manufactureShellCommand, manufactureShell);
						manufactureShell.setOrderManufacture(manufacture);
						manufactureShells.add(manufactureShell);
					}else {
						if(tempMap.get(manufactureShellCommand.getId()) != null){//修改
							BeanUtils.copyProperties(manufactureShellCommand, tempMap.get(manufactureShellCommand.getId()));
							tempMap.remove(manufactureShellCommand.getId());//移出后就剩下要删除的对象
						}
					}
				}
				for (ManufactureShell h : tempMap.values()) {// 删除操作，级联删除起作用的前提是关联的集合对象不能又一次指向新的引用，必须在原有的集合里操作新增、删除、清空元素，像上面的setXXX(null)的方法等是起步到级联删除作用的，大概是Hibernate自认自己原生的集合对象吧。自己New的放进行级联删除无效！
					manufactureShells.remove(h);
				}
			}else{
				Set<ManufactureShell> manufactureShells = manufacture.getOrderMftShells();
				if(manufactureShells != null && manufactureShells.size() > 0){
					manufactureShells.clear();
				}
			}
			//配件
			if(manufactureCommand.getManufactureFittingCommands() != null && !manufactureCommand.getManufactureFittingCommands().isEmpty()){
				List<ManufactureFittingCommand> manufactureFittingCommands = manufactureCommand.getManufactureFittingCommands();
				Set<ManufactureFitting> manufactureFittings = manufacture.getOrderMftFittings();
				//无数据的初始化
				if (manufactureFittings == null) {
					manufactureFittings = new LinkedHashSet<ManufactureFitting>();
					manufacture.setOrderMftFittings(manufactureFittings);
				}
				//临时存放老数据
				Map<Integer, ManufactureFitting> tempMap = new HashMap<>();
				for (ManufactureFitting manufactureFitting : manufactureFittings) {
					tempMap.put(manufactureFitting.getId(), manufactureFitting);
				}
				
				for(ManufactureFittingCommand manufactureFittingCommand : manufactureFittingCommands){
					if(manufactureFittingCommand.getId() == null){//新增
						ManufactureFitting manufactureFitting = new ManufactureFitting();
						BeanUtils.copyProperties(manufactureFittingCommand, manufactureFitting);
						manufactureFitting.setOrderManufacture(manufacture);
						manufactureFittings.add(manufactureFitting);
					}else {
						if(tempMap.get(manufactureFittingCommand.getId()) != null){//修改
							BeanUtils.copyProperties(manufactureFittingCommand, tempMap.get(manufactureFittingCommand.getId()));
							tempMap.remove(manufactureFittingCommand.getId());//移出后就剩下要删除的对象
						}
					}
				}
				
				for (ManufactureFitting h : tempMap.values()) {// 删除操作，级联删除起作用的前提是关联的集合对象不能又一次指向新的引用，必须在原有的集合里操作新增、删除、清空元素，像上面的setXXX(null)的方法等是起步到级联删除作用的，大概是Hibernate自认自己原生的集合对象吧。自己New的放进行级联删除无效！
					manufactureFittings.remove(h);
				}
				
			}else {
				Set<ManufactureFitting> manufactureFittings = manufacture.getOrderMftFittings();
				if(manufactureFittings != null && manufactureFittings.size() > 0){
					manufactureFittings.clear();
				}
			}
			//包材
			if(manufactureCommand.getManufacturePackageCommands() != null && !manufactureCommand.getManufacturePackageCommands().isEmpty()){
				List<ManufacturePackageCommand> manufacturePackageCommands = manufactureCommand.getManufacturePackageCommands();
				Set<ManufacturePackage> manufacturePackages = manufacture.getOrderMftPackages();
				//无数据的初始化
				if (manufacturePackages == null) {
					manufacturePackages = new LinkedHashSet<ManufacturePackage>();
					manufacture.setOrderMftPackages(manufacturePackages);
				}
				//临时存放老数据
				Map<Integer, ManufacturePackage> tempMap = new HashMap<>();
				for (ManufacturePackage manufacturePackage : manufacturePackages) {
					tempMap.put(manufacturePackage.getId(), manufacturePackage);
				}
				
				for(ManufacturePackageCommand manufacturePackageCommand : manufacturePackageCommands){
					if(manufacturePackageCommand.getId() == null){//新增
						ManufacturePackage manufacturePackage = new ManufacturePackage();
						BeanUtils.copyProperties(manufacturePackageCommand, manufacturePackage);
						if(manufacturePackageCommand.getTitleId() != null){
							ManufacturePackageTitle manufacturePackageTitle = new ManufacturePackageTitle(manufacturePackageCommand.getTitleId());
							manufacturePackage.setOrderMftPackageTitle(manufacturePackageTitle);
						}
						manufacturePackage.setOrderManufacture(manufacture);
						manufacturePackages.add(manufacturePackage);
					}else {
						if(tempMap.get(manufacturePackageCommand.getId()) != null){//修改
							BeanUtils.copyProperties(manufacturePackageCommand, tempMap.get(manufacturePackageCommand.getId()));
							tempMap.remove(manufacturePackageCommand.getId());//移出后就剩下要删除的对象
						}
					}
				}
				for (ManufacturePackage h : tempMap.values()) {// 删除操作，级联删除起作用的前提是关联的集合对象不能又一次指向新的引用，必须在原有的集合里操作新增、删除、清空元素，像上面的setXXX(null)的方法等是起步到级联删除作用的，大概是Hibernate自认自己原生的集合对象吧。自己New的放进行级联删除无效！
					manufacturePackages.remove(h);
				}
				
			}else {
				Set<ManufacturePackage> manufacturePackages = manufacture.getOrderMftPackages();
				if(manufacturePackages != null && manufacturePackages.size() > 0){
					manufacturePackages.clear();
				}
			}
			manufactureService.edit(manufacture);
			
			
			//变更记录计算,得放在最后，否则添加失败就麻烦了
			manufactureAltFacade.editAllRecord(manufacture1, manufactureCommand, currUserId, currUserName);
		}		
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		if(id != null){
			manufactureService.delete(id);
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
	public OrderInfoDTO getByOrderDTO(OrderInfoCommand orderInfoCommand) {
		
		if(orderInfoCommand != null &&  orderInfoCommand.getOrderNo() != null){
			OrderInfo orderInfoQuery = new OrderInfo();
			if(orderInfoCommand.getOrderNo() != null){
				orderInfoQuery.setOrderNo(orderInfoCommand.getOrderNo());
				OrderInfo orderInfo = orderInfoService.getByOrder(orderInfoQuery);
				if(orderInfo != null ){
					OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
					BeanUtils.copyProperties(orderInfo, orderInfoDTO);
					return orderInfoDTO;
				}
			}
			return null;
		} 
		return null;
	}

	@Override
	public ManufactureDTO getManufactureByOrderNo(String orderNo) {
		// TODO Auto-generated method stub
		
		if(orderNo != null){
			Manufacture manufactureQuery = new Manufacture();
			manufactureQuery.setOrderNo(orderNo);
			Manufacture manufacture = manufactureService.getManufacture(manufactureQuery, true);
		
			if(manufacture != null){
				ManufactureDTO manufactureDTO = new ManufactureDTO();
				BeanUtils.copyProperties(manufacture, manufactureDTO);
				return manufactureDTO;
			}
		}
		return null;
	}

	@Override
	public void review(Integer id, Integer currUserId) {
		// TODO Auto-generated method stub
		if(id != null){
			Manufacture manufacture = manufactureService.get(id);
			manufacture.setState(ManufactureConst.approved);
			manufactureService.edit(manufacture);
			// 务必放在最后，现在增加成功在调用
			manufactureAltFacade.addMftUnreadInfo(manufacture.getOrderNo(), currUserId == null ? 0 : currUserId, id);// 给其他拥有订单菜单权限的用户增加订单未读信息
		}
		
	}

	@Override
	public ManufactureDTO getManufacture(ManufactureCommand manufactureCommand, boolean lazy,Integer currUserId) {
		if(manufactureCommand != null){
			Manufacture manufactureQuery = new Manufacture();
			BeanUtils.copyProperties(manufactureCommand, manufactureQuery);
			Manufacture manufacture = null;
			if(manufactureQuery.getOrderNo() != null){
				manufacture = manufactureService.getManufacture(manufactureQuery, lazy);
			}
			 
			/*if (currUserId != null && currUserId != 0 && manufacture.getState() >= ManufactureConst.approved) {
				manufactureUnreadService.delete(currUserId, id);
				//SystemContext.removeCurrUserId();
			}*/
			ManufactureDTO manufactureDTO = new ManufactureDTO();
			if(manufacture != null){
				BeanUtils.copyProperties(manufacture, manufactureDTO);
				if(manufacture.getDateIssue() != null){
					manufactureDTO.setDateIssue(TimeUtil.dateToString(manufacture.getDateIssue()));
				}
				if(manufacture.getDateShipment() != null){
					manufactureDTO.setDateShipment(TimeUtil.dateToString(manufacture.getDateShipment()));
				}
				//软件
				if(manufacture.getOrderMftOs() != null){
					Set<ManufactureOs> orderMftOs = manufacture.getOrderMftOs();
					ManufactureOsDTO manufactureOsDTO = new ManufactureOsDTO();
					for(ManufactureOs manufactureOs : orderMftOs){
						BeanUtils.copyProperties(manufactureOs, manufactureOsDTO);
					}
					manufactureDTO.setManufactureOsDTO(manufactureOsDTO);
				}
				
				//外壳
				if (manufacture.getOrderMftShells() != null) {
					Set<ManufactureShell> manufactureShells = manufacture.getOrderMftShells();
					List<ManufactureShellDTO> manufactureShellDTOs = new ArrayList<ManufactureShellDTO>();
					for(ManufactureShell manufactureShell : manufactureShells){
						ManufactureShellDTO manufactureShellDTO = new ManufactureShellDTO();
						BeanUtils.copyProperties(manufactureShell, manufactureShellDTO);
						//System.out.println(manufactureShell.getPictPaths()+":xx:"+manufactureShellDTO.getPictPaths());
						manufactureShellDTOs.add(manufactureShellDTO);
					}
					manufactureDTO.setManufactureShellDTOs(manufactureShellDTOs);
				}
				
				//配件
				if(manufacture.getOrderMftFittings() != null){
					Set<ManufactureFitting> orderMftFittings = manufacture.getOrderMftFittings();
					List<ManufactureFittingDTO> manufactureFittingDTOs = new ArrayList<ManufactureFittingDTO>();
					for(ManufactureFitting manufactureFitting : orderMftFittings){
						ManufactureFittingDTO manufactureFittingDTO = new ManufactureFittingDTO();
						BeanUtils.copyProperties(manufactureFitting, manufactureFittingDTO);
						//System.out.println(manufactureFitting.getPictPaths()+"::"+manufactureFittingDTO.getPictPaths());
						manufactureFittingDTOs.add(manufactureFittingDTO);
					}
					manufactureDTO.setManufactureFittingDTOs(manufactureFittingDTOs);
				}
				
				//包材
				if(manufacture.getOrderMftPackages() != null){
					Set<ManufacturePackage> manufacturePackages = manufacture.getOrderMftPackages();
					List<ManufacturePackageDTO> manufacturePackageDTOs = new ArrayList<ManufacturePackageDTO>();
					
					
					for(ManufacturePackage manufacturePackage : manufacturePackages){
						ManufacturePackageDTO manufacturePackageDTO = new ManufacturePackageDTO();
						BeanUtils.copyProperties(manufacturePackage, manufacturePackageDTO);
						if(manufacturePackage.getOrderMftPackageTitle() != null){
							ManufacturePackageTitle orderMftPackageTitle = manufacturePackage.getOrderMftPackageTitle();
							ManufacturePackageTitleDTO manufacturePackageTitleDTO = new ManufacturePackageTitleDTO();
							BeanUtils.copyProperties(orderMftPackageTitle, manufacturePackageTitleDTO);
							manufacturePackageDTO.setManufacturePackageTitleDTO(manufacturePackageTitleDTO);
						}
						manufacturePackageDTOs.add(manufacturePackageDTO);
					}
					
					manufactureDTO.setManufacturePackageDTOs(manufacturePackageDTOs);
				}
				return manufactureDTO;
			}
		}
		return null;
	}

	
}
