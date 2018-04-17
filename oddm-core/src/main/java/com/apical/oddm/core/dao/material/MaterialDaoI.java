package com.apical.oddm.core.dao.material;

import java.util.Set;

import com.apical.oddm.core.model.material.Material;
import com.apical.oddm.core.model.page.Pager;

/**
 * 裸机和包材视图Dao接口
 * @author lgx
 * 2017-2-5
 */
public interface MaterialDaoI {

	/**
	 * 分页获取裸机配件物料
	 * @return
	 */
	//public Pager<Material> dataGrid();
	
	/**
	 * 通过物料类型分页获取裸机配件物料
	 * @param type 大类：1 外壳，2 硬件、屏，4 配件
	 * @param materialBare.setName() 物料名
	 * @param materialBare.setMaterialType(new MaterialType(1)) //类型id
	 * @return
	 */
	public Pager<Material> dataGridBySuperType(Material materialBare, Set<Integer> type);
}
