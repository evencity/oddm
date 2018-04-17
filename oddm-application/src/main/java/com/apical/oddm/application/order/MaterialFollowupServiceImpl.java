package com.apical.oddm.application.order;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apical.oddm.application.base.BaseServiceImpl;
import com.apical.oddm.core.constant.BomConst;
import com.apical.oddm.core.constant.MaterialFollowupConst;
import com.apical.oddm.core.constant.OrderInfoConst;
import com.apical.oddm.core.dao.bom.BomInfoDaoI;
import com.apical.oddm.core.dao.order.MaterialFollowupDaoI;
import com.apical.oddm.core.dao.order.OrderInfoDaoI;
import com.apical.oddm.core.model.bom.BomInfo;
import com.apical.oddm.core.model.order.MaterialFollowup;
import com.apical.oddm.core.model.order.OrderInfo;
import com.apical.oddm.core.model.page.Pager;

@Service("materialFollowupService")
public class MaterialFollowupServiceImpl extends BaseServiceImpl<MaterialFollowup> implements MaterialFollowupServiceI {

	@Autowired
	private MaterialFollowupDaoI materialFollowupDao;
	
	@Autowired
	private OrderInfoDaoI orderInfoDao;
	
	@Autowired
	private BomInfoDaoI bomInfoDao;
	
	@Override
	public Serializable add(MaterialFollowup t) {
		Serializable add = super.add(t);
		return add;
	}
	
	@Override
	public void edit(MaterialFollowup t) {
		super.edit(t);
		updateOrderMaterialfinished(t, t.getOrderId());
	}

	/**
	 * 物料交期全部完结（批量数累加等于订单数），则触发订单到下一个状态
	 * @param materialFollowup
	 */
	private void updateOrderMaterialfinished(MaterialFollowup materialFollowup, Integer orderId) {
		if (materialFollowup.getState() == MaterialFollowupConst.archive) {
			OrderInfo orderInfo = new OrderInfo();
			orderInfo.setId(orderId);
			List<MaterialFollowup> listAll = materialFollowupDao.listAll(orderInfo, null, false);
			int totalQlity = 0;
			OrderInfo order = null;
			for (MaterialFollowup mfp : listAll) {
				if (order == null) order = mfp.getOrderInfo();
				if(mfp.getQuality() != null) {
					totalQlity += mfp.getQuality();
				}
			}
			if (totalQlity == order.getQuantity()) {
				//OrderInfo order = orderInfoDao.get(t.getOrderId());
				order.setState(OrderInfoConst.materialfinished);
				orderInfoDao.saveOrUpdate(order);
				BomInfo bomInfo = new BomInfo();bomInfo.setOrderInfo(orderInfo);
				BomInfo bom = bomInfoDao.getBom(bomInfo, true);
				if (bom != null) {
					bom.setState(BomConst.materialfinished);
					bomInfoDao.saveOrUpdate(bom);
				}
			}
		}
	}
	
	@Override
	public Pager<MaterialFollowup> dataGrid() {
		return materialFollowupDao.dataGrid();
	}

	@Override
	public Pager<MaterialFollowup> dataGrid(Set<Integer> states) {
		return materialFollowupDao.dataGrid(states);
	}

	@Override
	public Pager<MaterialFollowup> dataGridByOrderInfo(OrderInfo orderInfo, Set<Integer> states) {
		return materialFollowupDao.dataGridByOrder(orderInfo, states);
	}

	@Override
	public Pager<MaterialFollowup> dataGridByDateDeliver(Date startDate,
			Date endDate) {
		return materialFollowupDao.dataGridByDateDeliver(startDate, endDate);
	}

	@Override
	public Pager<MaterialFollowup> dataGridByDateCommit(Date startDate,
			Date endDate) {
		return materialFollowupDao.dataGridByDateCommit(startDate, endDate);
	}

	@Override
	public MaterialFollowup getMaterialFollowup(int id) {
		return materialFollowupDao.getMaterialFollowup(id);
	}

	@Override
	public List<MaterialFollowup> listAll(OrderInfo orderInfo,
			Set<Integer> states, boolean lazy) {
		return materialFollowupDao.listAll(orderInfo, states, lazy);
	}

	/*		@Override
	public Pager<MaterialFollowup> dataGridByUserId(int userId) {
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setUser(new User(userId));
		return materialFollowupDao.dataGridByOrder(orderInfo);
	}
	
	@Override
	public Pager<MaterialFollowup> dataGridByOrderNo(String orderNo) {
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setOrderNo(orderNo);
		return materialFollowupDao.dataGridByOrder(orderInfo);
	}*/

}
