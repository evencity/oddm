package com.apical.oddm.application.produce;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apical.oddm.application.base.BaseServiceImpl;
import com.apical.oddm.core.dao.produce.OrderPrototypeDetialDaoI;
import com.apical.oddm.core.model.produce.OrderPrototypeDetial;

@Service("orderPrototypeDetialService")
public class OrderPrototypeDetialServiceImpl extends BaseServiceImpl<OrderPrototypeDetial> implements OrderPrototypeDetialServiceI {

	@Autowired
	private OrderPrototypeDetialDaoI orderPrototypeDetialDao;
	
	@Override
	public List<OrderPrototypeDetial> getByProId(int proId) {
		return orderPrototypeDetialDao.getByProId(proId);
	}

}
