package com.apical.oddm.core.dao.sys;

import java.util.Date;
import java.util.Set;

import com.apical.oddm.core.dao.basedao.BaseDaoI;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.sys.SysNotice;

/**
 * 通知公告表dao操作接口
 * @author lgx
 * 2016-10-12
 */
public interface SysNoticeDaoI extends BaseDaoI<SysNotice> {

	/**
	 * 分页获取通知公告信息
	 * @return
	 */
	public Pager<SysNotice> dataGrid(SysNotice sysNotice, Set<Integer> states);

	/**
	 * 分页获取列表
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 */
	public Pager<SysNotice> dataGrid(Date startTime, Date endTime);

	/**
	 * 通过公告标题查询
	 * @param title
	 * @return
	 */
	public Pager<SysNotice> dataGrid(String title);

}
