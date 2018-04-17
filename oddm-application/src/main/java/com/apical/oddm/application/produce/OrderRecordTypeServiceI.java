package com.apical.oddm.application.produce;

import com.apical.oddm.application.base.BaseServiceI;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.produce.OrderRecordType;


/**
 * 履历类型描述表操作接口
 * @author lgx
 * 2016-11-1
 */
public interface OrderRecordTypeServiceI extends BaseServiceI<OrderRecordType> {

	/**
	 * 分页获取履历类型列表
	 * @return 慢加载
	 */
	public Pager<OrderRecordType> dataGrid();
	
	/**
	 * 通过名称查询
	 * @param name
	 * @return 慢加载
	 */
	public OrderRecordType getByName(String name);
}
