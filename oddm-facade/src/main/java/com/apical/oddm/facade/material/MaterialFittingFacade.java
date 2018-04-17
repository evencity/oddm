package com.apical.oddm.facade.material;

import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.base.dto.BasePage;
import com.apical.oddm.facade.material.command.MaterialFittingCommand;
import com.apical.oddm.facade.material.dto.MaterialFittingDTO;


/** 
 * @description 类的描述：
 * @author  作者 : zhuzh
 * @date 创建时间：2016年10月28日 上午9:14:30 
 * @version 1.0 
 */

public interface MaterialFittingFacade {


	/**
	 * 分页获取裸机配件物料
	 * @return
	 */
	public  BasePage<MaterialFittingDTO> pageList(MaterialFittingCommand fittingCommand,PageCommand pageCommand);
	
	

	/**
	 * 增加
	 * @param fitting
	 * @return
	 */
	public Boolean add(MaterialFittingCommand fittingCommand);

	/**
	 * 删除
	 * @param id
	 */
	public void delete(Integer id);

	/**
	 * 修改
	 * @param fitting
	 */
	public void edit(MaterialFittingCommand fittingCommand);

	/**
	 * 获取裸机配件物料
	 * @param id
	 * @return
	 */
	public MaterialFittingDTO get(Integer id);

}
