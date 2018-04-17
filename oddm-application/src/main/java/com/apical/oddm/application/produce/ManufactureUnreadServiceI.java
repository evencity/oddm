package com.apical.oddm.application.produce;

import java.util.List;

import com.apical.oddm.application.base.BaseServiceI;
import com.apical.oddm.core.model.produce.ManufactureUnread;
import com.apical.oddm.core.model.sys.User;


/**
 * 指导书未读表操作接口
 * @author lgx
 * 2016-12-8
 */
public interface ManufactureUnreadServiceI extends BaseServiceI<ManufactureUnread> {

	/**
	 * 删除未读指导书表记录
	 * @param userId
	 * @param manufactureId
	 */
	public void delete(int userId, int manufactureId);

	/**
	 * 删除 几个月前的
	 * @param month
	 */
	public void deleteMonth(int month);

	/**
	 * 批量添加指导书指导书未读取记录
	 * @param currUserId 当前用户id
	 * @param users用户列表
	 * @param manufactureId
	 */
	public void addManufactureUnreadBatch(int currUserId, List<User> users,int manufactureId);
	
	/**
	 * 增加或则更新
	 * @param orderUnread
	 */
	public void saveOrUpdate(ManufactureUnread orderUnread);
}
