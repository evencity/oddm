package com.apical.oddm.facade.bom.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apical.oddm.application.bom.BomInfoServiceI;
import com.apical.oddm.application.bom.BomMaterialContactServiceI;
import com.apical.oddm.application.bom.BomMaterialRefServiceI;
import com.apical.oddm.application.bom.BomMaterialServiceI;
import com.apical.oddm.application.bom.BomUnreadServiceI;
import com.apical.oddm.application.order.OrderInfoServiceI;
import com.apical.oddm.core.constant.BomConst;
import com.apical.oddm.core.constant.ManufactureConst;
import com.apical.oddm.core.model.bom.BomInfo;
import com.apical.oddm.core.model.bom.BomMaterial;
import com.apical.oddm.core.model.bom.BomMaterialContact;
import com.apical.oddm.core.model.bom.BomMaterialRef;
import com.apical.oddm.core.model.order.OrderInfo;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.page.SystemContext;
import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.base.dto.BasePage;
import com.apical.oddm.facade.bom.BomFacade;
import com.apical.oddm.facade.bom.BomInfoAltFacade;
import com.apical.oddm.facade.bom.BomMaterialContactFacade;
import com.apical.oddm.facade.bom.dto.BomDTO;
import com.apical.oddm.facade.bom.dto.BomMaterialContactDTO;
import com.apical.oddm.facade.bom.dto.BomMaterialDTO;
import com.apical.oddm.facade.bom.dto.BomMaterialRefDTO;
import com.apical.oddm.facade.order.command.OrderInfoCommand;
import com.apical.oddm.facade.order.dto.OrderInfoDTO;
import com.apical.oddm.facade.util.FractionalUtil;
import com.apical.oddm.facade.util.TimeUtil;

/** 
 * @description 类的描述：
 * @author  作者 : zhuzh
 * @date 创建时间：2017年2月9日 下午2:17:02 
 * @version 1.0 
 */
@Component("bomFacade")
public class BomFacadeImpl implements BomFacade {
	private static final Logger log = LoggerFactory.getLogger(BomFacadeImpl.class);
	@Autowired  
	private BomInfoServiceI bomInfoService;
	
	@Autowired
	private BomMaterialServiceI bomMaterialService;
	
	@Autowired
	private OrderInfoServiceI orderInfoService;
	
	@Autowired
	private BomMaterialRefServiceI bomMaterialRefService;
	
	@Autowired
	private BomMaterialContactFacade bomMaterialContactFacade;
	
	@Autowired
	private BomInfoAltFacade bomInfoAltFacade;
	
	@Autowired  
	private BomMaterialContactServiceI bomMaterialContactServiceI;
	
	@Autowired
	private BomUnreadServiceI bomUnreadService ;
	
	@Override
	public BasePage<BomDTO> dataGrid(BomDTO bomDTOQuery,Set<Integer> states, PageCommand pageCommand) {
		SystemContext.setPageOffset(pageCommand.getPage());
		SystemContext.setPageSize(pageCommand.getRows());
		SystemContext.setOrder(pageCommand.getOrder());
		SystemContext.setSort("t." + pageCommand.getSort());
		Pager<BomInfo> dataGrid = null;
		BomInfo bomInfoQuery = new BomInfo();
		if(bomDTOQuery != null){
			BeanUtils.copyProperties(bomDTOQuery, bomInfoQuery);
			if(bomDTOQuery.getOrderNo() != null){
				OrderInfo orderInfo = new OrderInfo();
				orderInfo.setOrderNo(bomDTOQuery.getOrderNo());
				bomInfoQuery.setOrderInfo(orderInfo);
			}
		}
		
		dataGrid = bomInfoService.dataGridByBomInfo(bomInfoQuery,states);
		
		BasePage<BomDTO> basePage = new BasePage<BomDTO>();
		if(dataGrid != null && dataGrid.getTotal() > 0){
			List<BomDTO> list = new ArrayList<BomDTO>();
			if(dataGrid.getRows() != null && dataGrid.getRows().size() > 0){
				for(BomInfo bomInfo : dataGrid.getRows()){
					BomDTO bomDTO = new BomDTO();
					BeanUtils.copyProperties(bomInfo, bomDTO);
					if(bomInfo.getUnread() != null){
						bomDTO.setUnread(bomInfo.getUnread());
					}
					if(bomInfo.getOrderInfo() != null){
						OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
						BeanUtils.copyProperties(bomInfo.getOrderInfo(), orderInfoDTO);
						bomDTO.setOrderInfoDTO(orderInfoDTO);
					}
					
					list.add(bomDTO);
				}
				basePage.setTotal(dataGrid.getTotal());
				basePage.setRows(list);
			}
		}
		return basePage;
	}
	
