package com.apical.oddm.core.dao.bom;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.apical.oddm.core.dao.basedao.BaseDaoImpl;
import com.apical.oddm.core.model.bom.BomMaterial;
import com.apical.oddm.core.model.page.Pager;

@Repository("momMaterialDao")
public class BomMaterialDaoImpl extends BaseDaoImpl<BomMaterial> implements BomMaterialDaoI {

	@Override
	public void saveBomMaterialBatch(List<BomMaterial> bomMaterial) {
		this.saveBatch(bomMaterial);
	}

	@Override
	public void delete(String materialCode) {
		String hql="delete BomMaterial t where t.mtlCode=?";
		this.updateByHql(hql, materialCode);
	}

	@Override
	public BomMaterial get(String materialCode) {
		String hql ="select t from BomMaterial t where t.mtlCode=?";
		return (BomMaterial) this.queryObject(hql, materialCode);
	}

	@Override
	public List<BomMaterial> getByMaterialName(String materialName) {
		String hql ="select t from BomMaterial t where t.materialName=? order by t.updatetime";
		return this.list(hql, materialName);
	}

	@Override
	public Pager<BomMaterial> dataGrid() {
		String hql = "select t from BomMaterial t order by t.updatetime desc";
		return this.find(hql);
	}

	@Override
	public Pager<BomMaterial> dataGrid(BomMaterial bomMaterial) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "select t from BomMaterial t where 1=1";
		if (bomMaterial != null) {
			if (StringUtils.isNotBlank(bomMaterial.getMaterialName())) {
				hql +=" and t.materialName like :materialName";
				params.put("materialName", "%"+bomMaterial.getMaterialName()+"%");
			}
			if (StringUtils.isNotBlank(bomMaterial.getMtlCode())) {
				hql +=" and t.mtlCode like :mtlCode";
				params.put("mtlCode", "%"+bomMaterial.getMtlCode()+"%");
			}
		}
		hql += " order by t.updatetime desc";
		return this.findByAlias(hql, params);
	}
	
}
