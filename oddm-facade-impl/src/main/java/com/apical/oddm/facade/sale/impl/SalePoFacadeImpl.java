package com.apical.oddm.facade.sale.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apical.oddm.application.order.OrderInfoServiceI;
import com.apical.oddm.application.sale.SalePoServiceI;
import com.apical.oddm.application.sale.SalePoUnreadServiceI;
import com.apical.oddm.core.constant.SalePoConst;
import com.apical.oddm.core.model.order.OrderInfo;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.sale.SalePo;
import com.apical.oddm.core.model.sale.SalePoList;
import com.apical.oddm.core.model.sys.User;
import com.apical.oddm.facade.sale.po.SalePoAltFacade;
import com.apical.oddm.facade.sale.po.SalePoFacadeI;
import com.apical.oddm.facade.sale.po.cmd.SalePoCmd;
import com.apical.oddm.facade.sale.po.cmd.SalePoListCmd;
import com.apical.oddm.facade.sale.po.dto.SalePoDto;
import com.apical.oddm.facade.sale.po.dto.SalePoListDto;

@Component("salePoFacade")
public class SalePoFacadeImpl implements SalePoFacadeI {

	@Autowired
	private SalePoServiceI salePoService;
	
	@Autowired
	private OrderInfoServiceI orderInfoService;
	
	@Autowired
	private SalePoAltFacade salePoAltFacade;

	@Autowired
	private SalePoUnreadServiceI salePoUnreadService;
	@Override
	public Pager<SalePoDto> dataGrid(SalePoDto salePoDto, Set<Integer> states) {
		Pager<SalePoDto> page = null;
		SalePo salePo = null;
		if (salePoDto != null) {
			salePo = new SalePo();
			BeanUtils.copyProperties(salePoDto, salePo);
			OrderInfo orderInfo = new OrderInfo();
			salePo.setOrderInfo(orderInfo);
			BeanUtils.copyProperties(salePoDto, orderInfo);
		}
		Pager<SalePo> dataGrid = salePoService.dataGrid(salePo, states);
		if (dataGrid != null) {
			page = new Pager<SalePoDto>();
			BeanUtils.copyProperties(dataGrid, page);
			if (dataGrid.getRows() != null) {
				List<SalePoDto> rows = new ArrayList<SalePoDto>();
				page.setRows(rows);
				for (SalePo po : dataGrid.getRows()) {
					SalePoDto SalePoDto = new SalePoDto();
					//BeanUtils.copyProperties(po.getOrderInfo(), SalePoDto);
					//System.err.println("po.getUnread() ::"+po.getUnread());
					BeanUtils.copyProperties(po, SalePoDto);
					SalePoDto.setUnread(po.getUnread());//必须写，不知道为什么BeanUtils不帮复制
					//System.err.println("SalePoDto.getUnread() ::"+SalePoDto.getUnread());
					rows.add(SalePoDto);
				}
			}
		}
		return page;
	}

	@Override
	public Serializable add(SalePoCmd salePoCmd) {
		SalePo salePo = new SalePo();
		//System.err.println(salePoCmd);
		BeanUtils.copyProperties(salePoCmd, salePo);
		salePo.setState(1);
		salePo.setOrderInfo(new OrderInfo(salePoCmd.getOrderId()));
		if (salePoCmd.getSalePoListCmd() != null && !salePoCmd.getSalePoListCmd().isEmpty()) {
			Set<SalePoList> salePoLists = new LinkedHashSet<SalePoList>();
			salePo.setSalePoLists(salePoLists);
			SalePoList salePoList = null;
			for (SalePoListCmd list : salePoCmd.getSalePoListCmd()) {
				salePoList = new SalePoList();
				BeanUtils.copyProperties(list, salePoList);
				salePoList.setSalePo(salePo);
				salePoLists.add(salePoList);
			}
		}
		return salePoService.add(salePo);
	}

	@Override
	public SalePoDto getBaseInfo(Integer orderId) {
		OrderInfo orderInfo = orderInfoService.get(orderId);
		SalePoDto SalePoDto = new SalePoDto();
		SalePo latelyCompany = salePoService.getLatelyCompany(orderInfo.getSellerId(), orderInfo.getMerchandiserId());
		if (latelyCompany != null) BeanUtils.copyProperties(latelyCompany, SalePoDto);
		BeanUtils.copyProperties(orderInfo, SalePoDto);
		SalePoDto.setOrderId(orderInfo.getId());
		return SalePoDto;
	}

	@Override
	public OrderInfo getOrderInfo(SalePoCmd salePoCmd) {
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setOrderNo(salePoCmd.getOrderNo());
		orderInfo.setSellerId(salePoCmd.getSellerId());
		orderInfo.setMerchandiserId(salePoCmd.getMerchandiserId());
		return orderInfoService.getByOrder(orderInfo);
	}

