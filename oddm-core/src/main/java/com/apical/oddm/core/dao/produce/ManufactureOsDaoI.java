package com.apical.oddm.core.dao.produce;

import com.apical.oddm.core.dao.basedao.BaseDaoI;
import com.apical.oddm.core.model.produce.ManufactureOs;

/**
 * 生产任务书软件信息表dao操作接口
 * @author lgx
 * 2016-10-16
 */
public interface ManufactureOsDaoI extends BaseDaoI<ManufactureOs> {

	/**
	 * 获取生产任务软件信息
	 * @param mftId 通过生产任务书id
	 * @return 返回生产任务书及软件信息
	 */
	public ManufactureOs getByMftId(int mftId);
}
