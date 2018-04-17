package com.apical.oddm.core.dao.sale;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.apical.oddm.core.dao.basedao.BaseDaoImpl;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.page.SystemContext;
import com.apical.oddm.core.model.sale.SalePi;
import com.apical.oddm.infra.util.OddmDateUtil;

@Repository("salePiDao")
public class SalePiDaoImpl extends BaseDaoImpl<SalePi> implements SalePiDaoI {

	@Override
	public Pager<SalePi> dataGrid() {
		String hql = "select t from SalePi t order by t.orderDate desc";
		return this.find(hql);
	}

	@Override
	public SalePi getSalePi(SalePi salePi) {
		String hql = "select t from SalePi t where 1=1";
		Map<String, Object> params = new HashMap<String, Object>(1);
		if (salePi != null) {
			if (StringUtils.isNotBlank(salePi.getPiNo())) {
				hql += " and t.piNo=:piNo";
				params.put("piNo", salePi.getPiNo());
			}
		} else {
			return null;
		}
		return (SalePi) this.queryObjectByAlias(hql, params);
	}
	
	@Override
	public Pager<SalePi> dataGridByDateOrder(Date startDate, Date endDate) {
		String hql = "select t from SalePi t where t.orderDate>=:startDate and t.orderDate<=:endDate order by t.orderDate desc";
		Map<String, Object> params = new HashMap<String, Object>(2);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		return this.findByAlias(hql, params);
	}

	private String getHql(SalePi salePi, Map<String, Object> params) {
		String hql = "select t from SalePi t where 1=1";
		if (salePi != null) {
			if (StringUtils.isNotBlank(salePi.getPiNo())) {
				hql +=" and t.piNo like :piNo";
				params.put("piNo", "%"+salePi.getPiNo()+"%");
			}
			if (StringUtils.isNotBlank(salePi.getPoNo())) {
				hql +=" and t.poNo like :poNo";
				params.put("poNo", "%"+salePi.getPoNo()+"%");
			}
			if (StringUtils.isNotBlank(salePi.getClientName())) {
				hql +=" and t.clientName like :clientName";
				params.put("clientName", "%"+salePi.getClientName()+"%");
			}
			if (StringUtils.isNotBlank(salePi.getSeller())) {
				hql +=" and t.seller like :seller";
				params.put("seller", "%"+salePi.getSeller()+"%");
			}
			if (StringUtils.isNotBlank(salePi.getDistrict())) {
				hql +=" and t.district like :district";
				params.put("district", "%"+salePi.getDistrict()+"%");
			}
			if (StringUtils.isNotBlank(salePi.getOrderMonth())) {
				try {
					//获取每月的1号
					Date startDate = OddmDateUtil.dayParse(salePi.getOrderMonth()+"-1");
					Date endDate = OddmDateUtil.getMonthMaxDay(salePi.getOrderMonth());
					hql +=" and t.orderDate>=:startDate";
					hql +=" and t.orderDate<=:endDate";
					params.put("startDate", startDate);
					params.put("endDate", endDate);
				} catch (ParseException e) {
					throw new RuntimeException("解析月份异常:"+salePi.getOrderMonth());
				}
			}
		}
		hql += " order by t.orderDate desc";
		return hql;
	}
	
	@Override
	public Pager<SalePi> dataGrid(SalePi salePi) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = getHql(salePi, params);
		return this.findByAlias(hql, params);
	}

	@Override
	public List<SalePi> list(SalePi salePi) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = getHql(salePi, params);
		SystemContext.setSort("t.orderDate");
		SystemContext.setOrder("asc");
		return this.listByAlias(hql, params);
	}

}
