package com.apical.oddm.application.material;

import java.util.List;
import java.util.Set;

import com.apical.oddm.application.base.BaseServiceI;
import com.apical.oddm.core.model.material.MaterialBare;
import com.apical.oddm.core.model.material.Product;
import com.apical.oddm.core.model.page.Pager;

/**
 * 裸机配件物料表操作接口
 * @author lgx
 * 2016-10-15
 */
public interface MaterialBareServiceI extends BaseServiceI<MaterialBare> {

	/**
	 * 分页获取裸机配件物料
	 * @return
	 */
	public Pager<MaterialBare> dataGrid();
	
	/**
	 * 通过物料类型分页获取裸机配件物料
	 * @param type 大类：1 外壳，2 硬件、屏，4 配件
	 * @param materialBare.setName() 物料名
	 * @param materialBare.setMaterialType(new MaterialType(1)) //类型id
	 * @return
	 */
	public Pager<MaterialBare> dataGridBySuperType(MaterialBare materialBare, Set<Integer> type);
	
	/**
	 * 通过物料类型获取裸机配件物料
	 * @param type 大类：1 外壳，2 硬件、屏，4 配件
	 * @param isBase 是否是基础物料
	 * @return
	 */
	public List<MaterialBare> listGridBySuperType(Set<Integer> type, Integer isBase);

	/**
	 * 通过物料类型分页获取裸机配件物料
	 * @param type 小类
	 * @return
	 */
	public Pager<MaterialBare> dataGridByType(Set<Integer> type);
	
	/**
	 * 通过物料类型获取裸机配件物料
	 * @param type 小类
	 * @return
	 */
	public List<MaterialBare> listGridByType(Set<Integer> type, Integer isBase);

	/**
	 * 获取裸机配件物料被哪个机型使用
	 * @param bareId 裸机配件物料id
	 * @return
	 */
	public Set<Product> getMaterialBareProduct(int bareId);
	
	/**
	 * 分页获取裸机配件物料
	 * @param materialName 裸机配件物料名称
	 * @return
	 */
	public Pager<MaterialBare> dataGridByName(String materialName);
	
	/**
	 * 分页获取裸机配件物料
	 * @param materialTypeName 物料类型名称
	 * @return
	 */
	public Pager<MaterialBare> dataGridByTypeName(String materialTypeName);
	
	/**
	 * 获取裸机配件物料
	 * @param materialName 裸机配件物料名称
	 * @return
	 */
	public MaterialBare getByName(String materialName);
}
