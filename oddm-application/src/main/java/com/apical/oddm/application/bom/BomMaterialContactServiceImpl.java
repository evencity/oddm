package com.apical.oddm.application.bom;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apical.oddm.application.base.BaseServiceImpl;
import com.apical.oddm.core.dao.bom.BomMaterialContactDaoI;
import com.apical.oddm.core.model.bom.BomMaterialContact;
import com.apical.oddm.core.model.page.Pager;

@Service("bomMaterialContactService")
public class BomMaterialContactServiceImpl extends BaseServiceImpl<BomMaterialContact> implements BomMaterialContactServiceI {

	@Autowired
	private BomMaterialContactDaoI bomMaterialContactDao;
	
	@Override
	public Pager<BomMaterialContact> dataGrid(BomMaterialContact contact) {
		return bomMaterialContactDao.dataGrid(contact);
	}

	@Override
	public List<BomMaterialContact> list(BomMaterialContact contact) {
		return bomMaterialContactDao.list(contact);
	}

	@Override
	public void delete(int bomMaterialRefId, int bomMaterialContactId) {
		bomMaterialContactDao.delete(bomMaterialRefId, bomMaterialContactId);
	}

}
