package com.apical.oddm.core.dao.material;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.apical.oddm.core.dao.basedao.BaseDaoImpl;
import com.apical.oddm.core.model.material.MaterialBare;
import com.apical.oddm.core.model.page.Pager;

@Repository("materialBareDao")
public class MaterialBareDaoImpl extends BaseDaoImpl<MaterialBare> implements  MaterialBareDaoI {
	
	@Override
	public MaterialBare get(int id) {
		String hql = "from MaterialBare t join fetch t.materialType o where t.id=?";
		return (MaterialBare) this.queryObject(hql, id);
	}

	@Override
	public Pager<MaterialBare> dataGrid() {
		String hql = "from MaterialBare t join fetch t.materialType o";
		return this.find(hql);
	}

	@Override
	public Pager<MaterialBare> dataGridByName(String materialName) {
		String hql = "from MaterialBare t join fetch t.materialType o where t.name like ?";
		return this.find(hql, "%"+materialName+"%");
	}

	@Override
	public Pager<MaterialBare> dataGridBySuperType(MaterialBare materialBare, Set<Integer> type) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "select t from MaterialBare t join fetch t.materialType m where 1=1";
		if (type != null && !type.isEmpty()) {
			hql +=" and m.type in ("+ org.springframework.util.StringUtils.collectionToDelimitedString(type, ",") + ")";
		}
		if (materialBare != null) {
			if (StringUtils.isNotBlank(materialBare.getName())) {
				hql +=" and t.name like :name";
				params.put("name", "%"+materialBare.getName()+"%");
			}
			if (materialBare.getMaterialType() != null) {
				if (materialBare.getMaterialType().getId() != null) {
					hql +=" and m.id=:mid";
					params.put("mid", materialBare.getMaterialType().getId());
				}
			}
		}
		return this.findByAlias(hql, params);
	}

	@Override
	public MaterialBare getByName(String materialName) {
		String hql = "from MaterialBare t join fetch t.materialType o where t.name=?";
		return (MaterialBare) this.queryObject(hql, materialName);
	}

	@Override
	public Pager<MaterialBare> dataGridByTypeName(String materialTypeName) {
		String hql = "from MaterialBare t join fetch t.materialType o where o.name=?";
		return this.find(hql, materialTypeName);
	}

	@Override
	public Pager<MaterialBare> dataGridByType(Set<Integer> type) {
		return this.find("select t from MaterialBare t join fetch t.materialType m where m.id in ("
				+ org.springframework.util.StringUtils.collectionToDelimitedString(type, ",") + ")");
	}

	@Override
	public List<MaterialBare> listGridBySuperType(Set<Integer> type,
			Integer isBase) {
		String hql = null;
		if (isBase != null) {
			hql = "select t from MaterialBare t join fetch t.materialType m where t.isbase=? and m.type in ("
					+ org.springframework.util.StringUtils.collectionToDelimitedString(type, ",") + ")";
			return this.list(hql, isBase);
		} else {
			hql = "select t from MaterialBare t join fetch t.materialType m where m.type in ("
					+ org.springframework.util.StringUtils.collectionToDelimitedString(type, ",") + ")";
			return this.list(hql);
		}
	}

	@Override
	public List<MaterialBare> listGridByType(Set<Integer> type, Integer isBase) {
		String hql = null;
		if (isBase != null) {
			hql = "select t from MaterialBare t join fetch t.materialType where t.isbase=? and t.type in ("
					+ org.springframework.util.StringUtils.collectionToDelimitedString(type, ",") + ")";
			return this.list(hql, isBase);
		} else {
			hql = "select t from MaterialBare t join fetch t.materialType where t.type in ("
					+ org.springframework.util.StringUtils.collectionToDelimitedString(type, ",") + ")";
			return this.list(hql);
		}
	}

}
