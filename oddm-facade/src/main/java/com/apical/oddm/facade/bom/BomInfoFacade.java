package com.apical.oddm.facade.bom;



import com.apical.oddm.core.model.bom.BomInfo;
import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.bom.dto.BomInfoDTO;
import com.apical.oddm.facade.pageModel.DataGrid;

public interface BomInfoFacade {

	public DataGrid getBomInfoInPage(BomInfoDTO bomInfoDTO, PageCommand pageCommand);
	
	public boolean addBomInfo(BomInfoDTO bomInfo);
	
	public boolean updateBomInfo(BomInfoDTO bomInfo,int curretUserId);
	
	public boolean delBomInfo(int ids);
	
	public BomInfoDTO getBomInfoById(int id);
	
	public Integer checkOrderNo(String orderNo);
	
	public BomInfoDTO getBomInfoByOrderNo(int orderNoId);
	
	public String reportData(int id);
	
	public boolean delBomMaterialRef(int id);
	
	public BomInfo getBomBomMaterial(String BomMaterial);
}
