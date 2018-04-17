package com.apical.oddm.facade.encase.assembler;

import java.util.ArrayList;
import java.util.List;

import com.apical.oddm.core.model.encase.InvoiceInfo;
import com.apical.oddm.facade.encase.dto.InvoiceInfoDTO;
import com.apical.oddm.facade.util.TimeUtil;

public class InvoiceAssembler {

	public static InvoiceInfoDTO toInvoiceInfoDTO(InvoiceInfo invoiceInfo, Long sum, Double count){
		
		if(invoiceInfo == null){
			return null;
		}
		InvoiceInfoDTO invoiceInfoDTO = new InvoiceInfoDTO();
		invoiceInfoDTO.setAddress(invoiceInfo.getAddress());
		invoiceInfoDTO.setBrand(invoiceInfo.getBrand());
		invoiceInfoDTO.setCompanyAddr(invoiceInfo.getCompanyAddr());
		invoiceInfoDTO.setCompanyName(invoiceInfo.getCompanyName());
		invoiceInfoDTO.setId(invoiceInfo.getId());
		invoiceInfoDTO.setCurrency(invoiceInfo.getCurrency());
		//invoiceInfoDTO.setDateInvoice(TimeUtil.dateToString(invoiceInfo.getDateInvoice()));
		invoiceInfoDTO.setFax(invoiceInfo.getFax());
		invoiceInfoDTO.setIncoterms(invoiceInfo.getIncoterms());
		invoiceInfoDTO.setName(invoiceInfo.getName());
		invoiceInfoDTO.setOrigion(invoiceInfo.getOrigion());
		invoiceInfoDTO.setPayment(invoiceInfo.getPayment());
		invoiceInfoDTO.setPiNo(invoiceInfo.getPiNo());
		invoiceInfoDTO.setShippingMethod(invoiceInfo.getShippingMethod());
		invoiceInfoDTO.setTel(invoiceInfo.getTel());
		invoiceInfoDTO.setTimestamp(TimeUtil.timestampToStrings(invoiceInfo.getTimestamp()));
		invoiceInfoDTO.setTo_(invoiceInfo.getTo_());
	//	invoiceInfoDTO.setSum(sum);
	//	invoiceInfoDTO.setCount(count);
		
		return invoiceInfoDTO;
	}
	
public static InvoiceInfoDTO toInvoiceInfoDTO(InvoiceInfo invoiceInfo){
		
		if(invoiceInfo == null){
			return null;
		}
		InvoiceInfoDTO invoiceInfoDTO = new InvoiceInfoDTO();
		invoiceInfoDTO.setAddress(invoiceInfo.getAddress());
		invoiceInfoDTO.setBrand(invoiceInfo.getBrand());
		invoiceInfoDTO.setCompanyAddr(invoiceInfo.getCompanyAddr());
		invoiceInfoDTO.setCompanyName(invoiceInfo.getCompanyName());
		invoiceInfoDTO.setId(invoiceInfo.getId());
		invoiceInfoDTO.setCurrency(invoiceInfo.getCurrency());
		//invoiceInfoDTO.setDateInvoice(TimeUtil.dateToString(invoiceInfo.getDateInvoice()));
		invoiceInfoDTO.setFax(invoiceInfo.getFax());
		invoiceInfoDTO.setIncoterms(invoiceInfo.getIncoterms());
		invoiceInfoDTO.setName(invoiceInfo.getName());
		invoiceInfoDTO.setOrigion(invoiceInfo.getOrigion());
		invoiceInfoDTO.setPayment(invoiceInfo.getPayment());
		invoiceInfoDTO.setPiNo(invoiceInfo.getPiNo());
		invoiceInfoDTO.setShippingMethod(invoiceInfo.getShippingMethod());
		invoiceInfoDTO.setTel(invoiceInfo.getTel());
		invoiceInfoDTO.setTimestamp(TimeUtil.timestampToStrings(invoiceInfo.getTimestamp()));
		invoiceInfoDTO.setTo_(invoiceInfo.getTo_());
		
		return invoiceInfoDTO;
	}
	
