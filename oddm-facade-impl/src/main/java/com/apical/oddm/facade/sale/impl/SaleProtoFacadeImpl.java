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

import com.apical.oddm.application.sale.SalePrototypeServiceI;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.sale.SalePrototype;
import com.apical.oddm.facade.sale.proto.SaleProtoCmd;
import com.apical.oddm.facade.sale.proto.SaleProtoDto;
import com.apical.oddm.facade.sale.proto.SaleProtoFacadeI;
import com.apical.oddm.infra.util.OddmDateUtil;

@Component("saleProtoFacade")
public class SaleProtoFacadeImpl implements SaleProtoFacadeI {

	@Autowired
	private SalePrototypeServiceI saleProtoService;
	
	@Override
	public Pager<SaleProtoDto> dataGrid(SaleProtoCmd saleProtoCmd) throws ParseException {
		Pager<SaleProtoDto> page = null;
		SalePrototype saleProto = null;
		if (saleProtoCmd != null) {
			saleProto = new SalePrototype();
			BeanUtils.copyProperties(saleProtoCmd, saleProto);
			if (StringUtils.isNotBlank(saleProtoCmd.getDateSendStart())) {
				saleProto.setDateSendStart(OddmDateUtil.dayParse(saleProtoCmd.getDateSendStart()));
			}
			if (StringUtils.isNotBlank(saleProtoCmd.getDateSendEnd())) {
				saleProto.setDateSendEnd(OddmDateUtil.dayParse(saleProtoCmd.getDateSendEnd()));
			}
		}
		Pager<SalePrototype> dataGrid = saleProtoService.dataGrid(saleProto);
		if (dataGrid != null) {
			page = new Pager<SaleProtoDto>();
			BeanUtils.copyProperties(dataGrid, page);
			if (dataGrid.getRows() != null) {
				List<SaleProtoDto> rows = new ArrayList<SaleProtoDto>();
				page.setRows(rows);
				for (SalePrototype po : dataGrid.getRows()) {
					SaleProtoDto SaleProtoDto = new SaleProtoDto();
					BeanUtils.copyProperties(po, SaleProtoDto);
					rows.add(SaleProtoDto);
				}
			}
		}
		return page;
	}

	@Override
	public Serializable add(SaleProtoCmd saleProtoCmd) {
		//System.err.println(saleProtoCmd);
		SalePrototype saleProto = new SalePrototype();
		BeanUtils.copyProperties(saleProtoCmd, saleProto);
		return saleProtoService.add(saleProto);
	}

	@Override
	public SaleProtoDto get(Integer id) {
		SalePrototype saleProto = saleProtoService.get(id);
		SaleProtoDto saleProtoDto = new SaleProtoDto();
		BeanUtils.copyProperties(saleProto, saleProtoDto);
		return saleProtoDto;
	}

	@Override
	public void edit(SaleProtoCmd saleProtoCmd) {
		SalePrototype saleProto = new SalePrototype();
		BeanUtils.copyProperties(saleProtoCmd, saleProto);
		saleProtoService.edit(saleProto);
	}

	@Override
	public void delete(Integer id) {
		saleProtoService.delete(id);
	}

	@Override
	public List<SaleProtoDto> list(SaleProtoCmd saleProtoCmd) throws ParseException {
		SalePrototype saleProto = new SalePrototype();
		BeanUtils.copyProperties(saleProtoCmd, saleProto);
		if (StringUtils.isNotBlank(saleProtoCmd.getDateSendStart())) {
			saleProto.setDateSendStart(OddmDateUtil.dayParse(saleProtoCmd.getDateSendStart()));
		}
		if (StringUtils.isNotBlank(saleProtoCmd.getDateSendEnd())) {
			saleProto.setDateSendEnd(OddmDateUtil.dayParse(saleProtoCmd.getDateSendEnd()));
		}

		List<SalePrototype> listSaleProto = saleProtoService.list(saleProto);
		List<SaleProtoDto> listSaleProtoDto = new LinkedList<SaleProtoDto>();
		for (SalePrototype s: listSaleProto) {
			SaleProtoDto dto = new SaleProtoDto();
			BeanUtils.copyProperties(s, dto);
			listSaleProtoDto.add(dto);
		}
		return listSaleProtoDto;
	}

}
