package com.apical.oddm.core.dao.bom;

import java.util.List;

import com.apical.oddm.core.dao.basedao.BaseDaoI;
import com.apical.oddm.core.model.bom.BomMaterialRef;

/**
 * BOM信息与物料关联表dao操作接口
 * @author lgx
 * 2016-10-16
 */
public interface BomMaterialRefDaoI extends BaseDaoI<BomMaterialRef> {
	
	/**
	 * 通过bom id获取物料列表
	 * @param bomId
	 * @return
	 */
	public List<BomMaterialRef> getByBomId(int bomId);
}
