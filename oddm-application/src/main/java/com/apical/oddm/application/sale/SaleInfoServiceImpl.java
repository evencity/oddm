package com.apical.oddm.application.sale;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apical.oddm.application.base.BaseServiceImpl;
import com.apical.oddm.core.dao.sale.SaleInfoDaoI;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.sale.SaleInfo;
import com.apical.oddm.core.vo.sale.SaleDeptStatisVo;
import com.apical.oddm.core.vo.sale.SaleInfoAllYearVo;
import com.apical.oddm.core.vo.sale.SaleInfoYearVo;

@Service("saleInfoServiceI")
public class SaleInfoServiceImpl extends BaseServiceImpl<SaleInfo> implements SaleInfoServiceI {

	@Autowired
	private SaleInfoDaoI saleInfoDao;
	
/*	@Autowired
	private UserDaoI userDao;*/
	
	@Override
	public Pager<SaleInfo> dataGrid() {
		return saleInfoDao.dataGrid();
	}

	@Override
	public Pager<SaleInfo> dataGrid(SaleInfo saleInfo) {
		return saleInfoDao.dataGrid(saleInfo);
	}

	@Override
	public Pager<SaleInfo> dataGridByDateOrder(Date startDate, Date endDate) {
		return saleInfoDao.dataGridByDateOrder(startDate, endDate);
	}

	@Override
	public List<SaleDeptStatisVo> getSaleStatisVo(String year, String currency) {
		return saleInfoDao.getSaleStatisVo(year, currency);
	}

	@Override
	public SaleInfo getDetail(int id) {
		return saleInfoDao.getDetail(id);
	}

	@Override
	public SaleInfo getByOrderId(int orderId, boolean lazy) {
		return saleInfoDao.getByOrderId(orderId, lazy);
	}

	@Override
	public List<SaleInfo> list(SaleInfo saleInfo) {
		return saleInfoDao.list(saleInfo);
	}

	@Override
	public List<SaleInfoYearVo> getSaleInfoYearVoList(int type, String year,
			String currency) {
		return saleInfoDao.getSaleInfoYearVoList(type, year, currency);
	}

	@Override
	public List<SaleInfoAllYearVo> getSaleInfoAllYearVoList(String yearStart,
			String yearEnd, String currency) {
		return saleInfoDao.getSaleInfoAllYearVoList(yearStart, yearEnd, currency);
	}

}