	@Override
	public BomDTO getBomDetailById(Integer id,Integer currUserId) {
		BomDTO bomDTO = new BomDTO();
		if(id != null){
			BomInfo bomInfo = bomInfoService.getBomDetailById(id);
			if(bomInfo != null){
				BeanUtils.copyProperties(bomInfo, bomDTO);
				if(bomInfo.getDate() != null){
					bomDTO.setDateString(TimeUtil.dateToString(bomInfo.getDate()));
				}
				//订单信息
				if(bomInfo.getOrderInfo() != null ){
					OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
					BeanUtils.copyProperties(bomInfo.getOrderInfo(), orderInfoDTO);
					bomDTO.setOrderInfoDTO(orderInfoDTO);
				}
				//物料
				if(bomInfo.getBomMaterialRefs() != null && bomInfo.getBomMaterialRefs().size() > 0){
					Set<BomMaterialRef> bomMaterialRefs = bomInfo.getBomMaterialRefs();
					List<BomMaterialRefDTO> list = new ArrayList<BomMaterialRefDTO>();
					for(BomMaterialRef bomMaterialRef : bomMaterialRefs){
						BomMaterialRefDTO bomMaterialRefDTO = new BomMaterialRefDTO();
						BeanUtils.copyProperties(bomMaterialRef, bomMaterialRefDTO);
						//System.err.println(bomMaterialRef.getUsageAmount()+"。。。。。。。。详细");
						if(bomMaterialRefDTO.getUsageAmount1() != null && bomMaterialRefDTO.getUsageAmount2() != null){
							int usageAmount1 =  bomMaterialRefDTO.getUsageAmount1();
							int usageAmount2 =  bomMaterialRefDTO.getUsageAmount2();
							try {
								String showToString = FractionalUtil.showToString(usageAmount1, usageAmount2);
								bomMaterialRefDTO.setUsageAmount(showToString);
							} catch (Exception e) {
								// TODO: handle exception
								log.error("转化出错：",e);
							}
							
						}
						if(bomMaterialRef.getContacts() != null){
							BomMaterialContactDTO bomMaterialContactDTO = new BomMaterialContactDTO();
							BeanUtils.copyProperties(bomMaterialRef.getContacts(), bomMaterialContactDTO);
							bomMaterialRefDTO.setMaterialContactDTO(bomMaterialContactDTO);
						}
						list.add(bomMaterialRefDTO);
					}
					bomDTO.setBomMaterialRefDTOs(list);
				}
				//删除未读记录
				if (currUserId != null && currUserId != 0 && bomInfo.getState() >= BomConst.approved) {
					bomUnreadService.delete(currUserId, id);
				}
			}
			
		}
		return bomDTO;
	}

	@Override
	public void updateUpdateTime(Integer id) {
		// TODO Auto-generated method stub

	}


	@Override
	public BomDTO getBomByMaterialCode(String materialCode) {
		// TODO Auto-generated method stub
		if(materialCode != null && !"".equals(materialCode)){
			BomDTO bomDTO = new BomDTO();
			BomInfo bomByMaterialCode = bomInfoService.getBomByMaterialCode(materialCode);
			if(bomByMaterialCode != null){
				BeanUtils.copyProperties(bomByMaterialCode, bomDTO);
				return bomDTO;
			}
			return null;
		}
		return null;
	}

