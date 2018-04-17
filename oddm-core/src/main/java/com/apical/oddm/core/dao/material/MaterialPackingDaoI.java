package com.apical.oddm.core.dao.material;

import java.util.List;

import com.apical.oddm.core.dao.basedao.BaseDaoI;
import com.apical.oddm.core.model.material.MaterialPacking;
import com.apical.oddm.core.model.page.Pager;

/**
 * 包材物料Dao接口
 * @author lgx
 * 2016-10-15
 */
public interface MaterialPackingDaoI extends BaseDaoI<MaterialPacking> {

	/**
	 * 分页获取包材物料
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
	 * 分页获取包材物料
	 * @param materialTypeName 物料类型名称
	 * @return
	 */
	public Pager<MaterialPacking> dataGridByTypeName(String materialTypeName);
	
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
	 * @param materialName 传入物料名称
	 * @return
	 */
	public Pager<MaterialPacking> dataGridByName(String materialName);

	/**
	 * 获取包材物料
	 * @param materialName 传入物料名称
	 * @return
	 */
	public MaterialPacking getByName(String materialName);
}
