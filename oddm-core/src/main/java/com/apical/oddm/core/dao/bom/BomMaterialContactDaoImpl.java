package com.apical.oddm.core.dao.bom;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.apical.oddm.core.dao.basedao.BaseDaoImpl;
import com.apical.oddm.core.model.bom.BomMaterialContact;
import com.apical.oddm.core.model.page.Pager;

@Repository("bomMaterialContactDao")
public class BomMaterialContactDaoImpl extends BaseDaoImpl<BomMaterialContact> implements BomMaterialContactDaoI {

	@Override
	public Pager<BomMaterialContact> dataGrid(BomMaterialContact contact) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "select t from BomMaterialContact t where 1=1";
		if (contact != null) {
			if (StringUtils.isNotBlank(contact.getCompany())) {
				hql +=" and t.company like :company";
				params.put("company", contact.getCompany()+"%");
			}
			if (StringUtils.isNotBlank(contact.getContacts())) {
				hql +=" and t.contacts like :contacts";
				params.put("contacts", contact.getContacts()+"%");
			}
		}
		hql += " order by t.updatetime desc";
		return this.findByAlias(hql, params);
	}

	@Override
	public List<BomMaterialContact> list(BomMaterialContact contact) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "select distinct t from BomMaterialContact t where 1=1";
		if (contact != null) {
			if (StringUtils.isNotBlank(contact.getCompany())) {
				hql +=" and t.company like :company";
				params.put("company", contact.getCompany()+"%");
			}
			if (StringUtils.isNotBlank(contact.getContacts())) {
				hql +=" and t.contacts like :contacts";
				params.put("contacts", contact.getContacts()+"%");
			}
		} else {
			return null;
		}
		hql += " order by t.updatetime desc";
		return this.listByAlias(hql, params);
	}

	@Override
	public void delete(int bomMaterialRefId, int bomMaterialContactId) {
		String sql = "update bom_material_ref set contacts=null where id="+bomMaterialRefId;
		this.updateBySql(sql);
		this.delete(bomMaterialContactId);
	}
}
