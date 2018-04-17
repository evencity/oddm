package com.apical.oddm.facade.sale.impl;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apical.oddm.application.material.ProductServiceI;
import com.apical.oddm.application.order.OrderInfoServiceI;
import com.apical.oddm.application.sale.SaleInfoServiceI;
import com.apical.oddm.core.model.material.Product;
import com.apical.oddm.core.model.order.OrderInfo;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.sale.SaleInfo;
import com.apical.oddm.core.vo.sale.SaleInfoAllYearVo;
import com.apical.oddm.core.vo.sale.SaleInfoYearVo;
import com.apical.oddm.facade.sale.saleInfo.SaleInfoFacadeI;
import com.apical.oddm.facade.sale.saleInfo.cmd.SaleInfoCmd;
import com.apical.oddm.facade.sale.saleInfo.dto.SaleInfoDto;
import com.apical.oddm.facade.sale.saleInfo.dto.SaleInfoYearDto;
import com.apical.oddm.infra.util.OddmDateUtil;

@Component("saleInfoFacade")
public class SaleInfoFacadeImpl implements SaleInfoFacadeI {

	@Autowired
	private SaleInfoServiceI saleInfoService;
	
	@Autowired
	private OrderInfoServiceI orderInfoService;
	
	@Autowired
	private ProductServiceI productService;
	
	@Override
	public Pager<SaleInfoDto> dataGrid(SaleInfoCmd saleInfoCmd) throws ParseException {
		Pager<SaleInfoDto> page = null;
		SaleInfo saleInfo = null;
		if (saleInfoCmd != null) {
			saleInfo = new SaleInfo();
			BeanUtils.copyProperties(saleInfoCmd, saleInfo);
			OrderInfo orderInfo = new OrderInfo();
			saleInfo.setOrderInfo(orderInfo);
			BeanUtils.copyProperties(saleInfoCmd, orderInfo);
			if (StringUtils.isNotBlank(saleInfoCmd.getDateOrderStart())) {
				saleInfo.setDateOrderStart(OddmDateUtil.dayParse(saleInfoCmd.getDateOrderStart()));
			}
			if (StringUtils.isNotBlank(saleInfoCmd.getDateOrderEnd())) {
				saleInfo.setDateOrderEnd(OddmDateUtil.dayParse(saleInfoCmd.getDateOrderEnd()));
			}
		}
		Pager<SaleInfo> dataGrid = saleInfoService.dataGrid(saleInfo);
		if (dataGrid != null) {
			page = new Pager<SaleInfoDto>();
			BeanUtils.copyProperties(dataGrid, page);
			if (dataGrid.getRows() != null) {
				List<SaleInfoDto> rows = new ArrayList<SaleInfoDto>();
				page.setRows(rows);
				for (SaleInfo po : dataGrid.getRows()) {
					SaleInfoDto SaleInfoDto = new SaleInfoDto();
					//BeanUtils.copyProperties(po.getOrderInfo(), SaleInfoDto);
					//System.err.println("po.getUnread() ::"+po.getUnread());
					BeanUtils.copyProperties(po, SaleInfoDto);
					//System.err.println("SaleInfoDto.getUnread() ::"+SaleInfoDto.getUnread());
					rows.add(SaleInfoDto);
				}
			}
		}
		return page;
	}

	@Override
	public Serializable add(SaleInfoCmd saleInfoCmd) {
		SaleInfo saleInfo = new SaleInfo();
		BeanUtils.copyProperties(saleInfoCmd, saleInfo);
		saleInfo.setOrderInfo(new OrderInfo(saleInfoCmd.getOrderId()));
		return saleInfoService.add(saleInfo);
	}

	@Override
	public SaleInfoDto getBaseInfo(Integer orderId) {
		OrderInfo orderInfo = orderInfoService.get(orderId);
		SaleInfoDto saleInfoDto = new SaleInfoDto();
		BeanUtils.copyProperties(orderInfo, saleInfoDto);//注意 这里要把订单的id传出去
		saleInfoDto.setOrderId(orderId);
		if (StringUtils.isNotBlank(orderInfo.getProductFactory())) {
			Product product = productService.getProductTypeByName(orderInfo.getProductFactory());
			if (product != null && product.getProductType() != null) {
				String name = product.getProductType().getName();
				saleInfoDto.setProductType(name);
			}
		}
		return saleInfoDto;
	}

	@Override
	public OrderInfo getOrderInfo(SaleInfoCmd saleInfoCmd) {
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setOrderNo(saleInfoCmd.getOrderNo());
		orderInfo.setSellerId(saleInfoCmd.getSellerId());
		orderInfo.setMerchandiserId(saleInfoCmd.getMerchandiserId());
		return orderInfoService.getByOrder(orderInfo);
	}

	@Override
	public SaleInfoDto getDetail(Integer id) {
		SaleInfo detail = saleInfoService.getDetail(id);
		SaleInfoDto saleInfoDto = new SaleInfoDto();
		BeanUtils.copyProperties(detail, saleInfoDto);
		return saleInfoDto;
	}

	@Override
	public void edit(SaleInfoCmd saleInfoCmd) {
		SaleInfo saleInfo = saleInfoService.get(saleInfoCmd.getId());
		BeanUtils.copyProperties(saleInfoCmd, saleInfo);
		saleInfoService.edit(saleInfo);
	}

