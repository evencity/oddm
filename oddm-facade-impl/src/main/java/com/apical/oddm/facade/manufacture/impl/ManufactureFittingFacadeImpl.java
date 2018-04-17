package com.apical.oddm.facade.manufacture.impl;

import java.io.Serializable;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apical.oddm.application.produce.ManufactureFittingServiceI;
import com.apical.oddm.core.constant.ManufactureConst;
import com.apical.oddm.core.model.produce.Manufacture;
import com.apical.oddm.core.model.produce.ManufactureFitting;
import com.apical.oddm.facade.manufacture.ManufactureAltFacade;
import com.apical.oddm.facade.manufacture.ManufactureFittingFacade;
import com.apical.oddm.facade.manufacture.command.ManufactureFittingCommand;
import com.apical.oddm.facade.manufacture.dto.ManufactureFittingDTO;


@Component("manufactureFittingFacade")
public class ManufactureFittingFacadeImpl implements ManufactureFittingFacade {

	@Autowired
	private ManufactureFittingServiceI manufactureFittingService;
	@Autowired
	private ManufactureAltFacade manufactureAltFacade;
	
	@Override
	public ManufactureFittingDTO add(ManufactureFittingCommand manufactureFittingCommand,Integer currUserId,String currUserName) {
		// TODO Auto-generated method stub
		if(manufactureFittingCommand != null){
			ManufactureFitting manufactureFitting = new ManufactureFitting();
			BeanUtils.copyProperties(manufactureFittingCommand, manufactureFitting);
			if(manufactureFittingCommand.getManufactureId() != null){
				Manufacture manufacture = new Manufacture(manufactureFittingCommand.getManufactureId() );
				manufactureFitting.setOrderManufacture(manufacture);
			}
			Serializable add = manufactureFittingService.add(manufactureFitting);
			if(add != null){

				//变更记录
				if(manufactureFittingCommand.getState() >= ManufactureConst.approved){
					manufactureAltFacade.addFittingRecord(manufactureFittingCommand, currUserId, currUserName);
				}
				
				ManufactureFittingDTO manufactureFittingDTO = new ManufactureFittingDTO();
				BeanUtils.copyProperties(manufactureFittingCommand, manufactureFittingDTO);
				manufactureFittingDTO.setId((Integer)add);
				return manufactureFittingDTO;
			}else {
				return null;
			}
		}
		return null;
	}

	@Override
	public ManufactureFittingDTO edit(ManufactureFittingCommand manufactureFittingCommand,Integer currUserId,String currUserName) {
		// TODO Auto-generated method stub
		if(manufactureFittingCommand != null){
			//变更记录
			ManufactureFitting manufactureFitting1 = null;
			if(manufactureFittingCommand.getState() >= ManufactureConst.approved){
				manufactureFitting1 = manufactureFittingService.get(manufactureFittingCommand.getId());
			}
			
			ManufactureFitting manufactureFitting = new ManufactureFitting();
			BeanUtils.copyProperties(manufactureFittingCommand, manufactureFitting);
			if(manufactureFittingCommand.getManufactureId() != null){
				Manufacture manufacture = new Manufacture(manufactureFittingCommand.getManufactureId() );
				manufactureFitting.setOrderManufacture(manufacture);
			}
			manufactureFittingService.edit(manufactureFitting);

			//添加纪录
			manufactureAltFacade.editFittingRecord(manufactureFitting1, manufactureFittingCommand, currUserId, currUserName);
			
			ManufactureFittingDTO manufactureFittingDTO = new ManufactureFittingDTO();
			BeanUtils.copyProperties(manufactureFittingCommand, manufactureFittingDTO);
			return manufactureFittingDTO;
		}
		return null;
	}

	@Override
	public void delete(Integer id,Integer state,Integer currUserId,String currUserName) {
		// TODO Auto-generated method stub
		if(id != null){
			ManufactureFitting manufactureFitting = null;
			//变更记录
			if(state >= ManufactureConst.approved){
				manufactureFitting = manufactureFittingService.get(id);
			}
			
			manufactureFittingService.delete(id);
			
			manufactureAltFacade.delFittingRecord(manufactureFitting, currUserId, currUserName);
		}
	}

	@Override
	public ManufactureFittingDTO get(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