	@Override
	public SalePoDto getDetail(Integer id, Integer currUserId) {
		SalePoDto SalePoDto = new SalePoDto();
		SalePo salePo = salePoService.getSalePo(id);
		BeanUtils.copyProperties(salePo, SalePoDto);
		OrderInfo orderInfo = salePo.getOrderInfo();
		SalePoDto.setClientName(orderInfo.getClientName());
		SalePoDto.setClientNameCode(orderInfo.getClientNameCode());
		SalePoDto.setDateDelivery(orderInfo.getDateDelivery());
		SalePoDto.setDateOrder(orderInfo.getDateOrder());
		SalePoDto.setMerchandiser(orderInfo.getMerchandiser());
		SalePoDto.setMerchandiserId(orderInfo.getMerchandiserId());
		SalePoDto.setOrderNo(orderInfo.getOrderNo());
		SalePoDto.setProductFactory(orderInfo.getProductFactory());
		SalePoDto.setSeller(orderInfo.getSeller());
		SalePoDto.setSellerId(orderInfo.getSellerId());
		
		if (salePo.getSalePoLists() != null && !salePo.getSalePoLists().isEmpty()) {
			Set<SalePoListDto> salePoLists = new LinkedHashSet<SalePoListDto>();
			SalePoDto.setSalePoListsDto(salePoLists);
			SalePoListDto salePoListDto = null;
			for (SalePoList list : salePo.getSalePoLists()) {
				salePoListDto = new SalePoListDto();
				BeanUtils.copyProperties(list, salePoListDto);
				salePoLists.add(salePoListDto);
			}
		}
		//删除未读记录
		if (currUserId != null && currUserId != 0 && SalePoDto.getState() >= SalePoConst.approved) {
			salePoUnreadService.delete(currUserId, id);
		}
		return SalePoDto;
	}

	@Override
	public void edit(SalePoCmd salePoCmd, Integer currUserId) {
		//System.err.println("salePoCmd "+salePoCmd);
		SalePo salePo = salePoService.getSalePo(salePoCmd.getId());
		Integer state = salePo.getState();//编辑的时候没必要改state的
		SalePo salePoBefore = null;
		if (state >= SalePoConst.approved) {//计算变更记录
			salePoBefore = salePoService.getSalePo(salePoCmd.getId());
		}
		BeanUtils.copyProperties(salePoCmd, salePo);// 会自动判断date是否相等，但是订单那边的为什么不会？
		salePo.setState(state);
		/*if (!StringUtils.equals(TimeUtil.dayFmat.format(salePoCmd.getDateExamine()), TimeUtil.dayFmat.format(salePoCmd.getDateExamine())))
			salePo.setDateExamine(TimeUtil.datToDate(salePoCmd.getDateExamine()));*/
		//列表
		if (salePoCmd.getSalePoListCmd() != null && !salePoCmd.getSalePoListCmd().isEmpty()) {
			List<SalePoListCmd> listCmds = salePoCmd.getSalePoListCmd();
			Set<SalePoList> lists = salePo.getSalePoLists();//
			if (lists == null) {
				lists = new HashSet<SalePoList>();
				salePo.setSalePoLists(lists);
			}
			Map<Integer, SalePoList> tempMap = new HashMap<>();
			for (SalePoList list : lists) {
				tempMap.put(list.getId(), list);
			}
			for (SalePoListCmd listCmd : listCmds) {
				if (listCmd.getId() == null) {// 增加
					SalePoList list = new SalePoList();
					BeanUtils.copyProperties(listCmd, list);
					list.setSalePo(salePo);
					lists.add(list);
				} else {// 删除或者修改
					if (tempMap.get(listCmd.getId()) != null) {// 修改操作
						BeanUtils.copyProperties(listCmd, tempMap.get(listCmd.getId()));
						tempMap.remove(listCmd.getId());// 移除掉修改的，剩下的就应该删除操作
					} else {// 增加，多人操作可能会导致：A先查出，B删除，A再插入。
						SalePoList list = new SalePoList();
						BeanUtils.copyProperties(listCmd, list);
						list.setSalePo(salePo);
						lists.add(list);
					}
				}
			}
			for (SalePoList h : tempMap.values()) {// 删除操作，级联删除起作用的前提是关联的集合对象不能又一次指向新的引用，必须在原有的集合里操作新增、删除、清空元素，像上面的setXXX(null)的方法等是起步到级联删除作用的，大概是Hibernate自认自己原生的集合对象吧。自己New的放进行级联删除无效！
				lists.remove(h);
			}
		} else {
			if(salePo.getSalePoLists() != null) salePo.getSalePoLists().clear();
		}
		//System.err.println(salePo);
		salePoService.edit(salePo);
		salePoAltFacade.addEditRecord(salePoBefore, salePoCmd, currUserId);
	}

	@Override
	public void updateApprover(User user, Integer poId) {
		SalePo salePo = salePoService.get(poId);
		salePo.setApproverId(user.getId());
		salePo.setApprover(user.getUsername());
		salePo.setState(SalePoConst.submitaudit);
		salePoService.update(salePo);
	}

	@Override
	public void audit(Integer currUserId, Integer state, Integer poId) {
		SalePo salePo = salePoService.get(poId);
		if (state == 1) {
			salePo.setState(SalePoConst.approved);
		} else {
			salePo.setState(SalePoConst.rejected);
		}
		salePoService.update(salePo);
		if (state == 1) {
			salePoAltFacade.addSalePoUnreadInfo(currUserId, poId);
		}
	}
}