	@Override
	public List<SaleInfoDto> list(SaleInfoCmd saleInfoCmd) throws ParseException {
		SaleInfo saleInfo = new SaleInfo();
		OrderInfo orderInfo = new OrderInfo();
		saleInfo.setOrderInfo(orderInfo);
		BeanUtils.copyProperties(saleInfoCmd, saleInfo);
		BeanUtils.copyProperties(saleInfoCmd, orderInfo);
		if (StringUtils.isNotBlank(saleInfoCmd.getDateOrderStart())) {
			saleInfo.setDateOrderStart(OddmDateUtil.dayParse(saleInfoCmd.getDateOrderStart()));
		}
		if (StringUtils.isNotBlank(saleInfoCmd.getDateOrderEnd())) {
			saleInfo.setDateOrderEnd(OddmDateUtil.dayParse(saleInfoCmd.getDateOrderEnd()));
		}

		List<SaleInfo> listSaleInfo = saleInfoService.list(saleInfo);
		List<SaleInfoDto> listSaleInfoDto = new LinkedList<SaleInfoDto>();
		for (SaleInfo s: listSaleInfo) {
			SaleInfoDto dto = new SaleInfoDto();
			BeanUtils.copyProperties(s, dto);
			listSaleInfoDto.add(dto);
		}
		return listSaleInfoDto;
	}

	@Override
	public Pager<SaleInfoDto> dataGridStatisticsMonth(SaleInfoCmd saleInfoCmd)
			throws ParseException {
		Pager<SaleInfoDto> page = null;
		SaleInfo saleInfo = null;
		if (saleInfoCmd != null) {
			saleInfo = new SaleInfo();
			BeanUtils.copyProperties(saleInfoCmd, saleInfo);
			OrderInfo orderInfo = new OrderInfo();
			saleInfo.setOrderInfo(orderInfo);
			BeanUtils.copyProperties(saleInfoCmd, orderInfo);
			if (StringUtils.isNotBlank(saleInfoCmd.getDateOrderStart())) {
				saleInfo.setDateOrderStart(OddmDateUtil.dayParse(saleInfoCmd.getDateOrderStart()));
			}
			if (StringUtils.isNotBlank(saleInfoCmd.getDateOrderEnd())) {
				saleInfo.setDateOrderEnd(OddmDateUtil.dayParse(saleInfoCmd.getDateOrderEnd()));
			}
		}
		Pager<SaleInfo> dataGrid = saleInfoService.dataGrid(saleInfo);
		if (dataGrid != null) {
			page = new Pager<SaleInfoDto>();
			BeanUtils.copyProperties(dataGrid, page);
			if (dataGrid.getRows() != null) {
				List<SaleInfoDto> rows = new ArrayList<SaleInfoDto>();
				page.setRows(rows);
				Calendar calendar = Calendar.getInstance();
				calendar.setFirstDayOfWeek(Calendar.MONDAY);//设置星期一为一周的第一天。
				for (SaleInfo po : dataGrid.getRows()) {
					SaleInfoDto SaleInfoDto = new SaleInfoDto();
					//BeanUtils.copyProperties(po.getOrderInfo(), SaleInfoDto);
					//System.err.println("po.getUnread() ::"+po.getUnread());
					BeanUtils.copyProperties(po, SaleInfoDto);
					//System.err.println("SaleInfoDto.getUnread() ::"+SaleInfoDto.getUnread());
					if (po.getDateOrder() != null) {
						calendar.setTime(po.getDateOrder());
						int weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH);
						SaleInfoDto.setDateOrderWeek("第"+weekOfMonth+"周");
					}
					rows.add(SaleInfoDto);
				}
			}
		}
		return page;
	}

	/*
	 * List<SaleInfoYearDto> 原始数据格式
	 * month  seller  totals
	 * 1           徐细梅        541
	 * 1	   李晓磊        1700
	 * 2 	   徐细梅        222
	 * 2	   李晓磊        432
	 * 3	   徐细梅        2456
	 * 3	   李晓磊        3330
	 * */
	@Override
	public List<SaleInfoYearDto> getSaleInfoYearSellerVoList(Integer type,
			String year, String currency) {
		List<SaleInfoYearVo> voList = saleInfoService.getSaleInfoYearVoList(type, year, currency);
		//long startTime = System.currentTimeMillis();
		if (voList == null) return null;
		List<SaleInfoYearDto> dtoList = new LinkedList<SaleInfoYearDto>();
		HashMap<String, Map<Integer, Double>> sellerMap = new HashMap<String, Map<Integer, Double>>();
		HashMap<Integer, Double> monthMap = null;
		double totals = 0f;
		for (SaleInfoYearVo vo : voList) {
			if (vo.getTotals() != null) totals += vo.getTotals();
			if (sellerMap.get(vo.getName()) == null) {
				monthMap = new HashMap<Integer, Double>();
				sellerMap.put(vo.getName(), monthMap);
			}
			sellerMap.get(vo.getName()).put(vo.getMonth(), vo.getTotals());
		}
		SaleInfoYearDto dto = null;
		double total = 0f;
		double value = 0f;
		Map<Integer, Double> tempMap = null;
		for (Entry<String, Map<Integer, Double>> entry : sellerMap.entrySet()) {
			//System.out.println("key= " + entry.getKey() + "and  value= "+ entry.getValue());
			dto = new SaleInfoYearDto();
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
            	SaleInfoYearDto d1 = (SaleInfoYearDto)o1;
            	SaleInfoYearDto d2 = (SaleInfoYearDto)o2;
                return d2.getTotal().compareTo(d1.getTotal());
            }
         });
		//3毫秒内
		//System.err.println("shyong shijian "+( System.currentTimeMillis() - startTime));
		return dtoList;
	}

	@Override
	public List<SaleInfoAllYearVo> getSaleInfoAllYearVoList(String yearStart,
			String yearEnd, String currency) {
		return saleInfoService.getSaleInfoAllYearVoList(yearStart, yearEnd, currency);
	}

}
