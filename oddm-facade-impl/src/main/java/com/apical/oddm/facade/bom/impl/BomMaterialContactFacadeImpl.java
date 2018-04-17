package com.apical.oddm.facade.bom.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apical.oddm.application.bom.BomMaterialContactServiceI;
import com.apical.oddm.core.model.bom.BomMaterialContact;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.page.SystemContext;
import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.bom.BomMaterialContactFacade;
import com.apical.oddm.facade.bom.assembler.BomMaterialContactAssembler;
import com.apical.oddm.facade.bom.dto.BomMaterialContactDTO;
import com.apical.oddm.facade.pageModel.DataGrid;
/**
 * bom物料联系人
 * 暂时没有用上
 * @author wangtianqun
 *
 */
@Component("bomMaterialContactFacade")
public class BomMaterialContactFacadeImpl implements BomMaterialContactFacade {

	@Autowired  
	private BomMaterialContactServiceI bomMaterialContactServiceI;
	
	@Override
	public DataGrid getBomMaterialContactInPage(
			BomMaterialContactDTO bomMaterialContact, PageCommand pageCommand) {
		DataGrid dg = new DataGrid();
		int page = pageCommand.getPage();
		int rows = pageCommand.getRows();
		
		BomMaterialContact bmc = new BomMaterialContact();
		
		/*System.out.println("第几页："+page);
		System.out.println("每页显示："+rows);
		*/
		 SystemContext.setPageOffset(page);
		 SystemContext.setPageSize(rows);
		 Pager<BomMaterialContact> dataGrid = null;
		 System.out.println(bomMaterialContact.getContacts());
			 if(bomMaterialContact.getContacts() != null && !"".equals(bomMaterialContact.getContacts())){
				 bmc.setContacts(bomMaterialContact.getContacts());
			 }
				dataGrid = bomMaterialContactServiceI.dataGrid(bmc);
			 
		/*System.out.println("总个数："+dataGrid.getTotal());*/
		 
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("pageSize", rows);   //页面的大小
		map.put("curRow",(page-1)*rows);  //当前记录数
		List<BomMaterialContactDTO> BomMaterialContact = (BomMaterialContactAssembler.toBomMaterialContactDTOs(dataGrid.getRows()));
		dg.setRows(BomMaterialContact);    //设置数据
		dg.setTotal(Long.valueOf(dataGrid.getTotal()));   //设置数据表格记录数
		return dg;
	}

	@Override
	public int addBomMaterialContact(BomMaterialContactDTO bomMaterialContact) {
		BomMaterialContact bomMaterialContactz = new BomMaterialContact();
		bomMaterialContactz.setCellphone(bomMaterialContact.getCellphone());
		bomMaterialContactz.setCompany(bomMaterialContact.getCompany());
		bomMaterialContactz.setContacts(bomMaterialContact.getContacts());
		bomMaterialContactz.setEmail(bomMaterialContact.getEmail());
		bomMaterialContactz.setTelphone(bomMaterialContact.getTelphone());
		bomMaterialContactz.setFax(bomMaterialContact.getFax());
		int id = (int) bomMaterialContactServiceI.add(bomMaterialContactz);
		return id;
	}

	@Override
	public int updateBomMaterialContact(BomMaterialContactDTO bomMaterialContact) {
		BomMaterialContact bomMaterialContactz = bomMaterialContactServiceI.get(bomMaterialContact.getId());
		bomMaterialContactz.setCellphone(bomMaterialContact.getCellphone());
		bomMaterialContactz.setCompany(bomMaterialContact.getCompany());
		bomMaterialContactz.setContacts(bomMaterialContact.getContacts());
		bomMaterialContactz.setEmail(bomMaterialContact.getEmail());
		bomMaterialContactz.setTelphone(bomMaterialContact.getTelphone());
		bomMaterialContactz.setFax(bomMaterialContact.getFax());
		bomMaterialContactServiceI.edit(bomMaterialContactz);
		return 1;
	}

	@Override
	public boolean delBomMaterialContact(int ids,int mtcId) {
		bomMaterialContactServiceI.delete(ids,mtcId);
		return true;
	}

	@Override
	public BomMaterialContactDTO getBomMaterialContactById(int id) {
		return BomMaterialContactAssembler.toBomMaterialContactDTO(bomMaterialContactServiceI.get(id));
	}

	@Override
	public List<BomMaterialContactDTO> getBomMaterialContacts() {
		BomMaterialContact bmc = new BomMaterialContact();
		return BomMaterialContactAssembler.toBomMaterialContactDTOs(bomMaterialContactServiceI.dataGrid(bmc).getRows());
	}

}
