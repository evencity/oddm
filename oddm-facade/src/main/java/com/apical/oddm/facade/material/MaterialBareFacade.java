package com.apical.oddm.facade.material;

import java.util.List;
import java.util.Set;

import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.base.dto.BasePage;
import com.apical.oddm.facade.material.command.MaterialBareCommand;
import com.apical.oddm.facade.material.dto.MaterialBareDTO;


/** 
 * @description 类的描述：
 * @author  作者 : zhuzh
 * @date 创建时间：2016年10月28日 上午9:14:30 
 * @version 1.0 
 */

public interface MaterialBareFacade {


	/**
	 * 分页获取裸机配件物料
	 * @return
	 */
	public  BasePage<MaterialBareDTO> pageList(MaterialBareCommand materialBareCommand,PageCommand pageCommand);
	
	/**
	 * 通过物料类型分页获取裸机配件物料
	 * @param type 小类
	 * @return
	 */
	public BasePage<MaterialBareDTO> dataGridByType(Set<Integer> type,PageCommand pageCommand);
	
	/**
	 * 通过物料类型分页获取裸机配件物料
	 * @param type 大类：1 裸机、2 包材、3 配件
	 * @return
	 */
	public BasePage<MaterialBareDTO> dataGridBySuperType(Set<Integer> type,PageCommand pageCommand);

	/**
	 * 通过物料类型获取裸机配件物料
	 * @param type 大类：1 裸机、2 包材、3 配件
	 * @param isBase 是否是基础物料
	 * @return
	 */
	public List<MaterialBareDTO> listGridBySuperType(Set<Integer> type, Integer isBase);
	/**
	 * 通过物料类型获取裸机配件物料
	 * @param type 小类
	 * @return
	 */
	public List<MaterialBareDTO> listGridByType(Set<Integer> type, Integer isBase);
	
	/**
	 * 分页获取裸机配件物料
	 * @param materialName 裸机配件物料名称
	 * @return
	 */
	public BasePage<MaterialBareDTO> dataGridByName(String materialName,PageCommand pageCommand);
	
	/**
	 * 增加
	 * @param materialBare
	 * @return
	 */
	public Boolean add(MaterialBareCommand materialBareCommand);

	/**
	 * 删除
	 * @param id
	 */
	public void delete(Integer id);

	/**
	 * 修改
	 * @param materialBare
	 */
	public void edit(MaterialBareCommand materialBareCommand);

	/**
	 * 获取裸机配件物料
	 * @param id
	 * @return
	 */
	public MaterialBareDTO get(Integer id);

}
