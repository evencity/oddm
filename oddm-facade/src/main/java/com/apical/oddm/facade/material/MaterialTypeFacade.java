package com.apical.oddm.facade.material;

import java.util.List;
import java.util.Set;

import com.apical.oddm.facade.material.command.MaterialTypeCommand;
import com.apical.oddm.facade.material.dto.MaterialTypeDTO;

/** 
 * @description 类的描述：
 * @author  作者 : zhuzh
 * @date 创建时间：2016年10月28日 上午9:51:37 
 * @version 1.0 
 */

public interface MaterialTypeFacade {


	/**
	 * 获取所有物料类型列表
	 * @return
	 */
	public List<MaterialTypeDTO> pageList();

	/**
	 * 获取指定类型的所有物料类型列表
	 * @param types 传入物料大类
	 * @return
	 */
	public List<MaterialTypeDTO> dataGrid(Set<Integer> types);
	
	/**
	 * 增加物料类型
	 * @param materialType
	 * @return
	 */
	public Boolean add(MaterialTypeCommand materialTypeCommand);

	/**
	 * 通过物料类型id删除物料类型
	 * @param id
	 */
	public void delete(Integer id);

	/**
	 * 修改物料类型
	 * @param materialType
	 */
	public void edit(MaterialTypeCommand materialTypeCommand);

	/**
	 * 获取单个物料类型
	 * @param id
	 * @return
	 */
	public MaterialTypeDTO get(Integer id);
}
