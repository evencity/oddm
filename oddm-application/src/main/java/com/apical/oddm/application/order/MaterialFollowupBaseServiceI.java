package com.apical.oddm.application.order;

import com.apical.oddm.application.base.BaseServiceI;
import com.apical.oddm.core.model.order.MaterialFollowupBase;


/**
 * 表操作接口
 * @author lgx
 * 2016-10-16
 */
public interface MaterialFollowupBaseServiceI extends BaseServiceI<MaterialFollowupBase> {

	/**
	 * 获取整机订单物料齐套状况跟踪表，此表仅一条记录
	 * @return
	 */
	public MaterialFollowupBase get();
}
