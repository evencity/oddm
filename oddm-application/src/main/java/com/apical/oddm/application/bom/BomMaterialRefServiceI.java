package com.apical.oddm.application.bom;

import java.util.List;

import com.apical.oddm.application.base.BaseServiceI;
import com.apical.oddm.core.model.bom.BomMaterialRef;


/**
 * BOM信息和物料关联表操作接口
 * @author lgx
 * 2016-10-16
 */
public interface BomMaterialRefServiceI extends BaseServiceI<BomMaterialRef> {

	/**
	 * 通过bom id获取物料列表
	 * @param bomId
	 * @return 仅返回物料信息，慢加载
	 */
	public List<BomMaterialRef> getByBomId(int bomId);
}
