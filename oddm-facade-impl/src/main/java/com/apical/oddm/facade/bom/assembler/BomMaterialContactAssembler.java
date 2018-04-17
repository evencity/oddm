package com.apical.oddm.facade.bom.assembler;

import java.util.ArrayList;
import java.util.List;

import com.apical.oddm.core.model.bom.BomMaterialContact;
import com.apical.oddm.facade.bom.dto.BomMaterialContactDTO;

public class BomMaterialContactAssembler {

public static BomMaterialContactDTO toBomMaterialContactDTO(BomMaterialContact bomMaterialContact){
		
		if(bomMaterialContact == null){
			return null;
		}
		BomMaterialContactDTO bomMaterialContactDTO = new BomMaterialContactDTO();
		bomMaterialContactDTO.setCellphone(bomMaterialContact.getCellphone());
		bomMaterialContactDTO.setCompany(bomMaterialContact.getCompany());
		bomMaterialContactDTO.setContacts(bomMaterialContact.getContacts());
		bomMaterialContactDTO.setEmail(bomMaterialContact.getEmail());
		bomMaterialContactDTO.setFax(bomMaterialContact.getFax());
		bomMaterialContactDTO.setId(bomMaterialContact.getId());
		bomMaterialContactDTO.setTelphone(bomMaterialContact.getTelphone());
		return bomMaterialContactDTO;
	}
	
	public static BomMaterialContact toBomMaterialContactDTO(BomMaterialContactDTO bomMaterialContact){
		
		if(bomMaterialContact == null){
			return null;
		}
		BomMaterialContact bomMaterialContactDTO = new BomMaterialContact();
		bomMaterialContactDTO.setCellphone(bomMaterialContact.getCellphone());
		bomMaterialContactDTO.setCompany(bomMaterialContact.getCompany());
		bomMaterialContactDTO.setContacts(bomMaterialContact.getContacts());
		bomMaterialContactDTO.setEmail(bomMaterialContact.getEmail());
		bomMaterialContactDTO.setFax(bomMaterialContact.getFax());
		bomMaterialContactDTO.setId(bomMaterialContact.getId());
		bomMaterialContactDTO.setTelphone(bomMaterialContact.getTelphone());
		return bomMaterialContactDTO;
	}
	
	public static List<BomMaterialContactDTO> toBomMaterialContactDTOs(List<BomMaterialContact> bomMaterialContact){
		
		if(bomMaterialContact == null){
			return null;
		}
		List<BomMaterialContactDTO> bomMaterialContacts = new ArrayList<BomMaterialContactDTO>();
		for(int i = 0; i < bomMaterialContact.size(); i++){
			BomMaterialContactDTO bomMaterialContactDTO = new BomMaterialContactDTO();
			bomMaterialContactDTO.setCellphone(bomMaterialContact.get(i).getCellphone());
			bomMaterialContactDTO.setCompany(bomMaterialContact.get(i).getCompany());
			bomMaterialContactDTO.setContacts(bomMaterialContact.get(i).getContacts());
			bomMaterialContactDTO.setEmail(bomMaterialContact.get(i).getEmail());
			bomMaterialContactDTO.setFax(bomMaterialContact.get(i).getFax());
			bomMaterialContactDTO.setId(bomMaterialContact.get(i).getId());
			bomMaterialContactDTO.setTelphone(bomMaterialContact.get(i).getTelphone());
			bomMaterialContacts.add(bomMaterialContactDTO);
		}
		return bomMaterialContacts;
	}
}
