package com.apical.oddm.application.produce;

import com.apical.oddm.application.base.BaseServiceI;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.produce.ManufactureAlt;


/**
 * 包装工艺指导书变更记录表操作接口
 * @author lgx
 * 2016-10-16
 */
public interface ManufactureAlterServiceI extends BaseServiceI<ManufactureAlt> {

	/**
	 * 分页获取
	 * @param mftId
	 * @return 
	 */
	public Pager<ManufactureAlt> dataGrid(int mftId);
	
}
