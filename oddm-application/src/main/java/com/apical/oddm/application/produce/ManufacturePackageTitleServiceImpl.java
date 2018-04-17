package com.apical.oddm.application.produce;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apical.oddm.application.base.BaseServiceImpl;
import com.apical.oddm.core.dao.produce.ManufacturePackageTitleDaoI;
import com.apical.oddm.core.model.produce.ManufacturePackageTitle;

@Service("manufacturePackageTitleService")
public class ManufacturePackageTitleServiceImpl extends BaseServiceImpl<ManufacturePackageTitle> implements ManufacturePackageTitleServiceI {

	@Autowired
	private ManufacturePackageTitleDaoI manufacturePackageTitleDao;
	
	@Override
	public List<ManufacturePackageTitle> getListAll() {
		return manufacturePackageTitleDao.getListAll();
	}

}
