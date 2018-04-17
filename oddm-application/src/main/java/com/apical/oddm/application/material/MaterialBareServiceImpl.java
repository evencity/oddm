package com.apical.oddm.application.material;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apical.oddm.application.base.BaseServiceImpl;
import com.apical.oddm.core.dao.material.MaterialBareDaoI;
import com.apical.oddm.core.model.material.MaterialBare;
import com.apical.oddm.core.model.material.Product;
import com.apical.oddm.core.model.page.Pager;

@Service("materialBareService")
public class MaterialBareServiceImpl extends BaseServiceImpl<MaterialBare> implements MaterialBareServiceI {

	@Autowired
	private MaterialBareDaoI materialBareDao;
	
	@Override
	public Pager<MaterialBare> dataGrid() {
		return materialBareDao.dataGrid();
	}

	@Override
	public Set<Product> getMaterialBareProduct(int bareId) {
		MaterialBare materialBare = materialBareDao.get(bareId);
		if (materialBare != null) {
			materialBare.getProducts().isEmpty();//达到快速加载的作用
			return materialBare.getProducts();
		} else {
			return null;
		}
	}

	@Override
	public Pager<MaterialBare> dataGridByName(String materialName) {
		return materialBareDao.dataGridByName(materialName);
	}

	@Override
	public Pager<MaterialBare> dataGridBySuperType(MaterialBare materialBare, Set<Integer> type) {
		return materialBareDao.dataGridBySuperType(materialBare, type);
	}

	@Override
	public MaterialBare getByName(String materialName) {
		return materialBareDao.getByName(materialName);
	}

	@Override
	public Pager<MaterialBare> dataGridByTypeName(String materialTypeName) {
		return materialBareDao.dataGridByTypeName(materialTypeName);
	}

	@Override
	public Pager<MaterialBare> dataGridByType(Set<Integer> type) {
		return materialBareDao.dataGridByType(type);
	}

	@Override
	public List<MaterialBare> listGridBySuperType(Set<Integer> type,
			Integer isBase) {
		return materialBareDao.listGridBySuperType(type, isBase);
	}

	@Override
	public List<MaterialBare> listGridByType(Set<Integer> type, Integer isBase) {
		return materialBareDao.listGridByType(type, isBase);
	}
	
/*	@Override
	public MaterialBare add(MaterialBare materialBare) {
		return materialBareDao.add(materialBare);
	}

	@Override
	public void delete(int id) {
		materialBareDao.delete(id);
	}

	@Override
	public void edit(MaterialBare materialBare) {
		materialBareDao.update(materialBare);		
	}
*/
/*	@Override //本父类MaterialBareServiceI不写下面方法，下面方法也可以被重写
	public MaterialBare get(int id) {
		System.out.println("使用自己的接口重写方法！！");
		return materialBareDao.get(id);
	}*/

}
