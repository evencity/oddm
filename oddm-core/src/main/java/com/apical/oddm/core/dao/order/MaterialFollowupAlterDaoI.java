package com.apical.oddm.core.dao.order;

import java.util.List;

import com.apical.oddm.core.dao.basedao.BaseDaoI;
import com.apical.oddm.core.model.order.MaterialFollowupAlter;
import com.apical.oddm.core.model.page.Pager;

/**
 * 物料交期表更记录表dao操作接口
 * @author lgx
 * 2016-10-16
 */
public interface MaterialFollowupAlterDaoI extends BaseDaoI<MaterialFollowupAlter> {

	/**
	 * 通过物料交期id查询关联的所有列表
	 * @param followUpId 物料交期id
	 * @return
	 */
	public List<MaterialFollowupAlter> listGrid(int followUpId);
	
	/**
	 * 分页获取
	 * @return 
	 */
	public Pager<MaterialFollowupAlter> dataGrid(int followUpId);
}
