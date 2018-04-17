package com.apical.oddm.facade.material.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apical.oddm.application.material.MaterialBareServiceI;
import com.apical.oddm.application.material.MaterialTypeServiceI;
import com.apical.oddm.core.model.material.MaterialBare;
import com.apical.oddm.core.model.material.MaterialType;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.page.SystemContext;
import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.base.dto.BasePage;
import com.apical.oddm.facade.material.MaterialFittingFacade;
import com.apical.oddm.facade.material.command.MaterialFittingCommand;
import com.apical.oddm.facade.material.dto.MaterialFittingDTO;

/**
 * @description 类的描述：
 * @author 作者 : zhuzh
 * @date 创建时间：2016年10月28日 上午9:14:30
 * @version 1.0
 */
@Component("fittingFacade")
public class MaterialFittingFacadeImpl implements MaterialFittingFacade {

	@Autowired
	private MaterialBareServiceI fittingService;

	@Autowired
	private MaterialTypeServiceI materialTypeService;

	@Override
	public BasePage<MaterialFittingDTO> pageList(MaterialFittingCommand fittingCommand, PageCommand pageCommand) {
		// TODO Auto-generated method stub
		SystemContext.setPageOffset(pageCommand.getPage());
		SystemContext.setPageSize(pageCommand.getRows());
		SystemContext.setOrder(pageCommand.getOrder());
		if (pageCommand.getSort().equals("typeName")) {
			SystemContext.setSort("t.type");
		} else {
			SystemContext.setSort("t." + pageCommand.getSort());
		}
		Pager<MaterialBare> dataGrid = null;
		BasePage<MaterialFittingDTO> basePage = new BasePage<MaterialFittingDTO>();
		if (fittingCommand != null) {
			Set<Integer> set = new HashSet<Integer>();
			set.add(4);
			MaterialBare materialBareQuery = new MaterialBare();
			if (fittingCommand.getName() != null) {
				materialBareQuery.setName(fittingCommand.getName());
			}
			if (fittingCommand.getTypeId() != null) {
				materialBareQuery.setMaterialType(new MaterialType(fittingCommand.getTypeId()));
			}

			dataGrid = fittingService.dataGridBySuperType(materialBareQuery, set);
			if (dataGrid != null && dataGrid.getRows().size() > 0) {
				List<MaterialFittingDTO> list = new ArrayList<MaterialFittingDTO>();
				for (MaterialBare materialBare : dataGrid.getRows()) {
					MaterialFittingDTO fittingDTO = new MaterialFittingDTO();
					BeanUtils.copyProperties(materialBare, fittingDTO);
					if (materialBare.getMaterialType() != null) {
						// MaterialType materialType = materialTypeService.get(materialBare.getType());
						fittingDTO.setTypeId(materialBare.getMaterialType().getType());
						fittingDTO.setTypeName(materialBare.getMaterialType().getName());
					}
					list.add(fittingDTO);
				}
				basePage.setTotal(dataGrid.getTotal());
				basePage.setRows(list);
			}
		}

		return basePage;
	}

	@Override
	public Boolean add(MaterialFittingCommand fittingCommand) {
		// TODO Auto-generated method stub
		if (fittingCommand != null) {
			MaterialBare fitting = new MaterialBare();
			BeanUtils.copyProperties(fittingCommand, fitting);
			MaterialType materialType = materialTypeService.get(fittingCommand.getTypeId());
			if (materialType != null) {
				fitting.setMaterialType(materialType);
			}
			Serializable id = fittingService.add(fitting);
			if (id != null) {
				return true;
			}
			return false;
		}
		return false;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		if (id != null) {
			fittingService.delete(id);
		}
	}

	@Override
	public void edit(MaterialFittingCommand fittingCommand) {
		// TODO Auto-generated method stub
		if (fittingCommand != null) {
			MaterialBare fitting = new MaterialBare();
			BeanUtils.copyProperties(fittingCommand, fitting);
			MaterialType materialType = materialTypeService.get(fittingCommand.getTypeId());
			if (materialType != null) {
				fitting.setMaterialType(materialType);
			}
			fittingService.edit(fitting);
		}
	}

	@Override
	public MaterialFittingDTO get(Integer id) {
		// TODO Auto-generated method stub
		if (id != null) {
			MaterialBare fitting = fittingService.get(id);
			MaterialFittingDTO fittingDTO = new MaterialFittingDTO();
			BeanUtils.copyProperties(fitting, fittingDTO);
			if (fitting.getMaterialType() != null) {
				MaterialType materialType = materialTypeService.get(fitting.getType());
				fittingDTO.setTypeId(fitting.getType());
				fittingDTO.setTypeName(materialType.getName());
			}
			return fittingDTO;
		}
		return null;
	}
}
