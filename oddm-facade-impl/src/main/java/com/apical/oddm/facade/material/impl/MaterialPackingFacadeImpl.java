package com.apical.oddm.facade.material.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apical.oddm.application.material.MaterialPackingServiceI;
import com.apical.oddm.application.material.MaterialTypeServiceI;
import com.apical.oddm.core.model.material.MaterialPacking;
import com.apical.oddm.core.model.material.MaterialType;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.page.SystemContext;
import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.base.dto.BasePage;
import com.apical.oddm.facade.material.MaterialPackingFacade;
import com.apical.oddm.facade.material.command.MaterialPackingCommand;
import com.apical.oddm.facade.material.dto.MaterialPackingDTO;
import com.apical.oddm.facade.material.dto.ProductDTO;


/** 
 * @description 类的描述：
 * @author  作者 : zhuzh
 * @date 创建时间：2016年10月28日 上午9:51:01 
 * @version 1.0 
 */
@Component("materialPackingFacade")
public class MaterialPackingFacadeImpl implements MaterialPackingFacade{

	@Autowired 
	private MaterialPackingServiceI materialPackingService;

	@Autowired 
	private MaterialTypeServiceI materialTypeService;
	
	@Override
	public BasePage<MaterialPackingDTO> pageList(MaterialPackingCommand materialPackingCommand,PageCommand pageCommand) {
		// TODO Auto-generated method stub
		SystemContext.setPageOffset(pageCommand.getPage());
		SystemContext.setPageSize(pageCommand.getRows());
		SystemContext.setOrder(pageCommand.getOrder());
		if(pageCommand.getSort().equals("typeName")){
			SystemContext.setSort("t.type");
		}else {
			SystemContext.setSort("t."+pageCommand.getSort());
		}
		Pager<MaterialPacking> dataGrid = null;
		
		BasePage<MaterialPackingDTO> basePage = new  BasePage<MaterialPackingDTO>();
		if(materialPackingCommand != null){
			MaterialPacking materialPackingQuery = new MaterialPacking();
			if(materialPackingCommand.getName() != null){
				materialPackingQuery.setName(materialPackingCommand.getName());
			}
			if(materialPackingCommand.getTypeId() != null){
				materialPackingQuery.setMaterialType(new MaterialType(materialPackingCommand.getTypeId()));
			}
			dataGrid = materialPackingService.dataGridByType(materialPackingQuery);
			if(dataGrid != null && dataGrid.getRows().size() > 0){
				List<MaterialPackingDTO> list = new  ArrayList<MaterialPackingDTO>();
				for(MaterialPacking materialPacking:dataGrid.getRows()){
					MaterialPackingDTO materialPackingDTO = new MaterialPackingDTO();
					BeanUtils.copyProperties(materialPacking, materialPackingDTO);
					materialPackingDTO.setTypeId(materialPacking.getMaterialType().getType());
					materialPackingDTO.setTypeName(materialPacking.getMaterialType().getName());
					list.add(materialPackingDTO);
				}
				basePage.setTotal(dataGrid.getTotal());
				basePage.setRows(list);
			}
		}
		
		return basePage;
	}

	@Override
	public List<MaterialPackingDTO> dataGrid(Integer customType) {
		// TODO Auto-generated method stub
		if(customType != null){
			List<MaterialPacking> dataGrid = materialPackingService.listIsCustom(customType);
			List<MaterialPackingDTO> list = new ArrayList<MaterialPackingDTO>();
			if(dataGrid != null && dataGrid.size() > 0){
				for(MaterialPacking materialPacking :dataGrid){
					MaterialPackingDTO materialPackingDTO = new MaterialPackingDTO();
					BeanUtils.copyProperties(materialPacking, materialPackingDTO);
					list.add(materialPackingDTO);
				}
			}
			
			return list;
		}
		return null;
	}

	@Override
	public Set<ProductDTO> getMaterialPackingProduct(Integer packingId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean add(MaterialPackingCommand materialPackingCommand) {
		// TODO Auto-generated method stub
		MaterialPacking materialPacking = new MaterialPacking();
		BeanUtils.copyProperties(materialPackingCommand, materialPacking);
		MaterialType materialType = materialTypeService.get(materialPackingCommand.getTypeId());
		if(materialType != null){
			materialPacking.setMaterialType(materialType);
		}
		Serializable id = materialPackingService.add(materialPacking);
		if(id != null){
			return true;
		}
		return false;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		materialPackingService.delete(id);
	}

	@Override
	public void edit(MaterialPackingCommand materialPackingCommand) {
		// TODO Auto-generated method stub
		MaterialPacking materialPacking = new MaterialPacking();
		BeanUtils.copyProperties(materialPackingCommand, materialPacking);
		MaterialType materialType = materialTypeService.get(materialPackingCommand.getTypeId());
		if(materialType != null){
			materialPacking.setMaterialType(materialType);
		}
		materialPackingService.edit(materialPacking);
	}

	@Override
	public MaterialPackingDTO get(Integer id) {
		// TODO Auto-generated method stub
		MaterialPackingDTO materialPackingDTO = new MaterialPackingDTO();
		MaterialPacking materialPacking = materialPackingService.get(id);
		BeanUtils.copyProperties(materialPacking, materialPackingDTO);
		if(materialPacking.getMaterialType() != null){
			MaterialType materialType = materialTypeService.get(materialPacking.getType());
			materialPackingDTO.setTypeId(materialPacking.getType());
			materialPackingDTO.setTypeName(materialType.getName());
		}
		return materialPackingDTO;
	}

	@Override
	public List<MaterialPackingDTO> listIsBase(Integer isBase) {
		// TODO Auto-generated method stub
		List<MaterialPacking> listIsBase = materialPackingService.listIsBase(isBase);
		List<MaterialPackingDTO> list = new ArrayList<MaterialPackingDTO>();
		if(listIsBase != null && listIsBase.size() > 0){
			for(MaterialPacking materialPacking : listIsBase){
				MaterialPackingDTO materialPackingDTO = new MaterialPackingDTO();
				BeanUtils.copyProperties(materialPacking, materialPackingDTO);
				list.add(materialPackingDTO);
			}
		}
		return list;
	}


}
