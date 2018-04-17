package com.apical.oddm.facade.material;

import java.util.List;
import java.util.Set;

import com.apical.oddm.core.model.material.MaterialPacking;
import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.base.dto.BasePage;
import com.apical.oddm.facade.material.command.MaterialPackingCommand;
import com.apical.oddm.facade.material.dto.MaterialPackingDTO;
import com.apical.oddm.facade.material.dto.ProductDTO;

/** 
 * @description 类的描述：
 * @author  作者 : zhuzh
 * @date 创建时间：2016年10月28日 上午9:51:01 
 * @version 1.0 
 */

public interface MaterialPackingFacade {

	/**
	 * 分页获取包材物料
	 * @param 查询条件的对象 
	 * @return
	 */
	public BasePage<MaterialPackingDTO> pageList(MaterialPackingCommand materialPackingCommand,PageCommand pageCommand);
	
	/**
	 * 查询普通货则客制化包材列表
	 * @param customType 普通、客制化两种类型
	 * @return
	 */
	public List<MaterialPackingDTO> dataGrid(Integer customType);
	/**
	 * 获取包材物料被哪个机型使用
	 * @param packingId 包材物料id
	 * @return
	 */
	public Set<ProductDTO> getMaterialPackingProduct(Integer packingId);

	/**
	 * 通过物料类型获取包材物料
	 * @param isBase 是否是基础物料
	 * @return
	 */
	public List<MaterialPackingDTO> listIsBase(Integer isBase);
	/**
	 * 增加
	 * @param materialBare
	 * @return
	 */
	public Boolean add(MaterialPackingCommand materialPackingCommand);

	/**
	 * 删除
	 * @param id
	 */
	public void delete(Integer id);

	 /**
	 * 修改
	 * @param materialPacking
	 */
	public void edit(MaterialPackingCommand materialPackingCommand);

	/**
	 * 获取单条包材物料信息
	 * @param id
	 * @return
	 */
	public MaterialPackingDTO get(Integer id);

}
