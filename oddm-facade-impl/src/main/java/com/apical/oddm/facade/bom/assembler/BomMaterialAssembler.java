package com.apical.oddm.facade.bom.assembler;

import java.util.ArrayList;
import java.util.List;

import com.apical.oddm.core.model.bom.BomMaterial;
import com.apical.oddm.facade.bom.dto.BomMaterialDTO;
import com.apical.oddm.facade.util.TimeUtil;

public class BomMaterialAssembler {

public static BomMaterialDTO toBomMaterialDTO(BomMaterial bomMaterial){
		
		if(bomMaterial == null){
			return null;
		}
		BomMaterialDTO bomMaterialDTO = new BomMaterialDTO();
		bomMaterialDTO.setDescription(bomMaterial.getDescription());
		bomMaterialDTO.setSpecification(bomMaterial.getSpecification());
		bomMaterialDTO.setMaterialName(bomMaterial.getMaterialName());
		bomMaterialDTO.setMtlCode(bomMaterial.getMtlCode());
		return bomMaterialDTO;
	}
	
	public static BomMaterial toBomMaterialDTO(BomMaterialDTO bomMaterial){
		
		if(bomMaterial == null){
			return null;
		}
		BomMaterial bomMaterialDTO = new BomMaterial();
		bomMaterialDTO.setDescription(bomMaterial.getDescription());
		bomMaterialDTO.setSpecification(bomMaterial.getSpecification());
		bomMaterialDTO.setMaterialName(bomMaterial.getMaterialName());
		bomMaterialDTO.setMtlCode(bomMaterial.getMtlCode());
		return bomMaterialDTO;
	}
	
	public static List<BomMaterialDTO> toBomMaterialDTOs(List<BomMaterial> bomMaterial){
		
		if(bomMaterial == null){
			return null;
		}
		List<BomMaterialDTO> bomMaterials = new ArrayList<BomMaterialDTO>();
		for(int i = 0; i < bomMaterial.size(); i++){
			BomMaterialDTO bomMaterialDTO = new BomMaterialDTO();
			bomMaterialDTO.setDescription(bomMaterial.get(i).getDescription());
			bomMaterialDTO.setSpecification(bomMaterial.get(i).getSpecification());
			bomMaterialDTO.setMaterialName(bomMaterial.get(i).getMaterialName());
			bomMaterialDTO.setMtlCode(bomMaterial.get(i).getMtlCode());
			bomMaterialDTO.setUpdatetime(TimeUtil.timestampToStringDetaile(bomMaterial.get(i).getUpdatetime()));

			bomMaterials.add(bomMaterialDTO);
		}
		return bomMaterials;
	}
}
