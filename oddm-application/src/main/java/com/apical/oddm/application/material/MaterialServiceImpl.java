package com.apical.oddm.application.material;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apical.oddm.application.base.BaseServiceImpl;
import com.apical.oddm.core.dao.material.MaterialDaoI;
import com.apical.oddm.core.model.material.Material;
import com.apical.oddm.core.model.page.Pager;

@Service("materialService")
public class MaterialServiceImpl extends BaseServiceImpl<Material> implements MaterialServiceI {

	@Autowired
	private MaterialDaoI materialBareDao;
	
/*	@Override
	public Pager<Material> dataGrid() {
		return materialBareDao.dataGrid();
	}*/

	@Override
	public Pager<Material> dataGridBySuperType(Material materialBare, Set<Integer> type) {
		return materialBareDao.dataGridBySuperType(materialBare, type);
	}

}
