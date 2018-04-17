package com.apical.oddm.facade.sale.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apical.oddm.application.sale.SalePiServiceI;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.sale.SalePi;
import com.apical.oddm.facade.sale.pi.SalePiCmd;
import com.apical.oddm.facade.sale.pi.SalePiDto;
import com.apical.oddm.facade.sale.pi.SalePiFacadeI;

@Component("salePiFacade")
public class SalePiFacadeImpl implements SalePiFacadeI {

	@Autowired
	private SalePiServiceI salePiService;
	
	@Override
	public Pager<SalePiDto> dataGrid(SalePiCmd salePiCmd) {
		Pager<SalePiDto> page = null;
		SalePi salePi = null;
		if (salePiCmd != null) {
			salePi = new SalePi();
			BeanUtils.copyProperties(salePiCmd, salePi);
		}
		Pager<SalePi> dataGrid = salePiService.dataGrid(salePi);
		if (dataGrid != null) {
			page = new Pager<SalePiDto>();
			BeanUtils.copyProperties(dataGrid, page);
			if (dataGrid.getRows() != null) {
				List<SalePiDto> rows = new ArrayList<SalePiDto>();
				page.setRows(rows);
				for (SalePi po : dataGrid.getRows()) {
					SalePiDto SalePiDto = new SalePiDto();
					BeanUtils.copyProperties(po, SalePiDto);
					rows.add(SalePiDto);
				}
			}
		}
		return page;
	}

	@Override
	public Serializable add(SalePiCmd salePiCmd) {
		//System.err.println(salePiCmd);
		SalePi salePi = new SalePi();
		BeanUtils.copyProperties(salePiCmd, salePi);
		return salePiService.add(salePi);
	}

	@Override
	public SalePiDto get(Integer id) {
		SalePi salePi = salePiService.get(id);
		SalePiDto salePiDto = new SalePiDto();
		BeanUtils.copyProperties(salePi, salePiDto);
		return salePiDto;
	}

	@Override
	public void edit(SalePiCmd salePiCmd) {
		SalePi salePi = new SalePi();
		BeanUtils.copyProperties(salePiCmd, salePi);
		salePiService.edit(salePi);
	}

	@Override
	public void delete(Integer id) {
		salePiService.delete(id);
	}

	@Override
	public SalePiDto getSalePi(SalePiCmd salePiCmd) {
		SalePi salePi = null;
		if (salePiCmd != null) {
			salePi = new SalePi();
			BeanUtils.copyProperties(salePiCmd, salePi);
		} else {
			return null;
		}
		SalePi salePi2 = salePiService.getSalePi(salePi);
		SalePiDto salePiDto = new SalePiDto();
		BeanUtils.copyProperties(salePi2, salePiDto);
		return salePiDto;
	}

	@Override
	public List<SalePiDto> list(SalePiCmd salePiCmd) {
		SalePi salePi = new SalePi();
		BeanUtils.copyProperties(salePiCmd, salePi);
		List<SalePi> listSalePi = salePiService.list(salePi);
		List<SalePiDto> listSalePiDto = new LinkedList<SalePiDto>();
		for (SalePi s: listSalePi) {
			SalePiDto dto = new SalePiDto();
			BeanUtils.copyProperties(s, dto);
			listSalePiDto.add(dto);
		}
		return listSalePiDto;
	}
}
