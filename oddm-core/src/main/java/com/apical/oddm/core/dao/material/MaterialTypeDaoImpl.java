package com.apical.oddm.core.dao.material;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.apical.oddm.core.dao.basedao.BaseDaoImpl;
import com.apical.oddm.core.model.material.MaterialType;

@Repository("materialTypeDao")
public class MaterialTypeDaoImpl  extends BaseDaoImpl<MaterialType>  implements MaterialTypeDaoI {

	@Override
	public List<MaterialType> dataGrid() {
		String hql = "from MaterialType t order by t.type";
		return this.list(hql);
	}

	@Override
	public List<MaterialType> dataGrid(Set<Integer> types) {
		return this.list("from MaterialType t where t.type in ("
				+ StringUtils.collectionToDelimitedString(types, ",") + ") order by t.type");
	}

	@Override
	public MaterialType getByName(String materialName) {
		String hql = "from MaterialType t where t.name=?";
		return (MaterialType) this.queryObject(hql, materialName);
	}
}
