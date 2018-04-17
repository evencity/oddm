package com.apical.oddm.application.produce;

import java.util.List;

import com.apical.oddm.application.base.BaseServiceI;
import com.apical.oddm.core.model.produce.ManufacturePackageTitle;


/**
 * 生产任务书订单包材及包装标题配置表表操作接口
 * @author lgx
 * 2016-10-26
 */
public interface ManufacturePackageTitleServiceI extends BaseServiceI<ManufacturePackageTitle> {

	/**
	 * 获取全部记录
	 * @return
	 */
	public List<ManufacturePackageTitle> getListAll();
}
