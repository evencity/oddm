package com.apical.oddm.facade.bom;

import java.util.List;

import com.apical.oddm.core.model.bom.BomMaterial;
import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.bom.dto.BomMaterialDTO;
import com.apical.oddm.facade.pageModel.DataGrid;

public interface BomMaterialFacade {

public DataGrid getBomMaterialInPage(BomMaterialDTO bomMaterialDTO, PageCommand pageCommand);
	
	public boolean addBomMaterial(BomMaterialDTO bomMaterial);
	
	public void updateBomMaterial(BomMaterialDTO bomMaterial);
	
	public boolean delBomMaterial(String materialCode);
	
	public BomMaterialDTO getBomMaterialById(int id);
	
	public BomMaterialDTO getBomMaterialByNumber(String number);
	
	public boolean addBomMaterials(List<BomMaterial> bomMaterials);
}
