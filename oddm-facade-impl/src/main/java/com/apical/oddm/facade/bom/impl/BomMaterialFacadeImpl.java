package com.apical.oddm.facade.bom.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apical.oddm.application.bom.BomMaterialServiceI;
import com.apical.oddm.core.model.bom.BomMaterial;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.page.SystemContext;
import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.bom.BomMaterialFacade;
import com.apical.oddm.facade.bom.assembler.BomMaterialAssembler;
import com.apical.oddm.facade.bom.dto.BomMaterialDTO;
import com.apical.oddm.facade.pageModel.DataGrid;

@Component("bomMaterialFacade")
public class BomMaterialFacadeImpl implements BomMaterialFacade {

	@Autowired
	private BomMaterialServiceI bomMaterialServiceI;
	
	@Override
	public DataGrid getBomMaterialInPage(BomMaterialDTO bomMaterialDTO,
			PageCommand pageCommand) {
		DataGrid dg = new DataGrid();
		int page = pageCommand.getPage();
		int rows = pageCommand.getRows();
		
		/*System.out.println("第几页："+page);
		System.out.println("每页显示："+rows);
		*/
		SystemContext.setPageOffset(page);
		SystemContext.setPageSize(rows);
		SystemContext.setOrder(pageCommand.getOrder());
		SystemContext.setSort("t." + pageCommand.getSort());
		BomMaterial bomMaterial = new BomMaterial();
		 //System.out.println(bomMaterialDTO.getMtlCode());
		// System.out.println(bomMaterialDTO.getMaterialName());
		 
		 
		 if(bomMaterialDTO.getMtlCode() != null && !"".equals(bomMaterialDTO.getMtlCode())){
			 bomMaterial.setMtlCode(bomMaterialDTO.getMtlCode().trim());
		 }else if(bomMaterialDTO.getMaterialName() != null && !"".equals(bomMaterialDTO.getMaterialName())){
			 bomMaterial.setMaterialName(bomMaterialDTO.getMaterialName().trim());
		 }
		 Pager<BomMaterial> dataGridz = bomMaterialServiceI.dataGrid(bomMaterial);
		 
		/* System.out.println("总个数："+dataGridz.getTotal());*/
		 
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("pageSize", rows);   //页面的大小
		map.put("curRow",(page-1)*rows);  //当前记录数
		List<BomMaterialDTO> BomMaterial = (BomMaterialAssembler.toBomMaterialDTOs(dataGridz.getRows()));
		dg.setRows(BomMaterial);    //设置数据
		dg.setTotal(Long.valueOf(dataGridz.getTotal()));   //设置数据表格记录数
		return dg;
	}

	@Override
	public boolean addBomMaterial(BomMaterialDTO bomMaterialz) {
		BomMaterial bomMaterial = new BomMaterial();
		BeanUtils.copyProperties(bomMaterialz, bomMaterial);
		Serializable id = bomMaterialServiceI.add(bomMaterial);
		if(id != null){
			return true;
		}
		return false;
	}

	@Override
	public void updateBomMaterial(BomMaterialDTO bomMaterial) {
		BomMaterial bomMaterialz = new BomMaterial();
		BeanUtils.copyProperties(bomMaterial, bomMaterialz);
//		MaterialType materialType = bomMaterialServiceI.get(materialBareCommand.getTypeId());
//		if(materialType != null){
//			materialBare.setMaterialType(materialType);
//		}
		bomMaterialServiceI.edit(bomMaterialz);
	}

	@Override
	public boolean delBomMaterial(String materialCode) {
		bomMaterialServiceI.delete(materialCode);
		return true;
	}

	@Override
	public BomMaterialDTO getBomMaterialById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BomMaterialDTO getBomMaterialByNumber(String mtlCode) {
		return BomMaterialAssembler.toBomMaterialDTO(bomMaterialServiceI.get(mtlCode));
	}

	@Override
	public boolean addBomMaterials(List<BomMaterial> bomMaterials) {
		
			try {
				 bomMaterialServiceI.saveBomMaterialBatch(bomMaterials);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			return true;
	}

}
