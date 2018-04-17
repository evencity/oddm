package com.apical.oddm.application.sale;

import java.util.List;

import com.apical.oddm.application.base.BaseServiceI;
import com.apical.oddm.core.model.sale.SalePoList;


/**
 * 内部订单列表操作接口
 * @author lgx
 * 2016-11-1
 */
public interface SalePoListServiceI extends BaseServiceI<SalePoList> {

	/**
	 * 通过poid获取po详细列表
	 * @param poId
	 * @return 仅返列表，慢加载
	 */
	public List<SalePoList> getByPoId(int poId);
}
