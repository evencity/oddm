package com.apical.oddm.facade.sale.impl;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apical.oddm.application.sale.SaleLcServiceI;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.sale.SaleLc;
import com.apical.oddm.facade.sale.lc.SaleLcCmd;
import com.apical.oddm.facade.sale.lc.SaleLcDto;
import com.apical.oddm.facade.sale.lc.SaleLcFacadeI;
import com.apical.oddm.infra.util.OddmDateUtil;

@Component("saleLcFacade")
public class SaleLcFacadeImpl implements SaleLcFacadeI {

	@Autowired
	private SaleLcServiceI saleLcService;
	
	@Override
	public Pager<SaleLcDto> dataGrid(SaleLcCmd saleLcCmd) throws ParseException {
		Pager<SaleLcDto> page = null;
		SaleLc saleLc = null;
		if (saleLcCmd != null) {
			saleLc = new SaleLc();
			BeanUtils.copyProperties(saleLcCmd, saleLc);
			if (StringUtils.isNotBlank(saleLcCmd.getDateDeliveryStart())) {
				saleLc.setDateDeliveryStart(OddmDateUtil.dayParse(saleLcCmd.getDateDeliveryStart()));
			}
			if (StringUtils.isNotBlank(saleLcCmd.getDateDeliveryEnd())) {
				saleLc.setDateDeliveryEnd(OddmDateUtil.dayParse(saleLcCmd.getDateDeliveryEnd()));
			}
		}
		Pager<SaleLc> dataGrid = saleLcService.dataGrid(saleLc);
		if (dataGrid != null) {
			page = new Pager<SaleLcDto>();
			BeanUtils.copyProperties(dataGrid, page);
			if (dataGrid.getRows() != null) {
				List<SaleLcDto> rows = new ArrayList<SaleLcDto>();
				page.setRows(rows);
				for (SaleLc po : dataGrid.getRows()) {
					SaleLcDto SaleLcDto = new SaleLcDto();
					BeanUtils.copyProperties(po, SaleLcDto);
					rows.add(SaleLcDto);
				}
			}
		}
		return page;
	}

	@Override
	public Serializable add(SaleLcCmd saleLcCmd) {
		//System.err.println(saleLcCmd);
		SaleLc saleLc = new SaleLc();
		BeanUtils.copyProperties(saleLcCmd, saleLc);
		return saleLcService.add(saleLc);
	}

	@Override
	public SaleLcDto get(Integer id) {
		SaleLc saleLc = saleLcService.get(id);
		SaleLcDto saleLcDto = new SaleLcDto();
		BeanUtils.copyProperties(saleLc, saleLcDto);
		return saleLcDto;
	}

	@Override
	public void edit(SaleLcCmd saleLcCmd) {
		SaleLc saleLc = new SaleLc();
		BeanUtils.copyProperties(saleLcCmd, saleLc);
		saleLcService.edit(saleLc);
	}

	@Override
	public void delete(Integer id) {
		saleLcService.delete(id);
	}

	@Override
	public List<SaleLcDto> list(SaleLcCmd saleLcCmd) throws ParseException {
		SaleLc saleLc = new SaleLc();
		BeanUtils.copyProperties(saleLcCmd, saleLc);
		if (StringUtils.isNotBlank(saleLcCmd.getDateDeliveryStart())) {
			saleLc.setDateDeliveryStart(OddmDateUtil.dayParse(saleLcCmd.getDateDeliveryStart()));
		}
		if (StringUtils.isNotBlank(saleLcCmd.getDateDeliveryEnd())) {
			saleLc.setDateDeliveryEnd(OddmDateUtil.dayParse(saleLcCmd.getDateDeliveryEnd()));
		}

		List<SaleLc> listSaleLc = saleLcService.list(saleLc);
		List<SaleLcDto> listSaleLcDto = new LinkedList<SaleLcDto>();
		for (SaleLc s: listSaleLc) {
			SaleLcDto dto = new SaleLcDto();
			BeanUtils.copyProperties(s, dto);
			listSaleLcDto.add(dto);
		}
		return listSaleLcDto;
	}
}
