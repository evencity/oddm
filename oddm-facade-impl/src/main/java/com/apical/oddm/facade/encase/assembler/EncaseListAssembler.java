package com.apical.oddm.facade.encase.assembler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.apical.oddm.core.model.encase.EncaseList;
import com.apical.oddm.facade.encase.dto.EncaseListDTO;


public class EncaseListAssembler {

		public static EncaseListDTO toEncaseListDTO(EncaseList encaseList){
		
		if(encaseList == null){
			return null;
		}
		EncaseListDTO encaseListDTO = new EncaseListDTO();
		encaseListDTO.setCNo(encaseList.getCNo());
		encaseListDTO.setDescription(encaseList.getDescription());
		encaseListDTO.setgW(encaseList.getGW());
		encaseListDTO.setHight(encaseList.getHight());
		encaseListDTO.setId(encaseList.getId());
		encaseListDTO.setItemNo(encaseList.getItemNo());
		encaseListDTO.setLength(encaseList.getLength());
		encaseListDTO.setnW(encaseList.getNW());
		encaseListDTO.setOrderNo(encaseList.getOrderNo());
		encaseListDTO.setProductFatory(encaseList.getProductFatory());
		encaseListDTO.setQty(encaseList.getQty());
		encaseListDTO.setQtyCtn(encaseList.getQtyCtn());
		encaseListDTO.setRemark(encaseList.getRemark());
		encaseListDTO.setUnit(encaseList.getUnit());
		encaseListDTO.setWidth(encaseList.getWidth());
		
		return encaseListDTO;
	}
	
	public static EncaseList toEncaseListDTO(EncaseListDTO encaseList){
		
		if(encaseList == null){
			return null;
		}
		EncaseList encaseListDTO = new EncaseList();
		encaseListDTO.setCNo(encaseList.getCNo());
		encaseListDTO.setDescription(encaseList.getDescription());
		encaseListDTO.setGW(encaseList.getgW());
		encaseListDTO.setHight(encaseList.getHight());
		encaseListDTO.setId(encaseList.getId());
		encaseListDTO.setItemNo(encaseList.getItemNo());
		encaseListDTO.setLength(encaseList.getLength());
		encaseListDTO.setNW(encaseList.getnW());
		encaseListDTO.setOrderNo(encaseList.getOrderNo());
		encaseListDTO.setProductFatory(encaseList.getProductFatory());
		encaseListDTO.setQty(encaseList.getQty());
		encaseListDTO.setQtyCtn(encaseList.getQtyCtn());
		encaseListDTO.setRemark(encaseList.getRemark());
		encaseListDTO.setUnit(encaseList.getUnit());
		encaseListDTO.setWidth(encaseList.getWidth());
		
		return encaseListDTO;
	}
	
	public static List<EncaseListDTO> toEncaseListDTOs(List<EncaseList> encaseList){
		
		if(encaseList == null){
			return null;
		}
		List<EncaseListDTO> encaseLists = new ArrayList<EncaseListDTO>();
		for(int i = 0; i < encaseList.size(); i++){
			EncaseListDTO encaseListDTO = new EncaseListDTO();
			encaseListDTO.setCNo(encaseList.get(i).getCNo());
			encaseListDTO.setDescription(encaseList.get(i).getDescription());
			encaseListDTO.setgW(encaseList.get(i).getGW());
			encaseListDTO.setHight(encaseList.get(i).getHight());
			encaseListDTO.setId(encaseList.get(i).getId());
			encaseListDTO.setItemNo(encaseList.get(i).getItemNo());
			encaseListDTO.setLength(encaseList.get(i).getLength());
			encaseListDTO.setnW(encaseList.get(i).getNW());
			encaseListDTO.setOrderNo(encaseList.get(i).getOrderNo());
			encaseListDTO.setProductFatory(encaseList.get(i).getProductFatory());
			encaseListDTO.setQty(encaseList.get(i).getQty());
			encaseListDTO.setQtyCtn(encaseList.get(i).getQtyCtn());
			encaseListDTO.setRemark(encaseList.get(i).getRemark());
			encaseListDTO.setUnit(encaseList.get(i).getUnit());
			encaseListDTO.setWidth(encaseList.get(i).getWidth());
			encaseLists.add(encaseListDTO);
		}
		return encaseLists;
	}
	
	public static List<EncaseList> toEncaseLists(Set<EncaseListDTO> encaseList){
			
			if(encaseList == null){
				return null;
			}
			List<EncaseList> encaseLists = new ArrayList<EncaseList>();
			List<EncaseListDTO> encaseListzs = new ArrayList<EncaseListDTO>();
			encaseListzs.addAll(encaseList);
			for(int i = 0; i < encaseList.size(); i++){
				EncaseList encaseListzsz = new EncaseList();
				encaseListzsz.setCNo(encaseListzs.get(i).getCNo());
				encaseListzsz.setDescription(encaseListzs.get(i).getDescription());
				encaseListzsz.setGW(encaseListzs.get(i).getgW());
				encaseListzsz.setHight(encaseListzs.get(i).getHight());
				encaseListzsz.setId(encaseListzs.get(i).getId());
				encaseListzsz.setItemNo(encaseListzs.get(i).getItemNo());
				encaseListzsz.setLength(encaseListzs.get(i).getLength());
				encaseListzsz.setNW(encaseListzs.get(i).getnW());
				encaseListzsz.setOrderNo(encaseListzs.get(i).getOrderNo());
				encaseListzsz.setProductFatory(encaseListzs.get(i).getProductFatory());
				encaseListzsz.setQty(encaseListzs.get(i).getQty());
				encaseListzsz.setQtyCtn(encaseListzs.get(i).getQtyCtn());
				encaseListzsz.setRemark(encaseListzs.get(i).getRemark());
				encaseListzsz.setUnit(encaseListzs.get(i).getUnit());
				encaseListzsz.setWidth(encaseListzs.get(i).getWidth());
				encaseLists.add(encaseListzsz);
			}
			return encaseLists;
		}
	
		public static List<EncaseListDTO> toEncaseListDTOs(Set<EncaseList> encaseList){
		
			if(encaseList == null){
				return null;
			}
			List<EncaseListDTO> encaseLists = new ArrayList<EncaseListDTO>();
			 Iterator<EncaseList> it = encaseList.iterator();
			  while(it.hasNext()){
				EncaseList o=it.next();
				EncaseListDTO encaseListDTO = new EncaseListDTO();
				encaseListDTO.setCNo(o.getCNo());
				encaseListDTO.setDescription(o.getDescription());
				encaseListDTO.setgW(o.getGW());
				encaseListDTO.setHight(o.getHight());
				encaseListDTO.setId(o.getId());
				encaseListDTO.setItemNo(o.getItemNo());
				encaseListDTO.setLength(o.getLength());
				encaseListDTO.setnW(o.getNW());
				encaseListDTO.setOrderNo(o.getOrderNo());
				encaseListDTO.setProductFatory(o.getProductFatory());
				encaseListDTO.setQty(o.getQty());
				encaseListDTO.setQtyCtn(o.getQtyCtn());
				encaseListDTO.setRemark(o.getRemark());
				encaseListDTO.setUnit(o.getUnit());
				encaseListDTO.setWidth(o.getWidth());
				encaseLists.add(encaseListDTO);
			}
			return encaseLists;
		}
}