	@Override
	public BomDTO getBomByOrderNo(String orderNO) {
		
		if(orderNO != null){
			BomInfo bomInfo = bomInfoService.getBomByOrderNo(orderNO);
			if(bomInfo != null){
				BomDTO bomDTO = new BomDTO();
				BeanUtils.copyProperties(bomInfo, bomDTO);
				return bomDTO;
			}
			return null;
		}
		return null;
	}


	@Override
	public BasePage<BomDTO> dataGridByDateCreate(Date startDate, Date endDate, PageCommand pageCommand) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer add(BomDTO bomDTO) {
		BomInfo bomInfo = new BomInfo();
		if(bomDTO != null){
			BeanUtils.copyProperties(bomDTO, bomInfo);
			if (bomDTO.getDate() != null) {
				bomInfo.setDate(TimeUtil.datToDate(bomDTO.getDate()));
			}
			if(bomDTO.getOrderId() != null){
				OrderInfo orderInfo = new OrderInfo(bomDTO.getOrderId());
				bomInfo.setOrderInfo(orderInfo);
			}
			if(bomDTO.getBomMaterialRefDTOs() != null ){
				List<BomMaterialRefDTO> bomMaterialRefDTOs = bomDTO.getBomMaterialRefDTOs();
				Set<BomMaterialRef> set = new LinkedHashSet<BomMaterialRef>();
				for(BomMaterialRefDTO bomMaterialRefDTO : bomMaterialRefDTOs){
					BomMaterialRef bomMaterialRef = new BomMaterialRef();
					BeanUtils.copyProperties(bomMaterialRefDTO, bomMaterialRef);
					
					if(bomMaterialRefDTO.getUsageAmount1() != null && bomMaterialRefDTO.getUsageAmount2() != null){
						int usageAmount1 =  bomMaterialRefDTO.getUsageAmount1();
						int usageAmount2 =  bomMaterialRefDTO.getUsageAmount2();
						try {
							String showToString = FractionalUtil.showToString(usageAmount1, usageAmount2);
							bomMaterialRefDTO.setUsageAmount(showToString);
						} catch (Exception e) {
							// TODO: handle exception
							log.error("用量转化出错：",e);
						}
					}
					
					bomMaterialRef.setBomInfo(bomInfo);
					if(bomMaterialRefDTO.getMaterialContactDTO() != null){
						BomMaterialContact bomMaterialContact = new BomMaterialContact();
						BeanUtils.copyProperties(bomMaterialRefDTO.getMaterialContactDTO(), bomMaterialContact);
						//二级关联的时候出现id为0的情况，原因不明
						bomMaterialContact.setId(null);
						bomMaterialRef.setContacts(bomMaterialContact);
					}
					//System.out.println(bomMaterialRef.getUsageAmount()+"。。。。。。。。添加");
					set.add(bomMaterialRef);
				}
				bomInfo.setBomMaterialRefs(set);
			}
			
			bomInfo.setState(BomConst.save);
			Serializable add = bomInfoService.add(bomInfo);
			if(add != null){
				return (Integer) add;
			}
			return null;
		}
		return null;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		if(id != null){
			bomInfoService.delete(id);
		}
	}

