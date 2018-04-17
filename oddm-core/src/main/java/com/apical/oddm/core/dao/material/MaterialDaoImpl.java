package com.apical.oddm.core.dao.material;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.apical.oddm.core.dao.basedao.BaseDaoImpl;
import com.apical.oddm.core.model.material.Material;
import com.apical.oddm.core.model.page.Pager;

@Repository("materialDao")
public class MaterialDaoImpl extends BaseDaoImpl<Material> implements  MaterialDaoI {
	
/*	@Override
	public Pager<Material> dataGrid() {
		String hql = "from Material t join fetch t.materialType o";
		return this.find(hql);
	}*/

	@Override
	public Pager<Material> dataGridBySuperType(Material materialBare, Set<Integer> type) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "select t from Material t join fetch t.materialType m where 1=1";
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

}
