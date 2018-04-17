package com.apical.oddm.core.dao.material;

import java.util.List;
import java.util.Set;

import com.apical.oddm.core.dao.basedao.BaseDaoI;
import com.apical.oddm.core.model.material.MaterialType;

/**
 * 物料类型Dao接口
 * @author lgx
 * 2016-10-15
 */
public interface MaterialTypeDaoI extends BaseDaoI<MaterialType> {

	/**
	 * 获取所有物料类型
	 * @return
	 */
	List<MaterialType> dataGrid();

	/**
	 * 获取指定类型的所有物料类型列表
	 * @param types 传入物料大类
	 * @return
	 */
	List<MaterialType> dataGrid(Set<Integer> types);

	/**
	 * 通过名称获取物料类型
	 * @param materialName 传入物料类型名称
	 * @return
	 */
	public MaterialType getByName(String materialName);
}