	@Override
	public void edit(BomDTO bomDTO,Integer currUserId,String currUserName) {
		// TODO Auto-generated method stub
		BomInfo bomInfo = null;
		BomInfo bomInfoForRecord = null;//
		if(bomDTO != null){
			if(bomDTO.getId() != null){
				bomInfo = bomInfoService.getBomDetailById(bomDTO.getId());
			}
			//变更记录
			if(bomInfo.getState() >= BomConst.approved){
				bomInfoForRecord = bomInfoService.getBomDetailById(bomDTO.getId());
			}
			
			BeanUtils.copyProperties(bomDTO, bomInfo);
			
			if(bomDTO.getOrderId() != null){
				OrderInfo orderInfo = new OrderInfo(bomDTO.getOrderId());
				bomInfo.setOrderInfo(orderInfo);
			}
			
			if(bomDTO.getBomMaterialRefDTOs() != null ){
				List<BomMaterialRefDTO> bomMaterialRefDTOs = bomDTO.getBomMaterialRefDTOs();
				Set<BomMaterialRef> bomMaterialRefs = bomInfo.getBomMaterialRefs();
				
				//无数据的初始化
				if (bomMaterialRefs == null) {
					bomMaterialRefs = new LinkedHashSet<BomMaterialRef>();
					bomInfo.setBomMaterialRefs(bomMaterialRefs);
				}
				
				//临时存放老数据
				Map<Integer, BomMaterialRef> tempMap = new HashMap<>();
				for (BomMaterialRef bomMaterialRef : bomMaterialRefs) {
					tempMap.put(bomMaterialRef.getId(), bomMaterialRef);
				}
				
				for(BomMaterialRefDTO bomMaterialRefDTO : bomMaterialRefDTOs){
					if(bomMaterialRefDTO.getId() == null ){//id为空则为新增物料
						BomMaterialRef bomMaterialRef = new BomMaterialRef();
						BeanUtils.copyProperties(bomMaterialRefDTO, bomMaterialRef);
						
						//新增物料  1.新增联系人
						if(bomMaterialRefDTO.getMaterialContactDTO() != null){
							BomMaterialContact bomMaterialContact = new BomMaterialContact();
							BeanUtils.copyProperties(bomMaterialRefDTO.getMaterialContactDTO() , bomMaterialContact);
							bomMaterialContact.setId(null);
							bomMaterialRef.setContacts(bomMaterialContact);
						}
						bomMaterialRef.setBomInfo(bomInfo);
						bomMaterialRefs.add(bomMaterialRef);
						//bomInfo.setBomMaterialRefs(bomMaterialRefs);
					}else{
						if(tempMap.get(bomMaterialRefDTO.getId()) != null){//数据库存在id则为修改
							BeanUtils.copyProperties(bomMaterialRefDTO,tempMap.get(bomMaterialRefDTO.getId()));
							//System.out.println(tempMap.get(bomMaterialRefDTO.getId()).getUsageAmount()+"555555555555555555");
							//修改则分 联系人的增加   删除  和 修改
							if(bomMaterialRefDTO.getMaterialContactDTO() != null){
								BomMaterialContactDTO materialContactDTO = bomMaterialRefDTO.getMaterialContactDTO();
								if(materialContactDTO.getId() != null){
									//修改
									BomMaterialContact bomMaterialContact = tempMap.get(bomMaterialRefDTO.getId()).getContacts();
									BeanUtils.copyProperties(bomMaterialRefDTO.getMaterialContactDTO() , bomMaterialContact);
									tempMap.get(bomMaterialRefDTO.getId()).setContacts(bomMaterialContact);
								}else {
									//增加
									if(materialContactDTO.getContacts() != null && !"".equals(materialContactDTO.getContacts())){
										BomMaterialContact bomMaterialContact = new BomMaterialContact();
										BeanUtils.copyProperties(bomMaterialRefDTO.getMaterialContactDTO() , bomMaterialContact);
										bomMaterialContact.setId(null);
										tempMap.get(bomMaterialRefDTO.getId()).setContacts(bomMaterialContact);
									}else{
										//删除
										tempMap.get(bomMaterialRefDTO.getId()).setContacts(null);
									}
								}
							}else{
								//删除,此处会执行标题的bomMaterialRefDTO类
								tempMap.get(bomMaterialRefDTO.getId()).setContacts(null);
							}
							tempMap.remove(bomMaterialRefDTO.getId());
						}
					}
				}
				
				for(BomMaterialRef h : tempMap.values()){
					bomMaterialRefs.remove(h);
				}
			}else {
				Set<BomMaterialRef> bomMaterialRefs = bomInfo.getBomMaterialRefs();
				if(bomMaterialRefs != null && bomMaterialRefs.size() > 0){
					bomMaterialRefs.clear();
				}
			}
			
			bomInfoService.edit(bomInfo);
			
			// 变更记录计算
			bomInfoAltFacade.addEditRecord(bomInfoForRecord, bomDTO, currUserId,currUserName);
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
	public void review(Integer id, Integer currUserId,String orderNo) {
		// TODO Auto-generated method stub
		if(id != null){
			BomInfo bomInfo = bomInfoService.get(id);
			bomInfo.setState(ManufactureConst.approved);
			bomInfoService.edit(bomInfo);
			if (bomInfo.getState() == 5) {// 务必放在最后，现在增加成功在调用
				bomInfoAltFacade.addBomUnreadInfo(currUserId == null ? 0 : currUserId,bomInfo.getId(), orderNo, 1,bomInfo.getClientNameCode());// 给其他拥有订单菜单权限的用户增加订单未读信息
				
			}
		}
		
	}

	@Override
	public BomMaterialDTO getByMaterialCode(String materialCode) {
		// TODO Auto-generated method stub
		if(materialCode != null && !"".equals(materialCode)){
			BomMaterialDTO bomMaterialDTO = new BomMaterialDTO();
			BomMaterial bomMaterial = bomMaterialService.get(materialCode);
			if(bomMaterial != null){
				BeanUtils.copyProperties(bomMaterial, bomMaterialDTO);
			}
			return bomMaterialDTO;
		}
		return null;
	}

	@Override
	public BomDTO getBomByOrderId(Integer orderId,Integer currUserId) {
		// TODO Auto-generated method stub
		BomDTO bomDTO = new BomDTO();
		if(orderId != null){
			BomInfo bomInfoQuery = new BomInfo();
			OrderInfo orderInfo = new OrderInfo();
			orderInfo.setId(orderId);
			bomInfoQuery.setOrderInfo(orderInfo);
			BomInfo bomInfo = bomInfoService.getBom(bomInfoQuery, false);
			if(bomInfo != null){
				BeanUtils.copyProperties(bomInfo, bomDTO);
				if(bomInfo.getDate() != null){
					bomDTO.setDateString(TimeUtil.dateToString(bomInfo.getDate()));
				}
				//订单信息
				if(bomInfo.getOrderInfo() != null ){
					OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
					BeanUtils.copyProperties(bomInfo.getOrderInfo(), orderInfoDTO);
					bomDTO.setOrderInfoDTO(orderInfoDTO);
				}
				//物料
				if(bomInfo.getBomMaterialRefs() != null && bomInfo.getBomMaterialRefs().size() > 0){
					Set<BomMaterialRef> bomMaterialRefs = bomInfo.getBomMaterialRefs();
					List<BomMaterialRefDTO> list = new ArrayList<BomMaterialRefDTO>();
					
					for(BomMaterialRef bomMaterialRef : bomMaterialRefs){
						BomMaterialRefDTO bomMaterialRefDTO = new BomMaterialRefDTO();
						BeanUtils.copyProperties(bomMaterialRef, bomMaterialRefDTO);
						//System.err.println(bomMaterialRef.getUsageAmount()+"。。。。。。。。详细");
						if(bomMaterialRefDTO.getUsageAmount1() != null && bomMaterialRefDTO.getUsageAmount2() != null){
							int usageAmount1 =  bomMaterialRefDTO.getUsageAmount1();
							int usageAmount2 =  bomMaterialRefDTO.getUsageAmount2();
							try {
								String showToString = FractionalUtil.showToString(usageAmount1, usageAmount2);
								bomMaterialRefDTO.setUsageAmount(showToString);
							} catch (Exception e) {
								// TODO: handle exception
								log.error("转化出错：",e);
							}
							
						}
						if(bomMaterialRef.getContacts() != null){
							BomMaterialContactDTO bomMaterialContactDTO = new BomMaterialContactDTO();
							BeanUtils.copyProperties(bomMaterialRef.getContacts(), bomMaterialContactDTO);
							bomMaterialRefDTO.setMaterialContactDTO(bomMaterialContactDTO);
						}
						list.add(bomMaterialRefDTO);
					}
					bomDTO.setBomMaterialRefDTOs(list);
				}
				//删除未读记录
				if (currUserId != null && currUserId != 0 && bomInfo.getState() >= BomConst.approved) {
					bomUnreadService.delete(currUserId, bomInfo.getId());
				}
			}
		}
		return bomDTO;
	}
}
