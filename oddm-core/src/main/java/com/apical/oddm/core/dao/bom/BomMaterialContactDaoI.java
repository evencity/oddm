package com.apical.oddm.core.dao.bom;

import java.util.List;

import com.apical.oddm.core.dao.basedao.BaseDaoI;
import com.apical.oddm.core.model.bom.BomMaterialContact;
import com.apical.oddm.core.model.page.Pager;

/**
 * BOM物料联系人表dao操作接口
 * @author lgx
 * 2016-10-16
 */
public interface BomMaterialContactDaoI extends BaseDaoI<BomMaterialContact> {

	/**
	 * 分页获取联系人
	 * @param contact
	 * @return 
	 */
	public Pager<BomMaterialContact> dataGrid(BomMaterialContact contact);	
	
	/**
	 * @param contact.setCompany() 厂家% 半模糊查询
	 * @param contact.setContacts() 联系人% 半模糊查询
	 * @return 
	 */
	public List<BomMaterialContact> list(BomMaterialContact contact);	
	
	/**
	 * 删除物料联系人，不要用delete(int id)方法
	 * @param bomMaterialRefId 物料表的id
	 * @param bomMaterialContactId 联系人表的id
	 */
	public void delete(int bomMaterialRefId, int bomMaterialContactId);
}
