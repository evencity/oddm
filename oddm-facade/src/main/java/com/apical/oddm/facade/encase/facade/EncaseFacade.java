package com.apical.oddm.facade.encase.facade;

import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.encase.dto.EncaseInfoDTO;
import com.apical.oddm.facade.pageModel.DataGrid;

public interface EncaseFacade {

	public DataGrid getEncaseInfoInPage(EncaseInfoDTO encaseDTO, PageCommand pageCommand);
	
	public boolean addEncaseInfo(EncaseInfoDTO encaseInfo);
	
	public boolean updateEncaseInfo(EncaseInfoDTO encaseInfo);
	
	public boolean delEncaseInfo(int ids);
	
	public EncaseInfoDTO getEncaseInfoById(int id);
	
	public boolean delEncaseList(int id);
}
