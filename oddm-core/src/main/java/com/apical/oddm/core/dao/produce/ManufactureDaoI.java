package com.apical.oddm.core.dao.produce;

import java.util.Date;
import java.util.Set;

import com.apical.oddm.core.dao.basedao.BaseDaoI;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.produce.Manufacture;

/**
 * 生产任务书表dao操作接口
 * @author lgx
 * 2016-10-16
 */
public interface ManufactureDaoI extends BaseDaoI<Manufacture> {
	/**
	 * 分页获取生产任务书列表
	 * @return
	 */
	public Pager<Manufacture> dataGrid();

	/**
	 * 通过参数获取生产任务书列表
	 * @param manufacture
	 * @return
	 */
	public Pager<Manufacture> dataGrid(Manufacture manufacture, Set<Integer> states);

	/**
	 * 通过发行日期分页获取列表
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @return
	 */
	public Pager<Manufacture> dataGridByDateIssue(Date startDate, Date endDate);

	/**
	 * 通过更新日期分页获取列表
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @return
	 */
	public Pager<Manufacture> dataGridByDateUpdate(Date startDate, Date endDate);

	/**
	 * 根据id立即加载对象
	 * @param id
	 * @return 生产任务书的全部信息、快加载
	 */
	public Manufacture getManufacture(int id);
	
	/**
	 * 可以判断生产任务书是否存在
	 * @param lazy true慢 ,false快 
	 * @param manufacture.setOrderNo() --  订单号
	 * @return 慢加载
	 */
	public Manufacture getManufacture(Manufacture manufacture, boolean lazy);
	
	/**
	 * 通过生产任务书状态分页获取生产任务书列表，适合生产任务书管理-生产任务书列表
	 * @param states
	 * @return 生产任务书基础信息，不含其它
	 */
	//public Pager<Manufacture> dataGrid(Set<Integer> states);
	
	/**
	 * 更新更新时间戳
	 * @param id
	 */
	public void updateUpdateTime(Integer id);
}
