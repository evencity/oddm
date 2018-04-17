package com.apical.oddm.facade.manufacture.impl;

import java.io.Serializable;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apical.oddm.application.produce.ManufactureShellServiceI;
import com.apical.oddm.core.constant.ManufactureConst;
import com.apical.oddm.core.model.produce.Manufacture;
import com.apical.oddm.core.model.produce.ManufactureShell;
import com.apical.oddm.facade.manufacture.ManufactureAltFacade;
import com.apical.oddm.facade.manufacture.ManufactureShellFacade;
import com.apical.oddm.facade.manufacture.command.ManufactureShellCommand;
import com.apical.oddm.facade.manufacture.dto.ManufactureShellDTO;

@Component("manufactureShellFacade")
public class ManufactureShellFacadeImpl implements ManufactureShellFacade {

	@Autowired
	private ManufactureShellServiceI manufactureShellService;
	@Autowired
	private ManufactureAltFacade manufactureAltFacade;

	@Override
	public ManufactureShellDTO add(ManufactureShellCommand manufactureShellCommand,Integer currUserId,String currUserName) {
		// TODO Auto-generated method stub
		if(manufactureShellCommand != null){
			ManufactureShell manufactureShell = new ManufactureShell();
			BeanUtils.copyProperties(manufactureShellCommand, manufactureShell);
			if(manufactureShellCommand.getManufactureId() != null){
				Manufacture manufacture = new Manufacture(manufactureShellCommand.getManufactureId());
				manufactureShell.setOrderManufacture(manufacture);
			}
			Serializable add = manufactureShellService.add(manufactureShell);
			if (add != null) {
				
				//变更记录
				if(manufactureShellCommand.getState() >= ManufactureConst.approved){
					manufactureAltFacade.addShellRecord(manufactureShellCommand, currUserId, currUserName);
				}
				
				ManufactureShellDTO manufactureShellDTO = new ManufactureShellDTO();
				BeanUtils.copyProperties(manufactureShellCommand, manufactureShellDTO);
				manufactureShellDTO.setId((Integer)add);
				return manufactureShellDTO;
			}else {
				return null;
			}
		}
		return null;
	}

	@Override
	public ManufactureShellDTO edit(ManufactureShellCommand manufactureShellCommand,Integer currUserId,String currUserName) {
		// TODO Auto-generated method stub
		if(manufactureShellCommand != null){
			//变更记录
			ManufactureShell manufactureShell2 = null;
			if(manufactureShellCommand.getState() >= ManufactureConst.approved){
				manufactureShell2 = manufactureShellService.get(manufactureShellCommand.getId());
			}
			
			ManufactureShellDTO manufactureShellDTO = new ManufactureShellDTO();
			ManufactureShell manufactureShell = new ManufactureShell();
			BeanUtils.copyProperties(manufactureShellCommand, manufactureShell);
			if(manufactureShellCommand.getManufactureId() != null){
				Manufacture manufacture = new Manufacture(manufactureShellCommand.getManufactureId());
				manufactureShell.setOrderManufacture(manufacture);
			}
			manufactureShellService.edit(manufactureShell);
			
			//添加纪录
			manufactureAltFacade.editShellRecord(manufactureShell2, manufactureShellCommand, currUserId, currUserName);
			
			BeanUtils.copyProperties(manufactureShellCommand, manufactureShellDTO);
			return manufactureShellDTO;
		}
		return null;
	}

	@Override
	public void delete(Integer id,Integer state,Integer currUserId,String currUserName) {
		// TODO Auto-generated method stub
		if(id != null){
			ManufactureShell manufactureShell = null;
			//变更记录
			if(state >= ManufactureConst.approved){
				manufactureShell = manufactureShellService.get(id);
			}
			manufactureShellService.delete(id);
			
			manufactureAltFacade.delShellRecord(manufactureShell, currUserId, currUserName);
		}
	}

	@Override
	public ManufactureShellDTO get(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
}
