package com.apical.oddm.facade.manufacture.impl;

import java.io.Serializable;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apical.oddm.application.produce.ManufacturePackageServiceI;
import com.apical.oddm.core.constant.ManufactureConst;
import com.apical.oddm.core.model.produce.Manufacture;
import com.apical.oddm.core.model.produce.ManufacturePackage;
import com.apical.oddm.core.model.produce.ManufacturePackageTitle;
import com.apical.oddm.facade.manufacture.ManufactureAltFacade;
import com.apical.oddm.facade.manufacture.ManufacturePackageFacade;
import com.apical.oddm.facade.manufacture.command.ManufacturePackageCommand;
import com.apical.oddm.facade.manufacture.dto.ManufacturePackageDTO;

@Component("manufacturePackageFacade")
public class ManufacturePackageFacadeImpl implements ManufacturePackageFacade {

	@Autowired
	private ManufacturePackageServiceI manufacturePackageService;
	@Autowired
	private ManufactureAltFacade manufactureAltFacade;
	
	@Override
	public ManufacturePackageDTO add(ManufacturePackageCommand manufacturePackageCommand,Integer currUserId,String currUserName) {
		// TODO Auto-generated method stub
		if(manufacturePackageCommand != null){
			ManufacturePackage manufacturePackage = new ManufacturePackage();
			BeanUtils.copyProperties(manufacturePackageCommand, manufacturePackage);
			if(manufacturePackageCommand.getManufactureId() != null){
				Manufacture manufacture = new Manufacture(manufacturePackageCommand.getManufactureId() );
				manufacturePackage.setOrderManufacture(manufacture);
			}
			if(manufacturePackageCommand.getTitleId()!= null){
				ManufacturePackageTitle manufacture = new ManufacturePackageTitle(manufacturePackageCommand.getTitleId() );
				manufacturePackage.setOrderMftPackageTitle(manufacture);
			}
			Serializable add = manufacturePackageService.add(manufacturePackage);
			if(add != null){
				
				//变更记录
				if(manufacturePackageCommand.getState() >= ManufactureConst.approved){
					manufactureAltFacade.addPackageRecord(manufacturePackageCommand, currUserId, currUserName);
				}
				ManufacturePackageDTO manufacturePackageDTO = new ManufacturePackageDTO();
				BeanUtils.copyProperties(manufacturePackageCommand, manufacturePackageDTO);
				manufacturePackageDTO.setId((Integer)add);
				return manufacturePackageDTO;
			}else {
				return null;
			}
		}
		return null;
	}

	@Override
	public ManufacturePackageDTO edit(ManufacturePackageCommand manufacturePackageCommand,Integer currUserId,String currUserName) {
		// TODO Auto-generated method stub
		if(manufacturePackageCommand != null){
			ManufacturePackage manufacturePackage1 = null;
			if(manufacturePackageCommand.getState() >= ManufactureConst.approved){
				manufacturePackage1 = manufacturePackageService.get(manufacturePackageCommand.getId());
			}
			
			
			ManufacturePackage manufacturePackage = new ManufacturePackage();
			BeanUtils.copyProperties(manufacturePackageCommand, manufacturePackage);
			if(manufacturePackageCommand.getManufactureId() != null){
				Manufacture manufacture = new Manufacture(manufacturePackageCommand.getManufactureId() );
				manufacturePackage.setOrderManufacture(manufacture);
			}
			if(manufacturePackageCommand.getTitleId()!= null){
				ManufacturePackageTitle manufacture = new ManufacturePackageTitle(manufacturePackageCommand.getTitleId() );
				manufacturePackage.setOrderMftPackageTitle(manufacture);
			}
			manufacturePackageService.edit(manufacturePackage);
			
			//变更记录
			manufactureAltFacade.editPackageRecord(manufacturePackage1, manufacturePackageCommand, currUserId, currUserName);
			
			ManufacturePackageDTO manufacturePackageDTO = new ManufacturePackageDTO();
			BeanUtils.copyProperties(manufacturePackageCommand, manufacturePackageDTO);
			return manufacturePackageDTO;
		}
		return null;
	}

	@Override
	public void delete(Integer id,Integer state,Integer currUserId,String currUserName) {
		// TODO Auto-generated method stub
		if(id != null){
			ManufacturePackage manufacturePackage = null;
			if(state >= ManufactureConst.approved){
				manufacturePackage =  manufacturePackageService.get(id);
			}
			
			manufacturePackageService.delete(id);
			
			//变更记录
			manufactureAltFacade.delPackageRecord(manufacturePackage, currUserId, currUserName);
		}
	}

	@Override
	public ManufacturePackageDTO get(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

}
