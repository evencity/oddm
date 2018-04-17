package com.apical.oddm.facade.encase.assembler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.apical.oddm.core.model.encase.InvoiceList;
import com.apical.oddm.facade.encase.dto.InvoiceListDTO;

public class InvoiceListAssembler {
public static InvoiceListDTO toInvoiceListDTO(InvoiceList InvoiceList){
		
		if(InvoiceList == null){
			return null;
		}
		InvoiceListDTO InvoiceListDTO = new InvoiceListDTO();
		InvoiceListDTO.setDescription(InvoiceList.getDescription());
		InvoiceListDTO.setId(InvoiceList.getId());
		InvoiceListDTO.setModel(InvoiceList.getModel());
		InvoiceListDTO.setOrderNo(InvoiceList.getOrderNo());
		//InvoiceListDTO.setQty(InvoiceList.getQty().toString());
		//InvoiceListDTO.setUnitPrice(InvoiceList.getUnitPrice().toString());
		
		return InvoiceListDTO;
	}
	
	public static InvoiceList toInvoiceListDTO(InvoiceListDTO InvoiceList){
		
		if(InvoiceList == null){
			return null;
		}
		InvoiceList InvoiceListDTO = new InvoiceList();
		InvoiceListDTO.setDescription(InvoiceList.getDescription());
		InvoiceListDTO.setId(InvoiceList.getId());
		InvoiceListDTO.setModel(InvoiceList.getModel());
		InvoiceListDTO.setOrderNo(InvoiceList.getOrderNo());
		//InvoiceListDTO.setQty(Integer.valueOf(InvoiceList.getQty().substring(1)));
		//InvoiceListDTO.setUnitPrice(new BigDecimal(InvoiceList.getUnitPrice().substring(1)));
		
		return InvoiceListDTO;
	}
	
	public static List<InvoiceListDTO> toInvoiceListDTOs(List<InvoiceList> InvoiceList){
		
		if(InvoiceList == null){
			return null;
		}
		List<InvoiceListDTO> InvoiceLists = new ArrayList<InvoiceListDTO>();
		for(int i = 0; i < InvoiceList.size(); i++){
			InvoiceListDTO InvoiceListDTO = new InvoiceListDTO();
			InvoiceListDTO.setDescription(InvoiceList.get(i).getDescription().replace("\n", "<br>"));
			InvoiceListDTO.setId(InvoiceList.get(i).getId());
			InvoiceListDTO.setModel(InvoiceList.get(i).getModel());
			InvoiceListDTO.setOrderNo(InvoiceList.get(i).getOrderNo());
			//InvoiceListDTO.setQty(InvoiceList.get(i).getQty().toString());
			//InvoiceListDTO.setUnitPrice(InvoiceList.get(i).getUnitPrice().toString());
			InvoiceLists.add(InvoiceListDTO);
		}
		return InvoiceLists;
	}
}
