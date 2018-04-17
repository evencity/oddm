package com.apical.oddm.core.dao.document;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.apical.oddm.core.dao.basedao.BaseDaoImpl;
import com.apical.oddm.core.model.document.DocumentCommon;
import com.apical.oddm.core.model.page.Pager;

@Repository("documentCommonDao")
public class DocumentCommonDaoImpl extends BaseDaoImpl<DocumentCommon> implements DocumentCommonDaoI {

	@Override
	public Pager<DocumentCommon> dataGrid() {
		String hql = "select t from DocumentCommon t order by t.uploadtime desc";
		return this.find(hql);
	}

	@Override
	public Pager<DocumentCommon> dataGridByMtName(String nameMaterial) {
		String hql = "select t from DocumentCommon t where t.nameMt like ? order by t.uploadtime desc";
		return this.find(hql, nameMaterial+"%");
	}

	@Override
	public DocumentCommon getByMtName(String nameMaterial) {
		String hql = "select t from DocumentCommon t where t.nameMt=?";
		return (DocumentCommon) this.queryObject(hql, nameMaterial);
	}

	@Override
	public List<DocumentCommon> getListByMtName(String nameMaterial) {
		String hql = "select t from DocumentCommon t where t.nameMt like ? order by t.uploadtime desc";
		return this.list(hql, nameMaterial+"%");
	}

}
