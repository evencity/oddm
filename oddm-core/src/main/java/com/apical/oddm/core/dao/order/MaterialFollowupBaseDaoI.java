package com.apical.oddm.core.dao.order;

import com.apical.oddm.core.dao.basedao.BaseDaoI;
import com.apical.oddm.core.model.order.MaterialFollowupBase;

/**
 * 物料交期Excel表头信息表dao操作接口
 * @author lgx
 * 2016-10-16
 */
public interface MaterialFollowupBaseDaoI extends BaseDaoI<MaterialFollowupBase> {

	/**
	 * 获取整机订单物料齐套状况跟踪表，此表仅一条记录
	 * @return
	 */
	public MaterialFollowupBase get();
}
