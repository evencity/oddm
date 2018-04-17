package com.apical.oddm.facade.encase.impl;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apical.oddm.application.encase.InvoiceInfoServiceI;
import com.apical.oddm.application.encase.InvoiceListServiceI;
import com.apical.oddm.application.order.OrderInfoServiceI;
import com.apical.oddm.core.model.encase.InvoiceInfo;
import com.apical.oddm.core.model.encase.InvoiceList;
import com.apical.oddm.core.model.order.OrderInfo;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.page.SystemContext;
import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.base.dto.BasePage;
import com.apical.oddm.facade.encase.dto.InvoiceInfoDTO;
import com.apical.oddm.facade.encase.dto.InvoiceListDTO;
import com.apical.oddm.facade.encase.facade.InvoiceFacade;
import com.apical.oddm.facade.order.command.OrderInfoCommand;
import com.apical.oddm.facade.order.dto.OrderInfoDTO;
import com.apical.oddm.facade.util.TimeUtil;
import com.apical.oddm.infra.util.OddmDateUtil;

@Component("invoiceFacade")
public class InvoiceFacadeImpl implements InvoiceFacade {

	@Autowired
	private InvoiceInfoServiceI invoiceInfoService;
	
	@Autowired
	private InvoiceListServiceI invoiceListService;
	
	@Autowired
	private OrderInfoServiceI orderInfoService;
	
	@Override
	public BasePage<InvoiceInfoDTO> dataGrid(InvoiceInfoDTO invoiceInfoCommand, PageCommand pageCommand) throws ParseException {
		SystemContext.setPageOffset(pageCommand.getPage());
		SystemContext.setPageSize(pageCommand.getRows());
		SystemContext.setOrder(pageCommand.getOrder());
		SystemContext.setSort(pageCommand.getSort());
		Pager<InvoiceInfo> dataGrid = null;
		if(invoiceInfoCommand != null){
			InvoiceInfo invoiceInfoQuery = new InvoiceInfo(); 
			BeanUtils.copyProperties(invoiceInfoCommand, invoiceInfoQuery);
			if (StringUtils.isNotBlank(invoiceInfoCommand.getDateInvoiceStart())) {
				invoiceInfoQuery.setDateInvoiceStart(OddmDateUtil.dayParse(invoiceInfoCommand.getDateInvoiceStart()));
			}
			if (StringUtils.isNotBlank(invoiceInfoCommand.getDateInvoiceEnd())) {
				invoiceInfoQuery.setDateInvoiceEnd(OddmDateUtil.dayParse(invoiceInfoCommand.getDateInvoiceEnd()));
			}
			dataGrid = invoiceInfoService.dataGrid(invoiceInfoQuery);
		}
		
		BasePage<InvoiceInfoDTO> basePage = new BasePage<InvoiceInfoDTO>();
		if(dataGrid != null && dataGrid.getTotal() > 0){
			List<InvoiceInfoDTO> list = new ArrayList<InvoiceInfoDTO>();
			for(InvoiceInfo invoiceInfo : dataGrid.getRows()){
				InvoiceInfoDTO invoiceInfoDTO = new InvoiceInfoDTO();
				BeanUtils.copyProperties(invoiceInfo, invoiceInfoDTO);
				if(invoiceInfo.getDateInvoice() != null){
					invoiceInfoDTO.setDateInvoiceString(TimeUtil.dateToString(invoiceInfo.getDateInvoice()));
				}
				if(invoiceInfo.getUpdatetime() != null){
					invoiceInfoDTO.setUpdatetime(TimeUtil.timestampToStringDetaile(invoiceInfo.getUpdatetime()));
				}
				list.add(invoiceInfoDTO);
			}
			basePage.setRows(list);
			basePage.setTotal(dataGrid.getTotal());
			
		}
		return basePage;
	}

	@Override
	public InvoiceInfoDTO getInvoiceInfoById(Integer id) {
		// TODO Auto-generated method stub
		InvoiceInfoDTO invoiceInfoDTO = new InvoiceInfoDTO();
		if(id != null){
			InvoiceInfo invoiceInfo = invoiceInfoService.getDetail(id);
			BeanUtils.copyProperties(invoiceInfo, invoiceInfoDTO);
			if(invoiceInfo.getDateInvoice() != null){
				invoiceInfoDTO.setDateInvoiceString(TimeUtil.dateToString(invoiceInfo.getDateInvoice()));
			}
			System.err.println(invoiceInfo.getAmount()+"...."+invoiceInfo.getQtys());
			if(invoiceInfo.getInvoiceLists() != null && invoiceInfo.getInvoiceLists().size() > 0){
				List<InvoiceListDTO> list = new ArrayList<InvoiceListDTO>(); 
				for(InvoiceList invoiceList : invoiceInfo.getInvoiceLists()){
					InvoiceListDTO invoiceListDTO = new InvoiceListDTO();
					BeanUtils.copyProperties(invoiceList, invoiceListDTO);
					if(invoiceInfo.getDateInvoice() != null){
						invoiceInfoDTO.setDateInvoiceString(TimeUtil.dateToString(invoiceInfo.getDateInvoice()));
					}
					//invoiceListDTO.setInvoiceInfoDTO(invoiceInfoDTO);
					list.add(invoiceListDTO);
				}
				invoiceInfoDTO.setInvoiceListDTOs(list);
			}
			
		}
		return invoiceInfoDTO;
	}

