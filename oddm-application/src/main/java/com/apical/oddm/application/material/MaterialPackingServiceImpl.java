package com.apical.oddm.application.material;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apical.oddm.application.base.BaseServiceImpl;
import com.apical.oddm.core.dao.material.MaterialPackingDaoI;
import com.apical.oddm.core.model.material.MaterialPacking;
import com.apical.oddm.core.model.material.Product;
import com.apical.oddm.core.model.page.Pager;

@Service("materialPackingService")
public class MaterialPackingServiceImpl extends BaseServiceImpl<MaterialPacking> implements MaterialPackingServiceI {

	@Autowired
	private MaterialPackingDaoI materialPackingDao;
	
	@Override
	public Pager<MaterialPacking> dataGrid() {
		return materialPackingDao.dataGrid();
	}
	
	@Override
	public Set<Product> getMaterialPackingProduct(int packingId) {
		MaterialPacking materialPacking = materialPackingDao.get(packingId);
		if (materialPacking != null) {
			materialPacking.getProducts().isEmpty();//达到快速加载的作用
			return materialPacking.getProducts();
		} else {
			return null;
		}
	}

	@Override
	public Pager<MaterialPacking> dataGridByName(String materialName) {
		return materialPackingDao.dataGridByName(materialName);
	}

	@Override
	public List<MaterialPacking> listIsCustom(int isCustom) {
		return materialPackingDao.listIsCustom(isCustom);
	}

	@Override
	public MaterialPacking getByName(String materialName) {
		return materialPackingDao.getByName(materialName);
	}

	@Override
	public Pager<MaterialPacking> dataGridByTypeName(String materialTypeName) {
		return materialPackingDao.dataGridByTypeName(materialTypeName);
	}

	@Override
	public Pager<MaterialPacking> dataGridByType(MaterialPacking materialPacking) {
		return materialPackingDao.dataGridByType(materialPacking);
	}

	@Override
	public List<MaterialPacking> listIsBase(int isBase) {
		return materialPackingDao.listIsBase(isBase);
	}
}
