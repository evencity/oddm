package com.apical.oddm.facade.manufacture.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apical.oddm.application.produce.ManufacturePackageTitleServiceI;
import com.apical.oddm.core.model.produce.ManufacturePackageTitle;
import com.apical.oddm.facade.manufacture.ManufacturePackageTitleFacade;
import com.apical.oddm.facade.manufacture.dto.ManufacturePackageTitleDTO;

@Component("manufacturePackageTitleFacade")
public class ManufacturePackageTitleFacadeImpl implements ManufacturePackageTitleFacade {

	@Autowired
	private ManufacturePackageTitleServiceI manufacturePackageTitleService;

	@Override
	public List<ManufacturePackageTitleDTO> getListAll() {
		// TODO Auto-generated method stub
		List<ManufacturePackageTitle> listAll = manufacturePackageTitleService.getListAll();
		List<ManufacturePackageTitleDTO> manufacturePackageTitleDTOs = new ArrayList<ManufacturePackageTitleDTO>();
		if(listAll != null && listAll.size() > 0){
			for(ManufacturePackageTitle manufacturePackageTitle : listAll){
				ManufacturePackageTitleDTO manufacturePackageTitleDTO = new ManufacturePackageTitleDTO();
				BeanUtils.copyProperties(manufacturePackageTitle, manufacturePackageTitleDTO);
				manufacturePackageTitleDTOs.add(manufacturePackageTitleDTO);
			}
		}
		return manufacturePackageTitleDTOs;
	}

}
