package com.apical.oddm.core.dao.produce;

import java.util.List;

import com.apical.oddm.core.dao.basedao.BaseDaoI;
import com.apical.oddm.core.model.produce.ManufactureUnread;

/**
 * 订单未读表dao操作接口
 * @author lgx
 * 2016-12-8
 */
public interface ManufactureUnreadDaoI extends BaseDaoI<ManufactureUnread> {

	/**
	 * 返回用户id
	 * @param manufactureId
	 * @return 
	 */
	public List<Object> listByManufactureId(int manufactureId);
	/**
	 * 删除未读订单表记录
	 * @param userId
	 * @param ManufactureId
	 */
	public void delete(int userId, int manufactureId);
	
	/**
	 * 删除 几个月前的
	 */
	public void deleteMonth(int month);
	
	/**
	 * 删除所有含此用户id的记录，当删除用户的时候使用
	 * @param userId
	 */
	public void deleteAllUserId(int userId);
	
	/**
	 * 删除所有含此订单id的记录，当删除订单的时候使用
	 * @param ManufactureId
	 */
	public void deleteAllManufactureId(int manufactureId);
}
