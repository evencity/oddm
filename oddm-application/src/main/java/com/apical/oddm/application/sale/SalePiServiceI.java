package com.apical.oddm.application.sale;

import java.util.Date;
import java.util.List;

import com.apical.oddm.application.base.BaseServiceI;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.sale.SalePi;


/**
 * PI表操作接口
 * @author lgx
 * 2016-10-16
 */
public interface SalePiServiceI extends BaseServiceI<SalePi> {

	/**
	 * 分页获取PI列表
	 * @return
	 */
	public Pager<SalePi> dataGrid();
	
	/**
	 * 通过制定参数查询单个pi记录
	 * @param salePi.setPiNo();
	 * @return
	 */
	public SalePi getSalePi(SalePi salePi);
	
	/**
	 * 通过参数（仅支持传以下参数）获取PI列表
	 * @param salePi.setPiNo()
	 * @param salePi.setClientName()
	 * @param salePi.setBizName()
	 * @param salePi.setDistrict()
	 * @param salePi.setOrderMonth()
	 * @return
	 */
	public Pager<SalePi> dataGrid(SalePi salePi);
	
	/**
	 * 通过下单日期分页获取列表
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @return
	 */
	public Pager<SalePi> dataGridByDateOrder(Date startDate, Date endDate);

	/**
	 * @param salePi.setPiNo()
	 * @param salePi.setClientName()
	 * @param salePi.setBizName()
	 * @param salePi.setDistrict()
	 * @param salePi.setOrderMonth()
	 * @return
	 */
	public List<SalePi> list(SalePi salePi);
	
}
