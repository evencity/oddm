package com.apical.oddm.core.dao.material;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.apical.oddm.core.dao.basedao.BaseDaoImpl;
import com.apical.oddm.core.model.material.MaterialPacking;
import com.apical.oddm.core.model.page.Pager;

@Repository("materialPackingDao")
public class MaterialPackingDaoImpl extends BaseDaoImpl<MaterialPacking> implements  MaterialPackingDaoI {

	@Override
	public MaterialPacking get(int id) {
		String hql = "from MaterialPacking t join fetch t.materialType o where t.id=?";
		return (MaterialPacking) this.queryObject(hql, id);
	}
	
	@Override
	public Pager<MaterialPacking> dataGrid() {
		String hql = "from MaterialPacking t join fetch t.materialType o";
		return this.find(hql);
	}

	@Override
	public Pager<MaterialPacking> dataGridByName(String materialName) {
		String hql = "from MaterialPacking t join fetch t.materialType o where t.name like ?";
		return this.find(hql, "%"+materialName+"%");
	}

	@Override
	public List<MaterialPacking> listIsCustom(int customType) {
		String hql = "from MaterialPacking t join fetch t.materialType o where t.normal=?";
		return this.list(hql, customType);
	}

	@Override
	public MaterialPacking getByName(String materialName) {
		String hql = "from MaterialPacking t join fetch t.materialType o where t.name=?";
		return (MaterialPacking) this.queryObject(hql, materialName);
	}

	@Override
	public Pager<MaterialPacking> dataGridByTypeName(String materialTypeName) {
		String hql = "from MaterialPacking t join fetch t.materialType o where o.name=?";
		return this.find(hql, materialTypeName);
	}

	@Override
	public Pager<MaterialPacking> dataGridByType(MaterialPacking materialPacking) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "select t from MaterialPacking t join fetch t.materialType m where 1=1";
		if (materialPacking != null) {
			if (StringUtils.isNotBlank(materialPacking.getName())) {
				hql +=" and t.name like :name";
				params.put("name", "%"+materialPacking.getName()+"%");
			}
			if (materialPacking.getMaterialType() != null) {
				if (materialPacking.getMaterialType().getId() != null) {
					hql +=" and m.id=:mid";
					params.put("mid", materialPacking.getMaterialType().getId());
				}
			}
		}
		return this.findByAlias(hql, params);
	}

	@Override
	public List<MaterialPacking> listIsBase(int isBase) {
		String hql = "from MaterialPacking t join fetch t.materialType o where t.isbase=?";
		return this.list(hql, isBase);
	}

}
