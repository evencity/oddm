package com.apical.oddm.application.sale;

import java.util.Date;
import java.util.List;

import com.apical.oddm.application.base.BaseServiceI;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.sale.SaleLc;


/**
 * 信用证台账表操作接口
 * @author lgx
 * 2016-10-16
 */
public interface SaleLcServiceI extends BaseServiceI<SaleLc> {
	/**
	 * 分页获取信用证台账列表
	 * @return
	 */
	public Pager<SaleLc> dataGrid();
	
	/**
	 * 通过参数（仅支持传以下参数）获取信用证台账列表
	 * @param saleLc.getInvoiceNo()
	 * @param saleLc.getClientName()
	 * @param saleLc.getCreditNo()
	 * @param saleLc.dateDeliveryStart() saleLc.dateDeliveryEnd()
	 * @return
	 */
	public Pager<SaleLc> dataGrid(SaleLc saleLc);
	
	/**
	 * 通过交单日期分页获取列表
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @return
	 */
	public Pager<SaleLc> dataGridByDateOrder(Date startDate, Date endDate);

	public List<SaleLc> list(SaleLc saleLc);
}
