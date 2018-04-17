package com.apical.oddm.application.bom;

import java.util.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apical.oddm.application.base.BaseServiceImpl;
import com.apical.oddm.core.dao.bom.BomInfoDaoI;
import com.apical.oddm.core.model.bom.BomInfo;
import com.apical.oddm.core.model.page.Pager;

@Service("bomInfoService")
public class BomInfoServiceImpl extends BaseServiceImpl<BomInfo> implements BomInfoServiceI {

	@Autowired
	private BomInfoDaoI bomInfoDao;
	
	@Override
	public BomInfo getBomDetailById(int id) {
		return bomInfoDao.getBomDetailById(id);
	}
	
	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		super.delete(id);
	}

	@Override
	public Pager<BomInfo> dataGrid() {
		return bomInfoDao.dataGrid();
	}

	@Override
	public Pager<BomInfo> dataGridByBomInfo(BomInfo bomInfo, Set<Integer> states) {
		return bomInfoDao.dataGridByBomInfo(bomInfo, states);
	}

	/*@Override
	public Pager<BomInfo> dataGridByMaterialCode(String materialCode) {
		BomInfo bomInfo = new BomInfo();
		bomInfo.setMaterialCode(materialCode);
		return bomInfoDao.dataGridByBomInfo(bomInfo);
	}

	@Override
	public Pager<BomInfo> dataGridByProductName(String productName) {
		BomInfo bomInfo = new BomInfo();
		bomInfo.setProductName(productName);
		return bomInfoDao.dataGridByBomInfo(bomInfo);
	}

	@Override
	public Pager<BomInfo> dataGridByMaker(String maker) {
		BomInfo bomInfo = new BomInfo();
		bomInfo.setMaker(maker);
		return bomInfoDao.dataGridByBomInfo(bomInfo);
	}*/

	@Override
	public Pager<BomInfo> dataGridByDateCreate(Date startDate, Date endDate) {
		return bomInfoDao.dataGridByDateCreate(startDate, endDate);
	}

	@Override
	public Pager<BomInfo> dataGridByDateUpdate(Date startDate, Date endDate) {
		return bomInfoDao.dataGridByDateUpdate(startDate, endDate);
	}

	/*@Override
	public Pager<BomInfo> dataGridByOrderInfo(OrderInfo orderInfo) {
		return bomInfoDao.dataGridByOrder(orderInfo);
	}*/

	@Override
	public BomInfo getBomByMaterialCode(String materialCode) {
		return bomInfoDao.getBomByMaterialCode(materialCode);
	}

	@Override
	public BomInfo getBomByOrderNo(String orderNO) {
		return bomInfoDao.getBomByOrderNo(orderNO);
	}

	@Override
	public void updateUpdateTime(int id) {
		bomInfoDao.updateUpdateTime(id);
	}

	@Override
	public BomInfo getBom(BomInfo bomInfo, boolean lazy) {
		return bomInfoDao.getBom(bomInfo, lazy);
	}

}