	@Override
	public void edit(InvoiceInfoDTO invoice) {
		// TODO Auto-generated method stub
		if(invoice != null && invoice.getId() != null){
			InvoiceInfo invoiceInfo = invoiceInfoService.getDetail(invoice.getId());
			if(invoiceInfo != null){
				BeanUtils.copyProperties(invoice, invoiceInfo);
				if(invoice.getInvoiceListDTOs() != null && !invoice.getInvoiceListDTOs().isEmpty()){
					List<InvoiceListDTO> invoiceListDTOs = invoice.getInvoiceListDTOs();
					Set<InvoiceList> invoiceLists = invoiceInfo.getInvoiceLists();//获取原来的对象集合
					if(invoiceLists == null){
						invoiceLists = new LinkedHashSet<InvoiceList>();//为空则初始化
						invoiceInfo.setInvoiceLists(invoiceLists);
					}
					Map<Integer, InvoiceList> tempMap = new HashMap<>();
					for(InvoiceList invoiceList : invoiceLists){
						tempMap.put(invoiceList.getId(), invoiceList);
					}
					for(InvoiceListDTO invoiceListDTO : invoiceListDTOs ){
						if(invoiceListDTO.getId() == null){//id为空则是添加
							InvoiceList invoiceList = new InvoiceList();
							BeanUtils.copyProperties(invoiceListDTO, invoiceList);
							invoiceList.setInvoiceInfo(invoiceInfo);
							invoiceLists.add(invoiceList);
						}else{//修改和删除
							if(tempMap.get(invoiceListDTO.getId()) != null){
								BeanUtils.copyProperties(invoiceListDTO, tempMap.get(invoiceListDTO.getId()) );
								tempMap.remove(invoiceListDTO.getId());
							}
						}
					}
					for (InvoiceList h : tempMap.values()) {// 删除操作，级联删除起作用的前提是关联的集合对象不能又一次指向新的引用，必须在原有的集合里操作新增、删除、清空元素，像上面的setXXX(null)的方法等是起步到级联删除作用的，大概是Hibernate自认自己原生的集合对象吧。自己New的放进行级联删除无效！
						invoiceLists.remove(h);
					}
				}else{
					Set<InvoiceList> invoiceLists = invoiceInfo.getInvoiceLists();//获取原来的对象集合
					if(invoiceLists != null && invoiceLists.size() > 0){
						invoiceLists.clear();
					}
				}
				invoiceInfoService.edit(invoiceInfo);
			}
		}
	}

	@Override
	public InvoiceInfo getInvoiceById(Integer id) {
		// TODO Auto-generated method stub
		if(id != null){
			return invoiceInfoService.getDetail(id);
		}
		return null;
	}

	@Override
	public Integer add(InvoiceInfoDTO invoice) {
		// TODO Auto-generated method stub
		if(invoice != null){
			InvoiceInfo invoiceInfo = new InvoiceInfo();
			BeanUtils.copyProperties(invoice, invoiceInfo);
			
			if(invoice.getInvoiceListDTOs() != null && invoice.getInvoiceListDTOs().size() > 0){
				List<InvoiceListDTO> invoiceListDTOs = invoice.getInvoiceListDTOs();
				Set<InvoiceList> set = new LinkedHashSet<InvoiceList>();
				for(InvoiceListDTO invoiceListDTO : invoiceListDTOs){
					InvoiceList invoiceList = new InvoiceList();
					BeanUtils.copyProperties(invoiceListDTO, invoiceList);
					invoiceList.setInvoiceInfo(invoiceInfo);
					set.add(invoiceList);
				}
				invoiceInfo.setInvoiceLists(set);
			}
			Serializable add = invoiceInfoService.add(invoiceInfo);
			if(add != null){
				return (Integer) add;
			}
		}
		return null;
	}

	@Override
	public void detele(Integer id) {
		// TODO Auto-generated method stub
		if(id != null){
			invoiceInfoService.delete(id);
		}
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
		} 
		return null;
	}

	@Override
	public InvoiceInfo get(Integer id) {
		// TODO Auto-generated method stub
		if(id != null){
			InvoiceInfo invoiceInfo = invoiceInfoService.get(id);
			if(invoiceInfo != null){
				return invoiceInfo;
			}
		}
		return null;
	}
	
	
}
