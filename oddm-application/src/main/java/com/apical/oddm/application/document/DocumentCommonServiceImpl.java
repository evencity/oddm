package com.apical.oddm.application.document;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apical.oddm.application.base.BaseServiceImpl;
import com.apical.oddm.core.dao.document.DocumentCommonDaoI;
import com.apical.oddm.core.dao.document.DocumentDaoI;
import com.apical.oddm.core.model.document.DocumentCommon;
import com.apical.oddm.core.model.page.Pager;

@Service("documentCommonService")
public class DocumentCommonServiceImpl extends BaseServiceImpl<DocumentCommon> implements DocumentCommonServiceI {

	@Autowired
	private DocumentDaoI documentDao;
	
	@Autowired
	private DocumentCommonDaoI documentCommonDao;
	
	@Override
	public void delete(int id) {
		super.delete(id);
	}
	
	@Override
	public Pager<DocumentCommon> dataGrid() {
		return documentCommonDao.dataGrid();
	}

	@Override
	public Pager<DocumentCommon> dataGridByMtName(String nameMaterial) {
		return documentCommonDao.dataGridByMtName(nameMaterial);
	}

	@Override
	public DocumentCommon getByMtName(String nameMaterial) {
		return documentCommonDao.getByMtName(nameMaterial);
	}

	@Override
	public List<DocumentCommon> getListByMtName(String nameMaterial) {
		return documentCommonDao.getListByMtName(nameMaterial);
	}

}
