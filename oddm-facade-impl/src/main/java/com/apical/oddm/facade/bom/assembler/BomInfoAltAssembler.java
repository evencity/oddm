//package com.apical.oddm.facade.bom.assembler;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import com.apical.oddm.core.model.bom.BomInfoAlt;
//import com.apical.oddm.facade.bom.dto.BomInfoAltDTO;
//import com.apical.oddm.facade.util.TimeUtil;
//
//public class BomInfoAltAssembler {
//
//	public static BomInfoAltDTO toBomInfoAltDTO(BomInfoAlt BomInfoAlt){
//		
//		if(BomInfoAlt == null){
//			return null;
//		}
//		BomInfoAltDTO BomInfoAltDTO = new BomInfoAltDTO();
//		BomInfoAltDTO.setAfterInfo(BomInfoAltDTO.set.getAfterInfo());
//		BomInfoAltDTO.setAlteritem(BomInfoAlt.getAlteritem());
//		BomInfoAltDTO.setAltitemDesc(BomInfoAlt.getAltitemDesc());
//		BomInfoAltDTO.setBeforeInfo(BomInfoAlt.getBeforeInfo());
//		BomInfoAltDTO.setId(BomInfoAlt.getId());
//		BomInfoAltDTO.setOperator(BomInfoAlt.getOperator());
//		BomInfoAltDTO.setTimestamp(BomInfoAlt.getTimestamp());
//		
//		return BomInfoAltDTO;
//	}
//	
//	public static BomInfoAlt toBomInfoAltDTO(BomInfoAltDTO BomInfoAlt){
//		
//		if(BomInfoAlt == null){
//			return null;
//		}
//		BomInfoAlt BomInfoAltDTO = new BomInfoAlt();
//		BomInfoAltDTO.setBrand(BomInfoAlt.getBrand());
//		BomInfoAltDTO.setDescription(BomInfoAlt.getDescription());
//		BomInfoAltDTO.setId(BomInfoAlt.getId());
//		BomInfoAltDTO.setMaker(BomInfoAlt.getMaker());
//		BomInfoAltDTO.setProductName(BomInfoAlt.getProductName());
//		BomInfoAltDTO.setSpecification(BomInfoAlt.getSpecification());
//		if(BomInfoAlt.getState() != null && BomInfoAlt.getState() == "准备状态"){
//			BomInfoAltDTO.setState(1);
//		}
//		if(BomInfoAlt.getTimestamp() != null){
//			BomInfoAltDTO.setTimestamp(TimeUtil.stringToTimestamps(BomInfoAlt.getTimestamp()));
//		}
//		if(BomInfoAlt.getUpdatetime() != null){
//			BomInfoAltDTO.setUpdatetime(TimeUtil.stringToTimestamps(BomInfoAlt.getUpdatetime()));
//		}
//		BomInfoAltDTO.setTitle(BomInfoAlt.getTitle());
//		
//		BomInfoAltDTO.setVersion(BomInfoAlt.getVersion());
//		BomInfoAltDTO.setMaterialCode(BomInfoAlt.getMaterialCode());
//		return BomInfoAltDTO;
//	}
//	
//	public static List<BomInfoAltDTO> toBomInfoAltDTOs(List<BomInfoAlt> BomInfoAlt){
//		
//		if(BomInfoAlt == null){
//			return null;
//		}
//		List<BomInfoAltDTO> BomInfoAlts = new ArrayList<BomInfoAltDTO>();
//		for(int i = 0; i < BomInfoAlt.size(); i++){
//			BomInfoAltDTO BomInfoAltDTO = new BomInfoAltDTO();
//			BomInfoAltDTO.setBrand(BomInfoAlt.get(i).getBrand());
//			BomInfoAltDTO.setDescription(BomInfoAlt.get(i).getDescription());
//			BomInfoAltDTO.setId(BomInfoAlt.get(i).getId());
//			BomInfoAltDTO.setMaker(BomInfoAlt.get(i).getMaker());
//			BomInfoAltDTO.setOrderNo(BomInfoAlt.get(i).getOrderInfo().getOrderNo());
//			BomInfoAltDTO.setQuantity(BomInfoAlt.get(i).getOrderInfo().getQuantity()+"");
//			BomInfoAltDTO.setProductName(BomInfoAlt.get(i).getProductName());
//			BomInfoAltDTO.setSpecification(BomInfoAlt.get(i).getSpecification());
//			if(BomInfoAlt.get(i).getState() == 1){
//				BomInfoAltDTO.setState("1");
//			}else if(BomInfoAlt.get(i).getState() == 2){
//				BomInfoAltDTO.setState("2");
//			}else if(BomInfoAlt.get(i).getState() == 3){
//				BomInfoAltDTO.setState("3");
//			}
//			BomInfoAltDTO.setTimestamp(TimeUtil.timestampToStrings(BomInfoAlt.get(i).getTimestamp()));
//			BomInfoAltDTO.setTitle(BomInfoAlt.get(i).getTitle());
//			BomInfoAltDTO.setUpdatetime(TimeUtil.timestampToStrings(BomInfoAlt.get(i).getUpdatetime()));
//			BomInfoAltDTO.setVersion(BomInfoAlt.get(i).getVersion());
//			BomInfoAltDTO.setMaterialCode(BomInfoAlt.get(i).getMaterialCode());
//			BomInfoAlts.add(BomInfoAltDTO);
//		}
//		return BomInfoAlts;
//	}
//}
