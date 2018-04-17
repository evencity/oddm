package com.apical.oddm.facade.bom.assembler;

import java.util.ArrayList;
import java.util.List;

import com.apical.oddm.core.model.bom.BomInfo;
import com.apical.oddm.facade.bom.dto.BomInfoDTO;
import com.apical.oddm.facade.util.TimeUtil;

public class BomInfoAssembler {

	public static BomInfoDTO toBomInfoDTO(BomInfo bomInfo){
		
		if(bomInfo == null){
			return null;
		}
		BomInfoDTO bomInfoDTO = new BomInfoDTO();
		bomInfoDTO.setBrand(bomInfo.getBrand());
		bomInfoDTO.setDescription(bomInfo.getDescription());
		bomInfoDTO.setId(bomInfo.getId());
		bomInfoDTO.setMaker(bomInfo.getMaker());
		bomInfoDTO.setProductName(bomInfo.getProductName());
		bomInfoDTO.setSpecification(bomInfo.getSpecification());
		if(bomInfo.getState() == 1){
			bomInfoDTO.setState("编辑状态");
		}else if(bomInfo.getState() == 2){
			bomInfoDTO.setState("正式创建");
		}else if(bomInfo.getState() == 3){
			bomInfoDTO.setState("完结");
		}
		
		bomInfoDTO.setTimestamp(TimeUtil.timestampToStrings(bomInfo.getTimestamp()));
		//bomInfoDTO.setTitle(bomInfo.getTitle());
		bomInfoDTO.setUpdatetime(TimeUtil.timestampToStrings(bomInfo.getUpdatetime()));
		bomInfoDTO.setVersion(bomInfo.getVersion());
		bomInfoDTO.setMaterialCode(bomInfo.getMaterialCode());
		bomInfoDTO.setBomMaterialRefs(BomMaterialRefAssembler.toBomMaterialRefDTOs(bomInfo.getBomMaterialRefs()));
		return bomInfoDTO;
	}
	
	public static BomInfo toBomInfoDTO(BomInfoDTO bomInfo){
		
		if(bomInfo == null){
			return null;
		}
		BomInfo bomInfoDTO = new BomInfo();
		bomInfoDTO.setBrand(bomInfo.getBrand());
		bomInfoDTO.setDescription(bomInfo.getDescription());
		bomInfoDTO.setId(bomInfo.getId());
		bomInfoDTO.setMaker(bomInfo.getMaker());
		bomInfoDTO.setProductName(bomInfo.getProductName());
		bomInfoDTO.setSpecification(bomInfo.getSpecification());
		if(bomInfo.getState() != null && bomInfo.getState() == "准备状态"){
			bomInfoDTO.setState(1);
		}
		if(bomInfo.getTimestamp() != null){
			bomInfoDTO.setTimestamp(TimeUtil.stringToTimestamps(bomInfo.getTimestamp()));
		}
		if(bomInfo.getUpdatetime() != null){
			bomInfoDTO.setUpdatetime(TimeUtil.stringToTimestamps(bomInfo.getUpdatetime()));
		}
		//bomInfoDTO.setTitle(bomInfo.getTitle());
		
		bomInfoDTO.setVersion(bomInfo.getVersion());
		bomInfoDTO.setMaterialCode(bomInfo.getMaterialCode());
		return bomInfoDTO;
	}
	
	public static List<BomInfoDTO> toBomInfoDTOs(List<BomInfo> bomInfo){
		
		if(bomInfo == null){
			return null;
		}
		List<BomInfoDTO> bomInfos = new ArrayList<BomInfoDTO>();
		for(int i = 0; i < bomInfo.size(); i++){
			BomInfoDTO bomInfoDTO = new BomInfoDTO();
			bomInfoDTO.setBrand(bomInfo.get(i).getBrand());
			bomInfoDTO.setDescription(bomInfo.get(i).getDescription());
			bomInfoDTO.setId(bomInfo.get(i).getId());
			bomInfoDTO.setMaker(bomInfo.get(i).getMaker());
			bomInfoDTO.setOrderNo(bomInfo.get(i).getOrderInfo().getOrderNo());
			bomInfoDTO.setQuantity(bomInfo.get(i).getOrderInfo().getQuantity()+"");
			bomInfoDTO.setProductName(bomInfo.get(i).getProductName());
			bomInfoDTO.setSpecification(bomInfo.get(i).getSpecification());
			if(bomInfo.get(i).getState() == 1){
				bomInfoDTO.setState("1");
			}else if(bomInfo.get(i).getState() == 2){
				bomInfoDTO.setState("2");
			}else if(bomInfo.get(i).getState() == 3){
				bomInfoDTO.setState("3");
			}
			bomInfoDTO.setTimestamp(TimeUtil.timestampToStrings(bomInfo.get(i).getTimestamp()));
			//bomInfoDTO.setTitle(bomInfo.get(i).getTitle());
			bomInfoDTO.setUpdatetime(TimeUtil.timestampToStrings(bomInfo.get(i).getUpdatetime()));
			bomInfoDTO.setVersion(bomInfo.get(i).getVersion());
			bomInfoDTO.setMaterialCode(bomInfo.get(i).getMaterialCode());
			bomInfos.add(bomInfoDTO);
		}
		return bomInfos;
	}
}
