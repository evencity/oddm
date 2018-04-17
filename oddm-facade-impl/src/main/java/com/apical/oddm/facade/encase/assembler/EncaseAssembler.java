package com.apical.oddm.facade.encase.assembler;

import java.util.ArrayList;
import java.util.List;

import com.apical.oddm.core.model.encase.EncaseInfo;
import com.apical.oddm.facade.encase.dto.EncaseInfoDTO;
import com.apical.oddm.facade.util.TimeUtil;

public class EncaseAssembler {

	public static EncaseInfoDTO toEncaseInfoDTO(EncaseInfo encase, Long total, Long count){
		
		if(encase == null){
			return null;
		}
		EncaseInfoDTO encaseDTO = new EncaseInfoDTO();
		encaseDTO.setAddress(encase.getAddress());
		encaseDTO.setCompany(encase.getCompany());
		encaseDTO.setDescription(encase.getDescription());
		encaseDTO.setEncaseDate(TimeUtil.dateToString(encase.getEncaseDate()));
		encaseDTO.setHomepage(encase.getHomepage());
		encaseDTO.setId(encase.getId());
		encaseDTO.setName(encase.getName());
		encaseDTO.setTelphone(encase.getTelphone());
		encaseDTO.setTimestamp(TimeUtil.timestampToString(encase.getTimestamp()));
		encaseDTO.setUnitLength(encase.getUnitLength());
		encaseDTO.setUnitWeight(encase.getUnitWeight());
		encaseDTO.setZipcode(encase.getZipcode());
		encaseDTO.setTotal(total);
		encaseDTO.setCount(count);
		
		return encaseDTO;
	}
		
	public static EncaseInfoDTO toEncaseInfoDTO(EncaseInfo encase){
		
		if(encase == null){
			return null;
		}
		EncaseInfoDTO encaseDTO = new EncaseInfoDTO();
		encaseDTO.setAddress(encase.getAddress());
		encaseDTO.setCompany(encase.getCompany());
		encaseDTO.setDescription(encase.getDescription());
		encaseDTO.setEncaseDate(TimeUtil.dateToString(encase.getEncaseDate()));
		encaseDTO.setHomepage(encase.getHomepage());
		encaseDTO.setId(encase.getId());
		encaseDTO.setName(encase.getName());
		encaseDTO.setTelphone(encase.getTelphone());
		encaseDTO.setTimestamp(TimeUtil.timestampToString(encase.getTimestamp()));
		encaseDTO.setUnitLength(encase.getUnitLength());
		encaseDTO.setUnitWeight(encase.getUnitWeight());
		encaseDTO.setZipcode(encase.getZipcode());
		
		return encaseDTO;
	}
	
	public static EncaseInfo toEncaseInfoDTO(EncaseInfoDTO encase){
		
		if(encase == null){
			return null;
		}
		EncaseInfo encaseDTO = new EncaseInfo();
		encaseDTO.setAddress(encase.getAddress());
		encaseDTO.setCompany(encase.getCompany());
		encaseDTO.setDescription(encase.getDescription());
		encaseDTO.setEncaseDate(TimeUtil.stringToDate(encase.getEncaseDate()));
		encaseDTO.setHomepage(encase.getHomepage());
		encaseDTO.setId(encase.getId());
		encaseDTO.setName(encase.getName());
		encaseDTO.setTelphone(encase.getTelphone());
		encaseDTO.setTimestamp(TimeUtil.stringToTimestamps(encase.getTimestamp()));
		encaseDTO.setUnitLength(encase.getUnitLength());
		encaseDTO.setUnitWeight(encase.getUnitWeight());
		encaseDTO.setZipcode(encase.getZipcode());
		
		return encaseDTO;
	}
	
	public static List<EncaseInfoDTO> toEncaseInfoDTOs(List<EncaseInfo> encase){
		
		if(encase == null){
			return null;
		}
		List<EncaseInfoDTO> encases = new ArrayList<EncaseInfoDTO>();
		for(int i = 0; i < encase.size(); i++){
			EncaseInfoDTO encaseDTO = new EncaseInfoDTO();
			encaseDTO.setAddress(encase.get(i).getAddress());
			encaseDTO.setCompany(encase.get(i).getCompany());
			encaseDTO.setDescription(encase.get(i).getDescription());
			encaseDTO.setEncaseDate(TimeUtil.dateToString(encase.get(i).getEncaseDate()));
			encaseDTO.setHomepage(encase.get(i).getHomepage());
			encaseDTO.setId(encase.get(i).getId());
			encaseDTO.setName(encase.get(i).getName());
			encaseDTO.setTelphone(encase.get(i).getTelphone());
			encaseDTO.setTimestamp(TimeUtil.timestampToString(encase.get(i).getTimestamp()));
			encaseDTO.setUnitLength(encase.get(i).getUnitLength());
			encaseDTO.setUnitWeight(encase.get(i).getUnitWeight());
			encaseDTO.setZipcode(encase.get(i).getZipcode());
			encases.add(encaseDTO);
		}
		return encases;
	}
}
