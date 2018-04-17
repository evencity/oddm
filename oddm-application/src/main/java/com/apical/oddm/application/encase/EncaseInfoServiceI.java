package com.apical.oddm.application.encase;

import com.apical.oddm.application.base.BaseServiceI;
import com.apical.oddm.core.model.encase.EncaseInfo;
import com.apical.oddm.core.model.page.Pager;

/**
 * 装箱单信息表操作接口
 * @author lgx
 * 2016-10-16
 */
public interface EncaseInfoServiceI extends BaseServiceI<EncaseInfo> {

	/**
	 * 获取分页数组结果集
	 * @param encaseInfo.setName()
	 * @param encaseInfo.setEncaseDateStart() encaseInfo.setEncaseDateEnd(),根据装箱日期起止查询
	 * @return
	 */
	public Pager<EncaseInfo> dataGrid(EncaseInfo encaseInfo);
	
	/**
	 * 通过装箱信息id返回详细信息
	 * @param encaseInfoId
	 * @return 装箱信息和列表
	 */
	public EncaseInfo getDetail(int encaseInfoId);
	
	/**
	 * 返回最近的一条记录
	 * @return 只返会装箱信息不含列表
	 */
	public EncaseInfo getLatestRow();
}
