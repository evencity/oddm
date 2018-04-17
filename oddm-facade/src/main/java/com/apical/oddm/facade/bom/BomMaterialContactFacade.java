package com.apical.oddm.facade.bom;

import java.util.List;

import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.bom.dto.BomMaterialContactDTO;
import com.apical.oddm.facade.pageModel.DataGrid;

public interface BomMaterialContactFacade {

public DataGrid getBomMaterialContactInPage(BomMaterialContactDTO bomMaterialContact, PageCommand pageCommand);
	
	public int addBomMaterialContact(BomMaterialContactDTO bomMaterialContact);
	
	public int updateBomMaterialContact(BomMaterialContactDTO bomMaterialContact);
	
	public boolean delBomMaterialContact(int ids,int mtcId);
	
	public BomMaterialContactDTO getBomMaterialContactById(int id);
	
	public List<BomMaterialContactDTO> getBomMaterialContacts();
}
