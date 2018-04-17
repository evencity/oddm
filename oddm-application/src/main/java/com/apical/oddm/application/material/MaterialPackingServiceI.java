package com.apical.oddm.application.material;

import java.util.List;
import java.util.Set;

import com.apical.oddm.application.base.BaseServiceI;
import com.apical.oddm.core.model.material.MaterialPacking;
import com.apical.oddm.core.model.material.Product;
import com.apical.oddm.core.model.page.Pager;

/**
 * 包材物料表操作接口
 * @author lgx
 * 2016-10-15
 */
public interface MaterialPackingServiceI extends BaseServiceI<MaterialPacking> {

	/**
	 * 分页获取包材物料，大类：3、包材，
	 * @return
	 */
	public Pager<MaterialPacking> dataGrid();
	
	/**
	 * 通过物料类型分页获取包材物料
	 * @param MaterialPacking.setName() 物料名
	 * @param MaterialPacking.setMaterialType(new MaterialType(1)) //类型id
	 * @return
	 */
	public Pager<MaterialPacking> dataGridByType(MaterialPacking materialPacking);
	
	/**
	 * 通过物料类型获取包材物料
	 * @param isBase 是否是基础物料
	 * @return
	 */
	public List<MaterialPacking> listIsBase(int isBase);
	
	/**
	 * 查询普通货则客制化包材列表
	 * @param isCustom 普通、客制化两种类型
	 * @return
	 */
	public List<MaterialPacking> listIsCustom(int isCustom);
	
	/**
	 * 分页获取包材物料
	 * @param materialTypeName 物料类型名称
	 * @return
	 */
	public Pager<MaterialPacking> dataGridByTypeName(String materialTypeName);

	/**
	 * 获取包材物料被哪个机型使用
	 * @param packingId 包材物料id
	 * @return
	 */
	public Set<Product> getMaterialPackingProduct(int packingId);
	
	/**
	 * 分页获物料
	 * @param materialName 裸机配件物料名称
	 * @return
	 */
	public Pager<MaterialPacking> dataGridByName(String materialName);
	
	/**
	 * 获取包材物料
	 * @param materialName 传入物料名称
	 * @return
	 */
	public MaterialPacking getByName(String materialName);
	
/*
	*//**
	 * 增加
	 * @param materialBare
	 * @return
	 *//*
	public MaterialPacking add(MaterialPacking materialPacking);

	*//**
	 * 删除
	 * @param id
	 *//*
	public void delete(int id);

	*//**
	 * 修改
	 * @param materialPacking
	 *//*
	public void edit(MaterialPacking materialPacking);

	*//**
	 * 获取单条包材物料信息
	 * @param id
	 * @return
	 *//*
	public MaterialPacking get(int id);
*/
}
