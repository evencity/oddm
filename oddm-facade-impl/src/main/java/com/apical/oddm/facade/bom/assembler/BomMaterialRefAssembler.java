package com.apical.oddm.facade.bom.assembler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.apical.oddm.core.model.bom.BomMaterialRef;
import com.apical.oddm.facade.bom.dto.BomMaterialRefDTO;

public class BomMaterialRefAssembler {

public static BomMaterialRefDTO toBomMaterialRefDTO(BomMaterialRef bomMaterialRef){
		
		if(bomMaterialRef == null){
			return null;
		}
		BomMaterialRefDTO bomMaterialRefDTO = new BomMaterialRefDTO();
		bomMaterialRefDTO.setBrand(bomMaterialRef.getBrand());
		bomMaterialRefDTO.setDescription(bomMaterialRef.getDescription());
		bomMaterialRefDTO.setId(bomMaterialRef.getId());
		bomMaterialRefDTO.setMaterialCode(bomMaterialRef.getMaterialCode());
		bomMaterialRefDTO.setProductName(bomMaterialRef.getProductName());
		bomMaterialRefDTO.setSeq(bomMaterialRef.getSeq());
		bomMaterialRefDTO.setSpecification(bomMaterialRef.getSpecification());
		bomMaterialRefDTO.setType(bomMaterialRef.getType());
		bomMaterialRefDTO.setUpdatetime(bomMaterialRef.getUpdatetime());
		if(bomMaterialRef.getUsageAmount2() !=null && bomMaterialRef.getUsageAmount2() == 1){
			bomMaterialRefDTO.setUsageAmount(bomMaterialRef.getUsageAmount1().toString());
		}else if(bomMaterialRef.getUsageAmount2() != null){
			bomMaterialRefDTO.setUsageAmount(Integer.valueOf(bomMaterialRef.getUsageAmount1())+"/"+Integer.valueOf(bomMaterialRef.getUsageAmount2()));
		}else{
			bomMaterialRefDTO.setUsageAmount("");
		}
		return bomMaterialRefDTO;
	}
	
	public static BomMaterialRef toBomMaterialRefDTO(BomMaterialRefDTO bomMaterialRef){
		
		if(bomMaterialRef == null){
			return null;
		}
		BomMaterialRef bomMaterialRefDTO = new BomMaterialRef();
		bomMaterialRefDTO.setBrand(bomMaterialRef.getBrand());
		bomMaterialRefDTO.setDescription(bomMaterialRef.getDescription());
		bomMaterialRefDTO.setId(bomMaterialRef.getId());
		bomMaterialRefDTO.setMaterialCode(bomMaterialRef.getMaterialCode());
		bomMaterialRefDTO.setProductName(bomMaterialRef.getProductName());
		bomMaterialRefDTO.setSeq(bomMaterialRef.getSeq());
		bomMaterialRefDTO.setSpecification(bomMaterialRef.getSpecification());
		bomMaterialRefDTO.setType(bomMaterialRef.getType());
		bomMaterialRefDTO.setUpdatetime(bomMaterialRef.getUpdatetime());
		bomMaterialRefDTO.setUsageAmount1(bomMaterialRef.getUsageAmount1());
		bomMaterialRefDTO.setUsageAmount2(bomMaterialRef.getUsageAmount2());
		
		return bomMaterialRefDTO;
	}
	
	public static List<BomMaterialRefDTO> toBomMaterialRefDTOs(Set<BomMaterialRef> bomMaterialRef){
		
		if(bomMaterialRef == null){
			return null;
		}
		List<BomMaterialRefDTO> bomMaterialRefs = new ArrayList<BomMaterialRefDTO>();
		List<BomMaterialRef> btr = new ArrayList<BomMaterialRef>();
        btr.addAll(bomMaterialRef);
		for(BomMaterialRef btrz:btr){
			BomMaterialRefDTO bomMaterialRefDTO = new BomMaterialRefDTO();
			bomMaterialRefDTO.setBrand(btrz.getBrand());
			bomMaterialRefDTO.setDescription(btrz.getDescription());
			bomMaterialRefDTO.setId(btrz.getId());
			bomMaterialRefDTO.setMaterialCode(btrz.getMaterialCode());
			bomMaterialRefDTO.setProductName(btrz.getProductName());
			bomMaterialRefDTO.setSeq(btrz.getSeq());
			bomMaterialRefDTO.setSpecification(btrz.getSpecification());
			bomMaterialRefDTO.setType(btrz.getType());
			bomMaterialRefDTO.setUpdatetime(btrz.getUpdatetime());
			if(btrz.getUsageAmount2() !=null && btrz.getUsageAmount2() == 1){
				bomMaterialRefDTO.setUsageAmount(btrz.getUsageAmount1()+"");
			}else if(btrz.getUsageAmount2() !=null && btrz.getUsageAmount1() != null){
				bomMaterialRefDTO.setUsageAmount(btrz.getUsageAmount1()+"/"+btrz.getUsageAmount2());
			}else{
				bomMaterialRefDTO.setUsageAmount("");
			}
			if(btrz.getContacts() != null){
				bomMaterialRefDTO.setContacts(btrz.getContacts().getContacts());
				bomMaterialRefDTO.setContactId(btrz.getContacts().getId());
				if(btrz.getContacts().getTelphone()!=null){
					bomMaterialRefDTO.setTelphone(btrz.getContacts().getTelphone());
				}else{
					bomMaterialRefDTO.setTelphone("");
				}
				if(btrz.getContacts().getEmail()!=null){
					bomMaterialRefDTO.setEmail(btrz.getContacts().getEmail());
				}else{
					bomMaterialRefDTO.setEmail("");
				}
				if(btrz.getContacts().getCellphone()!=null){
					bomMaterialRefDTO.setCellphone(btrz.getContacts().getCellphone());
				}else{
					bomMaterialRefDTO.setCellphone("");
				}
				if(btrz.getContacts().getCompany()!=null){
					bomMaterialRefDTO.setCompany(btrz.getContacts().getCompany());
				}else{
					bomMaterialRefDTO.setCompany("");
				}
			}else{
				bomMaterialRefDTO.setContacts("");
			}
			bomMaterialRefs.add(bomMaterialRefDTO);
		}
		return bomMaterialRefs;
	}

	
}
