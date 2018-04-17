package com.apical.oddm.facade.material;

import java.util.Set;

import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.base.dto.BasePage;
import com.apical.oddm.facade.material.dto.MaterialDTO;

/** 
 * @description 类的描述：
 * @author  作者 : zhuzh
 * @date 创建时间：2017年2月5日 下午8:30:10 
 * @version 1.0 
 */

public interface MaterialFacade {

	/**
	 * 通过物料类型分页获取裸机配件物料
	 * @param type 大类：1 外壳，2 硬件、屏，4 配件，5 辅料
	 * @param materialBare.setName() 物料名
	 * @param materialBare.setMaterialType(new MaterialType(1)) //类型id
	 * @return
	 */
	public BasePage<MaterialDTO> dataGridBySuperType(MaterialDTO materialDTO, Set<Integer> type,PageCommand pageCommand);

}
