package com.apical.oddm.application.order;

import java.util.List;

import com.apical.oddm.application.base.BaseServiceI;
import com.apical.oddm.core.model.order.MaterialFollowupAlter;
import com.apical.oddm.core.model.page.Pager;


/**
 * 物料交期修改记录表操作接口
 * @author lgx
 * 2016-10-16
 */
public interface MaterialFollowupAlterServiceI extends BaseServiceI<MaterialFollowupAlter> {

	/**
	 * 通过物料交期id查询关联的所有列表
	 * @param followUpId 物料交期id
	 * @return
	 */
	public List<MaterialFollowupAlter> listGrid(int followUpId);
	
	/**
	 * 分页获取
	 * @param followUpId 订单跟进表id
	 * @return 
	 */
	public Pager<MaterialFollowupAlter> dataGrid(int followUpId);
}
