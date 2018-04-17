package com.apical.oddm.application.sale;

import java.util.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apical.oddm.application.base.BaseServiceImpl;
import com.apical.oddm.core.dao.sale.SalePoDaoI;
import com.apical.oddm.core.dao.sale.SalePoUnreadDaoI;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.sale.SalePo;

@Service("salePoService")
public class SalePoServiceImpl extends BaseServiceImpl<SalePo> implements SalePoServiceI {

	@Autowired
	private SalePoDaoI salePoDao;
	
	@Autowired
	private SalePoUnreadDaoI SalePoUnreadDao;
	
	@Override
	public Pager<SalePo> dataGrid() {
		return salePoDao.dataGrid();
	}

	@Override
	public SalePo getSalePo(int id) {
		return salePoDao.getSalePo(id);
	}

/*	@Override
	public Pager<SalePo> dataGrid(OrderInfo orderInfo) {
		return salePoDao.dataGrid(orderInfo);
	}*/

	@Override
	public void delete(int id) {
		SalePoUnreadDao.deleteAllPoId(id);
		super.delete(id);
	}

	@Override
	public Pager<SalePo> dataGridByDateDelivery(Date startDate, Date endDate) {
		return salePoDao.dataGridByDateDelivery(startDate, endDate);
	}

	@Override
	public Pager<SalePo> dataGridByDateOrder(Date startDate, Date endDate) {
		return salePoDao.dataGridByDateOrder(startDate, endDate);
	}

	@Override
	public Pager<SalePo> dataGridByDateUpdate(Date startDate, Date endDate) {
		return salePoDao.dataGridByDateUpdate(startDate, endDate);
	}

	@Override
	public Pager<SalePo> dataGrid(SalePo salePo, Set<Integer> states) {
		return salePoDao.dataGrid(salePo, states);
	}

	@Override
	public SalePo getLatelyCompany(Integer sellerId, Integer merchandiserId) {
		return salePoDao.getLatelyCompany(sellerId, merchandiserId);
	}

	@Override
	public void updateUpdateTime(int id) {
		salePoDao.updateUpdateTime(id);
	}

	@Override
	public SalePo getByOrderId(int orderId, boolean lazy) {
		return salePoDao.getByOrderId(orderId, lazy);
	}

}
