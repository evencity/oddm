package com.apical.oddm.application.bom;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apical.oddm.application.base.BaseServiceImpl;
import com.apical.oddm.core.dao.bom.BomMaterialRefDaoI;
import com.apical.oddm.core.model.bom.BomMaterialRef;

@Service("bomMaterialRefService")
public class BomMaterialRefServiceImpl extends BaseServiceImpl<BomMaterialRef> implements BomMaterialRefServiceI {

	@Autowired
	private BomMaterialRefDaoI bomMaterialRefDao;
	
	@Override
	public List<BomMaterialRef> getByBomId(int bomId) {
		return bomMaterialRefDao.getByBomId(bomId);
	}

}
