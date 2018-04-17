package com.apical.oddm.application.material;

import java.util.List;
import java.util.Set;

import com.apical.oddm.application.base.BaseServiceI;
import com.apical.oddm.core.model.material.MaterialType;

/**
 * 物料类型表操作接口
 * @author lgx
 * 2016-10-15
 */
public interface MaterialTypeServiceI extends BaseServiceI<MaterialType> {

	/**
	 * 获取所有物料类型列表
	 * @return
	 */
	public List<MaterialType> dataGrid();

	/**
	 * 获取指定类型的所有物料类型列表
	 * @param types 传入物料大类
	 * @return
	 */
	public List<MaterialType> dataGrid(Set<Integer> types);
	
	/**
	 * 通过名称获取物料类型
	 * @param materialName 传入物料类型名称
	 * @return
	 */
	public MaterialType getByName(String materialName);
	
/*	*//**
	 * 增加物料类型
	 * @param materialType
	 * @return
	 *//*
	public MaterialType add(MaterialType materialType);

	*//**
	 * 通过物料类型id删除物料类型
	 * @param id
	 *//*
	public void delete(int id);

	*//**
	 * 修改物料类型
	 * @param materialType
	 *//*
	public void edit(MaterialType materialType);

	*//**
	 * 获取单个物料类型
	 * @param id
	 * @return
	 *//*
	public MaterialType get(int id);*/
}
