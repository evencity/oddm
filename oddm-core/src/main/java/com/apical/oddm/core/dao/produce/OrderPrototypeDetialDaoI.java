package com.apical.oddm.core.dao.produce;

import java.util.List;

import com.apical.oddm.core.dao.basedao.BaseDaoI;
import com.apical.oddm.core.model.produce.OrderPrototypeDetial;

/**
 * 首件确认书明细表dao操作接口
 * @author lgx
 * 2016-11-1
 */
public interface OrderPrototypeDetialDaoI extends BaseDaoI<OrderPrototypeDetial> {

	/**
	 * 通过首件主表id获取首件详细列表
	 * @param proId
	 * @return 仅返列表，慢加载
	 */
	public List<OrderPrototypeDetial> getByProId(int proId);
}