	public static InvoiceInfo toInvoiceInfoDTO(InvoiceInfoDTO invoiceInfo){
		
		if(invoiceInfo == null){
			return null;
		}
		InvoiceInfo invoiceInfoDTO = new InvoiceInfo();
		invoiceInfoDTO.setAddress(invoiceInfo.getAddress());
		invoiceInfoDTO.setBrand(invoiceInfo.getBrand());
		invoiceInfoDTO.setCompanyAddr(invoiceInfo.getCompanyAddr());
		invoiceInfoDTO.setCompanyName(invoiceInfo.getCompanyName());
		invoiceInfoDTO.setId(invoiceInfo.getId());
		invoiceInfoDTO.setCurrency(invoiceInfo.getCurrency());
		//invoiceInfoDTO.setDateInvoice(TimeUtil.stringToDate(invoiceInfo.getDateInvoice()));
		invoiceInfoDTO.setFax(invoiceInfo.getFax());
		invoiceInfoDTO.setIncoterms(invoiceInfo.getIncoterms());
		invoiceInfoDTO.setName(invoiceInfo.getName());
		invoiceInfoDTO.setOrigion(invoiceInfo.getOrigion());
		invoiceInfoDTO.setPayment(invoiceInfo.getPayment());
		invoiceInfoDTO.setPiNo(invoiceInfo.getPiNo());
		invoiceInfoDTO.setShippingMethod(invoiceInfo.getShippingMethod());
		invoiceInfoDTO.setTel(invoiceInfo.getTel());
		invoiceInfoDTO.setTimestamp(TimeUtil.stringToTimestamp(invoiceInfo.getTimestamp()));
		invoiceInfoDTO.setTo_(invoiceInfo.getTo_());
		
		return invoiceInfoDTO;
	}
	
	public static List<InvoiceInfoDTO> toInvoiceInfoDTOs(List<InvoiceInfo> invoiceInfo){
		
		if(invoiceInfo == null){
			return null;
		}
		List<InvoiceInfoDTO> invoiceInfos = new ArrayList<InvoiceInfoDTO>();
		for(int i = 0; i < invoiceInfo.size(); i++){
			InvoiceInfoDTO invoiceInfoDTO = new InvoiceInfoDTO();
			invoiceInfoDTO.setAddress(invoiceInfo.get(i).getAddress());
			invoiceInfoDTO.setBrand(invoiceInfo.get(i).getBrand());
			invoiceInfoDTO.setCompanyAddr(invoiceInfo.get(i).getCompanyAddr());
			invoiceInfoDTO.setCompanyName(invoiceInfo.get(i).getCompanyName());
			invoiceInfoDTO.setId(invoiceInfo.get(i).getId());
			invoiceInfoDTO.setCurrency(invoiceInfo.get(i).getCurrency());
		//	invoiceInfoDTO.setDateInvoice(TimeUtil.dateToString(invoiceInfo.get(i).getDateInvoice()));
			invoiceInfoDTO.setFax(invoiceInfo.get(i).getFax());
			invoiceInfoDTO.setIncoterms(invoiceInfo.get(i).getIncoterms());
			invoiceInfoDTO.setName(invoiceInfo.get(i).getName());
			invoiceInfoDTO.setOrigion(invoiceInfo.get(i).getOrigion());
			invoiceInfoDTO.setPayment(invoiceInfo.get(i).getPayment());
			invoiceInfoDTO.setPiNo(invoiceInfo.get(i).getPiNo());
			invoiceInfoDTO.setShippingMethod(invoiceInfo.get(i).getShippingMethod());
			invoiceInfoDTO.setTel(invoiceInfo.get(i).getTel());
			invoiceInfoDTO.setTimestamp(TimeUtil.timestampToStrings(invoiceInfo.get(i).getTimestamp()));
			invoiceInfoDTO.setTo_(invoiceInfo.get(i).getTo_());
			invoiceInfos.add(invoiceInfoDTO);
		}
		return invoiceInfos;
	}
}
