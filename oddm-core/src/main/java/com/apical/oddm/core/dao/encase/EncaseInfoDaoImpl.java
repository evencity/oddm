package com.apical.oddm.core.dao.encase;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.apical.oddm.core.dao.basedao.BaseDaoImpl;
import com.apical.oddm.core.model.encase.EncaseInfo;
import com.apical.oddm.core.model.page.Pager;

@Repository("encaseInfoDao")
public class EncaseInfoDaoImpl extends BaseDaoImpl<EncaseInfo> implements EncaseInfoDaoI {

	@Override
	public Pager<EncaseInfo> dataGrid(EncaseInfo encaseInfo) {
		Map<String, Object> params = new HashMap<String, Object>(2);
		String sql = "select t.id, t.name,"
				+ " t.company,"
				+ " t.address,"
				+ " t.zipcode, t.telphone, t.homepage, t.description,"
				+ " t.timestamp, t.updatetime,"
				+ " t.encase_date as encaseDate, t.unit_weight as unitWeight,"
				+ " t.unit_length as unitLength,"
				+ " sum(e.qty) as qtys"
				+ " from encase_info t"
				+ " left join encase_list e on t.id = e.encase_infoid"
				+ " where 1=1";
		if (encaseInfo != null) {
			if (StringUtils.isNotBlank(encaseInfo.getName())) {
				sql +=" and t.name like :name";
				params.put("name", "%"+encaseInfo.getName()+"%");
			}
			if (encaseInfo.getEncaseDateStart() != null  && encaseInfo.getEncaseDateEnd() != null) {
				sql +=" and t.encase_date>=:startDate and t.encase_date<=:endDate";
				params.put("startDate", encaseInfo.getEncaseDateStart());
				params.put("endDate", encaseInfo.getEncaseDateEnd());
			} else {
				if (encaseInfo.getEncaseDateStart() != null) {
					sql +=" and t.encase_date>=:startDate";
					params.put("startDate", encaseInfo.getEncaseDateStart());
				}
				if (encaseInfo.getEncaseDateEnd() != null) {
					sql +=" and t.encase_date<=:endDate";
					params.put("endDate", encaseInfo.getEncaseDateEnd());
				}
			}
		}
		sql += " group by t.id order by t.id desc";
		return this.findByAliasSql(sql, params, EncaseInfo.class, false);
	}

	@Override
	public EncaseInfo getDetail(int encaseInfoId) {
		String hql = "select t from EncaseInfo t left join fetch t.encaseLists e where t.id=?";
		return (EncaseInfo) this.queryObject(hql, encaseInfoId);
	}

	@Override
	public EncaseInfo getLatestRow() {
		String hql = "select t from EncaseInfo t order by t.id desc";
		return (EncaseInfo) this.queryObject(hql);
	}
	/*public static void main(String[] args) {
		List<Object> oo = new LinkedList();
		System.out.println(oo.get(0));
	}*/
}
