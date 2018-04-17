package com.apical.oddm.application.bom;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apical.oddm.application.base.BaseServiceImpl;
import com.apical.oddm.core.dao.bom.BomMaterialDaoI;
import com.apical.oddm.core.model.bom.BomMaterial;
import com.apical.oddm.core.model.page.Pager;

@Service("bomMaterialService")
public class BomMaterialServiceImpl extends BaseServiceImpl<BomMaterial> implements BomMaterialServiceI {

	@Autowired
	private BomMaterialDaoI bomMaterialDao;
	
	@Override
	public void saveBomMaterialBatch(List<BomMaterial> bomMaterial) {
		bomMaterialDao.saveBomMaterialBatch(bomMaterial);
	}

	@Override
	public void delete(String materialCode) {
		bomMaterialDao.delete(materialCode);
	}

	@Override
	public BomMaterial get(String materialCode) {
		return bomMaterialDao.get(materialCode);
	}

	@Override
	public List<BomMaterial> getByMaterialName(String materialName) {
		return bomMaterialDao.getByMaterialName(materialName);
	}

	@Override
	public void deletBomMaterialBatch(List<String> materialCode) {
		for (String str : materialCode) {
			this.delete(str);
		}
	}

	@Override
	public Pager<BomMaterial> dataGrid() {
		return bomMaterialDao.dataGrid();
	}

	@Override
	public Pager<BomMaterial> dataGrid(BomMaterial bomMaterial) {
		return bomMaterialDao.dataGrid(bomMaterial);
	}

}
