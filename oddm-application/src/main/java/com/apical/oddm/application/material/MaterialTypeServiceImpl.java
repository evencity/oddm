package com.apical.oddm.application.material;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apical.oddm.application.base.BaseServiceImpl;
import com.apical.oddm.core.dao.material.MaterialTypeDaoI;
import com.apical.oddm.core.model.material.MaterialType;

@Service("materialTypeService")
public class MaterialTypeServiceImpl extends BaseServiceImpl<MaterialType> implements MaterialTypeServiceI {

	@Autowired
	private MaterialTypeDaoI materialTypeDao;
	
	@Override
	public List<MaterialType> dataGrid() {
		return materialTypeDao.dataGrid();
	}

	@Override
	public List<MaterialType> dataGrid(Set<Integer> types) {
		return materialTypeDao.dataGrid(types);
	}
/*	
	@Override
	public MaterialType add(MaterialType materialType) {
		return materialTypeDao.add(materialType);		
	}

	@Override
	public void delete(int id) {
		materialTypeDao.delete(id);		
	}

	@Override
	public void edit(MaterialType materialType) {
		materialTypeDao.update(materialType);		
	}

	@Override
	public MaterialType get(int id) {
		return materialTypeDao.get(id);
	}*/

	@Override
	public MaterialType getByName(String materialName) {
		return materialTypeDao.getByName(materialName);
	}
}
