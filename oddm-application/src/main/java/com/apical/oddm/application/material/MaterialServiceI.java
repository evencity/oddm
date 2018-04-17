package com.apical.oddm.application.material;

import java.util.Set;

import com.apical.oddm.core.model.material.Material;
import com.apical.oddm.core.model.page.Pager;

/**
 * 裸机和包材组成的视图操作查询接口
 * @author lgx
 * 2016-10-15
 */
public interface MaterialServiceI  {

	/**
	 * 分页获取裸机配件物料
	 * @return
	 */
	//public Pager<Material> dataGrid();
	
	/**
	 * 通过物料类型分页获取裸机配件物料
	 * @param type 大类：1 外壳，2 硬件、屏，4 配件，5 辅料
	 * @param materialBare.setName() 物料名
	 * @param materialBare.setMaterialType(new MaterialType(1)) //类型id
	 * @return
	 */
	public Pager<Material> dataGridBySuperType(Material materialBare, Set<Integer> type);
}
