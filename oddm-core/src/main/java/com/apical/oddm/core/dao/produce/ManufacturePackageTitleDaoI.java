package com.apical.oddm.core.dao.produce;

import java.util.List;

import com.apical.oddm.core.dao.basedao.BaseDaoI;
import com.apical.oddm.core.model.produce.ManufacturePackageTitle;

/**
 * 生产任务书订单包材及包装标题配置表表dao操作接口
 * @author lgx
 * 2016-10-16
 */
public interface ManufacturePackageTitleDaoI extends BaseDaoI<ManufacturePackageTitle> {

	/**
	 * 获取全部记录
	 * @return
	 */
	public List<ManufacturePackageTitle> getListAll();
}
