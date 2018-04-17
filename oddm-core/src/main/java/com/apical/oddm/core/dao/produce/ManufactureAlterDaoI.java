package com.apical.oddm.core.dao.produce;

import com.apical.oddm.core.dao.basedao.BaseDaoI;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.produce.ManufactureAlt;

/**
 * 生产任务书信息更改记录表dao操作接口
 * @author lgx
 * 2016-10-16
 */
public interface ManufactureAlterDaoI extends BaseDaoI<ManufactureAlt> {
	/**
	 * 分页获取
	 * @return 
	 */
	public Pager<ManufactureAlt> dataGrid(int mftId);
}
